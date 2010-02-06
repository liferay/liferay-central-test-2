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

import com.liferay.portal.model.UserGroupGroupRole;

/**
 * <a href="UserGroupGroupRolePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       UserGroupGroupRolePersistenceImpl
 * @see       UserGroupGroupRoleUtil
 * @generated
 */
public interface UserGroupGroupRolePersistence extends BasePersistence<UserGroupGroupRole> {
	public void cacheResult(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroupGroupRole> userGroupGroupRoles);

	public com.liferay.portal.model.UserGroupGroupRole create(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK);

	public com.liferay.portal.model.UserGroupGroupRole remove(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole updateImpl(
		com.liferay.portal.model.UserGroupGroupRole userGroupGroupRole,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByUserGroupId(
		long userGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByUserGroupId_First(
		long userGroupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByUserGroupId_Last(
		long userGroupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole[] findByUserGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long userGroupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByU_G(
		long userGroupId, long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByU_G_First(
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByU_G_Last(
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole[] findByU_G_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long userGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findByG_R(
		long groupId, long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByG_R_First(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole findByG_R_Last(
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.UserGroupGroupRole[] findByG_R_PrevAndNext(
		com.liferay.portal.service.persistence.UserGroupGroupRolePK userGroupGroupRolePK,
		long groupId, long roleId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchUserGroupGroupRoleException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.UserGroupGroupRole> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByUserGroupId(long userGroupId)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByRoleId(long roleId)
		throws com.liferay.portal.SystemException;

	public void removeByU_G(long userGroupId, long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByG_R(long groupId, long roleId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByUserGroupId(long userGroupId)
		throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByRoleId(long roleId)
		throws com.liferay.portal.SystemException;

	public int countByU_G(long userGroupId, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByG_R(long groupId, long roleId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}