package com.pgbattleguide.jose.pgbattleguide.model;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.pgbattleguide.jose.pgbattleguide.DisplayPokemon;
import com.pgbattleguide.jose.pgbattleguide.MainActivity;
import com.pgbattleguide.jose.pgbattleguide.R;
import com.pkmmte.view.CircularImageView;

/**
 * Created by Jose on 01/10/2016.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    public CircularImageView bubbleImage;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        bubbleImage = (CircularImageView) itemView.findViewById(R.id.bubble_image);
    }

}
