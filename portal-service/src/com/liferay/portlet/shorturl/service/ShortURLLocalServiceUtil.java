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

package com.liferay.portlet.shorturl.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the short u r l local service. This utility wraps {@link com.liferay.portlet.shorturl.service.impl.ShortURLLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShortURLLocalService
 * @see com.liferay.portlet.shorturl.service.base.ShortURLLocalServiceBaseImpl
 * @see com.liferay.portlet.shorturl.service.impl.ShortURLLocalServiceImpl
 * @generated
 */
public class ShortURLLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.shorturl.service.impl.ShortURLLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the short u r l to the database. Also notifies the appropriate model listeners.
	*
	* @param shortURL the short u r l to add
	* @return the short u r l that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL addShortURL(
		com.liferay.portlet.shorturl.model.ShortURL shortURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShortURL(shortURL);
	}

	/**
	* Creates a new short u r l with the primary key. Does not add the short u r l to the database.
	*
	* @param shortURLId the primary key for the new short u r l
	* @return the new short u r l
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL createShortURL(
		long shortURLId) {
		return getService().createShortURL(shortURLId);
	}

	/**
	* Deletes the short u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param shortURLId the primary key of the short u r l to delete
	* @throws PortalException if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteShortURL(long shortURLId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShortURL(shortURLId);
	}

	/**
	* Deletes the short u r l from the database. Also notifies the appropriate model listeners.
	*
	* @param shortURL the short u r l to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteShortURL(
		com.liferay.portlet.shorturl.model.ShortURL shortURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteShortURL(shortURL);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the short u r l with the primary key.
	*
	* @param shortURLId the primary key of the short u r l to get
	* @return the short u r l
	* @throws PortalException if a short u r l with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL getShortURL(
		long shortURLId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getShortURL(shortURLId);
	}

	/**
	* Gets a range of all the short u r ls.
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
	public static java.util.List<com.liferay.portlet.shorturl.model.ShortURL> getShortURLs(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShortURLs(start, end);
	}

	/**
	* Gets the number of short u r ls.
	*
	* @return the number of short u r ls
	* @throws SystemException if a system exception occurred
	*/
	public static int getShortURLsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getShortURLsCount();
	}

	/**
	* Updates the short u r l in the database. Also notifies the appropriate model listeners.
	*
	* @param shortURL the short u r l to update
	* @return the short u r l that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL updateShortURL(
		com.liferay.portlet.shorturl.model.ShortURL shortURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShortURL(shortURL);
	}

	/**
	* Updates the short u r l in the database. Also notifies the appropriate model listeners.
	*
	* @param shortURL the short u r l to update
	* @param merge whether to merge the short u r l with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the short u r l that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.shorturl.model.ShortURL updateShortURL(
		com.liferay.portlet.shorturl.model.ShortURL shortURL, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateShortURL(shortURL, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.shorturl.model.ShortURL addCustomURL(
		java.lang.String url, java.lang.String path)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCustomURL(url, path);
	}

	public static com.liferay.portlet.shorturl.model.ShortURL addShortURL(
		java.lang.String url)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShortURL(url);
	}

	public static com.liferay.portlet.shorturl.model.ShortURL addShortURL(
		java.lang.String url, java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShortURL(url, description);
	}

	public static com.liferay.portlet.shorturl.model.ShortURL addShortURL(
		java.lang.String url, java.lang.String hash,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addShortURL(url, hash, description);
	}

	public static java.lang.String getURLByHash(java.lang.String hash)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getURLByHash(hash);
	}

	public static java.lang.String getURLByURI(java.lang.String uri)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getURLByURI(uri);
	}

	public static ShortURLLocalService getService() {
		if (_service == null) {
			_service = (ShortURLLocalService)PortalBeanLocatorUtil.locate(ShortURLLocalService.class.getName());

			ReferenceRegistry.registerReference(ShortURLLocalServiceUtil.class,
				"_service");
			MethodCache.remove(ShortURLLocalService.class);
		}

		return _service;
	}

	public void setService(ShortURLLocalService service) {
		MethodCache.remove(ShortURLLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(ShortURLLocalServiceUtil.class,
			"_service");
		MethodCache.remove(ShortURLLocalService.class);
	}

	private static ShortURLLocalService _service;
}