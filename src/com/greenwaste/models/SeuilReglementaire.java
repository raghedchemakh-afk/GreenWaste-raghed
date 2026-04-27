public class SeuilReglementaire {
    private int    id;
    private String typeDechet;
    private double seuilMaxKg;
    private double valeurActuelleKg;
    private String unite;

    public SeuilReglementaire(int id,String typeDechet,double seuilMaxKg,String unite) {
        this.id               = id;
        this.typeDechet       = typeDechet;
        this.seuilMaxKg       = seuilMaxKg;
        this.valeurActuelleKg = 0.0;
        this.unite            = unite;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : verifierSeuil()
    //
    // PROMPT UTILISÉ :
    // "Méthode verifierSeuil() dans SeuilReglementaire
    //  qui compare valeurActuelle avec seuilMax et
    //  retourne une Alerte si dépassement ou proche
    //  à 90%."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean verifierSeuil() {
    //     return valeurActuelleKg > seuilMaxKg;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Alerte à 90% du seuil
    // 2. Alerte CRITIQUE si dépassement
    // 3. Retour Alerte au lieu de boolean
    // ─────────────────────────────────────────────
    public Alerte verifierSeuil() {
        double pct =
            (valeurActuelleKg / seuilMaxKg) * 100;
        if (valeurActuelleKg >= seuilMaxKg) {
            System.out.printf(
                "DEPASSEMENT : %s | %.1f / %.1f %s%n",
                typeDechet, valeurActuelleKg,
                seuilMaxKg, unite);
            return new Alerte(id,
                "DEPASSEMENT SEUIL",
                typeDechet + " : "
                + valeurActuelleKg
                + " / max " + seuilMaxKg,
                "CRITIQUE", "2026-04-25");
        }
        if (pct >= 90) {
            System.out.printf(
                "PROCHE SEUIL : %s | %.1f%% utilise%n",
                typeDechet, pct);
            return new Alerte(id,
                "PROCHE SEUIL",
                typeDechet + " a "
                + String.format("%.1f", pct)
                + "%",
                "AVERTISSEMENT", "2026-04-25");
        }
        System.out.printf(
            "Seuil OK : %s | %.1f%% utilise%n",
            typeDechet, pct);
        return null;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : ajouterQuantite()
    //
    // PROMPT UTILISÉ :
    // "Méthode ajouterQuantite(quantite) dans
    //  SeuilReglementaire qui ajoute la quantité
    //  à valeurActuelle et appelle verifierSeuil()."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void ajouterQuantite(double q) {
    //     valeurActuelleKg += q;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Validation quantité positive
    // 2. Appel automatique verifierSeuil()
    // 3. Message avec total actuel
    // ─────────────────────────────────────────────
    public boolean ajouterQuantite(double quantite) {
        if (quantite <= 0) {
            System.out.println("Quantite invalide !");
            return false;
        }
        this.valeurActuelleKg += quantite;
        System.out.printf(
            "%s : %.1f / %.1f %s%n",
            typeDechet, valeurActuelleKg,
            seuilMaxKg, unite);
        verifierSeuil();
        return true;
    }

    public double getSeuilMaxKg()       { return seuilMaxKg; }
    public double getValeurActuelleKg() { return valeurActuelleKg; }
    public String getTypeDechet()       { return typeDechet; }
}