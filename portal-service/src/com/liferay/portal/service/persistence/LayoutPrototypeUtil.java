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
import com.liferay.portal.model.LayoutPrototype;

import java.util.List;

/**
 * <a href="LayoutPrototypeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPrototypePersistence
 * @see       LayoutPrototypePersistenceImpl
 * @generated
 */
public class LayoutPrototypeUtil {
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
	public static LayoutPrototype remove(LayoutPrototype layoutPrototype)
		throws SystemException {
		return getPersistence().remove(layoutPrototype);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LayoutPrototype update(LayoutPrototype layoutPrototype,
		boolean merge) throws SystemException {
		return getPersistence().update(layoutPrototype, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.LayoutPrototype layoutPrototype) {
		getPersistence().cacheResult(layoutPrototype);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutPrototype> layoutPrototypes) {
		getPersistence().cacheResult(layoutPrototypes);
	}

	public static com.liferay.portal.model.LayoutPrototype create(
		long layoutPrototypeId) {
		return getPersistence().create(layoutPrototypeId);
	}

	public static com.liferay.portal.model.LayoutPrototype remove(
		long layoutPrototypeId)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(layoutPrototypeId);
	}

	public static com.liferay.portal.model.LayoutPrototype updateImpl(
		com.liferay.portal.model.LayoutPrototype layoutPrototype, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(layoutPrototype, merge);
	}

	public static com.liferay.portal.model.LayoutPrototype findByPrimaryKey(
		long layoutPrototypeId)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(layoutPrototypeId);
	}

	public static com.liferay.portal.model.LayoutPrototype fetchByPrimaryKey(
		long layoutPrototypeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutPrototypeId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.LayoutPrototype findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.LayoutPrototype findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.LayoutPrototype[] findByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(layoutPrototypeId, companyId,
			obc);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active, start, end, obc);
	}

	public static com.liferay.portal.model.LayoutPrototype findByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_First(companyId, active, obc);
	}

	public static com.liferay.portal.model.LayoutPrototype findByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_Last(companyId, active, obc);
	}

	public static com.liferay.portal.model.LayoutPrototype[] findByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_A_PrevAndNext(layoutPrototypeId, companyId, active,
			obc);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutPrototype> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_A(long companyId, boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_A(companyId, active);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_A(long companyId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_A(companyId, active);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutPrototypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutPrototypePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(LayoutPrototypePersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutPrototypePersistence _persistence;
}