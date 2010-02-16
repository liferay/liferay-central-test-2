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

import com.liferay.portal.model.OrgGroupRole;

/**
 * <a href="OrgGroupRolePersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       OrgGroupRolePersistenceImpl
 * @see       OrgGroupRoleUtil
 * @generated
 */
public interface OrgGroupRolePersistence extends BasePersistence<OrgGroupRole> {
	public void cacheResult(com.liferay.portal.model.OrgGroupRole orgGroupRole);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.OrgGroupRole> orgGroupRoles);

	public com.liferay.portal.model.OrgGroupRole create(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK);

	public com.liferay.portal.model.OrgGroupRole remove(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole updateImpl(
		com.liferay.portal.model.OrgGroupRole orgGroupRole, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole findByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole fetchByPrimaryKey(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole[] findByGroupId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByRoleId(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByRoleId(
		long roleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findByRoleId(
		long roleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole findByRoleId_First(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole findByRoleId_Last(
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.OrgGroupRole[] findByRoleId_PrevAndNext(
		com.liferay.portal.service.persistence.OrgGroupRolePK orgGroupRolePK,
		long roleId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchOrgGroupRoleException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.OrgGroupRole> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByRoleId(long roleId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}