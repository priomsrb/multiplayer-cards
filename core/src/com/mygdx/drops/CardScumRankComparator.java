package com.mygdx.drops;

import java.util.Comparator;

/**
 * Created by Shafqat on 13/08/2014.
 */
public class CardScumRankComparator implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        if(isRankHigher(o1.rank, o2.rank)) {
            return 1;
        } else if(o1.rank == o2.rank && o1.suit > o2.suit) {
            return 1;
        } else if(o1.rank == o2.rank && o1.suit == o2.suit) {
            return 0;
        } else {
            return -1;
        }
    }

    private boolean isRankHigher(int rank1, int rank2) {
        if(rank1 <= 2) {
            if(rank2 <= 2) {
                return rank1 > rank2;
            } else {
                return true;
            }
        } else {
            if(rank2 <= 2) {
                return false;
            } else {
                return rank1 > rank2;
            }
        }
    }
}
