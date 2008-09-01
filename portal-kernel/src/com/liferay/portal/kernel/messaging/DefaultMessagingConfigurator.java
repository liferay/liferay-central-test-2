/*
 * Copyright (c) 2008, Your Corporation. All Rights Reserved.
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.SystemException;

import java.util.List;
import java.util.Map;

/**
 * <a href="DefaultMessagingConfigurator.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultMessagingConfigurator implements MessagingConfigurator {
    public void setMessagingDestinations(List<Destination> destinations) {
        _destinations = destinations;
    }

    public void setMessageListeners(
		Map<String, List<MessageListener>> listeners) {
        _listeners = listeners;
    }

    public void setMessageBus(MessageBus bus) {
        _bus = bus;
    }

    public void configure() throws SystemException {
        for (Destination destination : _destinations) {
            _bus.addDestination(destination);
        }

        for (Map.Entry<String, List<MessageListener>> lsnrs : _listeners.entrySet()) {
			String destinationName = lsnrs.getKey();
			for (MessageListener listener : lsnrs.getValue()) {
				_bus.registerMessageListener(destinationName, listener);
			}
        }
    }

    private List<Destination> _destinations;
    private Map<String, List<MessageListener>> _listeners;
    private MessageBus _bus;
}