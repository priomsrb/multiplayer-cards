package com.mygdx.drops;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class GameServer {
    final Server server;
    public GameServer() throws IOException {
        server = new Server();
        server.start();
        server.bind(ClientServerHelper.TCP_PORT, ClientServerHelper.UDP_PORT);
        ClientServerHelper.registerClasses(server);

        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof CardMoved) {
                    server.sendToAllExceptTCP(connection.getID(), object);
                }
            }
        });
    }
}
