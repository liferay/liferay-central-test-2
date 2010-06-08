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
 * <a href="SocialEquityUserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialEquityUserLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserLocalService
 * @generated
 */
public class SocialEquityUserLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialEquityUser addSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialEquityUser(socialEquityUser);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser createSocialEquityUser(
		long equityUserId) {
		return getService().createSocialEquityUser(equityUserId);
	}

	public static void deleteSocialEquityUser(long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityUser(equityUserId);
	}

	public static void deleteSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialEquityUser(socialEquityUser);
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

	public static com.liferay.portlet.social.model.SocialEquityUser getSocialEquityUser(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUser(equityUserId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getSocialEquityUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUsers(start, end);
	}

	public static int getSocialEquityUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUsersCount();
	}

	public static com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityUser(socialEquityUser);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialEquityUser(socialEquityUser, merge);
	}

	public static com.liferay.portlet.social.model.SocialEquityUser getSocialEquityUserAggregate(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialEquityUserAggregate(userId);
	}

	public static SocialEquityUserLocalService getService() {
		if (_service == null) {
			_service = (SocialEquityUserLocalService)PortalBeanLocatorUtil.locate(SocialEquityUserLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialEquityUserLocalService service) {
		_service = service;
	}

	private static SocialEquityUserLocalService _service;
}