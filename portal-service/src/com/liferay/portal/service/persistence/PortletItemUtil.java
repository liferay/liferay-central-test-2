/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

	public static com.liferay.portal.model.PortletItem remove(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(portletItem);
	}

	/**
	 * @deprecated Use <code>update(PortletItem portletItem, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.PortletItem update(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(portletItem);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        portletItem the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when portletItem is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
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
		long classNameId, boolean cacheEmptyResult)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_N_P_C(groupId, name, portletId, classNameId,
			cacheEmptyResult);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
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
		return _persistence;
	}

	public void setPersistence(PortletItemPersistence persistence) {
		_persistence = persistence;
	}

	private static PortletItemPersistence _persistence;
}