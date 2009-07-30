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

package com.liferay.portlet.softwarecatalog.service.persistence;


/**
 * <a href="SCLicenseUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    SCLicensePersistence
 * @see    SCLicensePersistenceImpl
 * @generated
 */
public class SCLicenseUtil {
	public static void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense) {
		getPersistence().cacheResult(scLicense);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses) {
		getPersistence().cacheResult(scLicenses);
	}

	public static void clearCache() {
		getPersistence().clearCache();
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense create(
		long licenseId) {
		return getPersistence().create(licenseId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense remove(
		long licenseId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().remove(licenseId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense remove(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(scLicense);
	}

	/**
	 * @deprecated Use {@link #update(SCLicense, boolean merge)}.
	 */
	public static com.liferay.portlet.softwarecatalog.model.SCLicense update(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(scLicense);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  scLicense the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when scLicense is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public static com.liferay.portlet.softwarecatalog.model.SCLicense update(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(scLicense, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(scLicense, merge);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByPrimaryKey(
		long licenseId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByPrimaryKey(licenseId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense fetchByPrimaryKey(
		long licenseId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(licenseId);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active) throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByActive(active, start, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_First(active, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_Last(active, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense[] findByActive_PrevAndNext(
		long licenseId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByActive_PrevAndNext(licenseId, active, obc);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByA_R(active, recommended);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByA_R(active, recommended, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByA_R(active, recommended, start, end, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByA_R_First(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByA_R_First(active, recommended, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense findByA_R_Last(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence().findByA_R_Last(active, recommended, obc);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense[] findByA_R_PrevAndNext(
		long licenseId, boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException {
		return getPersistence()
				   .findByA_R_PrevAndNext(licenseId, active, recommended, obc);
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

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByActive(boolean active)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByActive(active);
	}

	public static void removeByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByA_R(active, recommended);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByActive(boolean active)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByActive(active);
	}

	public static int countByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByA_R(active, recommended);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getSCProductEntries(pk);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getSCProductEntries(pk, start, end);
	}

	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getSCProductEntries(pk, start, end, obc);
	}

	public static int getSCProductEntriesSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getSCProductEntriesSize(pk);
	}

	public static boolean containsSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSCProductEntry(pk, scProductEntryPK);
	}

	public static boolean containsSCProductEntries(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsSCProductEntries(pk);
	}

	public static void addSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addSCProductEntry(pk, scProductEntryPK);
	}

	public static void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		getPersistence().addSCProductEntry(pk, scProductEntry);
	}

	public static void addSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addSCProductEntries(pk, scProductEntryPKs);
	}

	public static void addSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().addSCProductEntries(pk, scProductEntries);
	}

	public static void clearSCProductEntries(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearSCProductEntries(pk);
	}

	public static void removeSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeSCProductEntry(pk, scProductEntryPK);
	}

	public static void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.SystemException {
		getPersistence().removeSCProductEntry(pk, scProductEntry);
	}

	public static void removeSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeSCProductEntries(pk, scProductEntryPKs);
	}

	public static void removeSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().removeSCProductEntries(pk, scProductEntries);
	}

	public static void setSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setSCProductEntries(pk, scProductEntryPKs);
	}

	public static void setSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.SystemException {
		getPersistence().setSCProductEntries(pk, scProductEntries);
	}

	public static SCLicensePersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(SCLicensePersistence persistence) {
		_persistence = persistence;
	}

	private static SCLicensePersistence _persistence;
}