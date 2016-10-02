package com.pgbattleguide.jose.pgbattleguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pgbattleguide.jose.pgbattleguide.model.CustomComparator;
import com.pgbattleguide.jose.pgbattleguide.model.PokemonData;

public class DisplayPokemon extends AppCompatActivity {

    protected ImageView pokePicView;
    protected TextView pokeNameView;
    protected ScrollView tankinessLayout;
    protected ScrollView duelLayout;
    protected ScrollView defenseLayout;
    protected ScrollView offenseLayout;
    protected ScrollView numberLayout;
    //Tankiness Views
    protected TextView tankNumber;
    //Duel Views

    //Defense Views

    //Offense Views

    //Number Views


    private int currentPokeNo;
    private PokemonData currentPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pokemon);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Views
        pokePicView = (ImageView) findViewById(R.id.poke_pic);
        pokeNameView = (TextView) findViewById(R.id.poke_name);
        tankinessLayout = (ScrollView) findViewById(R.id.tankiness_layout);
        offenseLayout = (ScrollView) findViewById(R.id.offense_layout);
        //Tankiness Views
        tankNumber = (TextView) findViewById(R.id.tank_number);
        //Duel Views

        //Defense Views

        //Offense Views

        //Number Views


        //Get last query
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.POKE_URI)) {
            int pokeUri = intent.getIntExtra(MainActivity.POKE_URI, 0);
            int pokeNumber = intent.getIntExtra(MainActivity.POKE_NO, 1);
            //int id = getResources().getIdentifier(pokeUri, null, null);
            pokePicView.setImageResource(pokeUri);
            //currentPokeNo = Integer.parseInt(pokeUri.substring(47));
            currentPokeNo = pokeNumber;
        }

        searchPokemon();

        //set pokemon data
        pokeNameView.setText(currentPokemon.getName());

        //Display appropiate layout
        switch (PokemonData.getCurrentSort()) {
            case Tankiness:
                setTitle("Tankiness");
                tankinessLayout.setVisibility(View.VISIBLE);
                tankNumber.setText(currentPokemon.getMaxTankiness() + "");
                break;
            case Duel:
                setTitle("Duel");
                break;
            case Defense:
                setTitle("Defense");
                break;
            case Offense:
                setTitle("Offense");
                break;
            case Number:
                setTitle("Number");
                break;
            default:
        }

    }

    private void searchPokemon() {
        for (PokemonData pokemon : MainActivity.mPokemons) {
            if (currentPokeNo == pokemon.getNumber()) {
                currentPokemon = pokemon;
            }
        }
    }
}
