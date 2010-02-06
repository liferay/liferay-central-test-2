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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ConcurrentHashSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <a href="DefaultMessageBus.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class DefaultMessageBus implements MessageBus {

	public synchronized void addDestination(Destination destination) {
		_destinations.put(destination.getName(), destination);

		fireDestinationAddedEvent(destination);
	}

	public void addDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		_destinationEventListeners.add(destinationEventListener);
	}

	public void addDestinationEventListener(
		String destinationName,
		DestinationEventListener destinationEventListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination != null) {
			destination.addDestinationEventListener(destinationEventListener);
		}
	}

	public void destroy() {
		shutdown(true);
	}

	public Destination getDestination(String destinationName) {
		return _destinations.get(destinationName);
	}

	public int getDestinationCount() {
		return _destinations.size();
	}

	public Collection<String> getDestinationNames() {
		return _destinations.keySet();
	}

	public Collection<Destination> getDestinations() {
		return _destinations.values();
	}

	public boolean hasDestination(String destinationName) {
		return _destinations.containsKey(destinationName);
	}

	public boolean hasMessageListener(String destinationName) {
		Destination destination = _destinations.get(destinationName);

		if ((destination != null) && destination.isRegistered()) {
			return true;
		}
		else {
			return false;
		}
	}

	public synchronized boolean registerMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			throw new IllegalStateException(
				"Destination " + destinationName + " is not configured");
		}

		boolean registered = destination.register(messageListener);

		if (registered) {
			fireMessageListenerRegisteredEvent(destination, messageListener);
		}

		return registered;
	}

	public synchronized Destination removeDestination(String destinationName) {
		Destination destinationModel = _destinations.remove(destinationName);

		destinationModel.removeDestinationEventListeners();
		destinationModel.unregisterMessageListeners();

		fireDestinationRemovedEvent(destinationModel);

		return destinationModel;
	}

	public void removeDestinationEventListener(
		DestinationEventListener destinationEventListener) {

		_destinationEventListeners.remove(destinationEventListener);
	}

	public void removeDestinationEventListener(
		String destinationName,
		DestinationEventListener destinationEventListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination != null) {
			destination.removeDestinationEventListener(
				destinationEventListener);
		}
	}

	public void replace(Destination destination) {
		Destination oldDestination = _destinations.get(destination.getName());

		oldDestination.copyDestinationEventListeners(destination);
		oldDestination.copyMessageListeners(destination);

		removeDestination(oldDestination.getName());

		addDestination(destination);
	}

	public void sendMessage(String destinationName, Message message) {
		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Destination " + destinationName + " is not configured");
			}

			return;
		}

		message.setDestinationName(destinationName);

		destination.send(message);
	}

	public void shutdown() {
		shutdown(false);
	}

	public synchronized void shutdown(boolean force) {
		for (Destination destination : _destinations.values()) {
			destination.close(force);
		}
	}

	public synchronized boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		Destination destination = _destinations.get(destinationName);

		if (destination == null) {
			return false;
		}

		boolean unregistered = destination.unregister(messageListener);

		if (unregistered) {
			fireMessageListenerUnregisteredEvent(destination, messageListener);
		}

		return unregistered;
	}

	protected void fireDestinationAddedEvent(Destination destination) {
		for (DestinationEventListener listener : _destinationEventListeners) {
			listener.destinationAdded(destination);
		}
	}

	protected void fireDestinationRemovedEvent(Destination destination) {
		for (DestinationEventListener listener : _destinationEventListeners) {
			listener.destinationRemoved(destination);
		}
	}

	protected void fireMessageListenerRegisteredEvent(
		Destination destination, MessageListener messageListener) {

		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destinationEventListener.messageListenerRegistered(
				destination.getName(), messageListener);
		}
	}

	protected void fireMessageListenerUnregisteredEvent(
		Destination destination, MessageListener messageListener) {

		for (DestinationEventListener destinationEventListener :
				_destinationEventListeners) {

			destinationEventListener.messageListenerUnregistered(
				destination.getName(), messageListener);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DefaultMessageBus.class);

	private Set<DestinationEventListener> _destinationEventListeners =
		new ConcurrentHashSet<DestinationEventListener>();
	private Map<String, Destination> _destinations =
		new HashMap<String, Destination>();

}