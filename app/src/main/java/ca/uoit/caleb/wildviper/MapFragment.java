package ca.uoit.caleb.wildviper;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMapLongClickListener {

    private static final int RC_PERMISSION_REQUEST = 1231;

    FirebaseUser mUser;
    MapStyleDBHelper mMapStyleDBHelper;
    DatabaseReference mMessagesReference;

    private GoogleApiClient mGoogleApiClient;
    private Float mZoomLevel = 15.0f;
    private GoogleMap mMap;
    private MapView mMapView;
    private Activity mActivity;
    private LatLng mUserLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        /**
         * Get Parent Activity
         */
        mActivity = getActivity();

        /**
         * Get Current User
         */
        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        /**
         * Get Database helper for Map Styles
         */
        mMapStyleDBHelper = new MapStyleDBHelper(mActivity);

        /**
         * Create Google Location Api object
         */
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        /**
         * Set Default Location to Toronto
         */
        mUserLocation = new LatLng(43.652, -79.3832);

        /**
         * Attach map to XML
         */
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        return v;
    }

    /**
     * Google Api Connected Success
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setCurrentLocation();
    }

    /**
     * Check user has set location permissions
     * If so, move to users location
     * If not, request location permissions
     */
    private void setCurrentLocation() {
        if (mActivity.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                mUserLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (mMap != null) {
                    moveToUserLocation();
                }
            }
        } else {
            requestLocationPermissions();
        }
    }

    /**
     * Start permission request
     */
    private void requestLocationPermissions() {
        FragmentCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                RC_PERMISSION_REQUEST);
    }

    /**
     * Center map around location
     */
    private void moveToUserLocation() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mUserLocation, mZoomLevel));
        dropUserOverlay();
    }

    /**
     * Drop marker of users profile image at location
     */
    private void dropUserOverlay() {
        Uri photoUri = mUser.getPhotoUrl();
        String username = mUser.getDisplayName();
        UserOverlay userOverlay = new UserOverlay(photoUri, username, mUserLocation, mMap);
    }


    /**
     * Callback when user accepts or rejects Location Permission
     * If user rejects move to default location, otherwise recall method to get user location
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RC_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setCurrentLocation();
                } else {
                    Toast.makeText(mActivity, "Moving to default location.", Toast.LENGTH_LONG).show();
                    moveToUserLocation();
                }
                return;
            }
        }
    }


    /**
     * Map has been loaded
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        setMapStyle();
        moveToUserLocation();

        /**
         * Get Firebase DB reference to message tree and set this as listener
         * Listener is used to drop markers
         */
        mMessagesReference = FirebaseDatabase.getInstance().getReference().child("messages");
        mMessagesReference.addValueEventListener(new MessageListener(mMap, mActivity));
    }

    /**
     * Get currently selected style from DB and set it on map
     */
    public void setMapStyle() {
        mMap.setMapStyle(new MapStyleOptions(mMapStyleDBHelper.getSelectedStyleJson()));
    }

    /**
     * On Map Click
     * @param latLng - Location of click
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent i = new Intent(mActivity, WriteMessageActivity.class);
        i.putExtra("latitude", latLng.latitude);
        i.putExtra("longitude", latLng.longitude);
        startActivity(i);
    }

    /**
     * Google Api connection suspended
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }


    /**
     * Activity LifeCycles
     */
    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onResume() {
        if (mMap != null) {
            setMapStyle();
        }
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
