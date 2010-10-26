/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the layout revision service. This utility wraps {@link LayoutRevisionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistence
 * @see LayoutRevisionPersistenceImpl
 * @generated
 */
public class LayoutRevisionUtil {
	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(LayoutRevision layoutRevision) {
		getPersistence().clearCache(layoutRevision);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LayoutRevision> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static LayoutRevision remove(LayoutRevision layoutRevision)
		throws SystemException {
		return getPersistence().remove(layoutRevision);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static LayoutRevision update(LayoutRevision layoutRevision,
		boolean merge) throws SystemException {
		return getPersistence().update(layoutRevision, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static LayoutRevision update(LayoutRevision layoutRevision,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(layoutRevision, merge, serviceContext);
	}

	/**
	* Caches the layout revision in the entity cache if it is enabled.
	*
	* @param layoutRevision the layout revision to cache
	*/
	public static void cacheResult(
		com.liferay.portal.model.LayoutRevision layoutRevision) {
		getPersistence().cacheResult(layoutRevision);
	}

	/**
	* Caches the layout revisions in the entity cache if it is enabled.
	*
	* @param layoutRevisions the layout revisions to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutRevision> layoutRevisions) {
		getPersistence().cacheResult(layoutRevisions);
	}

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param revisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public static com.liferay.portal.model.LayoutRevision create(
		long revisionId) {
		return getPersistence().create(revisionId);
	}

	/**
	* Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param revisionId the primary key of the layout revision to remove
	* @return the layout revision that was removed
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision remove(
		long revisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(revisionId);
	}

	public static com.liferay.portal.model.LayoutRevision updateImpl(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layoutRevision, merge);
	}

	/**
	* Finds the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param revisionId the primary key of the layout revision to find
	* @return the layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByPrimaryKey(
		long revisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(revisionId);
	}

	/**
	* Finds the layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param revisionId the primary key of the layout revision to find
	* @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision fetchByPrimaryKey(
		long revisionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(revisionId);
	}

	/**
	* Finds all the layout revisions where branchId = &#63;.
	*
	* @param branchId the branch id to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByBranchId(
		long branchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBranchId(branchId);
	}

	/**
	* Finds a range of all the layout revisions where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByBranchId(
		long branchId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBranchId(branchId, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByBranchId(
		long branchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByBranchId(branchId, start, end, orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByBranchId_First(
		long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBranchId_First(branchId, orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByBranchId_Last(
		long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByBranchId_Last(branchId, orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where branchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current layout revision
	* @param branchId the branch id to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByBranchId_PrevAndNext(
		long revisionId, long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByBranchId_PrevAndNext(revisionId, branchId,
			orderByComparator);
	}

	/**
	* Finds all the layout revisions where plid = &#63;.
	*
	* @param plid the plid to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid);
	}

	/**
	* Finds a range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current layout revision
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByPlid_PrevAndNext(
		long revisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlid_PrevAndNext(revisionId, plid, orderByComparator);
	}

	/**
	* Finds all the layout revisions where branchId = &#63; and plid = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P(
		long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByB_P(branchId, plid);
	}

	/**
	* Finds a range of all the layout revisions where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P(
		long branchId, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByB_P(branchId, plid, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P(
		long branchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P(branchId, plid, start, end, orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByB_P_First(
		long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P_First(branchId, plid, orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByB_P_Last(
		long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByB_P_Last(branchId, plid, orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where branchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current layout revision
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByB_P_PrevAndNext(
		long revisionId, long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P_PrevAndNext(revisionId, branchId, plid,
			orderByComparator);
	}

	/**
	* Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByB_P_H(
		long branchId, long plid, boolean head)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByB_P_H(branchId, plid, head);
	}

	/**
	* Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision fetchByB_P_H(
		long branchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByB_P_H(branchId, plid, head);
	}

	/**
	* Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision fetchByB_P_H(
		long branchId, long plid, boolean head, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByB_P_H(branchId, plid, head, retrieveFromCache);
	}

	/**
	* Finds all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P_S(
		long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByB_P_S(branchId, plid, status);
	}

	/**
	* Finds a range of all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P_S(
		long branchId, long plid, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByB_P_S(branchId, plid, status, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P_S(
		long branchId, long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P_S(branchId, plid, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByB_P_S_First(
		long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P_S_First(branchId, plid, status, orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByB_P_S_Last(
		long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P_S_Last(branchId, plid, status, orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param revisionId the primary key of the current layout revision
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByB_P_S_PrevAndNext(
		long revisionId, long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByB_P_S_PrevAndNext(revisionId, branchId, plid, status,
			orderByComparator);
	}

	/**
	* Finds all the layout revisions.
	*
	* @return the layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the layout revisions where branchId = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByBranchId(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByBranchId(branchId);
	}

	/**
	* Removes all the layout revisions where plid = &#63; from the database.
	*
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPlid(plid);
	}

	/**
	* Removes all the layout revisions where branchId = &#63; and plid = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByB_P(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByB_P(branchId, plid);
	}

	/**
	* Removes the layout revision where branchId = &#63; and plid = &#63; and head = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByB_P_H(long branchId, long plid, boolean head)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByB_P_H(branchId, plid, head);
	}

	/**
	* Removes all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByB_P_S(long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByB_P_S(branchId, plid, status);
	}

	/**
	* Removes all the layout revisions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the layout revisions where branchId = &#63;.
	*
	* @param branchId the branch id to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByBranchId(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByBranchId(branchId);
	}

	/**
	* Counts all the layout revisions where plid = &#63;.
	*
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPlid(plid);
	}

	/**
	* Counts all the layout revisions where branchId = &#63; and plid = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByB_P(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByB_P(branchId, plid);
	}

	/**
	* Counts all the layout revisions where branchId = &#63; and plid = &#63; and head = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByB_P_H(long branchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByB_P_H(branchId, plid, head);
	}

	/**
	* Counts all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByB_P_S(long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByB_P_S(branchId, plid, status);
	}

	/**
	* Counts all the layout revisions.
	*
	* @return the number of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LayoutRevisionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LayoutRevisionPersistence)PortalBeanLocatorUtil.locate(LayoutRevisionPersistence.class.getName());

			ReferenceRegistry.registerReference(LayoutRevisionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(LayoutRevisionPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(LayoutRevisionUtil.class,
			"_persistence");
	}

	private static LayoutRevisionPersistence _persistence;
}