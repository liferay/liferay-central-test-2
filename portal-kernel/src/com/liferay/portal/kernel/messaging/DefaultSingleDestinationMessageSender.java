/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

/**
 * <a href="DefaultSingleDestinationMessageSender.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultSingleDestinationMessageSender
	implements SingleDestinationMessageSender {

	public DefaultSingleDestinationMessageSender(String destination,
												 MessageSender sender) {
		_sender = sender;
		_destination = destination;
	}

	public void send(String message) {
		_sender.send(_destination, message);
	}

	public void send(Object message) {
		_sender.send(_destination, message);
	}

	private MessageSender _sender;
	private String _destination;
}