/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

/**
 * <a href="PortletItemUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PortletItemUtil {
	public static com.liferay.portal.model.PortletItem create(
		long portletItemId) {
		return getPersistence().create(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem remove(
		long portletItemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		return getPersistence().remove(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem remove(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(portletItem);
	}

	public static com.liferay.portal.model.PortletItem update(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(portletItem);
	}

	public static com.liferay.portal.model.PortletItem update(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(portletItem, merge);
	}

	public static com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(portletItem, merge);
	}

	public static com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		return getPersistence().findByPrimaryKey(portletItemId);
	}

	public static com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(portletItemId);
	}

	public static java.util.List findByG_P_C(long groupId,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByG_P_C(groupId, portletId, classNameId);
	}

	public static java.util.List findByG_P_C(long groupId,
		java.lang.String portletId, long classNameId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, begin, end);
	}

	public static java.util.List findByG_P_C(long groupId,
		java.lang.String portletId, long classNameId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByG_P_C(groupId, portletId, classNameId, begin, end, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		return getPersistence()
				   .findByG_P_C_First(groupId, portletId, classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_P_C_Last(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		return getPersistence()
				   .findByG_P_C_Last(groupId, portletId, classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		return getPersistence()
				   .findByG_P_C_PrevAndNext(portletItemId, groupId, portletId,
			classNameId, obc);
	}

	public static com.liferay.portal.model.PortletItem findByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		return getPersistence()
				   .findByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static com.liferay.portal.model.PortletItem fetchByG_N_P_C(
		long groupId, java.lang.String name, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
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

	public static void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId) throws com.liferay.portal.SystemException {
		getPersistence().removeByG_P_C(groupId, portletId, classNameId);
	}

	public static void removeByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.NoSuchPortletItemException {
		getPersistence().removeByG_N_P_C(groupId, name, portletId, classNameId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
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
		return _getUtil()._persistence;
	}

	public void setPersistence(PortletItemPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletItemUtil _getUtil() {
		if (_util == null) {
			_util = (PortletItemUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = PortletItemUtil.class.getName();
	private static PortletItemUtil _util;
	private PortletItemPersistence _persistence;
}