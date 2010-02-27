/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MBDiscussionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBDiscussionLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBDiscussionLocalService
 * @generated
 */
public class MBDiscussionLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBDiscussion addMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMBDiscussion(mbDiscussion);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion createMBDiscussion(
		long discussionId) {
		return getService().createMBDiscussion(discussionId);
	}

	public static void deleteMBDiscussion(long discussionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBDiscussion(discussionId);
	}

	public static void deleteMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBDiscussion(mbDiscussion);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getMBDiscussion(
		long discussionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBDiscussion(discussionId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getMBDiscussions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBDiscussions(start, end);
	}

	public static int getMBDiscussionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBDiscussionsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion updateMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBDiscussion(mbDiscussion);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion updateMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBDiscussion(mbDiscussion, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion addDiscussion(
		long classNameId, long classPK, long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDiscussion(classNameId, classPK, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getDiscussion(
		long discussionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDiscussion(discussionId);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getDiscussion(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDiscussion(className, classPK);
	}

	public static com.liferay.portlet.messageboards.model.MBDiscussion getThreadDiscussion(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getThreadDiscussion(threadId);
	}

	public static MBDiscussionLocalService getService() {
		if (_service == null) {
			_service = (MBDiscussionLocalService)PortalBeanLocatorUtil.locate(MBDiscussionLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MBDiscussionLocalService service) {
		_service = service;
	}

	private static MBDiscussionLocalService _service;
}