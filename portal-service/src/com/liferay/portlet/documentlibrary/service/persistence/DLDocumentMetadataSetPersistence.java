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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;

/**
 * The persistence interface for the d l document metadata set service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentMetadataSetPersistenceImpl
 * @see DLDocumentMetadataSetUtil
 * @generated
 */
public interface DLDocumentMetadataSetPersistence extends BasePersistence<DLDocumentMetadataSet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLDocumentMetadataSetUtil} to access the d l document metadata set persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d l document metadata set in the entity cache if it is enabled.
	*
	* @param dlDocumentMetadataSet the d l document metadata set to cache
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet dlDocumentMetadataSet);

	/**
	* Caches the d l document metadata sets in the entity cache if it is enabled.
	*
	* @param dlDocumentMetadataSets the d l document metadata sets to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> dlDocumentMetadataSets);

	/**
	* Creates a new d l document metadata set with the primary key. Does not add the d l document metadata set to the database.
	*
	* @param documentMetadataSetId the primary key for the new d l document metadata set
	* @return the new d l document metadata set
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet create(
		long documentMetadataSetId);

	/**
	* Removes the d l document metadata set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param documentMetadataSetId the primary key of the d l document metadata set to remove
	* @return the d l document metadata set that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet remove(
		long documentMetadataSetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet updateImpl(
		com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet dlDocumentMetadataSet,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l document metadata set with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException} if it could not be found.
	*
	* @param documentMetadataSetId the primary key of the d l document metadata set to find
	* @return the d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByPrimaryKey(
		long documentMetadataSetId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds the d l document metadata set with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param documentMetadataSetId the primary key of the d l document metadata set to find
	* @return the d l document metadata set, or <code>null</code> if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet fetchByPrimaryKey(
		long documentMetadataSetId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l document metadata sets where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

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
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds the d l document metadata sets before and after the current d l document metadata set in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentMetadataSetId the primary key of the current d l document metadata set
	* @param uuid the uuid to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet[] findByUuid_PrevAndNext(
		long documentMetadataSetId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds all the d l document metadata sets where documentTypeId = &#63;.
	*
	* @param documentTypeId the document type ID to search with
	* @return the matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByDocumentTypeId(
		long documentTypeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByDocumentTypeId_First(
		long documentTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

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
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByDocumentTypeId_Last(
		long documentTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds the d l document metadata sets before and after the current d l document metadata set in the ordered set where documentTypeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentMetadataSetId the primary key of the current d l document metadata set
	* @param documentTypeId the document type ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet[] findByDocumentTypeId_PrevAndNext(
		long documentMetadataSetId, long documentTypeId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds all the d l document metadata sets where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByFileVersionId(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByFileVersionId(
		long fileVersionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

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
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds the d l document metadata sets before and after the current d l document metadata set in the ordered set where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param documentMetadataSetId the primary key of the current d l document metadata set
	* @param fileVersionId the file version ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a d l document metadata set with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet[] findByFileVersionId_PrevAndNext(
		long documentMetadataSetId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException} if it could not be found.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata set
	* @throws com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet findByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Finds the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata set, or <code>null</code> if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet fetchByD_F(
		long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the matching d l document metadata set, or <code>null</code> if a matching d l document metadata set could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet fetchByD_F(
		long DDMStructureId, long fileVersionId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l document metadata sets.
	*
	* @return the d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l document metadata sets where uuid = &#63; from the database.
	*
	* @param uuid the uuid to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l document metadata sets where documentTypeId = &#63; from the database.
	*
	* @param documentTypeId the document type ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByDocumentTypeId(long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l document metadata sets where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d l document metadata set where DDMStructureId = &#63; and fileVersionId = &#63; from the database.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByD_F(long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchDocumentMetadataSetException;

	/**
	* Removes all the d l document metadata sets from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l document metadata sets where uuid = &#63;.
	*
	* @param uuid the uuid to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l document metadata sets where documentTypeId = &#63;.
	*
	* @param documentTypeId the document type ID to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public int countByDocumentTypeId(long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l document metadata sets where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public int countByFileVersionId(long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l document metadata sets where DDMStructureId = &#63; and fileVersionId = &#63;.
	*
	* @param DDMStructureId the d d m structure ID to search with
	* @param fileVersionId the file version ID to search with
	* @return the number of matching d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public int countByD_F(long DDMStructureId, long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l document metadata sets.
	*
	* @return the number of d l document metadata sets
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DLDocumentMetadataSet remove(
		DLDocumentMetadataSet dlDocumentMetadataSet) throws SystemException;
}