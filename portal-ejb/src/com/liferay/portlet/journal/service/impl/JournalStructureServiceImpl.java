/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureService;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;

/**
 * <a href="JournalStructureServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalStructureServiceImpl
	extends PrincipalBean implements JournalStructureService {

	public JournalStructure addStructure(
			String structureId, boolean autoStructureId, String plid,
			String name, String description, String xsd,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.ADD_STRUCTURE);

		return JournalStructureLocalServiceUtil.addStructure(
			getUserId(), structureId, autoStructureId, plid, name, description,
			xsd, addCommunityPermissions, addGuestPermissions);
	}

	public JournalStructure addStructure(
			String structureId, boolean autoStructureId, String plid,
			String name, String description, String xsd,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		PortletPermission.check(
			getPermissionChecker(), plid, PortletKeys.JOURNAL,
			ActionKeys.ADD_STRUCTURE);

		return JournalStructureLocalServiceUtil.addStructure(
			getUserId(), structureId, autoStructureId, plid, name, description,
			xsd, communityPermissions, guestPermissions);
	}

	public void deleteStructure(
			String companyId, long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructurePermission.check(
			getPermissionChecker(), companyId, groupId, structureId,
			ActionKeys.DELETE);

		JournalStructureLocalServiceUtil.deleteStructure(
			companyId, groupId, structureId);
	}

	public JournalStructure getStructure(
			String companyId, long groupId, String structureId)
		throws PortalException, SystemException {

		JournalStructurePermission.check(
			getPermissionChecker(), companyId, groupId, structureId,
			ActionKeys.VIEW);

		return JournalStructureLocalServiceUtil.getStructure(
			companyId, groupId, structureId);
	}

	public JournalStructure updateStructure(
			long groupId, String structureId, String name, String description,
			String xsd)
		throws PortalException, SystemException {

		User user = getUser();

		JournalStructurePermission.check(
			getPermissionChecker(), user.getCompanyId(), groupId, structureId,
			ActionKeys.UPDATE);

		return JournalStructureLocalServiceUtil.updateStructure(
			user.getCompanyId(), groupId, structureId, name, description, xsd);
	}

}