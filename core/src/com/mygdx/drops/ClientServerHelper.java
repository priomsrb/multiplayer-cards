package com.mygdx.drops;

import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.drops.Messages.*;

/**
 * Created by Shafqat on 30/07/2014.
 */
public class ClientServerHelper {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54556;
    public static String host = "192.168.1.7";

    static void registerClasses(EndPoint endPoint) {
        endPoint.getKryo().register(CardIdList.class);
        endPoint.getKryo().register(Message.class);
        endPoint.getKryo().register(PlayerIdRequest.class);
        endPoint.getKryo().register(RequestShuffle.class);
        endPoint.getKryo().register(CardsDealt.class);
        endPoint.getKryo().register(CardMoved.class);
    }
}
