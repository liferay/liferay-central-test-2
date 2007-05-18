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

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalArticleUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleUtil {
	public static com.liferay.portlet.journal.model.JournalArticle create(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK) {
		return getPersistence().create(journalArticlePK);
	}

	public static com.liferay.portlet.journal.model.JournalArticle remove(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(journalArticlePK));
		}

		com.liferay.portlet.journal.model.JournalArticle journalArticle = getPersistence()
																			  .remove(journalArticlePK);

		if (listener != null) {
			listener.onAfterRemove(journalArticle);
		}

		return journalArticle;
	}

	public static com.liferay.portlet.journal.model.JournalArticle remove(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(journalArticle);
		}

		journalArticle = getPersistence().remove(journalArticle);

		if (listener != null) {
			listener.onAfterRemove(journalArticle);
		}

		return journalArticle;
	}

	public static com.liferay.portlet.journal.model.JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalArticle.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalArticle);
			}
			else {
				listener.onBeforeUpdate(journalArticle);
			}
		}

		journalArticle = getPersistence().update(journalArticle);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalArticle);
			}
			else {
				listener.onAfterUpdate(journalArticle);
			}
		}

		return journalArticle;
	}

	public static com.liferay.portlet.journal.model.JournalArticle update(
		com.liferay.portlet.journal.model.JournalArticle journalArticle,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalArticle.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalArticle);
			}
			else {
				listener.onBeforeUpdate(journalArticle);
			}
		}

		journalArticle = getPersistence().update(journalArticle, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalArticle);
			}
			else {
				listener.onAfterUpdate(journalArticle);
			}
		}

		return journalArticle;
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByPrimaryKey(journalArticlePK);
	}

	public static com.liferay.portlet.journal.model.JournalArticle fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(journalArticlePK);
	}

	public static java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
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
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByCompanyId_PrevAndNext(journalArticlePK,
			companyId, obc);
	}

	public static java.util.List findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(long groupId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
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
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByGroupId_PrevAndNext(journalArticlePK,
			groupId, obc);
	}

	public static java.util.List findByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_A(companyId, groupId, articleId);
	}

	public static java.util.List findByC_G_A(long companyId, long groupId,
		java.lang.String articleId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_A(companyId, groupId, articleId,
			begin, end);
	}

	public static java.util.List findByC_G_A(long companyId, long groupId,
		java.lang.String articleId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_A(companyId, groupId, articleId,
			begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_A_First(
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_A_First(companyId, groupId,
			articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_A_Last(
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_A_Last(companyId, groupId, articleId,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByC_G_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_A_PrevAndNext(journalArticlePK,
			companyId, groupId, articleId, obc);
	}

	public static java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_S(companyId, groupId, structureId);
	}

	public static java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_S(companyId, groupId, structureId,
			begin, end);
	}

	public static java.util.List findByC_G_S(long companyId, long groupId,
		java.lang.String structureId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_S(companyId, groupId, structureId,
			begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_S_First(
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_S_First(companyId, groupId,
			structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_S_Last(
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_S_Last(companyId, groupId,
			structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByC_G_S_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_S_PrevAndNext(journalArticlePK,
			companyId, groupId, structureId, obc);
	}

	public static java.util.List findByC_G_T(long companyId, long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_T(companyId, groupId, templateId);
	}

	public static java.util.List findByC_G_T(long companyId, long groupId,
		java.lang.String templateId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_T(companyId, groupId, templateId,
			begin, end);
	}

	public static java.util.List findByC_G_T(long companyId, long groupId,
		java.lang.String templateId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_T(companyId, groupId, templateId,
			begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_T_First(
		long companyId, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_T_First(companyId, groupId,
			templateId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_T_Last(
		long companyId, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_T_Last(companyId, groupId,
			templateId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByC_G_T_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String templateId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_T_PrevAndNext(journalArticlePK,
			companyId, groupId, templateId, obc);
	}

	public static java.util.List findByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_A_A(companyId, groupId, articleId,
			approved);
	}

	public static java.util.List findByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_A_A(companyId, groupId, articleId,
			approved, begin, end);
	}

	public static java.util.List findByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_A_A(companyId, groupId, articleId,
			approved, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_A_A_First(
		long companyId, long groupId, java.lang.String articleId,
		boolean approved, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_A_A_First(companyId, groupId,
			articleId, approved, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle findByC_G_A_A_Last(
		long companyId, long groupId, java.lang.String articleId,
		boolean approved, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_A_A_Last(companyId, groupId,
			articleId, approved, obc);
	}

	public static com.liferay.portlet.journal.model.JournalArticle[] findByC_G_A_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalArticlePK journalArticlePK,
		long companyId, long groupId, java.lang.String articleId,
		boolean approved, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchArticleException {
		return getPersistence().findByC_G_A_A_PrevAndNext(journalArticlePK,
			companyId, groupId, articleId, approved, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer, begin,
			end);
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

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_A(companyId, groupId, articleId);
	}

	public static void removeByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_S(companyId, groupId, structureId);
	}

	public static void removeByC_G_T(long companyId, long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_T(companyId, groupId, templateId);
	}

	public static void removeByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_A_A(companyId, groupId, articleId, approved);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_A(companyId, groupId, articleId);
	}

	public static int countByC_G_S(long companyId, long groupId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_S(companyId, groupId, structureId);
	}

	public static int countByC_G_T(long companyId, long groupId,
		java.lang.String templateId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_T(companyId, groupId, templateId);
	}

	public static int countByC_G_A_A(long companyId, long groupId,
		java.lang.String articleId, boolean approved)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_A_A(companyId, groupId, articleId,
			approved);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static JournalArticlePersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(JournalArticlePersistence persistence) {
		_persistence = persistence;
	}

	private static JournalArticleUtil _getUtil() {
		if (_util == null) {
			_util = (JournalArticleUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static ModelListener _getListener() {
		if (Validator.isNotNull(_LISTENER)) {
			try {
				return (ModelListener)Class.forName(_LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return null;
	}

	private static final String _UTIL = JournalArticleUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalArticle"));
	private static Log _log = LogFactory.getLog(JournalArticleUtil.class);
	private static JournalArticleUtil _util;
	private JournalArticlePersistence _persistence;
}