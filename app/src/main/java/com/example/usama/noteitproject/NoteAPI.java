package com.example.usama.noteitproject;

import com.example.usama.noteitproject.Models.Note;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Usama on 11/15/2017.
 */

public interface NoteAPI {

    String baseURL = "http://192.168.43.86:8000/api/";

    @GET("notes")
    Call<ArrayList<Note>> getNotesList();

    @GET("notes/{id}")
    Call<Note> getNoteById(int id);

    @POST("notes")
    @FormUrlEncoded
    Call<Note> saveNote(@Field("title") String head,
                        @Field("body") String desc);

    @DELETE("notes/{id}")
    Call<String> deleteNote(@Path("id") int id);

    @PUT("notes/{id}")
    @FormUrlEncoded
    Call<Note> editNote(@Path("id") int id,
                        @Field("title") String title,
                        @Field("body") String body);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
