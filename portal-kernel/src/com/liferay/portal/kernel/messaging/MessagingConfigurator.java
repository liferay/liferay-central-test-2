/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.SystemException;

import java.util.List;
import java.util.Map;

/**
 * <a href="MessagingConfigurator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public interface MessagingConfigurator {
	public void setMessagingDestinations(List<Destination> destinations);

	public void setMessageListeners(
		Map<String, List<MessageListener>> listeners);

	public void configure()
		throws SystemException;
}