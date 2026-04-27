import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe Administrateur — Tableau de bord admin & gestion des comptes
 * Fonctionnalité : US-07 (Tableau de bord global) + US-08 (Gestion des comptes)
 * Membre : Maha
 * Sprint 2 — Logique métier complète
 */
public class Administrateur extends Utilisateur {

    // ─── Attributs ───────────────────────────────────────────────
    private String region;
    private List<Utilisateur> tousLesUtilisateurs;
    private List<Collecte> toutesLesCollectes;
    private List<Alerte> toutesLesAlertes;
    private List<Facture> toutesLesFactures;

    // ─── Constructeur ────────────────────────────────────────────
    public Administrateur(String id, String nom, String email, String motDePasse, String region) {
        super(id, nom, email, motDePasse, "ADMIN");
        this.region = region;
        this.tousLesUtilisateurs = new ArrayList<>();
        this.toutesLesCollectes = new ArrayList<>();
        this.toutesLesAlertes = new ArrayList<>();
        this.toutesLesFactures = new ArrayList<>();
    }

    // ─── US-07 : Tableau de bord global ──────────────────────────

    /**
     * Affiche le tableau de bord global avec toutes les statistiques clés.
     * Filtrage possible par région et type de déchet.
     */
    public void afficherTableauDeBord(String filtreRegion, String filtreTypeDechet) {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║         TABLEAU DE BORD ADMIN — WasteWaste           ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  Mis à jour le : " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "                   ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");

        // 1. Nombre d'usines inscrites (rôle OPERATEUR ou HSE)
        long nbUsines = tousLesUtilisateurs.stream()
                .filter(u -> u.getRole().equals("OPERATEUR") || u.getRole().equals("HSE"))
                .count();
        System.out.println("\n🏭  Usines inscrites          : " + nbUsines);

        // 2. Collectes du jour
        String aujourd_hui = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        long collectesDuJour = toutesLesCollectes.stream()
                .filter(c -> c.getDateCollecte().startsWith(aujourd_hui))
                .count();
        System.out.println("🚛  Collectes du jour         : " + collectesDuJour);

        // 3. Alertes actives
        long alertesActives = toutesLesAlertes.stream()
                .filter(a -> a.isActive())
                .count();
        System.out.println("⚠️   Alertes actives          : " + alertesActives);

        // 4. Volume total de déchets (kg)
        double volumeTotal = toutesLesCollectes.stream()
                .filter(c -> filtreTypeDechet == null || c.getTypeDechet().equalsIgnoreCase(filtreTypeDechet))
                .filter(c -> filtreRegion == null || c.getRegion().equalsIgnoreCase(filtreRegion))
                .mapToDouble(Collecte::getVolumeKg)
                .sum();
        System.out.printf("♻️   Volume total             : %.2f kg%n", volumeTotal);

        // 5. Chiffre d'affaires total (DT)
        double totalFactures = toutesLesFactures.stream()
                .mapToDouble(Facture::getMontantTTC)
                .sum();
        System.out.printf("💰  Chiffre d'affaires total : %.3f DT%n", totalFactures);

        // 6. Graphique évolution mensuelle (simplifiée en console)
        System.out.println("\n📊  Évolution mensuelle des collectes :");
        afficherGraphiqueMensuel();

        // 7. Filtres appliqués
        if (filtreRegion != null || filtreTypeDechet != null) {
            System.out.println("\n🔍  Filtres appliqués :");
            if (filtreRegion != null)      System.out.println("    - Région       : " + filtreRegion);
            if (filtreTypeDechet != null)  System.out.println("    - Type déchet  : " + filtreTypeDechet);
        }

        System.out.println("\n[Prochain rafraîchissement automatique dans 5 minutes]");
    }

    /**
     * Graphique mensuel simplifié en console (barres ASCII).
     * Compte les collectes par mois sur les 6 derniers mois.
     */
    private void afficherGraphiqueMensuel() {
        Map<String, Long> parMois = new LinkedHashMap<>();
        String[] mois = {"Jan","Fév","Mar","Avr","Mai","Jun",
                         "Jul","Aoû","Sep","Oct","Nov","Déc"};

        for (String m : mois) parMois.put(m, 0L);

        for (Collecte c : toutesLesCollectes) {
            try {
                int moisIndex = Integer.parseInt(c.getDateCollecte().substring(3, 5)) - 1;
                String nomMois = mois[moisIndex];
                parMois.put(nomMois, parMois.get(nomMois) + 1);
            } catch (Exception ignored) {}
        }

        parMois.forEach((m, count) -> {
            String barre = "█".repeat((int) Math.min(count, 30));
            System.out.printf("  %s | %-30s %d%n", m, barre, count);
        });
    }

    // ─── US-08 : Gestion des comptes ─────────────────────────────

    /**
     * Retourne la liste complète des utilisateurs avec rôle et statut.
     */
    public void listerUtilisateurs() {
        System.out.println("\n── Liste des utilisateurs ──────────────────────────────");
        System.out.printf("%-15s %-25s %-15s %-12s%n", "ID", "Nom", "Rôle", "Statut");
        System.out.println("─".repeat(70));

        if (tousLesUtilisateurs.isEmpty()) {
            System.out.println("Aucun utilisateur enregistré.");
            return;
        }

        for (Utilisateur u : tousLesUtilisateurs) {
            System.out.printf("%-15s %-25s %-15s %-12s%n",
                    u.getId(), u.getNom(), u.getRole(), u.getStatut());
        }
        System.out.println("─".repeat(70));
        System.out.println("Total : " + tousLesUtilisateurs.size() + " utilisateurs");
    }

    /**
     * Active un compte utilisateur (statut INACTIF → ACTIF).
     * Envoie un email de notification simulé.
     */
    public boolean activerCompte(String userId) {
        Utilisateur u = trouverUtilisateur(userId);
        if (u == null) {
            System.out.println("❌ Utilisateur introuvable : " + userId);
            return false;
        }
        if (u.getStatut().equals("ACTIF")) {
            System.out.println("ℹ️  Le compte " + u.getNom() + " est déjà actif.");
            return false;
        }
        u.setStatut("ACTIF");
        envoyerEmailNotification(u.getEmail(),
                "Votre compte WasteWaste a été activé",
                "Bonjour " + u.getNom() + ", votre compte est maintenant actif.");
        System.out.println("✅ Compte activé : " + u.getNom() + " (" + u.getEmail() + ")");
        return true;
    }

    /**
     * Suspend un compte utilisateur (statut → SUSPENDU).
     * Envoie un email de notification simulé.
     */
    public boolean suspendreCompte(String userId, String motif) {
        Utilisateur u = trouverUtilisateur(userId);
        if (u == null) {
            System.out.println("❌ Utilisateur introuvable : " + userId);
            return false;
        }
        if (u.getRole().equals("ADMIN")) {
            System.out.println("❌ Impossible de suspendre un administrateur.");
            return false;
        }
        u.setStatut("SUSPENDU");
        envoyerEmailNotification(u.getEmail(),
                "Suspension de votre compte WasteWaste",
                "Bonjour " + u.getNom() + ", votre compte a été suspendu. Motif : " + motif);
        System.out.println("🔒 Compte suspendu : " + u.getNom() + " — Motif : " + motif);
        return true;
    }

    /**
     * Supprime définitivement un compte utilisateur.
     * Envoie un email de notification simulé.
     */
    public boolean supprimerCompte(String userId) {
        Utilisateur u = trouverUtilisateur(userId);
        if (u == null) {
            System.out.println("❌ Utilisateur introuvable : " + userId);
            return false;
        }
        if (u.getRole().equals("ADMIN")) {
            System.out.println("❌ Impossible de supprimer un administrateur.");
            return false;
        }
        tousLesUtilisateurs.remove(u);
        envoyerEmailNotification(u.getEmail(),
                "Suppression de votre compte WasteWaste",
                "Bonjour " + u.getNom() + ", votre compte a été supprimé définitivement.");
        System.out.println("🗑️  Compte supprimé : " + u.getNom());
        return true;
    }

    /**
     * Modifie le rôle d'un utilisateur.
     */
    public boolean modifierRole(String userId, String nouveauRole) {
        List<String> rolesValides = Arrays.asList("OPERATEUR", "HSE", "COLLECTEUR", "INSPECTEUR", "ADMIN");
        if (!rolesValides.contains(nouveauRole)) {
            System.out.println("❌ Rôle invalide : " + nouveauRole);
            return false;
        }
        Utilisateur u = trouverUtilisateur(userId);
        if (u == null) {
            System.out.println("❌ Utilisateur introuvable : " + userId);
            return false;
        }
        String ancienRole = u.getRole();
        u.setRole(nouveauRole);
        envoyerEmailNotification(u.getEmail(),
                "Modification de votre rôle sur WasteWaste",
                "Bonjour " + u.getNom() + ", votre rôle a été modifié : "
                        + ancienRole + " → " + nouveauRole);
        System.out.println("✏️  Rôle modifié : " + u.getNom() + " [" + ancienRole + " → " + nouveauRole + "]");
        return true;
    }

    // ─── Méthodes utilitaires ─────────────────────────────────────

    private Utilisateur trouverUtilisateur(String userId) {
        return tousLesUtilisateurs.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Simulation d'envoi d'email (à remplacer par JavaMail en production).
     */
    private void envoyerEmailNotification(String destinataire, String sujet, String corps) {
        System.out.println("📧 Email envoyé à " + destinataire);
        System.out.println("   Sujet : " + sujet);
        System.out.println("   Corps : " + corps);
    }

    // ─── Setters pour injection des données ──────────────────────

    public void setTousLesUtilisateurs(List<Utilisateur> liste) { this.tousLesUtilisateurs = liste; }
    public void setToutesLesCollectes(List<Collecte> liste)     { this.toutesLesCollectes = liste; }
    public void setToutesLesAlertes(List<Alerte> liste)         { this.toutesLesAlertes = liste; }
    public void setToutesLesFactures(List<Facture> liste)       { this.toutesLesFactures = liste; }
    public String getRegion()                                    { return region; }
}