
public class Collecteur extends Utilisateur {

    private String  vehicule;
    private String  zone;
    private boolean disponible;

    public Collecteur(int id, String nom,String email,String vehicule,String zone) {
        super(id, nom, email, "COLLECTEUR");
        this.vehicule   = vehicule;
        this.zone       = zone;
        this.disponible = true;
        this.setCompteActif(true);
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : effectuerCollecte()
    //
    // PROMPT UTILISÉ :
    // "Méthode effectuerCollecte(collecte) dans
    //  Collecteur qui vérifie disponibilité,
    //  marque collecte EN_COURS et met
    //  disponible à false."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public boolean effectuerCollecte(Collecte c) {
    //     c.setStatut("EN_COURS");
    //     return true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification disponibilité collecteur
    // 2. Mise à jour disponible à false
    // 3. Message avec nom et zone
    // ─────────────────────────────────────────────
    public boolean effectuerCollecte(Collecte c) {
        if (!disponible) {
            System.out.println(
                "❌ Collecteur non disponible !");
            return false;
        }
        if (c == null) {
            System.out.println(
                "❌ Collecte invalide !");
            return false;
        }
        this.disponible = false;
        c.setStatut("EN_COURS");
        System.out.println(
            "✅ Collecte démarrée par "
            + getNom()
            + " | Zone : " + zone
            + " | Véhicule : " + vehicule);
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : terminerCollecte()
    //
    // PROMPT UTILISÉ :
    // "Méthode terminerCollecte(collecte) dans
    //  Collecteur qui vérifie statut EN_COURS,
    //  met TERMINEE et disponible à true."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void terminerCollecte(Collecte c) {
    //     c.setStatut("TERMINEE");
    //     disponible = true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification statut EN_COURS
    // 2. Retour boolean
    // 3. Message de confirmation
    // ─────────────────────────────────────────────
    public boolean terminerCollecte(Collecte c) {
        if (c == null
                || !c.getStatut().equals("EN_COURS")) {
            System.out.println(
                "❌ Collecte non en cours !");
            return false;
        }
        c.setStatut("TERMINEE");
        this.disponible = true;
        System.out.println(
            "✅ Collecte terminée par "
            + getNom());
        return true;
    }

    public boolean isDisponible() { return disponible; }
    public String  getVehicule()  { return vehicule; }
    public String  getZone()      { return zone; }

    @Override
    public String toString() {
        return "Collecteur[" + getId() + "] "
            + getNom() + " | " + vehicule
            + " | Zone: " + zone
            + " | Dispo: " + disponible;
    }
}