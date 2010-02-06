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

/**
 * <a href="PermissionFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface PermissionFinder {
	public boolean containsPermissions_2(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		long groupId) throws com.liferay.portal.SystemException;

	public boolean containsPermissions_4(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, java.util.List<com.liferay.portal.model.Group> groups,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException;

	public int countByGroupsPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public int countByGroupsRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public int countByRolesPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		java.util.List<com.liferay.portal.model.Role> roles)
		throws com.liferay.portal.SystemException;

	public int countByUserGroupRole(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId, long groupId) throws com.liferay.portal.SystemException;

	public int countByUsersPermissions(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.SystemException;

	public int countByUsersRoles(
		java.util.List<com.liferay.portal.model.Permission> permissions,
		long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByA_R(
		java.lang.String actionId, long[] resourceIds)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByG_R(
		long groupId, long resourceId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByR_R(
		long roleId, long resourceId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByU_R(
		long userId, long resourceId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByO_G_R(
		long organizationId, long groupId, long resourceId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByU_A_R(
		long userId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByG_C_N_S_P(
		long groupId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Permission> findByU_C_N_S_P(
		long userId, long companyId, java.lang.String name, int scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException;
}