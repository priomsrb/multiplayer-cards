package com.mygdx.drops.Messages;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class CardMoved extends Message {
    public int cardId;
    public float x, y;

    public CardMoved() {

    }

    public CardMoved(int cardId, float x, float y) {
        this.cardId = cardId;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("CardMoved(%d, %.1f, %.1f)", cardId, x, y);
    }
}
