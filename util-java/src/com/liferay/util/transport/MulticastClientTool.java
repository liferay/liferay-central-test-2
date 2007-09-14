package com.liferay.util.transport;

import java.net.DatagramPacket;

/**
 * A client that listens for multicast messages at a designated port.  You may
 * use this to for potential multicast issues when tuning distributed caches and
 * etc.
 *
 * @author Michael C. Han
 * @version $Revision$
 */
public class MulticastClientTool {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[1]);

            MulticastTransport transport =
                new MulticastTransport(new DatagramHandler() {
                    public void process(DatagramPacket packet) {
                        String s =
                            new String(packet.getData(), 0, packet.getLength());
                        System.out.println(s);
                    }

                    public void errorReceived(Throwable t) {
                        t.printStackTrace();
                    }
                }, args[0], port);
            transport.connect();
            synchronized (transport) {
                transport.wait();
            }
        }// end try
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
            System.err.println
                ("Usage: java MulticastClientTool multicastAddress port");
            System.exit(1);
        }
    }    
}
