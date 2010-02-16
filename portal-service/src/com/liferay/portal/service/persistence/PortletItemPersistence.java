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

import com.liferay.portal.model.PortletItem;

/**
 * <a href="PortletItemPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemPersistenceImpl
 * @see       PortletItemUtil
 * @generated
 */
public interface PortletItemPersistence extends BasePersistence<PortletItem> {
	public void cacheResult(com.liferay.portal.model.PortletItem portletItem);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.PortletItem> portletItems);

	public com.liferay.portal.model.PortletItem create(long portletItemId);

	public com.liferay.portal.model.PortletItem remove(long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem updateImpl(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem findByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem fetchByPrimaryKey(
		long portletItemId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem findByG_C_First(long groupId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem findByG_C_Last(long groupId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem[] findByG_C_PrevAndNext(
		long portletItemId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findByG_P_C(
		long groupId, java.lang.String portletId, long classNameId, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem findByG_P_C_First(
		long groupId, java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem findByG_P_C_Last(long groupId,
		java.lang.String portletId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem[] findByG_P_C_PrevAndNext(
		long portletItemId, long groupId, java.lang.String portletId,
		long classNameId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem findByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletItem fetchByG_N_P_C(long groupId,
		java.lang.String name, java.lang.String portletId, long classNameId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletItem> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.NoSuchPortletItemException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_P_C(long groupId, java.lang.String portletId,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_N_P_C(long groupId, java.lang.String name,
		java.lang.String portletId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}