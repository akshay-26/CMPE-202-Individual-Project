package com.project.card;

import java.util.HashMap;
import java.util.Map;

public class CardList {
    Map<String, Double> cardlist;

    public CardList() {

        cardlist = new HashMap<>();
    }

    //using primitives in method body for attacker protection - primitives are immutable
    public void addCharge(String card, double amount) {
        //autoboxing double primitive to Double wrapper
        cardlist.put(card, cardlist.getOrDefault(card, 0.0)+amount);

    }

    public Map<String, Double> getCardlist() {

        return cardlist;
    }
}
