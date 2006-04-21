/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.service.spring.RoleLocalServiceUtil;
import com.liferay.portal.service.spring.RoleService;

import java.util.List;

/**
 * <a href="RoleServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class RoleServiceImpl extends PrincipalBean implements RoleService {

	public Role addRole(String name)
		throws PortalException, SystemException {

		String companyId = getUser().getCompanyId();

		return RoleLocalServiceUtil.addRole(companyId, name);
	}

	public void deleteRole(String roleId)
		throws PortalException, SystemException {

		RoleLocalServiceUtil.deleteRole(roleId);
	}

	public Role getRole(String roleId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getRole(roleId);
	}

	public Role getRole(String companyId, String name)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getRole(companyId, name);
	}

	public List getUserRoles(String userId)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.getUserRoles(userId);
	}

	public Role updateRole(String roleId, String name)
		throws PortalException, SystemException {

		return RoleLocalServiceUtil.updateRole(roleId, name);
	}

}