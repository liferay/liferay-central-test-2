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

import com.liferay.portal.model.LayoutRevision;

/**
 * The persistence interface for the layout revision service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link LayoutRevisionUtil} to access the layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionPersistenceImpl
 * @see LayoutRevisionUtil
 * @generated
 */
public interface LayoutRevisionPersistence extends BasePersistence<LayoutRevision> {
	/**
	* Caches the layout revision in the entity cache if it is enabled.
	*
	* @param layoutRevision the layout revision to cache
	*/
	public void cacheResult(
		com.liferay.portal.model.LayoutRevision layoutRevision);

	/**
	* Caches the layout revisions in the entity cache if it is enabled.
	*
	* @param layoutRevisions the layout revisions to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutRevision> layoutRevisions);

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param revisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public com.liferay.portal.model.LayoutRevision create(long revisionId);

	/**
	* Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param revisionId the primary key of the layout revision to remove
	* @return the layout revision that was removed
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision remove(long revisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.LayoutRevision updateImpl(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout revision with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutRevisionException} if it could not be found.
	*
	* @param revisionId the primary key of the layout revision to find
	* @return the layout revision
	* @throws com.liferay.portal.NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision findByPrimaryKey(
		long revisionId)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout revision with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param revisionId the primary key of the layout revision to find
	* @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision fetchByPrimaryKey(
		long revisionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout revisions where branchId = &#63;.
	*
	* @param branchId the branch id to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByBranchId(
		long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByBranchId(
		long branchId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByBranchId(
		long branchId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByBranchId_First(
		long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByBranchId_Last(
		long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision[] findByBranchId_PrevAndNext(
		long revisionId, long branchId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout revisions where plid = &#63;.
	*
	* @param plid the plid to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByPlid_First(long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByPlid_Last(long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision[] findByPlid_PrevAndNext(
		long revisionId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout revisions where branchId = &#63; and plid = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P(
		long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P(
		long branchId, long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P(
		long branchId, long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByB_P_First(
		long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByB_P_Last(
		long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision[] findByB_P_PrevAndNext(
		long revisionId, long branchId, long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByB_P_H(long branchId,
		long plid, boolean head)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision fetchByB_P_H(long branchId,
		long plid, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the layout revision where branchId = &#63; and plid = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.LayoutRevision fetchByB_P_H(long branchId,
		long plid, boolean head, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P_S(
		long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P_S(
		long branchId, long plid, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findByB_P_S(
		long branchId, long plid, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByB_P_S_First(
		long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision findByB_P_S_Last(
		long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portal.model.LayoutRevision[] findByB_P_S_PrevAndNext(
		long revisionId, long branchId, long plid, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the layout revisions.
	*
	* @return the layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.LayoutRevision> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portal.model.LayoutRevision> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where branchId = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByBranchId(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where plid = &#63; from the database.
	*
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where branchId = &#63; and plid = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByB_P(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the layout revision where branchId = &#63; and plid = &#63; and head = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByB_P_H(long branchId, long plid, boolean head)
		throws com.liferay.portal.NoSuchLayoutRevisionException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63; from the database.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByB_P_S(long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the layout revisions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout revisions where branchId = &#63;.
	*
	* @param branchId the branch id to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByBranchId(long branchId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout revisions where plid = &#63;.
	*
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout revisions where branchId = &#63; and plid = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByB_P(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout revisions where branchId = &#63; and plid = &#63; and head = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param head the head to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByB_P_H(long branchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout revisions where branchId = &#63; and plid = &#63; and status = &#63;.
	*
	* @param branchId the branch id to search with
	* @param plid the plid to search with
	* @param status the status to search with
	* @return the number of matching layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countByB_P_S(long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the layout revisions.
	*
	* @return the number of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}