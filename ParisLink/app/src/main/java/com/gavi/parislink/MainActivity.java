package com.gavi.parislink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.gavi.parislink.databinding.ActivityMainBinding;
import com.gavi.parislink.R;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_COVOITURAGE_ACTIVITY = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                replaceFragment(new HomeFragment());
            }

            if (id == R.id.cov) {
                replaceFragment(new OffreFragment());
            }
            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null); // Ajout à la pile de retour arrière
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COVOITURAGE_ACTIVITY && resultCode == RESULT_OK) {
            boolean showHomeFragment = data.getBooleanExtra("showHomeFragment", false);
            if (showHomeFragment) {
                replaceFragment(new HomeFragment());
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Animation de transition
            }
        }
    }
}
