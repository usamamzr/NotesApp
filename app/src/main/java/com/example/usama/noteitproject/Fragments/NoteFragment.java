package com.example.usama.noteitproject.Fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.usama.noteitproject.Adapters.RecyclerAdapter;
import com.example.usama.noteitproject.AddNoteActivity;
import com.example.usama.noteitproject.Events.NoteEvent;
import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.NoteAPI;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    Gson gson;
    Button addButton;

    public NoteFragment() {
        // Required empty public constructor
    }

    ArrayList arrayList = new ArrayList();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test(NoteEvent customEvent) {
        Log.i("check", "Hello");
        arrayList = customEvent.getMessage();
        gson = new Gson();
        String str = gson.toJson(customEvent.getMessage());

        Log.i("check", str);

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        adapter = new RecyclerAdapter(this.getContext(), arrayList);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addButton = view.findViewById(R.id.btnAddNote);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this.getContext());
        progressDialog.setMax(100);
        progressDialog.setMessage("Fetching data...");
        progressDialog.setTitle("Please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        NoteAPI service = NoteAPI.retrofit.create(NoteAPI.class);

        Call<ArrayList<Note>> noteList = service.getNotesList();

        noteList.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                progressDialog.dismiss();
                Log.i("response_check", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                ArrayList<Note> NoteDetailList = response.body();
                Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                adapter.setNotesList(NoteDetailList);
                NoteEvent noteEvent = new NoteEvent(NoteDetailList);
                EventBus.getDefault().post(noteEvent);
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_LONG).show();
                Log.i("response_check", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
            }
        });




        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddNoteActivity.class);
                startActivity(intent);
            }
        });

        /*final ArrayList<Note> arrayList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            arrayList.add(new Note("Heading", "Description"));
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);


        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this.getContext(), arrayList);
        //view = inflater.inflate(R.layout.fragment_note, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        Log.d(TAG, "end of oncreate method: ");*/

        return view;

    }

}
