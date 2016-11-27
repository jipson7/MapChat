package ca.uoit.caleb.wildviper;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowLongClickListener {

    private static final int RC_PERMISSION_REQUEST = 1231;

    User mUser;
    MapStyleDBHelper mMapStyleDBHelper;
    FirebaseDBHelper mFirebaseDBHelper;
    MessageListener mMessageListener;

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

        mActivity = getActivity();

        /**
         * Get Database helper for Map Styles
         */
        mMapStyleDBHelper = new MapStyleDBHelper(mActivity);

        /**
         * Get Database helper for firebase db
         */
        mFirebaseDBHelper = new FirebaseDBHelper();

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
        setUserLocation();
    }

    /**
     * Check user has set location permissions
     * If so, move to users location
     * If not, request location permissions
     */
    private void setUserLocation() {
        if (mActivity.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                mUserLocation = new LatLng(location.getLatitude(), location.getLongitude());
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                mUser = new User(firebaseUser, mUserLocation);
                markUserOnline(mUser);
                if (mMap != null) {
                    moveToLocation(mUserLocation);
                    mMap.setMyLocationEnabled(true);
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
    public void moveToLocation(LatLng location) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, mZoomLevel));
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
                    setUserLocation();
                } else {
                    Toast.makeText(mActivity, "Moving to default location.", Toast.LENGTH_LONG).show();
                    moveToLocation(mUserLocation);
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
        setMapStyle();
        mMap.getUiSettings().setMapToolbarEnabled(false);
        moveToLocation(mUserLocation);
        mMessageListener = new MessageListener(mMap);
        mFirebaseDBHelper.setMessageListener(mMessageListener);
        mFirebaseDBHelper.setUserListener(new UserListener(mMap));
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setInfoWindowAdapter(new MessageWindowAdapter(mActivity));
    }

    /**
     * Map Long Click to Add new Message
     * @param latLng
     */
    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent i = new Intent(mActivity, WriteMessageActivity.class);
        i.putExtra("latitude", latLng.latitude);
        i.putExtra("longitude", latLng.longitude);
        mActivity.startActivity(i);
    }

    /**
     * Info Window long click to delete message
     * @param marker
     */
    @Override
    public void onInfoWindowLongClick(Marker marker) {
        String userId = mUser.id;
        String messageKey = mMessageListener.getMessageKey(marker.getId(), userId);
        if (messageKey != null) {
            mFirebaseDBHelper.deleteSingleMessage(messageKey);
        } else {
            Toast.makeText(mActivity, getString(R.string.map_fragment_toast_delete_error), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get currently selected style from DB and set it on map
     */
    public void setMapStyle() {
        mMap.setMapStyle(new MapStyleOptions(mMapStyleDBHelper.getSelectedStyleJson()));
    }


    private void markUserOnline(User user) {
        if (user != null) {
            mFirebaseDBHelper.saveUser(user);
        }
    }

    private void markUserOffline(User user) {
        if (user != null) {
            mFirebaseDBHelper.deleteUser(user);
        }
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
        markUserOffline(mUser);
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onResume() {
        if (mMap != null) {
            setMapStyle();
        }
        markUserOnline(mUser);
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
