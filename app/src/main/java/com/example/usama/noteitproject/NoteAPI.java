package com.example.usama.noteitproject;

import com.example.usama.noteitproject.Models.Note;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Usama on 11/15/2017.
 */

public interface NoteAPI {

    @GET("notes")
    Call<ArrayList<Note>> getNotesList();

    @GET("notes/{id}")
    Call<Note> getNoteById(int id);

    @POST("api/notes")
    @FormUrlEncoded
    Call<Note> saveNote(@Field("title") String head,
                        @Field("body") String desc);

    /*@POST("notes")
    Call<Note> saveNote(@Body Note note);*/

    @DELETE
    Call<Note> deleteNote(@Query("id") int id);
}
