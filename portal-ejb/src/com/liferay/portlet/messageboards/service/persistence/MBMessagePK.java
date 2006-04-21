/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.util.StringPool;

import java.io.Serializable;

/**
 * <a href="MBMessagePK.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessagePK implements Comparable, Serializable {
	public String topicId;
	public String messageId;

	public MBMessagePK() {
	}

	public MBMessagePK(String topicId, String messageId) {
		this.topicId = topicId;
		this.messageId = messageId;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBMessagePK pk = (MBMessagePK)obj;
		int value = 0;
		value = topicId.compareTo(pk.topicId);

		if (value != 0) {
			return value;
		}

		value = messageId.compareTo(pk.messageId);

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBMessagePK pk = null;

		try {
			pk = (MBMessagePK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((topicId.equals(pk.topicId)) && (messageId.equals(pk.messageId))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (topicId + messageId).hashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(StringPool.OPEN_CURLY_BRACE);
		sb.append("topicId");
		sb.append(StringPool.EQUAL);
		sb.append(topicId);
		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("messageId");
		sb.append(StringPool.EQUAL);
		sb.append(messageId);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}