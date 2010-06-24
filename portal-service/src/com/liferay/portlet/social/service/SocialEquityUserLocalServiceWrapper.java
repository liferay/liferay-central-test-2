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


/**
 * <a href="SocialEquityUserLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialEquityUserLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUserLocalService
 * @generated
 */
public class SocialEquityUserLocalServiceWrapper
	implements SocialEquityUserLocalService {
	public SocialEquityUserLocalServiceWrapper(
		SocialEquityUserLocalService socialEquityUserLocalService) {
		_socialEquityUserLocalService = socialEquityUserLocalService;
	}

	public com.liferay.portlet.social.model.SocialEquityUser addSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.addSocialEquityUser(socialEquityUser);
	}

	public com.liferay.portlet.social.model.SocialEquityUser createSocialEquityUser(
		long equityUserId) {
		return _socialEquityUserLocalService.createSocialEquityUser(equityUserId);
	}

	public void deleteSocialEquityUser(long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.deleteSocialEquityUser(equityUserId);
	}

	public void deleteSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityUserLocalService.deleteSocialEquityUser(socialEquityUser);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialEquityUser getSocialEquityUser(
		long equityUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUser(equityUserId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialEquityUser> getSocialEquityUsers(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUsers(start, end);
	}

	public int getSocialEquityUsersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getSocialEquityUsersCount();
	}

	public com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.updateSocialEquityUser(socialEquityUser);
	}

	public com.liferay.portlet.social.model.SocialEquityUser updateSocialEquityUser(
		com.liferay.portlet.social.model.SocialEquityUser socialEquityUser,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.updateSocialEquityUser(socialEquityUser,
			merge);
	}

	public com.liferay.portlet.social.model.SocialEquityValue getContributionEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getContributionEquity(userId);
	}

	public com.liferay.portlet.social.model.SocialEquityValue getParticipationEquity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityUserLocalService.getParticipationEquity(userId);
	}

	public SocialEquityUserLocalService getWrappedSocialEquityUserLocalService() {
		return _socialEquityUserLocalService;
	}

	private SocialEquityUserLocalService _socialEquityUserLocalService;
}