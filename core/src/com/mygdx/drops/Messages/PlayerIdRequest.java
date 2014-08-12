package com.mygdx.drops.Messages;

/**
 * The client sends this message with a blank ID. The server replies with the ID set.
 */
public class PlayerIdRequest {
    public int playerId;

    public PlayerIdRequest() {  }

    public PlayerIdRequest(int playerId) {
        this.playerId = playerId;
    }
}
