package com.liferay.util.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * The MulticastTransport will send strings across a specified multicast address.
 * It will also listen for messages and hand them to the appropriate
 * DatagramHandler.
 *
 * @author Michael C. Han
 * @version $Revision$
 */
public class MulticastTransport extends Thread implements Transport {
    public MulticastTransport(DatagramHandler handler,
                              String host, int port) {
        super("MulticastListener-" + host + port);
        setDaemon(true);
        _handler = handler;
        _host = host;
        _port = port;
    }


    public synchronized void connect()
        throws IOException {
        if (_socket == null) {
            _socket = new MulticastSocket(_port);
        }
        else if (_socket.isConnected() && _socket.isBound()) {
            return;
        }
        _address = InetAddress.getByName(_host);
        _socket.joinGroup(_address);
        _connected = true;
        start();
    }

    public synchronized void disconnect() {
        //interrupt all processing...
        if (_address != null) {
            try {
                _socket.leaveGroup(_address);
                _address = null;
            }
            catch (IOException e) {
                if (_log.isErrorEnabled()) {
                    _log.error("Unable to leave group", e);
                }
            }
        }
        _connected = false;
        this.interrupt();
        _socket.close();
    }

    public synchronized void sendMessage(String mesg)
        throws IOException {
        _outboundPacket.setData(mesg.getBytes());
        _outboundPacket.setAddress(_address);
        _outboundPacket.setPort(_port);
        _socket.send(_outboundPacket);
    }

    public boolean isConnected() {
        return _connected;
    }

    @Override
    public void run() {
        try {
            while (_connected) {
                _socket.receive(_inboundPacket);
                _handler.process(_inboundPacket);
            }
        }
        catch (IOException e) {
            if (_log.isErrorEnabled()) {
                _log.error("Unable to process ", e);
            }
            _socket.disconnect();
            _connected = false;
            _handler.errorReceived(e);
        }

    }

    private static final Log _log = LogFactory.getLog(MulticastTransport.class);
    private final byte[] _inboundBuffer = new byte[4096];
    private final DatagramPacket _inboundPacket =
        new DatagramPacket(_inboundBuffer, _inboundBuffer.length);
    private final byte[] _outboundBuffer = new byte[4096];
    private final DatagramPacket _outboundPacket =
        new DatagramPacket(_outboundBuffer, _outboundBuffer.length);

    private final String _host;
    private final DatagramHandler _handler;
    private final int _port;
    private boolean _connected;
    private MulticastSocket _socket;
    private InetAddress _address;

}