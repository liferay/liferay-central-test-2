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
import com.liferay.portal.model.UserGroup;

import java.util.List;

/**
 * <a href="UserGroupUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupPersistence
 * @see       UserGroupPersistenceImpl
 * @generated
 */
public class UserGroupUtil {
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
	public static UserGroup remove(UserGroup userGroup)
		throws SystemException {
		return getPersistence().remove(userGroup);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static UserGroup update(UserGroup userGroup, boolean merge)
		throws SystemException {
		return getPersistence().update(userGroup, merge);
	}

	public static void cacheResult(com.liferay.portal.model.UserGroup userGroup) {
		getPersistence().cacheResult(userGroup);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().cacheResult(userGroups);
	}

	public static com.liferay.portal.model.UserGroup create(long userGroupId) {
		return getPersistence().create(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup remove(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup updateImpl(
		com.liferay.portal.model.UserGroup userGroup, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(userGroup, merge);
	}

	public static com.liferay.portal.model.UserGroup findByPrimaryKey(
		long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup fetchByPrimaryKey(
		long userGroupId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroup findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.UserGroup findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.UserGroup[] findByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userGroupId, companyId, obc);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_P(companyId, parentUserGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_P(companyId, parentUserGroupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_P(companyId, parentUserGroupId, start, end, obc);
	}

	public static com.liferay.portal.model.UserGroup findByC_P_First(
		long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_P_First(companyId, parentUserGroupId, obc);
	}

	public static com.liferay.portal.model.UserGroup findByC_P_Last(
		long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_P_Last(companyId, parentUserGroupId, obc);
	}

	public static com.liferay.portal.model.UserGroup[] findByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_P_PrevAndNext(userGroupId, companyId,
			parentUserGroupId, obc);
	}

	public static com.liferay.portal.model.UserGroup findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	public static com.liferay.portal.model.UserGroup fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	public static com.liferay.portal.model.UserGroup fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_P(companyId, parentUserGroupId);
	}

	public static void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_N(companyId, name);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_P(companyId, parentUserGroupId);
	}

	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) throws com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, start, end);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsers(pk, start, end, obc);
	}

	public static int getUsersSize(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	public static boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	public static boolean containsUsers(long pk)
		throws com.liferay.portal.SystemException {
		return getPersistence().containsUsers(pk);
	}

	public static void addUser(long pk, long userPK)
		throws com.liferay.portal.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	public static void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		getPersistence().addUser(pk, user);
	}

	public static void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		getPersistence().addUsers(pk, users);
	}

	public static void clearUsers(long pk)
		throws com.liferay.portal.SystemException {
		getPersistence().clearUsers(pk);
	}

	public static void removeUser(long pk, long userPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	public static void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUser(pk, user);
	}

	public static void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	public static void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static UserGroupPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserGroupPersistence)PortalBeanLocatorUtil.locate(UserGroupPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(UserGroupPersistence persistence) {
		_persistence = persistence;
	}

	private static UserGroupPersistence _persistence;
}