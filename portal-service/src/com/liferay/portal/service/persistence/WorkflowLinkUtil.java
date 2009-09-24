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
	public static void cacheResult(
		com.liferay.portal.model.WorkflowLink workflowLink) {
		getPersistence().cacheResult(workflowLink);
	}

	public static void cacheResult(
		java.util.List<com.liferay.portal.model.WorkflowLink> workflowLinks) {
		getPersistence().cacheResult(workflowLinks);
	}

	public static void clearCache() {
		getPersistence().clearCache();
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

	public static com.liferay.portal.model.WorkflowLink remove(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException {
		return getPersistence().remove(workflowLink);
	}

	/**
	 * @deprecated Use {@link #update(WorkflowLink, boolean merge)}.
	 */
	public static com.liferay.portal.model.WorkflowLink update(
		com.liferay.portal.model.WorkflowLink workflowLink)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(workflowLink);
	}

	/**
	 * Add, update, or merge, the entity. This method also calls the model
	 * listeners to trigger the proper events associated with adding, deleting,
	 * or updating an entity.
	 *
	 * @param  workflowLink the entity to add, update, or merge
	 * @param  merge boolean value for whether to merge the entity. The default
	 *         value is false. Setting merge to true is more expensive and
	 *         should only be true when workflowLink is transient. See
	 *         LEP-5473 for a detailed discussion of this method.
	 * @return the entity that was added, updated, or merged
	 */
	public static com.liferay.portal.model.WorkflowLink update(
		com.liferay.portal.model.WorkflowLink workflowLink, boolean merge)
		throws com.liferay.portal.SystemException {
		return getPersistence().update(workflowLink, merge);
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

	public static com.liferay.portal.model.WorkflowLink findByC_G_C(
		long companyId, long groupId, long classNameId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		return getPersistence().findByC_G_C(companyId, groupId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowLink fetchByC_G_C(
		long companyId, long groupId, long classNameId)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByC_G_C(companyId, groupId, classNameId);
	}

	public static com.liferay.portal.model.WorkflowLink fetchByC_G_C(
		long companyId, long groupId, long classNameId,
		boolean retrieveFromCache) throws com.liferay.portal.SystemException {
		return getPersistence()
				   .fetchByC_G_C(companyId, groupId, classNameId,
			retrieveFromCache);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> findWithDynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
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

	public static void removeByC_G_C(long companyId, long groupId,
		long classNameId)
		throws com.liferay.portal.NoSuchWorkflowLinkException,
			com.liferay.portal.SystemException {
		getPersistence().removeByC_G_C(companyId, groupId, classNameId);
	}

	public static void removeAll() throws com.liferay.portal.SystemException {
		getPersistence().removeAll();
	}

	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByC_G_C(long companyId, long groupId,
		long classNameId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_G_C(companyId, groupId, classNameId);
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