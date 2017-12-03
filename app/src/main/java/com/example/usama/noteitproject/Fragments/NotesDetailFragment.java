package com.example.usama.noteitproject.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.usama.noteitproject.EditNoteActivity;
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


    TextView tvDescHead, tvDescBody;
    Button btnEdit;
    View view;
    Gson gson;
    NotesDetailFragment context = this;

    public NotesDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notes_detail, container, false);

        gson = new Gson();
        EventBus.getDefault().register(this);

        tvDescHead = (TextView) view.findViewById(R.id.tvDescHead);
        tvDescBody = (TextView) view.findViewById(R.id.tvDescBody);
        btnEdit = (Button) view.findViewById(R.id.btnEdit);

        gson = new Gson();
        String target = getActivity().getIntent().getStringExtra("Details");
        final Note note = gson.fromJson(target, Note.class);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String string = gson.toJson(note);
                Intent intent = new Intent(getContext(), EditNoteActivity.class);
                intent.putExtra("Detail", string);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Subscribe
    public void onEvent (NoteDetailEvent noteDetailEvent){
        String string = noteDetailEvent.getMessage();
        Note note = gson.fromJson(string, Note.class);

        String head = note.getHead().toString();
        String body = note.getDesc().toString();

        tvDescHead.setText(head);
        tvDescBody.setText(body);
    }

}
