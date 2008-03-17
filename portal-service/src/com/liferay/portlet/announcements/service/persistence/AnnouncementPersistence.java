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
 * <a href="AnnouncementPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface AnnouncementPersistence {
	public com.liferay.portlet.announcements.model.Announcement create(
		long announcementId);

	public com.liferay.portlet.announcements.model.Announcement remove(
		long announcementId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement remove(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement update(
		com.liferay.portlet.announcements.model.Announcement announcement)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement update(
		com.liferay.portlet.announcements.model.Announcement announcement,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement updateImpl(
		com.liferay.portlet.announcements.model.Announcement announcement,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement findByPrimaryKey(
		long announcementId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement fetchByPrimaryKey(
		long announcementId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByUuid(
		java.lang.String uuid, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByUuid(
		java.lang.String uuid, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement[] findByUuid_PrevAndNext(
		long announcementId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C(
		long classNameId, long classPK, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C(
		long classNameId, long classPK, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement[] findByC_C_PrevAndNext(
		long announcementId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C_A(
		long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C_A(
		long classNameId, long classPK, boolean alert, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByC_C_A(
		long classNameId, long classPK, boolean alert, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement findByC_C_A_First(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement findByC_C_A_Last(
		long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement[] findByC_C_A_PrevAndNext(
		long announcementId, long classNameId, long classPK, boolean alert,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByUserId(
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByUserId(
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findByUserId(
		long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.Announcement findByUserId_First(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement findByUserId_Last(
		long userId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public com.liferay.portlet.announcements.model.Announcement[] findByUserId_PrevAndNext(
		long announcementId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.Announcement> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public void removeByC_C_A(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException;

	public void removeByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countByC_C_A(long classNameId, long classPK, boolean alert)
		throws com.liferay.portal.SystemException;

	public int countByUserId(long userId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}