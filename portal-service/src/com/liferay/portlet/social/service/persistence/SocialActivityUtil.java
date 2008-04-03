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

package com.liferay.portlet.social.service.persistence;

/**
 * <a href="SocialActivityUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SocialActivityUtil {
	public static com.liferay.portlet.social.model.SocialActivity create(
		long activityId) {
		return getPersistence().create(activityId);
	}

	public static com.liferay.portlet.social.model.SocialActivity remove(
		long activityId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().remove(activityId);
	}

	public static com.liferay.portlet.social.model.SocialActivity remove(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(socialActivity);
	}

	/**
	 * @deprecated Use <code>update(SocialActivity socialActivity, boolean merge)</code>.
	 */
	public static com.liferay.portlet.social.model.SocialActivity update(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(socialActivity);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        socialActivity the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when socialActivity is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portlet.social.model.SocialActivity update(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(socialActivity, merge);
	}

	public static com.liferay.portlet.social.model.SocialActivity updateImpl(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(socialActivity, merge);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByPrimaryKey(
		long activityId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByPrimaryKey(activityId);
	}

	public static com.liferay.portlet.social.model.SocialActivity fetchByPrimaryKey(
		long activityId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(activityId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByGroupId(
		long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity[] findByGroupId_PrevAndNext(
		long activityId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(activityId, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByCompanyId(
		long companyId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByCompanyId(
		long companyId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity[] findByCompanyId_PrevAndNext(
		long activityId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(activityId, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserId(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByUserId(
		long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity[] findByUserId_PrevAndNext(
		long activityId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByUserId_PrevAndNext(activityId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByReceiverUserId(
		long receiverUserId) throws com.liferay.portal.SystemException {
		return getPersistence().findByReceiverUserId(receiverUserId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByReceiverUserId(
		long receiverUserId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByReceiverUserId(receiverUserId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByReceiverUserId(
		long receiverUserId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByReceiverUserId(receiverUserId, begin, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByReceiverUserId_First(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByReceiverUserId_First(receiverUserId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByReceiverUserId_Last(
		long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByReceiverUserId_Last(receiverUserId, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity[] findByReceiverUserId_PrevAndNext(
		long activityId, long receiverUserId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByReceiverUserId_PrevAndNext(activityId,
			receiverUserId, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByC_C(
		long classNameId, long classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findByC_C(
		long classNameId, long classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, begin, end, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.social.model.SocialActivity[] findByC_C_PrevAndNext(
		long activityId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.social.NoSuchActivityException {
		return getPersistence()
				   .findByC_C_PrevAndNext(activityId, classNameId, classPK, obc);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByReceiverUserId(receiverUserId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByReceiverUserId(long receiverUserId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByReceiverUserId(receiverUserId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static SocialActivityPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(SocialActivityPersistence persistence) {
		_persistence = persistence;
	}

	private static SocialActivityUtil _getUtil() {
		if (_util == null) {
			_util = (SocialActivityUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = SocialActivityUtil.class.getName();
	private static SocialActivityUtil _util;
	private SocialActivityPersistence _persistence;
}