package com.example.usama.noteitproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usama.noteitproject.Activities.NotesDetailActivity;
import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.NoteAPI;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usama on 10/28/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Note> listItems;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<Note> arrayList) {
        this.context = context;
        this.listItems = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteID, noteHead, noteDesc;
        Button deleteButton;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            noteID = itemView.findViewById(R.id.tvId);
            noteHead = itemView.findViewById(R.id.tvHeading);
            noteDesc = itemView.findViewById(R.id.tvDesc);
            deleteButton = itemView.findViewById(R.id.btnDel);

        }
    }

    public void setNotesList(ArrayList<Note> arrayList) {
        this.listItems = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        context = holder.imageView.getContext();
        holder.noteID.setText(this.listItems.get(position).getId());
        holder.noteHead.setText(this.listItems.get(position).getHead());
        holder.noteDesc.setText(this.listItems.get(position).getDesc());

        /*Picasso.with(context)
                .load("http://icons.iconarchive.com/icons/pelfusion/long-shadow-media/512/Document-icon.png")
                .into(holder.imageView);*/

        final List<Note> listItems = this.listItems;

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                int noteId = Integer.parseInt(listItems.get(position).getId());

                NoteAPI service = NoteAPI.retrofit.create(NoteAPI.class);

                Call<String> deleteNote = service.deleteNote(noteId);

                deleteNote.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Snackbar.make(v, "Note Deleted", Snackbar.LENGTH_SHORT).show();
                        listItems.remove(position);
                        notifyDataSetChanged();
                        Log.i("response_check", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Snackbar.make(v, "Failed to Delete Note", Snackbar.LENGTH_SHORT).show();
                        Log.i("response_check", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String string = gson.toJson(listItems.get(position));
                Intent intent = new Intent(context, NotesDetailActivity.class);
                intent.putExtra("Detail", string);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }
}