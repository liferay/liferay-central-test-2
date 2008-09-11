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

package com.liferay.portal.kernel.messaging.config;

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;
import java.util.Map;

/**
 * <a href="AbstractMessagingConfigurator.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael C. Han
 */
public abstract class AbstractMessagingConfigurator implements MessagingConfigurator {
	public void configure() {
		MessageBus messageBus = getMessageBus();
		for (DestinationEventListener listener : _destinationEventListeners) {
			messageBus.addDestinationEventListener(listener);
		}

		for (Destination destination : _destinations) {
			messageBus.addDestination(destination);
		}

		for (Map.Entry<String, List<MessageListener>> listeners :
				_messageListeners.entrySet()) {

			String destination = listeners.getKey();

			for (MessageListener listener : listeners.getValue()) {
				messageBus.registerMessageListener(destination, listener);
			}
		}
	}

	public void destroy() {
		MessageBus messageBus = getMessageBus();
		for (DestinationEventListener listener : _destinationEventListeners) {
			messageBus.removeDestinationEventListener(listener);
		}

		for (Map.Entry<String, List<MessageListener>> listeners :
				_messageListeners.entrySet()) {

			String destination = listeners.getKey();

			for (MessageListener listener : listeners.getValue()) {
				messageBus.unregisterMessageListener(destination, listener);
			}
		}
		for (Destination destination : _destinations) {
			messageBus.removeDestination(destination.getName());
		}

	}

	public void setDestinationEventListeners(
		List<DestinationEventListener> destinationEventListeners) {

		_destinationEventListeners = destinationEventListeners;
	}

	public void setDestinations(List<Destination> destinations) {
		_destinations = destinations;
	}

	public void setMessageListeners(
		Map<String, List<MessageListener>> messageListeners) {

		_messageListeners = messageListeners;
	}

	protected abstract MessageBus getMessageBus();

	private List<DestinationEventListener> _destinationEventListeners;
	private List<Destination> _destinations;
	private Map<String, List<MessageListener>> _messageListeners;
}
