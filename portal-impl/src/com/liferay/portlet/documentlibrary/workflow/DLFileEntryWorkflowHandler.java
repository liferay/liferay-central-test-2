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

package com.liferay.portlet.documentlibrary.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.workflow.BaseWorkflowHandler;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;

import java.io.Serializable;

import java.util.Map;

/**
 * <a href="DLFileEntryWorkflowHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Jorge Ferrer
 */
public class DLFileEntryWorkflowHandler extends BaseWorkflowHandler {

	public static final String CLASS_NAME = DLFileEntry.class.getName();

	public String getClassName() {
		return CLASS_NAME;
	}

	public String getType() {
		return TYPE_DOCUMENT;
	}

	public DLFileEntry updateStatus(
			int status, Map<String, Serializable> workflowContext)
		throws PortalException, SystemException {

		long groupId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_GROUP_ID));
		long userId = GetterUtil.getLong(
			(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long classPK = GetterUtil.getLong(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);

		return DLFileEntryLocalServiceUtil.updateStatus(
			userId, classPK, status, serviceContext);
	}

}