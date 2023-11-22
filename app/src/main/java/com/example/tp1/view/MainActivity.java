package com.example.tp1.view;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {
    private EditText vm;
    private SeekBar sbAge;
    private TextView res;
    private Button btn;
    private RadioGroup rbGrp;
    private TextView votreAge;
    private boolean jeuner = true; // Jeûner (true) ou non jeûner (false)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        votreAge = findViewById(R.id.votreAge);
        vm = findViewById(R.id.vm);
        sbAge = findViewById(R.id.sbAge);
        res = findViewById(R.id.res);
        rbGrp = findViewById(R.id.rbGrp);
        btn = findViewById(R.id.btn);

        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Information", "onProgressChanged " + progress);
                votreAge.setText("Votre Age: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this, "Arrêt du suivi tactile", Toast.LENGTH_SHORT).show();
            }
        });

        rbGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedValue = selectedRadioButton.getText().toString();
                jeuner = selectedValue.equals("Oui");
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int age = sbAge.getProgress();
                String valMesure = vm.getText().toString();
                Controller controller = new Controller();
                int inputIsValid = controller.createPatient(age, valMesure, jeuner);

                if (inputIsValid == 1 ) {
                    Toast.makeText(getApplicationContext(), "L'âge et la valeur de mesure sont invalides", Toast.LENGTH_SHORT).show();
                } else if (inputIsValid == -1) {
                    Toast.makeText(getApplicationContext(), "L'âge est invalide", Toast.LENGTH_SHORT).show();

                } else if (inputIsValid ==-2) {
                    Toast.makeText(getApplicationContext(), "la valeur de mesure est invalide", Toast.LENGTH_SHORT).show();

                } else {
                    controller.patient.calcule();
                    res.setText(controller.getReponse());
                    vm.setText("");
                    sbAge.setProgress(0);
                }
            }
        });
    }
}
