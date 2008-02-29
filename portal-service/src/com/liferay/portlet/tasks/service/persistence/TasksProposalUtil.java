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
 * <a href="TasksProposalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksProposalUtil {
	public static com.liferay.portlet.tasks.model.TasksProposal create(
		long proposalId) {
		return getPersistence().create(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal remove(
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().remove(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal remove(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(tasksProposal);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal update(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(tasksProposal);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal update(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().update(tasksProposal, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateImpl(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal,
		boolean merge) throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(tasksProposal, merge);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByPrimaryKey(
		long proposalId)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByPrimaryKey(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByPrimaryKey(
		long proposalId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(proposalId);
	}

	public static java.util.List findByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(long companyId, int begin,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByCompanyId_PrevAndNext(
		long proposalId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(proposalId, companyId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	public static java.util.List findByC_G(long companyId, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G(companyId, groupId);
	}

	public static java.util.List findByC_G(long companyId, long groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G(companyId, groupId, begin, end);
	}

	public static java.util.List findByC_G(long companyId, long groupId,
		int begin, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G(companyId, groupId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_G_First(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByC_G_First(companyId, groupId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_G_Last(
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByC_G_Last(companyId, groupId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByC_G_PrevAndNext(
		long proposalId, long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByC_G_PrevAndNext(proposalId, companyId, groupId, obc);
	}

	public static java.util.List findByC_G_U(long companyId, long groupId,
		long userId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_G_U(companyId, groupId, userId);
	}

	public static java.util.List findByC_G_U(long companyId, long groupId,
		long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_G_U(companyId, groupId, userId, begin, end);
	}

	public static java.util.List findByC_G_U(long companyId, long groupId,
		long userId, int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findByC_G_U(companyId, groupId, userId, begin, end, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_G_U_First(
		long companyId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByC_G_U_First(companyId, groupId, userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal findByC_G_U_Last(
		long companyId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence().findByC_G_U_Last(companyId, groupId, userId, obc);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal[] findByC_G_U_PrevAndNext(
		long proposalId, long companyId, long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		return getPersistence()
				   .findByC_G_U_PrevAndNext(proposalId, companyId, groupId,
			userId, obc);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(queryInitializer);
	}

	public static java.util.List findWithDynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .findWithDynamicQuery(queryInitializer, begin, end);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portlet.tasks.NoSuchProposalException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	public static void removeByC_G(long companyId, long groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G(companyId, groupId);
	}

	public static void removeByC_G_U(long companyId, long groupId, long userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_G_U(companyId, groupId, userId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	public static int countByC_G(long companyId, long groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G(companyId, groupId);
	}

	public static int countByC_G_U(long companyId, long groupId, long userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_U(companyId, groupId, userId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static TasksProposalPersistence getPersistence() {
		return _getUtil()._persistence;
	}

	public void setPersistence(TasksProposalPersistence persistence) {
		_persistence = persistence;
	}

	private static TasksProposalUtil _getUtil() {
		if (_util == null) {
			_util = (TasksProposalUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = TasksProposalUtil.class.getName();
	private static TasksProposalUtil _util;
	private TasksProposalPersistence _persistence;
}