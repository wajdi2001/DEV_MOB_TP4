package com.example.tp1.serviceAPI;

import com.example.tp1.model.Compte;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @FormUrlEncoded
    @POST("/mesure/comptes/login")
    Call<ResponseBody> login(@Field("email") String email, @Field("motDePasse") String motDePasse);

    @GET("/mesure/compte/{email}/{motDePasse}")
    Call<Compte> getCompteByEmailAndPassword(
            @Query("email") String email,
            @Query("motDePasse") String motDePasse
    );


    @GET("/mesure/comptes/{id}")
    Call<Compte> getCompteById(@Path("id") Long id);

    @GET("/mesure/comptes")
    Call<List<Compte>> getComptesWithStatus(@Query("actif") boolean actif);

    @PUT("/mesure/comptes/{id}")
    Call<Compte> updateCompte(@Path("id") Long id, @Body Compte compte);

    @DELETE("/mesure/comptes/{id}")
    Call<Void> deleteCompte(@Path("id") Long id);
}