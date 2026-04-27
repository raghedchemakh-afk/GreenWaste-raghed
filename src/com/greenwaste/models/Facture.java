

public class Facture {

    private int    id;
    private String numeroFacture;
    private String dateEmission;
    private double montantDT;
    private String statut;
    private String destinataire;

    public Facture(int id, String destinataire,
                   double montantDT,
                   String dateEmission) {
        this.id            = id;
        this.numeroFacture = "FAC-2026-" + id;
        this.destinataire  = destinataire;
        this.montantDT     = montantDT;
        this.dateEmission  = dateEmission;
        this.statut        = "EN_ATTENTE";
    }

    public int    getId()             { return id; }
    public String getNumeroFacture()  { return numeroFacture; }
    public String getDateEmission()   { return dateEmission; }
    public double getMontantDT()      { return montantDT; }
    public String getStatut()         { return statut; }
    public String getDestinataire()   { return destinataire; }
    public void   setStatut(String s) { this.statut = s; }

    @Override
    public String toString() {
        return "Facture[" + numeroFacture + "] "
            + destinataire
            + " | "
            + String.format("%.3f", montantDT)
            + " DT | " + statut;
    }
}