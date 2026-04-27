import java.util.ArrayList;
import java.util.List;

public class Administrateur extends Utilisateur {
    private List<Alerte> alertes;

    public Administrateur(int id, String nom,String email) {
        super(id, nom, email, "ADMIN");
        this.alertes = new ArrayList<>();
        this.setCompteActif(true);
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : surveillerSeuils()
    //
    // PROMPT UTILISÉ :
    // "Méthode surveillerSeuils(seuils) dans
    //  Administrateur qui vérifie chaque seuil
    //  et collecte les alertes retournées."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void surveillerSeuils(
    //     List<SeuilReglementaire> seuils) {
    //     for (SeuilReglementaire s : seuils)
    //         s.verifierSeuil();
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Collecte des alertes retournées
    // 2. Comptage alertes critiques
    // 3. Message résumé final
    // ─────────────────────────────────────────────
    public void surveillerSeuils(
            List<SeuilReglementaire> seuils) {
        System.out.println("Surveillance seuils...");
        int critiques = 0;
        for (SeuilReglementaire s : seuils) {
            Alerte a = s.verifierSeuil();
            if (a != null) {
                alertes.add(a);
                if (a.getNiveau().equals("CRITIQUE"))
                    critiques++;
            }
        }
        System.out.println(
            "Alertes : " + alertes.size()
            + " | Critiques : " + critiques);
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : afficherTableauBord()
    //
    // PROMPT UTILISÉ :
    // "Méthode afficherTableauBord(auth, collectes,
    //  paiement) dans Administrateur qui affiche
    //  stats utilisateurs, collectes et paiements."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void afficherTableauBord() {
    //     System.out.println("Dashboard");
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Paramètres réels des autres classes
    // 2. Calcul statistiques dynamiques
    // 3. Affichage alertes actives
    // ─────────────────────────────────────────────
    public void afficherTableauBord(
            Authentification auth,
            List<Collecte> collectes,
            Paiement paiement) {
        System.out.println(
            "\n=== TABLEAU DE BORD ADMIN ===");
        System.out.println("Admin : " + getNom());
        int actifs = 0;
        for (Utilisateur u :
                auth.getUtilisateursInscrits()) {
            if (u.isCompteActif()) actifs++;
        }
        System.out.println("\nUtilisateurs");
        System.out.println(
            "  Inscrits  : "
            + auth.getNombreInscrits());
        System.out.println(
            "  Actifs    : " + actifs);
        System.out.println(
            "  Suspendus : "
            + (auth.getNombreInscrits() - actifs));
        int terminees = 0;
        double poids = 0;
        for (Collecte c : collectes) {
            if (c.getStatut().equals("TERMINEE"))
                terminees++;
            poids += c.getPoidsKg();
        }
        System.out.println("\nCollectes");
        System.out.println(
            "  Total     : " + collectes.size());
        System.out.println(
            "  Terminees : " + terminees);
        System.out.printf(
            "  Poids     : %.1f kg%n", poids);
        System.out.println(
            "\nAlertes : " + alertes.size());
        for (Alerte a : alertes) {
            if (!a.isTraitee())
                a.declencherAlerte();
        }
    }

    public List<Alerte> getAlertes() {
        return alertes;
    }
}
