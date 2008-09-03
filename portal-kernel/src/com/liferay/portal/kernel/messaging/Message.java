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


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="Message.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Message implements Serializable {
	public Message(String type) {
		_messageType = type;
	}

	public String getMessageType() {
		return _messageType;
	}

	public void setDestination(String destination) {
		_messageValues.put(DESTINATION_KEY, destination);
	}

	public String getDestination() {
		return (String)_messageValues.get(DESTINATION_KEY);
	}

	public void setReplyTo(String replyTo) {
		_messageValues.put(REPLY_TO_KEY, replyTo);
	}

	public String getReplyTo() {
		return (String)_messageValues.get(REPLY_TO_KEY);
	}

	public void setMessageId(String messageId) {
		_messageValues.put(MESSAGE_ID_KEY, messageId);
	}

	public String getMessageId() {
		return (String)_messageValues.get(MESSAGE_ID_KEY);
	}

	public Object getPayload() {
		return _messageValues.get(PAYLOAD_KEY);
	}
	
	public void setPayload(Object object) {
		_messageValues.put(PAYLOAD_KEY, object);
	}

	public void put(String key, Object value) {
		_messageValues.put(key, value);
	}

	public Object get(String key) {
		return _messageValues.get(key);
	}


	public String toString() {
		return "Message{" +
			"_messageType='" + _messageType + '\'' +
			", _messageValues=" + _messageValues +
			'}';
	}

	private String _messageType;
	private Map<String, Object> _messageValues = new HashMap<String, Object>();
	private static final String DESTINATION_KEY = "destination";
	private static final String REPLY_TO_KEY = "replyTo";
	private static final String MESSAGE_ID_KEY = "messageId";
	private static final String PAYLOAD_KEY = "payLoad";
}