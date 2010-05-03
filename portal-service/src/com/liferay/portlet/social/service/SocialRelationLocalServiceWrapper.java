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
 * <a href="SocialRelationLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SocialRelationLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelationLocalService
 * @generated
 */
public class SocialRelationLocalServiceWrapper
	implements SocialRelationLocalService {
	public SocialRelationLocalServiceWrapper(
		SocialRelationLocalService socialRelationLocalService) {
		_socialRelationLocalService = socialRelationLocalService;
	}

	public com.liferay.portlet.social.model.SocialRelation addSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.addSocialRelation(socialRelation);
	}

	public com.liferay.portlet.social.model.SocialRelation createSocialRelation(
		long relationId) {
		return _socialRelationLocalService.createSocialRelation(relationId);
	}

	public void deleteSocialRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteSocialRelation(relationId);
	}

	public void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteSocialRelation(socialRelation);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getSocialRelation(relationId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getSocialRelations(start, end);
	}

	public int getSocialRelationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getSocialRelationsCount();
	}

	public com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.updateSocialRelation(socialRelation);
	}

	public com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.updateSocialRelation(socialRelation,
			merge);
	}

	public com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.addRelation(userId1, userId2, type);
	}

	public void deleteRelation(long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelation(relationId);
	}

	public void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelation(userId1, userId2, type);
	}

	public void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelation(relation);
	}

	public void deleteRelations(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialRelationLocalService.deleteRelations(userId);
	}

	public com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelation(relationId);
	}

	public com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelation(userId1, userId2, type);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelations(userId, type, start, end);
	}

	public int getRelationsCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.getRelationsCount(userId, type);
	}

	public boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.hasRelation(userId1, userId2, type);
	}

	public boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialRelationLocalService.isRelatable(userId1, userId2, type);
	}

	public SocialRelationLocalService getWrappedSocialRelationLocalService() {
		return _socialRelationLocalService;
	}

	private SocialRelationLocalService _socialRelationLocalService;
}