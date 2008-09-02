/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	public void setDestinationEventListener(List<DestinationEventListener> listeners) {
		_destinationEventListeners = listeners;
	}

	public void setMessageListeners(
		Map<String, List<MessageListener>> listeners) {
		_listeners = listeners;
	}

	public void setMessageBus(MessageBus bus) {
		_bus = bus;
	}

	public void configure() throws SystemException {
		for (DestinationEventListener listener : _destinationEventListeners) {
			_bus.addDestinationEventListener(listener);
		}


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
	private List<DestinationEventListener> _destinationEventListeners;
}