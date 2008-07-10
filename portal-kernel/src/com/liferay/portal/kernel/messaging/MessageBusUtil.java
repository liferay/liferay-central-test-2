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

	public static void sendMessage(String destination, Object message) {
		_instance._sendMessage(destination, message);
	}

	public static void sendMessage(String destination, String message) {
		_instance._sendMessage(destination, message);
	}

	public static Object sendSynchronizedMessage(
			String destination, Message message)
		throws MessageBusException {

		return _instance._sendSynchronizedMessage(destination, message);
	}

	public static Object sendSynchronizedMessage(
			String destination, Message message, long timeout)
		throws MessageBusException {

		return _instance._sendSynchronizedMessage(
			destination, message, timeout);
	}

	public static String sendSynchronizedMessage(
			String destination, String message)
		throws MessageBusException {

		return _instance._sendSynchronizedMessage(destination, message);
	}

	public static String sendSynchronizedMessage(
			String destination, String message, long timeout)
		throws MessageBusException {

		return _instance._sendSynchronizedMessage(
			destination, message, timeout);
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

	private void _sendMessage(String destination, Object message) {
		_messageBus.sendMessage(destination, message);
	}

	private void _sendMessage(String destination, String message) {
		_messageBus.sendMessage(destination, message);
	}

	private Object _sendSynchronizedMessage(String destination, Message message)
		throws MessageBusException {

		return _messageBus.sendSynchronizedMessage(
			destination, message, _DEFAULT_TIMEOUT);
	}

	private Object _sendSynchronizedMessage(
			String destination, Message message, long timeout)
		throws MessageBusException {

		return _messageBus.sendSynchronizedMessage(
			destination, message, timeout);
	}

	private String _sendSynchronizedMessage(String destination, String message)
		throws MessageBusException {

		return _messageBus.sendSynchronizedMessage(
			destination, message, _DEFAULT_TIMEOUT);
	}

	private String _sendSynchronizedMessage(
			String destination, String message, long timeout)
		throws MessageBusException {

		return _messageBus.sendSynchronizedMessage(
			destination, message, timeout);
	}

	private boolean _unregisterMessageListener(
		String destination, MessageListener listener) {

		return _messageBus.unregisterMessageListener(destination, listener);
	}

	private static final long _DEFAULT_TIMEOUT = 10000;

	private static MessageBusUtil _instance = new MessageBusUtil();

	private MessageBus _messageBus;
	private MessageSender _messageSender;

}