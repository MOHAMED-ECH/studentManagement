package com.example.demo;

public class Etudiant {

    private String nom;
    private String prenom;
    private String email;
    private String gsm;
    private String cin;

    private static int numero;
    Etudiant (String cin, String nom, String prenom, String email, String gsm){
        numero++;
        this.setNom(nom);
        this.setPrenom(prenom);
        this.setEmail(email);
        this.setGsm(gsm);
        this.setCin(cin);

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
