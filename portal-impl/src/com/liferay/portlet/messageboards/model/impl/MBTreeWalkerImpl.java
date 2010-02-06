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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="MBTreeWalkerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBTreeWalkerImpl implements MBTreeWalker {

	public MBTreeWalkerImpl(MBMessage message, int status) {
		_messageIdsMap = new HashMap<Long, Integer>();

		try {
			_messages = MBMessageLocalServiceUtil.getThreadMessages(
				message.getThreadId(), status);

			for (int i = 0; i < _messages.size(); i++) {
				MBMessage curMessage = _messages.get(i);

				long parentMessageId = curMessage.getParentMessageId();

				if (!curMessage.isRoot() &&
					!_messageIdsMap.containsKey(parentMessageId)) {

					_messageIdsMap.put(parentMessageId, i);
				}
			}
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public List<MBMessage> getChildren(MBMessage message) {
		List<MBMessage> children = new ArrayList<MBMessage>();

		int[] range = getChildrenRange(message);

		for (int i = range[0]; i < range[1]; i++) {
			children.add(_messages.get(i));
		}

		return children;
	}

	public int[] getChildrenRange(MBMessage message) {
		long messageId = message.getMessageId();

		Integer pos = _messageIdsMap.get(messageId);

		if (pos == null) {
			return new int[] {0, 0};
		}

		int[] range = new int[2];
		range[0] = pos.intValue();

		for (int i = range[0]; i < _messages.size(); i++) {
			MBMessage curMessage = _messages.get(i);

			if (curMessage.getParentMessageId() == messageId) {
				range[1] = i + 1;
			}
			else {
				break;
			}
		}

		return range;
	}

	public List<MBMessage> getMessages() {
		return _messages;
	}

	public MBMessage getRoot() {
		return _messages.get(0);
	}

	public boolean isLeaf(MBMessage message) {
		Long messageIdObj = new Long(message.getMessageId());

		if (_messageIdsMap.containsKey(messageIdObj)) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean isOdd() {
		_odd = !_odd;

		return _odd;
	}

	private static Log _log = LogFactoryUtil.getLog(MBTreeWalkerImpl.class);

	private Map<Long, Integer> _messageIdsMap;
	private List<MBMessage> _messages;
	private boolean _odd;

}