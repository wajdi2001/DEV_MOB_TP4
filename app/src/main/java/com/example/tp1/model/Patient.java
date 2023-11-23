package com.example.tp1.model;

// Définition d'une classe Patient
public class Patient {
    // Déclaration des variables membres de la classe
    public int age; // L'âge du patient
    public String vm; // La valeur de mesure de la glycémie
    private String res; // Le résultat de l'analyse
    private boolean jeuner; // Un indicateur si le patient est à jeun ou non
    // Constructeur de la classe Patient prenant l'âge, la valeur de mesure et un indicateur de jeûne en paramètres
    public Patient(int age, String vm, boolean jeuner) {
        this.age = age; // Initialisation de l'âge du patient
        this.vm = vm; // Initialisation de la valeur de mesure de la glycémie
        this.jeuner = jeuner; // Initialisation de l'indicateur de jeûne
        calcule(); // Appel à la méthode calcule() pour effectuer l'analyse dès la création d'un objet Patient
    }

    // Méthode pour effectuer le calcul de la glycémie en fonction de l'âge, de la valeur de mesure et du statut de jeûne
    public void calcule() {
        // Conversion de la valeur de mesure (vm) de type String en double
        double valMesure = Double.parseDouble(vm);

        // Vérification des conditions pour déterminer le niveau de glycémie en fonction de l'âge et du statut de jeûne
        if (jeuner) { // Si le patient est à jeun
            if (age >= 13) {
                // Conditions pour les patients âgés de 13 ans ou plus
                if (valMesure >= 5.0 && valMesure <= 7.2) {
                    res = ("Le niveau de glycémie est normal 1");
                } else {
                    res = ("Le niveau de glycémie est trop bas ou trop élevé 1");
                }
            } else if (age >= 6) {
                // Conditions pour les patients âgés de 6 ans ou plus mais moins de 13 ans
                if (valMesure >= 5.0 && valMesure <= 10.0) {
                    res = ("Le niveau de glycémie est normal 2");
                } else if (valMesure >= 5.5 && valMesure <= 10.0) {
                    res = ("Le niveau de glycémie est normal 3");
                } else {
                    res = ("Le niveau de glycémie est trop bas ou trop élevé 1");
                }
            } else {
                res = ("L'âge n'est pas valide");
            }
        } else { // Si le patient n'est pas à jeun
            if (age >= 13) {
                // Conditions pour les patients âgés de 13 ans ou plus
                if (valMesure < 10.5) {
                    res = ("Le niveau de glycémie est normal");
                } else {
                    res = ("Le niveau de glycémie est trop bas ou trop élevé 2");
                }
            } else {
                res = ("L'âge n'est pas valide");
            }
        }
    }

    // Méthode pour récupérer le résultat de l'analyse de la glycémie
    public String getRes() {
        return res; // Retourne le résultat de l'analyse
    }
}
