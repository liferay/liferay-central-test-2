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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.model.WorkflowLink;

import java.util.List;

/**
 * <a href="WorkflowLinkUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowLinkPersistence
 * @see       WorkflowLinkPersistenceImpl
 * @generated
 */
public class WorkflowLinkUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Object> findWithDynamicQuery(DynamicQuery dynamicQuery,
		int start, int end) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static WorkflowLink remove(WorkflowLink workflowLink)
		throws SystemException {
		return getPersistence().remove(workflowLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WorkflowLink update(WorkflowLink workflowLink, boolean merge)
		throws SystemException {
		return getPersistence().update(workflowLink, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.WorkflowLink workflowLink) {
		getPersistence().cacheResult(workflowLink);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowLink> workflowLinks) {
		getPersistence().cacheResult(workflowLinks);
	}

	public static com.liferay.portal.model.WorkflowLink create(
		long workflowLinkId) {
		return getPersistence().create(workflowLinkId);
	}

	public static com.liferay.portal.model.WorkflowLink remove(
		long workflowLinkId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence().remove(workflowLinkId);
	}

	public static com.liferay.portal.model.WorkflowLink updateImpl(
		com.liferay.portal.model.WorkflowLink workflowLink, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().updateImpl(workflowLink, merge);
	}

	public static com.liferay.portal.model.WorkflowLink findByPrimaryKey(
		long workflowLinkId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(workflowLinkId);
	}

	public static com.liferay.portal.model.WorkflowLink fetchByPrimaryKey(
		long workflowLinkId) throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(workflowLinkId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> findByCompanyId(
		long companyId) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end, obc);
	}

	public static com.liferay.portal.model.WorkflowLink findByCompanyId_First(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.WorkflowLink findByCompanyId_Last(
		long companyId, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.WorkflowLink[] findByCompanyId_PrevAndNext(
		long workflowLinkId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(workflowLinkId, companyId, obc);
	}

	public static com.liferay.portal.model.WorkflowLink findByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence().findByG_C_C(groupId, companyId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowLink fetchByG_C_C(
		long groupId, long companyId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByG_C_C(groupId, companyId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowLink fetchByG_C_C(
		long groupId, long companyId, long classNameId,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByG_C_C(groupId, companyId, classNameId,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> findAll(
		int start, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByG_C_C(long groupId, long companyId,
		long classNameId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		getPersistence().removeByG_C_C(groupId, companyId, classNameId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByG_C_C(long groupId, long companyId,
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().countByG_C_C(groupId, companyId, classNameId);
	}

	public static int countAll() throws com.liferay.portal.SystemException {
		return getPersistence().countAll();
	}

	public static WorkflowLinkPersistence getPersistence() {
		return _persistence;
	}

	public void setPersistence(WorkflowLinkPersistence persistence) {
		_persistence = persistence;
	}

	private static WorkflowLinkPersistence _persistence;
}