package com.mygdx.drops;

import java.util.Comparator;

/**
 * Created by Shafqat on 13/08/2014.
 */
public class CardRankComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        if(o1.rank < o2.rank) {
            return -1;
        } else if(o1.rank == o2.rank && o1.suit < o2.suit) {
            return -1;
        } else if(o1.rank == o2.rank && o1.suit == o2.suit) {
            return 0;
        } else {
            return 1;
        }
    }
}
