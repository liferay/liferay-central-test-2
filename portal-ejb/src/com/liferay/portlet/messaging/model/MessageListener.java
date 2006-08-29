package com.liferay.portlet.messaging.model;

import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;

import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messaging.model.MessageWait;

public class MessageListener implements PacketListener {

	private HttpSession _session;

	public MessageListener(HttpSession ses) {
		_session = ses;
	}

	public void processPacket(Packet packet) {
		_notifyAction(packet.getTo());
	}
	
	private void _notifyAction (String to) {
		JabberSession jabberSes = (JabberSession) _session.getAttribute(WebKeys.JABBER_XMPP_SESSION);
		MessageWait msgWait = jabberSes.getMessageWait();
		
		if (msgWait != null) {
			msgWait.notifyWait();
		}
	}

}
