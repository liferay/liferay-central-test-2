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

package com.liferay.portlet.announcements.service.persistence;

/**
 * <a href="AnnouncementUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementUtil {
	public static com.liferay.portlet.announcements.model.Announcement create(
		long announcementId) {
		return getPersistence().create(announcementId);
	}

	public static com.liferay.portlet.announcements.model.Announcement remove(
		long announcementId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().remove(announcementId);
	}

	public static com.liferay.portlet.announcements.model.Announcement remove(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(announcement);
	}

	public static com.liferay.portlet.announcements.model.Announcement update(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(announcement);
	}

	public static com.liferay.portlet.announcements.model.Announcement update(
		com.liferay.portlet.announcements.model.Announcement announcement,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(announcement, merge);
	}

	public static com.liferay.portlet.announcements.model.Announcement updateImpl(
		com.liferay.portlet.announcements.model.Announcement announcement,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(announcement, merge);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByPrimaryKey(
		long announcementId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByPrimaryKey(announcementId);
	}

	public static com.liferay.portlet.announcements.model.Announcement fetchByPrimaryKey(
		long announcementId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(announcementId);
	}

	public static java.util.List findByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List findByUuid(java.lang.String uuid, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, begin, end);
	}

	public static java.util.List findByUuid(java.lang.String uuid, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement[] findByUuid_PrevAndNext(
		long announcementId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByUuid_PrevAndNext(announcementId, uuid, obc);
	}

	public static java.util.List findByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List findByC_C(long classNameId, long classPK,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, begin, end);
	}

	public static java.util.List findByC_C(long classNameId, long classPK,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement[] findByC_C_PrevAndNext(
		long announcementId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence()
				   .findByC_C_PrevAndNext(announcementId, classNameId, classPK,
			obc);
	}

	public static java.util.List findByC_C_A(long classNameId, long classPK,
		boolean alert) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_A(classNameId, classPK, alert);
	}

	public static java.util.List findByC_C_A(long classNameId, long classPK,
		boolean alert, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_A(classNameId, classPK, alert, begin, end);
	}

	public static java.util.List findByC_C_A(long classNameId, long classPK,
		boolean alert, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_A(classNameId, classPK, alert, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByC_C_A_First(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence()
				   .findByC_C_A_First(classNameId, classPK, alert, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByC_C_A_Last(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence()
				   .findByC_C_A_Last(classNameId, classPK, alert, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement[] findByC_C_A_PrevAndNext(
		long announcementId, long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence()
				   .findByC_C_A_PrevAndNext(announcementId, classNameId,
			classPK, alert, obc);
	}

	public static java.util.List findByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.Announcement[] findByUserId_PrevAndNext(
		long announcementId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException {
		return getPersistence()
				   .findByUserId_PrevAndNext(announcementId, userId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByC_C_A(long classNameId, long classPK,
		boolean alert) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_A(classNameId, classPK, alert);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_A(classNameId, classPK, alert);
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(AnnouncementPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementUtil.class.getName();
	private static AnnouncementUtil _util;
	private AnnouncementPersistence _persistence;
}