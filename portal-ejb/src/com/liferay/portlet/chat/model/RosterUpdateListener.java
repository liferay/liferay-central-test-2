package com.liferay.portlet.chat.model;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.jivesoftware.smack.RosterListener;

import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messaging.model.JabberSession;
import com.liferay.portlet.messaging.model.MessageWait;

public class RosterUpdateListener implements RosterListener {

	private HttpSession _session;

	public RosterUpdateListener (HttpSession ses) {
		_session = ses;
	}

	public void entriesUpdated(Collection packet) {
	}
	
	public void entriesAdded(Collection packet) {
	}
	
	public void entriesDeleted(Collection packet) {
	}

	public void presenceChanged(String id) {
		_notifyRoster(id);
	}
	
	private void _notifyRoster(String id) {
		JabberSession jabberSes = (JabberSession) _session.getAttribute(WebKeys.JABBER_XMPP_SESSION);
		MessageWait msgWait = jabberSes.getMessageWait();
		
		if (msgWait != null) {
			msgWait.notifyWait();
			msgWait.setCmd("getRoster");
		}
	}

}
