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

package com.liferay.portlet.shorturl.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shorturl.model.ShortURL;

/**
 * The persistence interface for the short u r l service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShortURLPersistenceImpl
 * @see ShortURLUtil
 * @generated
 */
public interface ShortURLPersistence extends BasePersistence<ShortURL> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShortURLUtil} to access the short u r l persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the short u r l in the entity cache if it is enabled.
	*
	* @param shortURL the short u r l to cache
	*/
	public void cacheResult(
		com.liferay.portlet.shorturl.model.ShortURL shortURL);

	/**
	* Caches the short u r ls in the entity cache if it is enabled.
	*
	* @param shortURLs the short u r ls to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.shorturl.model.ShortURL> shortURLs);

	/**
	* Creates a new short u r l with the primary key. Does not add the short u r l to the database.
	*
	* @param shortURLId the primary key for the new short u r l
	* @return the new short u r l
	*/
	public com.liferay.portlet.shorturl.model.ShortURL create(long shortURLId);

	/**
	* Removes the short u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shortURLId the primary key of the short u r l to remove
	* @return the short u r l that was removed
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL remove(long shortURLId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException;

	public com.liferay.portlet.shorturl.model.ShortURL updateImpl(
		com.liferay.portlet.shorturl.model.ShortURL shortURL, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the short u r l with the primary key or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	*
	* @param shortURLId the primary key of the short u r l to find
	* @return the short u r l
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL findByPrimaryKey(
		long shortURLId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException;

	/**
	* Finds the short u r l with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param shortURLId the primary key of the short u r l to find
	* @return the short u r l, or <code>null</code> if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL fetchByPrimaryKey(
		long shortURLId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the short u r l where originalURL = &#63; or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	*
	* @param originalURL the original u r l to search with
	* @return the matching short u r l
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL findByOriginalURL(
		java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException;

	/**
	* Finds the short u r l where originalURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param originalURL the original u r l to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL fetchByOriginalURL(
		java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the short u r l where originalURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param originalURL the original u r l to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL fetchByOriginalURL(
		java.lang.String originalURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the short u r l where hash = &#63; or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	*
	* @param hash the hash to search with
	* @return the matching short u r l
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL findByHash(
		java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException;

	/**
	* Finds the short u r l where hash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param hash the hash to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL fetchByHash(
		java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the short u r l where hash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param hash the hash to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shorturl.model.ShortURL fetchByHash(
		java.lang.String hash, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the short u r ls.
	*
	* @return the short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shorturl.model.ShortURL> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the short u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of short u r ls to return
	* @param end the upper bound of the range of short u r ls to return (not inclusive)
	* @return the range of short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shorturl.model.ShortURL> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the short u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of short u r ls to return
	* @param end the upper bound of the range of short u r ls to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shorturl.model.ShortURL> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the short u r l where originalURL = &#63; from the database.
	*
	* @param originalURL the original u r l to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByOriginalURL(java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException;

	/**
	* Removes the short u r l where hash = &#63; from the database.
	*
	* @param hash the hash to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByHash(java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException;

	/**
	* Removes all the short u r ls from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the short u r ls where originalURL = &#63;.
	*
	* @param originalURL the original u r l to search with
	* @return the number of matching short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public int countByOriginalURL(java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the short u r ls where hash = &#63;.
	*
	* @param hash the hash to search with
	* @return the number of matching short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public int countByHash(java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the short u r ls.
	*
	* @return the number of short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public ShortURL remove(ShortURL shortURL) throws SystemException;
}