/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

/**
 * <a href="JournalArticlePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface JournalArticlePersistence {
	public com.liferay.portlet.journal.model.JournalArticle create(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK);

	public com.liferay.portlet.journal.model.JournalArticle remove(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle remove(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK)
		throws com.liferay.portal.SystemException;

	public java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByCompanyId(long companyId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByCompanyId(long companyId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle[] findByCompanyId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByGroupId(long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public java.util.List findByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_A(long companyId, long groupId,
		java.lang.String articleId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_A(long companyId, long groupId,
		java.lang.String articleId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_A_First(
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_A_Last(
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_G_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_S_First(
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_S_Last(
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_G_S_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public java.util.List findByC_G_T(long companyId, long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_T(long companyId, long groupId,
		java.lang.String templateId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_T(long companyId, long groupId,
		java.lang.String templateId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_T_First(
		long companyId, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_T_Last(
		long companyId, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_G_T_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public java.util.List findByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_A_A_First(
		long companyId, long groupId, java.lang.String articleId,
		boolean approved, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle findByC_G_A_A_Last(
		long companyId, long groupId, java.lang.String articleId,
		boolean approved, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public com.liferay.portlet.journal.model.JournalArticle[] findByC_G_A_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String articleId,
		boolean approved, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List findAll() throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException;

	public void removeByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException;

	public void removeByC_G_T(long companyId, long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException;

	public void removeByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException;

	public int countByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException;

	public int countByC_G_T(long companyId, long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException;

	public int countByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}