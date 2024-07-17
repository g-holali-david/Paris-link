package com.gavi.parislink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ValidationOffreActivity extends AppCompatActivity {

    Button validationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_offre);

        validationBtn = (Button) findViewById(R.id.validationBtn);

        validationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("showHomeFragment", true); // Indiquer que HomeFragment doit être affiché
                setResult(RESULT_OK, i); // Définir le résultat pour indiquer l'action à effectuer
                finish(); // Terminer l'activité CovoiturageActivity
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Animation de transition
            }
        });
    }
}