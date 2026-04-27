public class Collecte {
    private String typeDechet;
    private String date;
    private String statut;

    public Collecte(String typeDechet, String date, String statut) {
        this.typeDechet = typeDechet;
        this.date = date;
        this.statut = statut;
    }

    /*
     * PROMPT :
     * Générer une méthode Java pour planifier une collecte avec validation de date
     * et mise à jour du statut
     *
     * CODE INITIAL :
     * System.out.println("Collecte planifiée pour le " + date);
     *
     * AMÉLIORATION :
     * - validation date
     * - gestion statut
     */
    public void planifierCollecte() {
        if (date == null || date.trim().isEmpty()) {
            System.out.println("Erreur : date invalide");
            return;
        }

        if (statut != null && statut.equalsIgnoreCase("Annulée")) {
            System.out.println("Impossible de planifier une collecte annulée");
            return;
        }

        if (statut != null && statut.equalsIgnoreCase("Planifiée")) {
            System.out.println("Collecte déjà planifiée");
            return;
        }

        this.statut = "Planifiée";
        System.out.println("Collecte planifiée avec succès pour le " + date);
    }

    /*
     * AMÉLIORATION :
     * - ajout méthode annulation
     * - empêche annulation après confirmation
     */
    public void annulerCollecte() {
        if (statut != null && statut.equalsIgnoreCase("Confirmée")) {
            System.out.println("Impossible d'annuler une collecte déjà confirmée");
            return;
        }

        this.statut = "Annulée";
        System.out.println("Collecte annulée");
    }

    // Getters / Setters
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDate() {
        return date;
    }
}