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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="WorkflowDefinitionLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WorkflowDefinitionLinkLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkLocalService
 * @generated
 */
public class WorkflowDefinitionLinkLocalServiceUtil {
	public static com.liferay.portal.model.WorkflowDefinitionLink addWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addWorkflowDefinitionLink(workflowDefinitionLink);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink createWorkflowDefinitionLink(
		long workflowDefinitionLinkId) {
		return getService()
				   .createWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public static void deleteWorkflowDefinitionLink(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public static void deleteWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWorkflowDefinitionLink(workflowDefinitionLink);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long workflowDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowDefinitionLink(workflowDefinitionLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowDefinitionLink> getWorkflowDefinitionLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowDefinitionLinks(start, end);
	}

	public static int getWorkflowDefinitionLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getWorkflowDefinitionLinksCount();
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateWorkflowDefinitionLink(workflowDefinitionLink);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		com.liferay.portal.model.WorkflowDefinitionLink workflowDefinitionLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateWorkflowDefinitionLink(workflowDefinitionLink, merge);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink addWorkflowDefinitionLink(
		long userId, long companyId, long groupId, java.lang.String className,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addWorkflowDefinitionLink(userId, companyId, groupId,
			className, workflowDefinitionName, workflowDefinitionVersion);
	}

	public static void deleteWorkflowDefinitionLink(long companyId,
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteWorkflowDefinitionLink(companyId, groupId, className);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink getWorkflowDefinitionLink(
		long companyId, long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getWorkflowDefinitionLink(companyId, groupId, className);
	}

	public static boolean hasWorkflowDefinitionLink(long companyId,
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .hasWorkflowDefinitionLink(companyId, groupId, className);
	}

	public static com.liferay.portal.model.WorkflowDefinitionLink updateWorkflowDefinitionLink(
		long userId, long companyId, long groupId, java.lang.String className,
		java.lang.String workflowDefinitionName, int workflowDefinitionVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateWorkflowDefinitionLink(userId, companyId, groupId,
			className, workflowDefinitionName, workflowDefinitionVersion);
	}

	public static WorkflowDefinitionLinkLocalService getService() {
		if (_service == null) {
			_service = (WorkflowDefinitionLinkLocalService)PortalBeanLocatorUtil.locate(WorkflowDefinitionLinkLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WorkflowDefinitionLinkLocalService service) {
		_service = service;
	}

	private static WorkflowDefinitionLinkLocalService _service;
}