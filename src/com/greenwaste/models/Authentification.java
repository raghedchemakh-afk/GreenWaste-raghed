public class Authentification {

    public void sInscrire(String nom,
                          String prenom,
                          String email,
                          String motDePasse,
                          String usine,
                          String departement) {

        System.out.println("Utilisateur inscrit : "
                + nom + " " + prenom);
    }

    public void seConnecter(String email,
                            String motDePasse) {

        System.out.println("Connexion reussie avec : "
                + email);
    }

    public void seDeconnecter() {

        System.out.println("Utilisateur deconnecte");
    }
}