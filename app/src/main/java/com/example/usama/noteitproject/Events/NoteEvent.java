package com.example.usama.noteitproject.Events;

import com.example.usama.noteitproject.Models.Note;

import java.util.ArrayList;

/**
 * Created by Usama on 11/26/2017.
 */

public class NoteEvent {

    private ArrayList<Note> message;

    public NoteEvent(ArrayList<Note> message) {
        this.message = message;
    }

    public ArrayList<Note> getMessage() {
        return message;
    }
}
