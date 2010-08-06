/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * The utility for the asset vocabulary local service. This utility wraps {@link com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyLocalService
 * @see com.liferay.portlet.asset.service.base.AssetVocabularyLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.impl.AssetVocabularyLocalServiceImpl
 * @generated
 */
public class AssetVocabularyLocalServiceUtil {
	/**
	* Adds the asset vocabulary to the database. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary to add
	* @return the asset vocabulary that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary addAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetVocabulary(assetVocabulary);
	}

	/**
	* Creates a new asset vocabulary with the primary key. Does not add the asset vocabulary to the database.
	*
	* @param vocabularyId the primary key for the new asset vocabulary
	* @return the new asset vocabulary
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary createAssetVocabulary(
		long vocabularyId) {
		return getService().createAssetVocabulary(vocabularyId);
	}

	/**
	* Deletes the asset vocabulary with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param vocabularyId the primary key of the asset vocabulary to delete
	* @throws PortalException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetVocabulary(vocabularyId);
	}

	/**
	* Deletes the asset vocabulary from the database. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetVocabulary(assetVocabulary);
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
	* @param orderByComparator the comparator to order the results by
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
	* Gets the asset vocabulary with the primary key.
	*
	* @param vocabularyId the primary key of the asset vocabulary to get
	* @return the asset vocabulary
	* @throws PortalException if a asset vocabulary with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary getAssetVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabulary(vocabularyId);
	}

	/**
	* Gets the asset vocabulary with the UUID and group id.
	*
	* @param uuid the UUID of asset vocabulary to get
	* @param groupId the group id of the asset vocabulary to get
	* @return the asset vocabulary
	* @throws PortalException if a asset vocabulary with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary getAssetVocabularyByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabularyByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the asset vocabularies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset vocabularies to return
	* @param end the upper bound of the range of asset vocabularies to return (not inclusive)
	* @return the range of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getAssetVocabularies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabularies(start, end);
	}

	/**
	* Gets the number of asset vocabularies.
	*
	* @return the number of asset vocabularies
	* @throws SystemException if a system exception occurred
	*/
	public static int getAssetVocabulariesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetVocabulariesCount();
	}

	/**
	* Updates the asset vocabulary in the database. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary to update
	* @return the asset vocabulary that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary updateAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetVocabulary(assetVocabulary);
	}

	/**
	* Updates the asset vocabulary in the database. Also notifies the appropriate model listeners.
	*
	* @param assetVocabulary the asset vocabulary to update
	* @param merge whether to merge the asset vocabulary with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the asset vocabulary that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.asset.model.AssetVocabulary updateAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetVocabulary(assetVocabulary, merge);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		long userId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addVocabulary(userId, titleMap, descriptionMap, settings,
			serviceContext);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addVocabularyResources(vocabulary, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addVocabularyResources(vocabulary, communityPermissions,
			guestPermissions);
	}

	public static void deleteVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteVocabulary(vocabulary);
	}

	public static void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteVocabulary(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupsVocabularies(groupIds);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupVocabularies(groupId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary getGroupVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupVocabulary(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateVocabulary(vocabularyId, titleMap, descriptionMap,
			settings, serviceContext);
	}

	public static AssetVocabularyLocalService getService() {
		if (_service == null) {
			_service = (AssetVocabularyLocalService)PortalBeanLocatorUtil.locate(AssetVocabularyLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AssetVocabularyLocalService service) {
		_service = service;
	}

	private static AssetVocabularyLocalService _service;
}