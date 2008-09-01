/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

/**
 * <a href="MessageDestinationEventListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface DestinationEventListener {
	public void destinationAdded(Destination dest);
	public void destinationRemoved(Destination dest);
}