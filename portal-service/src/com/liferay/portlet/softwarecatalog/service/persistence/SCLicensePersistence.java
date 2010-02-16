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

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.softwarecatalog.model.SCLicense;

/**
 * <a href="SCLicensePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCLicensePersistenceImpl
 * @see       SCLicenseUtil
 * @generated
 */
public interface SCLicensePersistence extends BasePersistence<SCLicense> {
	public void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense);

	public void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> scLicenses);

	public com.liferay.portlet.softwarecatalog.model.SCLicense create(
		long licenseId);

	public com.liferay.portlet.softwarecatalog.model.SCLicense remove(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCLicense scLicense,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense findByPrimaryKey(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense fetchByPrimaryKey(
		long licenseId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense findByActive_First(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense findByActive_Last(
		boolean active, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense[] findByActive_PrevAndNext(
		long licenseId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findByA_R(
		boolean active, boolean recommended, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense findByA_R_First(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense findByA_R_Last(
		boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public com.liferay.portlet.softwarecatalog.model.SCLicense[] findByA_R_PrevAndNext(
		long licenseId, boolean active, boolean recommended,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchLicenseException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCLicense> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByA_R(boolean active, boolean recommended)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> getSCProductEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getSCProductEntriesSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsSCProductEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearSCProductEntries(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductEntry(long pk, long scProductEntryPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductEntry(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductEntry scProductEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setSCProductEntries(long pk, long[] scProductEntryPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setSCProductEntries(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductEntry> scProductEntries)
		throws com.liferay.portal.kernel.exception.SystemException;
}