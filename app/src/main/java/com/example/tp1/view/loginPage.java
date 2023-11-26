package com.example.tp1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tp1.R;
import com.example.tp1.controller.Controller;
import com.example.tp1.model.Compte;
import com.example.tp1.serviceAPI.ApiService;


import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class loginPage extends AppCompatActivity {
    Button loginButton;
    EditText email;
    EditText password;
    private static final String BASE_URL = "http://192.168.1.178:8080";
    private ApiService apiService;
    Controller controller =Controller.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        init();
    }

    private void init() {
        loginButton = findViewById(R.id.buttonLogin);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long userEmail = Long.parseLong(email.getText().toString());
                String userPassword = password.getText().toString();

                if (!controller.verifEmail("u") && !controller.verifMotDePasse(userPassword)) {
                    // Vérifiez si les informations de connexion sont correctes

                    Call<Compte> call = apiService.getCompteById(userEmail);

                    call.enqueue(new Callback<Compte>() {
                        @Override
                        public void onResponse(Call<Compte> call, Response<Compte> response) {
                            int statusCode = response.code();
                            System.out.println("Status code: " + statusCode);
                            Compte compte = response.body();
                            System.out.println(compte);
                            if (response.isSuccessful()) {
                                if (compte != null) {
                                    // Si vous obtenez un compte valide, ouvrez MainActivity
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    // Le compte est vide ou nul, cela indique probablement un échec de connexion
                                    Toast.makeText(getApplicationContext(), "Échec de connexion", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // La requête a échoué (statut HTTP différent de 2xx)
                                Toast.makeText(getApplicationContext(), "Échec de la requête: " + statusCode, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Compte> call, Throwable t) {
                            System.out.println("Erreur : " + t.getMessage());
                            Toast.makeText(getApplicationContext(), "Erreur de connexion: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    // Les informations de connexion ne sont pas correctes
                    Toast.makeText(getApplicationContext(), "Vérifiez votre email ou votre mot de passe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}