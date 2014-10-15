package com.mygdx.drops;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.drops.Messages.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class GameServer {
    private final GameScreen screen;
    private Server server;
    private int totalPlayers = 0; // TODO: Not used. Currently using server.getConnections() instead

    public GameServer(final GameScreen screen) throws IOException {
        this.screen = screen;
        server = new Server();
        server.start();
        server.bind(ClientServerHelper.TCP_PORT, ClientServerHelper.UDP_PORT);
        ClientServerHelper.registerClasses(server);

        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof PlayerIdRequest) {
                    totalPlayers += 1;
                    int playerId = totalPlayers;
                    server.sendToTCP(connection.getID(), new PlayerIdRequest(playerId));
                } else if (object instanceof RequestShuffle) {
                    int totalClients = server.getConnections().length;
                    List<CardIdList> cardHands = CardShuffler.getShuffledCardHands(totalClients, screen.cards.size);
                    for(int i = 0; i < totalClients; i++) {
                        CardsDealt cardsDealt = new CardsDealt();
                        cardsDealt.cardIds = cardHands.get(i);
                        server.getConnections()[i].sendTCP(cardsDealt);
                    }
                } else if (object instanceof P2PMessage) {
                    server.sendToAllExceptTCP(connection.getID(), object);
                }
            }
        });
    }
}
