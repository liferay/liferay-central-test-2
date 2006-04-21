/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="JournalContentSearchUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalContentSearchUtil {
	public static final String CLASS_NAME = JournalContentSearchUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalContentSearch"));

	public static com.liferay.portlet.journal.model.JournalContentSearch create(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK) {
		return getPersistence().create(journalContentSearchPK);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch remove(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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

	public static com.liferay.portlet.journal.model.JournalContentSearch update(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

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

	public static com.liferay.portlet.journal.model.JournalContentSearch findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(journalContentSearchPK);
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
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId(ownerId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByOwnerId_First(
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_First(ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByOwnerId_Last(
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_Last(ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByOwnerId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByL_O(layoutId, ownerId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByL_O_First(
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByL_O_First(layoutId, ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByL_O_Last(
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByL_O_Last(layoutId, ownerId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByL_O_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByL_O_PrevAndNext(journalContentSearchPK,
			layoutId, ownerId, obc);
	}

	public static java.util.List findByO_A(java.lang.String ownerId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().findByO_A(ownerId, articleId);
	}

	public static java.util.List findByO_A(java.lang.String ownerId,
		java.lang.String articleId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_A(ownerId, articleId, begin, end);
	}

	public static java.util.List findByO_A(java.lang.String ownerId,
		java.lang.String articleId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_A(ownerId, articleId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByO_A_First(
		java.lang.String ownerId, java.lang.String articleId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_A_First(ownerId, articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByO_A_Last(
		java.lang.String ownerId, java.lang.String articleId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_A_Last(ownerId, articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByO_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String ownerId, java.lang.String articleId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_A_PrevAndNext(journalContentSearchPK,
			ownerId, articleId, obc);
	}

	public static java.util.List findByC_A(java.lang.String companyId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, articleId);
	}

	public static java.util.List findByC_A(java.lang.String companyId,
		java.lang.String articleId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, articleId, begin, end);
	}

	public static java.util.List findByC_A(java.lang.String companyId,
		java.lang.String articleId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, articleId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByC_A_First(
		java.lang.String companyId, java.lang.String articleId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_First(companyId, articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch findByC_A_Last(
		java.lang.String companyId, java.lang.String articleId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_Last(companyId, articleId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalContentSearch[] findByC_A_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalContentSearchPK journalContentSearchPK,
		java.lang.String companyId, java.lang.String articleId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchContentSearchException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_PrevAndNext(journalContentSearchPK,
			companyId, articleId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOwnerId(ownerId);
	}

	public static void removeByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		getPersistence().removeByL_O(layoutId, ownerId);
	}

	public static void removeByO_A(java.lang.String ownerId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		getPersistence().removeByO_A(ownerId, articleId);
	}

	public static void removeByC_A(java.lang.String companyId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_A(companyId, articleId);
	}

	public static int countByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOwnerId(ownerId);
	}

	public static int countByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		return getPersistence().countByL_O(layoutId, ownerId);
	}

	public static int countByO_A(java.lang.String ownerId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().countByO_A(ownerId, articleId);
	}

	public static int countByC_A(java.lang.String companyId,
		java.lang.String articleId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_A(companyId, articleId);
	}

	public static JournalContentSearchPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		JournalContentSearchUtil util = (JournalContentSearchUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(JournalContentSearchPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(JournalContentSearchUtil.class);
	private JournalContentSearchPersistence _persistence;
}