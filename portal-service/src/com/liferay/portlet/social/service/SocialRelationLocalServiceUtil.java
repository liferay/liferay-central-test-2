/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.social.service.SocialRelationLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.SocialRelationLocalService
 *
 */
public class SocialRelationLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialRelation addSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		return _service.addSocialRelation(socialRelation);
	}

	public static void deleteSocialRelation(long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteSocialRelation(relationId);
	}

	public static void deleteSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		_service.deleteSocialRelation(socialRelation);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.social.model.SocialRelation getSocialRelation(
		long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialRelation(relationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getSocialRelations(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getSocialRelations(start, end);
	}

	public static int getSocialRelationsCount()
		throws com.liferay.portal.SystemException {
		return _service.getSocialRelationsCount();
	}

	public static com.liferay.portlet.social.model.SocialRelation updateSocialRelation(
		com.liferay.portlet.social.model.SocialRelation socialRelation)
		throws com.liferay.portal.SystemException {
		return _service.updateSocialRelation(socialRelation);
	}

	public static com.liferay.portlet.social.model.SocialRelation addRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addRelation(userId1, userId2, type);
	}

	public static void deleteRelation(long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteRelation(relationId);
	}

	public static void deleteRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteRelation(userId1, userId2, type);
	}

	public static void deleteRelations(long userId)
		throws com.liferay.portal.SystemException {
		_service.deleteRelations(userId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long relationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getRelation(relationId);
	}

	public static com.liferay.portlet.social.model.SocialRelation getRelation(
		long userId1, long userId2, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getRelation(userId1, userId2, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialRelation> getRelations(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getRelations(userId, type, start, end);
	}

	public static int getRelationsCount(long userId, int type)
		throws com.liferay.portal.SystemException {
		return _service.getRelationsCount(userId, type);
	}

	public static boolean hasRelation(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return _service.hasRelation(userId1, userId2, type);
	}

	public static boolean isRelatable(long userId1, long userId2, int type)
		throws com.liferay.portal.SystemException {
		return _service.isRelatable(userId1, userId2, type);
	}

	public static SocialRelationLocalService getService() {
		return _service;
	}

	public void setService(SocialRelationLocalService service) {
		_service = service;
	}

	private static SocialRelationLocalService _service;
}