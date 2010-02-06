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
 * <a href="AssetCategoryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetCategoryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryLocalService
 * @generated
 */
public class AssetCategoryLocalServiceWrapper
	implements AssetCategoryLocalService {
	public AssetCategoryLocalServiceWrapper(
		AssetCategoryLocalService assetCategoryLocalService) {
		_assetCategoryLocalService = assetCategoryLocalService;
	}

	public com.liferay.portlet.asset.model.AssetCategory addAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.addAssetCategory(assetCategory);
	}

	public com.liferay.portlet.asset.model.AssetCategory createAssetCategory(
		long categoryId) {
		return _assetCategoryLocalService.createAssetCategory(categoryId);
	}

	public void deleteAssetCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.deleteAssetCategory(categoryId);
	}

	public void deleteAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		_assetCategoryLocalService.deleteAssetCategory(assetCategory);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.asset.model.AssetCategory getAssetCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getAssetCategory(categoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getAssetCategories(start, end);
	}

	public int getAssetCategoriesCount()
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getAssetCategoriesCount();
	}

	public com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	public com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.updateAssetCategory(assetCategory,
			merge);
	}

	public com.liferay.portlet.asset.model.AssetCategory addCategory(
		java.lang.String uuid, long userId, long parentCategoryId,
		java.util.Map<java.util.Locale, String> titleMap, long vocabularyId,
		java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryLocalService.addCategory(uuid, userId,
			parentCategoryId, titleMap, vocabularyId, categoryProperties,
			serviceContext);
	}

	public void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.addCategoryResources(category,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.addCategoryResources(category,
			communityPermissions, guestPermissions);
	}

	public void deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.deleteCategory(category);
	}

	public void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.deleteCategory(categoryId);
	}

	public void deleteVocabularyCategories(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.deleteVocabularyCategories(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getCategories();
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getCategories(classNameId, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getCategories(className, classPK);
	}

	public com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getCategory(categoryId);
	}

	public long[] getCategoryIds(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getCategoryIds(className, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getChildCategories(parentCategoryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getEntryCategories(
		long entryId) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getEntryCategories(entryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getVocabularyCategories(vocabularyId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.getVocabularyRootCategories(vocabularyId);
	}

	public void mergeCategories(long fromCategoryId, long toCategoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetCategoryLocalService.mergeCategories(fromCategoryId, toCategoryId);
	}

	public com.liferay.portal.kernel.json.JSONArray search(long groupId,
		java.lang.String name, java.lang.String[] categoryProperties,
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetCategoryLocalService.search(groupId, name,
			categoryProperties, start, end);
	}

	public com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long userId, long categoryId, long parentCategoryId,
		java.util.Map<java.util.Locale, String> titleMap, long vocabularyId,
		java.lang.String[] categoryProperties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetCategoryLocalService.updateCategory(userId, categoryId,
			parentCategoryId, titleMap, vocabularyId, categoryProperties,
			serviceContext);
	}

	public AssetCategoryLocalService getWrappedAssetCategoryLocalService() {
		return _assetCategoryLocalService;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;
}