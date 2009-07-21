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

public class LayoutSetUtil {
	public static void cacheResult(com.liferay.portal.model.LayoutSet layoutSet) {
		getPersistence().cacheResult(layoutSet);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSet> layoutSets) {
		getPersistence().cacheResult(layoutSets);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.LayoutSet create(long layoutSetId) {
		return getPersistence().create(layoutSetId);
	}

	public static com.liferay.portal.model.LayoutSet remove(long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(layoutSetId);
	}

	public static com.liferay.portal.model.LayoutSet remove(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(layoutSet);
	}

	public static com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(layoutSet);
	}

	public static com.liferay.portal.model.LayoutSet update(
		com.liferay.portal.model.LayoutSet layoutSet, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(layoutSet, merge);
	}

	public static com.liferay.portal.model.LayoutSet updateImpl(
		com.liferay.portal.model.LayoutSet layoutSet, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(layoutSet, merge);
	}

	public static com.liferay.portal.model.LayoutSet findByPrimaryKey(
		long layoutSetId)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(layoutSetId);
	}

	public static com.liferay.portal.model.LayoutSet fetchByPrimaryKey(
		long layoutSetId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutSetId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, start, end, obc);
	}

	public static com.liferay.portal.model.LayoutSet findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portal.model.LayoutSet findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portal.model.LayoutSet[] findByGroupId_PrevAndNext(
		long layoutSetId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(layoutSetId, groupId, obc);
	}

	public static com.liferay.portal.model.LayoutSet findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence().findByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.LayoutSet fetchByVirtualHost(
		java.lang.String virtualHost) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByVirtualHost(virtualHost);
	}

	public static com.liferay.portal.model.LayoutSet fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByVirtualHost(virtualHost, retrieveFromCache);
	}

	public static com.liferay.portal.model.LayoutSet findByG_P(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_P(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet fetchByG_P(long groupId,
		boolean privateLayout) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_P(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet fetchByG_P(long groupId,
		boolean privateLayout, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_P(groupId, privateLayout, retrieveFromCache);
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

	public static java.util.List<com.liferay.portal.model.LayoutSet> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		getPersistence().removeByVirtualHost(virtualHost);
	}

	public static void removeByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.NoSuchLayoutSetException,
			com.liferay.portal.SystemException {
		getPersistence().removeByG_P(groupId, privateLayout);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByVirtualHost(virtualHost);
	}

	public static int countByG_P(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByG_P(groupId, privateLayout);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutSetPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(LayoutSetPersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutSetPersistence _persistence;
}