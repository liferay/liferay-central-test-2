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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.journal.model.JournalContentSearch;

/**
 * <a href="JournalContentSearchPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearchPersistenceImpl
 * @see       JournalContentSearchUtil
 * @generated
 */
public interface JournalContentSearchPersistence extends BasePersistence<JournalContentSearch> {
	public void cacheResult(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch);

	public void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> journalContentSearchs);

	public com.liferay.portlet.journal.model.JournalContentSearch create(
		long contentSearchId);

	public com.liferay.portlet.journal.model.JournalContentSearch remove(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch updateImpl(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByPrimaryKey(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch fetchByPrimaryKey(
		long contentSearchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByArticleId(
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByArticleId_PrevAndNext(
		long contentSearchId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_First(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_Last(
		long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_A_First(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_A_Last(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_A_PrevAndNext(
		long contentSearchId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_L_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_A(
		long groupId, boolean privateLayout, java.lang.String articleId,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_A_First(
		long groupId, boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_A_Last(
		long groupId, boolean privateLayout, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_A_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findByG_P_L_P(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_First(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_Last(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch[] findByG_P_L_P_PrevAndNext(
		long contentSearchId, long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch findByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public com.liferay.portlet.journal.model.JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch fetchByG_P_L_P_A(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.journal.model.JournalContentSearch> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.journal.NoSuchContentSearchException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P_L(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P_A(long groupId, boolean privateLayout,
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P_L_P(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P_L_P_A(long groupId, boolean privateLayout,
		long layoutId, java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}