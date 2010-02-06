/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.asset.service;


/**
 * <a href="AssetVocabularyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetVocabularyLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetVocabularyLocalService
 * @generated
 */
public class AssetVocabularyLocalServiceWrapper
	implements AssetVocabularyLocalService {
	public AssetVocabularyLocalServiceWrapper(
		AssetVocabularyLocalService assetVocabularyLocalService) {
		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	public com.liferay.portlet.asset.model.AssetVocabulary addAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.addAssetVocabulary(assetVocabulary);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary createAssetVocabulary(
		long vocabularyId) {
		return _assetVocabularyLocalService.createAssetVocabulary(vocabularyId);
	}

	public void deleteAssetVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetVocabularyLocalService.deleteAssetVocabulary(vocabularyId);
	}

	public void deleteAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.SystemException {
		_assetVocabularyLocalService.deleteAssetVocabulary(assetVocabulary);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary getAssetVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getAssetVocabulary(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getAssetVocabularies(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getAssetVocabularies(start, end);
	}

	public int getAssetVocabulariesCount()
		throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getAssetVocabulariesCount();
	}

	public com.liferay.portlet.asset.model.AssetVocabulary updateAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary)
		throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.updateAssetVocabulary(assetVocabulary);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary updateAssetVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary assetVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.updateAssetVocabulary(assetVocabulary,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary addVocabulary(
		java.lang.String uuid, long userId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.addVocabulary(uuid, userId,
			titleMap, descriptionMap, settings, serviceContext);
	}

	public void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetVocabularyLocalService.addVocabularyResources(vocabulary,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetVocabularyLocalService.addVocabularyResources(vocabulary,
			communityPermissions, guestPermissions);
	}

	public void deleteVocabulary(
		com.liferay.portlet.asset.model.AssetVocabulary vocabulary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetVocabularyLocalService.deleteVocabulary(vocabulary);
	}

	public void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetVocabularyLocalService.deleteVocabulary(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getCompanyVocabularies(
		long companyId) throws com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getCompanyVocabularies(companyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupsVocabularies(
		long[] groupIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getGroupsVocabularies(groupIds);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getGroupVocabularies(groupId);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary getGroupVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getGroupVocabulary(groupId, name);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.getVocabulary(vocabularyId);
	}

	public com.liferay.portlet.asset.model.AssetVocabulary updateVocabulary(
		long vocabularyId, java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.lang.String settings,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetVocabularyLocalService.updateVocabulary(vocabularyId,
			titleMap, descriptionMap, settings, serviceContext);
	}

	public AssetVocabularyLocalService getWrappedAssetVocabularyLocalService() {
		return _assetVocabularyLocalService;
	}

	private AssetVocabularyLocalService _assetVocabularyLocalService;
}