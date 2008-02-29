/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tasks.service.persistence;

/**
 * <a href="TasksProposalPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface TasksProposalPersistence {
	public com.liferay.portlet.tasks.model.TasksProposal create(long proposalId);

	public com.liferay.portlet.tasks.model.TasksProposal remove(long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal remove(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal update(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal update(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal fetchByPrimaryKey(
		long proposalId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByCompanyId(
		long companyId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByCompanyId(
		long companyId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal[] findByCompanyId_PrevAndNext(
		long proposalId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByC_G(
		long companyId, long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByC_G(
		long companyId, long groupId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByC_G(
		long companyId, long groupId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByC_G_First(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByC_G_Last(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal[] findByC_G_PrevAndNext(
		long proposalId, long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByC_G_U(
		long companyId, long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByC_G_U(
		long companyId, long groupId, long userId, int begin, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByC_G_U(
		long companyId, long groupId, long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByC_G_U_First(
		long companyId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByC_G_U_Last(
		long companyId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal[] findByC_G_U_PrevAndNext(
		long proposalId, long companyId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int begin, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public void removeByC_G(long companyId, long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByC_G_U(long companyId, long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException;

	public int countByC_G(long companyId, long groupId)
		throws com.liferay.portal.SystemException;

	public int countByC_G_U(long companyId, long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}