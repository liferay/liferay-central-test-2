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

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.NoSuchWorkflowInstanceLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.ContextConstants;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowInstanceLink;
import com.liferay.portal.service.base.WorkflowInstanceLinkLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

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
			long userId, long companyId, long groupId, String className,
			long classPK, long workflowInstanceId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long workflowInstanceLinkId = counterLocalService.increment();

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
			WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
				companyId, groupId, className, classPK);

			deleteWorkflowInstanceLink(workflowInstanceLink);

			WorkflowInstanceManagerUtil.deleteWorkflowInstance(
				workflowInstanceLink.getWorkflowInstanceId());
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
		}
	}

	public String getState(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		WorkflowInstanceLink workflowInstanceLink = getWorkflowInstanceLink(
			companyId, groupId, className, classPK);

		WorkflowInstance workflowInstance =
			WorkflowInstanceManagerUtil.getWorkflowInstance(
				workflowInstanceLink.getWorkflowInstanceId());

		return workflowInstance.getState();
	}

	public WorkflowInstanceLink getWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return workflowInstanceLinkPersistence.findByG_C_C_C(
			groupId, companyId, classNameId, classPK);
	}

	public boolean hasWorkflowInstanceLink(
			long companyId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		try {
			getWorkflowInstanceLink(companyId, groupId, className, classPK);

			return true;
		}
		catch (NoSuchWorkflowInstanceLinkException nswile) {
			return false;
		}
	}

	public void startWorkflowInstance(
			long companyId, long groupId, long userId, String className,
			long classPK)
		throws PortalException, SystemException {

		try {
			WorkflowDefinitionLink workflowDefinitionLink =
				workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
					companyId, groupId, className);

			String workflowDefinitionName =
				workflowDefinitionLink.getWorkflowDefinitionName();
			int workflowDefinitionVersion =
				workflowDefinitionLink.getWorkflowDefinitionVersion();

			Map<String, Object> context = new HashMap<String, Object>();

			context.put(ContextConstants.COMPANY_ID, companyId);
			context.put(ContextConstants.GROUP_ID, groupId);
			context.put(ContextConstants.ENTRY_CLASS_NAME, className);
			context.put(ContextConstants.ENTRY_CLASS_PK, classPK);

			WorkflowInstance workflowInstance =
				WorkflowInstanceManagerUtil.startWorkflowInstance(
					userId, workflowDefinitionName, workflowDefinitionVersion,
					null, context);

			addWorkflowInstanceLink(
				userId, companyId, groupId, className, classPK,
				workflowInstance.getWorkflowInstanceId());
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return;
		}
	}

}