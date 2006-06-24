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

package com.liferay.portlet.messageboards.util.comparator;

import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.util.DateUtil;

import java.io.Serializable;

import java.util.Comparator;

/**
 * <a href="MessageThreadComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MessageThreadComparator implements Comparator, Serializable {

	public int compare(Object obj1, Object obj2) {
		MBMessage msg1 = (MBMessage)obj1;
		MBMessage msg2 = (MBMessage)obj2;

		int value =
			msg1.getParentMessageId().compareTo(msg2.getParentMessageId());

		if (value == 0) {
			value = DateUtil.compareTo(
				msg1.getCreateDate(), msg2.getCreateDate());
		}

		if (value == 0) {
			value = msg1.getMessageId().compareTo(msg2.getMessageId());
		}

		return value;
	}

}