import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println(
            "=== GreenWaste Sprint 2 ===\n");

        // SCENARIO 1 : Authentification
        System.out.println(
            "SCENARIO 1 : Authentification\n");

        Authentification auth = new Authentification();
        auth.inscrire(1, "Sarra Ben Amor",
            "sarra@greenwaste.tn",
            "pass123", "INDUSTRIE");
        auth.inscrire(2, "Karim Collecteur",
            "karim@collect.tn",
            "secure456", "COLLECTEUR");
        auth.inscrire(3, "Admin GreenWaste",
            "admin@greenwaste.tn",
            "admin789", "ADMIN");
        auth.seConnecter(
            "sarra@greenwaste.tn", "pass123");
        auth.seDeconnecter();
        auth.seConnecter(
            "karim@collect.tn", "mauvais");
        auth.suspendreCompte("karim@collect.tn");

        // SCENARIO 2 : Collectes
        System.out.println(
            "\nSCENARIO 2 : Collectes\n");

        Collecteur col1 = new Collecteur(
            1, "Karim", "karim@collect.tn",
            "Camion TUN-001", "Sfax");
        Collecte c1 = new Collecte(
            1, "Plastique", 500.0,
            "2026-04-25", col1);
        c1.planifier();
        col1.effectuerCollecte(c1);
        col1.terminerCollecte(c1);
        Collecte c2 = new Collecte(
            2, "Organique", 300.0,
            "2026-04-25", col1);
        c2.planifier();
        c2.annuler();
        List<Collecte> collectes = new ArrayList<>();
        collectes.add(c1);
        collectes.add(c2);

        // SCENARIO 3 : Paiements
        System.out.println(
            "\nSCENARIO 3 : Paiements\n");

        Paiement paiement = new Paiement(
            1, "VIREMENT");
        Facture f1 = paiement.genererFacture(
            1, "TunisPlastic SARL",
            750.500, "2026-04-25");
        paiement.effectuerPaiement(
            750.500, "VIREMENT", f1);
        Facture f2 = paiement.genererFacture(
            2, "SfaxChimie SA",
            1200.000, "2026-04-25");
        paiement.effectuerPaiement(
            1200.000, "CHEQUE", f2);
        paiement.afficherHistorique();

        // SCENARIO 4 : Seuils et Alertes
        System.out.println(
            "\nSCENARIO 4 : Seuils et Alertes\n");

        SeuilReglementaire s1 =
            new SeuilReglementaire(
                1, "Plastique", 1000.0, "kg");
        SeuilReglementaire s2 =
            new SeuilReglementaire(
                2, "Chimique", 500.0, "kg");
        s1.ajouterQuantite(950.0);
        s2.ajouterQuantite(600.0);
        List<SeuilReglementaire> seuils =
            new ArrayList<>();
        seuils.add(s1);
        seuils.add(s2);
        Alerte a1 = new Alerte(
            1, "QUOTA",
            "Plastique proche seuil",
            "AVERTISSEMENT", "2026-04-25");
        a1.declencherAlerte();
        a1.traiterAlerte();

        // SCENARIO 5 : Tableau de bord
        System.out.println(
            "\nSCENARIO 5 : Tableau de bord\n");

        Administrateur admin = new Administrateur(
            3, "Admin GreenWaste",
            "admin@greenwaste.tn");
        admin.surveillerSeuils(seuils);
        admin.afficherTableauBord(
            auth, collectes, paiement);

        // SCENARIO 6 : Rapport
        System.out.println(
            "\nSCENARIO 6 : Rapport\n");

        Rapport rapport = new Rapport(
            1, "Rapport Sprint 2",
            "2026-04-25", "MENSUEL");
        rapport.genererRapportCollectes(collectes);
        rapport.genererRapportPaiements(
            paiement.getHistoriqueFactures());

        System.out.println(
            "\n=== GreenWaste Sprint 2 Termine ! ===");
    }
}