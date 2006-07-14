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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="DLFileRankUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class DLFileRankUtil {
	public static final String CLASS_NAME = DLFileRankUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.documentlibrary.model.DLFileRank"));

	public static com.liferay.portlet.documentlibrary.model.DLFileRank create(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK) {
		return getPersistence().create(dlFileRankPK);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank remove(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
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
			listener.onBeforeRemove(findByPrimaryKey(dlFileRankPK));
		}

		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank = getPersistence()
																			  .remove(dlFileRankPK);

		if (listener != null) {
			listener.onAfterRemove(dlFileRank);
		}

		return dlFileRank;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank update(
		com.liferay.portlet.documentlibrary.model.DLFileRank dlFileRank)
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

		boolean isNew = dlFileRank.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(dlFileRank);
			}
			else {
				listener.onBeforeUpdate(dlFileRank);
			}
		}

		dlFileRank = getPersistence().update(dlFileRank);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(dlFileRank);
			}
			else {
				listener.onAfterUpdate(dlFileRank);
			}
		}

		return dlFileRank;
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByPrimaryKey(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(dlFileRankPK);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank fetchByPrimaryKey(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(dlFileRankPK);
	}

	public static java.util.List findByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_First(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByUserId_Last(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByUserId_PrevAndNext(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK,
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(dlFileRankPK, userId,
			obc);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name, begin, end);
	}

	public static java.util.List findByF_N(java.lang.String folderId,
		java.lang.String name, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByF_N(folderId, name, begin, end, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_First(
		java.lang.String folderId, java.lang.String name,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByF_N_First(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank findByF_N_Last(
		java.lang.String folderId, java.lang.String name,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByF_N_Last(folderId, name, obc);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileRank[] findByF_N_PrevAndNext(
		com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPK dlFileRankPK,
		java.lang.String folderId, java.lang.String name,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.documentlibrary.NoSuchFileRankException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByF_N_PrevAndNext(dlFileRankPK, folderId,
			name, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		getPersistence().removeByF_N(folderId, name);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByF_N(java.lang.String folderId,
		java.lang.String name) throws com.liferay.portal.SystemException {
		return getPersistence().countByF_N(folderId, name);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static DLFileRankPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		DLFileRankUtil util = (DLFileRankUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(DLFileRankPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(DLFileRankUtil.class);
	private DLFileRankPersistence _persistence;
}