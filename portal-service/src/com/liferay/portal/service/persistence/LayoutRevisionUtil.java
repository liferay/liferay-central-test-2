/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistence
 * @see LayoutRevisionPersistenceImpl
 * @generated
 */
public class LayoutRevisionUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public static com.liferay.portal.model.LayoutRevision create(
		long layoutRevisionId) {
		return getPersistence().create(layoutRevisionId);
	}

	/**
	* Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision to remove
	* @return the layout revision that was removed
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision remove(
		long layoutRevisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(layoutRevisionId);
	}

	public static com.liferay.portal.model.LayoutRevision updateImpl(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(layoutRevision, merge);
	}

	/**
	* Finds the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision to find
	* @return the layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByPrimaryKey(
		long layoutRevisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(layoutRevisionId);
	}

	/**
	* Finds the layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutRevisionId the primary key of the layout revision to find
	* @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision fetchByPrimaryKey(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(layoutRevisionId);
	}

	/**
	* Finds all the layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByLayoutSetBranchId(layoutSetBranchId);
	}

	/**
	* Finds a range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLayoutSetBranchId(layoutSetBranchId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByLayoutSetBranchId_First(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLayoutSetBranchId_First(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLayoutSetBranchId_Last(layoutSetBranchId,
			orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByLayoutSetBranchId_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByLayoutSetBranchId_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, orderByComparator);
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
	* @param layoutRevisionId the primary key of the current layout revision
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByPlid_PrevAndNext(
		long layoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlid_PrevAndNext(layoutRevisionId, plid,
			orderByComparator);
	}

	/**
	* Finds all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByL_P(layoutSetBranchId, plid);
	}

	/**
	* Finds a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByL_P(layoutSetBranchId, plid, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P(layoutSetBranchId, plid, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_P_First(
		long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_First(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_P_Last(
		long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_Last(layoutSetBranchId, plid, orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByL_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_PrevAndNext(layoutRevisionId, layoutSetBranchId,
			plid, orderByComparator);
	}

	/**
	* Finds the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param head the head to search with
	* @param plid the plid to search with
	* @return the matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_H_P(
		long layoutSetBranchId, boolean head, long plid)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Finds the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param head the head to search with
	* @param plid the plid to search with
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision fetchByL_H_P(
		long layoutSetBranchId, boolean head, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Finds the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param head the head to search with
	* @param plid the plid to search with
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision fetchByL_H_P(
		long layoutSetBranchId, boolean head, long plid,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByL_H_P(layoutSetBranchId, head, plid,
			retrieveFromCache);
	}

	/**
	* Finds all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	/**
	* Finds a range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId,
			plid, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_P(layoutSetBranchId, parentLayoutRevisionId,
			plid, start, end, orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_P_P_First(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_P_First(layoutSetBranchId,
			parentLayoutRevisionId, plid, orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_P_P_Last(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_P_Last(layoutSetBranchId, parentLayoutRevisionId,
			plid, orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByL_P_P_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_P_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, parentLayoutRevisionId, plid, orderByComparator);
	}

	/**
	* Finds all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByL_P_S(layoutSetBranchId, plid, status);
	}

	/**
	* Finds a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_S(layoutSetBranchId, plid, status, start, end);
	}

	/**
	* Finds an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_S(layoutSetBranchId, plid, status, start, end,
			orderByComparator);
	}

	/**
	* Finds the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_P_S_First(
		long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_S_First(layoutSetBranchId, plid, status,
			orderByComparator);
	}

	/**
	* Finds the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision findByL_P_S_Last(
		long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_S_Last(layoutSetBranchId, plid, status,
			orderByComparator);
	}

	/**
	* Finds the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param layoutRevisionId the primary key of the current layout revision
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision[] findByL_P_S_PrevAndNext(
		long layoutRevisionId, long layoutSetBranchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByL_P_S_PrevAndNext(layoutRevisionId,
			layoutSetBranchId, plid, status, orderByComparator);
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
	* Removes all the layout revisions where layoutSetBranchId = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByLayoutSetBranchId(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByLayoutSetBranchId(layoutSetBranchId);
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
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByL_P(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByL_P(layoutSetBranchId, plid);
	}

	/**
	* Removes the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param head the head to search with
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByL_H_P(long layoutSetBranchId, boolean head,
		long plid)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	/**
	* Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByL_P_S(long layoutSetBranchId, long plid,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByL_P_S(layoutSetBranchId, plid, status);
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
	* Counts all the layout revisions where layoutSetBranchId = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByLayoutSetBranchId(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByLayoutSetBranchId(layoutSetBranchId);
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
	* Counts all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByL_P(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByL_P(layoutSetBranchId, plid);
	}

	/**
	* Counts all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param head the head to search with
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByL_H_P(long layoutSetBranchId, boolean head,
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByL_H_P(layoutSetBranchId, head, plid);
	}

	/**
	* Counts all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param parentLayoutRevisionId the parent layout revision ID to search with
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByL_P_P(long layoutSetBranchId,
		long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByL_P_P(layoutSetBranchId, parentLayoutRevisionId, plid);
	}

	/**
	* Counts all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param layoutSetBranchId the layout set branch ID to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByL_P_S(long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByL_P_S(layoutSetBranchId, plid, status);
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