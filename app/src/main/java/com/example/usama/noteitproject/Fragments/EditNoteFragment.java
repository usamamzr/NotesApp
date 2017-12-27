package com.example.usama.noteitproject.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usama.noteitproject.Activities.NoteFragmentActivity;
import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.NoteAPI;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {

    EditText etHeadEdit;
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
        final View view = inflater.inflate(R.layout.fragment_edit_note, container, false);

        etHeadEdit = view.findViewById(R.id.etHeadEdit);
        etDescEdit = view.findViewById(R.id.etDescEdit);
        btnSaveEdit = view.findViewById(R.id.btnSaveEdit);

        gson = new Gson();
        String target = getActivity().getIntent().getStringExtra("Details");
        final Note note = gson.fromJson(target, Note.class);

        final int id = Integer.parseInt(note.getId());
        final String head = note.getHead();
        String desc = note.getHead();

        etHeadEdit.setText(head);
        etDescEdit.setText(desc);

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.setHead(etHeadEdit.getText().toString().trim());
                note.setDesc(etDescEdit.getText().toString().trim());

                String head = note.getHead();
                String desc = note.getDesc();

                NoteAPI service = NoteAPI.retrofit.create(NoteAPI.class);


                retrofit2.Call<Note> editNote = service.editNote(id, head, desc);

                editNote.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(retrofit2.Call<Note> call, Response<Note> response) {
                        Intent intent = new Intent(getContext(), NoteFragmentActivity.class);
                        startActivity(intent);
                        Toast.makeText(getContext(), "Note Edited", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Note> call, Throwable t) {
                        Snackbar.make(view, "Edit Note Failure", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }
}
