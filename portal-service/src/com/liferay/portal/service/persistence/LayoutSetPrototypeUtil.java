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
import com.liferay.portal.model.LayoutSetPrototype;

import java.util.List;

/**
 * <a href="LayoutSetPrototypeUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototypePersistence
 * @see       LayoutSetPrototypePersistenceImpl
 * @generated
 */
public class LayoutSetPrototypeUtil {
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
	public static LayoutSetPrototype remove(
		LayoutSetPrototype layoutSetPrototype) throws SystemException {
		return getPersistence().remove(layoutSetPrototype);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LayoutSetPrototype update(
		LayoutSetPrototype layoutSetPrototype, boolean merge)
		throws SystemException {
		return getPersistence().update(layoutSetPrototype, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype) {
		getPersistence().cacheResult(layoutSetPrototype);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutSetPrototype> layoutSetPrototypes) {
		getPersistence().cacheResult(layoutSetPrototypes);
	}

	public static com.liferay.portal.model.LayoutSetPrototype create(
		long layoutSetPrototypeId) {
		return getPersistence().create(layoutSetPrototypeId);
	}

	public static com.liferay.portal.model.LayoutSetPrototype remove(
		long layoutSetPrototypeId)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(layoutSetPrototypeId);
	}

	public static com.liferay.portal.model.LayoutSetPrototype updateImpl(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(layoutSetPrototype, merge);
	}

	public static com.liferay.portal.model.LayoutSetPrototype findByPrimaryKey(
		long layoutSetPrototypeId)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(layoutSetPrototypeId);
	}

	public static com.liferay.portal.model.LayoutSetPrototype fetchByPrimaryKey(
		long layoutSetPrototypeId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutSetPrototypeId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype[] findByCompanyId_PrevAndNext(
		long layoutSetPrototypeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(layoutSetPrototypeId,
			companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByC_A(
		long companyId, boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByC_A(
		long companyId, boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active, start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_A(companyId, active, start, end, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype findByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_First(companyId, active, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype findByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_A_Last(companyId, active, obc);
	}

	public static com.liferay.portal.model.LayoutSetPrototype[] findByC_A_PrevAndNext(
		long layoutSetPrototypeId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchLayoutSetPrototypeException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_A_PrevAndNext(layoutSetPrototypeId, companyId,
			active, obc);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSetPrototype> findAll(
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

	public static LayoutSetPrototypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutSetPrototypePersistence)PortalBeanLocatorUtil.locate(LayoutSetPrototypePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(LayoutSetPrototypePersistence persistence) {
		_persistence = persistence;
	}

	private static LayoutSetPrototypePersistence _persistence;
}