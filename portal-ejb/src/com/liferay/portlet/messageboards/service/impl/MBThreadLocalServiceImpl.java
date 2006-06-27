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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.SystemException;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.portlet.messageboards.service.spring.MBThreadLocalService;

import java.util.List;

/**
 * <a href="MBThreadLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBThreadLocalServiceImpl implements MBThreadLocalService {

	public List getGroupThreads(String groupId, int begin, int end)
		throws SystemException {

		return MBThreadFinder.findByGroupId(groupId, begin, end);
	}

	public int getGroupThreadsCount(String groupId) throws SystemException {
		return MBThreadFinder.countByGroupId(groupId);
	}

	public List getThreads(String topicId, int begin, int end)
		throws SystemException {

		return MBThreadUtil.findByTopicId(topicId, begin, end);
	}

	public int getThreadsCount(String topicId) throws SystemException {
		return MBThreadUtil.countByTopicId(topicId);
	}

	public boolean hasReadThread(String userId, String threadId)
		throws SystemException {

		if (userId == null) {

			// Unauthenticated users do not have a record of read messages

			return true;
		}

		int total = MBMessageUtil.countByThreadId(threadId);
		int read = MBMessageFlagFinder.countByT_U(threadId, userId);

		if (total != read) {
			return false;
		}
		else {
			return true;
		}
	}

}