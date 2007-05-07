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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletPreferencesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletPreferencesUtil {
	public static com.liferay.portal.model.PortletPreferences create(
		long portletPreferencesId) {
		return getPersistence().create(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences remove(
		long portletPreferencesId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		ModelListener listener = _getListener();

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(portletPreferencesId));
		}

		com.liferay.portal.model.PortletPreferences portletPreferences = getPersistence()
																			 .remove(portletPreferencesId);

		if (listener != null) {
			listener.onAfterRemove(portletPreferences);
		}

		return portletPreferences;
	}

	public static com.liferay.portal.model.PortletPreferences remove(
		com.liferay.portal.model.PortletPreferences portletPreferences)
		throws com.liferay.portal.SystemException {
		ModelListener listener = _getListener();

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
		ModelListener listener = _getListener();
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
		ModelListener listener = _getListener();
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
		long portletPreferencesId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPrimaryKey(portletPreferencesId);
	}

	public static com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(portletPreferencesId);
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

	public static com.liferay.portal.model.PortletPreferences findByOwnerId_First(
		java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByOwnerId_First(ownerId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByOwnerId_Last(
		java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByOwnerId_Last(ownerId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByOwnerId_PrevAndNext(
		long portletPreferencesId, java.lang.String ownerId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByOwnerId_PrevAndNext(portletPreferencesId,
			ownerId, obc);
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
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByLayoutId(layoutId, begin, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByLayoutId_First(
		java.lang.String layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByLayoutId_First(layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByLayoutId_Last(
		java.lang.String layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByLayoutId_Last(layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByLayoutId_PrevAndNext(
		long portletPreferencesId, java.lang.String layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByLayoutId_PrevAndNext(portletPreferencesId,
			layoutId, obc);
	}

	public static java.util.List findByPortletId(java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByPortletId(portletId);
	}

	public static java.util.List findByPortletId(java.lang.String portletId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByPortletId(portletId, begin, end);
	}

	public static java.util.List findByPortletId(java.lang.String portletId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByPortletId(portletId, begin, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByPortletId_First(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPortletId_First(portletId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByPortletId_Last(
		java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPortletId_Last(portletId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByPortletId_PrevAndNext(
		long portletPreferencesId, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByPortletId_PrevAndNext(portletPreferencesId,
			portletId, obc);
	}

	public static java.util.List findByO_L(java.lang.String ownerId,
		java.lang.String layoutId) throws com.liferay.portal.SystemException {
		return getPersistence().findByO_L(ownerId, layoutId);
	}

	public static java.util.List findByO_L(java.lang.String ownerId,
		java.lang.String layoutId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_L(ownerId, layoutId, begin, end);
	}

	public static java.util.List findByO_L(java.lang.String ownerId,
		java.lang.String layoutId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByO_L(ownerId, layoutId, begin, end, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByO_L_First(
		java.lang.String ownerId, java.lang.String layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByO_L_First(ownerId, layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByO_L_Last(
		java.lang.String ownerId, java.lang.String layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByO_L_Last(ownerId, layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences[] findByO_L_PrevAndNext(
		long portletPreferencesId, java.lang.String ownerId,
		java.lang.String layoutId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByO_L_PrevAndNext(portletPreferencesId,
			ownerId, layoutId, obc);
	}

	public static com.liferay.portal.model.PortletPreferences findByO_L_P(
		java.lang.String ownerId, java.lang.String layoutId,
		java.lang.String portletId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		return getPersistence().findByO_L_P(ownerId, layoutId, portletId);
	}

	public static com.liferay.portal.model.PortletPreferences fetchByO_L_P(
		java.lang.String ownerId, java.lang.String layoutId,
		java.lang.String portletId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByO_L_P(ownerId, layoutId, portletId);
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

	public static void removeByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOwnerId(ownerId);
	}

	public static void removeByLayoutId(java.lang.String layoutId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByLayoutId(layoutId);
	}

	public static void removeByPortletId(java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByPortletId(portletId);
	}

	public static void removeByO_L(java.lang.String ownerId,
		java.lang.String layoutId) throws com.liferay.portal.SystemException {
		getPersistence().removeByO_L(ownerId, layoutId);
	}

	public static void removeByO_L_P(java.lang.String ownerId,
		java.lang.String layoutId, java.lang.String portletId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.NoSuchPortletPreferencesException {
		getPersistence().removeByO_L_P(ownerId, layoutId, portletId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByOwnerId(java.lang.String ownerId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOwnerId(ownerId);
	}

	public static int countByLayoutId(java.lang.String layoutId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByLayoutId(layoutId);
	}

	public static int countByPortletId(java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByPortletId(portletId);
	}

	public static int countByO_L(java.lang.String ownerId,
		java.lang.String layoutId) throws com.liferay.portal.SystemException {
		return getPersistence().countByO_L(ownerId, layoutId);
	}

	public static int countByO_L_P(java.lang.String ownerId,
		java.lang.String layoutId, java.lang.String portletId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByO_L_P(ownerId, layoutId, portletId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static PortletPreferencesPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(PortletPreferencesPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletPreferencesUtil _getUtil() {
		if (_util == null) {
			_util = (PortletPreferencesUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
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

	private static final String _UTIL = PortletPreferencesUtil.class.getName();
	private static final String _LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.PortletPreferences"));
	private static Log _log = LogFactory.getLog(PortletPreferencesUtil.class);
	private static PortletPreferencesUtil _util;
	private PortletPreferencesPersistence _persistence;
}