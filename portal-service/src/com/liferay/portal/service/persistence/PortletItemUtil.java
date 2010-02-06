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

package com.liferay.portal.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.PortletItem;

import java.util.List;

/**
 * <a href="PortletItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemPersistence
 * @see       PortletItemPersistenceImpl
 * @generated
 */
public class PortletItemUtil {
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
	public static PortletItem remove(PortletItem portletItem)
		throws SystemException {
		return getPersistence().remove(portletItem);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static PortletItem update(PortletItem portletItem, boolean merge)
		throws SystemException {
		return getPersistence().update(portletItem, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.PortletItem portletItem) {
		getPersistence().cacheResult(portletItem);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.PortletItem> portletItems) {
		getPersistence().cacheResult(portletItems);
	}

	public static com.liferay.portal.model.PortletItem create(
		long portletItemId) {
		return getPersistence().create(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem remove(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(portletItem, merge);
	}

	public static com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(portletItemId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_C(groupId, classNameId, start, end, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_C_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_C_First(groupId, classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_C_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_C_Last(groupId, classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem[] findByG_C_PrevAndNext(
		long portletItemId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_C_PrevAndNext(portletItemId, groupId, classNameId,
			obc);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P_C(groupId, portletId, classNameId);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, start, end, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C_First(groupId, portletId, classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_P_C_Last(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C_Last(groupId, portletId, classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C_PrevAndNext(portletItemId, groupId, portletId,
			classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	public static void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P_C(groupId, portletId, classNameId);
	}

	public static void removeByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.SystemException {
		getPersistence().removeByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	public static int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P_C(groupId, portletId, classNameId);
	}

	public static int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .countByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static PortletItemPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortletItemPersistence)PortalBeanLocatorUtil.locate(PortletItemPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(PortletItemPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletItemPersistence _persistence;
}