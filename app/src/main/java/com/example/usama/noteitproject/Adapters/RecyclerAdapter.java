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
import android.widget.Toast;

import com.example.usama.noteitproject.Models.Note;
import com.example.usama.noteitproject.NoteAPI;
import com.example.usama.noteitproject.NotesDetailActivity;
import com.example.usama.noteitproject.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.squareup.picasso.Picasso;

/**
 * Created by Usama on 10/28/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Note> listItems;
    Context context;

    public RecyclerAdapter(Context context, ArrayList<Note> arrayList) {
        this.context = context;
        this.listItems = arrayList;
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
        holder.noteID.setText(this.listItems.get((int) getItemId(position)).getId());
        holder.noteHead.setText(this.listItems.get(position).getHead());
        holder.noteDesc.setText(this.listItems.get(position).getDesc());

        /*Picasso.with(context)
                .load("http://icons.iconarchive.com/icons/pelfusion/long-shadow-media/512/Document-icon.png")
                .into(holder.imageView);*/

        final List<Note> listItems = this.listItems;

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.remove(position);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.19:8000/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                NoteAPI service = retrofit.create(NoteAPI.class);
                final Call<Note> delRequest = service.deleteNote(position);

                delRequest.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {
                        // use response.code, response.headers, etc.
                        Log.i("response_check", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                        /*Note NoteDetailList = response.body();
                        Toast.makeText(RecyclerAdapter.this.context,"Success",Toast.LENGTH_LONG).show();
                        this.deleteNote(NoteDetailList);
                        NoteEvent noteEvent = new NoteEvent(NoteDetailList);
                        EventBus.getDefault().post(noteEvent);*/
                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {
                        // handle failure
                        Toast.makeText(RecyclerAdapter.this.context,"Failure",Toast.LENGTH_LONG).show();

                    }
                });

                notifyDataSetChanged();

                Snackbar.make(v, "Note Deleted", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView noteID, noteHead, noteDesc;
        public Button deleteButton;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            noteID = itemView.findViewById(R.id.tvId);
            noteHead = itemView.findViewById(R.id.tvHeading);
            noteDesc = itemView.findViewById(R.id.tvDesc);
            deleteButton = itemView.findViewById(R.id.btnDel);

        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}