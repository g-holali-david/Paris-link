package com.gavi.parislink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    Button btn, carteBtn, deconBnt, covoiturageList;
    TextView t;
    Utilisateur user = new Utilisateur();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        t = view.findViewById(R.id.nomConducteur);
        btn = view.findViewById(R.id.backBtn);
        covoiturageList = view.findViewById(R.id.covoiturageList);
        deconBnt = view.findViewById(R.id.deconnexionBtn);
        carteBtn = view.findViewById(R.id.carteBtn);

        Intent i = requireActivity().getIntent();
        if (i != null){
            user = (Utilisateur) i.getSerializableExtra("user");
            t.setText("Bonjour " + user.getPrenom());
        }

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CovoiturageActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        covoiturageList.setOnClickListener(v -> {
            OffreFragment offreFragment = new OffreFragment();
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.frameLayout, offreFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            ((BottomNavigationView) requireActivity().findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.cov);
        });

        deconBnt.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Confirmation de déconnexion");
            builder.setMessage("Êtes-vous sûr de vouloir vous déconnecter ?");
            builder.setPositiveButton("Oui", (dialog, which) -> requireActivity().finish());
            builder.setNegativeButton("Non", (dialog, which) -> dialog.dismiss());
            builder.show();
        });

        carteBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MapsActivity.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });

        return view;
    }

}