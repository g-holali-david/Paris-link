package com.gavi.parislink;

import static com.gavi.parislink.R.id.linearCovoiturages;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;

public class DisplayEntry extends AppCompatActivity {

    private LinearLayout linearCovoiturages;
    private ArrayList<Covoiturage> covoiturages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);

        // Récupérer la référence du LinearLayout
        linearCovoiturages = findViewById(R.id.linearCovoiturages);

        // Initialiser la liste des covoiturages (à remplacer par vos données réelles)
        covoiturages = getCovoituragesFromDatabase();

        // Vérifier si des covoiturages sont disponibles
        if (covoiturages.isEmpty()) {
            Toast.makeText(this, "Aucun covoiturage disponible.", Toast.LENGTH_SHORT).show();
        } else {
            // Afficher chaque covoiturage dans le LinearLayout
            for (Covoiturage covoiturage : covoiturages) {
                addCovoiturageToLayout(covoiturage);
            }
        }
    }

    // Méthode fictive pour récupérer les covoiturages depuis la base de données (à remplacer par vos données réelles)
    private ArrayList<Covoiturage> getCovoituragesFromDatabase() {
        ArrayList<Covoiturage> covoiturages = new ArrayList<>();

        // Créer une instance de ParisLinkDataBase pour interagir avec la base de données
        ParisLinkDataBase dbHelper = new ParisLinkDataBase(this); // "this" représente le contexte de l'activité

        // Récupérer tous les covoiturages de la base de données en utilisant la méthode getAllCovoituragesProposes()
        covoiturages = dbHelper.getAllCovoituragesProposes();

        return covoiturages;
    }


    // Méthode pour ajouter un covoiturage au LinearLayout
    private void addCovoiturageToLayout(Covoiturage covoiturage) {
        // Créer une vue pour afficher le covoiturage
        View covoiturageView = LayoutInflater.from(this).inflate(R.layout.item_covoiturage, null);

        // Récupérer les TextViews à partir du layout item_covoiturage
        TextView textViewNomPrenom = covoiturageView.findViewById(R.id.textViewNomPrenom);
        TextView textViewModele = covoiturageView.findViewById(R.id.textViewModele);
        TextView textViewCouleur = covoiturageView.findViewById(R.id.textViewCouleur);
        TextView textViewImmatriculation = covoiturageView.findViewById(R.id.textViewImmatriculation);
        TextView textViewLieuRDV = covoiturageView.findViewById(R.id.textViewLieuRDV);
        TextView textViewDestination = covoiturageView.findViewById(R.id.textViewDestination);
        TextView textViewHeureRDV = covoiturageView.findViewById(R.id.textViewHeureRDV);
        TextView textViewNbPlacePropose = covoiturageView.findViewById(R.id.textViewNbPlacePropose);

        // Récupérer les informations sur l'utilisateur associé à ce covoiturage
        Utilisateur utilisateur = getUtilisateurByCovoiturageLogin(covoiturage.getUtilisateurId());

        // Vérifier si l'utilisateur a été trouvé
        if (utilisateur != null) {
            // Afficher le nom et prénom de l'utilisateur
            textViewNomPrenom.setText("Proposé par: " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        } else {
            // Afficher un message d'erreur si l'utilisateur n'a pas été trouvé
            textViewNomPrenom.setText("Utilisateur inconnu");
        }
        // Ajouter les données du covoiturage aux TextViews
        textViewModele.setText("Modèle: " + covoiturage.getModele());
        textViewCouleur.setText("Couleur: " + covoiturage.getCouleur());
        textViewImmatriculation.setText("Immatriculation: " + covoiturage.getImmatriculation());
        textViewLieuRDV.setText("Lieu de rendez-vous: " + covoiturage.getLieuRDV());
        textViewDestination.setText("Destination: " + covoiturage.getDestination());
        textViewHeureRDV.setText("Heure de rendez-vous: " + covoiturage.getHeureRDV());
        textViewNbPlacePropose.setText("Nombre de places proposées: " + String.valueOf(covoiturage.getNbPlacePropose()));

        // Ajouter la vue du covoiturage au LinearLayout
        linearCovoiturages.addView(covoiturageView);
    }

    // Méthode pour obtenir les informations sur l'utilisateur associé à un covoiturage à partir de son login
    private Utilisateur getUtilisateurByCovoiturageLogin(String login) {
        ParisLinkDataBase dbHelper = new ParisLinkDataBase(this);
        return dbHelper.getUtilisateurByCovoiturageLogin(login);
    }
}