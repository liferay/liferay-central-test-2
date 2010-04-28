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

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="SocialEquityLogLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialEquityLogLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityLogLocalService
 * @generated
 */
public class SocialEquityLogLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialEquityLog addSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityLog(socialEquityLog);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog createSocialEquityLog(
		long equityLogId) {
		return getService().createSocialEquityLog(equityLogId);
	}

	public static void deleteSocialEquityLog(long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityLog(equityLogId);
	}

	public static void deleteSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityLog(socialEquityLog);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> dynamicQuery(
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

	public static com.liferay.portlet.social.model.SocialEquityLog getSocialEquityLog(
		long equityLogId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityLog(equityLogId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityLog> getSocialEquityLogs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityLogs(start, end);
	}

	public static int getSocialEquityLogsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityLogsCount();
	}

	public static com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityLog(socialEquityLog);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog updateSocialEquityLog(
		com.liferay.portlet.social.model.SocialEquityLog socialEquityLog,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityLog(socialEquityLog, merge);
	}

	public static com.liferay.portlet.social.model.SocialEquityLog addEquityLog(
		long userId, long assetEntryId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addEquityLog(userId, assetEntryId, actionId);
	}

	public static void checkEquityLogs()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkEquityLogs();
	}

	public static SocialEquityLogLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityLogLocalService)PortalBeanLocatorUtil.locate(SocialEquityLogLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialEquityLogLocalService service) {
		_service = service;
	}

	private static SocialEquityLogLocalService _service;
}