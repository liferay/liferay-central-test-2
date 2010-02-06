/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.addSocialRelation(socialRelation);
	}

	public com.liferay.portlet.social.model.SocialRelation createSocialRelation(
		long relationId) {
		return _socialRelationLocalService.createSocialRelation(relationId);
	}

	public void deleteSocialRelation(long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_socialRelationLocalService.deleteSocialRelation(relationId);
	}

	public void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		_socialRelationLocalService.deleteSocialRelation(socialRelation);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialRelationLocalService.getSocialRelation(relationId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end) throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.getSocialRelations(start, end);
	}

	public int getSocialRelationsCount()
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.getSocialRelationsCount();
	}

	public com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.updateSocialRelation(socialRelation);
	}

	public com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.updateSocialRelation(socialRelation,
			merge);
	}

	public com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialRelationLocalService.addRelation(userId1, userId2, type);
	}

	public void deleteRelation(long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_socialRelationLocalService.deleteRelation(relationId);
	}

	public void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_socialRelationLocalService.deleteRelation(userId1, userId2, type);
	}

	public void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_socialRelationLocalService.deleteRelation(relation);
	}

	public void deleteRelations(long userId)
		throws com.liferay.portal.SystemException {
		_socialRelationLocalService.deleteRelations(userId);
	}

	public com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialRelationLocalService.getRelation(relationId);
	}

	public com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _socialRelationLocalService.getRelation(userId1, userId2, type);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.getRelations(userId, type, start, end);
	}

	public int getRelationsCount(long userId, int type)
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.getRelationsCount(userId, type);
	}

	public boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.hasRelation(userId1, userId2, type);
	}

	public boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return _socialRelationLocalService.isRelatable(userId1, userId2, type);
	}

	public SocialRelationLocalService getWrappedSocialRelationLocalService() {
		return _socialRelationLocalService;
	}

	private SocialRelationLocalService _socialRelationLocalService;
}