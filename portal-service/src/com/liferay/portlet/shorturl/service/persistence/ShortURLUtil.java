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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.shorturl.model.ShortURL;

import java.util.List;

/**
 * The persistence utility for the short u r l service. This utility wraps {@link ShortURLPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShortURLPersistence
 * @see ShortURLPersistenceImpl
 * @generated
 */
public class ShortURLUtil {
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
	public static void clearCache(ShortURL shortURL) {
		getPersistence().clearCache(shortURL);
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
	public static List<ShortURL> findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShortURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShortURL> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static ShortURL remove(ShortURL shortURL) throws SystemException {
		return getPersistence().remove(shortURL);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static ShortURL update(ShortURL shortURL, boolean merge)
		throws SystemException {
		return getPersistence().update(shortURL, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static ShortURL update(ShortURL shortURL, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(shortURL, merge, serviceContext);
	}

	/**
	* Caches the short u r l in the entity cache if it is enabled.
	*
	* @param shortURL the short u r l to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.shorturl.model.ShortURL shortURL) {
		getPersistence().cacheResult(shortURL);
	}

	/**
	* Caches the short u r ls in the entity cache if it is enabled.
	*
	* @param shortURLs the short u r ls to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.shorturl.model.ShortURL> shortURLs) {
		getPersistence().cacheResult(shortURLs);
	}

	/**
	* Creates a new short u r l with the primary key. Does not add the short u r l to the database.
	*
	* @param shortURLId the primary key for the new short u r l
	* @return the new short u r l
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL create(
		long shortURLId) {
		return getPersistence().create(shortURLId);
	}

	/**
	* Removes the short u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shortURLId the primary key of the short u r l to remove
	* @return the short u r l that was removed
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL remove(
		long shortURLId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException {
		return getPersistence().remove(shortURLId);
	}

	public static com.liferay.portlet.shorturl.model.ShortURL updateImpl(
		com.liferay.portlet.shorturl.model.ShortURL shortURL, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(shortURL, merge);
	}

	/**
	* Finds the short u r l with the primary key or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	*
	* @param shortURLId the primary key of the short u r l to find
	* @return the short u r l
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL findByPrimaryKey(
		long shortURLId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException {
		return getPersistence().findByPrimaryKey(shortURLId);
	}

	/**
	* Finds the short u r l with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param shortURLId the primary key of the short u r l to find
	* @return the short u r l, or <code>null</code> if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL fetchByPrimaryKey(
		long shortURLId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(shortURLId);
	}

	/**
	* Finds the short u r l where originalURL = &#63; or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	*
	* @param originalURL the original u r l to search with
	* @return the matching short u r l
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL findByOriginalURL(
		java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException {
		return getPersistence().findByOriginalURL(originalURL);
	}

	/**
	* Finds the short u r l where originalURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param originalURL the original u r l to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL fetchByOriginalURL(
		java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByOriginalURL(originalURL);
	}

	/**
	* Finds the short u r l where originalURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param originalURL the original u r l to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL fetchByOriginalURL(
		java.lang.String originalURL, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByOriginalURL(originalURL, retrieveFromCache);
	}

	/**
	* Finds the short u r l where hash = &#63; or throws a {@link com.liferay.portlet.shorturl.NoSuchShortURLException} if it could not be found.
	*
	* @param hash the hash to search with
	* @return the matching short u r l
	* @throws com.liferay.portlet.shorturl.NoSuchShortURLException if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL findByHash(
		java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException {
		return getPersistence().findByHash(hash);
	}

	/**
	* Finds the short u r l where hash = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param hash the hash to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL fetchByHash(
		java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByHash(hash);
	}

	/**
	* Finds the short u r l where hash = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param hash the hash to search with
	* @return the matching short u r l, or <code>null</code> if a matching short u r l could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL fetchByHash(
		java.lang.String hash, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByHash(hash, retrieveFromCache);
	}

	/**
	* Finds all the short u r ls.
	*
	* @return the short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.shorturl.model.ShortURL> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

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
	public static java.util.List<com.liferay.portlet.shorturl.model.ShortURL> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<com.liferay.portlet.shorturl.model.ShortURL> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the short u r l where originalURL = &#63; from the database.
	*
	* @param originalURL the original u r l to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByOriginalURL(java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException {
		getPersistence().removeByOriginalURL(originalURL);
	}

	/**
	* Removes the short u r l where hash = &#63; from the database.
	*
	* @param hash the hash to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByHash(java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shorturl.NoSuchShortURLException {
		getPersistence().removeByHash(hash);
	}

	/**
	* Removes all the short u r ls from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the short u r ls where originalURL = &#63;.
	*
	* @param originalURL the original u r l to search with
	* @return the number of matching short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public static int countByOriginalURL(java.lang.String originalURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByOriginalURL(originalURL);
	}

	/**
	* Counts all the short u r ls where hash = &#63;.
	*
	* @param hash the hash to search with
	* @return the number of matching short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public static int countByHash(java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByHash(hash);
	}

	/**
	* Counts all the short u r ls.
	*
	* @return the number of short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ShortURLPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ShortURLPersistence)PortalBeanLocatorUtil.locate(ShortURLPersistence.class.getName());

			ReferenceRegistry.registerReference(ShortURLUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(ShortURLPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(ShortURLUtil.class, "_persistence");
	}

	private static ShortURLPersistence _persistence;
}