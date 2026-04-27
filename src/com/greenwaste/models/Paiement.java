import java.util.ArrayList;
import java.util.List;

public class Paiement {
    private int           id;
    private double        montantDT;
    private String        methodePaiement;
    private String        statut;
    private List<Facture> historiqueFactures;

    public Paiement(int id, String methodePaiement) {
        this.id                 = id;
        this.methodePaiement    = methodePaiement;
        this.statut             = "EN_ATTENTE";
        this.historiqueFactures = new ArrayList<>();
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : genererFacture()
    //
    // PROMPT UTILISÉ :
    // "Méthode genererFacture(id, destinataire,
    //  montant, date) dans Paiement GreenWaste.
    //  Valider entrées, créer Facture, ajouter
    //  à historiqueFactures. Montants en DT."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public Facture genererFacture(int id,
    //     String dest, double montant, String date) {
    //     return new Facture(id, dest, montant, date);
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Validation destinataire non vide
    // 2. Validation montant positif
    // 3. Ajout dans historiqueFactures
    // 4. Message confirmation numéro facture
    // ─────────────────────────────────────────────
    public Facture genererFacture(int id,
                                   String destinataire,
                                   double montant,
                                   String date) {
        if (destinataire == null
                || destinataire.trim().isEmpty()) {
            System.out.println(
                "Destinataire obligatoire !");
            return null;
        }
        if (montant <= 0) {
            System.out.println("Montant invalide !");
            return null;
        }
        Facture f = new Facture(
            id, destinataire, montant, date);
        historiqueFactures.add(f);
        System.out.printf(
            "Facture generee : %s | %.3f DT%n",
            f.getNumeroFacture(), montant);
        return f;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : effectuerPaiement()
    //
    // PROMPT UTILISÉ :
    // "Méthode effectuerPaiement(montant, methode,
    //  facture) dans Paiement GreenWaste. Vérifier
    //  montant positif, méthode parmi VIREMENT/
    //  ESPECES/CHEQUE, montant >= facture. DT."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean effectuerPaiement(double montant,
    //     String methode, Facture f) {
    //     this.statut = "VALIDE";
    //     f.setStatut("PAYEE");
    //     return true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Validation méthode liste autorisée
    // 2. Vérification montant >= montant facture
    // 3. Messages d'erreur explicites
    // 4. Format 3 décimales DT
    // ─────────────────────────────────────────────
    public boolean effectuerPaiement(double montant,
                                      String methode,
                                      Facture facture) {
        if (montant <= 0) {
            System.out.println("Montant invalide !");
            return false;
        }
        if (methode == null
                || (!methode.equals("VIREMENT")
                    && !methode.equals("ESPECES")
                    && !methode.equals("CHEQUE"))) {
            System.out.println(
                "Methode invalide ! "
                + "(VIREMENT / ESPECES / CHEQUE)");
            return false;
        }
        if (facture == null) {
            System.out.println("Facture introuvable !");
            return false;
        }
        if (montant < facture.getMontantDT()) {
            System.out.printf(
                "Montant insuffisant ! "
                + "Requis : %.3f DT%n",
                facture.getMontantDT());
            return false;
        }
        this.montantDT       = montant;
        this.methodePaiement = methode;
        this.statut          = "VALIDE";
        facture.setStatut("PAYEE");
        System.out.printf(
            "Paiement : %.3f DT via %s"
            + " | Facture %s PAYEE%n",
            montant, methode,
            facture.getNumeroFacture());
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 3 : annulerPaiement()
    //
    // PROMPT UTILISÉ :
    // "Méthode annulerPaiement(facture) dans Paiement
    //  qui vérifie statut VALIDE, remet ANNULE et
    //  facture à EN_ATTENTE."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean annulerPaiement() {
    //     this.statut = "ANNULE";
    //     return true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification statut VALIDE
    // 2. Remise statut facture EN_ATTENTE
    // 3. Message numéro facture
    // ─────────────────────────────────────────────
    public boolean annulerPaiement(Facture facture) {
        if (!this.statut.equals("VALIDE")) {
            System.out.println("Impossible d annuler !");
            return false;
        }
        if (facture == null) {
            System.out.println("Facture introuvable !");
            return false;
        }
        this.statut = "ANNULE";
        facture.setStatut("EN_ATTENTE");
        System.out.println(
            "Paiement annule | Facture "
            + facture.getNumeroFacture());
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 4 : afficherHistorique()
    //
    // PROMPT UTILISÉ :
    // "Méthode afficherHistorique() qui parcourt
    //  historiqueFactures, affiche chaque facture
    //  et calcule le total payé en DT."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void afficherHistorique() {
    //     for (Facture f : historiqueFactures)
    //         System.out.println(f);
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification liste vide
    // 2. Calcul total montants payés
    // 3. Format 3 décimales DT
    // ─────────────────────────────────────────────
    public void afficherHistorique() {
        if (historiqueFactures.isEmpty()) {
            System.out.println("Aucune facture !");
            return;
        }
        System.out.println("=== Historique Factures ===");
        double total = 0;
        for (Facture f : historiqueFactures) {
            System.out.println("  " + f);
            if (f.getStatut().equals("PAYEE"))
                total += f.getMontantDT();
        }
        System.out.printf(
            "Total paye : %.3f DT%n", total);
    }

    public String        getStatut()            { return statut; }
    public double        getMontantDT()          { return montantDT; }
    public List<Facture> getHistoriqueFactures() { return historiqueFactures; }
}