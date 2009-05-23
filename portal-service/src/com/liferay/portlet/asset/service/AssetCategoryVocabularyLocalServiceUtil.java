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
 * <a href="AssetCategoryVocabularyLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.asset.service.AssetCategoryVocabularyLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.asset.service.AssetCategoryVocabularyLocalService
 *
 */
public class AssetCategoryVocabularyLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary addAssetCategoryVocabulary(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary)
		throws com.liferay.portal.SystemException {
		return getService().addAssetCategoryVocabulary(assetCategoryVocabulary);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary createAssetCategoryVocabulary(
		long categoryVocabularyId) {
		return getService().createAssetCategoryVocabulary(categoryVocabularyId);
	}

	public static void deleteAssetCategoryVocabulary(long categoryVocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAssetCategoryVocabulary(categoryVocabularyId);
	}

	public static void deleteAssetCategoryVocabulary(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary)
		throws com.liferay.portal.SystemException {
		getService().deleteAssetCategoryVocabulary(assetCategoryVocabulary);
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

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary getAssetCategoryVocabulary(
		long categoryVocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAssetCategoryVocabulary(categoryVocabularyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getAssetCategoryVocabularies(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAssetCategoryVocabularies(start, end);
	}

	public static int getAssetCategoryVocabulariesCount()
		throws com.liferay.portal.SystemException {
		return getService().getAssetCategoryVocabulariesCount();
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary updateAssetCategoryVocabulary(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary)
		throws com.liferay.portal.SystemException {
		return getService()
				   .updateAssetCategoryVocabulary(assetCategoryVocabulary);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary updateAssetCategoryVocabulary(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary assetCategoryVocabulary,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService()
				   .updateAssetCategoryVocabulary(assetCategoryVocabulary, merge);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary addCategoryVocabulary(
		long userId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addCategoryVocabulary(userId, name, serviceContext);
	}

	public static void addCategoryVocabularyResources(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary categoryVocabulary,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryVocabularyResources(categoryVocabulary,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addCategoryVocabularyResources(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary categoryVocabulary,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addCategoryVocabularyResources(categoryVocabulary,
			communityPermissions, guestPermissions);
	}

	public static void deleteCategoryVocabulary(long categoryVocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategoryVocabulary(categoryVocabularyId);
	}

	public static void deleteCategoryVocabulary(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary categoryVocabulary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteCategoryVocabulary(categoryVocabulary);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getCompanyCategoryVocabularies(
		long companyId) throws com.liferay.portal.SystemException {
		return getService().getCompanyCategoryVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getGroupCategoryVocabularies(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupCategoryVocabularies(groupId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary getGroupCategoryVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupCategoryVocabulary(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary getCategoryVocabulary(
		long categoryVocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getCategoryVocabulary(categoryVocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary updateCategoryVocabulary(
		long categoryVocabularyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateCategoryVocabulary(categoryVocabularyId, name);
	}

	public static AssetCategoryVocabularyLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"AssetCategoryVocabularyLocalService is not set");
		}

		return _service;
	}

	public void setService(AssetCategoryVocabularyLocalService service) {
		_service = service;
	}

	private static AssetCategoryVocabularyLocalService _service;
}