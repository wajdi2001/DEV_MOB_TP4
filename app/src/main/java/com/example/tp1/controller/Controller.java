package com.example.tp1.controller;


import com.example.tp1.model.Patient;

// Définition de la classe Controller
public class Controller {
    public Patient patient;
    public static Controller instance;
    // Déclaration d'une variable de type Patient

    // Constructeur par défaut de la classe Controller
    private Controller() {
        // Le constructeur ne réalise aucune action spécifique lors de sa création
    }
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    // Méthode pour créer un objet Patient en fonction des paramètres fournis
    public int createPatient(int age, String vm, boolean jeuner) {
        // Vérifie si l'âge est inférieur ou égal à zéro et si la valeur de mesure est une chaîne vide
        if (age <= 0 && vm.isEmpty()) {
            return 1; // Retourne 1 si l'âge est invalide et la valeur de mesure est manquante
        } else if (age <= 0) {
            return -1; // Retourne -1 si l'âge est invalide
        } else if (vm.isEmpty()) {
            return -2; // Retourne -2 si la valeur de mesure est manquante
        }

        // Crée un nouvel objet Patient avec les paramètres fournis
        patient = new Patient(age, vm, jeuner);
        return 0; // Retourne 0 pour indiquer que la création du patient s'est déroulée avec succès
    }

    // Méthode pour obtenir la réponse, c'est-à-dire le résultat de l'analyse de la glycémie du patient
    public String getReponse() {
        // Renvoie le résultat de l'analyse de la glycémie en utilisant la méthode getRes() de l'objet patient
        return patient.getRes();
    }
    public boolean verifEmail(String emailStr) {
        if (emailStr.isEmpty() || emailStr.startsWith("@")) {
            return false;
        }

        boolean verifArrobas = false;
        int nbrPoint = 0;
        int i;

        for (i = 0; i < emailStr.length(); i++) {
            char currentChar = emailStr.charAt(i);

            if (currentChar == '@') {
                if (verifArrobas || i == 0 || i == emailStr.length() - 1) {
                    return false;
                }
                verifArrobas = true;
            } else if (currentChar == '.') {
                if (!verifArrobas || nbrPoint > 0 || i <= emailStr.lastIndexOf('@') + 4 || i == emailStr.length() - 1) {
                    return false;
                }
                nbrPoint++;
            }
        }

        if (!verifArrobas || nbrPoint != 1) {
            return false;
        }

        return true;
    }

    public boolean verifMotDePasse(String motDePasse) {
        // Vérifie si le mot de passe a au moins 8 caractères
        if (motDePasse.length() < 8) {
            return false;
        }

        // Vérifie si le mot de passe contient au moins un chiffre et une lettre majuscule
        boolean contientChiffre = false;
        boolean contientMajuscule = false;

        for (char c : motDePasse.toCharArray()) {
            if (Character.isDigit(c)) {
                contientChiffre = true;
            } else if (Character.isUpperCase(c)) {
                contientMajuscule = true;
            }

            // Si les deux conditions sont satisfaites, on peut arrêter la boucle
            if (contientChiffre && contientMajuscule) {
                break;
            }
        }

        return contientChiffre && contientMajuscule;
    }
}
