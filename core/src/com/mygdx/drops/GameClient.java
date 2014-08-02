package com.mygdx.drops;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class GameClient {
    Client client;
    GameScreen screen;

    public GameClient(GameScreen screen) throws IOException {
        this.screen = screen;
        client = new Client();
        client.start();
        client.connect(5000, ClientServerHelper.host, ClientServerHelper.TCP_PORT, ClientServerHelper.UDP_PORT);
        ClientServerHelper.registerClasses(client);

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Message) {
                    Message message = (Message)object;
                    Card card = GameClient.this.screen.cards.get(message.actorId);
                    card.receivedMessage(message);
                }
            }
        });
    }

    public void sendMessage(Message message) {
        client.sendTCP(message);
    }

    public void sendUdpMessage(Message message) {
        client.sendUDP(message);
    }
}
