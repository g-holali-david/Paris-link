package com.gavi.parislink;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.Serializable;

public class ConnexionActivity extends AppCompatActivity  {
    Button connexionBtn, inscriptionBtn;
    EditText inputLogin, inputPwd;
    ParisLinkDataBase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        inputLogin = findViewById(R.id.loginInput);
        inputPwd = findViewById(R.id.pwdInput);
        db = new ParisLinkDataBase(this);
        connexionBtn = findViewById(R.id.connexionBtn);
        inscriptionBtn = findViewById(R.id.inscriptionBtn);

        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userLogin = inputLogin.getText().toString();
                String userPwd = inputPwd.getText().toString();

                if (userLogin.isEmpty() || userPwd.isEmpty()) {
                    Toast.makeText(ConnexionActivity.this, R.string.toastValidationConnexion, Toast.LENGTH_SHORT).show();
                    return;
                }
                Utilisateur user = db.getUtilisateurByLoginAndPassword(userLogin, userPwd);
                if (user != null) {
                    Toast.makeText(ConnexionActivity.this, R.string.toastConnexion, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ConnexionActivity.this, MainActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    Toast.makeText(ConnexionActivity.this, R.string.toastIncorrectIdentification, Toast.LENGTH_SHORT).show();
                }
            }
        });

        inscriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ConnexionActivity.this, R.string.toastConnexionToInscription, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ConnexionActivity.this, InscriptionActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

    }

}