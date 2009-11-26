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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.base.WorkflowDefinitionLinkLocalServiceBaseImpl;

import java.util.Date;

/**
 * <a href="WorkflowDefinitionLinkLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 */
public class WorkflowDefinitionLinkLocalServiceImpl
	extends WorkflowDefinitionLinkLocalServiceBaseImpl {

	public WorkflowDefinitionLink addWorkflowDefinitionLink(
			long userId, long companyId, long groupId, long classNameId,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long workflowDefinitionLinkId = counterLocalService.increment();

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.create(workflowDefinitionLinkId);

		workflowDefinitionLink.setCreateDate(now);
		workflowDefinitionLink.setModifiedDate(now);
		workflowDefinitionLink.setUserId(userId);
		workflowDefinitionLink.setUserName(user.getFullName());
		workflowDefinitionLink.setGroupId(groupId);
		workflowDefinitionLink.setCompanyId(companyId);
		workflowDefinitionLink.setClassNameId(classNameId);
		workflowDefinitionLink.setWorkflowDefinitionName(
			workflowDefinitionName);
		workflowDefinitionLink.setWorkflowDefinitionVersion(
			workflowDefinitionVersion);

		workflowDefinitionLinkPersistence.update(workflowDefinitionLink, false);

		return workflowDefinitionLink;
	}

	public void deleteWorkflowDefinitionLink(
			long companyId, long groupId, long classNameId)
		throws PortalException, SystemException {

		try {
			WorkflowDefinitionLink workflowDefinitionLink =
				getWorkflowDefinitionLink(companyId, groupId, classNameId);

			deleteWorkflowDefinitionLink(workflowDefinitionLink);
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
		}
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, long classNameId)
		throws PortalException, SystemException {

		WorkflowDefinitionLink workflowDefinitionLink = null;

		if (groupId > 0) {
			workflowDefinitionLink =
				workflowDefinitionLinkPersistence.fetchByG_C_C(
					groupId, companyId, classNameId);
		}

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink =
				workflowDefinitionLinkPersistence.fetchByG_C_C(
					0, companyId, classNameId);
		}

		if (workflowDefinitionLink == null) {
			throw new NoSuchWorkflowDefinitionLinkException(
				"No workflow for groupId=" + groupId + ", companyId=" +
					companyId + " and classNameId=" + classNameId);
		}

		return workflowDefinitionLink;
	}

	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, long classNameId)
		throws PortalException, SystemException {

		try {
			getWorkflowDefinitionLink(companyId, groupId, classNameId);

			return true;
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return false;
		}
	}

	public WorkflowDefinitionLink updateWorkflowDefinitionLink(
			long userId, long companyId, long groupId, long classNameId,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.fetchByG_C_C(
				groupId, companyId, classNameId);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink = addWorkflowDefinitionLink(
				userId, companyId, groupId, classNameId, workflowDefinitionName,
				workflowDefinitionVersion);
		}

		workflowDefinitionLink.setModifiedDate(now);
		workflowDefinitionLink.setUserId(userId);
		workflowDefinitionLink.setUserName(user.getFullName());
		workflowDefinitionLink.setGroupId(groupId);
		workflowDefinitionLink.setCompanyId(companyId);
		workflowDefinitionLink.setClassNameId(classNameId);
		workflowDefinitionLink.setWorkflowDefinitionName(
			workflowDefinitionName);
		workflowDefinitionLink.setWorkflowDefinitionVersion(
			workflowDefinitionVersion);

		workflowDefinitionLinkPersistence.update(workflowDefinitionLink, false);

		return workflowDefinitionLink;
	}

}