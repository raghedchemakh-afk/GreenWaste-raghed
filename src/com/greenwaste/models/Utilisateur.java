
public class Utilisateur {

    private int     id;
    private String  nom;
    private String  email;
    private String  motDePasseHash;
    private String  role;
    private boolean compteActif;

    public Utilisateur() {}

    public Utilisateur(int id, String nom,
                       String email, String role) {
        this.id          = id;
        this.nom         = nom;
        this.email       = email;
        this.role        = role;
        this.compteActif = false;
    }

    public String hasherMotDePasse(String mdp) {
        return String.valueOf(mdp.hashCode());
    }

    public int     getId()             { return id; }
    public String  getNom()            { return nom; }
    public String  getEmail()          { return email; }
    public String  getRole()           { return role; }
    public boolean isCompteActif()     { return compteActif; }
    public String  getMotDePasseHash() { return motDePasseHash; }

    public void setId(int id)                  { this.id = id; }
    public void setNom(String nom)             { this.nom = nom; }
    public void setEmail(String email)         { this.email = email; }
    public void setRole(String role)           { this.role = role; }
    public void setCompteActif(boolean b)      { this.compteActif = b; }
    public void setMotDePasseHash(String hash) { this.motDePasseHash = hash; }

    @Override
    public String toString() {
        return "Utilisateur[" + id + "] "
            + nom + " | " + role
            + " | Actif: " + compteActif;
    }
}