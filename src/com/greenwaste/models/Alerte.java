

public class Alerte {

    private int     id;
    private String  type;
    private String  message;
    private String  niveau;
    private String  date;
    private boolean traitee;

    public Alerte(int id, String type,
                  String message, String niveau,
                  String date) {
        this.id      = id;
        this.type    = type;
        this.message = message;
        this.niveau  = niveau;
        this.date    = date;
        this.traitee = false;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 1 : declencherAlerte()
    //
    // PROMPT UTILISÉ :
    // "Méthode declencherAlerte() dans Alerte qui
    //  affiche un message selon le niveau parmi
    //  CRITIQUE, AVERTISSEMENT, INFO."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void declencherAlerte() {
    //     System.out.println("Alerte: " + message);
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification alerte non déjà traitée
    // 2. Affichage différent selon niveau
    // 3. Ajout date et type
    // ─────────────────────────────────────────────
    public boolean declencherAlerte() {
        if (traitee) {
            System.out.println(
                " Alerte déjà traitée !");
            return false;
        }
        String prefixe;
        if (niveau.equals("CRITIQUE"))
            prefixe = " CRITIQUE";
        else if (niveau.equals("AVERTISSEMENT"))
            prefixe = " AVERTISSEMENT";
        else
            prefixe = " INFO";

        System.out.println(prefixe + " | "
            + type + " | " + message
            + " | " + date);
        return true;
    }

    // ─────────────────────────────────────────────
    // MÉTHODE 2 : traiterAlerte()
    //
    // PROMPT UTILISÉ :
    // "Méthode traiterAlerte() qui marque l'alerte
    //  comme traitée et affiche confirmation."
    //
    // CODE GÉNÉRÉ PAR L'IA :
    // public void traiterAlerte() {
    //     this.traitee = true;
    // }
    //
    // CORRECTIONS APPORTÉES :
    // 1. Vérification pas déjà traitée
    // 2. Message de confirmation
    // 3. Retour boolean
    // ─────────────────────────────────────────────
    public boolean traiterAlerte() {
        if (traitee) {
            System.out.println(
                " Alerte déjà traitée !");
            return false;
        }
        this.traitee = true;
        System.out.println(
            "Alerte traitée : "
            + type + " | " + message);
        return true;
    }

    public int     getId()      { return id; }
    public String  getType()    { return type; }
    public String  getNiveau()  { return niveau; }
    public String  getMessage() { return message; }
    public boolean isTraitee()  { return traitee; }

    @Override
    public String toString() {
        return "Alerte[" + id + "] "
            + niveau + " | " + type
            + " | " + message
            + " | Traitée: " + traitee;
    }
}