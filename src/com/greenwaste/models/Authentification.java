import java.util.ArrayList;
import java.util.List;

public class Authentification {

    private List<Utilisateur> utilisateursInscrits;
    private Utilisateur       utilisateurConnecte;

    public Authentification() {
        this.utilisateursInscrits = new ArrayList<>();
        this.utilisateurConnecte  = null;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : inscrire()
    //
    // PROMPT UTILISÉ :
    // "Écris une méthode Java inscrire(id, nom, email,
    //  motDePasse, role) pour une classe Authentification
    //  qui gère une List<Utilisateur>. Elle doit vérifier
    //  que l'email n'est pas déjà utilisé, que le mot de
    //  passe fait min 6 caractères, créer l'utilisateur,
    //  hasher son mot de passe et l'ajouter à la liste."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean inscrire(int id, String nom,
    //         String email, String mdp, String role) {
    //     Utilisateur u = new Utilisateur(id,nom,email,role);
    //     utilisateursInscrits.add(u);
    //     return true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Ajout vérification email déjà existant
    // 2. Ajout validation format email
    // 3. Ajout validation longueur mot de passe
    // 4. Hash du mot de passe avant stockage
    // ─────────────────────────────────────────────
    public boolean inscrire(int id, String nom,String email,String motDePasse,String role) {
        if (nom == null || nom.trim().isEmpty()) {
            System.out.println("Nom obligatoire !");
            return false;
        }
        if (email == null
                || email.indexOf("@") < 1
                || !email.contains(".")) {
            System.out.println("Email invalide !");
            return false;
        }
        for (Utilisateur u : utilisateursInscrits) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                System.out.println(
                    "Email deja utilise : " + email);
                return false;
            }
        }
        if (motDePasse == null
                || motDePasse.length() < 6) {
            System.out.println(
                "Mot de passe min 6 caracteres !");
            return false;
        }
        Utilisateur nouveau = new Utilisateur(
            id, nom.trim(),
            email.toLowerCase(), role);
        nouveau.setMotDePasseHash(
            nouveau.hasherMotDePasse(motDePasse));
        nouveau.setCompteActif(true);
        utilisateursInscrits.add(nouveau);
        System.out.println("Compte cree : "
            + nom + " (" + role + ")");
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : seConnecter()
    //
    // PROMPT UTILISÉ :
    // "Méthode Java seConnecter(email, motDePasse) dans
    //  Authentification qui cherche l'email dans
    //  List<Utilisateur>, compare le hash du mot de
    //  passe, et stocke l'utilisateur connecté."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean seConnecter(String email, String mdp){
    //     for (Utilisateur u : utilisateursInscrits)
    //         if (u.getEmail().equals(email)) {
    //             utilisateurConnecte = u;
    //             return true;
    //         }
    //     return false;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Comparaison du HASH et non mot de passe clair
    // 2. Vérification compte actif
    // 3. equalsIgnoreCase() pour l'email
    // 4. Message différent selon cas d'erreur
    // ─────────────────────────────────────────────
    public boolean seConnecter(String email,
                                String motDePasse) {
        for (Utilisateur u : utilisateursInscrits) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                if (!u.isCompteActif()) {
                    System.out.println(
                        "Compte inactif !");
                    return false;
                }
                String hash =
                    u.hasherMotDePasse(motDePasse);
                if (!hash.equals(
                        u.getMotDePasseHash())) {
                    System.out.println(
                        "Mot de passe incorrect !");
                    return false;
                }
                this.utilisateurConnecte = u;
                System.out.println(
                    "Connexion reussie : "
                    + u.getNom()
                    + " [" + u.getRole() + "]");
                return true;
            }
        }
        System.out.println(
            "Email introuvable : " + email);
        return false;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 3 : seDeconnecter()
    //
    // PROMPT UTILISÉ :
    // "Méthode seDeconnecter() qui vérifie qu'un
    //  utilisateur est connecté, affiche son nom
    //  et remet utilisateurConnecte à null."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void seDeconnecter() {
    //     utilisateurConnecte = null;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification utilisateur connecté
    // 2. Affichage nom avant déconnexion
    // 3. Retour boolean
    // ─────────────────────────────────────────────
    public boolean seDeconnecter() {
        if (utilisateurConnecte == null) {
            System.out.println(
                "Aucun utilisateur connecte !");
            return false;
        }
        System.out.println("Deconnexion : "
            + utilisateurConnecte.getNom());
        this.utilisateurConnecte = null;
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 4 : suspendreCompte()
    //
    // PROMPT UTILISÉ :
    // "Méthode suspendreCompte(email) qui cherche
    //  l'utilisateur par email et met compteActif
    //  à false avec message de confirmation."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void suspendreCompte(String email) {
    //     for (Utilisateur u : utilisateursInscrits)
    //         if (u.getEmail().equals(email))
    //             u.setCompteActif(false);
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Retourner boolean
    // 2. equalsIgnoreCase pour email
    // 3. Message avec nom utilisateur
    // ─────────────────────────────────────────────
    public boolean suspendreCompte(String email) {
        for (Utilisateur u : utilisateursInscrits) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                u.setCompteActif(false);
                System.out.println(
                    "Compte suspendu : "
                    + u.getNom());
                return true;
            }
        }
        System.out.println(
            "Utilisateur introuvable : " + email);
        return false;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
    public List<Utilisateur> getUtilisateursInscrits() {
        return utilisateursInscrits;
    }
    public int getNombreInscrits() {
        return utilisateursInscrits.size();
    }
}