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
		long vocabularyId) {
		return getService().createAssetCategoryVocabulary(vocabularyId);
	}

	public static void deleteAssetCategoryVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAssetCategoryVocabulary(vocabularyId);
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
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAssetCategoryVocabulary(vocabularyId);
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

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary addVocabulary(
		long userId, java.lang.String name,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addVocabulary(userId, name, serviceContext);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary vocabulary,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addVocabularyResources(vocabulary, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addVocabularyResources(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary vocabulary,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addVocabularyResources(vocabulary, communityPermissions,
			guestPermissions);
	}

	public static void deleteVocabulary(long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabulary(vocabularyId);
	}

	public static void deleteVocabulary(
		com.liferay.portlet.asset.model.AssetCategoryVocabulary vocabulary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteVocabulary(vocabulary);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getCompanyVocabularies(
		long companyId) throws com.liferay.portal.SystemException {
		return getService().getCompanyVocabularies(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetCategoryVocabulary> getGroupVocabularies(
		long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupVocabularies(groupId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary getGroupVocabulary(
		long groupId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getGroupVocabulary(groupId, name);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary getVocabulary(
		long vocabularyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getVocabulary(vocabularyId);
	}

	public static com.liferay.portlet.asset.model.AssetCategoryVocabulary updateVocabulary(
		long vocabularyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateVocabulary(vocabularyId, name);
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