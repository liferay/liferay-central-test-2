/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;

public class MBDiscussionLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBDiscussion addMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		return getService().addMBDiscussion(mbDiscussion);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion createMBDiscussion(
		long discussionId) {
		return getService().createMBDiscussion(discussionId);
	}

	public static void deleteMBDiscussion(long discussionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMBDiscussion(discussionId);
	}

	public static void deleteMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		getService().deleteMBDiscussion(mbDiscussion);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getMBDiscussion(
		long discussionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMBDiscussion(discussionId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getMBDiscussions(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getMBDiscussions(start, end);
	}

	public static int getMBDiscussionsCount()
		throws com.liferay.portal.SystemException {
		return getService().getMBDiscussionsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion updateMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		return getService().updateMBDiscussion(mbDiscussion);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion updateMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateMBDiscussion(mbDiscussion, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion addDiscussion(
		long classNameId, long classPK, long threadId)
		throws com.liferay.portal.SystemException {
		return getService().addDiscussion(classNameId, classPK, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getDiscussion(
		long discussionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDiscussion(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getDiscussion(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDiscussion(className, classPK);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getThreadDiscussion(
		long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getThreadDiscussion(threadId);
	}

	public static MBDiscussionLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("MBDiscussionLocalService is not set");
		}

		return _service;
	}

	public void setService(MBDiscussionLocalService service) {
		_service = service;
	}

	private static MBDiscussionLocalService _service;
}