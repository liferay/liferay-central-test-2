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
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.service.base.SocialActivityLocalServiceBaseImpl;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		long activityId = counterLocalService.increment(
			SocialActivity.class.getName());

		SocialActivity activity = socialActivityPersistence.create(
			activityId);

		activity.setGroupId(groupId);
		activity.setCompanyId(user.getCompanyId());
		activity.setUserId(user.getUserId());
		activity.setCreateDate(new Date());
		activity.setClassNameId(classNameId);
		activity.setClassPK(classPK);
		activity.setType(type);
		activity.setExtraData(extraData);
		activity.setReceiverUserId(receiverUserId);

		socialActivityPersistence.update(activity, false);

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

		socialActivityPersistence.remove(activityId);
	}

	public void deleteUserActivities(long userId) throws SystemException {
		List<SocialActivity> activities = socialActivityFinder.findByU_R(
			userId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

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
			String className, long classPK, int begin, int end)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivities(classNameId, classPK, begin, end);
	}

	public List<SocialActivity> getActivities(
			long classNameId, long classPK, int begin, int end)
		throws SystemException {

		return socialActivityPersistence.findByC_C(
			classNameId, classPK, begin, end);
	}

	public int getActivitiesCount(String className) throws SystemException {
		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(classNameId);
	}

	public int getActivitiesCount(long classNameId) throws SystemException {
		return socialActivityPersistence.countByClassNameId(classNameId);
	}

	public int getActivitiesCount(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getActivitiesCount(classNameId, classPK);
	}

	public int getActivitiesCount(long classNameId, long classPK)
		throws SystemException {

		return socialActivityPersistence.countByC_C(
			classNameId, classPK);
	}

	public List<SocialActivity> getCompanyActivities(
			long companyId, int begin, int end)
		throws SystemException {

		return socialActivityPersistence.findByCompanyId(companyId, begin, end);
	}

	public int getCompanyActivitiesCount(long companyId)
		throws SystemException {

		return socialActivityPersistence.countByCompanyId(companyId);
	}

	public List<SocialActivity> getGroupActivities(
			long groupId, int begin, int end)
		throws SystemException {

		return socialActivityPersistence.findByGroupId(groupId, begin, end);
	}

	public int getGroupActivitiesCount(long groupId) throws SystemException {
		return socialActivityPersistence.countByGroupId(groupId);
	}

	public List<SocialActivity> getRelationActivities(
			long userId, int type, int begin, int end)
		throws SystemException {

		return socialActivityFinder.findByRelationTypeBi(
			userId, type, begin, end);
	}

	public List<SocialActivity> getUserActivities(
			long userId, int begin, int end)
		throws SystemException {

		return socialActivityFinder.findByU_R(userId, userId, begin, end);
	}

	public int getUserActivitiesCount(long userId) throws SystemException {
		return socialActivityFinder.countByU_R(userId, userId);
	}

	private static Log _log =
		LogFactory.getLog(SocialActivityLocalServiceImpl.class);

}