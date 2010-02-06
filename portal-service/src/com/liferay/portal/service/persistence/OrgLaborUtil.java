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
import com.liferay.portal.model.OrgLabor;

import java.util.List;

/**
 * <a href="OrgLaborUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgLaborPersistence
 * @see       OrgLaborPersistenceImpl
 * @generated
 */
public class OrgLaborUtil {
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
	public static OrgLabor remove(OrgLabor orgLabor) throws SystemException {
		return getPersistence().remove(orgLabor);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static OrgLabor update(OrgLabor orgLabor, boolean merge)
		throws SystemException {
		return getPersistence().update(orgLabor, merge);
	}

	public static void cacheResult(com.liferay.portal.model.OrgLabor orgLabor) {
		getPersistence().cacheResult(orgLabor);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.OrgLabor> orgLabors) {
		getPersistence().cacheResult(orgLabors);
	}

	public static com.liferay.portal.model.OrgLabor create(long orgLaborId) {
		return getPersistence().create(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor remove(long orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor updateImpl(
		com.liferay.portal.model.OrgLabor orgLabor, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(orgLabor, merge);
	}

	public static com.liferay.portal.model.OrgLabor findByPrimaryKey(
		long orgLaborId)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(orgLaborId);
	}

	public static com.liferay.portal.model.OrgLabor fetchByPrimaryKey(
		long orgLaborId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(orgLaborId);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId) throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId(organizationId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findByOrganizationId(
		long organizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByOrganizationId(organizationId, start, end, obc);
	}

	public static com.liferay.portal.model.OrgLabor findByOrganizationId_First(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_First(organizationId, obc);
	}

	public static com.liferay.portal.model.OrgLabor findByOrganizationId_Last(
		long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence().findByOrganizationId_Last(organizationId, obc);
	}

	public static com.liferay.portal.model.OrgLabor[] findByOrganizationId_PrevAndNext(
		long orgLaborId, long organizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgLaborException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByOrganizationId_PrevAndNext(orgLaborId,
			organizationId, obc);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.OrgLabor> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByOrganizationId(long organizationId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByOrganizationId(organizationId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByOrganizationId(long organizationId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByOrganizationId(organizationId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static OrgLaborPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (OrgLaborPersistence)PortalBeanLocatorUtil.locate(OrgLaborPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(OrgLaborPersistence persistence) {
		_persistence = persistence;
	}

	private static OrgLaborPersistence _persistence;
}