package com.mygdx.drops;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.BitSet;
import java.util.List;

/**
 * Created by Shafqat on 12/08/2014.
 */
public class CardShufflerTest {

    @Test
    public void cardShuffler_always_hasCorrectNumberOfCards() {
        List<CardIdList> cardIds = CardShuffler.getShuffledCardHands(1, 52);

        Assert.assertEquals(cardIds.size(), 1);
        Assert.assertEquals(cardIds.get(0).size(), 52);
    }

    @Test
    public void cardShuffler_always_evenlyDividesCards() {
        List<CardIdList> cardIds = CardShuffler.getShuffledCardHands(4, 52);

        for(int i = 0; i < cardIds.size(); i++) {
            Assert.assertEquals(cardIds.get(i).size(), 52/4);
        }
    }

    @Test
    public void cardShuffler_never_hasDuplicates() {
        List<CardIdList> cardIds = CardShuffler.getShuffledCardHands(4, 52);
        BitSet cardsUsed = new BitSet(52);

        for(int i = 0; i < cardIds.size(); i++) {
            for(int j = 0; j < cardIds.get(i).size(); j++) {
                cardsUsed.set(cardIds.get(i).get(j));
            }
        }

        Assert.assertEquals(cardsUsed.cardinality(), 52);
    }

    @Test
    public void cardShuffler_never_repeats() {
        List<CardIdList> cardIds1 = CardShuffler.getShuffledCardHands(4, 52);
        List<CardIdList> cardIds2 = CardShuffler.getShuffledCardHands(4, 52);

        Boolean shufflesDifferent = false;

        outerloop:
        for(int i = 0; i < cardIds1.size(); i++) {
            for(int j = 0; j < cardIds1.get(i).size(); j++) {
                Integer card1 = cardIds1.get(i).get(j);
                Integer card2 = cardIds2.get(i).get(j);
                if(!card1.equals(card2)) {
                    shufflesDifferent = true;
                    break outerloop;
                }
            }
        }

        Assert.assertTrue(shufflesDifferent);
    }
}
