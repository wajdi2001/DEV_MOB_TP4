package com.example.tp1.view;

import android.util.Log;
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
import com.example.tp1.serviceAPI.NetworkApi;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    Button buttonSignUp;


    Controller controller =Controller.getInstance();
    List<Compte> comptes =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);



        init();
    }

    private void init() {
        loginButton = findViewById(R.id.buttonLogin);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        buttonSignUp =findViewById(R.id.buttonSignUp1);
        getAllComptesFromBackend();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();

                if (controller.verifEmail(userEmail) && controller.verifMotDePasse(userPassword)) {
                   int verif= verifEmailPassBackEnd(userEmail,userPassword);
                    if(verif == 0){
                        Toast.makeText(getApplicationContext(), "login with success", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(loginPage.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (verif == 1) {
                        Toast.makeText(getApplicationContext(), "verifier votre mot de pass", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "verifier votre email", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    // Les informations de connexion ne sont pas correctes
                    Toast.makeText(getApplicationContext(), "Vérifiez votre email ou votre mot de passe", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(loginPage.this, SignUp.class);
                startActivity(intent);
            }
        });
    }



    private void getAllComptesFromBackend() {
        Call<List<Compte>> call = NetworkApi.apiService.getAllComptes();
        call.enqueue(new Callback<List<Compte>>() {
            @Override
            public void onResponse(Call<List<Compte>> call, Response<List<Compte>> response) {
                if (response.isSuccessful()) {
                     comptes = response.body();
                    if (comptes != null) {
                        // Do something with the list of Compte objects
                        for (Compte compte : comptes) {
                            System.out.println("id :"+compte.getId()+"  email: "+compte.getEmail()+"  pass: "+compte.getMotDePasse()+"\n");
                            // Handle the retrieved Compte objects as needed
                        }
                    }
                } else {
                    System.out.println("APIError Failed to get data from backend. Code: " + response.code());                }
            }

            @Override
            public void onFailure(Call<List<Compte>> call, Throwable t) {
                System.out.println("APIError Error fetching data from backend: " + t.getMessage());
            }
        });


    }
    int verifEmailPassBackEnd(String email, String pass) {
        boolean isEmail = false;
        boolean isPass = false;

        for (Compte compte : comptes) {
            if (email.equals(compte.getEmail())) {
                isEmail = true;
                if (pass.equals(compte.getMotDePasse())) {
                    isPass = true;
                }
            }
        }
        if (isEmail && isPass) {
            return 0; // Email et mot de passe corrects
        } else if (isEmail && !isPass) {
            return 1; // Email correct, mais mot de passe incorrect
        }
        return -1; // Email incorrect
    }

}