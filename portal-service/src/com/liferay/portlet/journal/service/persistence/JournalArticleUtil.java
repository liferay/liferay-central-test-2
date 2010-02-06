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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;

import com.liferay.portlet.journal.model.JournalArticle;

import java.util.List;

/**
 * <a href="JournalArticleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticlePersistence
 * @see       JournalArticlePersistenceImpl
 * @generated
 */
public class JournalArticleUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static JournalArticle remove(JournalArticle journalArticle)
		throws SystemException {
		return getPersistence().remove(journalArticle);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static JournalArticle update(JournalArticle journalArticle,
		boolean merge) throws SystemException {
		return getPersistence().update(journalArticle, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.journal.model.JournalArticle journalArticle) {
		getPersistence().cacheResult(journalArticle);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.journal.model.JournalArticle> journalArticles) {
		getPersistence().cacheResult(journalArticles);
	}

	public static com.liferay.portlet.journal.model.JournalArticle create(
		long id) {
		return getPersistence().create(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle remove(
		long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().remove(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle updateImpl(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(journalArticle, merge);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByPrimaryKey(
		long id)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByPrimaryKey(id);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchByPrimaryKey(
		long id) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(id);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid) throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUuid(uuid, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUuid_First(uuid, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUuid_Last(uuid, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByUuid_PrevAndNext(
		long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUuid_PrevAndNext(id, uuid, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByGroupId_PrevAndNext(
		long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByGroupId_PrevAndNext(id, groupId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByCompanyId_PrevAndNext(id, companyId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId) throws com.liferay.portal.SystemException {
		return getPersistence().findBySmallImageId(smallImageId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySmallImageId(smallImageId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findBySmallImageId(
		long smallImageId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findBySmallImageId(smallImageId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findBySmallImageId_First(
		long smallImageId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findBySmallImageId_First(smallImageId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findBySmallImageId_Last(
		long smallImageId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findBySmallImageId_Last(smallImageId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findBySmallImageId_PrevAndNext(
		long id, long smallImageId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findBySmallImageId_PrevAndNext(id, smallImageId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_S(
		long resourcePrimKey, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByR_S(resourcePrimKey, status);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_S(
		long resourcePrimKey, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByR_S(resourcePrimKey, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByR_S(
		long resourcePrimKey, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByR_S(resourcePrimKey, status, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByR_S_First(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByR_S_First(resourcePrimKey, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByR_S_Last(
		long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByR_S_Last(resourcePrimKey, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByR_S_PrevAndNext(
		long id, long resourcePrimKey, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByR_S_PrevAndNext(id, resourcePrimKey, status, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A(groupId, articleId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A(groupId, articleId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A(
		long groupId, java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A(groupId, articleId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_First(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_A_First(groupId, articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_Last(
		long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_A_Last(groupId, articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_A_PrevAndNext(
		long id, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_PrevAndNext(id, groupId, articleId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, structureId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, structureId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_S(
		long groupId, java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_S(groupId, structureId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_S_First(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_S_First(groupId, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_S_Last(
		long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_S_Last(groupId, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_S_PrevAndNext(
		long id, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_S_PrevAndNext(id, groupId, structureId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_T(groupId, templateId);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_T(groupId, templateId, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_T(
		long groupId, java.lang.String templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_T(groupId, templateId, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_T_First(
		long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_T_First(groupId, templateId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_T_Last(
		long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_T_Last(groupId, templateId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_T_PrevAndNext(
		long id, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_T_PrevAndNext(id, groupId, templateId, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_UT(groupId, urlTitle);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_UT(groupId, urlTitle, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT(
		long groupId, java.lang.String urlTitle, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_UT(groupId, urlTitle, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_First(
		long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_UT_First(groupId, urlTitle, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_Last(
		long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_UT_Last(groupId, urlTitle, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_UT_PrevAndNext(
		long id, long groupId, java.lang.String urlTitle,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_PrevAndNext(id, groupId, urlTitle, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_S(
		long companyId, int status) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, status);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_S(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByC_S(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, status, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_S_First(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_S_First(companyId, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_S_Last(
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_S_Last(companyId, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByC_S_PrevAndNext(
		long id, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_S_PrevAndNext(id, companyId, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_A_V(groupId, articleId, version);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchByG_A_V(
		long groupId, java.lang.String articleId, double version)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_A_V(groupId, articleId, version);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchByG_A_V(
		long groupId, java.lang.String articleId, double version,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_A_V(groupId, articleId, version, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_S(
		long groupId, java.lang.String articleId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_A_S(groupId, articleId, status);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_S(
		long groupId, java.lang.String articleId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_A_S(groupId, articleId, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_A_S(
		long groupId, java.lang.String articleId, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_A_S(groupId, articleId, status, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_S_First(
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_S_First(groupId, articleId, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_A_S_Last(
		long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_A_S_Last(groupId, articleId, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_A_S_PrevAndNext(
		long id, long groupId, java.lang.String articleId, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_A_S_PrevAndNext(id, groupId, articleId, status, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_S(
		long groupId, java.lang.String urlTitle, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_UT_S(groupId, urlTitle, status);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_S(
		long groupId, java.lang.String urlTitle, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_UT_S(groupId, urlTitle, status, start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findByG_UT_S(
		long groupId, java.lang.String urlTitle, int status, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_UT_S(groupId, urlTitle, status, start, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_S_First(
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_S_First(groupId, urlTitle, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByG_UT_S_Last(
		long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByG_UT_S_Last(groupId, urlTitle, status, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByG_UT_S_PrevAndNext(
		long id, long groupId, java.lang.String urlTitle, int status,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence()
				   .findByG_UT_S_PrevAndNext(id, groupId, urlTitle, status, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalArticle> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	public static void removeByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		getPersistence().removeByUUID_G(uuid, groupId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeBySmallImageId(smallImageId);
	}

	public static void removeByR_S(long resourcePrimKey, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByR_S(resourcePrimKey, status);
	}

	public static void removeByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_A(groupId, articleId);
	}

	public static void removeByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_S(groupId, structureId);
	}

	public static void removeByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_T(groupId, templateId);
	}

	public static void removeByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_UT(groupId, urlTitle);
	}

	public static void removeByC_S(long companyId, int status)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_S(companyId, status);
	}

	public static void removeByG_A_V(long groupId, java.lang.String articleId,
		double version)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.journal.NoSuchArticleException {
		getPersistence().removeByG_A_V(groupId, articleId, version);
	}

	public static void removeByG_A_S(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_A_S(groupId, articleId, status);
	}

	public static void removeByG_UT_S(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_UT_S(groupId, urlTitle, status);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countBySmallImageId(long smallImageId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countBySmallImageId(smallImageId);
	}

	public static int countByR_S(long resourcePrimKey, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByR_S(resourcePrimKey, status);
	}

	public static int countByG_A(long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_A(groupId, articleId);
	}

	public static int countByG_S(long groupId, java.lang.String structureId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_S(groupId, structureId);
	}

	public static int countByG_T(long groupId, java.lang.String templateId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_T(groupId, templateId);
	}

	public static int countByG_UT(long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_UT(groupId, urlTitle);
	}

	public static int countByC_S(long companyId, int status)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, status);
	}

	public static int countByG_A_V(long groupId, java.lang.String articleId,
		double version) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_A_V(groupId, articleId, version);
	}

	public static int countByG_A_S(long groupId, java.lang.String articleId,
		int status) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_A_S(groupId, articleId, status);
	}

	public static int countByG_UT_S(long groupId, java.lang.String urlTitle,
		int status) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_UT_S(groupId, urlTitle, status);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static JournalArticlePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(JournalArticlePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalArticlePersistence _persistence;
}