package com.liferay.util.transport;

import java.net.DatagramPacket;

/**
 * @author Michael C. Han
 * @version $Revision$
 */
public interface DatagramHandler {
    public void process(DatagramPacket packet);
    public void errorReceived(Throwable t);
}
