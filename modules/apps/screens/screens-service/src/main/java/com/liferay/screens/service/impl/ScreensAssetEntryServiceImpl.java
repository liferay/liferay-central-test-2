/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.screens.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.permission.JournalArticlePermission;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletItem;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.service.permission.AssetEntryPermission;
import com.liferay.screens.service.base.ScreensAssetEntryServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletPreferences;

/**
 * @author José Manuel Navarro
 */
public class ScreensAssetEntryServiceImpl
	extends ScreensAssetEntryServiceBaseImpl {

	@Override
	public JSONArray getAssetEntries(
			AssetEntryQuery assetEntryQuery, Locale locale)
		throws PortalException {

		List<AssetEntry> assetEntries = assetEntryLocalService.getEntries(
			assetEntryQuery);

		assetEntries = filterAssetEntries(assetEntries);

		return toJSONArray(assetEntries, locale);
	}

	@Override
	public JSONArray getAssetEntries(
			long companyId, long groupId, String portletItemName, Locale locale,
			int max)
		throws PortalException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			PortletItem.class);

		Property property = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(property.eq(portletItemName));

		dynamicQuery.setLimit(0, 1);

		List<PortletItem> portletItems = portletItemLocalService.dynamicQuery(
			dynamicQuery);

		if (portletItems.isEmpty()) {
			throw new PortalException(
				"No portlet items associated with portlet item name " +
					portletItemName);
		}

		PortletItem portletItem = portletItems.get(0);

		PortletPreferences portletPreferences =
			portletPreferencesLocalService.getPreferences(
				portletItem.getCompanyId(), portletItem.getPortletItemId(),
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED, 0,
				portletItem.getPortletId());

		String selectionStyle = GetterUtil.getString(
			portletPreferences.getValue("selectionStyle", null), "dynamic");

		if (selectionStyle.equals("dynamic")) {
			if (max > 500) {
				max = 500;
			}

			Layout layout = LayoutUtil.fetchByCompanyId_First(companyId, null);

			if (layout != null) {
				List<AssetEntry> assetEntries =
					AssetPublisherUtil.getAssetEntries(
						portletPreferences, layout, groupId, max, false);

				assetEntries = filterAssetEntries(assetEntries);

				return toJSONArray(assetEntries, locale);
			}
			else {
				return JSONFactoryUtil.createJSONArray();
			}
		}
		else {
			try {
				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(getUser());

				List<AssetEntry> assetEntries =
					AssetPublisherUtil.getAssetEntries(
						null, portletPreferences, permissionChecker,
						new long[] {groupId}, false, false, false);

				assetEntries = filterAssetEntries(assetEntries);

				return toJSONArray(assetEntries, locale);
			}
			catch (PortalException | SystemException e) {
				throw e;
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
		}
	}

	protected List<AssetEntry> filterAssetEntries(List<AssetEntry> assetEntries)
		throws PortalException {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>(
			assetEntries.size());

		for (AssetEntry assetEntry : assetEntries) {
			if (AssetEntryPermission.contains(
					getPermissionChecker(), assetEntry, ActionKeys.VIEW)) {

				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	protected JSONObject getAssetObjectJSONObject(AssetEntry assetEntry)
		throws PortalException {

		AssetEntryPermission.check(
			getPermissionChecker(), assetEntry, ActionKeys.VIEW);

		String className = assetEntry.getClassName();

		if (className.equals(
				"com.liferay.document.library.kernel.model.DLFileEntry")) {

			return getFileEntryJSONObject(assetEntry);
		}
		else if (className.equals(
					"com.liferay.portlet.journal.model.JournalArticle")) {

			return getJournalArticleJSONObject(assetEntry);
		}

		return JSONFactoryUtil.createJSONObject();
	}

	protected JSONObject getFileEntryJSONObject(AssetEntry assetEntry)
		throws PortalException {

		FileEntry fileEntry = dlAppService.getFileEntry(
			assetEntry.getClassPK());

		JSONObject fileEntryJSONObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(fileEntry));

		fileEntryJSONObject.put("url", getFileEntryPreviewURL(fileEntry));

		return fileEntryJSONObject;
	}

	protected String getFileEntryPreviewURL(FileEntry fileEntry) {
		StringBundler sb = new StringBundler(9);

		sb.append(PortalUtil.getPathContext());
		sb.append("/documents/");
		sb.append(fileEntry.getRepositoryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getFolderId());
		sb.append(StringPool.SLASH);
		sb.append(HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())));
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());

		return sb.toString();
	}

	protected JSONObject getJournalArticleJSONObject(AssetEntry assetEntry)
		throws PortalException {

		JournalArticle journalArticle = null;

		JournalArticlePermission.check(
			getPermissionChecker(), assetEntry.getClassPK(), ActionKeys.VIEW);

		try {
			journalArticle = journalArticleLocalService.getArticle(
				assetEntry.getClassPK());
		}
		catch (Exception nsae) {
			JournalArticleResource journalArticleResource =
				journalArticleResourceLocalService.getArticleResource(
					assetEntry.getClassPK());

			journalArticle = journalArticleLocalService.getLatestArticle(
				journalArticleResource.getGroupId(),
				journalArticleResource.getArticleId());
		}

		JSONObject journalArticleJSONObject = JSONFactoryUtil.createJSONObject(
			JSONFactoryUtil.looseSerialize(journalArticle));

		journalArticleJSONObject.remove("content");

		return journalArticleJSONObject;
	}

	protected JSONArray toJSONArray(
			List<AssetEntry> assetEntries, Locale locale)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (AssetEntry assetEntry : assetEntries) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				JSONFactoryUtil.looseSerialize(assetEntry));

			jsonObject.put("description", assetEntry.getDescription(locale));
			jsonObject.put("object", getAssetObjectJSONObject(assetEntry));
			jsonObject.put("summary", assetEntry.getSummary(locale));
			jsonObject.put("title", assetEntry.getTitle(locale));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

}