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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.OrgLabor;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.OrgLaborServiceBaseImpl;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;

import java.util.List;

/**
 * <a href="OrgLaborServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class OrgLaborServiceImpl extends OrgLaborServiceBaseImpl {

	public OrgLabor addOrgLabor(
			long organizationId, int typeId, int sunOpen, int sunClose,
			int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
			int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
			int satOpen, int satClose)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.UPDATE);

		return orgLaborLocalService.addOrgLabor(
			organizationId, typeId, sunOpen, sunClose, monOpen, monClose,
			tueOpen, tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen,
			friClose, satOpen, satClose);
	}

	public void deleteOrgLabor(long orgLaborId)
		throws PortalException, SystemException {

		OrgLabor orgLabor = orgLaborPersistence.findByPrimaryKey(orgLaborId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), orgLabor.getOrganizationId(),
			ActionKeys.UPDATE);

		orgLaborLocalService.deleteOrgLabor(orgLaborId);
	}

	public OrgLabor getOrgLabor(long orgLaborId)
		throws PortalException, SystemException {

		OrgLabor orgLabor = orgLaborPersistence.findByPrimaryKey(orgLaborId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), orgLabor.getOrganizationId(),
			ActionKeys.VIEW);

		return orgLabor;
	}

	public List<OrgLabor> getOrgLabors(long organizationId)
		throws PortalException, SystemException {

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.VIEW);

		return orgLaborLocalService.getOrgLabors(organizationId);
	}

	public OrgLabor updateOrgLabor(
			long orgLaborId, int typeId, int sunOpen, int sunClose, int monOpen,
			int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose,
			int thuOpen, int thuClose, int friOpen, int friClose, int satOpen,
			int satClose)
		throws PortalException, SystemException {

		OrgLabor orgLabor = orgLaborPersistence.findByPrimaryKey(orgLaborId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), orgLabor.getOrganizationId(),
			ActionKeys.UPDATE);

		return orgLaborLocalService.updateOrgLabor(
			orgLaborId, typeId ,sunOpen, sunClose, monOpen, monClose, tueOpen,
			tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose,
			satOpen, satClose);
	}

}