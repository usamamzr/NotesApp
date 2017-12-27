package com.example.usama.noteitproject.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.usama.noteitproject.Events.NoteDetailEvent;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

public class NotesDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail);

        Gson gson = new Gson();
        String target = getIntent().getStringExtra("Detail");
        NoteDetailEvent noteDetailEvent = new NoteDetailEvent(target);

        EventBus.getDefault().post(noteDetailEvent);
    }
}