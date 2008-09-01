/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

/**
 * <a href="ForwardingMessageListener.java.html"><b><i>View Source</i></b></a>
 * <p/>
 * Message listener that will forward a message received from one destination to
 * another
 *
 * @author Michael C. Han
 */
public class ForwardingMessageListener implements MessageListener {
	public ForwardingMessageListener(SingleDestinationMessageSender sender) {
		_sender = sender;
	}

	public void receive(Object message) {
		_sender.send(message);
	}

	public void receive(String message) {
		_sender.send(message);
	}

	private SingleDestinationMessageSender _sender;
}