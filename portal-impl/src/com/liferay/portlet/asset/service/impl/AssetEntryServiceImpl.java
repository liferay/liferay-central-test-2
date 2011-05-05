/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetEntryDisplay;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.service.base.AssetEntryServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.service.permission.AssetTagPermission;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
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
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class AssetEntryServiceImpl extends AssetEntryServiceBaseImpl {

	public List<AssetEntry> getCompanyEntries(
			long companyId, int start, int end)
		throws SystemException {

		return assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	public String getCompanyEntriesRSS(
			long companyId, int max, String type, double version,
			String displayStyle, String feedURL, String tagURL)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();

		List<AssetEntry> entries = getCompanyEntries(companyId, 0, max);

		return exportToRSS(
			name, null, type, version, displayStyle, feedURL, tagURL, entries);
	}

	public AssetEntryDisplay[] getCompanyEntryDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return assetEntryLocalService.getCompanyEntryDisplays(
			companyId, start, end, languageId);
	}

	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		setupQuery(entryQuery);

		Object[] results = filterQuery(entryQuery);

		return (List<AssetEntry>)results[0];
	}

	public int getEntriesCount(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		setupQuery(entryQuery);

		Object[] results = filterQuery(entryQuery);

		return (Integer)results[1];
	}

	public String getEntriesRSS(
			AssetEntryQuery entryQuery, String name, String type,
			double version, String displayStyle, String feedURL, String tagURL)
		throws PortalException, SystemException {

		setupQuery(entryQuery);

		Object[] results = filterQuery(entryQuery);

		return exportToRSS(
			name, null, type, version, displayStyle, feedURL, tagURL,
			(List<AssetEntry>)results[0]);
	}

	public AssetEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return assetEntryLocalService.getEntry(entryId);
	}

	public void incrementViewCounter(String className, long classPK)
		throws PortalException, SystemException {

		assetEntryLocalService.incrementViewCounter(
			getGuestOrUserId(), className, classPK);
	}

	public AssetEntryDisplay[] searchEntryDisplays(
			long companyId, long[] groupIds, String portletId, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		return assetEntryLocalService.searchEntryDisplays(
			companyId, groupIds, portletId, keywords, languageId, start, end);
	}

	public int searchEntryDisplaysCount(
			long companyId, long[] groupIds, String portletId, String keywords,
			String languageId)
		throws SystemException {

		return assetEntryLocalService.searchEntryDisplaysCount(
			companyId, groupIds, portletId, keywords, languageId);
	}

	public AssetEntry updateEntry(
			long groupId, String className, long classPK, String classUuid,
			long[] categoryIds, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date publishDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, String layoutUuid, int height, int width,
			Integer priority, boolean sync)
		throws PortalException, SystemException {

		return assetEntryLocalService.updateEntry(
			getUserId(), groupId, className, classPK, classUuid, categoryIds,
			tagNames, visible, startDate, endDate, publishDate, expirationDate,
			mimeType, title, description, summary, url, layoutUuid, height,
			width, priority, sync);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String displayStyle, String feedURL, String tagURL,
			List<AssetEntry> assetEntries)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(RSSUtil.getFeedType(type, version));
		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(GetterUtil.getString(description, name));

		List<SyndEntry> entries = new ArrayList<SyndEntry>();

		syndFeed.setEntries(entries);

		for (AssetEntry entry : assetEntries) {
			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(entry.getClassName());

			String author = HtmlUtil.escape(
				PortalUtil.getUserName(entry.getUserId(), entry.getUserName()));

			StringBundler sb = new StringBundler(4);

			sb.append(tagURL);
			sb.append(assetRendererFactory.getType());
			sb.append("/id/");
			sb.append(entry.getEntryId());

			String link = sb.toString();

			String value = null;

			if (displayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE)) {
				value = StringPool.BLANK;
			}
			else {
				value = entry.getSummary();
			}

			SyndEntry syndEntry = new SyndEntryImpl();

			syndEntry.setAuthor(author);
			syndEntry.setTitle(entry.getTitle());
			syndEntry.setLink(link);
			syndEntry.setUri(syndEntry.getLink());
			syndEntry.setPublishedDate(entry.getCreateDate());
			syndEntry.setUpdatedDate(entry.getModifiedDate());

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

	protected long[] filterCategoryIds(long[] categoryIds)
		throws PortalException, SystemException {

		List<Long> viewableCategoryIds = new ArrayList<Long>();

		for (long categoryId : categoryIds) {
			if (AssetCategoryPermission.contains(
					getPermissionChecker(), categoryId, ActionKeys.VIEW)) {

				viewableCategoryIds.add(categoryId);
			}
		}

		return ArrayUtil.toArray(
			viewableCategoryIds.toArray(new Long[viewableCategoryIds.size()]));
	}

	protected long[] filterTagIds(long[] tagIds)
		throws PortalException, SystemException {

		List<Long> viewableTagIds = new ArrayList<Long>();

		for (long tagId : tagIds) {
			if (AssetTagPermission.contains(
					getPermissionChecker(), tagId, ActionKeys.VIEW)) {

				viewableTagIds.add(tagId);
			}
		}

		return ArrayUtil.toArray(
			viewableTagIds.toArray(new Long[viewableTagIds.size()]));
	}

	protected Object[] filterQuery(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		int end = entryQuery.getEnd();
		int start = entryQuery.getStart();

		entryQuery.setEnd(end + PropsValues.ASSET_FILTER_SEARCH_LIMIT);
		entryQuery.setStart(0);

		List<AssetEntry> entries = assetEntryLocalService.getEntries(
			entryQuery);

		PermissionChecker permissionChecker = getPermissionChecker();

		List<AssetEntry> filteredEntries = new ArrayList<AssetEntry>();

		for (AssetEntry entry : entries) {
			String className = entry.getClassName();
			long classPK = entry.getClassPK();

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			try {
				if (assetRendererFactory.hasPermission(
						permissionChecker, classPK, ActionKeys.VIEW)) {

					filteredEntries.add(entry);
				}
			}
			catch (Exception e) {
			}
		}

		int length = filteredEntries.size();

		if ((end != QueryUtil.ALL_POS) && (start != QueryUtil.ALL_POS)) {
			if (end > length) {
				end = length;
			}

			if (start > length) {
				start = length;
			}

			filteredEntries = filteredEntries.subList(start, end);
		}

		entryQuery.setEnd(end);
		entryQuery.setStart(start);

		return new Object[] {filteredEntries, length};
	}

	protected void setupQuery(AssetEntryQuery entryQuery)
		throws PortalException, SystemException {

		entryQuery.setAllCategoryIds(
			filterCategoryIds(entryQuery.getAllCategoryIds()));
		entryQuery.setAllTagIds(filterTagIds(entryQuery.getAllTagIds()));
		entryQuery.setAnyCategoryIds(
			filterCategoryIds(entryQuery.getAnyCategoryIds()));
		entryQuery.setAnyTagIds(filterTagIds(entryQuery.getAnyTagIds()));
	}

}