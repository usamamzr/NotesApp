package com.example.usama.noteitproject.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usama.noteitproject.Events.NoteDetailEvent;
import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {

    TextView tvHeadEdit;
    EditText etDescEdit;
    Button btnSaveEdit;
    Gson gson;

    public EditNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);

        gson = new Gson();
        EventBus.getDefault().register(this);

        tvHeadEdit = view.findViewById(R.id.tvHeadEdit);
        etDescEdit = view.findViewById(R.id.etDescEdit);
        btnSaveEdit = view.findViewById(R.id.btnSaveEdit);

        gson = new Gson();
        String target = getActivity().getIntent().getStringExtra("Details");
        //final Note note = gson.fromJson(target, Note.class);

//        String note = getActivity().getIntent().getExtras().getString("Details");

        /*String head = note.getHead().toString();
        String desc = note.getHead().toString();

        tvHeadEdit.setText(head);
        etDescEdit.setText(desc);*/

        return view;
    }

    @Subscribe
    public void onEvent (NoteDetailEvent noteDetailEvent){
        String string = noteDetailEvent.getMessage();
        Note note = gson.fromJson(string, Note.class);

        String head = note.getHead().toString();
        String desc = note.getDesc().toString();

        tvHeadEdit.setText(head);
        etDescEdit.setText(desc);
    }
}
