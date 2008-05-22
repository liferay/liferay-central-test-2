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
 * <p>
 * <code>com.liferay.portlet.social.service.SocialActivityLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.SocialActivityLocalService
 * @see com.liferay.portlet.social.service.SocialActivityLocalServiceFactory
 *
 */
public class SocialActivityLocalServiceUtil {
	public static com.liferay.portlet.social.model.SocialActivity addSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.addSocialActivity(socialActivity);
	}

	public static void deleteSocialActivity(long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		socialActivityLocalService.deleteSocialActivity(activityId);
	}

	public static void deleteSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		socialActivityLocalService.deleteSocialActivity(socialActivity);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.dynamicQuery(queryInitializer, start,
			end);
	}

	public static com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.updateSocialActivity(socialActivity);
	}

	public static com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.addActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.addActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.addUniqueActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public static com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.addUniqueActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	public static void deleteActivities(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		socialActivityLocalService.deleteActivities(className, classPK);
	}

	public static void deleteActivities(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		socialActivityLocalService.deleteActivities(classNameId, classPK);
	}

	public static void deleteActivity(long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		socialActivityLocalService.deleteActivity(activityId);
	}

	public static void deleteUserActivities(long userId)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		socialActivityLocalService.deleteUserActivities(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivities(className, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivities(classNameId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, java.lang.String className, long classPK,
		int start, int end) throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivities(mirrorActivityId,
			className, classPK, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivities(mirrorActivityId,
			classNameId, classPK, start, end);
	}

	public static int getActivitiesCount(java.lang.String className)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivitiesCount(className);
	}

	public static int getActivitiesCount(long classNameId)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivitiesCount(classNameId);
	}

	public static int getActivitiesCount(long mirrorActivityId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			className, classPK);
	}

	public static int getActivitiesCount(long mirrorActivityId,
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			classNameId, classPK);
	}

	public static com.liferay.portlet.social.model.SocialActivity getActivity(
		long activityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getActivity(activityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getGroupActivities(groupId, start, end);
	}

	public static int getGroupActivitiesCount(long groupId)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getGroupActivitiesCount(groupId);
	}

	public static com.liferay.portlet.social.model.SocialActivity getMirrorActivity(
		long mirrorActivityId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getMirrorActivity(mirrorActivityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getOrganizationActivities(organizationId,
			start, end);
	}

	public static int getOrganizationActivitiesCount(long organizationId)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getOrganizationActivitiesCount(organizationId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getRelationActivities(userId, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int type, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getRelationActivities(userId, type,
			start, end);
	}

	public static int getRelationActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getRelationActivitiesCount(userId);
	}

	public static int getRelationActivitiesCount(long userId, int type)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getRelationActivitiesCount(userId,
			type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserActivities(
		long userId, int start, int end)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getUserActivities(userId, start, end);
	}

	public static int getUserActivitiesCount(long userId)
		throws com.liferay.portal.SystemException {
		SocialActivityLocalService socialActivityLocalService = SocialActivityLocalServiceFactory.getService();

		return socialActivityLocalService.getUserActivitiesCount(userId);
	}
}