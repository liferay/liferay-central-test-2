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
 * <a href="TagsAssetLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * <code>com.liferay.portlet.tags.service.impl.TagsAssetLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tags.service.TagsAssetLocalServiceFactory
 * @see com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil
 *
 */
public interface TagsAssetLocalService {
	public com.liferay.portlet.tags.model.TagsAsset addTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public void deleteTagsAsset(long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> dynamicQuery(
		com.liferay.portal.kernel.dao.search.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset getTagsAsset(long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public com.liferay.portlet.tags.model.TagsAsset updateTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public void deleteAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteAsset(java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteAsset(com.liferay.portlet.tags.model.TagsAsset asset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset getAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset getAsset(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAssetType[] getAssetTypes(
		java.lang.String languageId);

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException;

	public int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException;

	public int getAssetsCount(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException;

	public int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException;

	public int getAssetsCount(long groupId, long[] classNameIds,
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAssetDisplay[] getCompanyAssetDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getCompanyAssets(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	public int getCompanyAssetsCount(long companyId)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTopViewedAssets(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException;

	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTopViewedAssets(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAssetDisplay[] searchAssetDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.SystemException;

	public int searchAssetDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url, int height, int width,
		java.lang.Integer priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String[] entryNames, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String className,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException;
}