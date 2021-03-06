package com.pgbattleguide.jose.pgbattleguide;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.pgbattleguide.jose.pgbattleguide.controller.CSVReader;
import com.pgbattleguide.jose.pgbattleguide.model.CustomComparator;
import com.pgbattleguide.jose.pgbattleguide.model.PokemonData;
import com.pgbattleguide.jose.pgbattleguide.model.RecyclerViewAdapter;
import com.pkmmte.view.CircularImageView;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private static Point mSize;


    public static final String POKE_STAT = "Poke stat";
    public static final String POKE_URI = "Poke uri";
    public static final String POKE_NO = "Poke number";
    public static final String PKMN_DRAWABLE_DIR = "com.pgbattleguide.jose.pgbattleguide:drawable/p";

    protected EditText searchBar;
    protected FloatingActionButton fab;
    protected FloatingActionsMenu fabm;
    protected ImageView mSearchButton;
    protected RelativeLayout mMainContainer;
    private GridLayoutManager lLayout;
    private RecyclerView mRecyclerView;

    protected ProgressBar mProgressBar;

    private String mQueryString;

    private List<String> mPokemonPNGS;
    public static List<PokemonData> mPokemons;
    public List<PokemonData> mFilteredPokemon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = getApplicationContext();

        mSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(mSize);


        //Main display
        searchBar = (EditText) findViewById(R.id.search_bar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSearchButton = (ImageView) findViewById(R.id.search_button);
        mMainContainer = (RelativeLayout) findViewById(R.id.main_container);

        toggleRefresh();

        loadPokemonData();
        //Display pkmns
        getImagesUris();

        //TEMP
        lLayout = new GridLayoutManager(MainActivity.this, 5);
        mRecyclerView = (RecyclerView) findViewById(R.id.bubbleGrid);
        mRecyclerView.setLayoutManager(lLayout);
        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, mPokemons);
        mRecyclerView.setAdapter(rcAdapter);
        //sad
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateDisplay(false);
            }
        });*/


        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Search pokemons
                //Get query
                getQuery();
                getFilteredPokemon();
                RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, mFilteredPokemon);
                mRecyclerView.setAdapter(rcAdapter);
                /*runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateDisplay(true);
                    }
                });*/
            }
        });

        fabm = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Potato", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    private void loadPokemonData() {
        mPokemons = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.movesets);
        CSVReader csv = new CSVReader(inputStream);
        List<String[]> results = csv.read();

        int counter = 0;
        int previousNumber = 1;

        int number = 1;
        String name = "";
        boolean isNewPokemon = false;
        List<Integer> tankiness = new ArrayList<>();
        List<Integer> duel = new ArrayList<>();
        List<Integer> defense = new ArrayList<>();
        List<Integer> offense = new ArrayList<>();
        int id = 0;
        for (String[] row : results) {
            if (counter != 0) {
                //Loop for each pokemon set of data
                int tempNumber = Integer.parseInt(row[5]);

                if (previousNumber != tempNumber) {
                    previousNumber = tempNumber;
                    isNewPokemon = true;
                }

                if (isNewPokemon) {
                    final String pokeURI = MainActivity.PKMN_DRAWABLE_DIR + number;
                    id = getResources().getIdentifier(pokeURI, null, null);
                    mPokemons.add(new PokemonData(number, name, tankiness, duel, defense, offense, id));

                    tankiness = new ArrayList<>();
                    duel = new ArrayList<>();
                    defense = new ArrayList<>();
                    offense = new ArrayList<>();
                    isNewPokemon = false;
                }

                number = Integer.parseInt(row[5]);
                name = row[6];
                tankiness.add(Integer.parseInt(row[7]));
                duel.add(Integer.parseInt(row[8]));
                offense.add(Integer.parseInt(row[9]));
                defense.add(Integer.parseInt(row[10]));
            }
            counter++;
        }
        mPokemons.add(new PokemonData(number, name, tankiness, duel, defense, offense, id));
    }

    private void getFilteredPokemon() {
        mFilteredPokemon = new ArrayList<>();
        for (PokemonData pokemon : mPokemons) {
            if (pokemon.getName().toLowerCase().contains(mQueryString.toLowerCase())) {
                mFilteredPokemon.add(pokemon);

            }
        }
    }


    private void getQuery() {
        mQueryString = searchBar.getText().toString();
    }

    /*private void updateDisplay(boolean isFiltered) {

        int row = 0;
        int col = 0;

        int total = mPokemons.size();
        if(isFiltered)
            total = mFilteredPokemon.size();
        int column = 5;
        int rows = total / column;

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int thirdScreenWidth = (int) (screenWidth * 0.2 - 7);

        GridLayout insertPoint = (GridLayout) findViewById(R.id.bubbleGrid);
        insertPoint.removeAllViews();

        insertPoint.setColumnCount(column);
        insertPoint.setRowCount(rows + 1);

        if ( isFiltered) {
            if (mFilteredPokemon.size() == 0) {
                Toast.makeText(this, "No results were found", Toast.LENGTH_LONG).show();
            }
        }

        for (PokemonData pokemon : mPokemons) {
            final String pokeURI = PKMN_DRAWABLE_DIR + pokemon.getNumber();
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = vi.inflate(R.layout.bubble_template, null);

            final CircularImageView bubbleImage = (CircularImageView) view.findViewById(R.id.bubble_image);

            bubbleImage.getLayoutParams().height = thirdScreenWidth;
            bubbleImage.getLayoutParams().width = thirdScreenWidth;

            bubbleImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, "No: " + pokePNG, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, DisplayPokemon.class);
                    intent.putExtra(POKE_URI, pokeURI);
                    startActivity(intent);
                }
            });

            int id = getResources().getIdentifier(pokeURI, null, null);
            bubbleImage.setImageResource(id);

            //TEST
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.width = thirdScreenWidth;

            params.leftMargin = 4;
            params.rightMargin = 4;
            params.topMargin = 3;
            params.bottomMargin = 3;
            params.setGravity(Gravity.CENTER);
            params.columnSpec = GridLayout.spec(col);
            params.rowSpec = GridLayout.spec(row);
            view.setLayoutParams(params);

            if (isFiltered) {
                if (mFilteredPokemon.size() > 0) {
                    if (mFilteredPokemon.get(0).getNumber() == pokemon.getNumber()) {
                        insertPoint.addView(view);
                        mFilteredPokemon.remove(mFilteredPokemon.remove(0));

                        //rows and columns
                        if (col == 4) {
                            col = 0;
                            row++;
                        } else {
                            col++;
                        }
                    }
                }

            } else {
                insertPoint.addView(view);
                //rows and columns
                if (col == 4) {
                    col = 0;
                    row++;
                } else {
                    col++;
                }
            }


        }
    }*/

    public void sortPokemonBy(CustomComparator.Order sortingBy) {
        CustomComparator comparator = new CustomComparator();
        comparator.setSortingBy(sortingBy);
        Collections.sort(mPokemons, comparator);
    }

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 200, 200, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }


    private void getImagesUris() {
        mPokemonPNGS = new ArrayList<>();
        for (int i = 1; i <= 151; i++) {
            mPokemonPNGS.add(PKMN_DRAWABLE_DIR + i);
        }
    }

    public void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }


    public void filterClick(View view) {
        switch (view.getId()) {
            case R.id.action_a:
                sortPokemonBy(CustomComparator.Order.Tankiness);
                PokemonData.setCurrentSort(CustomComparator.Order.Tankiness);
                break;
            case R.id.action_b:
                sortPokemonBy(CustomComparator.Order.Duel);
                PokemonData.setCurrentSort(CustomComparator.Order.Duel);
                break;
            case R.id.action_c:
                sortPokemonBy(CustomComparator.Order.Defense);
                PokemonData.setCurrentSort(CustomComparator.Order.Defense);
                break;
            case R.id.action_d:
                sortPokemonBy(CustomComparator.Order.Offense);
                PokemonData.setCurrentSort(CustomComparator.Order.Offense);
                break;
            case R.id.action_e:
                sortPokemonBy(CustomComparator.Order.Number);
                PokemonData.setCurrentSort(CustomComparator.Order.Number);
                break;
            default:
                sortPokemonBy(CustomComparator.Order.Number);
                PokemonData.setCurrentSort(CustomComparator.Order.Number);
        }
        fabm.collapse();

        getQuery();
        getFilteredPokemon();
        RecyclerViewAdapter rcAdapter;
        if (mFilteredPokemon.size() > 0)
            rcAdapter = new RecyclerViewAdapter(MainActivity.this, mFilteredPokemon);
        else
            rcAdapter = new RecyclerViewAdapter(MainActivity.this, mPokemons);
        mRecyclerView.setAdapter(rcAdapter);
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateDisplay(true);
            }
        });*/


    }

    public static Context getContext() {
        return mContext;
    }

    public static int getWidth() {


        int screenWidth = mSize.x;
        int thirdScreenWidth = (int) (screenWidth * 0.2 - 7);
        return thirdScreenWidth;
    }

}
