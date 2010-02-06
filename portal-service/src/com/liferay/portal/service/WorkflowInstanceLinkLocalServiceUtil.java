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
 * <a href="WorkflowInstanceLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WorkflowInstanceLinkLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkLocalService
 * @generated
 */
public class WorkflowInstanceLinkLocalServiceUtil {
	public static com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.SystemException {
		return getService().addWorkflowInstanceLink(workflowInstanceLink);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink createWorkflowInstanceLink(
		long workflowInstanceLinkId) {
		return getService().createWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static void deleteWorkflowInstanceLink(long workflowInstanceLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static void deleteWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.SystemException {
		getService().deleteWorkflowInstanceLink(workflowInstanceLink);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long workflowInstanceLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWorkflowInstanceLink(workflowInstanceLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> getWorkflowInstanceLinks(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWorkflowInstanceLinks(start, end);
	}

	public static int getWorkflowInstanceLinksCount()
		throws com.liferay.portal.SystemException {
		return getService().getWorkflowInstanceLinksCount();
	}

	public static com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink)
		throws com.liferay.portal.SystemException {
		return getService().updateWorkflowInstanceLink(workflowInstanceLink);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink updateWorkflowInstanceLink(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService()
				   .updateWorkflowInstanceLink(workflowInstanceLink, merge);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink addWorkflowInstanceLink(
		long userId, long companyId, long groupId, java.lang.String className,
		long classPK, long workflowInstanceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addWorkflowInstanceLink(userId, companyId, groupId,
			className, classPK, workflowInstanceId);
	}

	public static void deleteWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.deleteWorkflowInstanceLink(companyId, groupId, className, classPK);
	}

	public static java.lang.String getState(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getState(companyId, groupId, className, classPK);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink getWorkflowInstanceLink(
		long companyId, long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .getWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static boolean hasWorkflowInstanceLink(long companyId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .hasWorkflowInstanceLink(companyId, groupId, className,
			classPK);
	}

	public static void startWorkflowInstance(long companyId, long groupId,
		long userId, java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.startWorkflowInstance(companyId, groupId, userId, className,
			classPK);
	}

	public static WorkflowInstanceLinkLocalService getService() {
		if (_service == null) {
			_service = (WorkflowInstanceLinkLocalService)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkLocalService.class.getName());
		}

		return _service;
	}

	public void setService(WorkflowInstanceLinkLocalService service) {
		_service = service;
	}

	private static WorkflowInstanceLinkLocalService _service;
}