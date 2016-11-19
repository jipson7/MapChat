package ca.uoit.caleb.wildviper;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks {

    private static final int RC_PERMISSION_REQUEST = 1231;

    FirebaseUser mUser;

    private GoogleApiClient mGoogleApiClient;
    private Float mZoomLevel = 15.0f;
    private GoogleMap mMap;
    private MapView mMapView;
    private Activity mActivity;
    private LatLng mDefaultLocation;
    private LatLng mUserLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        /**
         * Get Parent Activity
         */
        mActivity = getActivity();

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
        mDefaultLocation = new LatLng(43.652, -79.3832);

        /**
         * Attach map to XML
         */
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        return v;
    }

    private void setCurrentLocation() {
        if (mActivity.checkCallingOrSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location != null) {
                mUserLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (mMap != null) {
                    moveToLocation(mUserLocation);
                }
            }
        } else {
            requestLocationPermissions();
        }
    }

    private void moveToLocation(LatLng location) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(location).title(mUser.getDisplayName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, mZoomLevel));
    }


    private void requestLocationPermissions() {
        FragmentCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                RC_PERMISSION_REQUEST);
    }

    /**
     * Callback when user accepts or rejects Location Permission
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
                    moveToLocation(mDefaultLocation);
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

        changeMapStyle(MapData.getMapStyle(mActivity));

        if (mUserLocation == null) {
            moveToLocation(mDefaultLocation);
        } else {
            moveToLocation(mUserLocation);
        }
    }

    public void changeMapStyle(int mapStyle) {
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(mActivity, mapStyle));
    }


    /**
     * Google Api Connected Success
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setCurrentLocation();
    }

    /**
     * Google Api connection suspended
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {

    }

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
