package com.example.tp1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tp1.R;
import com.example.tp1.controller.Controller;

// Classe représentant l'activité principale de l'application Android
public class MainActivity extends AppCompatActivity {
    private EditText vm; // Champ de saisie pour la valeur de mesure
    private SeekBar sbAge; // Barre de progression pour l'âge
    private TextView res; // Champ de texte pour afficher la réponse
    private Button btn; // Bouton pour lancer le calcul
    private RadioGroup rbGrp; // Groupe de boutons radio pour sélectionner le statut de jeûne
    private TextView votreAge; // Champ de texte pour afficher l'âge
    private boolean jeuner = true; // Jeûner (true) ou non jeûner (false)

    // Méthode appelée lors de la création de l'activité
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Définition de la vue à afficher
        init(); // Initialisation des éléments de l'interface utilisateur
    }

    // Méthode pour initialiser les composants de l'interface utilisateur
    private void init() {
        votreAge = findViewById(R.id.votreAge); // Liaison avec le champ de texte "votreAge" dans le layout
        vm = findViewById(R.id.vm); // Liaison avec le champ de saisie "vm" dans le layout
        sbAge = findViewById(R.id.sbAge); // Liaison avec la barre de progression "sbAge" dans le layout

        rbGrp = findViewById(R.id.rbGrp); // Liaison avec le groupe de boutons radio "rbGrp" dans le layout
        btn = findViewById(R.id.buttonConsult); // Liaison avec le bouton "btn" dans le layout

        // Gestionnaire de changement de progression de la barre d'âge
        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Information", "onProgressChanged " + progress);
                votreAge.setText("Votre Age: " + progress); // Affichage de l'âge sélectionné
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Gestionnaire de changement d'état des boutons radio pour le statut de jeûne
        rbGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedValue = selectedRadioButton.getText().toString();
                jeuner = selectedValue.equals("Oui"); // Met à jour le statut de jeûne en fonction du choix
            }
        });

        // Gestionnaire de clic sur le bouton "btn" pour lancer le calcul
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = sbAge.getProgress(); // Récupération de l'âge sélectionné
                String valMesure = vm.getText().toString(); // Récupération de la valeur de mesure saisie
                Controller controller =Controller.getInstance(); // Création d'un nouvel objet Controller

                // Validation des entrées en appelant la méthode createPatient du Controller
                int inputIsValid = controller.createPatient(age, valMesure, jeuner);

                // Gestion des différentes erreurs possibles ou affichage de la réponse calculée
                if (inputIsValid == 1) {
                    Toast.makeText(getApplicationContext(), "L'âge et la valeur de mesure sont invalides", Toast.LENGTH_SHORT).show();
                } else if (inputIsValid == -1) {
                    Toast.makeText(getApplicationContext(), "L'âge est invalide", Toast.LENGTH_SHORT).show();
                } else if (inputIsValid == -2) {
                    Toast.makeText(getApplicationContext(), "La valeur de mesure est invalide", Toast.LENGTH_SHORT).show();
                } else {
                    controller.patient.calcule(); // Calcul du résultat basé sur les données saisies

                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("RES", controller.getReponse());
                    startActivity(intent);

                    vm.setText(""); // Réinitialisation du champ de saisie de la valeur de mesure
                    sbAge.setProgress(0); // Réinitialisation de la barre de progression de l'âge
                }

            }
        });
    }
}
