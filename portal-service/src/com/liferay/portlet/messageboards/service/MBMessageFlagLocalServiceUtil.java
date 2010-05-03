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
 * <a href="MBMessageFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBMessageFlagLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagLocalService
 * @generated
 */
public class MBMessageFlagLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessageFlag addMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMBMessageFlag(mbMessageFlag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag createMBMessageFlag(
		long messageFlagId) {
		return getService().createMBMessageFlag(messageFlagId);
	}

	public static void deleteMBMessageFlag(long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBMessageFlag(messageFlagId);
	}

	public static void deleteMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMBMessageFlag(mbMessageFlag);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag getMBMessageFlag(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessageFlag(messageFlagId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> getMBMessageFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessageFlags(start, end);
	}

	public static int getMBMessageFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMBMessageFlagsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBMessageFlag(mbMessageFlag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMBMessageFlag(mbMessageFlag, merge);
	}

	public static void addReadFlags(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addReadFlags(userId, thread);
	}

	public static void addQuestionFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addQuestionFlag(messageId);
	}

	public static void deleteAnswerFlags(long threadId, long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAnswerFlags(threadId, messageId);
	}

	public static void deleteFlags(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFlags(userId);
	}

	public static void deleteFlags(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFlags(messageId, flag);
	}

	public static void deleteQuestionAndAnswerFlags(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteQuestionAndAnswerFlags(threadId);
	}

	public static void deleteThreadFlags(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteThreadFlags(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag getReadFlag(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getReadFlag(userId, thread);
	}

	public static boolean hasAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasAnswerFlag(messageId);
	}

	public static boolean hasQuestionFlag(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasQuestionFlag(messageId);
	}

	public static boolean hasReadFlag(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().hasReadFlag(userId, thread);
	}

	public static MBMessageFlagLocalService getService() {
		if (_service == null) {
			_service = (MBMessageFlagLocalService)PortalBeanLocatorUtil.locate(MBMessageFlagLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MBMessageFlagLocalService service) {
		_service = service;
	}

	private static MBMessageFlagLocalService _service;
}