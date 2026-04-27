

public class Collecte {

    private int       id;
    private String    typeDechet;
    private double    poidsKg;
    private String    date;
    private String    statut;
    private Collecteur collecteur;

    public Collecte(int id, String typeDechet,
                    double poidsKg, String date,
                    Collecteur collecteur) {
        this.id         = id;
        this.typeDechet = typeDechet;
        this.poidsKg    = poidsKg;
        this.date       = date;
        this.collecteur = collecteur;
        this.statut     = "EN_ATTENTE";
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : planifier()
    //
    // PROMPT UTILISÉ :
    // "Méthode planifier() dans Collecte qui vérifie
    //  que le poids est positif, que le collecteur
    //  est disponible et met statut à PLANIFIEE."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean planifier() {
    //     statut = "PLANIFIEE";
    //     return true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification poids positif
    // 2. Vérification collecteur disponible
    // 3. Message avec détails collecte
    // ─────────────────────────────────────────────
    public boolean planifier() {
        if (poidsKg <= 0) {
            System.out.println(
                "❌ Poids invalide !");
            return false;
        }
        if (collecteur == null
                || !collecteur.isDisponible()) {
            System.out.println(
                "❌ Collecteur non disponible !");
            return false;
        }
        this.statut = "PLANIFIEE";
        System.out.println(
            "✅ Collecte planifiée : "
            + typeDechet + " | "
            + poidsKg + " kg | " + date);
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : annuler()
    //
    // PROMPT UTILISÉ :
    // "Méthode annuler() dans Collecte qui vérifie
    //  que statut n'est pas TERMINEE et met
    //  statut à ANNULEE."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void annuler() {
    //     statut = "ANNULEE";
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification statut TERMINEE
    // 2. Retour boolean
    // 3. Message explicite
    // ─────────────────────────────────────────────
    public boolean annuler() {
        if (statut.equals("TERMINEE")) {
            System.out.println(
                "❌ Impossible d'annuler !");
            return false;
        }
        this.statut = "ANNULEE";
        System.out.println(
            "⚠️ Collecte annulée : "
            + typeDechet);
        return true;
    }

    public int    getId()         { return id; }
    public String getTypeDechet() { return typeDechet; }
    public double getPoidsKg()    { return poidsKg; }
    public String getDate()       { return date; }
    public String getStatut()     { return statut; }
    public void   setStatut(String s) { this.statut = s; }

    @Override
    public String toString() {
        return "Collecte[" + id + "] "
            + typeDechet + " | "
            + poidsKg + " kg | "
            + date + " | " + statut;
    }
}