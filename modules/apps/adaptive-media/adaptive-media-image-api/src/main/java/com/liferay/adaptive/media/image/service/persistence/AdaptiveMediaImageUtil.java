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

package com.liferay.adaptive.media.image.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.model.AdaptiveMediaImage;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the adaptive media image service. This utility wraps {@link com.liferay.adaptive.media.image.service.persistence.impl.AdaptiveMediaImagePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImagePersistence
 * @see com.liferay.adaptive.media.image.service.persistence.impl.AdaptiveMediaImagePersistenceImpl
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(AdaptiveMediaImage adaptiveMediaImage) {
		getPersistence().clearCache(adaptiveMediaImage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AdaptiveMediaImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AdaptiveMediaImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AdaptiveMediaImage> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AdaptiveMediaImage update(
		AdaptiveMediaImage adaptiveMediaImage) {
		return getPersistence().update(adaptiveMediaImage);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AdaptiveMediaImage update(
		AdaptiveMediaImage adaptiveMediaImage, ServiceContext serviceContext) {
		return getPersistence().update(adaptiveMediaImage, serviceContext);
	}

	/**
	* Returns all the adaptive media images where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid(java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the adaptive media images where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid(java.lang.String uuid,
		int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid(java.lang.String uuid,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByUuid_First(java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first adaptive media image in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByUuid_First(java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByUuid_Last(java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByUuid_Last(java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the adaptive media images before and after the current adaptive media image in the ordered set where uuid = &#63;.
	*
	* @param adaptiveMediaImageId the primary key of the current adaptive media image
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage[] findByUuid_PrevAndNext(
		long adaptiveMediaImageId, java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByUuid_PrevAndNext(adaptiveMediaImageId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the adaptive media images where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of adaptive media images where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching adaptive media images
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the adaptive media image where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAdaptiveMediaImageException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the adaptive media image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the adaptive media image where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the adaptive media image where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the adaptive media image that was removed
	*/
	public static AdaptiveMediaImage removeByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of adaptive media images where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching adaptive media images
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the adaptive media images where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid_C(java.lang.String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the adaptive media images where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByUuid_C_First(java.lang.String uuid,
		long companyId, OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByUuid_C_Last(java.lang.String uuid,
		long companyId, OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the adaptive media images before and after the current adaptive media image in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param adaptiveMediaImageId the primary key of the current adaptive media image
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage[] findByUuid_C_PrevAndNext(
		long adaptiveMediaImageId, java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(adaptiveMediaImageId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the adaptive media images where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of adaptive media images where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching adaptive media images
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the adaptive media images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the adaptive media images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the adaptive media images before and after the current adaptive media image in the ordered set where groupId = &#63;.
	*
	* @param adaptiveMediaImageId the primary key of the current adaptive media image
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage[] findByGroupId_PrevAndNext(
		long adaptiveMediaImageId, long groupId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(adaptiveMediaImageId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the adaptive media images where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of adaptive media images where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching adaptive media images
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the adaptive media images where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the adaptive media images where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByCompanyId_First(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByCompanyId_First(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the adaptive media images before and after the current adaptive media image in the ordered set where companyId = &#63;.
	*
	* @param adaptiveMediaImageId the primary key of the current adaptive media image
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage[] findByCompanyId_PrevAndNext(
		long adaptiveMediaImageId, long companyId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(adaptiveMediaImageId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the adaptive media images where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of adaptive media images where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching adaptive media images
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the adaptive media images where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByConfigurationUuid(
		java.lang.String configurationUuid) {
		return getPersistence().findByConfigurationUuid(configurationUuid);
	}

	/**
	* Returns a range of all the adaptive media images where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end) {
		return getPersistence()
				   .findByConfigurationUuid(configurationUuid, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByConfigurationUuid_First(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByConfigurationUuid_First(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the first adaptive media image in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByConfigurationUuid_First(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByConfigurationUuid_First(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByConfigurationUuid_Last(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByConfigurationUuid_Last(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByConfigurationUuid_Last(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByConfigurationUuid_Last(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the adaptive media images before and after the current adaptive media image in the ordered set where configurationUuid = &#63;.
	*
	* @param adaptiveMediaImageId the primary key of the current adaptive media image
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage[] findByConfigurationUuid_PrevAndNext(
		long adaptiveMediaImageId, java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByConfigurationUuid_PrevAndNext(adaptiveMediaImageId,
			configurationUuid, orderByComparator);
	}

	/**
	* Removes all the adaptive media images where configurationUuid = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	*/
	public static void removeByConfigurationUuid(
		java.lang.String configurationUuid) {
		getPersistence().removeByConfigurationUuid(configurationUuid);
	}

	/**
	* Returns the number of adaptive media images where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the number of matching adaptive media images
	*/
	public static int countByConfigurationUuid(
		java.lang.String configurationUuid) {
		return getPersistence().countByConfigurationUuid(configurationUuid);
	}

	/**
	* Returns all the adaptive media images where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByFileVersionId(
		long fileVersionId) {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

	/**
	* Returns a range of all the adaptive media images where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByFileVersionId(
		long fileVersionId, int start, int end) {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media images
	*/
	public static List<AdaptiveMediaImage> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the adaptive media images before and after the current adaptive media image in the ordered set where fileVersionId = &#63;.
	*
	* @param adaptiveMediaImageId the primary key of the current adaptive media image
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage[] findByFileVersionId_PrevAndNext(
		long adaptiveMediaImageId, long fileVersionId,
		OrderByComparator<AdaptiveMediaImage> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(adaptiveMediaImageId,
			fileVersionId, orderByComparator);
	}

	/**
	* Removes all the adaptive media images where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public static void removeByFileVersionId(long fileVersionId) {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Returns the number of adaptive media images where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching adaptive media images
	*/
	public static int countByFileVersionId(long fileVersionId) {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Returns the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAdaptiveMediaImageException} if it could not be found.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage findByC_F(
		java.lang.String configurationUuid, long fileVersionId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Returns the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByC_F(
		java.lang.String configurationUuid, long fileVersionId) {
		return getPersistence().fetchByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Returns the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching adaptive media image, or <code>null</code> if a matching adaptive media image could not be found
	*/
	public static AdaptiveMediaImage fetchByC_F(
		java.lang.String configurationUuid, long fileVersionId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_F(configurationUuid, fileVersionId,
			retrieveFromCache);
	}

	/**
	* Removes the adaptive media image where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the adaptive media image that was removed
	*/
	public static AdaptiveMediaImage removeByC_F(
		java.lang.String configurationUuid, long fileVersionId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().removeByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Returns the number of adaptive media images where configurationUuid = &#63; and fileVersionId = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the number of matching adaptive media images
	*/
	public static int countByC_F(java.lang.String configurationUuid,
		long fileVersionId) {
		return getPersistence().countByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Caches the adaptive media image in the entity cache if it is enabled.
	*
	* @param adaptiveMediaImage the adaptive media image
	*/
	public static void cacheResult(AdaptiveMediaImage adaptiveMediaImage) {
		getPersistence().cacheResult(adaptiveMediaImage);
	}

	/**
	* Caches the adaptive media images in the entity cache if it is enabled.
	*
	* @param adaptiveMediaImages the adaptive media images
	*/
	public static void cacheResult(List<AdaptiveMediaImage> adaptiveMediaImages) {
		getPersistence().cacheResult(adaptiveMediaImages);
	}

	/**
	* Creates a new adaptive media image with the primary key. Does not add the adaptive media image to the database.
	*
	* @param adaptiveMediaImageId the primary key for the new adaptive media image
	* @return the new adaptive media image
	*/
	public static AdaptiveMediaImage create(long adaptiveMediaImageId) {
		return getPersistence().create(adaptiveMediaImageId);
	}

	/**
	* Removes the adaptive media image with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageId the primary key of the adaptive media image
	* @return the adaptive media image that was removed
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage remove(long adaptiveMediaImageId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().remove(adaptiveMediaImageId);
	}

	public static AdaptiveMediaImage updateImpl(
		AdaptiveMediaImage adaptiveMediaImage) {
		return getPersistence().updateImpl(adaptiveMediaImage);
	}

	/**
	* Returns the adaptive media image with the primary key or throws a {@link NoSuchAdaptiveMediaImageException} if it could not be found.
	*
	* @param adaptiveMediaImageId the primary key of the adaptive media image
	* @return the adaptive media image
	* @throws NoSuchAdaptiveMediaImageException if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage findByPrimaryKey(long adaptiveMediaImageId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageException {
		return getPersistence().findByPrimaryKey(adaptiveMediaImageId);
	}

	/**
	* Returns the adaptive media image with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param adaptiveMediaImageId the primary key of the adaptive media image
	* @return the adaptive media image, or <code>null</code> if a adaptive media image with the primary key could not be found
	*/
	public static AdaptiveMediaImage fetchByPrimaryKey(
		long adaptiveMediaImageId) {
		return getPersistence().fetchByPrimaryKey(adaptiveMediaImageId);
	}

	public static java.util.Map<java.io.Serializable, AdaptiveMediaImage> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the adaptive media images.
	*
	* @return the adaptive media images
	*/
	public static List<AdaptiveMediaImage> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the adaptive media images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @return the range of adaptive media images
	*/
	public static List<AdaptiveMediaImage> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of adaptive media images
	*/
	public static List<AdaptiveMediaImage> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media images.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media images
	* @param end the upper bound of the range of adaptive media images (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of adaptive media images
	*/
	public static List<AdaptiveMediaImage> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImage> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the adaptive media images from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of adaptive media images.
	*
	* @return the number of adaptive media images
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AdaptiveMediaImagePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AdaptiveMediaImagePersistence, AdaptiveMediaImagePersistence> _serviceTracker =
		ServiceTrackerFactory.open(AdaptiveMediaImagePersistence.class);
}