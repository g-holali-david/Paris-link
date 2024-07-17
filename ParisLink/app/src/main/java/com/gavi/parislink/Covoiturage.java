package com.gavi.parislink;
public class Covoiturage {
    private int id;
    private String utilisateurId; // Nouvel attribut pour stocker l'identifiant de l'utilisateur
    private String modele;
    private String couleur;
    private String immatriculation;
    private String lieuRDV;
    private String destination;
    private String heureRDV;
    private int nbPlacePropose;

    // Constructeur

    public Covoiturage(){}
    public Covoiturage(int id, String utilisateurId, String modele, String couleur, String immatriculation, String lieuRDV, String destination, String heureRDV, int nbPlacePropose) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.modele = modele;
        this.couleur = couleur;
        this.immatriculation = immatriculation;
        this.lieuRDV = lieuRDV;
        this.destination = destination;
        this.heureRDV = heureRDV;
        this.nbPlacePropose = nbPlacePropose;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getLieuRDV() {
        return lieuRDV;
    }

    public void setLieuRDV(String lieuRDV) {
        this.lieuRDV = lieuRDV;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHeureRDV() {
        return heureRDV;
    }

    public void setHeureRDV(String heureRDV) {
        this.heureRDV = heureRDV;
    }

    public int getNbPlacePropose() {
        return nbPlacePropose;
    }

    public void setNbPlacePropose(int nbPlacePropose) {
        this.nbPlacePropose = nbPlacePropose;
    }

    // Méthode toString pour afficher les informations du covoiturage
    @Override
    public String toString() {
        return "Covoiturage{" +
                "id=" + id +
                ", utilisateurId=" + utilisateurId +
                ", modele='" + modele + '\'' +
                ", couleur='" + couleur + '\'' +
                ", immatriculation='" + immatriculation + '\'' +
                ", lieuRDV='" + lieuRDV + '\'' +
                ", destination='" + destination + '\'' +
                ", heureRDV='" + heureRDV + '\'' +
                ", nbPlacePropose=" + nbPlacePropose +
                '}';
    }
}

