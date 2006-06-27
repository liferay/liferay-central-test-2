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

package com.liferay.portlet.messageboards.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.messageboards.service.spring.MBThreadLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBThreadLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBThreadLocalServiceEJBImpl implements MBThreadLocalService,
	SessionBean {
	public static final String CLASS_NAME = MBThreadLocalService.class.getName() +
		".transaction";

	public static MBThreadLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (MBThreadLocalService)ctx.getBean(CLASS_NAME);
	}

	public java.util.List getGroupThreads(java.lang.String groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getGroupThreads(groupId, begin, end);
	}

	public int getGroupThreadsCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getService().getGroupThreadsCount(groupId);
	}

	public java.util.List getThreads(java.lang.String topicId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getThreads(topicId, begin, end);
	}

	public int getThreadsCount(java.lang.String topicId)
		throws com.liferay.portal.SystemException {
		return getService().getThreadsCount(topicId);
	}

	public boolean hasReadThread(java.lang.String userId,
		java.lang.String threadId) throws com.liferay.portal.SystemException {
		return getService().hasReadThread(userId, threadId);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}