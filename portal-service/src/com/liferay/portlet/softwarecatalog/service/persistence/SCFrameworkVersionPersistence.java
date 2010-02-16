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

import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

/**
 * <a href="SCFrameworkVersionPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersionPersistenceImpl
 * @see       SCFrameworkVersionUtil
 * @generated
 */
public interface SCFrameworkVersionPersistence extends BasePersistence<SCFrameworkVersion> {
	public void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion);

	public void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions);

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion create(
		long frameworkVersionId);

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion remove(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByPrimaryKey(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByPrimaryKey(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByG_A(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByG_A(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByG_A(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByG_A_First(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByG_A_Last(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_A(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_A(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getSCProductVersionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsSCProductVersion(long pk, long scProductVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsSCProductVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductVersion(long pk, long scProductVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductVersions(long pk, long[] scProductVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearSCProductVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductVersion(long pk, long scProductVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductVersions(long pk, long[] scProductVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setSCProductVersions(long pk, long[] scProductVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws com.liferay.portal.kernel.exception.SystemException;
}