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
 * <a href="AnnouncementEntryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementEntryUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry remove(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry remove(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(announcementEntry);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry update(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementEntry);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry update(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementEntry, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementEntry announcementEntry,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(announcementEntry, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry fetchByPrimaryKey(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByUuid(
		java.lang.String uuid, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByUuid(
		java.lang.String uuid, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry[] findByUuid_PrevAndNext(
		long entryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByUuid_PrevAndNext(entryId, uuid, obc);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByC_C(
		long classNameId, long classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByC_C(
		long classNameId, long classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByC_C_First(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByC_C_Last(classNameId, classPK, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry[] findByC_C_PrevAndNext(
		long entryId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(entryId, classNameId, classPK, obc);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_A(classNameId, classPK, alert);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_A(classNameId, classPK, alert, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByC_C_A(
		long classNameId, long classPK, boolean alert, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_C_A(classNameId, classPK, alert, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByC_C_A_First(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence()
				   .findByC_C_A_First(classNameId, classPK, alert, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByC_C_A_Last(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence()
				   .findByC_C_A_Last(classNameId, classPK, alert, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry[] findByC_C_A_PrevAndNext(
		long entryId, long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence()
				   .findByC_C_A_PrevAndNext(entryId, classNameId, classPK,
			alert, obc);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByUserId(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findByUserId(
		long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntry[] findByUserId_PrevAndNext(
		long entryId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementEntryException {
		return getPersistence().findByUserId_PrevAndNext(entryId, userId, obc);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementEntry> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
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

	public static AnnouncementEntryPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(AnnouncementEntryPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementEntryUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementEntryUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementEntryUtil.class.getName();
	private static AnnouncementEntryUtil _util;
	private AnnouncementEntryPersistence _persistence;
}