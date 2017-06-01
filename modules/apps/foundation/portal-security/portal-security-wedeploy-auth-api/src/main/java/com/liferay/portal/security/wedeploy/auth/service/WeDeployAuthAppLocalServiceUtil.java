/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.wedeploy.auth.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for WeDeployAuthApp. This utility wraps
 * {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthAppLocalService
 * @see com.liferay.portal.security.wedeploy.auth.service.base.WeDeployAuthAppLocalServiceBaseImpl
 * @see com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppLocalServiceImpl
 * @generated
 */
@ProviderType
public class WeDeployAuthAppLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.security.wedeploy.auth.service.impl.WeDeployAuthAppLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the we deploy auth app to the database. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthApp the we deploy auth app
	* @return the we deploy auth app that was added
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp addWeDeployAuthApp(
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp weDeployAuthApp) {
		return getService().addWeDeployAuthApp(weDeployAuthApp);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp addWeDeployAuthApp(
		long userId, java.lang.String name, java.lang.String redirectURI,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addWeDeployAuthApp(userId, name, redirectURI, serviceContext);
	}

	/**
	* Creates a new we deploy auth app with the primary key. Does not add the we deploy auth app to the database.
	*
	* @param weDeployAuthAppId the primary key for the new we deploy auth app
	* @return the new we deploy auth app
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp createWeDeployAuthApp(
		long weDeployAuthAppId) {
		return getService().createWeDeployAuthApp(weDeployAuthAppId);
	}

	/**
	* Deletes the we deploy auth app from the database. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthApp the we deploy auth app
	* @return the we deploy auth app that was removed
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp deleteWeDeployAuthApp(
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp weDeployAuthApp) {
		return getService().deleteWeDeployAuthApp(weDeployAuthApp);
	}

	/**
	* Deletes the we deploy auth app with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthAppId the primary key of the we deploy auth app
	* @return the we deploy auth app that was removed
	* @throws PortalException if a we deploy auth app with the primary key could not be found
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp deleteWeDeployAuthApp(
		long weDeployAuthAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteWeDeployAuthApp(weDeployAuthAppId);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp fetchWeDeployAuthApp(
		java.lang.String redirectURI, java.lang.String clientId) {
		return getService().fetchWeDeployAuthApp(redirectURI, clientId);
	}

	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp fetchWeDeployAuthApp(
		long weDeployAuthAppId) {
		return getService().fetchWeDeployAuthApp(weDeployAuthAppId);
	}

	/**
	* Returns the we deploy auth app with the primary key.
	*
	* @param weDeployAuthAppId the primary key of the we deploy auth app
	* @return the we deploy auth app
	* @throws PortalException if a we deploy auth app with the primary key could not be found
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp getWeDeployAuthApp(
		long weDeployAuthAppId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getWeDeployAuthApp(weDeployAuthAppId);
	}

	/**
	* Updates the we deploy auth app in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param weDeployAuthApp the we deploy auth app
	* @return the we deploy auth app that was updated
	*/
	public static com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp updateWeDeployAuthApp(
		com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp weDeployAuthApp) {
		return getService().updateWeDeployAuthApp(weDeployAuthApp);
	}

	/**
	* Returns the number of we deploy auth apps.
	*
	* @return the number of we deploy auth apps
	*/
	public static int getWeDeployAuthAppsCount() {
		return getService().getWeDeployAuthAppsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the we deploy auth apps.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthAppModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of we deploy auth apps
	* @param end the upper bound of the range of we deploy auth apps (not inclusive)
	* @return the range of we deploy auth apps
	*/
	public static java.util.List<com.liferay.portal.security.wedeploy.auth.model.WeDeployAuthApp> getWeDeployAuthApps(
		int start, int end) {
		return getService().getWeDeployAuthApps(start, end);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static WeDeployAuthAppLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WeDeployAuthAppLocalService, WeDeployAuthAppLocalService> _serviceTracker =
		ServiceTrackerFactory.open(WeDeployAuthAppLocalService.class);
}