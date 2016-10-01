package com.pgbattleguide.jose.pgbattleguide.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgbattleguide.jose.pgbattleguide.DisplayPokemon;
import com.pgbattleguide.jose.pgbattleguide.MainActivity;
import com.pgbattleguide.jose.pgbattleguide.R;

import java.util.List;

/**
 * Created by Jose on 01/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<PokemonData> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<PokemonData> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        //View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bubble_template, null);
        LayoutInflater vi = (LayoutInflater) MainActivity.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = vi.inflate(R.layout.bubble_template, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {
        holder.bubbleImage.getLayoutParams().height = MainActivity.getWidth();
        holder.bubbleImage.getLayoutParams().width = MainActivity.getWidth();
        holder.bubbleImage.setImageResource(itemList.get(position).getPhotoID());
        holder.bubbleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "No: " + pokePNG, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.getContext(), DisplayPokemon.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(MainActivity.POKE_URI, itemList.get(position).getPhotoID());
                intent.putExtra(MainActivity.POKE_NO, itemList.get(position).getNumber());
                MainActivity.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}