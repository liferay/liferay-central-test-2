/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service;


/**
 * <a href="TagsAssetLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tags.service.TagsAssetLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.tags.service.TagsAssetLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsAssetLocalService
 * @see com.liferay.portlet.tags.service.TagsAssetLocalServiceFactory
 *
 */
public class TagsAssetLocalServiceUtil {
	public static com.liferay.portlet.tags.model.TagsAsset addTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.addTagsAsset(tagsAsset);
	}

	public static void deleteTagsAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		tagsAssetLocalService.deleteTagsAsset(assetId);
	}

	public static void deleteTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		tagsAssetLocalService.deleteTagsAsset(tagsAsset);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.dynamicQuery(queryInitializer, start, end);
	}

	public static com.liferay.portlet.tags.model.TagsAsset getTagsAsset(
		long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTagsAsset(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.updateTagsAsset(tagsAsset);
	}

	public static void deleteAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		tagsAssetLocalService.deleteAsset(assetId);
	}

	public static void deleteAsset(java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		tagsAssetLocalService.deleteAsset(className, classPK);
	}

	public static void deleteAsset(
		com.liferay.portlet.tags.model.TagsAsset asset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		tagsAssetLocalService.deleteAsset(asset);
	}

	public static com.liferay.portlet.tags.model.TagsAsset getAsset(
		long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAsset(assetId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset getAsset(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAsset(className, classPK);
	}

	public static com.liferay.portlet.tags.model.TagsAssetType[] getAssetTypes(
		java.lang.String languageId) {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetTypes(languageId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(groupId, classNameIds, entryIds,
			notEntryIds, andOperator, excludeZeroViewCount, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(groupId, classNameIds, entryIds,
			notEntryIds, andOperator, excludeZeroViewCount, publishDate,
			expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(entryIds, notEntryIds,
			andOperator, orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssets(groupId, classNameIds, entryIds,
			notEntryIds, andOperator, orderByCol1, orderByCol2, orderByType1,
			orderByType2, excludeZeroViewCount, publishDate, expirationDate,
			start, end);
	}

	public static int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount);
	}

	public static int getAssetsCount(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(groupId, entryIds,
			notEntryIds, andOperator, excludeZeroViewCount);
	}

	public static int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(entryIds, notEntryIds,
			andOperator, excludeZeroViewCount, publishDate, expirationDate);
	}

	public static int getAssetsCount(long groupId, long[] classNameIds,
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getAssetsCount(groupId, classNameIds,
			entryIds, notEntryIds, andOperator, excludeZeroViewCount,
			publishDate, expirationDate);
	}

	public static com.liferay.portlet.tags.model.TagsAssetDisplay[] getCompanyAssetDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getCompanyAssetDisplays(companyId, start,
			end, languageId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getCompanyAssets(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getCompanyAssets(companyId, start, end);
	}

	public static int getCompanyAssetsCount(long companyId)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getCompanyAssetsCount(companyId);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTopViewedAssets(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTopViewedAssets(className, asc, start,
			end);
	}

	public static java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTopViewedAssets(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.getTopViewedAssets(className, asc, start,
			end);
	}

	public static com.liferay.portlet.tags.model.TagsAsset incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.incrementViewCounter(className, classPK);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.search(companyId, portletId, keywords,
			start, end);
	}

	public static com.liferay.portlet.tags.model.TagsAssetDisplay[] searchAssetDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.searchAssetDisplays(companyId, portletId,
			keywords, languageId, start, end);
	}

	public static int searchAssetDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.searchAssetDisplaysCount(companyId,
			portletId, keywords, languageId);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.updateAsset(userId, groupId, className,
			classPK, entryNames);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url, int height, int width,
		java.lang.Integer priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.updateAsset(userId, groupId, className,
			classPK, entryNames, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url, height,
			width, priority);
	}

	public static com.liferay.portlet.tags.model.TagsAsset updateAsset(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		return tagsAssetLocalService.updateAsset(userId, groupId, className,
			classPK, entryNames, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url, height,
			width, priority, sync);
	}

	public static void validate(java.lang.String className,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException {
		TagsAssetLocalService tagsAssetLocalService = TagsAssetLocalServiceFactory.getService();

		tagsAssetLocalService.validate(className, entryNames);
	}
}