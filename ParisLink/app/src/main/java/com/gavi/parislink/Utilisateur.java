package com.gavi.parislink;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Utilisateur implements Serializable {
    private String nom;
    private String prenom;
    private String phone;
    private String email;
    private String login;
    private String mdp;
    private List<Covoiturage> covoiturages;

    public Utilisateur() {}

    public Utilisateur(String nom, String prenom, String phone, String email, String login, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.email = email;
        this.login = login;
        this.mdp = mdp;
    }

    public void addCovoiturage(Covoiturage covoiturage) {
        covoiturages.add(covoiturage);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @NonNull
    @Override
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", mdp='" + mdp + '\'' +
                '}';
    }
}

