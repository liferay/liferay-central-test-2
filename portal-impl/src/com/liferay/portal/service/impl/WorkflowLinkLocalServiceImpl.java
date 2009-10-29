/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchWorkflowLinkException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowLink;
import com.liferay.portal.service.base.WorkflowLinkLocalServiceBaseImpl;

import java.util.Date;

/**
 * <a href="WorkflowLinkLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class WorkflowLinkLocalServiceImpl
	extends WorkflowLinkLocalServiceBaseImpl {

	public WorkflowLink addWorkflowLink(
			long userId, long companyId, long groupId, long classNameId,
			String definitionName, int definitionVersion)
		throws PortalException, SystemException {
		long workflowLinkId = counterLocalService.increment();

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		WorkflowLink workflowLink = workflowLinkPersistence.create(
			workflowLinkId);

		workflowLink.setModifiedDate(now);
		workflowLink.setUserId(userId);
		workflowLink.setUserName(user.getFullName());
		workflowLink.setGroupId(groupId);
		workflowLink.setCompanyId(companyId);
		workflowLink.setClassNameId(classNameId);
		workflowLink.setDefinitionName(definitionName);
		workflowLink.setDefinitionVersion(definitionVersion);

		workflowLinkPersistence.update(workflowLink, false);

		return workflowLink;
	}

	public void deleteWorkflowLink(
			long userId, long companyId, long groupId, long classNameId)
		throws PortalException, SystemException {

		try {
			WorkflowLink workflowLink = getWorkflowLink(
				companyId, groupId, classNameId);

			deleteWorkflowLink(workflowLink);
		}
		catch (NoSuchWorkflowLinkException nswle) {
		}
	}

	public WorkflowLink getWorkflowLink(
			long companyId, long groupId, long classNameId)
		throws PortalException, SystemException {

		WorkflowLink workflowLink = null;

		if (groupId > 0) {
			workflowLink = workflowLinkPersistence.fetchByG_C_C(
				groupId, companyId, classNameId);
		}

		if (workflowLink == null) {
			workflowLink = workflowLinkPersistence.fetchByG_C_C(
				0, companyId, classNameId);
		}

		if (workflowLink == null) {
			throw new NoSuchWorkflowLinkException(
				"No workflow for groupId=" + groupId + ", companyId=" +
					companyId + " and classNameId=" + classNameId);
		}

		return workflowLink;
	}

	public WorkflowLink updateWorkflowLink(
			long userId, long companyId, long groupId, long classNameId,
			String definitionName, int definitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		WorkflowLink workflowLink = workflowLinkPersistence.fetchByG_C_C(
			groupId, companyId, classNameId);

		if (workflowLink == null) {
			workflowLink = addWorkflowLink(
				userId, companyId, groupId, classNameId, definitionName,
				definitionVersion);
		}

		workflowLink.setModifiedDate(now);
		workflowLink.setUserId(userId);
		workflowLink.setUserName(user.getFullName());
		workflowLink.setGroupId(groupId);
		workflowLink.setCompanyId(companyId);
		workflowLink.setClassNameId(classNameId);
		workflowLink.setDefinitionName(definitionName);
		workflowLink.setDefinitionVersion(definitionVersion);

		workflowLinkPersistence.update(workflowLink, false);

		return workflowLink;
	}

}