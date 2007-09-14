package com.liferay.util.transport;

import java.io.IOException;

/**
 * @author Michael C. Han
 * @version $Revision$
 */
public interface Transport {
    /**
     *
     * @throws java.io.IOException
     */
    public void connect()
        throws IOException;
    /**
     *
     */
    public void disconnect() throws IOException;

    /**
     * @param message
     */
    public void sendMessage(String message)
        throws IOException;

    /**
     *
     * @return is connected
     */
    public boolean isConnected();

}

