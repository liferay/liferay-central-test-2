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
import com.liferay.portal.model.Organization;

import java.util.List;

/**
 * <a href="OrganizationUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrganizationPersistence
 * @see       OrganizationPersistenceImpl
 * @generated
 */
public class OrganizationUtil {
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
	public static Organization remove(Organization organization)
		throws SystemException {
		return getPersistence().remove(organization);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static Organization update(Organization organization, boolean merge)
		throws SystemException {
		return getPersistence().update(organization, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.Organization organization) {
		getPersistence().cacheResult(organization);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		getPersistence().cacheResult(organizations);
	}

	public static com.liferay.portal.model.Organization create(
		long organizationId) {
		return getPersistence().create(organizationId);
	}

	public static com.liferay.portal.model.Organization remove(
		long organizationId)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(organizationId);
	}

	public static com.liferay.portal.model.Organization updateImpl(
		com.liferay.portal.model.Organization organization, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(organization, merge);
	}

	public static com.liferay.portal.model.Organization findByPrimaryKey(
		long organizationId)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(organizationId);
	}

	public static com.liferay.portal.model.Organization fetchByPrimaryKey(
		long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(organizationId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Organization findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Organization findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Organization[] findByCompanyId_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(organizationId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLocations(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLocations(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByLocations(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLocations(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.Organization findByLocations_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLocations_First(companyId, obc);
	}

	public static com.liferay.portal.model.Organization findByLocations_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLocations_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Organization[] findByLocations_PrevAndNext(
		long organizationId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLocations_PrevAndNext(organizationId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(companyId, parentOrganizationId);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(companyId, parentOrganizationId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findByC_P(
		long companyId, long parentOrganizationId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(companyId, parentOrganizationId, start, end, obc);
	}

	public static com.liferay.portal.model.Organization findByC_P_First(
		long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_First(companyId, parentOrganizationId, obc);
	}

	public static com.liferay.portal.model.Organization findByC_P_Last(
		long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_Last(companyId, parentOrganizationId, obc);
	}

	public static com.liferay.portal.model.Organization[] findByC_P_PrevAndNext(
		long organizationId, long companyId, long parentOrganizationId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_PrevAndNext(organizationId, companyId,
			parentOrganizationId, obc);
	}

	public static com.liferay.portal.model.Organization findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Organization fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static com.liferay.portal.model.Organization fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.Organization> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.Organization> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByLocations(companyId);
	}

	public static void removeByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(companyId, parentOrganizationId);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchOrganizationException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByLocations(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLocations(companyId);
	}

	public static int countByC_P(long companyId, long parentOrganizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(companyId, parentOrganizationId);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end, obc);
	}

	public static int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	public static boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	public static boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroups(pk);
	}

	public static void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, groupPK);
	}

	public static void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, group);
	}

	public static void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groupPKs);
	}

	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groups);
	}

	public static void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearGroups(pk);
	}

	public static void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, groupPK);
	}

	public static void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, group);
	}

	public static void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groups);
	}

	public static void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groupPKs);
	}

	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end, obc);
	}

	public static int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	public static void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, user);
	}

	public static void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	public static void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, user);
	}

	public static void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	public static void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static void rebuildTree(long companyId, boolean force)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().rebuildTree(companyId, force);
	}

	public static OrganizationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (OrganizationPersistence)PortalBeanLocatorUtil.locate(OrganizationPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(OrganizationPersistence persistence) {
		_persistence = persistence;
	}

	private static OrganizationPersistence _persistence;
}