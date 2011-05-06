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

import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;

import java.util.List;

/**
 * The persistence utility for the d l document metadata set service. This utility wraps {@link DLDocumentMetadataSetPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentMetadataSetPersistence
 * @see DLDocumentMetadataSetPersistenceImpl
 * @generated
 */
public class DLDocumentMetadataSetUtil {
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
	public static void clearCache(DLDocumentMetadataSet dlDocumentMetadataSet) {
		getPersistence().clearCache(dlDocumentMetadataSet);
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
	public static List<DLDocumentMetadataSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<DLDocumentMetadataSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<DLDocumentMetadataSet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static DLDocumentMetadataSet remove(
		DLDocumentMetadataSet dlDocumentMetadataSet) throws SystemException {
		return getPersistence().remove(dlDocumentMetadataSet);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static DLDocumentMetadataSet update(
		DLDocumentMetadataSet dlDocumentMetadataSet, boolean merge)
		throws SystemException {
		return getPersistence().update(dlDocumentMetadataSet, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static DLDocumentMetadataSet update(
		DLDocumentMetadataSet dlDocumentMetadataSet, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(dlDocumentMetadataSet, merge, serviceContext);
	}

	/**
	* Caches the d l document metadata set in the entity cache if it is enabled.
	*
	* @param dlDocumentMetadataSet the d l document metadata set to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet dlDocumentMetadataSet) {
		getPersistence().cacheResult(dlDocumentMetadataSet);
	}

	/**
	* Caches the d l document metadata sets in the entity cache if it is enabled.
	*
	* @param dlDocumentMetadataSets the d l document metadata sets to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> dlDocumentMetadataSets) {
		getPersistence().cacheResult(dlDocumentMetadataSets);
	}

	/**
	* Creates a new d l document metadata set with the primary key. Does not add the d l document metadata set to the database.
	*
	* @param metadataSetId the primary key for the new d l document metadata set
	* @return the new d l document metadata set
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet create(
		long metadataSetId) {
		return getPersistence().create(metadataSetId);
	}

	/**
	* Removes the d l document metadata set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param metadataSetId the primary key of the d l document metadata set to remove
	* @return the d l document metadata set that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet remove(
		long metadataSetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence().remove(metadataSetId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet updateImpl(
		com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet dlDocumentMetadataSet,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(dlDocumentMetadataSet, merge);
	}

	/**
	* Finds the d l document metadata set with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException} if it could not be found.
	*
	* @param metadataSetId the primary key of the d l document metadata set to find
	* @return the d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByPrimaryKey(
		long metadataSetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence().findByPrimaryKey(metadataSetId);
	}

	/**
	* Finds the d l document metadata set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param metadataSetId the primary key of the d l document metadata set to find
	* @return the d l document metadata set, or <code>null</code> if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet fetchByPrimaryKey(
		long metadataSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(metadataSetId);
	}

	/**
	* Finds all the d l document metadata sets where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Finds a range of all the d l document metadata sets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @return the range of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Finds an ordered range of all the d l document metadata sets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Finds the first d l document metadata set in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Finds the last d l document metadata set in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Finds the d l document metadata sets before and after the current d l document metadata set in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param metadataSetId the primary key of the current d l document metadata set
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet[] findByUuid_PrevAndNext(
		long metadataSetId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByUuid_PrevAndNext(metadataSetId, uuid,
			orderByComparator);
	}

	/**
	* Finds the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException} if it could not be found.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence().findByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Finds the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata set, or <code>null</code> if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet fetchByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Finds the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata set, or <code>null</code> if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet fetchByD_F(
		long DDMStructureId, long fileVersionId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByD_F(DDMStructureId, fileVersionId, retrieveFromCache);
	}

	/**
	* Finds all the d l document metadata sets where documentTypeId = &#63;.
	*
	* @param documentTypeId the document type ID to search with
	* @return the matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByDocumentTypeId(documentTypeId);
	}

	/**
	* Finds a range of all the d l document metadata sets where documentTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the document type ID to search with
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @return the range of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByDocumentTypeId(documentTypeId, start, end);
	}

	/**
	* Finds an ordered range of all the d l document metadata sets where documentTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the document type ID to search with
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByDocumentTypeId(documentTypeId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first d l document metadata set in the ordered set where documentTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the document type ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByDocumentTypeId_First(
		long documentTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByDocumentTypeId_First(documentTypeId, orderByComparator);
	}

	/**
	* Finds the last d l document metadata set in the ordered set where documentTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentTypeId the document type ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByDocumentTypeId_Last(
		long documentTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByDocumentTypeId_Last(documentTypeId, orderByComparator);
	}

	/**
	* Finds the d l document metadata sets before and after the current d l document metadata set in the ordered set where documentTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param metadataSetId the primary key of the current d l document metadata set
	* @param documentTypeId the document type ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet[] findByDocumentTypeId_PrevAndNext(
		long metadataSetId, long documentTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByDocumentTypeId_PrevAndNext(metadataSetId,
			documentTypeId, orderByComparator);
	}

	/**
	* Finds all the d l document metadata sets where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByFileVersionId(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

	/**
	* Finds a range of all the d l document metadata sets where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID to search with
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @return the range of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByFileVersionId(
		long fileVersionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

	/**
	* Finds an ordered range of all the d l document metadata sets where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID to search with
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first d l document metadata set in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Finds the last d l document metadata set in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the file version ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Finds the d l document metadata sets before and after the current d l document metadata set in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param metadataSetId the primary key of the current d l document metadata set
	* @param fileVersionId the file version ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet[] findByFileVersionId_PrevAndNext(
		long metadataSetId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(metadataSetId,
			fileVersionId, orderByComparator);
	}

	/**
	* Finds all the d l document metadata sets.
	*
	* @return the d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the d l document metadata sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @return the range of d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the d l document metadata sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l document metadata sets to return
	* @param end the upper bound of the range of d l document metadata sets to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the d l document metadata sets where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Removes the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByD_F(long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException {
		getPersistence().removeByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Removes all the d l document metadata sets where documentTypeId = &#63; from the database.
	*
	* @param documentTypeId the document type ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByDocumentTypeId(long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByDocumentTypeId(documentTypeId);
	}

	/**
	* Removes all the d l document metadata sets where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Removes all the d l document metadata sets from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the d l document metadata sets where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Counts all the d l document metadata sets where DDMStructureId = &#63; and fileVersionId = &#63;.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static int countByD_F(long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByD_F(DDMStructureId, fileVersionId);
	}

	/**
	* Counts all the d l document metadata sets where documentTypeId = &#63;.
	*
	* @param documentTypeId the document type ID to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static int countByDocumentTypeId(long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByDocumentTypeId(documentTypeId);
	}

	/**
	* Counts all the d l document metadata sets where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Counts all the d l document metadata sets.
	*
	* @return the number of d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static DLDocumentMetadataSetPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (DLDocumentMetadataSetPersistence)PortalBeanLocatorUtil.locate(DLDocumentMetadataSetPersistence.class.getName());

			ReferenceRegistry.registerReference(DLDocumentMetadataSetUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(DLDocumentMetadataSetPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(DLDocumentMetadataSetUtil.class,
			"_persistence");
	}

	private static DLDocumentMetadataSetPersistence _persistence;
}