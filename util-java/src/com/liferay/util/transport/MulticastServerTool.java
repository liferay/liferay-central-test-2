package com.liferay.util.transport;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * A server that will send out heart beat messages until you kill it.  This
 * enables you to try and debug multicast issues
 *
 *
 * @author Michael C. Han
 * @version $Revision$
 */
public class MulticastServerTool {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[1]);
            long interval = Long.parseLong(args[2]);
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
            String mesg = InetAddress.getLocalHost().getHostName() +
                ":" + port + " heartbeat " ;
            int i = 0;
            while (true) {
                transport.sendMessage(mesg + i);
                i++;
                Thread.sleep(interval);
            }
        }// end try
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
            System.err.println("Usage: java MulticastServerTool " +
                "multicastAddress port interval");
            System.exit(1);
        }
    }
}