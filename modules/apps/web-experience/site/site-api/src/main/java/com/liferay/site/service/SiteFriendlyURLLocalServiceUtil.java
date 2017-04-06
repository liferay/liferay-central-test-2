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

package com.liferay.site.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for SiteFriendlyURL. This utility wraps
 * {@link com.liferay.site.service.impl.SiteFriendlyURLLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SiteFriendlyURLLocalService
 * @see com.liferay.site.service.base.SiteFriendlyURLLocalServiceBaseImpl
 * @see com.liferay.site.service.impl.SiteFriendlyURLLocalServiceImpl
 * @generated
 */
@ProviderType
public class SiteFriendlyURLLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.site.service.impl.SiteFriendlyURLLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
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
	* Adds the site friendly url to the database. Also notifies the appropriate model listeners.
	*
	* @param siteFriendlyURL the site friendly url
	* @return the site friendly url that was added
	*/
	public static com.liferay.site.model.SiteFriendlyURL addSiteFriendlyURL(
		com.liferay.site.model.SiteFriendlyURL siteFriendlyURL) {
		return getService().addSiteFriendlyURL(siteFriendlyURL);
	}

	public static com.liferay.site.model.SiteFriendlyURL addSiteFriendlyURL(
		long userId, long companyId, long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSiteFriendlyURL(userId, companyId, groupId, friendlyURL,
			languageId, serviceContext);
	}

	/**
	* Creates a new site friendly url with the primary key. Does not add the site friendly url to the database.
	*
	* @param siteFriendlyURLId the primary key for the new site friendly url
	* @return the new site friendly url
	*/
	public static com.liferay.site.model.SiteFriendlyURL createSiteFriendlyURL(
		long siteFriendlyURLId) {
		return getService().createSiteFriendlyURL(siteFriendlyURLId);
	}

	/**
	* Deletes the site friendly url from the database. Also notifies the appropriate model listeners.
	*
	* @param siteFriendlyURL the site friendly url
	* @return the site friendly url that was removed
	*/
	public static com.liferay.site.model.SiteFriendlyURL deleteSiteFriendlyURL(
		com.liferay.site.model.SiteFriendlyURL siteFriendlyURL) {
		return getService().deleteSiteFriendlyURL(siteFriendlyURL);
	}

	public static com.liferay.site.model.SiteFriendlyURL deleteSiteFriendlyURL(
		long companyId, long groupId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSiteFriendlyURL(companyId, groupId, languageId);
	}

	/**
	* Deletes the site friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param siteFriendlyURLId the primary key of the site friendly url
	* @return the site friendly url that was removed
	* @throws PortalException if a site friendly url with the primary key could not be found
	*/
	public static com.liferay.site.model.SiteFriendlyURL deleteSiteFriendlyURL(
		long siteFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSiteFriendlyURL(siteFriendlyURLId);
	}

	public static com.liferay.site.model.SiteFriendlyURL fetchSiteFriendlyURL(
		long companyId, long groupId, java.lang.String languageId) {
		return getService().fetchSiteFriendlyURL(companyId, groupId, languageId);
	}

	public static com.liferay.site.model.SiteFriendlyURL fetchSiteFriendlyURL(
		long siteFriendlyURLId) {
		return getService().fetchSiteFriendlyURL(siteFriendlyURLId);
	}

	public static com.liferay.site.model.SiteFriendlyURL fetchSiteFriendlyURLByFriendlyURL(
		long companyId, java.lang.String friendlyURL) {
		return getService()
				   .fetchSiteFriendlyURLByFriendlyURL(companyId, friendlyURL);
	}

	/**
	* Returns the site friendly url matching the UUID and group.
	*
	* @param uuid the site friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching site friendly url, or <code>null</code> if a matching site friendly url could not be found
	*/
	public static com.liferay.site.model.SiteFriendlyURL fetchSiteFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService().fetchSiteFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the site friendly url with the primary key.
	*
	* @param siteFriendlyURLId the primary key of the site friendly url
	* @return the site friendly url
	* @throws PortalException if a site friendly url with the primary key could not be found
	*/
	public static com.liferay.site.model.SiteFriendlyURL getSiteFriendlyURL(
		long siteFriendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSiteFriendlyURL(siteFriendlyURLId);
	}

	/**
	* Returns the site friendly url matching the UUID and group.
	*
	* @param uuid the site friendly url's UUID
	* @param groupId the primary key of the group
	* @return the matching site friendly url
	* @throws PortalException if a matching site friendly url could not be found
	*/
	public static com.liferay.site.model.SiteFriendlyURL getSiteFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSiteFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the site friendly url in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param siteFriendlyURL the site friendly url
	* @return the site friendly url that was updated
	*/
	public static com.liferay.site.model.SiteFriendlyURL updateSiteFriendlyURL(
		com.liferay.site.model.SiteFriendlyURL siteFriendlyURL) {
		return getService().updateSiteFriendlyURL(siteFriendlyURL);
	}

	public static com.liferay.site.model.SiteFriendlyURL updateSiteFriendlyURL(
		long userId, long companyId, long groupId,
		java.lang.String friendlyURL, java.lang.String languageId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSiteFriendlyURL(userId, companyId, groupId,
			friendlyURL, languageId, serviceContext);
	}

	/**
	* Returns the number of site friendly urls.
	*
	* @return the number of site friendly urls
	*/
	public static int getSiteFriendlyURLsCount() {
		return getService().getSiteFriendlyURLsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.site.model.SiteFriendlyURL> addSiteFriendlyURLs(
		long userId, long companyId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addSiteFriendlyURLs(userId, companyId, groupId,
			friendlyURLMap, serviceContext);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.model.impl.SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.model.impl.SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the site friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.site.model.impl.SiteFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of site friendly urls
	* @param end the upper bound of the range of site friendly urls (not inclusive)
	* @return the range of site friendly urls
	*/
	public static java.util.List<com.liferay.site.model.SiteFriendlyURL> getSiteFriendlyURLs(
		int start, int end) {
		return getService().getSiteFriendlyURLs(start, end);
	}

	public static java.util.List<com.liferay.site.model.SiteFriendlyURL> getSiteFriendlyURLs(
		long companyId, long groupId) {
		return getService().getSiteFriendlyURLs(companyId, groupId);
	}

	/**
	* Returns all the site friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the site friendly urls
	* @param companyId the primary key of the company
	* @return the matching site friendly urls, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.site.model.SiteFriendlyURL> getSiteFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getSiteFriendlyURLsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of site friendly urls matching the UUID and company.
	*
	* @param uuid the UUID of the site friendly urls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of site friendly urls
	* @param end the upper bound of the range of site friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching site friendly urls, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.site.model.SiteFriendlyURL> getSiteFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.site.model.SiteFriendlyURL> orderByComparator) {
		return getService()
				   .getSiteFriendlyURLsByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
	}

	public static java.util.List<com.liferay.site.model.SiteFriendlyURL> updateSiteFriendlyURLs(
		long userId, long companyId, long groupId,
		java.util.Map<java.util.Locale, java.lang.String> friendlyURLMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateSiteFriendlyURLs(userId, companyId, groupId,
			friendlyURLMap, serviceContext);
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

	public static void deleteSiteFriendlyURLs(long companyId, long groupId) {
		getService().deleteSiteFriendlyURLs(companyId, groupId);
	}

	public static SiteFriendlyURLLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<SiteFriendlyURLLocalService, SiteFriendlyURLLocalService> _serviceTracker =
		ServiceTrackerFactory.open(SiteFriendlyURLLocalService.class);
}