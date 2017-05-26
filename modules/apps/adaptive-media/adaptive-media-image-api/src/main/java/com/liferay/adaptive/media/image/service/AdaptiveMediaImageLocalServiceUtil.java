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

package com.liferay.adaptive.media.image.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AdaptiveMediaImage. This utility wraps
 * {@link com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageLocalService
 * @see com.liferay.adaptive.media.image.service.base.AdaptiveMediaImageLocalServiceBaseImpl
 * @see com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageLocalServiceImpl
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.adaptive.media.image.service.impl.AdaptiveMediaImageLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the adaptive media image to the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImage the adaptive media image
	* @return the adaptive media image that was added
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage addAdaptiveMediaImage(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImage adaptiveMediaImage) {
		return getService().addAdaptiveMediaImage(adaptiveMediaImage);
	}

	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage addAdaptiveMediaImage(
		java.lang.String configurationUuid, long fileVersionId,
		java.lang.String mimeType, int width, int size, int height)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addAdaptiveMediaImage(configurationUuid, fileVersionId,
			mimeType, width, size, height);
	}

	/**
	* Creates a new adaptive media image with the primary key. Does not add the adaptive media image to the database.
	*
	* @param adaptiveMediaImageId the primary key for the new adaptive media image
	* @return the new adaptive media image
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage createAdaptiveMediaImage(
		long adaptiveMediaImageId) {
		return getService().createAdaptiveMediaImage(adaptiveMediaImageId);
	}

	/**
	* Deletes the adaptive media image from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImage the adaptive media image
	* @return the adaptive media image that was removed
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage deleteAdaptiveMediaImage(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImage adaptiveMediaImage) {
		return getService().deleteAdaptiveMediaImage(adaptiveMediaImage);
	}

	/**
	* Deletes the adaptive media image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageId the primary key of the adaptive media image
	* @return the adaptive media image that was removed
	* @throws PortalException if a adaptive media image with the primary key could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage deleteAdaptiveMediaImage(
		long adaptiveMediaImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAdaptiveMediaImage(adaptiveMediaImageId);
	}

	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage fetchAdaptiveMediaImage(
		java.lang.String configurationUuid, long fileVersionId) {
		return getService()
				   .fetchAdaptiveMediaImage(configurationUuid, fileVersionId);
	}

	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage fetchAdaptiveMediaImage(
		long adaptiveMediaImageId) {
		return getService().fetchAdaptiveMediaImage(adaptiveMediaImageId);
	}

	/**
	* Returns the adaptive media image matching the UUID and group.
	*
	* @param uuid the adaptive media image's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage fetchAdaptiveMediaImageByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return getService()
				   .fetchAdaptiveMediaImageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns the adaptive media image with the primary key.
	*
	* @param adaptiveMediaImageId the primary key of the adaptive media image
	* @return the adaptive media image
	* @throws PortalException if a adaptive media image with the primary key could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage getAdaptiveMediaImage(
		long adaptiveMediaImageId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAdaptiveMediaImage(adaptiveMediaImageId);
	}

	/**
	* Returns the adaptive media image matching the UUID and group.
	*
	* @param uuid the adaptive media image's UUID
	* @param groupId the primary key of the group
	* @return the matching adaptive media image
	* @throws PortalException if a matching adaptive media image could not be found
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage getAdaptiveMediaImageByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAdaptiveMediaImageByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Updates the adaptive media image in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImage the adaptive media image
	* @return the adaptive media image that was updated
	*/
	public static com.liferay.adaptive.media.image.model.AdaptiveMediaImage updateAdaptiveMediaImage(
		com.liferay.adaptive.media.image.model.AdaptiveMediaImage adaptiveMediaImage) {
		return getService().updateAdaptiveMediaImage(adaptiveMediaImage);
	}

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
	* Returns the number of adaptive media images.
	*
	* @return the number of adaptive media images
	*/
	public static int getAdaptiveMediaImagesCount() {
		return getService().getAdaptiveMediaImagesCount();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the adaptive media images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of adaptive media images
	*/
	public static java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImage> getAdaptiveMediaImages(
		int start, int end) {
		return getService().getAdaptiveMediaImages(start, end);
	}

	/**
	* Returns all the adaptive media images matching the UUID and company.
	*
	* @param uuid the UUID of the adaptive media images
	* @param companyId the primary key of the company
	* @return the matching adaptive media images, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImage> getAdaptiveMediaImagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService()
				   .getAdaptiveMediaImagesByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns a range of adaptive media images matching the UUID and company.
	*
	* @param uuid the UUID of the adaptive media images
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching adaptive media images, or an empty list if no matches were found
	*/
	public static java.util.List<com.liferay.adaptive.media.image.model.AdaptiveMediaImage> getAdaptiveMediaImagesByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.adaptive.media.image.model.AdaptiveMediaImage> orderByComparator) {
		return getService()
				   .getAdaptiveMediaImagesByUuidAndCompanyId(uuid, companyId,
			start, end, orderByComparator);
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

	public static void deleteAdaptiveMediaImageFileVersion(long fileVersionId) {
		getService().deleteAdaptiveMediaImageFileVersion(fileVersionId);
	}

	public static AdaptiveMediaImageLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AdaptiveMediaImageLocalService, AdaptiveMediaImageLocalService> _serviceTracker =
		ServiceTrackerFactory.open(AdaptiveMediaImageLocalService.class);
}