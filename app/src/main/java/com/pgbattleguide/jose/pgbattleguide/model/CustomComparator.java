package com.pgbattleguide.jose.pgbattleguide.model;

import java.util.Comparator;
/**
 * Created by Jose on 01/10/2016.
 */
public class CustomComparator implements Comparator<PokemonData> {
    public enum Order {Number, Tankiness, Duel, Offense, Defense}

    private Order sortingBy = Order.Number;

    @Override
    public int compare(PokemonData o1, PokemonData o2) {
        switch (sortingBy) {
            case Number: return compare(o2.getNumber(), o1.getNumber());
            case Tankiness: return compare(o1.getMaxTankiness(), o2.getMaxTankiness());
            case Duel: return compare(o1.getMaxDuel(), o2.getMaxDuel());
            case Offense: return compare(o1.getMaxOffense(), o2.getMaxOffense());
            case Defense: return compare(o1.getMaxDefense(), o2.getMaxDefense());
        }
        throw new RuntimeException("Shit, things went wrong");
    }

    public void setSortingBy(Order sortBy) {
        this.sortingBy = sortBy;
    }

    public static int compare(int x, int y) {
        return x > y ? -1
                : x < y ? 1
                : 0;
    }
}
