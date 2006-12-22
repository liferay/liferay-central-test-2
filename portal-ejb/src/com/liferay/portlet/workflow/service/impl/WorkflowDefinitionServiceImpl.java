/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.workflow.service.impl;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.workflow.NoSuchDefinitionException;
import com.liferay.portlet.workflow.model.WorkflowDefinition;
import com.liferay.portlet.workflow.service.WorkflowComponentServiceUtil;
import com.liferay.portlet.workflow.service.WorkflowDefinitionService;
import com.liferay.util.GetterUtil;

import java.rmi.RemoteException;

/**
 * <a href="WorkflowDefinitionServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WorkflowDefinitionServiceImpl
	extends PrincipalBean implements WorkflowDefinitionService {

	public WorkflowDefinition addDefinition(
			String xml, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addDefinition(
			xml, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public WorkflowDefinition addDefinition(
			String xml, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addDefinition(
			xml, null, null, communityPermissions, guestPermissions);
	}

	public WorkflowDefinition addDefinition(
			String xml, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		try {

			// Deploy xml

			User user = getUser();

			long definitionId = GetterUtil.getLong(
				WorkflowComponentServiceUtil.deploy(xml));

			// File

			String companyId = user.getCompanyId();
			String portletId = CompanyImpl.SYSTEM;
			String groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
			String repositoryId = CompanyImpl.SYSTEM;
			String dirName = "workflow/definitions";
			String fileName = dirName  + "/" + definitionId + ".xml";

			try {
				DLServiceUtil.addDirectory(
					companyId, repositoryId, dirName);
			}
			catch (DuplicateDirectoryException dde) {
			}

			DLServiceUtil.addFile(
				companyId, portletId, groupId, repositoryId, fileName,
				xml.getBytes());

			// Resources

			if ((addCommunityPermissions != null) &&
				(addGuestPermissions != null)) {

				addDefinitionResources(
					user, definitionId, addCommunityPermissions.booleanValue(),
					addGuestPermissions.booleanValue());
			}
			else {
				addDefinitionResources(
					user, definitionId, communityPermissions, guestPermissions);
			}

			return getDefinition(definitionId);
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	public void addDefinitionResources(
			User user, long definitionId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			user.getCompanyId(), null, user.getUserId(),
			WorkflowDefinition.class.getName(), definitionId, false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addDefinitionResources(
			User user, long definitionId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			user.getCompanyId(), null, user.getUserId(),
			WorkflowDefinition.class.getName(), definitionId,
			communityPermissions, guestPermissions);
	}

	public WorkflowDefinition getDefinition(long definitionId)
		throws PortalException, SystemException {

		try {
			String companyId = getUser().getCompanyId();
			String repositoryId = CompanyImpl.SYSTEM;
			String dirName = "workflow/definitions";
			String fileName = dirName  + "/" + definitionId + ".xml";

			String xml = new String(
				DLServiceUtil.getFile(companyId, repositoryId, fileName));

			WorkflowDefinition definition =
				(WorkflowDefinition)WorkflowComponentServiceUtil.getDefinition(
					definitionId);

			definition.setXml(xml);

			return definition;
		}
		catch (NoSuchFileException nsfe) {
			throw new NoSuchDefinitionException();
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

}