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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="WorkflowLinkLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portal.service.impl.WorkflowLinkLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowLinkLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface WorkflowLinkLocalService {
	public com.liferay.portal.model.WorkflowLink addWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink createWorkflowLink(
		long workflowLinkId);

	public void deleteWorkflowLink(long workflowLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.WorkflowLink getWorkflowLink(
		long workflowLinkId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portal.model.WorkflowLink> getWorkflowLinks(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getWorkflowLinksCount()
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink updateWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink updateWorkflowLink(
		com.liferay.portal.model.WorkflowLink workflowLink, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink addWorkflowLink(long userId,
		long companyId, long groupId, long classNameId,
		java.lang.String definitionName, int definitionVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteWorkflowLink(long userId, long companyId, long groupId,
		long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.WorkflowLink getWorkflowLink(
		long companyId, long groupId, long classNameId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink updateWorkflowLink(
		long userId, long companyId, long groupId, long classNameId,
		java.lang.String definitionName, int definitionVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}