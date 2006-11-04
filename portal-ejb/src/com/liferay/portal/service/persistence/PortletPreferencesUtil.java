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
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="PortletPreferencesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesUtil {
	public static final String CLASS_NAME = PortletPreferencesUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.PortletPreferences"));

	public static com.liferay.portal.model.PortletPreferences create(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK) {
		return getPersistence().create(portletPreferencesPK);
	}

	public static com.liferay.portal.model.PortletPreferences remove(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
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
			listener.onBeforeRemove(findByPrimaryKey(portletPreferencesPK));
		}

		com.liferay.portal.model.PortletPreferences portletPreferences = getPersistence()
																			 .remove(portletPreferencesPK);

		if (listener != null) {
			listener.onAfterRemove(portletPreferences);
		}

		return portletPreferences;
	}

	public static com.liferay.portal.model.PortletPreferences remove(
		com.liferay.portal.model.PortletPreferences portletPreferences)
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

		if (listener != null) {
			listener.onBeforeRemove(portletPreferences);
		}

		portletPreferences = getPersistence().remove(portletPreferences);

		if (listener != null) {
			listener.onAfterRemove(portletPreferences);
		}

		return portletPreferences;
	}

	public static com.liferay.portal.model.PortletPreferences update(
		com.liferay.portal.model.PortletPreferences portletPreferences)
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

		boolean isNew = portletPreferences.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(portletPreferences);
			}
			else {
				listener.onBeforeUpdate(portletPreferences);
			}
		}

		portletPreferences = getPersistence().update(portletPreferences);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(portletPreferences);
			}
			else {
				listener.onAfterUpdate(portletPreferences);
			}
		}

		return portletPreferences;
	}

	public static com.liferay.portal.model.PortletPreferences update(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = portletPreferences.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(portletPreferences);
			}
			else {
				listener.onBeforeUpdate(portletPreferences);
			}
		}

		portletPreferences = getPersistence().update(portletPreferences,
				saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(portletPreferences);
			}
			else {
				listener.onAfterUpdate(portletPreferences);
			}
		}

		return portletPreferences;
	}

	public static com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(portletPreferencesPK);
	}

	public static com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(portletPreferencesPK);
	}

	public static java.util.List findByLayoutId(java.lang.String layoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId(layoutId);
	}

	public static java.util.List findByLayoutId(java.lang.String layoutId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId(layoutId, begin, end);
	}

	public static java.util.List findByLayoutId(java.lang.String layoutId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId(layoutId, begin, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByLayoutId_First(
		java.lang.String layoutId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId_First(layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByLayoutId_Last(
		java.lang.String layoutId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId_Last(layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByLayoutId_PrevAndNext(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK,
		java.lang.String layoutId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId_PrevAndNext(portletPreferencesPK,
			layoutId, obc);
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

	public static com.liferay.portal.model.PortletPreferences findByOwnerId_First(
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_First(ownerId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByOwnerId_Last(
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_Last(ownerId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByOwnerId_PrevAndNext(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK,
		java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByOwnerId_PrevAndNext(portletPreferencesPK,
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

	public static com.liferay.portal.model.PortletPreferences findByL_O_First(
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByL_O_First(layoutId, ownerId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByL_O_Last(
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByL_O_Last(layoutId, ownerId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByL_O_PrevAndNext(
		com.liferay.portal.service.persistence.PortletPreferencesPK portletPreferencesPK,
		java.lang.String layoutId, java.lang.String ownerId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByL_O_PrevAndNext(portletPreferencesPK,
			layoutId, ownerId, obc);
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

	public static void removeByLayoutId(java.lang.String layoutId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByLayoutId(layoutId);
	}

	public static void removeByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOwnerId(ownerId);
	}

	public static void removeByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		getPersistence().removeByL_O(layoutId, ownerId);
	}

	public static int countByLayoutId(java.lang.String layoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLayoutId(layoutId);
	}

	public static int countByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOwnerId(ownerId);
	}

	public static int countByL_O(java.lang.String layoutId,
		java.lang.String ownerId) throws com.liferay.portal.SystemException {
		return getPersistence().countByL_O(layoutId, ownerId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PortletPreferencesPersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		PortletPreferencesUtil util = (PortletPreferencesUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(PortletPreferencesPersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(PortletPreferencesUtil.class);
	private PortletPreferencesPersistence _persistence;
}