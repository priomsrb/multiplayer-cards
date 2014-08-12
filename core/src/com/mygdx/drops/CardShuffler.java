package com.mygdx.drops;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shafqat on 7/08/2014.
 */
public class CardShuffler {
    public static List<CardIdList> getShuffledCardHands(int numPlayers, int numCards) {
        Array<Integer> shuffledCardIds = getShuffledCardIds(numCards);
        List<CardIdList> cardHands = new ArrayList<CardIdList>();
        for(int i = 0; i < numPlayers; i++) {
            cardHands.add(new CardIdList());
        }
        for(int i = 0; i < numCards; i++) {
            int clientId = i % numPlayers;
            cardHands.get(clientId).add(shuffledCardIds.get(i));
        }
        return cardHands;
    }

    private static Array<Integer> getShuffledCardIds(int numCards) {
        Array<Integer> cardIds = new Array<Integer>();
        for(int i = 0; i < numCards; i++) {
            cardIds.add(i);
        }
        for(int i = 0; i < cardIds.size; i++) {
            cardIds.swap(i, MathUtils.random(cardIds.size - 1));
        }
        return cardIds;
    }
}
