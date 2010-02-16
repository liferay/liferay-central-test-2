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

import com.liferay.portal.model.UserGroup;

/**
 * <a href="UserGroupPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupPersistenceImpl
 * @see       UserGroupUtil
 * @generated
 */
public interface UserGroupPersistence extends BasePersistence<UserGroup> {
	public void cacheResult(com.liferay.portal.model.UserGroup userGroup);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	public com.liferay.portal.model.UserGroup create(long userGroupId);

	public com.liferay.portal.model.UserGroup remove(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup updateImpl(
		com.liferay.portal.model.UserGroup userGroup, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup findByPrimaryKey(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup fetchByPrimaryKey(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup[] findByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup findByC_P_First(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup findByC_P_Last(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup[] findByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup fetchByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.UserGroup fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
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
}