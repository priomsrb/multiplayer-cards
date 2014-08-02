package com.mygdx.drops;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class CardMoved extends Message {
    public float x, y;

    public CardMoved() {

    }

    public CardMoved(int actorId, float x, float y) {
        this.actorId = actorId;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("CardMoved(%d, %.1f, %.1f)", actorId, x, y);
    }
}
