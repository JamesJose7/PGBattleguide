package com.pgbattleguide.jose.pgbattleguide.model;

import java.util.List;

/**
 * Created by Jose on 29/09/2016.
 */
public class PokemonData {
    private int mNumber;
    private String mName;
    private List<Integer> mTankiness;
    private List<Integer> mDuel;
    private List<Integer> mDefense;
    private List<Integer> mOffense;
    private int mMaxTankiness;
    private int mMaxDuel;
    private int mMaxDefense;
    private int mMaxOffense;

    public PokemonData(int number, String name, List<Integer> tankiness, List<Integer> duel,
                       List<Integer> defense, List<Integer> offense) {
        mNumber = number;
        mName = name;
        mTankiness = tankiness;
        mDuel = duel;
        mDefense = defense;
        mOffense = offense;

        getMaxStats();
    }

    public int getNumber() {
        return mNumber;
    }

    public String getName() {
        return mName;
    }

    public List<Integer> getTankiness() {
        return mTankiness;
    }

    public List<Integer> getDuel() {
        return mDuel;
    }

    public List<Integer> getDefense() {
        return mDefense;
    }

    public List<Integer> getOffense() {
        return mOffense;
    }

    public int getMaxTankiness() {
        return mMaxTankiness;
    }

    public int getMaxDuel() {
        return mMaxDuel;
    }

    public int getMaxDefense() {
        return mMaxDefense;
    }

    public int getMaxOffense() {
        return mMaxOffense;
    }

    private void getMaxStats() {
        int max = 0;
        for (int tank : mTankiness)
            if (tank > max)
                max = tank;

        //set max
        mMaxTankiness = max;
        max = 0;

        for (int duel : mDuel)
            if (duel > max)
                max = duel;

        //set max
        mMaxDuel = max;
        max = 0;

        for (int defense : mDefense)
            if (defense > max)
                max = defense;

        //set max
        mMaxDefense = max;
        max = 0;

        for (int offense : mOffense)
            if (offense > max)
                max = offense;

        //set max
        mMaxOffense = max;

    }
}
