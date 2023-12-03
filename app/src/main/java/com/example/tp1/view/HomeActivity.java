package com.example.tp1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.example.tp1.R;

public class HomeActivity extends AppCompatActivity {
    Button btnHome;
    Handler handler =new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnHome =findViewById(R.id.btnhome);

        Intent intent = new Intent(this, loginPage.class);
        result(intent);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                finish();
            }
        });
    }
    public void result(Intent intent)
    {
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        startActivity(intent);
                        finish();
                    }
                },3000
        );
    }
}