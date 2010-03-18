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
 * <a href="SocialActivityLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link SocialActivityLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityLocalService
 * @generated
 */
public class SocialActivityLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialActivity addSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSocialActivity(socialActivity);
	}

	public static com.liferay.portlet.social.model.SocialActivity createSocialActivity(
		long activityId) {
		return getService().createSocialActivity(activityId);
	}

	public static void deleteSocialActivity(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialActivity(activityId);
	}

	public static void deleteSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteSocialActivity(socialActivity);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.social.model.SocialActivity getSocialActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialActivity(activityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getSocialActivities(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialActivities(start, end);
	}

	public static int getSocialActivitiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSocialActivitiesCount();
	}

	public static com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialActivity(socialActivity);
	}

	public static com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSocialActivity(socialActivity, merge);
	}

	public static com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addActivity(userId, groupId, createDate, className,
			classPK, type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addActivity(userId, groupId, className, classPK, type,
			extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUniqueActivity(userId, groupId, createDate, className,
			classPK, type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addUniqueActivity(userId, groupId, className, classPK,
			type, extraData, receiverUserId);
	}

	public static void deleteActivities(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteActivities(classNameId, classPK);
	}

	public static void deleteActivities(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteActivities(className, classPK);
	}

	public static void deleteActivity(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteActivity(activityId);
	}

	public static void deleteActivity(
		com.liferay.portlet.social.model.SocialActivity activity)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteActivity(activity);
	}

	public static void deleteUserActivities(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteUserActivities(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActivities(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getActivities(mirrorActivityId, classNameId, classPK,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, java.lang.String className, long classPK,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getActivities(mirrorActivityId, className, classPK, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActivities(className, start, end);
	}

	public static int getActivitiesCount(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActivitiesCount(classNameId);
	}

	public static int getActivitiesCount(long mirrorActivityId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getActivitiesCount(mirrorActivityId, classNameId, classPK);
	}

	public static int getActivitiesCount(long mirrorActivityId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getActivitiesCount(mirrorActivityId, className, classPK);
	}

	public static int getActivitiesCount(java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getActivitiesCount(className);
	}

	public static com.liferay.portlet.social.model.SocialActivity getActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getActivity(activityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupActivities(groupId, start, end);
	}

	public static int getGroupActivitiesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupActivitiesCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupUsersActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUsersActivities(groupId, start, end);
	}

	public static int getGroupUsersActivitiesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupUsersActivitiesCount(groupId);
	}

	public static com.liferay.portlet.social.model.SocialActivity getMirrorActivity(
		long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMirrorActivity(mirrorActivityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationActivities(organizationId, start, end);
	}

	public static int getOrganizationActivitiesCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationActivitiesCount(organizationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationUsersActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getOrganizationUsersActivities(organizationId, start, end);
	}

	public static int getOrganizationUsersActivitiesCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationUsersActivitiesCount(organizationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelationActivities(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelationActivities(userId, type, start, end);
	}

	public static int getRelationActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelationActivitiesCount(userId);
	}

	public static int getRelationActivitiesCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRelationActivitiesCount(userId, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserActivities(userId, start, end);
	}

	public static int getUserActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserActivitiesCount(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupsActivities(userId, start, end);
	}

	public static int getUserGroupsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupsActivitiesCount(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsAndOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getUserGroupsAndOrganizationsActivities(userId, start, end);
	}

	public static int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupsAndOrganizationsActivitiesCount(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserOrganizationsActivities(userId, start, end);
	}

	public static int getUserOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserOrganizationsActivitiesCount(userId);
	}

	public static SocialActivityLocalService getService() {
		if (_service == null) {
			_service = (SocialActivityLocalService)PortalBeanLocatorUtil.locate(SocialActivityLocalService.class.getName());
		}

		return _service;
	}

	public void setService(SocialActivityLocalService service) {
		_service = service;
	}

	private static SocialActivityLocalService _service;
}