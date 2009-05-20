/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
 * This class provides static methods for the
 * <code>com.liferay.portlet.asset.service.AssetCategoryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.asset.service.AssetCategoryLocalService
 *
 */
public class AssetCategoryLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetCategory addAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		return getService().addAssetCategory(assetCategory);
	}

	public static com.liferay.portlet.asset.model.AssetCategory createAssetCategory(
		long categoryId) {
		return getService().createAssetCategory(categoryId);
	}

	public static void deleteAssetCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAssetCategory(categoryId);
	}

	public static void deleteAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		getService().deleteAssetCategory(assetCategory);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getAssetCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAssetCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAssetCategories(start, end);
	}

	public static int getAssetCategoriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getAssetCategoriesCount();
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory)
		throws com.liferay.portal.SystemException {
		return getService().updateAssetCategory(assetCategory);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateAssetCategory(
		com.liferay.portlet.asset.model.AssetCategory assetCategory,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateAssetCategory(assetCategory, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategory addCategory(
		long userId, long vocabularyId, long parentCategoryId,
		java.lang.String name, java.lang.String[] properties,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .addCategory(userId, vocabularyId, parentCategoryId, name,
			properties, serviceContext);
	}

	public static void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryResources(category, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addCategoryResources(
		com.liferay.portlet.asset.model.AssetCategory category,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryResources(category, communityPermissions,
			guestPermissions);
	}

	public static void deleteCategory(long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategory(categoryId);
	}

	public static void deleteCategory(
		com.liferay.portlet.asset.model.AssetCategory category)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategory(category);
	}

	public static void deleteVocabularyCategories(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabularyCategories(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getAssetCategories(
		long assetId) throws com.liferay.portal.SystemException {
		return getService().getAssetCategories(assetId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories()
		throws com.liferay.portal.SystemException {
		return getService().getCategories();
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getCategories(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getCategories(
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().getCategories(classNameId, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetCategory getCategory(
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategory(categoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getChildCategories(
		long parentCategoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getChildCategories(parentCategoryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyCategories(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getVocabularyCategories(vocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategory> getVocabularyRootCategories(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getVocabularyRootCategories(vocabularyId);
	}

	public static void mergeCategories(long fromCategoryId, long toCategoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().mergeCategories(fromCategoryId, toCategoryId);
	}

	public static com.liferay.portal.kernel.json.JSONArray search(
		long groupId, java.lang.String name, java.lang.String[] properties,
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().search(groupId, name, properties, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetCategory updateCategory(
		long userId, long categoryId, long vocabularyId, long parentCategoryId,
		java.lang.String name, java.lang.String[] properties)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateCategory(userId, categoryId, vocabularyId,
			parentCategoryId, name, properties);
	}

	public static AssetCategoryLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("AssetCategoryLocalService is not set");
		}

		return _service;
	}

	public void setService(AssetCategoryLocalService service) {
		_service = service;
	}

	private static AssetCategoryLocalService _service;
}