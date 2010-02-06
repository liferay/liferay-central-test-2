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
 * <a href="RoleFinder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface RoleFinder {
	public int countByR_U(long roleId, long userId)
		throws com.liferay.portal.SystemException;

	public int countByU_G_R(long userId, long groupId, long roleId)
		throws com.liferay.portal.SystemException;

	public int countByC_N_D_T(long companyId, java.lang.String name,
		java.lang.String description, java.lang.Integer type,
		java.util.LinkedHashMap<String, Object> params)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findBySystem(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByUserGroupGroupRole(
		long userId, long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByUserGroupRole(
		long userId, long groupId) throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.Role findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchRoleException,
			com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByU_G(
		long userId, long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByU_G(
		long userId, long[] groupIds) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByU_G(
		long userId, java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.Role> findByC_N_D_T(
		long companyId, java.lang.String name, java.lang.String description,
		java.lang.Integer type, java.util.LinkedHashMap<String, Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public java.util.Map<String, java.util.List<String>> findByC_N_S_P(
		long companyId, java.lang.String name, int scope,
		java.lang.String primKey) throws com.liferay.portal.SystemException;
}