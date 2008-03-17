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
		long announcementFlagId) {
		return getPersistence().create(announcementFlagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag remove(
		long announcementFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().remove(announcementFlagId);
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
		long announcementFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByPrimaryKey(announcementFlagId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag fetchByPrimaryKey(
		long announcementFlagId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(announcementFlagId);
	}

	public static java.util.List findByAnnouncementId(long announcementId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByAnnouncementId(announcementId);
	}

	public static java.util.List findByAnnouncementId(long announcementId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByAnnouncementId(announcementId, begin, end);
	}

	public static java.util.List findByAnnouncementId(long announcementId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByAnnouncementId(announcementId, begin, end, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByAnnouncementId_First(
		long announcementId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByAnnouncementId_First(announcementId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByAnnouncementId_Last(
		long announcementId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByAnnouncementId_Last(announcementId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag[] findByAnnouncementId_PrevAndNext(
		long announcementFlagId, long announcementId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence()
				   .findByAnnouncementId_PrevAndNext(announcementFlagId,
			announcementId, obc);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag findByU_A_F(
		long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		return getPersistence().findByU_A_F(userId, announcementId, flag);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementFlag fetchByU_A_F(
		long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByU_A_F(userId, announcementId, flag);
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

	public static void removeByAnnouncementId(long announcementId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByAnnouncementId(announcementId);
	}

	public static void removeByU_A_F(long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException {
		getPersistence().removeByU_A_F(userId, announcementId, flag);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByAnnouncementId(long announcementId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByAnnouncementId(announcementId);
	}

	public static int countByU_A_F(long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByU_A_F(userId, announcementId, flag);
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