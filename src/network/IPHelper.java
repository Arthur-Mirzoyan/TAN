package network;

import core.exceptions.IPNotFoundException;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * The <code>IPHelper</code> class provides utility methods for retrieving
 * the local machine’s IP address.
 */
public class IPHelper {

    /**
     * Returns the first non-loopback IPv4 address of the machine.
     *
     * @return  the local IPv4 address as a String
     * @throws IPNotFoundException if no valid IP could be found
     */
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
