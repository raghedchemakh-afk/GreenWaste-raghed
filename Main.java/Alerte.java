public class Alerte {

    private String typeAlerte;
    private double volume;
    private SeuilReglementaire seuil;

    public Alerte(String typeAlerte, double volume, SeuilReglementaire seuil) {
        this.typeAlerte = typeAlerte;
        this.volume = volume;
        this.seuil = seuil;
    }

    // Déclencher une alerte
    public void declencherAlerte() {
        System.out.println("=== ALERTE ===");
        System.out.println("Type déchet : " + typeAlerte);
        System.out.println("Volume actuel : " + volume + " kg");

        if (!seuil.verifierConformite(volume)) {
            System.out.println(" Alerte déclenchée ! Seuil dépassé.");
            if (volume >= seuil.getSeuilMax() * 1.2) {
                System.out.println(" Inspection urgente requise !");
            }
        } else {
            System.out.println("Aucune alerte.");
        }
    }
}