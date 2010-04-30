/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTemplate;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.LayoutTypePortletConstants;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutTemplateLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.DuplicateCategoryException;
import com.liferay.portlet.asset.DuplicateVocabularyException;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="LayoutImporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class LayoutImporter {

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		boolean deleteMissingLayouts = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.booleanValue());
		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean importTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);
		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_ID);
		String portletsMergeMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.PORTLETS_MERGE_MODE,
			PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet data " + deletePortletData);
			_log.debug("Import categories " + importCategories);
			_log.debug("Import permissions " + importPermissions);
			_log.debug("Import user permissions " + importUserPermissions);
			_log.debug("Import portlet data " + importPortletData);
			_log.debug("Import portlet setup " + importPortletSetup);
			_log.debug(
				"Import portlet archived setups " +
					importPortletArchivedSetups);
			_log.debug(
				"Import portlet user preferences " +
					importPortletUserPreferences);
			_log.debug("Import theme " + importTheme);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();

		User user = UserUtil.findByPrimaryKey(userId);

		UserIdStrategy strategy = _portletImporter.getUserIdStrategy(
			user, userIdStrategy);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), strategy,
			zipReader);

		context.setPrivateLayout(privateLayout);

		// Zip

		Element root = null;
		InputStream themeZip = null;

		// Manifest

		String xml = context.getZipEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException("manifest.xml not found in the LAR");
		}

		try {
			Document doc = SAXReaderUtil.read(xml);

			root = doc.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException(e);
		}

		// Build compatibility

		Element header = root.element("header");

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			header.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type compatibility

		String larType = header.attributeValue("type");

		if (!larType.equals("layout-set")) {
			throw new LARTypeException(
				"Invalid type of LAR file (" + larType + ")");
		}

		// Import GroupId

		long sourceGroupId = GetterUtil.getLong(
			header.attributeValue("group-id"));

		context.setSourceGroupId(sourceGroupId);

		// Look and feel

		if (importTheme) {
			themeZip = context.getZipEntryAsInputStream("theme.zip");
		}

		// Look and feel

		String themeId = header.attributeValue("theme-id");
		String colorSchemeId = header.attributeValue("color-scheme-id");

		boolean useThemeZip = false;

		if (themeZip != null) {
			try {
				String importThemeId = importTheme(layoutSet, themeZip);

				if (importThemeId != null) {
					themeId = importThemeId;
					colorSchemeId =
						ColorSchemeImpl.getDefaultRegularColorSchemeId();

					useThemeZip = true;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Importing theme takes " + stopWatch.getTime() + " ms");
				}
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		boolean wapTheme = false;

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			groupId, privateLayout, themeId, colorSchemeId, StringPool.BLANK,
			wapTheme);

		// Read categories, comments, locks, ratings, and tags to make them
		// available to the data handlers through the context

		if (importCategories) {
			importCategories(context);
		}

		_portletImporter.readCategories(context, root);
		_portletImporter.readComments(context, root);
		_portletImporter.readLocks(context, root);

		if (importPermissions) {
			_permissionImporter.readPortletDataPermissions(context);
		}

		_portletImporter.readRatings(context, root);
		_portletImporter.readTags(context, root);

		// Layouts

		List<Layout> previousLayouts = LayoutUtil.findByG_P(
			groupId, privateLayout);

		List<Layout> newLayouts = new ArrayList<Layout>();

		Set<Long> newLayoutIds = new HashSet<Long>();

		Map<Long, Long> newLayoutIdPlidMap =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(Layout.class);

		List<Element> layoutEls = root.element("layouts").elements("layout");

		if (_log.isDebugEnabled()) {
			if (layoutEls.size() > 0) {
				_log.debug("Importing layouts");
			}
		}

		for (Element layoutRefEl : layoutEls) {
			long layoutId = GetterUtil.getInteger(
				layoutRefEl.attributeValue("layout-id"));

			long oldLayoutId = layoutId;

			boolean deleteLayout = GetterUtil.getBoolean(
				layoutRefEl.attributeValue("delete"));

			if (deleteLayout) {
				try {
					LayoutLocalServiceUtil.deleteLayout(
						context.getGroupId(), privateLayout, oldLayoutId);
				}
				catch (NoSuchLayoutException nsle) {
					_log.warn(
						"Error deleting layout for {" + sourceGroupId + ", " +
							privateLayout + ", " + oldLayoutId + "}");
				}

				continue;
			}

			String layoutPath = layoutRefEl.attributeValue("path");

			Element layoutEl = null;

			try {
				Document layoutDoc = SAXReaderUtil.read(
					context.getZipEntryAsString(layoutPath));

				layoutEl = layoutDoc.getRootElement();
			}
			catch (DocumentException de) {
				throw new SystemException(de);
			}

			long parentLayoutId = GetterUtil.getInteger(
				layoutEl.elementText("parent-layout-id"));

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Importing layout with layout id " + layoutId +
						" and parent layout id " + parentLayoutId);
			}

			long oldPlid = GetterUtil.getInteger(
				layoutEl.attributeValue("old-plid"));

			String name = layoutEl.elementText("name");
			String title = layoutEl.elementText("title");
			String description = layoutEl.elementText("description");
			String type = layoutEl.elementText("type");
			String typeSettings = layoutEl.elementText("type-settings");
			boolean hidden = GetterUtil.getBoolean(
				layoutEl.elementText("hidden"));
			String friendlyURL = layoutEl.elementText("friendly-url");
			boolean iconImage = GetterUtil.getBoolean(
				layoutEl.elementText("icon-image"));

			byte[] iconBytes = null;

			if (iconImage) {
				String path = layoutEl.elementText("icon-image-path");

				iconBytes = context.getZipEntryAsByteArray(path);
			}

			if (useThemeZip) {
				themeId = StringPool.BLANK;
				colorSchemeId = StringPool.BLANK;
			}
			else {
				themeId = layoutEl.elementText("theme-id");
				colorSchemeId = layoutEl.elementText("color-scheme-id");
			}

			String wapThemeId = layoutEl.elementText("wap-theme-id");
			String wapColorSchemeId = layoutEl.elementText(
				"wap-color-scheme-id");
			String css = layoutEl.elementText("css");
			int priority = GetterUtil.getInteger(
				layoutEl.elementText("priority"));

			Layout layout = null;

			if (layoutsImportMode.equals(
					PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_ADD_AS_NEW)) {

				layoutId = LayoutLocalServiceUtil.getNextLayoutId(
					groupId, privateLayout);
				friendlyURL = StringPool.SLASH + layoutId;
			}
			else if (layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_NAME)) {

				Locale locale = LocaleUtil.getDefault();

				String localizedName = LocalizationUtil.getLocalization(
					name, LocaleUtil.toLanguageId(locale));

				for (Layout curLayout : previousLayouts) {
					if (curLayout.getName(locale).equals(localizedName)) {
						layout = curLayout;

						break;
					}
				}

				if (layout == null) {
					layoutId = LayoutLocalServiceUtil.getNextLayoutId(
						groupId, privateLayout);
				}
			}
			else {
				layout = LayoutUtil.fetchByG_P_L(
					groupId, privateLayout, layoutId);

				if (layout == null) {
					layoutId = LayoutLocalServiceUtil.getNextLayoutId(
						groupId, privateLayout);
				}
			}

			if (_log.isDebugEnabled()) {
				if (layout == null) {
					_log.debug(
						"Layout with {groupId=" + groupId + ",privateLayout=" +
							privateLayout + ",layoutId=" + layoutId +
								"} does not exist");
				}
				else {
					_log.debug(
						"Layout with {groupId=" + groupId + ",privateLayout=" +
							privateLayout + ",layoutId=" + layoutId +
								"} exists");
				}
			}

			if (layout == null) {
				long plid = CounterLocalServiceUtil.increment();

				layout = LayoutUtil.create(plid);

				layout.setGroupId(groupId);
				layout.setPrivateLayout(privateLayout);
				layout.setLayoutId(layoutId);
			}

			layout.setCompanyId(user.getCompanyId());
			layout.setParentLayoutId(parentLayoutId);
			layout.setName(name);
			layout.setTitle(title);
			layout.setDescription(description);
			layout.setType(type);

			if (layout.isTypePortlet() &&
				Validator.isNotNull(layout.getTypeSettings()) &&
				!portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_REPLACE)) {

				mergePortlets(layout, typeSettings, portletsMergeMode);
			}
			else {
				layout.setTypeSettings(typeSettings);
			}

			layout.setHidden(hidden);
			layout.setFriendlyURL(friendlyURL);

			if (iconImage) {
				layout.setIconImage(iconImage);

				if (layout.isNew()) {
					long iconImageId = CounterLocalServiceUtil.increment();

					layout.setIconImageId(iconImageId);
				}
			}

			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setWapThemeId(wapThemeId);
			layout.setWapColorSchemeId(wapColorSchemeId);
			layout.setCss(css);
			layout.setPriority(priority);

			fixTypeSettings(layout);

			LayoutUtil.update(layout, false);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				ImageLocalServiceUtil.updateImage(
					layout.getIconImageId(), iconBytes);
			}

			context.setPlid(layout.getPlid());
			context.setOldPlid(oldPlid);

			newLayoutIdPlidMap.put(oldLayoutId, layout.getPlid());

			newLayoutIds.add(layoutId);

			newLayouts.add(layout);

			// Layout permissions

			if (importPermissions) {
				_permissionImporter.importLayoutPermissions(
					layoutCache, companyId, groupId, userId, layout, layoutEl,
					root, importUserPermissions);
			}

			_portletImporter.importPortletData(
				context, PortletKeys.LAYOUT_CONFIGURATION, null, layoutEl);
		}

		List<Element> portletEls = root.element("portlets").elements("portlet");

		// Delete portlet data

		if (deletePortletData) {
			if (_log.isDebugEnabled()) {
				if (portletEls.size() > 0) {
					_log.debug("Deleting portlet data");
				}
			}

			for (Element portletRefEl : portletEls) {
				String portletId = portletRefEl.attributeValue("portlet-id");
				long layoutId = GetterUtil.getLong(
					portletRefEl.attributeValue("layout-id"));
				long plid = newLayoutIdPlidMap.get(layoutId);

				context.setPlid(plid);

				_portletImporter.deletePortletData(context, portletId, plid);
			}
		}

		// Import portlets

		if (_log.isDebugEnabled()) {
			if (portletEls.size() > 0) {
				_log.debug("Importing portlets");
			}
		}

		for (Element portletRefEl : portletEls) {
			String portletPath = portletRefEl.attributeValue("path");
			String portletId = portletRefEl.attributeValue("portlet-id");
			long layoutId = GetterUtil.getLong(
				portletRefEl.attributeValue("layout-id"));
			long plid = newLayoutIdPlidMap.get(layoutId);
			long oldPlid = GetterUtil.getLong(
				portletRefEl.attributeValue("old-plid"));

			Layout layout = LayoutUtil.findByPrimaryKey(plid);

			context.setPlid(plid);
			context.setOldPlid(oldPlid);

			Element portletEl = null;

			try {
				Document portletDoc = SAXReaderUtil.read(
					context.getZipEntryAsString(portletPath));

				portletEl = portletDoc.getRootElement();
			}
			catch (DocumentException de) {
				throw new SystemException(de);
			}

			// The order of the import is important. You must always import
			// the portlet preferences first, then the portlet data, then
			// the portlet permissions. The import of the portlet data
			// assumes that portlet preferences already exist.

			// Portlet preferences

			_portletImporter.importPortletPreferences(
				context, layoutSet.getCompanyId(), layout.getGroupId(),
				layout, null, portletEl, importPortletSetup,
				importPortletArchivedSetups, importPortletUserPreferences,
				false);

			// Portlet data scope

			long scopeLayoutId = GetterUtil.getLong(
				portletEl.attributeValue("scope-layout-id"));

			context.setScopeLayoutId(scopeLayoutId);

			// Portlet data

			Element portletDataEl = portletEl.element("portlet-data");

			if (importPortletData && portletDataEl != null) {
				_portletImporter.importPortletData(
					context, portletId, plid, portletDataEl);
			}

			// Portlet permissions

			if (importPermissions) {
				_permissionImporter.importPortletPermissions(
					layoutCache, companyId, groupId, userId, layout, portletEl,
					portletId, importUserPermissions);
			}

			// Archived setups

			_portletImporter.importPortletPreferences(
				context, layoutSet.getCompanyId(), groupId, null, null,
				portletEl, importPortletSetup, importPortletArchivedSetups,
				importPortletUserPreferences, false);
		}

		// Delete missing layouts

		if (deleteMissingLayouts) {
			deleteMissingLayouts(
				groupId, privateLayout, newLayoutIds, previousLayouts);
		}

		// Page count

		LayoutSetLocalServiceUtil.updatePageCount(groupId, privateLayout);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}

		// Web content layout type

		for (Layout layout : newLayouts) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			String articleId = typeSettingsProperties.getProperty("article-id");

			if (Validator.isNotNull(articleId)) {
				Map<String, String> articleIds =
					(Map<String, String>)context.getNewPrimaryKeysMap(
						JournalArticle.class);

				typeSettingsProperties.setProperty(
					"article-id",
					MapUtil.getString(articleIds, articleId, articleId));

				LayoutUtil.update(layout, false);
			}
		}

		zipReader.close();
	}

	protected String[] appendPortletIds(
		String[] portletIds, String[] newPortletIds, String portletsMergeMode) {

		for (String portletId : newPortletIds) {
			if (ArrayUtil.contains(portletIds, portletId)) {
				continue;
			}

			if (portletsMergeMode.equals(
					PortletDataHandlerKeys.PORTLETS_MERGE_MODE_ADD_TO_BOTTOM)) {

				portletIds = ArrayUtil.append(portletIds, portletId);
			}
			else {
				portletIds = ArrayUtil.append(
					new String[] {portletId}, portletIds);
			}
		}

		return portletIds;
	}

	protected void deleteMissingLayouts(
			long groupId, boolean privateLayout, Set<Long> newLayoutIds,
			List<Layout> previousLayouts)
		throws PortalException, SystemException {

		// Layouts

		if (_log.isDebugEnabled()) {
			if (newLayoutIds.size() > 0) {
				_log.debug("Delete missing layouts");
			}
		}

		for (Layout layout : previousLayouts) {
			if (!newLayoutIds.contains(layout.getLayoutId())) {
				try {
					LayoutLocalServiceUtil.deleteLayout(layout, false);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}

		// Layout set

		LayoutSetLocalServiceUtil.updatePageCount(groupId, privateLayout);
	}

	protected void fixTypeSettings(Layout layout)
		throws PortalException, SystemException {

		if (!layout.isTypeURL()) {
			return;
		}

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		String url = GetterUtil.getString(typeSettings.getProperty("url"));

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		if (!url.startsWith(friendlyURLPrivateGroupPath) &&
			!url.startsWith(friendlyURLPrivateUserPath) &&
			!url.startsWith(friendlyURLPublicPath)) {

			return;
		}

		int x = url.indexOf(StringPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		int y = url.indexOf(StringPool.SLASH, x + 1);

		if (y == -1) {
			return;
		}

		String friendlyURL = url.substring(x, y);

		if (!friendlyURL.equals(LayoutExporter.SAME_GROUP_FRIENDLY_URL)) {
			return;
		}

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + layout.getGroup().getFriendlyURL() +
				url.substring(y));
	}

	protected AssetVocabulary getAssetVocabulary(
			PortletDataContext context, String vocabularyUuid,
			String vocabularyName, String userUuid,
			ServiceContext serviceContext)
		throws Exception {

		AssetVocabulary assetVocabulary = null;

		try {
			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				AssetVocabulary existingAssetVocabulary =
					AssetVocabularyUtil.fetchByUUID_G(
						vocabularyUuid, context.getGroupId());

				if (existingAssetVocabulary == null) {
					Map<Locale, String> titleMap =
						new HashMap<Locale, String>();

					titleMap.put(LocaleUtil.getDefault(), vocabularyName);

					assetVocabulary =
						AssetVocabularyLocalServiceUtil.addVocabulary(
							vocabularyUuid, context.getUserId(userUuid),
							titleMap, null, StringPool.BLANK, serviceContext);
				}
				else {
					assetVocabulary =
						AssetVocabularyLocalServiceUtil.updateVocabulary(
							existingAssetVocabulary.getVocabularyId(),
							existingAssetVocabulary.getTitleMap(),
							existingAssetVocabulary.getDescriptionMap(),
							existingAssetVocabulary.getSettings(),
							serviceContext);
				}
			}
			else {
				Map<Locale, String> titleMap = 	new HashMap<Locale, String>();

				titleMap.put(LocaleUtil.getDefault(), vocabularyName);

				assetVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
					null, context.getUserId(userUuid), titleMap, null,
					StringPool.BLANK, serviceContext);
			}
		}
		catch (DuplicateVocabularyException dve) {
			assetVocabulary =
				AssetVocabularyLocalServiceUtil.getGroupVocabulary(
					context.getGroupId(), vocabularyName);
		}

		return assetVocabulary;
	}

	protected void importCategories(PortletDataContext context)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/categories-hierarchy.xml");

			if (Validator.isNull(xml)) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> vocabularies = root.elements("vocabulary");

			for (Element vocabularyEl : vocabularies) {
				String vocabularyUuid = GetterUtil.getString(
					vocabularyEl.attributeValue("uuid"));
				String vocabularyName = GetterUtil.getString(
					vocabularyEl.attributeValue("name"));
				String userUuid = GetterUtil.getString(
					vocabularyEl.attributeValue("userUuid"));

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddCommunityPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(context.getGroupId());

				AssetVocabulary assetVocabulary = getAssetVocabulary(
					context, vocabularyUuid, vocabularyName, userUuid,
					serviceContext);

				List<Element> categories = vocabularyEl.elements("category");

				for (Element category : categories) {
					String categoryUuid = GetterUtil.getString(
						category.attributeValue("uuid"));
					String parentCategoryUuid = GetterUtil.getString(
						category.attributeValue("parentCategoryUuid"));
					String categoryName = GetterUtil.getString(
						category.attributeValue("name"));
					String[] properties = null;

					try {
						importCategory(
							context, categoryUuid, userUuid, parentCategoryUuid,
							categoryName, assetVocabulary.getVocabularyId(),
							properties, serviceContext);
					}
					catch (DuplicateCategoryException dce) {
					}
					catch (NoSuchCategoryException nsce) {
					}
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void importCategory(
			PortletDataContext context, String categoryUuid, String userUuid,
			String parentCategoryUuid, String categoryName, long vocabularyId,
			String[] properties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long parentCategoryId = 0;

		if (Validator.isNotNull(parentCategoryUuid)) {
			AssetCategory parentCategory = AssetCategoryUtil.findByUUID_G(
				parentCategoryUuid,context.getScopeGroupId());

			parentCategoryId = parentCategory.getCategoryId();
		}

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			AssetCategory existingAssetCategory =
				AssetCategoryUtil.fetchByUUID_G(
					categoryUuid, context.getGroupId());

			if (existingAssetCategory == null) {
				Map<Locale, String> titleMap =	new HashMap<Locale, String>();

				titleMap.put(LocaleUtil.getDefault(), categoryName);

				AssetCategoryLocalServiceUtil.addCategory(
					categoryUuid, context.getUserId(userUuid), parentCategoryId,
					titleMap, vocabularyId, properties, serviceContext);
			}
			else {
				AssetCategoryLocalServiceUtil.updateCategory(
					context.getUserId(userUuid),
					existingAssetCategory.getCategoryId(), parentCategoryId,
					existingAssetCategory.getTitleMap(), vocabularyId,
					properties, serviceContext);
			}
		}
		else {
			Map<Locale, String> titleMap =	new HashMap<Locale, String>();

			titleMap.put(LocaleUtil.getDefault(), categoryName);

			AssetCategoryLocalServiceUtil.addCategory(
				null, context.getUserId(userUuid), parentCategoryId, titleMap,
				vocabularyId, properties, serviceContext);
		}
	}

	protected String importTheme(LayoutSet layoutSet, InputStream themeZip)
		throws IOException {

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader == null) {
			_log.error("No theme loaders are deployed");

			return null;
		}

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(themeZip);

		String lookAndFeelXML = zipReader.getEntryAsString(
			"liferay-look-and-feel.xml");

		String themeId = String.valueOf(layoutSet.getGroupId());

		if (layoutSet.isPrivateLayout()) {
			themeId += "-private";
		}
		else {
			themeId += "-public";
		}

		if (PropsValues.THEME_LOADER_NEW_THEME_ID_ON_IMPORT) {
			Date now = new Date();

			themeId += "-" + Time.getShortTimestamp(now);
		}

		String themeName = themeId;

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$GROUP_ID$]", "[$THEME_ID$]", "[$THEME_NAME$]"
			},
			new String[] {
				String.valueOf(layoutSet.getGroupId()), themeId, themeName
			}
		);

		FileUtil.deltree(
			themeLoader.getFileStorage() + StringPool.SLASH + themeId);

		List<String> zipEntries = zipReader.getEntries();

		for (String zipEntry : zipEntries) {
			String key = zipEntry;

			if (key.contains(StringPool.SLASH)) {
				key = key.substring(key.lastIndexOf(StringPool.SLASH));
			}

			if (key.equals("liferay-look-and-feel.xml")) {
				FileUtil.write(
					themeLoader.getFileStorage() + StringPool.SLASH + themeId +
						StringPool.SLASH + key,
					lookAndFeelXML.getBytes());
			}
			else {
				InputStream is = zipReader.getEntryAsInputStream(zipEntry);

				FileUtil.write(
					themeLoader.getFileStorage() + StringPool.SLASH + themeId +
						StringPool.SLASH + key,
					is);
			}
		}

		themeLoader.loadThemes();

		MethodWrapper methodWrapper = new MethodWrapper(
			ThemeLoaderFactory.class.getName(), "loadThemes");

		Message message = new Message();
		message.setPayload(methodWrapper);

		ClusterLinkUtil.sendMulticastMessage(message, Priority.LEVEL5);

		themeId +=
			PortletConstants.WAR_SEPARATOR +
				themeLoader.getServletContextName();

		return PortalUtil.getJsSafePortletId(themeId);
	}

	protected void mergePortlets(
		Layout layout, String newTypeSettings, String portletsMergeMode) {

		try {
			UnicodeProperties previousProps =
				layout.getTypeSettingsProperties();
			LayoutTypePortlet previousLayoutType =
				(LayoutTypePortlet)layout.getLayoutType();
			List<String> previousColumns =
				previousLayoutType.getLayoutTemplate().getColumns();

			UnicodeProperties newProps = new UnicodeProperties(true);

			newProps.load(newTypeSettings);

			String layoutTemplateId = newProps.getProperty(
					LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

			LayoutTemplate newLayoutTemplate =
				LayoutTemplateLocalServiceUtil.getLayoutTemplate(
					layoutTemplateId, false, null);

			String[] lostPortletIds = new String[0];

			for (String columnId : newLayoutTemplate.getColumns()) {
				String columnValue =
					newProps.getProperty(columnId);

				String[] portletIds = StringUtil.split(columnValue);

				if (!previousColumns.contains(columnId)) {
					lostPortletIds = ArrayUtil.append(
						lostPortletIds, portletIds);
				}
				else {

					String[] previousPortletIds = StringUtil.split(
						previousProps.getProperty(columnId));

					portletIds = appendPortletIds(
						previousPortletIds, portletIds, portletsMergeMode);

					previousProps.setProperty(
						columnId, StringUtil.merge(portletIds));
				}
			}

			// Add portlets in non-existent column to the first column

			String columnId = previousColumns.get(0);

			String[] portletIds = StringUtil.split(
				previousProps.getProperty(columnId));

			appendPortletIds(portletIds, lostPortletIds, portletsMergeMode);

			previousProps.setProperty(
				columnId, StringUtil.merge(portletIds));

			layout.setTypeSettings(previousProps.toString());

		}
		catch (IOException e) {
			layout.setTypeSettings(newTypeSettings);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutImporter.class);

	private PermissionImporter _permissionImporter = new PermissionImporter();
	private PortletImporter _portletImporter = new PortletImporter();

}