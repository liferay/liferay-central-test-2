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

package com.liferay.friendly.url.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FriendlyURLLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalService
 * @generated
 */
@ProviderType
public class FriendlyURLLocalServiceWrapper implements FriendlyURLLocalService,
	ServiceWrapper<FriendlyURLLocalService> {
	public FriendlyURLLocalServiceWrapper(
		FriendlyURLLocalService friendlyURLLocalService) {
		_friendlyURLLocalService = friendlyURLLocalService;
	}

	/**
	* Adds the friendly u r l to the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly u r l
	* @return the friendly u r l that was added
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL addFriendlyURL(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return _friendlyURLLocalService.addFriendlyURL(friendlyURL);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL addFriendlyURL(
		long companyId, long groupId, java.lang.Class<?> clazz, long classPK,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.addFriendlyURL(companyId, groupId,
			clazz, classPK, urlTitle);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL addFriendlyURL(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.addFriendlyURL(companyId, groupId,
			classNameId, classPK, urlTitle);
	}

	/**
	* Creates a new friendly u r l with the primary key. Does not add the friendly u r l to the database.
	*
	* @param friendlyURLId the primary key for the new friendly u r l
	* @return the new friendly u r l
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL createFriendlyURL(
		long friendlyURLId) {
		return _friendlyURLLocalService.createFriendlyURL(friendlyURLId);
	}

	/**
	* Deletes the friendly u r l from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly u r l
	* @return the friendly u r l that was removed
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL deleteFriendlyURL(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return _friendlyURLLocalService.deleteFriendlyURL(friendlyURL);
	}

	/**
	* Deletes the friendly u r l with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLId the primary key of the friendly u r l
	* @return the friendly u r l that was removed
	* @throws PortalException if a friendly u r l with the primary key could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL deleteFriendlyURL(
		long friendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.deleteFriendlyURL(friendlyURLId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURL(
		long companyId, long groupId, java.lang.Class<?> clazz,
		java.lang.String urlTitle) {
		return _friendlyURLLocalService.fetchFriendlyURL(companyId, groupId,
			clazz, urlTitle);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURL(
		long companyId, long groupId, long classNameId,
		java.lang.String urlTitle) {
		return _friendlyURLLocalService.fetchFriendlyURL(companyId, groupId,
			classNameId, urlTitle);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURL(
		long friendlyURLId) {
		return _friendlyURLLocalService.fetchFriendlyURL(friendlyURLId);
	}

	/**
	* Returns the friendly u r l matching the UUID and group.
	*
	* @param uuid the friendly u r l's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly u r l, or <code>null</code> if a matching friendly u r l could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL fetchFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId) {
		return _friendlyURLLocalService.fetchFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Returns the friendly u r l with the primary key.
	*
	* @param friendlyURLId the primary key of the friendly u r l
	* @return the friendly u r l
	* @throws PortalException if a friendly u r l with the primary key could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL getFriendlyURL(
		long friendlyURLId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.getFriendlyURL(friendlyURLId);
	}

	/**
	* Returns the friendly u r l matching the UUID and group.
	*
	* @param uuid the friendly u r l's UUID
	* @param groupId the primary key of the group
	* @return the matching friendly u r l
	* @throws PortalException if a matching friendly u r l could not be found
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL getFriendlyURLByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.getFriendlyURLByUuidAndGroupId(uuid,
			groupId);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL getMainFriendlyURL(
		long companyId, long groupId, java.lang.Class<?> clazz, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.getMainFriendlyURL(companyId, groupId,
			clazz, classPK);
	}

	@Override
	public com.liferay.friendly.url.model.FriendlyURL getMainFriendlyURL(
		long companyId, long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.getMainFriendlyURL(companyId, groupId,
			classNameId, classPK);
	}

	/**
	* Updates the friendly u r l in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param friendlyURL the friendly u r l
	* @return the friendly u r l that was updated
	*/
	@Override
	public com.liferay.friendly.url.model.FriendlyURL updateFriendlyURL(
		com.liferay.friendly.url.model.FriendlyURL friendlyURL) {
		return _friendlyURLLocalService.updateFriendlyURL(friendlyURL);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _friendlyURLLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _friendlyURLLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.exportimport.kernel.lar.PortletDataContext portletDataContext) {
		return _friendlyURLLocalService.getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _friendlyURLLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of friendly u r ls.
	*
	* @return the number of friendly u r ls
	*/
	@Override
	public int getFriendlyURLsCount() {
		return _friendlyURLLocalService.getFriendlyURLsCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _friendlyURLLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.lang.String getUniqueUrlTitle(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle) {
		return _friendlyURLLocalService.getUniqueUrlTitle(companyId, groupId,
			classNameId, classPK, urlTitle);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _friendlyURLLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _friendlyURLLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _friendlyURLLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns a range of all the friendly u r ls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.friendly.url.model.impl.FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly u r ls
	* @param end the upper bound of the range of friendly u r ls (not inclusive)
	* @return the range of friendly u r ls
	*/
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLs(
		int start, int end) {
		return _friendlyURLLocalService.getFriendlyURLs(start, end);
	}

	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLs(
		long companyId, long groupId, long classNameId, long classPK) {
		return _friendlyURLLocalService.getFriendlyURLs(companyId, groupId,
			classNameId, classPK);
	}

	/**
	* Returns all the friendly u r ls matching the UUID and company.
	*
	* @param uuid the UUID of the friendly u r ls
	* @param companyId the primary key of the company
	* @return the matching friendly u r ls, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return _friendlyURLLocalService.getFriendlyURLsByUuidAndCompanyId(uuid,
			companyId);
	}

	/**
	* Returns a range of friendly u r ls matching the UUID and company.
	*
	* @param uuid the UUID of the friendly u r ls
	* @param companyId the primary key of the company
	* @param start the lower bound of the range of friendly u r ls
	* @param end the upper bound of the range of friendly u r ls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the range of matching friendly u r ls, or an empty list if no matches were found
	*/
	@Override
	public java.util.List<com.liferay.friendly.url.model.FriendlyURL> getFriendlyURLsByUuidAndCompanyId(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.friendly.url.model.FriendlyURL> orderByComparator) {
		return _friendlyURLLocalService.getFriendlyURLsByUuidAndCompanyId(uuid,
			companyId, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _friendlyURLLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _friendlyURLLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void deleteFriendlyURL(long companyId, long groupId,
		java.lang.Class<?> clazz, long classPK) {
		_friendlyURLLocalService.deleteFriendlyURL(companyId, groupId, clazz,
			classPK);
	}

	@Override
	public void deleteFriendlyURL(long companyId, long groupId,
		java.lang.Class<?> clazz, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		_friendlyURLLocalService.deleteFriendlyURL(companyId, groupId, clazz,
			classPK, urlTitle);
	}

	@Override
	public void deleteFriendlyURL(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws com.liferay.friendly.url.exception.NoSuchFriendlyURLException {
		_friendlyURLLocalService.deleteFriendlyURL(companyId, groupId,
			classNameId, classPK, urlTitle);
	}

	@Override
	public void deleteGroupFriendlyURLs(long groupId, long classNameId) {
		_friendlyURLLocalService.deleteGroupFriendlyURLs(groupId, classNameId);
	}

	@Override
	public void validate(long companyId, long groupId, long classNameId,
		java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		_friendlyURLLocalService.validate(companyId, groupId, classNameId,
			urlTitle);
	}

	@Override
	public void validate(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException {
		_friendlyURLLocalService.validate(companyId, groupId, classNameId,
			classPK, urlTitle);
	}

	@Override
	public FriendlyURLLocalService getWrappedService() {
		return _friendlyURLLocalService;
	}

	@Override
	public void setWrappedService(
		FriendlyURLLocalService friendlyURLLocalService) {
		_friendlyURLLocalService = friendlyURLLocalService;
	}

	private FriendlyURLLocalService _friendlyURLLocalService;
}