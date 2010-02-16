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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.WorkflowInstanceLink;

/**
 * <a href="WorkflowInstanceLinkPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkPersistenceImpl
 * @see       WorkflowInstanceLinkUtil
 * @generated
 */
public interface WorkflowInstanceLinkPersistence extends BasePersistence<WorkflowInstanceLink> {
	public void cacheResult(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowInstanceLink> workflowInstanceLinks);

	public com.liferay.portal.model.WorkflowInstanceLink create(
		long workflowInstanceLinkId);

	public com.liferay.portal.model.WorkflowInstanceLink remove(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink updateImpl(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink findByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink fetchByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink findByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void removeByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException;

	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countByG_C_C_C(long groupId, long companyId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}