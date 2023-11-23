package com.example.tp1.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tp1.R;

public class SecondActivity extends AppCompatActivity {
    TextView textViewResult;
    Button btnRetour;
    void init()
    {
        textViewResult = findViewById(R.id.resultat);
        btnRetour = findViewById(R.id.btnRetour);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        init();
        String resultAnalysis = getIntent().getStringExtra("RES");
        textViewResult.setText("Résultat : " + resultAnalysis);
        Intent intent = new Intent(this, MainActivity.class);
        btnRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

    }

}
