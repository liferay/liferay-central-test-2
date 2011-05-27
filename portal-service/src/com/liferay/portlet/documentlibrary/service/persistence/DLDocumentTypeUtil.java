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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLDocumentType;

import java.util.List;

/**
 * The persistence utility for the d l document type service. This utility wraps {@link DLDocumentTypePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentTypePersistence
 * @see DLDocumentTypePersistenceImpl
 * @generated
 */
public class DLDocumentTypeUtil {
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
	public static void clearCache(DLDocumentType dlDocumentType) {
		getPersistence().clearCache(dlDocumentType);
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
	public static List<DLDocumentType> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLDocumentType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLDocumentType> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DLDocumentType remove(DLDocumentType dlDocumentType)
		throws SystemException {
		return getPersistence().remove(dlDocumentType);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLDocumentType update(DLDocumentType dlDocumentType,
		boolean merge) throws SystemException {
		return getPersistence().update(dlDocumentType, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DLDocumentType update(DLDocumentType dlDocumentType,
		boolean merge, ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(dlDocumentType, merge, serviceContext);
	}

	/**
	* Caches the d l document type in the entity cache if it is enabled.
	*
	* @param dlDocumentType the d l document type to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType) {
		getPersistence().cacheResult(dlDocumentType);
	}

	/**
	* Caches the d l document types in the entity cache if it is enabled.
	*
	* @param dlDocumentTypes the d l document types to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> dlDocumentTypes) {
		getPersistence().cacheResult(dlDocumentTypes);
	}

	/**
	* Creates a new d l document type with the primary key. Does not add the d l document type to the database.
	*
	* @param documentTypeId the primary key for the new d l document type
	* @return the new d l document type
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType create(
		long documentTypeId) {
		return getPersistence().create(documentTypeId);
	}

	/**
	* Removes the d l document type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param documentTypeId the primary key of the d l document type to remove
	* @return the d l document type that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType remove(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence().remove(documentTypeId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLDocumentType updateImpl(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlDocumentType, merge);
	}

	/**
	* Finds the d l document type with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException} if it could not be found.
	*
	* @param documentTypeId the primary key of the d l document type to find
	* @return the d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType findByPrimaryKey(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence().findByPrimaryKey(documentTypeId);
	}

	/**
	* Finds the d l document type with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param documentTypeId the primary key of the d l document type to find
	* @return the d l document type, or <code>null</code> if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType fetchByPrimaryKey(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(documentTypeId);
	}

	/**
	* Finds all the d l document types where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Finds a range of all the d l document types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Finds an ordered range of all the d l document types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Finds the first d l document type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a matching d l document type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Finds the last d l document type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a matching d l document type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Finds the d l document types before and after the current d l document type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the primary key of the current d l document type
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType[] findByGroupId_PrevAndNext(
		long documentTypeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(documentTypeId, groupId,
			orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the d l document types where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the d l document types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the d l document types where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Filters the d l document types before and after the current d l document type in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the primary key of the current d l document type
	* @param groupId the group ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType[] filterFindByGroupId_PrevAndNext(
		long documentTypeId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(documentTypeId, groupId,
			orderByComparator);
	}

	/**
	* Finds all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @return the matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_N_D(groupId, name, description);
	}

	/**
	* Finds a range of all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_N_D(groupId, name, description, start, end);
	}

	/**
	* Finds an ordered range of all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_N_D(groupId, name, description, start, end,
			orderByComparator);
	}

	/**
	* Finds the first d l document type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a matching d l document type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType findByG_N_D_First(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence()
				   .findByG_N_D_First(groupId, name, description,
			orderByComparator);
	}

	/**
	* Finds the last d l document type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a matching d l document type could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType findByG_N_D_Last(
		long groupId, java.lang.String name, java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence()
				   .findByG_N_D_Last(groupId, name, description,
			orderByComparator);
	}

	/**
	* Finds the d l document types before and after the current d l document type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the primary key of the current d l document type
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType[] findByG_N_D_PrevAndNext(
		long documentTypeId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence()
				   .findByG_N_D_PrevAndNext(documentTypeId, groupId, name,
			description, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @return the matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_N_D(groupId, name, description);
	}

	/**
	* Filters by the user's permissions and finds a range of all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_N_D(groupId, name, description, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> filterFindByG_N_D(
		long groupId, java.lang.String name, java.lang.String description,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_N_D(groupId, name, description, start, end,
			orderByComparator);
	}

	/**
	* Filters the d l document types before and after the current d l document type in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the primary key of the current d l document type
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document type
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType[] filterFindByG_N_D_PrevAndNext(
		long documentTypeId, long groupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentTypeException {
		return getPersistence()
				   .filterFindByG_N_D_PrevAndNext(documentTypeId, groupId,
			name, description, orderByComparator);
	}

	/**
	* Finds all the d l document types.
	*
	* @return the d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the d l document types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the d l document types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d l document types where groupId = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Removes all the d l document types where groupId = &#63; and name = &#63; and description = &#63; from the database.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_N_D(groupId, name, description);
	}

	/**
	* Removes all the d l document types from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the d l document types where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Filters by the user's permissions and counts all the d l document types where groupId = &#63;.
	*
	* @param groupId the group ID to search with
	* @return the number of matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Counts all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @return the number of matching d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_N_D(groupId, name, description);
	}

	/**
	* Filters by the user's permissions and counts all the d l document types where groupId = &#63; and name = &#63; and description = &#63;.
	*
	* @param groupId the group ID to search with
	* @param name the name to search with
	* @param description the description to search with
	* @return the number of matching d l document types that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_N_D(long groupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_N_D(groupId, name, description);
	}

	/**
	* Counts all the d l document types.
	*
	* @return the number of d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Gets all the d d m structures associated with the d l document type.
	*
	* @param pk the primary key of the d l document type to get the associated d d m structures for
	* @return the d d m structures associated with the d l document type
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getDDMStructures(pk);
	}

	/**
	* Gets a range of all the d d m structures associated with the d l document type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the d l document type to get the associated d d m structures for
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of d d m structures associated with the d l document type
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getDDMStructures(pk, start, end);
	}

	/**
	* Gets an ordered range of all the d d m structures associated with the d l document type.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param pk the primary key of the d l document type to get the associated d d m structures for
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m structures associated with the d l document type
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getDDMStructures(pk, start, end, orderByComparator);
	}

	/**
	* Gets the number of d d m structures associated with the d l document type.
	*
	* @param pk the primary key of the d l document type to get the number of associated d d m structures for
	* @return the number of d d m structures associated with the d l document type
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMStructuresSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getDDMStructuresSize(pk);
	}

	/**
	* Determines if the d d m structure is associated with the d l document type.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructurePK the primary key of the d d m structure
	* @return <code>true</code> if the d d m structure is associated with the d l document type; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsDDMStructure(long pk, long ddmStructurePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsDDMStructure(pk, ddmStructurePK);
	}

	/**
	* Determines if the d l document type has any d d m structures associated with it.
	*
	* @param pk the primary key of the d l document type to check for associations with d d m structures
	* @return <code>true</code> if the d l document type has any d d m structures associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsDDMStructures(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsDDMStructures(pk);
	}

	/**
	* Adds an association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructurePK the primary key of the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructure(long pk, long ddmStructurePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addDDMStructure(pk, ddmStructurePK);
	}

	/**
	* Adds an association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructure the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addDDMStructure(pk, ddmStructure);
	}

	/**
	* Adds an association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructurePKs the primary keys of the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructures(long pk, long[] ddmStructurePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addDDMStructures(pk, ddmStructurePKs);
	}

	/**
	* Adds an association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructures the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public static void addDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addDDMStructures(pk, ddmStructures);
	}

	/**
	* Clears all associations between the d l document type and its d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type to clear the associated d d m structures from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearDDMStructures(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearDDMStructures(pk);
	}

	/**
	* Removes the association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructurePK the primary key of the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public static void removeDDMStructure(long pk, long ddmStructurePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeDDMStructure(pk, ddmStructurePK);
	}

	/**
	* Removes the association between the d l document type and the d d m structure. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructure the d d m structure
	* @throws SystemException if a system exception occurred
	*/
	public static void removeDDMStructure(long pk,
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeDDMStructure(pk, ddmStructure);
	}

	/**
	* Removes the association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructurePKs the primary keys of the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public static void removeDDMStructures(long pk, long[] ddmStructurePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeDDMStructures(pk, ddmStructurePKs);
	}

	/**
	* Removes the association between the d l document type and the d d m structures. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type
	* @param ddmStructures the d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public static void removeDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeDDMStructures(pk, ddmStructures);
	}

	/**
	* Sets the d d m structures associated with the d l document type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type to set the associations for
	* @param ddmStructurePKs the primary keys of the d d m structures to be associated with the d l document type
	* @throws SystemException if a system exception occurred
	*/
	public static void setDDMStructures(long pk, long[] ddmStructurePKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setDDMStructures(pk, ddmStructurePKs);
	}

	/**
	* Sets the d d m structures associated with the d l document type, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the d l document type to set the associations for
	* @param ddmStructures the d d m structures to be associated with the d l document type
	* @throws SystemException if a system exception occurred
	*/
	public static void setDDMStructures(long pk,
		java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> ddmStructures)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setDDMStructures(pk, ddmStructures);
	}

	public static DLDocumentTypePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLDocumentTypePersistence)PortalBeanLocatorUtil.locate(DLDocumentTypePersistence.class.getName());

			ReferenceRegistry.registerReference(DLDocumentTypeUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DLDocumentTypePersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DLDocumentTypeUtil.class,
			"_persistence");
	}

	private static DLDocumentTypePersistence _persistence;
}