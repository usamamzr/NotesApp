package com.example.usama.noteitproject.Events;

/**
 * Created by Usama on 11/26/2017.
 */

public class NoteDetailEvent {

    private String message;

    public NoteDetailEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
