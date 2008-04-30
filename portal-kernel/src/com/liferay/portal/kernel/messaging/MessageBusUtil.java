/*
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

import java.util.List;

/**
 * <a href="MessageBusUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 *
 */
public class MessageBusUtil {

	public static void addDestination(Destination destination) {
		_instance._addDestination(destination);
	}

	public static SingleDestinationMessageSender createMessageSender(
		String destination) {

		return _instance._createMessageSender(destination);
	}

	public static MessageBus getMessageBus() {
		return _instance._messageBus;
	}

	public static MessageSender getMessageSender() {
		return _instance._messageSender;
	}

	public static void init(
		MessageBus messageBus, MessageSender messageSender) {

		_instance._init(messageBus, messageSender);
	}

	public static void registerMessageListener(
		String destination, MessageListener listener) {

		_instance._registerMessageListener(destination, listener);
	}

	public static void removeDestination(String destination) {
		_instance._removeDestination(destination);
	}

	public static void setDestinations(List<Destination> destinations) {
		_instance._setDestinations(destinations);
	}

	public static void sendMessage(String destination, String message) {
		_instance._sendMessage(destination, message);
	}

	public static boolean unregisterMessageListener(
		String destination, MessageListener listener) {

		return _instance._unregisterMessageListener(destination, listener);
	}

	private MessageBusUtil() {
	}

	private void _addDestination(Destination destination) {
		_messageBus.addDestination(destination);
	}

	private SingleDestinationMessageSender _createMessageSender(
		String destination) {

		return new DefaultSingleDestinationMessageSender(
			_messageSender, destination);
	}

	private void _init(MessageBus messageBus, MessageSender messageSender) {
		_messageBus = messageBus;
		_messageSender = messageSender;
	}

	private void _registerMessageListener(
		String destination, MessageListener listener) {

		_messageBus.registerMessageListener(destination, listener);
	}

	private void _removeDestination(String destination) {
		_messageBus.removeDestination(destination);
	}

	private void _setDestinations(List<Destination> destinations) {
		_messageBus.setDestinations(destinations);
	}

	private void _sendMessage(String destination, String message) {
		_messageBus.sendMessage(destination, message);
	}

	private boolean _unregisterMessageListener(
		String destination, MessageListener listener) {

		return _messageBus.unregisterMessageListener(destination, listener);
	}

	private static MessageBusUtil _instance = new MessageBusUtil();

	private MessageBus _messageBus;
	private MessageSender _messageSender;

}