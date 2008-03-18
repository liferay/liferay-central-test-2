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
 * <a href="AnnouncementFlagUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementFlagUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementFlag create(
		long flagId) {
		return getPersistence().create(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag remove(
		long flagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().remove(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag remove(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(announcementFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag update(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementFlag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag update(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(announcementFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(announcementFlag, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByPrimaryKey(
		long flagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByPrimaryKey(flagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag fetchByPrimaryKey(
		long flagId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(flagId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findByEntryId(
		long entryId) throws com.liferay.portal.SystemException {
		return getPersistence().findByEntryId(entryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findByEntryId(
		long entryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEntryId(entryId, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findByEntryId(
		long entryId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByEntryId(entryId, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByEntryId_First(
		long entryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByEntryId_First(entryId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByEntryId_Last(
		long entryId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByEntryId_Last(entryId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag[] findByEntryId_PrevAndNext(
		long flagId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByEntryId_PrevAndNext(flagId, entryId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByU_E_F(
		long userId, long entryId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByU_E_F(userId, entryId, flag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag fetchByU_E_F(
		long userId, long entryId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_E_F(userId, entryId, flag);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findAll(
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByEntryId(long entryId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByEntryId(entryId);
	}

	public static void removeByU_E_F(long userId, long entryId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		getPersistence().removeByU_E_F(userId, entryId, flag);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByEntryId(long entryId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByEntryId(entryId);
	}

	public static int countByU_E_F(long userId, long entryId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_E_F(userId, entryId, flag);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static AnnouncementFlagPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(AnnouncementFlagPersistence persistence) {
		_persistence = persistence;
	}

	private static AnnouncementFlagUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementFlagUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementFlagUtil.class.getName();
	private static AnnouncementFlagUtil _util;
	private AnnouncementFlagPersistence _persistence;
}