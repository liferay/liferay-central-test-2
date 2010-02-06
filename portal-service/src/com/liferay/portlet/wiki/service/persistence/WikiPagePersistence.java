/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.wiki.model.WikiPage;

/**
 * <a href="WikiPagePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPagePersistenceImpl
 * @see       WikiPageUtil
 * @generated
 */
public interface WikiPagePersistence extends BasePersistence<WikiPage> {
	public void cacheResult(com.liferay.portlet.wiki.model.WikiPage wikiPage);

	public void cacheResult(
		java.util.List<com.liferay.portlet.wiki.model.WikiPage> wikiPages);

	public com.liferay.portlet.wiki.model.WikiPage create(long pageId);

	public com.liferay.portlet.wiki.model.WikiPage remove(long pageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage updateImpl(
		com.liferay.portlet.wiki.model.WikiPage wikiPage, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByPrimaryKey(long pageId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage fetchByPrimaryKey(
		long pageId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByUuid_PrevAndNext(
		long pageId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByNodeId(
		long nodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByNodeId_First(
		long nodeId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByNodeId_Last(
		long nodeId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByNodeId_PrevAndNext(
		long pageId, long nodeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByFormat(
		java.lang.String format, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByFormat_First(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByFormat_Last(
		java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByFormat_PrevAndNext(
		long pageId, java.lang.String format,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T(
		long nodeId, java.lang.String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_T_First(
		long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_T_Last(long nodeId,
		java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByN_T_PrevAndNext(
		long pageId, long nodeId, java.lang.String title,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H(
		long nodeId, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_H_First(
		long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_H_Last(long nodeId,
		boolean head, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByN_H_PrevAndNext(
		long pageId, long nodeId, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_P(
		long nodeId, java.lang.String parentTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_P_First(
		long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_P_Last(long nodeId,
		java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByN_P_PrevAndNext(
		long pageId, long nodeId, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_R(
		long nodeId, java.lang.String redirectTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_R_First(
		long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_R_Last(long nodeId,
		java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByN_R_PrevAndNext(
		long pageId, long nodeId, java.lang.String redirectTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_T_V(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(long nodeId,
		java.lang.String title, double version)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage fetchByN_T_V(long nodeId,
		java.lang.String title, double version, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_T_H(
		long nodeId, java.lang.String title, boolean head, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_T_H_First(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_T_H_Last(
		long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByN_T_H_PrevAndNext(
		long pageId, long nodeId, java.lang.String title, boolean head,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findByN_H_P(
		long nodeId, boolean head, java.lang.String parentTitle, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_H_P_First(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage findByN_H_P_Last(
		long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public com.liferay.portlet.wiki.model.WikiPage[] findByN_H_P_PrevAndNext(
		long pageId, long nodeId, boolean head, java.lang.String parentTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.wiki.model.WikiPage> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public void removeByNodeId(long nodeId)
		throws com.liferay.portal.SystemException;

	public void removeByFormat(java.lang.String format)
		throws com.liferay.portal.SystemException;

	public void removeByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException;

	public void removeByN_H(long nodeId, boolean head)
		throws com.liferay.portal.SystemException;

	public void removeByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException;

	public void removeByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.SystemException;

	public void removeByN_T_V(long nodeId, java.lang.String title,
		double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.wiki.NoSuchPageException;

	public void removeByN_T_H(long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException;

	public void removeByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException;

	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByNodeId(long nodeId)
		throws com.liferay.portal.SystemException;

	public int countByFormat(java.lang.String format)
		throws com.liferay.portal.SystemException;

	public int countByN_T(long nodeId, java.lang.String title)
		throws com.liferay.portal.SystemException;

	public int countByN_H(long nodeId, boolean head)
		throws com.liferay.portal.SystemException;

	public int countByN_P(long nodeId, java.lang.String parentTitle)
		throws com.liferay.portal.SystemException;

	public int countByN_R(long nodeId, java.lang.String redirectTitle)
		throws com.liferay.portal.SystemException;

	public int countByN_T_V(long nodeId, java.lang.String title, double version)
		throws com.liferay.portal.SystemException;

	public int countByN_T_H(long nodeId, java.lang.String title, boolean head)
		throws com.liferay.portal.SystemException;

	public int countByN_H_P(long nodeId, boolean head,
		java.lang.String parentTitle) throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}