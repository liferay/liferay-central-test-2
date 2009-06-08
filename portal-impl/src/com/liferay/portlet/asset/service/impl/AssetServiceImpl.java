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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.Asset;
import com.liferay.portlet.asset.model.AssetDisplay;
import com.liferay.portlet.asset.model.AssetType;
import com.liferay.portlet.asset.service.base.AssetServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetTagPermission;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AssetServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class AssetServiceImpl extends AssetServiceBaseImpl {

	public void deleteAsset(long assetId)
		throws PortalException, SystemException {

		assetLocalService.deleteAsset(assetId);
	}

	public Asset getAsset(long assetId)
		throws PortalException, SystemException {

		return assetLocalService.getAsset(assetId);
	}

	public List<Asset> getAssets(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator, String orderByCol1,
			String orderByCol2, String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int start, int end)
		throws PortalException, SystemException {

		long[][] viewableTagIds = getViewableTagIds(tagIds, notTagIds);

		tagIds = viewableTagIds[0];
		notTagIds = viewableTagIds[1];

		return assetLocalService.getAssets(
			groupId, classNameIds, tagIds, notTagIds, andOperator,
			orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public int getAssetsCount(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate)
		throws PortalException, SystemException {

		long[][] viewableTagIds = getViewableTagIds(tagIds, notTagIds);

		tagIds = viewableTagIds[0];
		notTagIds = viewableTagIds[1];

		return assetLocalService.getAssetsCount(
			groupId, classNameIds, tagIds, notTagIds, andOperator,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public String getAssetsRSS(
			long groupId, long[] classNameIds, long[] tagIds,
			long[] notTagIds, boolean andOperator, String orderByCol1,
			String orderByCol2, String orderByType1, String orderByType2,
			boolean excludeZeroViewCount, Date publishDate, Date expirationDate,
			int max, String type, double version, String displayStyle,
			String feedURL, String tagURL)
		throws PortalException, SystemException {

		Group group = groupPersistence.findByPrimaryKey(groupId);

		String name = group.getName();

		List<Asset> assets = assetLocalService.getAssets(
			groupId, classNameIds, tagIds, notTagIds, andOperator,
			orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, 0, max);

		return exportToRSS(
			name, null, type, version, displayStyle, feedURL, tagURL, assets);
	}

	public AssetType[] getAssetTypes(String languageId) {
		return assetLocalService.getAssetTypes(languageId);
	}

	public AssetDisplay[] getCompanyAssetDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return assetLocalService.getCompanyAssetDisplays(
			companyId, start, end, languageId);
	}

	public List<Asset> getCompanyAssets(long companyId, int start, int end)
		throws SystemException {

		return assetLocalService.getCompanyAssets(companyId, start, end);
	}

	public int getCompanyAssetsCount(long companyId) throws SystemException {
		return assetLocalService.getCompanyAssetsCount(companyId);
	}

	public String getCompanyAssetsRSS(
			long companyId, int max, String type, double version,
			String displayStyle, String feedURL, String tagURL)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();

		List<Asset> assets = getCompanyAssets(companyId, 0, max);

		return exportToRSS(
			name, null, type, version, displayStyle, feedURL, tagURL, assets);
	}

	public Asset incrementViewCounter(String className, long classPK)
		throws SystemException {

		return assetLocalService.incrementViewCounter(className, classPK);
	}

	public AssetDisplay[] searchAssetDisplays(
			long companyId, String portletId, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		return assetLocalService.searchAssetDisplays(
			companyId, portletId, keywords, languageId, start, end);
	}

	public int searchAssetDisplaysCount(
			long companyId, String portletId, String keywords,
			String languageId)
		throws SystemException {

		return assetLocalService.searchAssetDisplaysCount(
			companyId, portletId, keywords, languageId);
	}

	public Asset updateAsset(
			long groupId, String className, long classPK,
			String[] categoryNames, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, int height, int width, Integer priority)
		throws PortalException, SystemException {

		return null;
//		return assetLocalService.updateAsset(
//			getUserId(), groupId, className, classPK, categoryNames, tagNames,
//			visible, startDate, endDate, publishDate, expirationDate, mimeType,
//			title, description, summary, url, height, width, priority);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String displayStyle, String feedURL, String tagURL,
			List<Asset> assets)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (Asset asset : assets) {
			String author = PortalUtil.getUserName(
				asset.getUserId(), asset.getUserName());

			String link = tagURL + "assetId=" + asset.getAssetId();

			String value = asset.getSummary();

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(asset.getTitle());
			syndEntry.setLink(link);
			syndEntry.setUri(syndEntry.getLink());
			syndEntry.setPublishedDate(asset.getCreateDate());
			syndEntry.setUpdatedDate(asset.getModifiedDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(RSSUtil.DEFAULT_ENTRY_TYPE);
			syndContent.setValue(value);

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

	protected long[][] getViewableTagIds(long[] tagIds, long[] notTagIds)
		throws PortalException, SystemException {

		List<Long> viewableList = new ArrayList<Long>();

		for (long tagId : tagIds) {
			if (AssetTagPermission.contains(
					getPermissionChecker(), tagId, ActionKeys.VIEW)) {

				viewableList.add(tagId);
			}
			else {
				notTagIds = ArrayUtil.append(notTagIds, tagId);
			}
		}

		tagIds = new long[viewableList.size()];

		for (int i = 0; i < viewableList.size(); i++) {
			tagIds[i] = viewableList.get(i).longValue();
		}

		return new long[][] {tagIds, notTagIds};
	}

}