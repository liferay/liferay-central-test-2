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

import com.liferay.portlet.documentlibrary.model.DLFileVersion;

/**
 * The persistence interface for the d l file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionPersistenceImpl
 * @see DLFileVersionUtil
 * @generated
 */
public interface DLFileVersionPersistence extends BasePersistence<DLFileVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileVersionUtil} to access the d l file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the d l file version in the entity cache if it is enabled.
	*
	* @param dlFileVersion the d l file version to cache
	*/
	public void cacheResult(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion);

	/**
	* Caches the d l file versions in the entity cache if it is enabled.
	*
	* @param dlFileVersions the d l file versions to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> dlFileVersions);

	/**
	* Creates a new d l file version with the primary key. Does not add the d l file version to the database.
	*
	* @param fileVersionId the primary key for the new d l file version
	* @return the new d l file version
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion create(
		long fileVersionId);

	/**
	* Removes the d l file version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionId the primary key of the d l file version to remove
	* @return the d l file version that was removed
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion remove(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	public com.liferay.portlet.documentlibrary.model.DLFileVersion updateImpl(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l file version with the primary key or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	*
	* @param fileVersionId the primary key of the d l file version to find
	* @return the d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByPrimaryKey(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the d l file version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileVersionId the primary key of the d l file version to find
	* @return the d l file version, or <code>null</code> if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByPrimaryKey(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l file versions where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @return the matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByFileEntryId(
		long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @return the range of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByFileEntryId(
		long fileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file version in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByFileEntryId_First(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the last d l file version in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByFileEntryId_Last(
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the d l file versions before and after the current d l file version in the ordered set where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the primary key of the current d l file version
	* @param fileEntryId the file entry ID to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByFileEntryId_PrevAndNext(
		long fileVersionId, long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the d l file version where fileEntryId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.documentlibrary.NoSuchFileVersionException} if it could not be found.
	*
	* @param fileEntryId the file entry ID to search with
	* @param version the version to search with
	* @return the matching d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_V(
		long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the d l file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID to search with
	* @param version the version to search with
	* @return the matching d l file version, or <code>null</code> if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByF_V(
		long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the d l file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID to search with
	* @param version the version to search with
	* @return the matching d l file version, or <code>null</code> if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion fetchByF_V(
		long fileEntryId, java.lang.String version, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the d l file versions where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @return the matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByF_S(
		long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @return the range of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findByF_S(
		long fileEntryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first d l file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_S_First(
		long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the last d l file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a matching d l file version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion findByF_S_Last(
		long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds the d l file versions before and after the current d l file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param fileVersionId the primary key of the current d l file version
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d l file version
	* @throws com.liferay.portlet.documentlibrary.NoSuchFileVersionException if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileVersion[] findByF_S_PrevAndNext(
		long fileVersionId, long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Finds all the d l file versions.
	*
	* @return the d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the d l file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @return the range of d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the d l file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file versions where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the d l file version where fileEntryId = &#63; and version = &#63; from the database.
	*
	* @param fileEntryId the file entry ID to search with
	* @param version the version to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByF_V(long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.documentlibrary.NoSuchFileVersionException;

	/**
	* Removes all the d l file versions where fileEntryId = &#63; and status = &#63; from the database.
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByF_S(long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the d l file versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file versions where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @return the number of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByFileEntryId(long fileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file versions where fileEntryId = &#63; and version = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @param version the version to search with
	* @return the number of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByF_V(long fileEntryId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file versions where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID to search with
	* @param status the status to search with
	* @return the number of matching d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public int countByF_S(long fileEntryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the d l file versions.
	*
	* @return the number of d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public DLFileVersion remove(DLFileVersion dlFileVersion)
		throws SystemException;
}