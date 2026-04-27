public class SeuilReglementaire {

    private String typeDechet;
    private double seuilMax;

    public SeuilReglementaire(String typeDechet, double seuilMax) {
        this.typeDechet = typeDechet;
        this.seuilMax = seuilMax;
    }

    public boolean verifierConformite(double volumeActuel) {
        if (volumeActuel > seuilMax) {
            System.out.println("Non conforme !");
            return false;
        } else {
            System.out.println("Conforme ✔");
            return true;
        }
    }

    public String getTypeDechet() {
        return typeDechet;
    }

    public double getSeuilMax() {
        return seuilMax;
    }
}