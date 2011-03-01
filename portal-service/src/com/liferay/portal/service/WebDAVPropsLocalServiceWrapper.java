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

package com.liferay.portal.service;

/**
 * <p>
 * This class is a wrapper for {@link WebDAVPropsLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WebDAVPropsLocalService
 * @generated
 */
public class WebDAVPropsLocalServiceWrapper implements WebDAVPropsLocalService {
	public WebDAVPropsLocalServiceWrapper(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		_webDAVPropsLocalService = webDAVPropsLocalService;
	}

	/**
	* Adds the web d a v props to the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props to add
	* @return the web d a v props that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps addWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.addWebDAVProps(webDAVProps);
	}

	/**
	* Creates a new web d a v props with the primary key. Does not add the web d a v props to the database.
	*
	* @param webDavPropsId the primary key for the new web d a v props
	* @return the new web d a v props
	*/
	public com.liferay.portal.model.WebDAVProps createWebDAVProps(
		long webDavPropsId) {
		return _webDAVPropsLocalService.createWebDAVProps(webDavPropsId);
	}

	/**
	* Deletes the web d a v props with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param webDavPropsId the primary key of the web d a v props to delete
	* @throws PortalException if a web d a v props with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteWebDAVProps(long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.deleteWebDAVProps(webDavPropsId);
	}

	/**
	* Deletes the web d a v props from the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.deleteWebDAVProps(webDAVProps);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the web d a v props with the primary key.
	*
	* @param webDavPropsId the primary key of the web d a v props to get
	* @return the web d a v props
	* @throws PortalException if a web d a v props with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps getWebDAVProps(
		long webDavPropsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVProps(webDavPropsId);
	}

	/**
	* Gets a range of all the web d a v propses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of web d a v propses to return
	* @param end the upper bound of the range of web d a v propses to return (not inclusive)
	* @return the range of web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.WebDAVProps> getWebDAVPropses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVPropses(start, end);
	}

	/**
	* Gets the number of web d a v propses.
	*
	* @return the number of web d a v propses
	* @throws SystemException if a system exception occurred
	*/
	public int getWebDAVPropsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVPropsesCount();
	}

	/**
	* Updates the web d a v props in the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props to update
	* @return the web d a v props that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.updateWebDAVProps(webDAVProps);
	}

	/**
	* Updates the web d a v props in the database. Also notifies the appropriate model listeners.
	*
	* @param webDAVProps the web d a v props to update
	* @param merge whether to merge the web d a v props with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the web d a v props that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.WebDAVProps updateWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDAVProps, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.updateWebDAVProps(webDAVProps, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _webDAVPropsLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_webDAVPropsLocalService.setIdentifier(identifier);
	}

	public void deleteWebDAVProps(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.deleteWebDAVProps(className, classPK);
	}

	public com.liferay.portal.model.WebDAVProps getWebDAVProps(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _webDAVPropsLocalService.getWebDAVProps(companyId, className,
			classPK);
	}

	public void storeWebDAVProps(
		com.liferay.portal.model.WebDAVProps webDavProps)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_webDAVPropsLocalService.storeWebDAVProps(webDavProps);
	}

	public WebDAVPropsLocalService getWrappedWebDAVPropsLocalService() {
		return _webDAVPropsLocalService;
	}

	public void setWrappedWebDAVPropsLocalService(
		WebDAVPropsLocalService webDAVPropsLocalService) {
		_webDAVPropsLocalService = webDAVPropsLocalService;
	}

	private WebDAVPropsLocalService _webDAVPropsLocalService;
}