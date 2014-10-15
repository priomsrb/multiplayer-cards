package com.mygdx.drops;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.drops.Messages.*;

import java.io.IOException;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class GameClient {
    Client client;
    GameScreen screen;
    int playerId;

    public GameClient(final GameScreen screen) throws IOException {
        this.screen = screen;
        client = new Client();
        client.start();
        client.connect(5000, ClientServerHelper.host, ClientServerHelper.TCP_PORT, ClientServerHelper.UDP_PORT);
        ClientServerHelper.registerClasses(client);
        sendMessage(new PlayerIdRequest());

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof PlayerIdRequest) {
                    PlayerIdRequest request = (PlayerIdRequest)object;
                    playerId = request.playerId;
                    System.out.println("My Player ID is: " + playerId);
                } else if (object instanceof CardMoved) {
                    CardMoved message = (CardMoved)object;
                    Card card = GameClient.this.screen.cards.get(message.cardId);
                    card.receivedMessage(message);
                } else if (object instanceof CardMovedToHand) {
                    CardMovedToHand message = (CardMovedToHand)object;
                    screen.cardController.cardMovedToOpponentHand(message.cardId);
                } else if (object instanceof CardsDealt) {
                    CardsDealt message = (CardsDealt)object;
                    screen.cardController.dealCards(message.cardIds);
                } else if (object instanceof DiscardCards) {
                    screen.cardController.discardCards();
                }
            }
        });
    }

    public void sendMessage(Object message) {
        client.sendTCP(message);
    }

    public void sendUdpMessage(Object message) {
        client.sendUDP(message);
    }
}
