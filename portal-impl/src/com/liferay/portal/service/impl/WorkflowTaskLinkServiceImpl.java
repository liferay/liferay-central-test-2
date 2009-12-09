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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.base.WorkflowTaskLinkServiceBaseImpl;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

/**
 * <a href="WorkflowTaskLinkServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class WorkflowTaskLinkServiceImpl
	extends WorkflowTaskLinkServiceBaseImpl {

	public void assignWorkflowTaskToUser(
			long workflowTaskId, long assigneeUserId, String comment,
			Map<String, Object> context)
		throws PortalException, SystemException {

		PortletPermissionUtil.check(
			getPermissionChecker(), PortletKeys.WORKFLOW_TASKS,
			ActionKeys.ASSIGN_USER_TASKS);

		WorkflowTaskManagerUtil.assignWorkflowTaskToUser(
			getUserId(), workflowTaskId, assigneeUserId, comment, context);
	}

}