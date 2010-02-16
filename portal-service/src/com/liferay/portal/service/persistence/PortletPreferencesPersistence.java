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

import com.liferay.portal.model.PortletPreferences;

/**
 * <a href="PortletPreferencesPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletPreferencesPersistenceImpl
 * @see       PortletPreferencesUtil
 * @generated
 */
public interface PortletPreferencesPersistence extends BasePersistence<PortletPreferences> {
	public void cacheResult(
		com.liferay.portal.model.PortletPreferences portletPreferences);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.PortletPreferences> portletPreferenceses);

	public com.liferay.portal.model.PortletPreferences create(
		long portletPreferencesId);

	public com.liferay.portal.model.PortletPreferences remove(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences updateImpl(
		com.liferay.portal.model.PortletPreferences portletPreferences,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences fetchByPrimaryKey(
		long portletPreferencesId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByPlid_First(
		long plid, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByPlid_Last(
		long plid, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences[] findByPlid_PrevAndNext(
		long portletPreferencesId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByP_P(
		long plid, java.lang.String portletId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByP_P_First(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByP_P_Last(
		long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences[] findByP_P_PrevAndNext(
		long portletPreferencesId, long plid, java.lang.String portletId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findByO_O_P(
		long ownerId, int ownerType, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByO_O_P_First(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByO_O_P_Last(
		long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences[] findByO_O_P_PrevAndNext(
		long portletPreferencesId, long ownerId, int ownerType, long plid,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences findByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.PortletPreferences fetchByO_O_P_P(
		long ownerId, int ownerType, long plid, java.lang.String portletId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.PortletPreferences> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletPreferencesException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByP_P(long plid, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByO_O_P(long ownerId, int ownerType, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByO_O_P_P(long ownerId, int ownerType, long plid,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}