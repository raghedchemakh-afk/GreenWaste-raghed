public class Utilisateur {

    private String nom;
    private String prenom;
    private String email;

    public Utilisateur(String nom,
                       String prenom,
                       String email) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public void afficherUtilisateur() {

        System.out.println("Nom : " + nom);
        System.out.println("Prenom : " + prenom);
        System.out.println("Email : " + email);
    }
}