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
import com.liferay.portal.model.WebDAVProps;

import java.util.List;

/**
 * <a href="WebDAVPropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVPropsPersistence
 * @see       WebDAVPropsPersistenceImpl
 * @generated
 */
public class WebDAVPropsUtil {
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
	public static WebDAVProps remove(WebDAVProps webDAVProps)
		throws SystemException {
		return getPersistence().remove(webDAVProps);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WebDAVProps update(WebDAVProps webDAVProps, boolean merge)
		throws SystemException {
		return getPersistence().update(webDAVProps, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.WebDAVProps webDAVProps) {
		getPersistence().cacheResult(webDAVProps);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WebDAVProps> webDAVPropses) {
		getPersistence().cacheResult(webDAVPropses);
	}

	public static com.liferay.portal.model.WebDAVProps create(
		long webDavPropsId) {
		return getPersistence().create(webDavPropsId);
	}

	public static com.liferay.portal.model.WebDAVProps remove(
		long webDavPropsId)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(webDavPropsId);
	}

	public static com.liferay.portal.model.WebDAVProps updateImpl(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(webDAVProps, merge);
	}

	public static com.liferay.portal.model.WebDAVProps findByPrimaryKey(
		long webDavPropsId)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(webDavPropsId);
	}

	public static com.liferay.portal.model.WebDAVProps fetchByPrimaryKey(
		long webDavPropsId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(webDavPropsId);
	}

	public static com.liferay.portal.model.WebDAVProps findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portal.model.WebDAVProps fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static com.liferay.portal.model.WebDAVProps fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.WebDAVProps> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.WebDAVProps> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWebDAVPropsException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static WebDAVPropsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WebDAVPropsPersistence)PortalBeanLocatorUtil.locate(WebDAVPropsPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(WebDAVPropsPersistence persistence) {
		_persistence = persistence;
	}

	private static WebDAVPropsPersistence _persistence;
}