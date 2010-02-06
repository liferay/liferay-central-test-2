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

package com.liferay.portlet.tasks.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.tasks.model.TasksProposal;

/**
 * <a href="TasksProposalPersistence.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TasksProposalPersistenceImpl
 * @see       TasksProposalUtil
 * @generated
 */
public interface TasksProposalPersistence extends BasePersistence<TasksProposal> {
	public void cacheResult(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal);

	public void cacheResult(
		java.util.List<com.liferay.portlet.tasks.model.TasksProposal> tasksProposals);

	public com.liferay.portlet.tasks.model.TasksProposal create(long proposalId);

	public com.liferay.portlet.tasks.model.TasksProposal remove(long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal fetchByPrimaryKey(
		long proposalId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByGroupId_First(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByGroupId_Last(
		long groupId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal[] findByGroupId_PrevAndNext(
		long proposalId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findByG_U(
		long groupId, long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal findByG_U_First(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByG_U_Last(
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal[] findByG_U_PrevAndNext(
		long proposalId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal findByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, java.lang.String classPK, boolean retrieveFromCache)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll()
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int start, int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tasks.model.TasksProposal> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	public void removeByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public void removeByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public void removeByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException;

	public void removeAll() throws com.liferay.portal.SystemException;

	public int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException;

	public int countByG_U(long groupId, long userId)
		throws com.liferay.portal.SystemException;

	public int countByC_C(long classNameId, java.lang.String classPK)
		throws com.liferay.portal.SystemException;

	public int countAll() throws com.liferay.portal.SystemException;
}