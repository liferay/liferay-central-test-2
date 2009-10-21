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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.WorkflowLink;

/**
 * <a href="WorkflowLinkPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowLinkPersistenceImpl
 * @see       WorkflowLinkUtil
 * @generated
 */
public interface WorkflowLinkPersistence extends BasePersistence<WorkflowLink> {
	public void cacheResult(com.liferay.portal.model.WorkflowLink workflowLink);

	public void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowLink> workflowLinks);

	public com.liferay.portal.model.WorkflowLink create(long workflowLinkId);

	public com.liferay.portal.model.WorkflowLink remove(long workflowLinkId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink updateImpl(
		com.liferay.portal.model.WorkflowLink workflowLink, boolean merge)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink findByPrimaryKey(
		long workflowLinkId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink fetchByPrimaryKey(
		long workflowLinkId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowLink> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowLink> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowLink> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink[] findByCompanyId_PrevAndNext(
		long workflowLinkId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink findByG_C_C(long groupId,
		long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink fetchByG_C_C(long groupId,
		long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.model.WorkflowLink fetchByG_C_C(long groupId,
		long companyId, long classNameId, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowLink> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowLink> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portal.model.WorkflowLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByG_C_C(long groupId, long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByG_C_C(long groupId, long companyId, long classNameId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}