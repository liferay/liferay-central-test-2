/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsAssetDisplay;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;
import com.liferay.portlet.tags.service.base.TagsAssetLocalServiceBaseImpl;
import com.liferay.portlet.tags.service.persistence.TagsAssetFinder;
import com.liferay.portlet.tags.service.persistence.TagsAssetUtil;
import com.liferay.portlet.tags.service.persistence.TagsEntryUtil;
import com.liferay.portlet.tags.util.TagsAssetValidator;
import com.liferay.util.ListUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TagsAssetLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsAssetLocalServiceImpl extends TagsAssetLocalServiceBaseImpl {

	public void deleteAsset(long assetId)
		throws PortalException, SystemException {

		TagsAsset asset = TagsAssetUtil.findByPrimaryKey(assetId);

		deleteAsset(asset);
	}

	public void deleteAsset(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		TagsAsset asset = TagsAssetUtil.fetchByC_C(classNameId, classPK);

		if (asset != null) {
			deleteAsset(asset);
		}
	}

	public void deleteAsset(TagsAsset asset)
		throws PortalException, SystemException {

		TagsAssetUtil.remove(asset.getAssetId());
	}

	public TagsAsset getAsset(long assetId)
		throws PortalException, SystemException {

		return TagsAssetUtil.findByPrimaryKey(assetId);
	}

	public TagsAsset getAsset(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return TagsAssetUtil.findByC_C(classNameId, classPK);
	}

	public List getAssets(
			long[] entryIds, long[] notEntryIds, boolean andOperator, int begin,
			int end)
		throws SystemException {

		return getAssets(
			entryIds, notEntryIds, andOperator, null, null, begin, end);
	}

	public List getAssets(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			Date publishDate, Date expirationDate, int begin, int end)
		throws SystemException {

		if (andOperator) {
			return TagsAssetFinder.findByAndEntryIds(
				entryIds, notEntryIds, publishDate, expirationDate, begin, end);
		}
		else {
			return TagsAssetFinder.findByOrEntryIds(
				entryIds, notEntryIds, publishDate, expirationDate, begin, end);
		}
	}

	public int getAssetsCount(
			long[] entryIds, long[] notEntryIds, boolean andOperator)
		throws SystemException {

		return getAssetsCount(entryIds, notEntryIds, andOperator, null, null);
	}

	public int getAssetsCount(
			long[] entryIds, long[] notEntryIds, boolean andOperator,
			Date publishDate, Date expirationDate)
		throws SystemException {

		if (andOperator) {
			return TagsAssetFinder.countByAndEntryIds(
				entryIds, notEntryIds, publishDate, expirationDate);
		}
		else {
			return TagsAssetFinder.countByOrEntryIds(
				entryIds, notEntryIds, publishDate, expirationDate);
		}
	}

	public TagsAssetDisplay[] getCompanyAssetDisplays(
			long companyId, int begin, int end, String languageId)
		throws PortalException, SystemException {

		return getAssetDisplays(
			getCompanyAssets(companyId, begin, end), languageId);
	}

	public List getCompanyAssets(long companyId, int begin, int end)
		throws SystemException {

		return TagsAssetUtil.findByCompanyId(companyId, begin, end);
	}

	public int getCompanyAssetsCount(long companyId) throws SystemException {
		return TagsAssetUtil.countByCompanyId(companyId);
	}

	public TagsAsset updateAsset(
			long userId, String className, long classPK, String[] entryNames)
		throws PortalException, SystemException {

		return updateAsset(
			userId, className, classPK, entryNames, null, null, null, null,
			null, null, null, null, null, 0, 0);
	}

	public TagsAsset updateAsset(
			long userId, String className, long classPK, String[] entryNames,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width)
		throws PortalException, SystemException {

		// Asset

		User user = UserUtil.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		validate(className, entryNames);

		TagsAsset asset = TagsAssetUtil.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			long assetId = CounterLocalServiceUtil.increment();

			asset = TagsAssetUtil.create(assetId);

			asset.setCompanyId(user.getCompanyId());
			asset.setUserId(user.getUserId());
			asset.setUserName(user.getFullName());
			asset.setCreateDate(now);
			asset.setClassNameId(classNameId);
			asset.setClassPK(classPK);
			asset.setPublishDate(publishDate);
			asset.setExpirationDate(expirationDate);
		}

		asset.setModifiedDate(now);
		asset.setStartDate(startDate);
		asset.setEndDate(endDate);
		asset.setPublishDate(publishDate);
		asset.setExpirationDate(expirationDate);
		asset.setMimeType(mimeType);
		asset.setTitle(title);
		asset.setDescription(description);
		asset.setSummary(summary);
		asset.setUrl(url);
		asset.setHeight(height);
		asset.setWidth(width);

		TagsAssetUtil.update(asset);

		// Entries

		List entries = new ArrayList(entryNames.length);

		for (int i = 0; i < entryNames.length; i++) {
			String name = entryNames[i].trim().toLowerCase();

			TagsEntry entry =
				TagsEntryUtil.fetchByC_N(user.getCompanyId(), name);

            if (entry == null) {
                String defaultProperties = "0:category:no category";

                TagsEntry newTagsEntry = TagsEntryLocalServiceUtil.addEntry(
					user.getUserId(), entryNames[i],
					new String[] {defaultProperties});

				entries.add(newTagsEntry);
            }
			else {
				entries.add(entry);
			}
		}

		TagsAssetUtil.setTagsEntries(asset.getAssetId(), entries);

		return asset;
	}

	public void validate(String className, String[] entryNames)
		throws PortalException {

		TagsAssetValidator validator = (TagsAssetValidator)InstancePool.get(
			PropsUtil.get(PropsUtil.TAGS_ASSET_VALIDATOR));

		validator.validate(className, entryNames);
	}

	protected TagsAssetDisplay[] getAssetDisplays(
			List assets, String languageId)
		throws PortalException, SystemException {

		TagsAssetDisplay[] assetDisplays = new TagsAssetDisplay[assets.size()];

		for (int i = 0; i < assets.size(); i++) {
			TagsAsset asset = (TagsAsset)assets.get(i);

			String className = PortalUtil.getClassName(asset.getClassNameId());
			String portletId = PortalUtil.getClassNamePortletId(className);
			String portletTitle = PortalUtil.getPortletTitle(
				portletId, asset.getCompanyId(), languageId);

			List tagsEntriesList = TagsAssetUtil.getTagsEntries(
				asset.getAssetId());

			String tagsEntries = ListUtil.toString(
				tagsEntriesList, "name", ", ");

			TagsAssetDisplay assetDisplay = new TagsAssetDisplay();

			assetDisplay.setAssetId(asset.getAssetId());
			assetDisplay.setCompanyId(asset.getCompanyId());
			assetDisplay.setUserId(asset.getUserId());
			assetDisplay.setUserName(asset.getUserName());
			assetDisplay.setCreateDate(asset.getCreateDate());
			assetDisplay.setModifiedDate(asset.getModifiedDate());
			assetDisplay.setClassNameId(asset.getClassNameId());
			assetDisplay.setClassName(className);
			assetDisplay.setClassPK(asset.getClassPK());
			assetDisplay.setPortletId(portletId);
			assetDisplay.setPortletTitle(portletTitle);
			assetDisplay.setStartDate(asset.getStartDate());
			assetDisplay.setEndDate(asset.getEndDate());
			assetDisplay.setPublishDate(asset.getPublishDate());
			assetDisplay.setExpirationDate(asset.getExpirationDate());
			assetDisplay.setMimeType(asset.getMimeType());
			assetDisplay.setTitle(asset.getTitle());
			assetDisplay.setDescription(asset.getDescription());
			assetDisplay.setSummary(asset.getSummary());
			assetDisplay.setUrl(asset.getUrl());
			assetDisplay.setHeight(asset.getHeight());
			assetDisplay.setWidth(asset.getWidth());
			assetDisplay.setTagsEntries(tagsEntries);

			assetDisplays[i] = assetDisplay;
		}

		return assetDisplays;
	}

}