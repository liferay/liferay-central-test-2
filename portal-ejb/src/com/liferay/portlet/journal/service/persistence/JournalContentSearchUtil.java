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
 * <a href="JournalContentSearchUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalContentSearchUtil {
	public static com.liferay.portlet.journal.model.JournalContentSearch create(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK) {
		return getPersistence().create(journalContentSearchPK);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch remove(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(journalContentSearchPK));
		}

		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch =
			getPersistence().remove(journalContentSearchPK);

		if (listener != null) {
			listener.onAfterRemove(journalContentSearch);
		}

		return journalContentSearch;
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch remove(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(journalContentSearch);
		}

		journalContentSearch = getPersistence().remove(journalContentSearch);

		if (listener != null) {
			listener.onAfterRemove(journalContentSearch);
		}

		return journalContentSearch;
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch update(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalContentSearch.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalContentSearch);
			}
			else {
				listener.onBeforeUpdate(journalContentSearch);
			}
		}

		journalContentSearch = getPersistence().update(journalContentSearch);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalContentSearch);
			}
			else {
				listener.onAfterUpdate(journalContentSearch);
			}
		}

		return journalContentSearch;
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch update(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = journalContentSearch.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalContentSearch);
			}
			else {
				listener.onBeforeUpdate(journalContentSearch);
			}
		}

		journalContentSearch = getPersistence().update(journalContentSearch,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalContentSearch);
			}
			else {
				listener.onAfterUpdate(journalContentSearch);
			}
		}

		return journalContentSearch;
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByPrimaryKey(journalContentSearchPK);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(journalContentSearchPK);
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

	public static com.liferay.portlet.journal.model.JournalContentSearch findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByGroupId_PrevAndNext(journalContentSearchPK,
			groupId, obc);
	}

	public static java.util.List findByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId(ownerId);
	}

	public static java.util.List findByOwnerId(java.lang.String ownerId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId(ownerId, begin, end);
	}

	public static java.util.List findByOwnerId(java.lang.String ownerId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId(ownerId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByOwnerId_First(
		java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByOwnerId_First(ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByOwnerId_Last(
		java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByOwnerId_Last(ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByOwnerId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByOwnerId_PrevAndNext(journalContentSearchPK,
			ownerId, obc);
	}

	public static java.util.List findByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		return getPersistence().findByL_O(layoutId, ownerId);
	}

	public static java.util.List findByL_O(java.lang.String layoutId,
		java.lang.String ownerId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByL_O(layoutId, ownerId, begin, end);
	}

	public static java.util.List findByL_O(java.lang.String layoutId,
		java.lang.String ownerId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByL_O(layoutId, ownerId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByL_O_First(
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByL_O_First(layoutId, ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByL_O_Last(
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByL_O_Last(layoutId, ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByL_O_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByL_O_PrevAndNext(journalContentSearchPK,
			layoutId, ownerId, obc);
	}

	public static java.util.List findByO_G_A(java.lang.String ownerId,
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_G_A(ownerId, groupId, articleId);
	}

	public static java.util.List findByO_G_A(java.lang.String ownerId,
		long groupId, java.lang.String articleId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_G_A(ownerId, groupId, articleId, begin,
			end);
	}

	public static java.util.List findByO_G_A(java.lang.String ownerId,
		long groupId, java.lang.String articleId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_G_A(ownerId, groupId, articleId, begin,
			end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByO_G_A_First(
		java.lang.String ownerId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByO_G_A_First(ownerId, groupId, articleId,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByO_G_A_Last(
		java.lang.String ownerId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByO_G_A_Last(ownerId, groupId, articleId,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByO_G_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String ownerId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByO_G_A_PrevAndNext(journalContentSearchPK,
			ownerId, groupId, articleId, obc);
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

	public static com.liferay.portlet.journal.model.JournalContentSearch findByC_G_A_First(
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByC_G_A_First(companyId, groupId,
			articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByC_G_A_Last(
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByC_G_A_Last(companyId, groupId, articleId,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByC_G_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		long companyId, long groupId, java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portlet.journal.NoSuchContentSearchException {
		return getPersistence().findByC_G_A_PrevAndNext(journalContentSearchPK,
			companyId, groupId, articleId, obc);
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

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOwnerId(ownerId);
	}

	public static void removeByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		getPersistence().removeByL_O(layoutId, ownerId);
	}

	public static void removeByO_G_A(java.lang.String ownerId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		getPersistence().removeByO_G_A(ownerId, groupId, articleId);
	}

	public static void removeByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_A(companyId, groupId, articleId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOwnerId(ownerId);
	}

	public static int countByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		return getPersistence().countByL_O(layoutId, ownerId);
	}

	public static int countByO_G_A(java.lang.String ownerId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().countByO_G_A(ownerId, groupId, articleId);
	}

	public static int countByC_G_A(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_A(companyId, groupId, articleId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static JournalContentSearchPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(JournalContentSearchPersistence persistence) {
		_persistence = persistence;
	}

	private static JournalContentSearchUtil _getUtil() {
		if (_util == null) {
			_util = (JournalContentSearchUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = JournalContentSearchUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalContentSearch"));
	private static Log _log = LogFactory.getLog(JournalContentSearchUtil.class);
	private static JournalContentSearchUtil _util;
	private JournalContentSearchPersistence _persistence;
}