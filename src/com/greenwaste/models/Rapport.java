import java.util.List;

public class Rapport {
    private int    id;
    private String titre;
    private String date;
    private String type;

    public Rapport(int id, String titre,String date, String type) {
        this.id    = id;
        this.titre = titre;
        this.date  = date;
        this.type  = type;
    }


    // MÉTHODE 1 : genererRapportCollectes()
    //
    // PROMPT UTILISÉ :
    // "Méthode genererRapportCollectes(collectes) dans
    //  Rapport qui affiche nombre total, poids total
    //  et nombre par statut."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void genererRapport(List<Collecte> list) {
    //     System.out.println(list.size()+" collectes");
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Calcul poids total
    // 2. Comptage par statut
    // 3. Format professionnel

    public void genererRapportCollectes(
            List<Collecte> collectes) {
        System.out.println("\n=== " + titre + " ===");
        System.out.println("Date : " + date);
        if (collectes == null
                || collectes.isEmpty()) {
            System.out.println("Aucune collecte !");
            return;
        }
        double poidsTotal = 0;
        int terminee = 0, annulee = 0;
        for (Collecte c : collectes) {
            poidsTotal += c.getPoidsKg();
            if (c.getStatut().equals("TERMINEE"))
                terminee++;
            else if (c.getStatut().equals("ANNULEE"))
                annulee++;
        }
        System.out.println(
            "Total    : " + collectes.size());
        System.out.printf(
            "Poids    : %.1f kg%n", poidsTotal);
        System.out.println(
            "Terminees: " + terminee);
        System.out.println(
            "Annulees : " + annulee);
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : genererRapportPaiements()
    //
    // PROMPT UTILISÉ :
    // "Méthode genererRapportPaiements(factures) dans
    //  Rapport qui calcule total payé, en attente
    //  et nombre de factures par statut en DT."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void genererRapport(List<Facture> list) {
    //     for (Facture f : list)
    //         System.out.println(f);
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Calcul total payé et en attente
    // 2. Comptage par statut
    // 3. Format montants 3 décimales DT
    // ─────────────────────────────────────────────
    public void genererRapportPaiements(
            List<Facture> factures) {
        System.out.println("\n=== Rapport Paiements ===");
        if (factures == null
                || factures.isEmpty()) {
            System.out.println("Aucune facture !");
            return;
        }
        double totalPaye = 0;
        int payees = 0;
        for (Facture f : factures) {
            if (f.getStatut().equals("PAYEE")) {
                totalPaye += f.getMontantDT();
                payees++;
            }
        }
        System.out.println(
            "Total : " + factures.size());
        System.out.printf(
            "Paye  : %.3f DT%n", totalPaye);
        System.out.println("Payees: " + payees);
    }
}