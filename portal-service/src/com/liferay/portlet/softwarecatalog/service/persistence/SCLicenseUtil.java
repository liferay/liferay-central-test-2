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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import com.liferay.portlet.softwarecatalog.model.SCLicense;

import java.util.List;

/**
 * <a href="SCLicenseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicensePersistence
 * @see       SCLicensePersistenceImpl
 * @generated
 */
public class SCLicenseUtil {
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
	public static SCLicense remove(SCLicense scLicense)
		throws SystemException {
		return getPersistence().remove(scLicense);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static SCLicense update(SCLicense scLicense, boolean merge)
		throws SystemException {
		return getPersistence().update(scLicense, merge);
	}

	public static void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		getPersistence().cacheResult(scLicense);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses) {
		getPersistence().cacheResult(scLicenses);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense create(
		long licenseId) {
		return getPersistence().create(licenseId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense remove(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().remove(licenseId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(scLicense, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByPrimaryKey(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByPrimaryKey(licenseId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense fetchByPrimaryKey(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(licenseId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByActive(active, start, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense[] findByActive_PrevAndNext(
		long licenseId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_PrevAndNext(licenseId, active, obc);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByA_R(active, recommended);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByA_R(active, recommended, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByA_R(active, recommended, start, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByA_R_First(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByA_R_First(active, recommended, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByA_R_Last(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByA_R_Last(active, recommended, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense[] findByA_R_PrevAndNext(
		long licenseId, boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .findByA_R_PrevAndNext(licenseId, active, recommended, obc);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByA_R(active, recommended);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByA_R(active, recommended);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductEntries(pk);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductEntries(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductEntries(pk, start, end, obc);
	}

	public static int getSCProductEntriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductEntriesSize(pk);
	}

	public static boolean containsSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCProductEntry(pk, scProductEntryPK);
	}

	public static boolean containsSCProductEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCProductEntries(pk);
	}

	public static void addSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductEntry(pk, scProductEntryPK);
	}

	public static void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductEntry(pk, scProductEntry);
	}

	public static void addSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductEntries(pk, scProductEntryPKs);
	}

	public static void addSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductEntries(pk, scProductEntries);
	}

	public static void clearSCProductEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearSCProductEntries(pk);
	}

	public static void removeSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductEntry(pk, scProductEntryPK);
	}

	public static void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductEntry(pk, scProductEntry);
	}

	public static void removeSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductEntries(pk, scProductEntryPKs);
	}

	public static void removeSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductEntries(pk, scProductEntries);
	}

	public static void setSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCProductEntries(pk, scProductEntryPKs);
	}

	public static void setSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCProductEntries(pk, scProductEntries);
	}

	public static SCLicensePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCLicensePersistence)PortalBeanLocatorUtil.locate(SCLicensePersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(SCLicensePersistence persistence) {
		_persistence = persistence;
	}

	private static SCLicensePersistence _persistence;
}