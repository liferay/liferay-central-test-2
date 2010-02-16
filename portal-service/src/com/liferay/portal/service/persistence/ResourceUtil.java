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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Resource;

import java.util.List;

/**
 * <a href="ResourceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourcePersistence
 * @see       ResourcePersistenceImpl
 * @generated
 */
public class ResourceUtil {
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
	public static Resource remove(Resource resource) throws SystemException {
		return getPersistence().remove(resource);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Resource update(Resource resource, boolean merge)
		throws SystemException {
		return getPersistence().update(resource, merge);
	}

	public static void cacheResult(com.liferay.portal.model.Resource resource) {
		getPersistence().cacheResult(resource);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Resource> resources) {
		getPersistence().cacheResult(resources);
	}

	public static com.liferay.portal.model.Resource create(long resourceId) {
		return getPersistence().create(resourceId);
	}

	public static com.liferay.portal.model.Resource remove(long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(resourceId);
	}

	public static com.liferay.portal.model.Resource updateImpl(
		com.liferay.portal.model.Resource resource, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(resource, merge);
	}

	public static com.liferay.portal.model.Resource findByPrimaryKey(
		long resourceId)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(resourceId);
	}

	public static com.liferay.portal.model.Resource fetchByPrimaryKey(
		long resourceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(resourceId);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCodeId(codeId);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCodeId(codeId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findByCodeId(
		long codeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCodeId(codeId, start, end, obc);
	}

	public static com.liferay.portal.model.Resource findByCodeId_First(
		long codeId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCodeId_First(codeId, obc);
	}

	public static com.liferay.portal.model.Resource findByCodeId_Last(
		long codeId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCodeId_Last(codeId, obc);
	}

	public static com.liferay.portal.model.Resource[] findByCodeId_PrevAndNext(
		long resourceId, long codeId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCodeId_PrevAndNext(resourceId, codeId, obc);
	}

	public static com.liferay.portal.model.Resource findByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(codeId, primKey);
	}

	public static com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_P(codeId, primKey);
	}

	public static com.liferay.portal.model.Resource fetchByC_P(long codeId,
		java.lang.String primKey, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_P(codeId, primKey, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Resource> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCodeId(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCodeId(codeId);
	}

	public static void removeByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.NoSuchResourceException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(codeId, primKey);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCodeId(long codeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCodeId(codeId);
	}

	public static int countByC_P(long codeId, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(codeId, primKey);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ResourcePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ResourcePersistence)PortalBeanLocatorUtil.locate(ResourcePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ResourcePersistence persistence) {
		_persistence = persistence;
	}

	private static ResourcePersistence _persistence;
}