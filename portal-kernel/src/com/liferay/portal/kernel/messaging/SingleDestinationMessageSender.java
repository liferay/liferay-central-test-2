/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

/**
 * <a href="SingleDestinationMessageSender.java.html"><b><i>View
 * Source</i></b></a>
 *
 * Message sender that only sends to a predefined destination
 *
 * @author Michael C. Han
 */
public interface SingleDestinationMessageSender {
	public void send(String message);
	public void send(Object message);
}