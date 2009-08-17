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
 * <a href="BrowserTrackerUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BrowserTrackerPersistence
 * @see       BrowserTrackerPersistenceImpl
 * @generated
 */
public class BrowserTrackerUtil {
	public static void cacheResult(
		com.liferay.portal.model.BrowserTracker browserTracker) {
		getPersistence().cacheResult(browserTracker);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.BrowserTracker> browserTrackers) {
		getPersistence().cacheResult(browserTrackers);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.BrowserTracker create(
		long browserTrackerId) {
		return getPersistence().create(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker remove(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker remove(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(browserTracker);
	}

	/**
	 * @deprecated Use {@link #update(BrowserTracker, boolean merge)}.
	 */
	public static com.liferay.portal.model.BrowserTracker update(
		com.liferay.portal.model.BrowserTracker browserTracker)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(browserTracker);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  browserTracker the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when browserTracker is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public static com.liferay.portal.model.BrowserTracker update(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(browserTracker, merge);
	}

	public static com.liferay.portal.model.BrowserTracker updateImpl(
		com.liferay.portal.model.BrowserTracker browserTracker, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(browserTracker, merge);
	}

	public static com.liferay.portal.model.BrowserTracker findByPrimaryKey(
		long browserTrackerId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker fetchByPrimaryKey(
		long browserTrackerId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(browserTrackerId);
	}

	public static com.liferay.portal.model.BrowserTracker findByUserId(
		long userId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static com.liferay.portal.model.BrowserTracker fetchByUserId(
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUserId(userId);
	}

	public static com.liferay.portal.model.BrowserTracker fetchByUserId(
		long userId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByUserId(userId, retrieveFromCache);
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

	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.BrowserTracker> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByUserId(long userId)
		throws com.liferay.portal.NoSuchBrowserTrackerException,
			com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByUserId(long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static BrowserTrackerPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(BrowserTrackerPersistence persistence) {
		_persistence = persistence;
	}

	private static BrowserTrackerPersistence _persistence;
}