package com.gavi.parislink;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CovoiturageActivity extends AppCompatActivity {

    private TextView nomTextView, prenomTextView, annulerBtn;
    private EditText modeleEditText, couleurEditText, immatriculationEditText,
            lieuRDVEditText, destinationEditText, heureRDVEditText, nbPlaceProposeEditText;
    private Button btn;
    private Utilisateur user = new Utilisateur();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covoiturage);

        // Initialisation des vues
        nomTextView = findViewById(R.id.covNom);
        prenomTextView = findViewById(R.id.covPrenom);
        modeleEditText = findViewById(R.id.modele);
        couleurEditText = findViewById(R.id.couleur);
        immatriculationEditText = findViewById(R.id.immatriculation);
        lieuRDVEditText = findViewById(R.id.lieuRDV);
        destinationEditText = findViewById(R.id.destination);
        heureRDVEditText = findViewById(R.id.heureRDV);
        nbPlaceProposeEditText = findViewById(R.id.nbPlacePropose);
        btn = findViewById(R.id.covoiturageFormBtn);
        annulerBtn = findViewById(R.id.annulerOffre);

        // Récupération des données utilisateur de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            user = (Utilisateur) intent.getSerializableExtra("user");
            nomTextView.setText(user.getNom());
            prenomTextView.setText(user.getPrenom());
        }

        // Listener pour le bouton "Créer l'offre de covoiturage"
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mod = modeleEditText.getText().toString();
                String coul = couleurEditText.getText().toString();
                String imma = immatriculationEditText.getText().toString();
                String lieu = lieuRDVEditText.getText().toString();
                String dest = destinationEditText.getText().toString();
                String heure = heureRDVEditText.getText().toString();
                String place = nbPlaceProposeEditText.getText().toString();

                // Vérification des champs vides
                if (mod.trim().isEmpty() || coul.trim().isEmpty() || imma.trim().isEmpty() ||
                        lieu.trim().isEmpty() || dest.trim().isEmpty() || heure.trim().isEmpty() ||
                        place.trim().isEmpty()) {
                    Toast.makeText(CovoiturageActivity.this, R.string.champsValidation, Toast.LENGTH_LONG).show();
                } else {
                    // Insertion des données dans la base de données
                    ParisLinkDataBase dbHelper = new ParisLinkDataBase(CovoiturageActivity.this);
                    long result = dbHelper.insertCovoiturage(user.getLogin(), mod, coul, imma, lieu, dest, heure, Integer.parseInt(place));

                    // Affichage d'un message Toast en fonction du résultat de l'insertion
                    if (result != -1) {
                        Toast.makeText(CovoiturageActivity.this, R.string.succesInsertion, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(CovoiturageActivity.this, R.string.failedInsertion, Toast.LENGTH_SHORT).show();
                    }

                    // Redirection vers l'activité de validation de l'offre de covoiturage
                    Intent i = new Intent(CovoiturageActivity.this, ValidationOffreActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }
            }
        });

// Listener pour le bouton "Annuler"
        annulerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Afficher une boîte de dialogue de confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(CovoiturageActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Êtes-vous sûr de vouloir annuler ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // L'utilisateur a confirmé l'annulation, passer à HomeFragment dans MainActivity
                        Intent i = new Intent();
                        i.putExtra("showHomeFragment", true); // Indiquer que HomeFragment doit être affiché
                        setResult(RESULT_OK, i); // Définir le résultat pour indiquer l'action à effectuer
                        finish(); // Terminer l'activité CovoiturageActivity
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out); // Animation de transition
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // L'utilisateur a annulé l'annulation, ne rien faire
                        dialog.dismiss(); // Fermer la boîte de dialogue
                    }
                });
                builder.show(); // Afficher la boîte de dialogue de confirmation
            }
        });


    }
}
