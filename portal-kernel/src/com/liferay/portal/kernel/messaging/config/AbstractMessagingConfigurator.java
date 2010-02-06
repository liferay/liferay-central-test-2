/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="AbstractMessagingConfigurator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Michael C. Han
 */
public abstract class AbstractMessagingConfigurator
	implements MessagingConfigurator {

	public void destroy() {
		MessageBus messageBus = getMessageBus();

		for (Map.Entry<String, List<MessageListener>> messageListeners :
				_messageListeners.entrySet()) {

			String destinationName = messageListeners.getKey();

			for (MessageListener messageListener :
					messageListeners.getValue()) {

				messageBus.unregisterMessageListener(
					destinationName, messageListener);
			}
		}

		for (Destination destination : _destinations) {
			messageBus.removeDestination(destination.getName());
		}

		for (DestinationEventListener destinationEventListener :
				_globalDestinationEventListeners) {

			messageBus.removeDestinationEventListener(destinationEventListener);
		}
	}

	public void init() {
		MessageBus messageBus = getMessageBus();

		for (DestinationEventListener destinationEventListener :
				_globalDestinationEventListeners) {

			messageBus.addDestinationEventListener(destinationEventListener);
		}

		for (Destination destination : _destinations) {
			messageBus.addDestination(destination);
		}

		for (Map.Entry<String, List<DestinationEventListener>>
				destinationEventListeners :
					_specificDestinationEventListeners.entrySet()) {

			String destinationName = destinationEventListeners.getKey();

			for (DestinationEventListener destinationEventListener :
					destinationEventListeners.getValue()) {

				messageBus.addDestinationEventListener(
					destinationName, destinationEventListener);
			}
		}

		for (Destination destination : _replacementDestinations) {
			messageBus.replace(destination);
		}

		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		try {
			ClassLoader operatingClassLoader = getOperatingClassloader();

			Thread.currentThread().setContextClassLoader(operatingClassLoader);

			for (Map.Entry<String, List<MessageListener>> messageListeners :
					_messageListeners.entrySet()) {

				String destinationName = messageListeners.getKey();

				for (MessageListener messageListener :
						messageListeners.getValue()) {

					messageBus.registerMessageListener(
						destinationName, messageListener);
				}
			}
		}
		finally {
			Thread.currentThread().setContextClassLoader(contextClassLoader);
		}
	}

	public void setDestinations(List<Destination> destinations) {
		_destinations = destinations;
	}

	public void setGlobalDestinationEventListeners(
		List<DestinationEventListener> globalDestinationEventListeners) {

		_globalDestinationEventListeners = globalDestinationEventListeners;
	}

	public void setMessageListeners(
		Map<String, List<MessageListener>> messageListeners) {

		_messageListeners = messageListeners;

		for (List<MessageListener> messageListenersList :
				_messageListeners.values()) {

			for (MessageListener messageListener : messageListenersList) {
				Class<?> messageListenerClass = messageListener.getClass();

				try {
					Method setMessageBusMethod =
						messageListenerClass.getMethod(
							"setMessageBus", MessageBus.class);

					setMessageBusMethod.setAccessible(true);

					setMessageBusMethod.invoke(
						messageListener, getMessageBus());

					continue;
				}
				catch (Exception e) {
				}

				try{
					Method setMessageBusMethod =
						messageListenerClass.getDeclaredMethod(
							"setMessageBus", MessageBus.class);

					setMessageBusMethod.setAccessible(true);

					setMessageBusMethod.invoke(
						messageListener, getMessageBus());
				}
				catch (Exception e) {
				}
			}
		}
	}

	public void setReplacementDestinations(
		List<Destination> replacementDestinations) {

		_replacementDestinations = replacementDestinations;
	}

	public void setSpecificDestinationEventListener(
		Map<String, List<DestinationEventListener>>
			specificDestinationEventListeners) {

		_specificDestinationEventListeners = specificDestinationEventListeners;
	}

	protected abstract MessageBus getMessageBus();

	protected abstract ClassLoader getOperatingClassloader();

	private List<Destination> _destinations = new ArrayList<Destination>();
	private List<DestinationEventListener> _globalDestinationEventListeners =
		new ArrayList<DestinationEventListener>();
	private Map<String, List<MessageListener>> _messageListeners  =
		new HashMap<String, List<MessageListener>>();
	private List<Destination> _replacementDestinations =
		new ArrayList<Destination>();
	private Map<String, List<DestinationEventListener>>
		_specificDestinationEventListeners =
			new HashMap<String, List<DestinationEventListener>>();

}