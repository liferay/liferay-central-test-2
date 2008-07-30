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
 * <a href="SocialActivityLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.social.service.SocialActivityLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.SocialActivityLocalService
 *
 */
public class SocialActivityLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialActivity addSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		return _service.addSocialActivity(socialActivity);
	}

	public static void deleteSocialActivity(long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteSocialActivity(activityId);
	}

	public static void deleteSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		_service.deleteSocialActivity(socialActivity);
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

	public static com.liferay.portlet.social.model.SocialActivity getSocialActivity(
		long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getSocialActivity(activityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getSocialActivities(
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getSocialActivities(start, end);
	}

	public static int getSocialActivitiesCount()
		throws com.liferay.portal.SystemException {
		return _service.getSocialActivitiesCount();
	}

	public static com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		return _service.updateSocialActivity(socialActivity);
	}

	public static com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addActivity(userId, groupId, className, classPK, type,
			extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addActivity(userId, groupId, createDate, className,
			classPK, type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addUniqueActivity(userId, groupId, className, classPK,
			type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.addUniqueActivity(userId, groupId, createDate,
			className, classPK, type, extraData, receiverUserId);
	}

	public static void deleteActivities(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		_service.deleteActivities(className, classPK);
	}

	public static void deleteActivities(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		_service.deleteActivities(classNameId, classPK);
	}

	public static void deleteActivity(long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_service.deleteActivity(activityId);
	}

	public static void deleteUserActivities(long userId)
		throws com.liferay.portal.SystemException {
		_service.deleteUserActivities(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getActivities(className, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getActivities(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, java.lang.String className, long classPK,
		int start, int end) throws com.liferay.portal.SystemException {
		return _service.getActivities(mirrorActivityId, className, classPK,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.SystemException {
		return _service.getActivities(mirrorActivityId, classNameId, classPK,
			start, end);
	}

	public static int getActivitiesCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		return _service.getActivitiesCount(className);
	}

	public static int getActivitiesCount(long classNameId)
		throws com.liferay.portal.SystemException {
		return _service.getActivitiesCount(classNameId);
	}

	public static int getActivitiesCount(long mirrorActivityId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _service.getActivitiesCount(mirrorActivityId, className, classPK);
	}

	public static int getActivitiesCount(long mirrorActivityId,
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return _service.getActivitiesCount(mirrorActivityId, classNameId,
			classPK);
	}

	public static com.liferay.portlet.social.model.SocialActivity getActivity(
		long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getActivity(activityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getGroupActivities(groupId, start, end);
	}

	public static int getGroupActivitiesCount(long groupId)
		throws com.liferay.portal.SystemException {
		return _service.getGroupActivitiesCount(groupId);
	}

	public static com.liferay.portlet.social.model.SocialActivity getMirrorActivity(
		long mirrorActivityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _service.getMirrorActivity(mirrorActivityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getOrganizationActivities(organizationId, start, end);
	}

	public static int getOrganizationActivitiesCount(long organizationId)
		throws com.liferay.portal.SystemException {
		return _service.getOrganizationActivitiesCount(organizationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getRelationActivities(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getRelationActivities(userId, type, start, end);
	}

	public static int getRelationActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _service.getRelationActivitiesCount(userId);
	}

	public static int getRelationActivitiesCount(long userId, int type)
		throws com.liferay.portal.SystemException {
		return _service.getRelationActivitiesCount(userId, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _service.getUserActivities(userId, start, end);
	}

	public static int getUserActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		return _service.getUserActivitiesCount(userId);
	}

	public static SocialActivityLocalService getService() {
		return _service;
	}

	public void setService(SocialActivityLocalService service) {
		_service = service;
	}

	private static SocialActivityLocalService _service;
}