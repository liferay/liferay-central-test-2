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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="DefaultMessageBus.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class DefaultMessageBus implements MessageBus {

	public DefaultMessageBus() {
		_responseDestination = new ParallelDispatchedDestination(
			DestinationNames.RESPONSE);

		addDestination(_responseDestination);
	}

	public synchronized void addDestination(Destination destination) {
		_destinations.put(destination.getName(), destination);
	}

	public String getNextResponseId() {
		return PortalUUIDUtil.generate();
	}

	public synchronized void registerMessageListener(
		String destination, MessageListener listener) {

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			throw new IllegalStateException(
				"Destination " + destination + " is not configured");
		}

		destinationModel.register(listener);
	}

	public synchronized void removeDestination(String destination) {
		_destinations.remove(destination);
	}

	public void sendMessage(String destination, String message) {
		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destination " + destination + " is not configured");
			}

			return;
		}

		destinationModel.send(message);
	}

	public String sendSynchronizedMessage(String destination, String message) {
		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Destination " + destination + " is not configured");
			}

			return null;
		}

		ResponseMessageListener responseMessageListener =
			new ResponseMessageListener(destinationModel, getNextResponseId());

		_responseDestination.register(responseMessageListener);

		try {
			return responseMessageListener.send(message);
		}
		catch (InterruptedException ie) {
			return null;
		}
		finally {
			_responseDestination.unregister(responseMessageListener);
		}
	}

	public synchronized void setDestinations(List<Destination> destinations) {
		for (Destination destination : destinations) {
			_destinations.put(destination.getName(), destination);
		}
	}

	public synchronized boolean unregisterMessageListener(
		String destination, MessageListener listener) {

		Destination destinationModel = _destinations.get(destination);

		if (destinationModel == null) {
			return false;
		}

		return destinationModel.unregister(listener);
	}

	private static Log _log = LogFactoryUtil.getLog(DefaultMessageBus.class);

	private Map<String, Destination> _destinations =
		new HashMap<String, Destination>();
	private Destination _responseDestination;

}