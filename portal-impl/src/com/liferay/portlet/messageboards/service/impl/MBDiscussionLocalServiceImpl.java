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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.service.base.MBDiscussionLocalServiceBaseImpl;

/**
 * <a href="MBDiscussionLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBDiscussionLocalServiceImpl
	extends MBDiscussionLocalServiceBaseImpl {

	public MBDiscussion addDiscussion(
			long classNameId, long classPK, long threadId)
		throws SystemException {

		long discussionId = counterLocalService.increment();

		MBDiscussion discussion = mbDiscussionPersistence.create(discussionId);

		discussion.setClassNameId(classNameId);
		discussion.setClassPK(classPK);
		discussion.setThreadId(threadId);

		mbDiscussionPersistence.update(discussion, false);

		return discussion;
	}

	public MBDiscussion getDiscussion(long discussionId)
		throws PortalException, SystemException {

		return mbDiscussionPersistence.findByPrimaryKey(discussionId);
	}

	public MBDiscussion getDiscussion(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return mbDiscussionPersistence.findByC_C(classNameId, classPK);
	}

	public MBDiscussion getThreadDiscussion(long threadId)
		throws PortalException, SystemException {

		return mbDiscussionPersistence.findByThreadId(threadId);
	}

}