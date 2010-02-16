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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.WorkflowInstanceLink;

import java.util.List;

/**
 * <a href="WorkflowInstanceLinkUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowInstanceLinkPersistence
 * @see       WorkflowInstanceLinkPersistenceImpl
 * @generated
 */
public class WorkflowInstanceLinkUtil {
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
	public static WorkflowInstanceLink remove(
		WorkflowInstanceLink workflowInstanceLink) throws SystemException {
		return getPersistence().remove(workflowInstanceLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static WorkflowInstanceLink update(
		WorkflowInstanceLink workflowInstanceLink, boolean merge)
		throws SystemException {
		return getPersistence().update(workflowInstanceLink, merge);
	}

	public static void cacheResult(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink) {
		getPersistence().cacheResult(workflowInstanceLink);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowInstanceLink> workflowInstanceLinks) {
		getPersistence().cacheResult(workflowInstanceLinks);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink create(
		long workflowInstanceLinkId) {
		return getPersistence().create(workflowInstanceLinkId);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink remove(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(workflowInstanceLinkId);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink updateImpl(
		com.liferay.portal.model.WorkflowInstanceLink workflowInstanceLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(workflowInstanceLink, merge);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink findByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(workflowInstanceLinkId);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink fetchByPrimaryKey(
		long workflowInstanceLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(workflowInstanceLinkId);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink findByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	public static com.liferay.portal.model.WorkflowInstanceLink fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_C_C(groupId, companyId, classNameId, classPK,
			retrieveFromCache);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	public static java.util.List<com.liferay.portal.model.WorkflowInstanceLink> findAll(
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, obc);
	}

	public static void removeByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.NoSuchWorkflowInstanceLinkException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	public static int countByG_C_C_C(long groupId, long companyId,
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_C_C_C(groupId, companyId, classNameId, classPK);
	}

	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static WorkflowInstanceLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (WorkflowInstanceLinkPersistence)PortalBeanLocatorUtil.locate(WorkflowInstanceLinkPersistence.class.getName());
		}

		return _persistence;
	}

	public void setPersistence(WorkflowInstanceLinkPersistence persistence) {
		_persistence = persistence;
	}

	private static WorkflowInstanceLinkPersistence _persistence;
}