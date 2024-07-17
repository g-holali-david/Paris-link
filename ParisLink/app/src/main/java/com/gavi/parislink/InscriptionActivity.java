package com.gavi.parislink;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class InscriptionActivity extends AppCompatActivity {

    Button btnSubmit, btnAnnuler;
    EditText nom, prenom, phone, email, login, mdp, confirmPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        nom = findViewById(R.id.inputNom);
        prenom = findViewById(R.id.inputPrenom);
        phone = findViewById(R.id.inputPhone);
        email = findViewById(R.id.inputEmail);
        login = findViewById(R.id.inputLogin);
        mdp = findViewById(R.id.inputPwd);
        confirmPwd = findViewById(R.id.inputConfPwd);

        btnSubmit = findViewById(R.id.buttonSubmit);
        btnAnnuler = findViewById(R.id.buttonAnnuler);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs de saisie
                String userNom = nom.getText().toString();
                String userPrenom = prenom.getText().toString();
                String userPhone = phone.getText().toString();
                String userEmail = email.getText().toString();
                String userLogin = login.getText().toString();
                String userMdp = mdp.getText().toString();
                String userConfMdp = confirmPwd.getText().toString();

                // Vérifier si tous les champs sont remplis
                if (TextUtils.isEmpty(userNom) || TextUtils.isEmpty(userPrenom) ||
                        TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(userEmail) ||
                        TextUtils.isEmpty(userLogin) || TextUtils.isEmpty(userMdp) ||
                        TextUtils.isEmpty(userConfMdp)) {
                    // Afficher un message d'erreur si un champ est vide
                    Toast.makeText(InscriptionActivity.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                } else {
                    // Vérifier si les mots de passe correspondent
                    if (userConfMdp.equals(userMdp)) {
                        // Créer une instance de ParisLinkDataBase
                        ParisLinkDataBase db = new ParisLinkDataBase(InscriptionActivity.this);

                        // Insérer un nouvel utilisateur dans la base de données
                        long result = db.insertUtilisateur(userLogin, userNom, userPrenom, userPhone, userEmail, userMdp);

                        // Vérifier si l'insertion a réussi
                        if (result != -1) {
                            Toast.makeText(InscriptionActivity.this, R.string.succes, Toast.LENGTH_SHORT).show();
                            Toast.makeText(InscriptionActivity.this, R.string.redirectString, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(InscriptionActivity.this, ConnexionActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else {
                            // Afficher un message d'erreur avec un Snackbar
                            Snackbar.make(v, "Un utilisateur avec le même login existe déjà!", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        // Les mots de passe ne correspondent pas
                        Toast.makeText(InscriptionActivity.this, "Les mots de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InscriptionActivity.this, R.string.toastAnnuler, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
