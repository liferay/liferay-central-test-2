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

import com.liferay.portal.model.Organization;

/**
 * <a href="OrganizationPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationPersistenceImpl
 * @see       OrganizationUtil
 * @generated
 */
public interface OrganizationPersistence extends BasePersistence<Organization> {
	public void cacheResult(com.liferay.portal.model.Organization organization);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.Organization> organizations);

	public com.liferay.portal.model.Organization create(long organizationId);

	public com.liferay.portal.model.Organization remove(long organizationId)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization updateImpl(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByPrimaryKey(
		long organizationId)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization fetchByPrimaryKey(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization[] findByCompanyId_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByLocations_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByLocations_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization[] findByLocations_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByC_P_First(
		long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByC_P_Last(
		long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization[] findByC_P_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Organization fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Organization> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void rebuildTree(long companyId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException;
}