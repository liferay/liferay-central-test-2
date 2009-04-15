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

package com.liferay.portlet.tags.service;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

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
 * @see com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil
 *
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TagsAssetLocalService {
	public com.liferay.portlet.tags.model.TagsAsset addTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset createTagsAsset(
		long assetId);

	public void deleteTagsAsset(long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	public void deleteTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsAsset getTagsAsset(long assetId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTagsAssets(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getTagsAssetsCount() throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateTagsAsset(
		com.liferay.portlet.tags.model.TagsAsset tagsAsset, boolean merge)
		throws com.liferay.portal.SystemException;

	public void deleteAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteAsset(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	public void deleteAsset(com.liferay.portlet.tags.model.TagsAsset asset)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsAsset getAsset(long assetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsAsset getAsset(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsAssetType[] getAssetTypes(
		java.lang.String languageId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getAssets(
		long groupId, long[] classNameIds, long[] entryIds, long[] notEntryIds,
		boolean andOperator, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetsCount(long groupId, long[] entryIds,
		long[] notEntryIds, boolean andOperator, boolean excludeZeroViewCount)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetsCount(long groupId, long[] classNameIds,
		long[] entryIds, long[] notEntryIds, boolean andOperator,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsAssetDisplay[] getCompanyAssetDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getCompanyAssets(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyAssetsCount(long companyId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTopViewedAssets(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.tags.model.TagsAsset> getTopViewedAssets(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.tags.model.TagsAssetDisplay[] searchAssetDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchAssetDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String[] categoryNames, java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String[] categoryNames, java.lang.String[] entryNames,
		boolean visible, java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width, java.lang.Integer priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String[] categoryNames, java.lang.String[] entryNames,
		boolean visible, java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.tags.model.TagsAsset updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void validate(java.lang.String className,
		java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException;
}