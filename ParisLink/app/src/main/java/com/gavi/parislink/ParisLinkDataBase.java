package com.gavi.parislink;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ParisLinkDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ma_base_de_donnees";
    private static final int DATABASE_VERSION = 1;

    // Définir la structure de la table Utilisateur
    // Table Utilisateur
    private static final String TABLE_UTILISATEUR = "utilisateur";
    private static final String UTILISATEUR_ID = "_id";
    private static final String UTILISATEUR_NOM = "nom";
    private static final String UTILISATEUR_PRENOM = "prenom";
    private static final String UTILISATEUR_PHONE = "phone";
    private static final String UTILISATEUR_EMAIL = "email";
    private static final String UTILISATEUR_LOGIN = "login";
    private static final String UTILISATEUR_MDP = "mdp";

    // Table Covoiturage
    private static final String TABLE_COVOITURAGE = "covoiturage";
    private static final String COVOITURAGE_ID = "_id";
    private static final String COVOITURAGE_MODELE = "modele";
    private static final String COVOITURAGE_COULEUR = "couleur";
    private static final String COVOITURAGE_IMMATRICULATION = "immatriculation";
    private static final String COVOITURAGE_LIEU_RDV = "lieuRDV";
    private static final String COVOITURAGE_DESTINATION = "destination";
    private static final String COVOITURAGE_HEURE_RDV = "heureRDV";
    private static final String COVOITURAGE_NB_PLACE_PROPOSE = "nbPlacePropose";
    private static final String COVOITURAGE_UTILISATEUR_ID = "utilisateur_login"; // Clé étrangère

    // Requête SQL pour créer la table Utilisateur
    private static final String SQL_CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
                    UTILISATEUR_LOGIN + " TEXT PRIMARY KEY," +
                    UTILISATEUR_NOM + " TEXT," +
                    UTILISATEUR_PRENOM + " TEXT," +
                    UTILISATEUR_PHONE + " TEXT," +
                    UTILISATEUR_EMAIL + " TEXT," +
                    UTILISATEUR_MDP + " TEXT)";

    // Requête SQL pour créer la table Covoiturage
    private static final String SQL_CREATE_TABLE_COVOITURAGE =
            "CREATE TABLE " + TABLE_COVOITURAGE + " (" +
                    COVOITURAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COVOITURAGE_MODELE + " TEXT," +
                    COVOITURAGE_COULEUR + " TEXT," +
                    COVOITURAGE_IMMATRICULATION + " TEXT," +
                    COVOITURAGE_LIEU_RDV + " TEXT," +
                    COVOITURAGE_DESTINATION + " TEXT," +
                    COVOITURAGE_HEURE_RDV + " TEXT," +
                    COVOITURAGE_NB_PLACE_PROPOSE + " INTEGER," +
                    COVOITURAGE_UTILISATEUR_ID + " TEXT," + // Clé étrangère
                    "FOREIGN KEY(" + COVOITURAGE_UTILISATEUR_ID + ") REFERENCES " + TABLE_UTILISATEUR + "(" + UTILISATEUR_LOGIN + "))"; // Définition de la contrainte de clé étrangère

    public ParisLinkDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Méthode pour insérer des données dans la table Utilisateur
    public long insertUtilisateur(String login, String nom, String prenom, String phone, String email, String mdp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UTILISATEUR_LOGIN, login);
        values.put(UTILISATEUR_NOM, nom);
        values.put(UTILISATEUR_PRENOM, prenom);
        values.put(UTILISATEUR_PHONE, phone);
        values.put(UTILISATEUR_EMAIL, email);
        values.put(UTILISATEUR_MDP, mdp);
        long result = -1;

        try {
            result = db.insert(TABLE_UTILISATEUR, null, values);
        } catch (SQLException e) {
            // Gérer l'exception si une erreur se produit lors de l'insertion
            e.printStackTrace();
        } finally {
            db.close();
        }

        return result;
    }
    @SuppressLint("Range")
    public Utilisateur getUtilisateurByLoginAndPassword(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Utilisateur utilisateur = null;

        // Colonnes à récupérer
        String[] columns = {
                UTILISATEUR_LOGIN,
                UTILISATEUR_NOM,
                UTILISATEUR_PRENOM,
                UTILISATEUR_PHONE,
                UTILISATEUR_EMAIL,
                UTILISATEUR_MDP
        };

        // Clause WHERE
        String selection = UTILISATEUR_LOGIN + " = ? AND " + UTILISATEUR_MDP + " = ?";
        // Arguments de la clause WHERE
        String[] selectionArgs = {login, password};

        // Exécuter la requête
        Cursor cursor = db.query(
                TABLE_UTILISATEUR,  // Table à interroger
                columns,            // Colonnes à retourner
                selection,          // Clause WHERE
                selectionArgs,      // Arguments de la clause WHERE
                null,               // GROUP BY (non utilisé)
                null,               // HAVING (non utilisé)
                null                // ORDER BY (non utilisé)
        );

        // Vérifier si un utilisateur correspondant a été trouvé
        if (cursor.moveToFirst()) {
            // Créer un objet Utilisateur avec les données du curseur
            utilisateur = new Utilisateur(
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_NOM)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_PHONE)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_MDP))
            );
        }

        // Fermer le curseur et la base de données
        cursor.close();
        db.close();

        // Retourner l'utilisateur trouvé (null si aucun utilisateur correspondant n'a été trouvé)
        return utilisateur;
    }
    @SuppressLint("Range")
    public Utilisateur getUtilisateurByCovoiturageLogin(String login) {
        SQLiteDatabase db = this.getReadableDatabase();
        Utilisateur utilisateur = null;

        // Colonnes à récupérer
        String[] columns = {
                UTILISATEUR_NOM,
                UTILISATEUR_PRENOM,
                UTILISATEUR_PHONE,
                UTILISATEUR_EMAIL,
                UTILISATEUR_LOGIN,
                UTILISATEUR_MDP
        };

        // Clause WHERE
        String selection = UTILISATEUR_LOGIN + " = ?";
        // Arguments de la clause WHERE
        String[] selectionArgs = {login};

        // Exécuter la requête
        Cursor cursor = db.query(
                TABLE_UTILISATEUR,  // Table à interroger
                columns,            // Colonnes à retourner
                selection,          // Clause WHERE
                selectionArgs,      // Arguments de la clause WHERE
                null,               // GROUP BY (non utilisé)
                null,               // HAVING (non utilisé)
                null                // ORDER BY (non utilisé)
        );

        // Vérifier si un utilisateur correspondant a été trouvé
        if (cursor.moveToFirst()) {
            // Créer un objet Utilisateur avec les données du curseur
            utilisateur = new Utilisateur(
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_NOM)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_PRENOM)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_PHONE)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_LOGIN)),
                    cursor.getString(cursor.getColumnIndex(UTILISATEUR_MDP))
            );
        }

        // Fermer le curseur et la base de données
        cursor.close();
        db.close();

        // Retourner l'utilisateur trouvé (null si aucun utilisateur correspondant n'a été trouvé)
        return utilisateur;
    }



    // Méthode pour insérer des données dans la table Covoiturage
    public long insertCovoiturage(String login, String modele, String couleur, String immatriculation, String lieuRDV, String destination, String heureRDV, int nbPlacePropose) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COVOITURAGE_MODELE, modele);
        values.put(COVOITURAGE_COULEUR, couleur);
        values.put(COVOITURAGE_IMMATRICULATION, immatriculation);
        values.put(COVOITURAGE_LIEU_RDV, lieuRDV);
        values.put(COVOITURAGE_DESTINATION, destination);
        values.put(COVOITURAGE_HEURE_RDV, heureRDV);
        values.put(COVOITURAGE_NB_PLACE_PROPOSE, nbPlacePropose);
        values.put(COVOITURAGE_UTILISATEUR_ID, login); // Associer l'utilisateur au covoiturage
        long result = -1;

        try {
            result = db.insert(TABLE_COVOITURAGE, null, values);
        } catch (SQLException e) {
            // Gérer l'exception si une erreur se produit lors de l'insertion
            e.printStackTrace();
        } finally {
            db.close();
        }

        return result;
    }

    @SuppressLint("Range")
    public ArrayList<Covoiturage> getCovoituragesByUtilisateur(String login) {
        ArrayList<Covoiturage> covoiturages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Colonnes à récupérer
        String[] columns = {
                COVOITURAGE_ID,
                COVOITURAGE_MODELE,
                COVOITURAGE_COULEUR,
                COVOITURAGE_IMMATRICULATION,
                COVOITURAGE_LIEU_RDV,
                COVOITURAGE_DESTINATION,
                COVOITURAGE_HEURE_RDV,
                COVOITURAGE_NB_PLACE_PROPOSE
        };

        // Clause WHERE
        String selection = COVOITURAGE_UTILISATEUR_ID + " = ?";
        // Arguments de la clause WHERE
        String[] selectionArgs = {login};

        // Exécuter la requête
        Cursor cursor = db.query(
                TABLE_COVOITURAGE,  // Table à interroger
                columns,            // Colonnes à retourner
                selection,          // Clause WHERE
                selectionArgs,      // Arguments de la clause WHERE
                null,               // GROUP BY (non utilisé)
                null,               // HAVING (non utilisé)
                null                // ORDER BY (non utilisé)
        );

        // Parcourir le curseur pour récupérer les covoiturages
        if (cursor.moveToFirst()) {
            do {
                Covoiturage covoiturage = new Covoiturage();
                covoiturage.setId(cursor.getInt(cursor.getColumnIndex(COVOITURAGE_ID)));
                covoiturage.setModele(cursor.getString(cursor.getColumnIndex(COVOITURAGE_MODELE)));
                covoiturage.setCouleur(cursor.getString(cursor.getColumnIndex(COVOITURAGE_COULEUR)));
                covoiturage.setImmatriculation(cursor.getString(cursor.getColumnIndex(COVOITURAGE_IMMATRICULATION)));
                covoiturage.setLieuRDV(cursor.getString(cursor.getColumnIndex(COVOITURAGE_LIEU_RDV)));
                covoiturage.setDestination(cursor.getString(cursor.getColumnIndex(COVOITURAGE_DESTINATION)));
                covoiturage.setHeureRDV(cursor.getString(cursor.getColumnIndex(COVOITURAGE_HEURE_RDV)));
                covoiturage.setNbPlacePropose(cursor.getInt(cursor.getColumnIndex(COVOITURAGE_NB_PLACE_PROPOSE)));

                // Ajouter le covoiturage à la liste
                covoiturages.add(covoiturage);
            } while (cursor.moveToNext());
        }

        // Fermer le curseur et la base de données
        cursor.close();
        db.close();

        // Retourner la liste des covoiturages de l'utilisateur
        return covoiturages;
    }

    @SuppressLint("Range")
    public ArrayList<Covoiturage> getAllCovoituragesProposes() {
        ArrayList<Covoiturage> covoiturages = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Requête SQL pour récupérer les covoiturages proposés par les utilisateurs
        String query = "SELECT * FROM " + TABLE_COVOITURAGE;

        // Exécution de la requête
        Cursor cursor = db.rawQuery(query, null);

        // Parcours du curseur pour récupérer les covoiturages
        if (cursor.moveToFirst()) {
            do {
                Covoiturage covoiturage = new Covoiturage();
                covoiturage.setId(cursor.getInt(cursor.getColumnIndex(COVOITURAGE_ID)));
                covoiturage.setUtilisateurId(cursor.getString(cursor.getColumnIndex(COVOITURAGE_UTILISATEUR_ID)));
                covoiturage.setModele(cursor.getString(cursor.getColumnIndex(COVOITURAGE_MODELE)));
                covoiturage.setCouleur(cursor.getString(cursor.getColumnIndex(COVOITURAGE_COULEUR)));
                covoiturage.setImmatriculation(cursor.getString(cursor.getColumnIndex(COVOITURAGE_IMMATRICULATION)));
                covoiturage.setLieuRDV(cursor.getString(cursor.getColumnIndex(COVOITURAGE_LIEU_RDV)));
                covoiturage.setDestination(cursor.getString(cursor.getColumnIndex(COVOITURAGE_DESTINATION)));
                covoiturage.setHeureRDV(cursor.getString(cursor.getColumnIndex(COVOITURAGE_HEURE_RDV)));
                covoiturage.setNbPlacePropose(cursor.getInt(cursor.getColumnIndex(COVOITURAGE_NB_PLACE_PROPOSE)));

                // Ajout du covoiturage à la liste
                covoiturages.add(covoiturage);
            } while (cursor.moveToNext());
        }

        // Fermeture du curseur et de la base de données
        cursor.close();
        db.close();

        // Retour de la liste des covoiturages proposés par les utilisateurs
        return covoiturages;
    }


    // Méthode pour récupérer les données de la table Utilisateur
    public Cursor getAllUtilisateurs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_UTILISATEUR, null);
    }

    public Cursor getAllCovoiturages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_COVOITURAGE, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer les tables
        db.execSQL(SQL_CREATE_TABLE_UTILISATEUR);
        db.execSQL(SQL_CREATE_TABLE_COVOITURAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettre à jour la base de données si nécessaire
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COVOITURAGE);
        onCreate(db);
    }
}
