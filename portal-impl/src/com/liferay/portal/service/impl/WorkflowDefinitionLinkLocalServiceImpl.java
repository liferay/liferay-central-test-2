/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchWorkflowDefinitionLinkException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowEngineManagerUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.service.base.WorkflowDefinitionLinkLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;

/**
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class WorkflowDefinitionLinkLocalServiceImpl
	extends WorkflowDefinitionLinkLocalServiceBaseImpl {

	public WorkflowDefinitionLink addWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
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
			long companyId, long groupId, String className)
		throws PortalException, SystemException {

		try {
			WorkflowDefinitionLink workflowDefinitionLink =
				getWorkflowDefinitionLink(companyId, groupId, className, true);

			deleteWorkflowDefinitionLink(workflowDefinitionLink);
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
		}
	}

	public WorkflowDefinitionLink getDefaultWorkflowDefinitionLink(
			long companyId, String className)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		long classNameId = PortalUtil.getClassNameId(className);

		return workflowDefinitionLinkPersistence.findByG_C_C(
			WorkflowConstants.DEFAULT_GROUP_ID, companyId, classNameId);
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, String className)
		throws PortalException, SystemException {

		return getWorkflowDefinitionLink(companyId, groupId, className, false);
	}

	public WorkflowDefinitionLink getWorkflowDefinitionLink(
			long companyId, long groupId, String className, boolean strict)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			throw new NoSuchWorkflowDefinitionLinkException();
		}

		long classNameId = PortalUtil.getClassNameId(className);

		WorkflowDefinitionLink workflowDefinitionLink = null;

		if (groupId > 0) {
			Group group = groupLocalService.getGroup(groupId);

			if (group.isLayout()) {
				groupId = group.getParentGroupId();
			}

			workflowDefinitionLink =
				workflowDefinitionLinkPersistence.fetchByG_C_C(
					groupId, companyId, classNameId);
		}

		if (!strict && (workflowDefinitionLink == null)) {
			workflowDefinitionLink =
				workflowDefinitionLinkPersistence.fetchByG_C_C(
					WorkflowConstants.DEFAULT_GROUP_ID, companyId, classNameId);
		}

		if (workflowDefinitionLink == null) {
			throw new NoSuchWorkflowDefinitionLinkException(
				"No workflow for groupId=" + groupId + ", companyId=" +
					companyId + " and classNameId=" + classNameId);
		}

		return workflowDefinitionLink;
	}

	public int getWorkflowDefinitionLinksCount(
			long companyId, String workflowDefinitionName,
			int workflowDefinitionVersion)
		throws SystemException{

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return 0;
		}

		return workflowDefinitionLinkPersistence.countByC_W_W(
			companyId, workflowDefinitionName, workflowDefinitionVersion);
	}

	public boolean hasWorkflowDefinitionLink(
			long companyId, long groupId, String className)
		throws PortalException, SystemException {

		if (!WorkflowEngineManagerUtil.isDeployed()) {
			return false;
		}

		try {
			getWorkflowDefinitionLink(companyId, groupId, className);

			return true;
		}
		catch (NoSuchWorkflowDefinitionLinkException nswdle) {
			return false;
		}
	}

	public WorkflowDefinitionLink updateWorkflowDefinitionLink(
			long userId, long companyId, long groupId, String className,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowDefinitionLinkPersistence.fetchByG_C_C(
				groupId, companyId, classNameId);

		if (workflowDefinitionLink == null) {
			workflowDefinitionLink = addWorkflowDefinitionLink(
				userId, companyId, groupId, className, workflowDefinitionName,
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