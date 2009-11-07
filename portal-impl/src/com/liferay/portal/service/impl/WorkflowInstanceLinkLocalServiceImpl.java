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

import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.base.WorkflowInstanceLinkLocalServiceBaseImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="WorkflowInstanceLinkLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class WorkflowInstanceLinkLocalServiceImpl
	extends WorkflowInstanceLinkLocalServiceBaseImpl {

	public WorkflowInstanceLink addWorkflowInstanceLink(
			long userId, long companyId, long groupId, long classNameId,
			long classPK, long workflowInstanceId)
		throws PortalException, SystemException {

		long workflowInstanceLinkId = counterLocalService.increment();

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		WorkflowInstanceLink workflowInstanceLink =
			workflowInstanceLinkPersistence.create(workflowInstanceLinkId);

		workflowInstanceLink.setCreateDate(now);
		workflowInstanceLink.setModifiedDate(now);
		workflowInstanceLink.setUserId(userId);
		workflowInstanceLink.setUserName(user.getFullName());
		workflowInstanceLink.setGroupId(groupId);
		workflowInstanceLink.setCompanyId(companyId);
		workflowInstanceLink.setClassNameId(classNameId);
		workflowInstanceLink.setClassPK(classPK);
		workflowInstanceLink.setWorkflowInstanceId(workflowInstanceId);

		workflowInstanceLinkPersistence.update(workflowInstanceLink, false);

		return workflowInstanceLink;
	}

	public void deleteWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		try {
			long classNameId = classNameLocalService.getClassNameId(className);

			WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
				companyId, groupId, classNameId, classPK);

			deleteWorkflowInstanceLink(workflowInstanceLink);
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
		}
	}

	public WorkflowInstanceLink getWorkflowInstanceLink(
			long companyId, long groupId, long classNameId, long classPK)
		throws PortalException, SystemException {

		return workflowInstanceLinkPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK)
		throws PortalException, SystemException {

		try {
			long classNameId = classNameLocalService.getClassNameId(className);

			WorkflowDefinitionLink workflowDefinitionLink =
				workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
					companyId, groupId, classNameId);

			String workflowDefinitionName =
				workflowDefinitionLink.getWorkflowDefinitionName();
			int workflowDefinitionVersion =
				workflowDefinitionLink.getWorkflowDefinitionVersion();

			String transitionName = null;
			Map<String, Object> context = new HashMap<String, Object>();

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.startWorkflowInstance(
					userId, workflowDefinitionName, workflowDefinitionVersion,
					transitionName, context);

			addWorkflowInstanceLink(
				userId, companyId, groupId, classNameId, classPK,
				workflowInstance.getWorkflowInstanceId());
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
			return;
		}
	}

}