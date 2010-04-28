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
 * <a href="SocialRelationLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialRelationLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationLocalService
 * @generated
 */
public class SocialRelationLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialRelation addSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialRelation(socialRelation);
	}

	public static com.liferay.portlet.social.model.SocialRelation createSocialRelation(
		long relationId) {
		return getService().createSocialRelation(relationId);
	}

	public static void deleteSocialRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialRelation(relationId);
	}

	public static void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialRelation(socialRelation);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> dynamicQuery(
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

	public static com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRelation(relationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRelations(start, end);
	}

	public static int getSocialRelationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialRelationsCount();
	}

	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialRelation(socialRelation);
	}

	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialRelation(socialRelation, merge);
	}

	public static com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addRelation(userId1, userId2, type);
	}

	public static void deleteRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelation(relationId);
	}

	public static void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelation(userId1, userId2, type);
	}

	public static void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelation(relation);
	}

	public static void deleteRelations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRelations(userId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelation(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelation(userId1, userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelations(userId, type, start, end);
	}

	public static int getRelationsCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelationsCount(userId, type);
	}

	public static boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRelation(userId1, userId2, type);
	}

	public static boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().isRelatable(userId1, userId2, type);
	}

	public static SocialRelationLocalService getService() {
		if (_service == null) {
			_service = (SocialRelationLocalService)PortalBeanLocatorUtil.locate(SocialRelationLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialRelationLocalService service) {
		_service = service;
	}

	private static SocialRelationLocalService _service;
}