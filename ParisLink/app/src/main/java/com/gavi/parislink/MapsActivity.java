package com.gavi.parislink;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Covoiturage> covoiturages;
    private ParisLinkDataBase parisLinkDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        parisLinkDataBase = new ParisLinkDataBase(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Charger les covoiturages de manière asynchrone
        new LoadCovoituragesTask().execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng parisCoordinates = new LatLng(48.8566, 2.3522); // Coordonnées de Paris
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(parisCoordinates, 12));

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(adapter);
    }

    private void addMarkersToMap(ArrayList<Covoiturage> covoiturages) {
        for (Covoiturage covoiturage : covoiturages) {
            moveToLocation(covoiturage.getLieuRDV(), covoiturage);
        }
    }

    private void moveToLocation(String address, Covoiturage covoiturage) {
        new Thread(() -> {
            LatLng latLng = getLocationFromAddress(address);
            if (latLng != null) {
                runOnUiThread(() -> {
                    Utilisateur utilisateur = parisLinkDataBase.getUtilisateurByCovoiturageLogin(covoiturage.getUtilisateurId());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Covoiturage proposé par " + utilisateur.getNom())
                            .snippet("Modèle: " + covoiturage.getModele() + "\nDestination: " + covoiturage.getDestination() + "\nCouleur: " + covoiturage.getCouleur() + "\nLieu du RDV: "+ covoiturage.getLieuRDV() + "\nHeure de RDV: " + covoiturage.getHeureRDV())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_taxi32))
                    );
                    marker.setTag(covoiturage);
                });
            } else {
                Log.e("Erreur", "Impossible de résoudre l'adresse en coordonnées géographiques : " + address);
            }
        }).start();
    }

    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        LatLng latLng = null;

        try {
            addresses = geocoder.getFromLocationName(strAddress, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            } else {
                Log.e("Geocoder", "Aucune adresse trouvée pour : " + strAddress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latLng;
    }

    private class LoadCovoituragesTask extends AsyncTask<Void, Void, ArrayList<Covoiturage>> {

        @Override
        protected ArrayList<Covoiturage> doInBackground(Void... voids) {
            return parisLinkDataBase.getAllCovoituragesProposes();
        }

        @Override
        protected void onPostExecute(ArrayList<Covoiturage> result) {
            super.onPostExecute(result);
            covoiturages = result;
            if (mMap != null && covoiturages != null) {
                addMarkersToMap(covoiturages);
            }
        }
    }
}