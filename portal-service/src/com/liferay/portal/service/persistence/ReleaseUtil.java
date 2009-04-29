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
 * <a href="ReleaseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ReleaseUtil {
	public static void cacheResult(com.liferay.portal.model.Release release) {
		getPersistence().cacheResult(release);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Release> releases) {
		getPersistence().cacheResult(releases);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portal.model.Release create(long releaseId) {
		return getPersistence().create(releaseId);
	}

	public static com.liferay.portal.model.Release remove(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(releaseId);
	}

	public static com.liferay.portal.model.Release remove(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(release);
	}

	/**
	 * @deprecated Use <code>update(Release release, boolean merge)</code>.
	 */
	public static com.liferay.portal.model.Release update(
		com.liferay.portal.model.Release release)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(release);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param        release the entity to add, update, or merge
	 * @param        merge boolean value for whether to merge the entity. The
	 *                default value is false. Setting merge to true is more
	 *                expensive and should only be true when release is
	 *                transient. See LEP-5473 for a detailed discussion of this
	 *                method.
	 * @return        true if the portlet can be displayed via Ajax
	 */
	public static com.liferay.portal.model.Release update(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(release, merge);
	}

	public static com.liferay.portal.model.Release updateImpl(
		com.liferay.portal.model.Release release, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(release, merge);
	}

	public static com.liferay.portal.model.Release findByPrimaryKey(
		long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(releaseId);
	}

	public static com.liferay.portal.model.Release fetchByPrimaryKey(
		long releaseId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(releaseId);
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

	public static java.util.List<com.liferay.portal.model.Release> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Release> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Release> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ReleasePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(ReleasePersistence persistence) {
		_persistence = persistence;
	}

	private static ReleasePersistence _persistence;
}