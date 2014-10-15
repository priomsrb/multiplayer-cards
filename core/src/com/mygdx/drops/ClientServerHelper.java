package com.mygdx.drops;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.EndPoint;
import com.mygdx.drops.Messages.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
        endPoint.getKryo().register(DiscardCards.class);
        endPoint.getKryo().register(CardsDealt.class);
        endPoint.getKryo().register(CardMoved.class);
        endPoint.getKryo().register(CardMovedToHand.class);
    }

    public static Array<String> getMyIpAddresses() {
        Array<String> myIps = new Array<String>();
        try {
            for(Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements();) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                for (Enumeration inetAddrs = networkInterface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if(!inetAddr.isLoopbackAddress() && inetAddr.getHostAddress().matches("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+")) {
                        myIps.add(inetAddr.getHostAddress());
                    }
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return myIps;
    }
}
