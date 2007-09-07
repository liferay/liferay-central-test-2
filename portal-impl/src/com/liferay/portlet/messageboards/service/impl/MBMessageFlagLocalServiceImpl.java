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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.SystemException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.service.base.MBMessageFlagLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="MBMessageFlagLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageFlagLocalServiceImpl
	extends MBMessageFlagLocalServiceBaseImpl {

	public void addReadFlags(long userId, List messages)
		throws SystemException {

		if (userId > 0) {
			Iterator itr = messages.iterator();

			while (itr.hasNext()) {
				MBMessage message = (MBMessage)itr.next();

				MBMessageFlag messageFlag = MBMessageFlagUtil.fetchByU_M(
					userId, message.getMessageId());

				if (messageFlag == null) {
					long messageFlagId = CounterLocalServiceUtil.increment();

					messageFlag = MBMessageFlagUtil.create(messageFlagId);

					messageFlag.setUserId(userId);
					messageFlag.setMessageId(message.getMessageId());
					messageFlag.setFlag(MBMessageFlagImpl.READ_FLAG);

					MBMessageFlagUtil.update(messageFlag);
				}
			}
		}
	}

	public void deleteFlags(long userId) throws SystemException {
		MBMessageFlagUtil.removeByUserId(userId);
	}

	public boolean hasReadFlag(long userId, long messageId)
		throws SystemException {

		if (userId > 0) {

			// Unauthenticated users do not have a record of read messages

			return true;
		}

		MBMessageFlag messageFlag = MBMessageFlagUtil.fetchByU_M(
			userId, messageId);

		if (messageFlag != null) {
			return true;
		}
		else {
			return false;
		}
	}

}