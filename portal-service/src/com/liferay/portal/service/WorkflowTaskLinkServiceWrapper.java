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

package com.liferay.portal.service;


/**
 * <a href="WorkflowTaskLinkServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WorkflowTaskLinkService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowTaskLinkService
 * @generated
 */
public class WorkflowTaskLinkServiceWrapper implements WorkflowTaskLinkService {
	public WorkflowTaskLinkServiceWrapper(
		WorkflowTaskLinkService workflowTaskLinkService) {
		_workflowTaskLinkService = workflowTaskLinkService;
	}

	public void assignWorkflowTaskToUser(long workflowTaskId,
		long assigneeUserId, java.lang.String comment,
		java.util.Map<String, Object> context)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_workflowTaskLinkService.assignWorkflowTaskToUser(workflowTaskId,
			assigneeUserId, comment, context);
	}

	public WorkflowTaskLinkService getWrappedWorkflowTaskLinkService() {
		return _workflowTaskLinkService;
	}

	private WorkflowTaskLinkService _workflowTaskLinkService;
}