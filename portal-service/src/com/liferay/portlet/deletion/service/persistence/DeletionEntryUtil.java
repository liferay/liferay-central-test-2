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

package com.liferay.portlet.deletion.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.deletion.model.DeletionEntry;

import java.util.List;

/**
 * The persistence utility for the deletion entry service. This utility wraps {@link DeletionEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DeletionEntryPersistence
 * @see DeletionEntryPersistenceImpl
 * @generated
 */
public class DeletionEntryUtil {
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
	public static void clearCache(DeletionEntry deletionEntry) {
		getPersistence().clearCache(deletionEntry);
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
	public static List<DeletionEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DeletionEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DeletionEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DeletionEntry remove(DeletionEntry deletionEntry)
		throws SystemException {
		return getPersistence().remove(deletionEntry);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DeletionEntry update(DeletionEntry deletionEntry,
		boolean merge) throws SystemException {
		return getPersistence().update(deletionEntry, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DeletionEntry update(DeletionEntry deletionEntry,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(deletionEntry, merge, serviceContext);
	}

	/**
	* Caches the deletion entry in the entity cache if it is enabled.
	*
	* @param deletionEntry the deletion entry to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry) {
		getPersistence().cacheResult(deletionEntry);
	}

	/**
	* Caches the deletion entries in the entity cache if it is enabled.
	*
	* @param deletionEntries the deletion entries to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> deletionEntries) {
		getPersistence().cacheResult(deletionEntries);
	}

	/**
	* Creates a new deletion entry with the primary key. Does not add the deletion entry to the database.
	*
	* @param entryId the primary key for the new deletion entry
	* @return the new deletion entry
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry create(
		long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the deletion entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the deletion entry to remove
	* @return the deletion entry that was removed
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry remove(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence().remove(entryId);
	}

	public static com.liferay.portlet.deletion.model.DeletionEntry updateImpl(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(deletionEntry, merge);
	}

	/**
	* Finds the deletion entry with the primary key or throws a {@link com.liferay.portlet.deletion.NoSuchEntryException} if it could not be found.
	*
	* @param entryId the primary key of the deletion entry to find
	* @return the deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Finds the deletion entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the deletion entry to find
	* @return the deletion entry, or <code>null</code> if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry fetchByPrimaryKey(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	/**
	* Finds all the deletion entries where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Finds a range of all the deletion entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Finds the first deletion entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Finds the last deletion entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry[] findByCompanyId_PrevAndNext(
		long entryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(entryId, companyId,
			orderByComparator);
	}

	/**
	* Finds all the deletion entries where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the deletion entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry[] findByGroupId_PrevAndNext(
		long entryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(entryId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, classNameId);
	}

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C(
		long groupId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C(groupId, classNameId, start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C(
		long groupId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C(groupId, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_First(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_First(groupId, classNameId, orderByComparator);
	}

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_Last(
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_Last(groupId, classNameId, orderByComparator);
	}

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_PrevAndNext(
		long entryId, long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_PrevAndNext(entryId, groupId, classNameId,
			orderByComparator);
	}

	/**
	* Finds the deletion entry where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.deletion.NoSuchEntryException} if it could not be found.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Finds the deletion entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching deletion entry, or <code>null</code> if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Finds the deletion entry where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching deletion entry, or <code>null</code> if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Finds all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C(
		long groupId, java.util.Date createDate, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_C(groupId, createDate, classNameId);
	}

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C(
		long groupId, java.util.Date createDate, long classNameId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C(groupId, createDate, classNameId, start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C(
		long groupId, java.util.Date createDate, long classNameId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C(groupId, createDate, classNameId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_First(
		long groupId, java.util.Date createDate, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_C_First(groupId, createDate, classNameId,
			orderByComparator);
	}

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_Last(
		long groupId, java.util.Date createDate, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, createDate, classNameId,
			orderByComparator);
	}

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_C_PrevAndNext(
		long entryId, long groupId, java.util.Date createDate,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(entryId, groupId, createDate,
			classNameId, orderByComparator);
	}

	/**
	* Finds all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_P(
		long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_P(groupId, classNameId, parentId);
	}

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_P(
		long groupId, long classNameId, long parentId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_P(groupId, classNameId, parentId, start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_P(
		long groupId, long classNameId, long parentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_P(groupId, classNameId, parentId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_P_First(
		long groupId, long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_P_First(groupId, classNameId, parentId,
			orderByComparator);
	}

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_P_Last(
		long groupId, long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_P_Last(groupId, classNameId, parentId,
			orderByComparator);
	}

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_P_PrevAndNext(
		long entryId, long groupId, long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_P_PrevAndNext(entryId, groupId, classNameId,
			parentId, orderByComparator);
	}

	/**
	* Finds all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C_P(
		long groupId, java.util.Date createDate, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C_P(groupId, createDate, classNameId, parentId);
	}

	/**
	* Finds a range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C_P(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C_P(groupId, createDate, classNameId, parentId,
			start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findByG_C_C_P(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C_P(groupId, createDate, classNameId, parentId,
			start, end, orderByComparator);
	}

	/**
	* Finds the first deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_P_First(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_C_P_First(groupId, createDate, classNameId,
			parentId, orderByComparator);
	}

	/**
	* Finds the last deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a matching deletion entry could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry findByG_C_C_P_Last(
		long groupId, java.util.Date createDate, long classNameId,
		long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_C_P_Last(groupId, createDate, classNameId,
			parentId, orderByComparator);
	}

	/**
	* Finds the deletion entries before and after the current deletion entry in the ordered set where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param entryId the primary key of the current deletion entry
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next deletion entry
	* @throws com.liferay.portlet.deletion.NoSuchEntryException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry[] findByG_C_C_P_PrevAndNext(
		long entryId, long groupId, java.util.Date createDate,
		long classNameId, long parentId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		return getPersistence()
				   .findByG_C_C_P_PrevAndNext(entryId, groupId, createDate,
			classNameId, parentId, orderByComparator);
	}

	/**
	* Finds all the deletion entries.
	*
	* @return the deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the deletion entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the deletion entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the deletion entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Removes all the deletion entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the deletion entries where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C(groupId, classNameId);
	}

	/**
	* Removes the deletion entry where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.deletion.NoSuchEntryException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Removes all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_C(long groupId, java.util.Date createDate,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_C(groupId, createDate, classNameId);
	}

	/**
	* Removes all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_P(long groupId, long classNameId,
		long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_P(groupId, classNameId, parentId);
	}

	/**
	* Removes all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_C_P(long groupId, java.util.Date createDate,
		long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence()
			.removeByG_C_C_P(groupId, createDate, classNameId, parentId);
	}

	/**
	* Removes all the deletion entries from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the deletion entries where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Counts all the deletion entries where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Counts all the deletion entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C(groupId, classNameId);
	}

	/**
	* Counts all the deletion entries where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Counts all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_C(long groupId, java.util.Date createDate,
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_C(groupId, createDate, classNameId);
	}

	/**
	* Counts all the deletion entries where groupId = &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_P(long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_P(groupId, classNameId, parentId);
	}

	/**
	* Counts all the deletion entries where groupId = &#63; and createDate &ge; &#63; and classNameId = &#63; and parentId = &#63;.
	*
	* @param groupId the group ID to search with
	* @param createDate the create date to search with
	* @param classNameId the class name ID to search with
	* @param parentId the parent ID to search with
	* @return the number of matching deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_C_P(long groupId, java.util.Date createDate,
		long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByG_C_C_P(groupId, createDate, classNameId, parentId);
	}

	/**
	* Counts all the deletion entries.
	*
	* @return the number of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DeletionEntryPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DeletionEntryPersistence)PortalBeanLocatorUtil.locate(DeletionEntryPersistence.class.getName());

			ReferenceRegistry.registerReference(DeletionEntryUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DeletionEntryPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DeletionEntryUtil.class,
			"_persistence");
	}

	private static DeletionEntryPersistence _persistence;
}