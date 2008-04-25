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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.NoSuchActivityException;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.service.base.SocialActivityLocalServiceBaseImpl;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="SocialActivityLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialActivityLocalServiceImpl
	extends SocialActivityLocalServiceBaseImpl {

	public SocialActivity addActivity(
			long userId, long groupId, String className, long classPK,
			String type, String extraData, long receiverUserId)
		throws PortalException, SystemException {

		return addActivity(
			userId, groupId, new Date(), className, classPK, type, extraData,
			receiverUserId);
	}

	public SocialActivity addActivity(
			long userId, long groupId, Date createDate, String className,
			long classPK, String type, String extraData, long receiverUserId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		long activityId = counterLocalService.increment(
			SocialActivity.class.getName());

		SocialActivity activity = socialActivityPersistence.create(
			activityId);

		activity.setGroupId(groupId);
		activity.setCompanyId(user.getCompanyId());
		activity.setUserId(user.getUserId());
		activity.setCreateDate(createDate);
		activity.setMirrorActivityId(0);
		activity.setClassNameId(classNameId);
		activity.setClassPK(classPK);
		activity.setType(type);
		activity.setExtraData(extraData);
		activity.setReceiverUserId(receiverUserId);

		socialActivityPersistence.update(activity, false);

		if ((receiverUserId > 0) && (userId != receiverUserId)) {
			long mirrorActivityId = counterLocalService.increment(
				SocialActivity.class.getName());

			SocialActivity mirrorActivity = socialActivityPersistence.create(
				mirrorActivityId);

			mirrorActivity.setGroupId(groupId);
			mirrorActivity.setCompanyId(user.getCompanyId());
			mirrorActivity.setUserId(receiverUserId);
			mirrorActivity.setCreateDate(createDate);
			mirrorActivity.setMirrorActivityId(activityId);
			mirrorActivity.setClassNameId(classNameId);
			mirrorActivity.setClassPK(classPK);
			mirrorActivity.setType(type);
			mirrorActivity.setExtraData(extraData);
			mirrorActivity.setReceiverUserId(user.getUserId());

			socialActivityPersistence.update(mirrorActivity, false);
		}

		return activity;
	}

	public void deleteActivities(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteActivities(classNameId, classPK);
	}

	public void deleteActivities(long classNameId, long classPK)
		throws SystemException {

		socialActivityPersistence.removeByC_C(classNameId, classPK);
	}

	public void deleteActivity(long activityId)
		throws PortalException, SystemException {

		SocialActivity activity = socialActivityPersistence.findByPrimaryKey(
			activityId);

		try {
			socialActivityPersistence.removeByMirrorActivityId(activityId);
		}
		catch (NoSuchActivityException nsae) {
		}

		socialActivityPersistence.remove(activity);
	}

	public void deleteUserActivities(long userId) throws SystemException {
		List<SocialActivity> activities =
			socialActivityPersistence.findByUserId(
				userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity activity : activities) {
			socialActivityPersistence.remove(activity);
		}

		activities = socialActivityPersistence.findByReceiverUserId(
			userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (SocialActivity activity : activities) {
			socialActivityPersistence.remove(activity);
		}
	}

	public List<SocialActivity> getActivities(
			String className, int begin, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivities(classNameId, begin, end);
	}

	public List<SocialActivity> getActivities(
			long classNameId, int begin, int end)
		throws SystemException {

		return socialActivityPersistence.findByClassNameId(
			classNameId, begin, end);
	}

	public List<SocialActivity> getActivities(
			long mirrorActivityId, String className, long classPK, int begin,
			int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivities(mirrorActivityId, classNameId, classPK, begin, end);
	}

	public List<SocialActivity> getActivities(
			long mirrorActivityId, long classNameId, long classPK, int begin,
			int end)
		throws SystemException {

		return socialActivityPersistence.findByM_C_C(
			mirrorActivityId, classNameId, classPK, begin, end);
	}

	public int getActivitiesCount(String className) throws SystemException {
		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(classNameId);
	}

	public int getActivitiesCount(long classNameId) throws SystemException {
		return socialActivityPersistence.countByClassNameId(classNameId);
	}

	public int getActivitiesCount(
			long mirrorActivityId, String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(mirrorActivityId, classNameId, classPK);
	}

	public int getActivitiesCount(
			long mirrorActivityId, long classNameId, long classPK)
		throws SystemException {

		return socialActivityPersistence.countByM_C_C(
			mirrorActivityId, classNameId, classPK);
	}

	public SocialActivity getActivity(long activityId)
		throws PortalException, SystemException {

		return socialActivityPersistence.findByPrimaryKey(activityId);
	}

	public SocialActivity getMirrorActivity(long mirrorActivityId)
		throws PortalException, SystemException {

		return socialActivityPersistence.findByMirrorActivityId(
			mirrorActivityId);
	}

	public List<SocialActivity> getRelationActivities(
			long userId, int type, int begin, int end)
		throws SystemException {

		return socialActivityFinder.findByRelationType(
			userId, type, begin, end);
	}

	public int getRelationActivitiesCount(long userId, int type)
		throws SystemException {

		return socialActivityFinder.countByRelationType(userId, type);
	}

	public List<SocialActivity> getUserActivities(
			long userId, int begin, int end)
		throws SystemException {

		return socialActivityPersistence.findByUserId(userId, begin, end);
	}

	public int getUserActivitiesCount(long userId) throws SystemException {
		return socialActivityPersistence.countByUserId(userId);
	}

}