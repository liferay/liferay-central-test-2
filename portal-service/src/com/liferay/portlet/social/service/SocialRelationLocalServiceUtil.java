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
		throws com.liferay.portal.SystemException {
		return getService().addSocialRelation(socialRelation);
	}

	public static com.liferay.portlet.social.model.SocialRelation createSocialRelation(
		long relationId) {
		return getService().createSocialRelation(relationId);
	}

	public static void deleteSocialRelation(long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteSocialRelation(relationId);
	}

	public static void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		getService().deleteSocialRelation(socialRelation);
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

	public static com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getSocialRelation(relationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getSocialRelations(start, end);
	}

	public static int getSocialRelationsCount()
		throws com.liferay.portal.SystemException {
		return getService().getSocialRelationsCount();
	}

	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		return getService().updateSocialRelation(socialRelation);
	}

	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateSocialRelation(socialRelation, merge);
	}

	public static com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addRelation(userId1, userId2, type);
	}

	public static void deleteRelation(long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteRelation(relationId);
	}

	public static void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteRelation(userId1, userId2, type);
	}

	public static void deleteRelation(
		com.liferay.portlet.social.model.SocialRelation relation)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteRelation(relation);
	}

	public static void deleteRelations(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteRelations(userId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRelation(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getRelation(userId1, userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getRelations(userId, type, start, end);
	}

	public static int getRelationsCount(long userId, int type)
		throws com.liferay.portal.SystemException {
		return getService().getRelationsCount(userId, type);
	}

	public static boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return getService().hasRelation(userId1, userId2, type);
	}

	public static boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
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