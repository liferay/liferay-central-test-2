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
import com.liferay.portal.model.ClassName;

import java.util.List;

/**
 * <a href="ClassNameUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNamePersistence
 * @see       ClassNamePersistenceImpl
 * @generated
 */
public class ClassNameUtil {
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
	public static ClassName remove(ClassName className)
		throws SystemException {
		return getPersistence().remove(className);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ClassName update(ClassName className, boolean merge)
		throws SystemException {
		return getPersistence().update(className, merge);
	}

	public static void cacheResult(com.liferay.portal.model.ClassName className) {
		getPersistence().cacheResult(className);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.ClassName> classNames) {
		getPersistence().cacheResult(classNames);
	}

	public static com.liferay.portal.model.ClassName create(long classNameId) {
		return getPersistence().create(classNameId);
	}

	public static com.liferay.portal.model.ClassName remove(long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(classNameId);
	}

	public static com.liferay.portal.model.ClassName updateImpl(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(className, merge);
	}

	public static com.liferay.portal.model.ClassName findByPrimaryKey(
		long classNameId)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(classNameId);
	}

	public static com.liferay.portal.model.ClassName fetchByPrimaryKey(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(classNameId);
	}

	public static com.liferay.portal.model.ClassName findByValue(
		java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByValue(value);
	}

	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByValue(value);
	}

	public static com.liferay.portal.model.ClassName fetchByValue(
		java.lang.String value, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByValue(value, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.ClassName> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.ClassName> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByValue(java.lang.String value)
		throws com.liferay.portal.NoSuchClassNameException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByValue(value);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByValue(java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByValue(value);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ClassNamePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ClassNamePersistence)PortalBeanLocatorUtil.locate(ClassNamePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(ClassNamePersistence persistence) {
		_persistence = persistence;
	}

	private static ClassNamePersistence _persistence;
}