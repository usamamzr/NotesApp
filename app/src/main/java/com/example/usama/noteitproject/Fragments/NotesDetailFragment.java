package com.example.usama.noteitproject.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.usama.noteitproject.Activities.EditNoteActivity;
import com.example.usama.noteitproject.Events.NoteDetailEvent;
import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotesDetailFragment extends Fragment {


    TextView tvDescHead, tvDescBody, tvDescId;
    Button btnEdit, btnDel;
    View view;
    Gson gson;
    NotesDetailFragment context = this;

    public NotesDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notes_detail, container, false);

        gson = new Gson();
        EventBus.getDefault().register(this);

        tvDescId = view.findViewById(R.id.tvDescId);
        tvDescHead = view.findViewById(R.id.tvDescHead);
        tvDescBody = view.findViewById(R.id.tvDescBody);
        btnEdit = view.findViewById(R.id.btnEdit);


        gson = new Gson();
        String target = getActivity().getIntent().getStringExtra("Detail");
        final Note note = gson.fromJson(target, Note.class);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String string = gson.toJson(note);
                Intent intent = new Intent(getContext(), EditNoteActivity.class);
                intent.putExtra("Details", string);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Subscribe
    public void onEvent(NoteDetailEvent noteDetailEvent) {
        String string = noteDetailEvent.getMessage();
        Note note = gson.fromJson(string, Note.class);

        String head = note.getHead();
        String body = note.getDesc();
        String id = note.getId();

        tvDescHead.setText(head);
        tvDescBody.setText(body);
        tvDescId.setText(id);
    }

}
