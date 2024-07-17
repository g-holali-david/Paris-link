package com.gavi.parislink;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class OffreFragment extends Fragment {
    private LinearLayout linearCovoiturages;
    private ArrayList<Covoiturage> covoiturages;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static OffreFragment newInstance(String param1, String param2) {
        OffreFragment fragment = new OffreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offre, container, false);
        linearCovoiturages = view.findViewById(R.id.linearCovoiturages);
        covoiturages = getCovoituragesFromDatabase();
        if (covoiturages.isEmpty()) {
            Toast.makeText(requireActivity(), "Aucun covoiturage disponible.", Toast.LENGTH_SHORT).show();
        } else {
            for (Covoiturage covoiturage : covoiturages) {
                addCovoiturageToLayout(covoiturage);
            }
        }
        return view;
    }

    private ArrayList<Covoiturage> getCovoituragesFromDatabase() {
        ArrayList<Covoiturage> covoiturages = new ArrayList<>();
        ParisLinkDataBase dbHelper = new ParisLinkDataBase(requireActivity());
        covoiturages = dbHelper.getAllCovoituragesProposes();
        return covoiturages;
    }

    @SuppressLint("SetTextI18n")
    private void addCovoiturageToLayout(Covoiturage covoiturage) {
        if (linearCovoiturages != null) {
            View covoiturageView = LayoutInflater.from(requireActivity()).inflate(R.layout.item_covoiturage, linearCovoiturages, false);
            TextView textViewNomPrenom = covoiturageView.findViewById(R.id.textViewNomPrenom);
            TextView textViewModele = covoiturageView.findViewById(R.id.textViewModele);
            TextView textViewCouleur = covoiturageView.findViewById(R.id.textViewCouleur);
            TextView textViewImmatriculation = covoiturageView.findViewById(R.id.textViewImmatriculation);
            TextView textViewLieuRDV = covoiturageView.findViewById(R.id.textViewLieuRDV);
            TextView textViewDestination = covoiturageView.findViewById(R.id.textViewDestination);
            TextView textViewHeureRDV = covoiturageView.findViewById(R.id.textViewHeureRDV);
            TextView textViewNbPlacePropose = covoiturageView.findViewById(R.id.textViewNbPlacePropose);

            Utilisateur utilisateur = getUtilisateurByCovoiturageLogin(covoiturage.getUtilisateurId());

            if (utilisateur != null) {
                textViewNomPrenom.setText("Proposé par: " + utilisateur.getNom() + " " + utilisateur.getPrenom());
            } else {
                textViewNomPrenom.setText("Utilisateur inconnu");
            }

            textViewModele.setText("Modèle: " + covoiturage.getModele());
            textViewCouleur.setText("Couleur: " + covoiturage.getCouleur());
            textViewImmatriculation.setText("Immatriculation: " + covoiturage.getImmatriculation());
            textViewLieuRDV.setText("Lieu de rendez-vous: " + covoiturage.getLieuRDV());
            textViewDestination.setText("Destination: " + covoiturage.getDestination());
            textViewHeureRDV.setText(covoiturage.getHeureRDV());
            textViewNbPlacePropose.setText("Nombre de places proposées: " + String.valueOf(covoiturage.getNbPlacePropose()));

            // Ajout de la vue de division horizontale après chaque item de covoiturage
            View divider = new View(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.divider_height));
            divider.setLayoutParams(params);
            divider.setBackgroundResource(R.drawable.divider_horizontal);
            linearCovoiturages.addView(divider);
            linearCovoiturages.addView(covoiturageView);
        }
    }

    private Utilisateur getUtilisateurByCovoiturageLogin(String login) {
        ParisLinkDataBase dbHelper = new ParisLinkDataBase(requireActivity());
        return dbHelper.getUtilisateurByCovoiturageLogin(login);
    }
}
