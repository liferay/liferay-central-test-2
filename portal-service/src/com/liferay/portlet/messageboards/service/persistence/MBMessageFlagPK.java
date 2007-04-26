/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="MBMessageFlagPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFlagPK implements Comparable, Serializable {
	public String topicId;
	public String messageId;
	public long userId;

	public MBMessageFlagPK() {
	}

	public MBMessageFlagPK(String topicId, String messageId, long userId) {
		this.topicId = topicId;
		this.messageId = messageId;
		this.userId = userId;
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

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBMessageFlagPK pk = (MBMessageFlagPK)obj;
		int value = 0;
		value = topicId.compareTo(pk.topicId);

		if (value != 0) {
			return value;
		}

		value = messageId.compareTo(pk.messageId);

		if (value != 0) {
			return value;
		}

		if (userId < pk.userId) {
			value = -1;
		}
		else if (userId > pk.userId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBMessageFlagPK pk = null;

		try {
			pk = (MBMessageFlagPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((topicId.equals(pk.topicId)) && (messageId.equals(pk.messageId)) &&
				(userId == pk.userId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(topicId) + String.valueOf(messageId) +
		String.valueOf(userId)).hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();
		sm.append(StringPool.OPEN_CURLY_BRACE);
		sm.append("topicId");
		sm.append(StringPool.EQUAL);
		sm.append(topicId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("messageId");
		sm.append(StringPool.EQUAL);
		sm.append(messageId);
		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("userId");
		sm.append(StringPool.EQUAL);
		sm.append(userId);
		sm.append(StringPool.CLOSE_CURLY_BRACE);

		return sm.toString();
	}
}