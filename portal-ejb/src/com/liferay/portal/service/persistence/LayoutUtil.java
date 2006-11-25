/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="LayoutUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutUtil {
	public static com.liferay.portal.model.Layout create(
		com.liferay.portal.service.persistence.LayoutPK layoutPK) {
		return getPersistence().create(layoutPK);
	}

	public static com.liferay.portal.model.Layout remove(
		com.liferay.portal.service.persistence.LayoutPK layoutPK)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(layoutPK));
		}

		com.liferay.portal.model.Layout layout = getPersistence().remove(layoutPK);

		if (listener != null) {
			listener.onAfterRemove(layout);
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout remove(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(layout);
		}

		layout = getPersistence().remove(layout);

		if (listener != null) {
			listener.onAfterRemove(layout);
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = layout.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(layout);
			}
			else {
				listener.onBeforeUpdate(layout);
			}
		}

		layout = getPersistence().update(layout);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(layout);
			}
			else {
				listener.onAfterUpdate(layout);
			}
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout update(
		com.liferay.portal.model.Layout layout, boolean saveOrUpdate)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();
		boolean isNew = layout.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(layout);
			}
			else {
				listener.onBeforeUpdate(layout);
			}
		}

		layout = getPersistence().update(layout, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(layout);
			}
			else {
				listener.onAfterUpdate(layout);
			}
		}

		return layout;
	}

	public static com.liferay.portal.model.Layout findByPrimaryKey(
		com.liferay.portal.service.persistence.LayoutPK layoutPK)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(layoutPK);
	}

	public static com.liferay.portal.model.Layout fetchByPrimaryKey(
		com.liferay.portal.service.persistence.LayoutPK layoutPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutPK);
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

	public static com.liferay.portal.model.Layout findByOwnerId_First(
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_First(ownerId, obc);
	}

	public static com.liferay.portal.model.Layout findByOwnerId_Last(
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_Last(ownerId, obc);
	}

	public static com.liferay.portal.model.Layout[] findByOwnerId_PrevAndNext(
		com.liferay.portal.service.persistence.LayoutPK layoutPK,
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_PrevAndNext(layoutPK, ownerId, obc);
	}

	public static java.util.List findByO_P(java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_P(ownerId, parentLayoutId);
	}

	public static java.util.List findByO_P(java.lang.String ownerId,
		java.lang.String parentLayoutId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_P(ownerId, parentLayoutId, begin, end);
	}

	public static java.util.List findByO_P(java.lang.String ownerId,
		java.lang.String parentLayoutId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_P(ownerId, parentLayoutId, begin, end,
			obc);
	}

	public static com.liferay.portal.model.Layout findByO_P_First(
		java.lang.String ownerId, java.lang.String parentLayoutId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_P_First(ownerId, parentLayoutId, obc);
	}

	public static com.liferay.portal.model.Layout findByO_P_Last(
		java.lang.String ownerId, java.lang.String parentLayoutId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_P_Last(ownerId, parentLayoutId, obc);
	}

	public static com.liferay.portal.model.Layout[] findByO_P_PrevAndNext(
		com.liferay.portal.service.persistence.LayoutPK layoutPK,
		java.lang.String ownerId, java.lang.String parentLayoutId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_P_PrevAndNext(layoutPK, ownerId,
			parentLayoutId, obc);
	}

	public static com.liferay.portal.model.Layout findByO_F(
		java.lang.String ownerId, java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByO_F(ownerId, friendlyURL);
	}

	public static com.liferay.portal.model.Layout fetchByO_F(
		java.lang.String ownerId, java.lang.String friendlyURL)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByO_F(ownerId, friendlyURL);
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
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOwnerId(ownerId);
	}

	public static void removeByO_P(java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByO_P(ownerId, parentLayoutId);
	}

	public static void removeByO_F(java.lang.String ownerId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.NoSuchLayoutException, 
			com.liferay.portal.SystemException {
		getPersistence().removeByO_F(ownerId, friendlyURL);
	}

	public static int countByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOwnerId(ownerId);
	}

	public static int countByO_P(java.lang.String ownerId,
		java.lang.String parentLayoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByO_P(ownerId, parentLayoutId);
	}

	public static int countByO_F(java.lang.String ownerId,
		java.lang.String friendlyURL) throws com.liferay.portal.SystemException {
		return getPersistence().countByO_F(ownerId, friendlyURL);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static LayoutPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(LayoutPersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutUtil _getUtil() {
		if (_util == null) {
			_util = (LayoutUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = LayoutUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Layout"));
	private static Log _log = LogFactory.getLog(LayoutUtil.class);
	private static LayoutUtil _util;
	private LayoutPersistence _persistence;
}