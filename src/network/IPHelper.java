package network;

import core.exceptions.IPNotFoundException;

import java.net.*;
import java.util.*;

public class IPHelper {
    public static String getLocalIP() throws IPNotFoundException {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) continue;

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet4Address)
                        return address.getHostAddress();
                }
            }
        } catch (SocketException e) {
            throw new IPNotFoundException();
        }

        return null;
    }
}
