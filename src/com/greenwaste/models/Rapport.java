import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe Rapport — Génération de rapports statistiques pour l'admin
 * Fonctionnalité : US-07 (Tableau de bord global) — support analytique
 * Membre : Maha
 * Sprint 2 — Logique métier complète
 */
public class Rapport {

    // ─── Types de rapports disponibles ───────────────────────────
    public enum TypeRapport {
        MENSUEL, TRIMESTRIEL, ANNUEL, PAR_REGION, PAR_TYPE_DECHET
    }

    // ─── Attributs ───────────────────────────────────────────────
    private String id;
    private TypeRapport type;
    private String periodeDebut;    // format : dd/MM/yyyy
    private String periodeFin;      // format : dd/MM/yyyy
    private String filtreRegion;
    private String filtreTypeDechet;
    private LocalDateTime dateGeneration;

    private List<Collecte> collectes;
    private List<Alerte> alertes;
    private List<Facture> factures;
    private List<Utilisateur> utilisateurs;

    // ─── Constructeur ────────────────────────────────────────────
    public Rapport(String id, TypeRapport type, String periodeDebut, String periodeFin) {
        this.id = id;
        this.type = type;
        this.periodeDebut = periodeDebut;
        this.periodeFin = periodeFin;
        this.dateGeneration = LocalDateTime.now();
        this.collectes = new ArrayList<>();
        this.alertes = new ArrayList<>();
        this.factures = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
    }

    // ─── Génération du rapport complet ───────────────────────────

    /**
     * Génère et affiche le rapport complet selon le type configuré.
     */
    public void genererRapport() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.printf ("║  RAPPORT %-10s — WasteWaste                    ║%n", type.name());
        System.out.printf ("║  ID : %-47s║%n", id);
        System.out.printf ("║  Période : %s → %s                    ║%n", periodeDebut, periodeFin);
        System.out.printf ("║  Généré le : %-39s║%n",
                dateGeneration.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // Sections communes
        afficherStatistiquesCollectes();
        afficherStatistiquesAlertes();
        afficherStatistiquesFacturation();
        afficherStatistiquesUtilisateurs();

        // Section spécifique au type
        switch (type) {
            case PAR_REGION        -> afficherRepartitionParRegion();
            case PAR_TYPE_DECHET   -> afficherRepartitionParTypeDechet();
            case MENSUEL, TRIMESTRIEL, ANNUEL -> afficherEvolutionTemporelle();
        }

        System.out.println("\n══════════════════ FIN DU RAPPORT ══════════════════\n");
    }

    // ─── Section 1 : Statistiques collectes ──────────────────────

    private void afficherStatistiquesCollectes() {
        System.out.println("\n📦  COLLECTES");
        System.out.println("─".repeat(50));

        long total       = collectes.size();
        long effectuees  = collectes.stream().filter(c -> c.getStatut().equals("EFFECTUEE")).count();
        long enAttente   = collectes.stream().filter(c -> c.getStatut().equals("EN_ATTENTE")).count();
        long annulees    = collectes.stream().filter(c -> c.getStatut().equals("ANNULEE")).count();
        double volumeTotal = collectes.stream().mapToDouble(Collecte::getVolumeKg).sum();
        double volumeMoyen = total > 0 ? volumeTotal / total : 0;

        System.out.printf("  Total collectes         : %d%n",   total);
        System.out.printf("  ✅ Effectuées           : %d%n",   effectuees);
        System.out.printf("  ⏳ En attente           : %d%n",   enAttente);
        System.out.printf("  ❌ Annulées             : %d%n",   annulees);
        System.out.printf("  Volume total            : %.2f kg%n", volumeTotal);
        System.out.printf("  Volume moyen / collecte : %.2f kg%n", volumeMoyen);

        // Taux d'exécution
        double taux = total > 0 ? (double) effectuees / total * 100 : 0;
        System.out.printf("  Taux d'exécution        : %.1f%%%n", taux);
    }

    // ─── Section 2 : Statistiques alertes ────────────────────────

    private void afficherStatistiquesAlertes() {
        System.out.println("\n⚠️   ALERTES & CONFORMITÉ");
        System.out.println("─".repeat(50));

        long totalAlertes   = alertes.size();
        long alertes80      = alertes.stream().filter(a -> a.getNiveau().equals("80%")).count();
        long alertes100     = alertes.stream().filter(a -> a.getNiveau().equals("100%")).count();
        long alertesActives = alertes.stream().filter(Alerte::isActive).count();

        System.out.printf("  Total alertes générées  : %d%n", totalAlertes);
        System.out.printf("  Alertes à 80%%          : %d%n", alertes80);
        System.out.printf("  Alertes à 100%% (crit.) : %d%n", alertes100);
        System.out.printf("  Alertes encore actives  : %d%n", alertesActives);

        // Usines les plus en infraction
        Map<String, Long> parUsine = new HashMap<>();
        for (Alerte a : alertes) {
            parUsine.merge(a.getNomUsine(), 1L, Long::sum);
        }
        if (!parUsine.isEmpty()) {
            System.out.println("  Top usines en alerte :");
            parUsine.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(3)
                    .forEach(e -> System.out.printf("    • %-25s : %d alertes%n", e.getKey(), e.getValue()));
        }
    }

    // ─── Section 3 : Statistiques facturation ────────────────────

    private void afficherStatistiquesFacturation() {
        System.out.println("\n💰  FACTURATION (en DT)");
        System.out.println("─".repeat(50));

        long   nbFactures    = factures.size();
        double totalTTC      = factures.stream().mapToDouble(Facture::getMontantTTC).sum();
        double totalPayees   = factures.stream()
                .filter(f -> f.getStatut().equals("PAYEE"))
                .mapToDouble(Facture::getMontantTTC).sum();
        double totalEnAttente = factures.stream()
                .filter(f -> f.getStatut().equals("EN_ATTENTE"))
                .mapToDouble(Facture::getMontantTTC).sum();
        double moyenneParFacture = nbFactures > 0 ? totalTTC / nbFactures : 0;

        System.out.printf("  Nombre de factures      : %d%n",      nbFactures);
        System.out.printf("  Total TTC facturé       : %.3f DT%n", totalTTC);
        System.out.printf("  Total recouvré (payé)   : %.3f DT%n", totalPayees);
        System.out.printf("  En attente paiement     : %.3f DT%n", totalEnAttente);
        System.out.printf("  Montant moyen / facture : %.3f DT%n", moyenneParFacture);

        double tauxRecouvrement = totalTTC > 0 ? totalPayees / totalTTC * 100 : 0;
        System.out.printf("  Taux de recouvrement    : %.1f%%%n", tauxRecouvrement);
    }

    // ─── Section 4 : Statistiques utilisateurs ───────────────────

    private void afficherStatistiquesUtilisateurs() {
        System.out.println("\n👥  UTILISATEURS");
        System.out.println("─".repeat(50));

        Map<String, Long> parRole = new LinkedHashMap<>();
        for (Utilisateur u : utilisateurs) {
            parRole.merge(u.getRole(), 1L, Long::sum);
        }

        long actifs   = utilisateurs.stream().filter(u -> u.getStatut().equals("ACTIF")).count();
        long suspendus = utilisateurs.stream().filter(u -> u.getStatut().equals("SUSPENDU")).count();

        System.out.printf("  Total utilisateurs      : %d%n", utilisateurs.size());
        System.out.printf("  ✅ Comptes actifs       : %d%n", actifs);
        System.out.printf("  🔒 Comptes suspendus    : %d%n", suspendus);
        System.out.println("  Répartition par rôle :");
        parRole.forEach((role, count) ->
                System.out.printf("    • %-20s : %d%n", role, count));
    }

    // ─── Section 5 : Répartition par région ──────────────────────

    private void afficherRepartitionParRegion() {
        System.out.println("\n🗺️   RÉPARTITION PAR RÉGION");
        System.out.println("─".repeat(50));

        Map<String, Long> parRegion = new LinkedHashMap<>();
        Map<String, Double> volumeParRegion = new LinkedHashMap<>();

        for (Collecte c : collectes) {
            parRegion.merge(c.getRegion(), 1L, Long::sum);
            volumeParRegion.merge(c.getRegion(), c.getVolumeKg(), Double::sum);
        }

        parRegion.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> {
                    double vol = volumeParRegion.getOrDefault(e.getKey(), 0.0);
                    System.out.printf("  %-20s : %3d collectes | %.2f kg%n",
                            e.getKey(), e.getValue(), vol);
                });
    }

    // ─── Section 6 : Répartition par type de déchet ──────────────

    private void afficherRepartitionParTypeDechet() {
        System.out.println("\n♻️   RÉPARTITION PAR TYPE DE DÉCHET");
        System.out.println("─".repeat(50));

        Map<String, Long> parType = new LinkedHashMap<>();
        Map<String, Double> volumeParType = new LinkedHashMap<>();

        for (Collecte c : collectes) {
            parType.merge(c.getTypeDechet(), 1L, Long::sum);
            volumeParType.merge(c.getTypeDechet(), c.getVolumeKg(), Double::sum);
        }

        parType.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> {
                    double vol = volumeParType.getOrDefault(e.getKey(), 0.0);
                    System.out.printf("  %-25s : %3d collectes | %.2f kg%n",
                            e.getKey(), e.getValue(), vol);
                });
    }

    // ─── Section 7 : Évolution temporelle ────────────────────────

    private void afficherEvolutionTemporelle() {
        System.out.println("\n📈  ÉVOLUTION TEMPORELLE");
        System.out.println("─".repeat(50));

        Map<String, Long> parMois = new LinkedHashMap<>();
        String[] mois = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        String[] nomsMois = {"Janvier","Février","Mars","Avril","Mai","Juin",
                             "Juillet","Août","Septembre","Octobre","Novembre","Décembre"};

        for (int i = 0; i < mois.length; i++) parMois.put(nomsMois[i], 0L);

        for (Collecte c : collectes) {
            try {
                int idx = Integer.parseInt(c.getDateCollecte().substring(3, 5)) - 1;
                parMois.merge(nomsMois[idx], 1L, Long::sum);
            } catch (Exception ignored) {}
        }

        parMois.forEach((m, count) -> {
            String barre = "█".repeat((int) Math.min(count, 40));
            System.out.printf("  %-12s | %-40s %d%n", m, barre, count);
        });
    }

    // ─── Getters / Setters ────────────────────────────────────────
    public String getId()             { return id; }
    public TypeRapport getType()      { return type; }
    public String getPeriodeDebut()   { return periodeDebut; }
    public String getPeriodeFin()     { return periodeFin; }

    public void setCollectes(List<Collecte> collectes)           { this.collectes = collectes; }
    public void setAlertes(List<Alerte> alertes)                 { this.alertes = alertes; }
    public void setFactures(List<Facture> factures)              { this.factures = factures; }
    public void setUtilisateurs(List<Utilisateur> utilisateurs)  { this.utilisateurs = utilisateurs; }
    public void setFiltreRegion(String r)                        { this.filtreRegion = r; }
    public void setFiltreTypeDechet(String t)                    { this.filtreTypeDechet = t; }
}

  
       
              