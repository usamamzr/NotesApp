package com.example.usama.noteitproject.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.NoteAPI;
import com.example.usama.noteitproject.NoteFragmentActivity;
import com.example.usama.noteitproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNoteFragment extends Fragment {

    Button btnSaveAdd;

    public AddNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        final EditText etHead = view.findViewById(R.id.etHeadAdd);
        final EditText etDesc = view.findViewById(R.id.etDescAdd);
        btnSaveAdd = view.findViewById(R.id.btnSaveAdd);

        btnSaveAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String head = etHead.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();

                NoteAPI service = NoteAPI.retrofit.create(NoteAPI.class);

                Call<Note> noteList = service.saveNote(head, desc);

                noteList.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {
//                        Snackbar.make(v, "Note Added", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(getContext(), "Note Added", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), NoteFragmentActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
//                        Snackbar.make(v, "Failed to add note", Snackbar.LENGTH_LONG).setAction("Retry", null).show();
                        Toast.makeText(getContext(), "Failed to add note", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }

}
