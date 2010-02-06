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
import com.liferay.portal.model.Release;

import java.util.List;

/**
 * <a href="ReleaseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReleasePersistence
 * @see       ReleasePersistenceImpl
 * @generated
 */
public class ReleaseUtil {
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
	public static Release remove(Release release) throws SystemException {
		return getPersistence().remove(release);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Release update(Release release, boolean merge)
		throws SystemException {
		return getPersistence().update(release, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Release release) {
		getPersistence().cacheResult(release);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Release> releases) {
		getPersistence().cacheResult(releases);
	}

	public static com.liferay.portal.model.Release create(long releaseId) {
		return getPersistence().create(releaseId);
	}

	public static com.liferay.portal.model.Release remove(long releaseId)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(releaseId);
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

	public static com.liferay.portal.model.Release findByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.SystemException {
		return getPersistence().findByServletContextName(servletContextName);
	}

	public static com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByServletContextName(servletContextName);
	}

	public static com.liferay.portal.model.Release fetchByServletContextName(
		java.lang.String servletContextName, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByServletContextName(servletContextName,
			retrieveFromCache);
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

	public static void removeByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.NoSuchReleaseException,
			com.liferay.portal.SystemException {
		getPersistence().removeByServletContextName(servletContextName);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByServletContextName(
		java.lang.String servletContextName)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByServletContextName(servletContextName);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static ReleasePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ReleasePersistence)PortalBeanLocatorUtil.locate(ReleasePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ReleasePersistence persistence) {
		_persistence = persistence;
	}

	private static ReleasePersistence _persistence;
}