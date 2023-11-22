package com.example.tp1.model;


public class Patient {
    public int age;
    public String vm;
    private String res;
    private boolean jeuner;

    public Patient(int age, String vm, boolean jeuner) {
        this.age = age;
        this.vm = vm;
        this.jeuner = jeuner;
        calcule();
    }
    public void calcule(){

        double valMesure =Double.parseDouble(vm);

            if (jeuner) {
                if (age >= 13) {
                    if (valMesure >= 5.0 && valMesure <= 7.2) {
                        res=("Le niveau de glycémie est normal 1");
                    } else {
                        res=("Le niveau de glycémie est trop bas ou trop élevé 1");
                    }
                } else if (age >= 6) {
                    if (valMesure >= 5.0 && valMesure <= 10.0) {
                        res=("Le niveau de glycémie est normal 2");
                    } else if (valMesure >= 5.5 && valMesure <= 10.0) {
                        res=("Le niveau de glycémie est normal 3");
                    } else {
                        res=("Le niveau de glycémie est trop bas ou trop élevé 1");
                    }
                } else {
                    res=("L'âge n'est pas valide");
                }
            } else {
                if (age >= 13) {
                    if (valMesure < 10.5) {
                        res= ("Le niveau de glycémie est normal");
                    } else {
                        res=("Le niveau de glycémie est trop bas ou trop élevé 2");
                    }
                } else {
                    res =("L'âge n'est pas valide");
                }
            }


        }

    public String getRes() {
        return res;
    }
}

