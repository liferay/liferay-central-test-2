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
 * <a href="WorkflowLinkLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link WorkflowLinkLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowLinkLocalService
 * @generated
 */
public class WorkflowLinkLocalServiceUtil {
	public static com.liferay.portal.model.WorkflowLink addWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException {
		return getService().addWorkflowLink(workflowLink);
	}

	public static com.liferay.portal.model.WorkflowLink createWorkflowLink(
		long workflowLinkId) {
		return getService().createWorkflowLink(workflowLinkId);
	}

	public static void deleteWorkflowLink(long workflowLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWorkflowLink(workflowLinkId);
	}

	public static void deleteWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException {
		getService().deleteWorkflowLink(workflowLink);
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

	public static com.liferay.portal.model.WorkflowLink getWorkflowLink(
		long workflowLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWorkflowLink(workflowLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> getWorkflowLinks(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWorkflowLinks(start, end);
	}

	public static int getWorkflowLinksCount()
		throws com.liferay.portal.SystemException {
		return getService().getWorkflowLinksCount();
	}

	public static com.liferay.portal.model.WorkflowLink updateWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException {
		return getService().updateWorkflowLink(workflowLink);
	}

	public static com.liferay.portal.model.WorkflowLink updateWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateWorkflowLink(workflowLink, merge);
	}

	public static com.liferay.portal.model.WorkflowLink addWorkflowLink(
		long userId, long companyId, long groupId, long classNameId,
		java.lang.String definitionName, int definitionVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addWorkflowLink(userId, companyId, groupId, classNameId,
			definitionName, definitionVersion);
	}

	public static void deleteWorkflowLink(long userId, long companyId,
		long groupId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWorkflowLink(userId, companyId, groupId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowLink getWorkflowLink(
		long companyId, long groupId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWorkflowLink(companyId, groupId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowLink updateWorkflowLink(
		long userId, long companyId, long groupId, long classNameId,
		java.lang.String definitionName, int definitionVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateWorkflowLink(userId, companyId, groupId, classNameId,
			definitionName, definitionVersion);
	}

	public static WorkflowLinkLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("WorkflowLinkLocalService is not set");
		}

		return _service;
	}

	public void setService(WorkflowLinkLocalService service) {
		_service = service;
	}

	private static WorkflowLinkLocalService _service;
}