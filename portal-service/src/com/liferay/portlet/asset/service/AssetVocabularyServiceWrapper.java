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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetVocabularyService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyService
 * @generated
 */
@ProviderType
public class AssetVocabularyServiceWrapper implements AssetVocabularyService,
	ServiceWrapper<AssetVocabularyService> {
	public AssetVocabularyServiceWrapper(
		AssetVocabularyService assetVocabularyService) {
		_assetVocabularyService = assetVocabularyService;
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		java.lang.String title,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.addVocabulary(title, serviceContext);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.addVocabulary(title, titleMap,
			descriptionMap, settings, serviceContext);
	}

	/**
	* @deprecated As of 6.1.0 {@link #addVocabulary(String, Map, Map, String,
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.addVocabulary(titleMap, descriptionMap,
			settings, serviceContext);
	}

	/**
	* @deprecated As of 6.2.0, Replaced by {@link #deleteVocabularies(long[],
	ServiceContext)}
	*/
	@Deprecated
	@Override
	public void deleteVocabularies(long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetVocabularyService.deleteVocabularies(vocabularyIds);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> deleteVocabularies(
		long[] vocabularyIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.deleteVocabularies(vocabularyIds,
			serviceContext);
	}

	@Override
	public void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetVocabularyService.deleteVocabulary(vocabularyId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary fetchVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.fetchVocabulary(vocabularyId);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _assetVocabularyService.getBeanIdentifier();
	}

	/**
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getCompanyVocabularies(companyId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getGroupVocabularies(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, boolean createDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getGroupVocabularies(groupId,
			createDefaultVocabulary);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, boolean createDefaultVocabulary, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getGroupVocabularies(groupId,
			createDefaultVocabulary, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc) {
		return _assetVocabularyService.getGroupVocabularies(groupId, name,
			start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc) {
		return _assetVocabularyService.getGroupVocabularies(groupId, start,
			end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long[] groupIds) {
		return _assetVocabularyService.getGroupVocabularies(groupIds);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId) {
		return _assetVocabularyService.getGroupVocabulariesCount(groupId);
	}

	@Override
	public int getGroupVocabulariesCount(long groupId, java.lang.String name) {
		return _assetVocabularyService.getGroupVocabulariesCount(groupId, name);
	}

	@Override
	public int getGroupVocabulariesCount(long[] groupIds) {
		return _assetVocabularyService.getGroupVocabulariesCount(groupIds);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabularyDisplay getGroupVocabulariesDisplay(
		long groupId, java.lang.String name, int start, int end,
		boolean addDefaultVocabulary,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getGroupVocabulariesDisplay(groupId,
			name, start, end, addDefaultVocabulary, obc);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabularyDisplay getGroupVocabulariesDisplay(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getGroupVocabulariesDisplay(groupId,
			name, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds) {
		return _assetVocabularyService.getGroupsVocabularies(groupIds);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, java.lang.String className) {
		return _assetVocabularyService.getGroupsVocabularies(groupIds, className);
	}

	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds, java.lang.String className, long classTypePK) {
		return _assetVocabularyService.getGroupsVocabularies(groupIds,
			className, classTypePK);
	}

	/**
	* @deprecated As of 6.2.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONGroupVocabularies(
		long groupId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.asset.model.AssetVocabulary> obc)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getJSONGroupVocabularies(groupId, name,
			start, end, obc);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#AssetUtil.filterVocabularyIds(PermissionChecker, long[])}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getVocabularies(
		long[] vocabularyIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getVocabularies(vocabularyIds);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.getVocabulary(vocabularyId);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabularyDisplay searchVocabulariesDisplay(
		long groupId, java.lang.String title, int start, int end,
		boolean addDefaultVocabulary)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.searchVocabulariesDisplay(groupId,
			title, start, end, addDefaultVocabulary);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetVocabularyService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId, java.lang.String title,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.updateVocabulary(vocabularyId, title,
			titleMap, descriptionMap, settings, serviceContext);
	}

	/**
	* @deprecated As of 6.1.0, {@link #updateVocabulary(long, String, Map, Map,
	String, ServiceContext)}
	*/
	@Deprecated
	@Override
	public com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetVocabularyService.updateVocabulary(vocabularyId, titleMap,
			descriptionMap, settings, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public AssetVocabularyService getWrappedAssetVocabularyService() {
		return _assetVocabularyService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedAssetVocabularyService(
		AssetVocabularyService assetVocabularyService) {
		_assetVocabularyService = assetVocabularyService;
	}

	@Override
	public AssetVocabularyService getWrappedService() {
		return _assetVocabularyService;
	}

	@Override
	public void setWrappedService(AssetVocabularyService assetVocabularyService) {
		_assetVocabularyService = assetVocabularyService;
	}

	private AssetVocabularyService _assetVocabularyService;
}