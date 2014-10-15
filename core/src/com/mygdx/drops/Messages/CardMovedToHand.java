package com.mygdx.drops.Messages;

/**
 * Created by Shafqat on 14/08/2014.
 */
public class CardMovedToHand extends P2PMessage {
    public int cardId;

    public CardMovedToHand() {
    }

    public CardMovedToHand(int cardId) {
        this.cardId = cardId;
    }
}
