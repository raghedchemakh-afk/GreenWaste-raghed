public class Collecteur {
    private String nom;

    public Collecteur(String nom) {
        this.nom = nom;
    }

    /*
     * PROMPT :
     * Générer une méthode Java pour confirmer une collecte en mettant à jour
     * le statut et en affichant un message avec le nom du collecteur
     *
     * CODE INITIAL :
     * System.out.println(nom + " a confirmé la collecte");
     *
     * AMÉLIORATION :
     * - ajout paramètre Collecte
     * - interaction entre classes
     * - vérification statut
     * - mise à jour en "Confirmée"
     */
    public void confirmerCollecte(Collecte collecte) {
        if (collecte == null) {
            System.out.println("Erreur : aucune collecte à confirmer");
            return;
        }

        String statutActuel = collecte.getStatut();

        if (statutActuel != null && statutActuel.equalsIgnoreCase("Annulée")) {
            System.out.println("Impossible de confirmer une collecte annulée");
            return;
        }

        if (statutActuel != null && statutActuel.equalsIgnoreCase("Confirmée")) {
            System.out.println("Collecte déjà confirmée");
            return;
        }

        collecte.setStatut("Confirmée");
        System.out.println(nom + " a confirmé la collecte du " + collecte.getDate());
    }
}