import java.util.ArrayList;
import java.util.List;

public class Authentification {

    private List<Utilisateur> utilisateurs;
    private Utilisateur utilisateurConnecte;

    public Authentification() {
        this.utilisateurs = new ArrayList<>();
        this.utilisateurConnecte = null;
    }

    // S'inscrire
    public boolean sInscrire(String nom, String prenom, String email,
                              String motDePasse, String nomUsine, 
                              String typeIndustrie) {
        // Vérifier si email déjà utilisé
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equals(email)) {
                System.out.println(" Email déjà utilisé : " + email);
                return false;
            }
        }
        // Vérifier longueur mot de passe
        if (motDePasse.length() < 8) {
            System.out.println(" Mot de passe trop court (8 caractères minimum)");
            return false;
        }
        // Créer le compte
        int id = utilisateurs.size() + 1;
        Utilisateur u = new Utilisateur(id, nom, prenom, email, 
                                        motDePasse, nomUsine, typeIndustrie);
        utilisateurs.add(u);
        System.out.println("✅ Compte créé : " + prenom + " " + nom);
        return true;
    }

    // Se connecter
    public boolean seConnecter(String email, String motDePasse) {
        for (Utilisateur u : utilisateurs) {
            if (u.getEmail().equals(email) && 
                u.getMotDePasse().equals(motDePasse)) {
                utilisateurConnecte = u;
                System.out.println("Connexion réussie : " + u.getPrenom());
                return true;
            }
        }
        System.out.println(" Email ou mot de passe incorrect");
        return false;
    }

    // Se déconnecter
    public void seDeconnecter() {
        if (utilisateurConnecte != null) {
            System.out.println(" Déconnexion de : " + utilisateurConnecte.getPrenom());
            utilisateurConnecte = null;
        }
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}