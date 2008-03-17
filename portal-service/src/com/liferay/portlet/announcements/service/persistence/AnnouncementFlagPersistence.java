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
 * <a href="AnnouncementFlagPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface AnnouncementFlagPersistence {
	public com.liferay.portlet.announcements.model.AnnouncementFlag create(
		long announcementFlagId);

	public com.liferay.portlet.announcements.model.AnnouncementFlag remove(
		long announcementFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag remove(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag update(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag update(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag updateImpl(
		com.liferay.portlet.announcements.model.AnnouncementFlag announcementFlag,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag findByPrimaryKey(
		long announcementFlagId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag fetchByPrimaryKey(
		long announcementFlagId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findByAnnouncementId(
		long announcementId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findByAnnouncementId(
		long announcementId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findByAnnouncementId(
		long announcementId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag findByAnnouncementId_First(
		long announcementId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag findByAnnouncementId_Last(
		long announcementId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag[] findByAnnouncementId_PrevAndNext(
		long announcementFlagId, long announcementId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag findByU_A_F(
		long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public com.liferay.portlet.announcements.model.AnnouncementFlag fetchByU_A_F(
		long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementFlag> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByAnnouncementId(long announcementId)
		throws com.liferay.portal.SystemException;

	public void removeByU_A_F(long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.announcements.NoSuchAnnouncementFlagException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByAnnouncementId(long announcementId)
		throws com.liferay.portal.SystemException;

	public int countByU_A_F(long userId, long announcementId, int flag)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}