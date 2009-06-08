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
 * <a href="AssetLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.asset.service.AssetLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.asset.service.AssetLocalService
 *
 */
public class AssetLocalServiceUtil {
	public static com.liferay.portlet.asset.model.Asset addAsset(
		com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		return getService().addAsset(asset);
	}

	public static com.liferay.portlet.asset.model.Asset createAsset(
		long assetId) {
		return getService().createAsset(assetId);
	}

	public static void deleteAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAsset(assetId);
	}

	public static void deleteAsset(com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		getService().deleteAsset(asset);
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

	public static com.liferay.portlet.asset.model.Asset getAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAsset(assetId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAssets(start, end);
	}

	public static int getAssetsCount()
		throws com.liferay.portal.SystemException {
		return getService().getAssetsCount();
	}

	public static com.liferay.portlet.asset.model.Asset updateAsset(
		com.liferay.portlet.asset.model.Asset asset)
		throws com.liferay.portal.SystemException {
		return getService().updateAsset(asset);
	}

	public static com.liferay.portlet.asset.model.Asset updateAsset(
		com.liferay.portlet.asset.model.Asset asset, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateAsset(asset, merge);
	}

	public static void deleteAsset(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		getService().deleteAsset(className, classPK);
	}

	public static com.liferay.portlet.asset.model.Asset getAsset(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAsset(className, classPK);
	}

	public static com.liferay.portlet.asset.model.AssetType[] getAssetTypes(
		java.lang.String languageId) {
		return getService().getAssetTypes(languageId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long[] tagIds, long[] notTagIds, boolean andOperator,
		boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssets(tagIds, notTagIds, andOperator,
			excludeZeroViewCount, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long groupId, long[] classNameIds, long[] tagIds, long[] notTagIds,
		boolean andOperator, boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssets(groupId, classNameIds, tagIds, notTagIds,
			andOperator, excludeZeroViewCount, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long[] tagIds, long[] notTagIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssets(tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long groupId, long[] classNameIds, long[] tagIds, long[] notTagIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService()
				   .getAssets(groupId, classNameIds, tagIds, notTagIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long[] tagIds, long[] notTagIds, boolean andOperator,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssets(tagIds, notTagIds, andOperator, orderByCol1,
			orderByCol2, orderByType1, orderByType2, excludeZeroViewCount,
			publishDate, expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getAssets(
		long groupId, long[] classNameIds, long[] tagIds, long[] notTagIds,
		boolean andOperator, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService()
				   .getAssets(groupId, classNameIds, tagIds, notTagIds,
			andOperator, orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public static int getAssetsCount(long[] tagIds, long[] notTagIds,
		boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssetsCount(tagIds, notTagIds, andOperator,
			excludeZeroViewCount);
	}

	public static int getAssetsCount(long groupId, long[] tagIds,
		long[] notTagIds, boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssetsCount(groupId, tagIds, notTagIds, andOperator,
			excludeZeroViewCount);
	}

	public static int getAssetsCount(long[] tagIds, long[] notTagIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssetsCount(tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public static int getAssetsCount(long groupId, long[] classNameIds,
		long[] tagIds, long[] notTagIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getAssetsCount(groupId, classNameIds, tagIds, notTagIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate);
	}

	public static com.liferay.portlet.asset.model.AssetDisplay[] getCompanyAssetDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.SystemException {
		return getService()
				   .getCompanyAssetDisplays(companyId, start, end, languageId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getCompanyAssets(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyAssets(companyId, start, end);
	}

	public static int getCompanyAssetsCount(long companyId)
		throws com.liferay.portal.SystemException {
		return getService().getCompanyAssetsCount(companyId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getTopViewedAssets(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getTopViewedAssets(className, asc, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> getTopViewedAssets(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getTopViewedAssets(className, asc, start, end);
	}

	public static com.liferay.portlet.asset.model.Asset incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return getService().incrementViewCounter(className, classPK);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().search(companyId, portletId, keywords, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetDisplay[] searchAssetDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService()
				   .searchAssetDisplays(companyId, portletId, keywords,
			languageId, start, end);
	}

	public static int searchAssetDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		return getService()
				   .searchAssetDisplaysCount(companyId, portletId, keywords,
			languageId);
	}

	public static com.liferay.portlet.asset.model.Asset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateAsset(userId, groupId, className, classPK,
			categoryIds, tagNames);
	}

	public static com.liferay.portlet.asset.model.Asset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames, boolean visible,
		java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateAsset(userId, groupId, className, classPK,
			categoryIds, tagNames, visible, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url, height,
			width, priority, sync);
	}

	public static com.liferay.portlet.asset.model.Asset updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateVisible(className, classPK, visible);
	}

	public static void validate(java.lang.String className,
		java.lang.String[] tagNames) throws com.liferay.portal.PortalException {
		getService().validate(className, tagNames);
	}

	public static AssetLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("AssetLocalService is not set");
		}

		return _service;
	}

	public void setService(AssetLocalService service) {
		_service = service;
	}

	private static AssetLocalService _service;
}