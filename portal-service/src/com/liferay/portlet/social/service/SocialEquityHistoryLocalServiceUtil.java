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
 * <a href="SocialEquityHistoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialEquityHistoryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityHistoryLocalService
 * @generated
 */
public class SocialEquityHistoryLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialEquityHistory addSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityHistory(socialEquityHistory);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory createSocialEquityHistory(
		long equityHistoryId) {
		return getService().createSocialEquityHistory(equityHistoryId);
	}

	public static void deleteSocialEquityHistory(long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityHistory(equityHistoryId);
	}

	public static void deleteSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityHistory(socialEquityHistory);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> dynamicQuery(
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

	public static com.liferay.portlet.social.model.SocialEquityHistory getSocialEquityHistory(
		long equityHistoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityHistory(equityHistoryId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityHistory> getSocialEquityHistories(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityHistories(start, end);
	}

	public static int getSocialEquityHistoriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityHistoriesCount();
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityHistory(socialEquityHistory);
	}

	public static com.liferay.portlet.social.model.SocialEquityHistory updateSocialEquityHistory(
		com.liferay.portlet.social.model.SocialEquityHistory socialEquityHistory,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityHistory(socialEquityHistory, merge);
	}

	public static SocialEquityHistoryLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityHistoryLocalService)PortalBeanLocatorUtil.locate(SocialEquityHistoryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialEquityHistoryLocalService service) {
		_service = service;
	}

	private static SocialEquityHistoryLocalService _service;
}