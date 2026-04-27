package com.greenwastemodels;

public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String nomUsine;
    private String typeIndustrie;

    // Constructeur vide
    public Utilisateur() {}

    // Constructeur complet
    public Utilisateur(int id, String nom, String prenom, 
                       String email, String motDePasse, 
                       String nomUsine, String typeIndustrie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nomUsine = nomUsine;
        this.typeIndustrie = typeIndustrie;
    }

    // Méthodes métier
    public void afficherDetails() {
        System.out.println("=== Utilisateur ===");
        System.out.println("Nom complet : " + prenom + " " + nom);
        System.out.println("Email : " + email);
        System.out.println("Usine : " + nomUsine);
        System.out.println("Industrie : " + typeIndustrie);
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getMotDePasse() { return motDePasse; }
    public String getNomUsine() { return nomUsine; }
    public String getTypeIndustrie() { return typeIndustrie; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public void setNomUsine(String nomUsine) { this.nomUsine = nomUsine; }
    public void setTypeIndustrie(String t) { this.typeIndustrie = t; }

    @Override
    public String toString() {
        return prenom + " " + nom + " (" + email + ") - " + nomUsine;
    }
}