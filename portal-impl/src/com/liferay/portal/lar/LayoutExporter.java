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

package com.liferay.portal.lar;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.util.ContentUtil;

import java.io.File;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class LayoutExporter {

	public static final String SAME_GROUP_FRIENDLY_URL =
		"/[$SAME_GROUP_FRIENDLY_URL$]";

	public static List<Portlet> getAlwaysExportablePortlets(long companyId)
		throws Exception {

		List<Portlet> portlets = PortletLocalServiceUtil.getPortlets(companyId);

		Iterator<Portlet> itr = portlets.iterator();

		while (itr.hasNext()) {
			Portlet portlet = itr.next();

			if (!portlet.isActive()) {
				itr.remove();

				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				(!portletDataHandler.isAlwaysExportable())) {

				itr.remove();
			}
		}

		return portlets;
	}

	public static void updateLastPublishDate(
			LayoutSet layoutSet, long lastPublishDate)
		throws Exception {

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		if (lastPublishDate <= 0) {
			settingsProperties.remove("last-publish-date");
		}
		else {
			settingsProperties.setProperty(
				"last-publish-date", String.valueOf(lastPublishDate));
		}

		LayoutSetLocalServiceUtil.updateSettings(
			layoutSet.getGroupId(), layoutSet.isPrivateLayout(),
			settingsProperties.toString());
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		File file = exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			return FileUtil.getBytes(file);
		}
		finally {
			file.delete();
		}
	}

	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
		boolean exportIgnoreLastPublishDate = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE);
		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.USER_PERMISSIONS);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Export categories " + exportCategories);
			_log.debug("Export permissions " + exportPermissions);
			_log.debug("Export user permissions " + exportUserPermissions);
			_log.debug(
				"Export portlet archived setups " +
					exportPortletArchivedSetups);
			_log.debug(
				"Export portlet user preferences " +
					exportPortletUserPreferences);
			_log.debug("Export theme " + exportTheme);
		}

		long lastPublishDate = System.currentTimeMillis();

		if (endDate != null) {
			lastPublishDate = endDate.getTime();
		}

		if (exportIgnoreLastPublishDate) {
			endDate = null;
			startDate = null;
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
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(), startDate,
			endDate, zipWriter);

		context.setPortetDataContextListener(
			new PortletDataContextListenerImpl(context));

		// Build compatibility

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Element headerElement = rootElement.addElement("header");

		headerElement.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		headerElement.addAttribute("export-date", Time.getRFC822());

		if (context.hasDateRange()) {
			headerElement.addAttribute(
				"start-date", String.valueOf(context.getStartDate()));
			headerElement.addAttribute(
				"end-date", String.valueOf(context.getEndDate()));
		}

		headerElement.addAttribute("type", "layout-set");
		headerElement.addAttribute("group-id", String.valueOf(groupId));
		headerElement.addAttribute(
			"private-layout", String.valueOf(privateLayout));

		if (exportTheme) {
			headerElement.addAttribute("theme-id", layoutSet.getThemeId());
			headerElement.addAttribute(
				"color-scheme-id", layoutSet.getColorSchemeId());
		}

		Element cssElement = headerElement.addElement("css");

		cssElement.addCDATA(layoutSet.getCss());

		// Layout configuration portlet

		Portlet layoutConfigurationPortlet =
			PortletLocalServiceUtil.getPortletById(
				context.getCompanyId(), PortletKeys.LAYOUT_CONFIGURATION);

		// Layouts

		Map<String, Object[]> portletIds =
			new LinkedHashMap<String, Object[]>();

		List<Layout> layouts = null;

		if ((layoutIds == null) || (layoutIds.length == 0)) {
			layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		}
		else {
			layouts = LayoutLocalServiceUtil.getLayouts(
				groupId, privateLayout, layoutIds);
		}

		Layout firstLayout = layouts.get(0);

		Group group = GroupLocalServiceUtil.getGroup(context.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		List<Portlet> portlets = getAlwaysExportablePortlets(companyId);

		for (Portlet portlet : portlets) {
			String portletId = portlet.getRootPortletId();

			if (!group.isStagedPortlet(portletId)) {
				continue;
			}

			if (portlet.isScopeable() && firstLayout.hasScopeGroup()) {
				String key = PortletPermissionUtil.getPrimaryKey(
					firstLayout.getPlid(), portletId);

				portletIds.put(
					key,
					new Object[] {
						portletId, firstLayout.getPlid(),
						firstLayout.getScopeGroup().getGroupId(),
						firstLayout.getUuid(), StringPool.BLANK
					}
				);
			}
			else {
				String key = PortletPermissionUtil.getPrimaryKey(
					0, portletId);

				if (portletIds.get(key) == null) {
					portletIds.put(
						key,
						new Object[] {
							portletId, firstLayout.getPlid(), groupId,
							StringPool.BLANK, StringPool.BLANK
						}
					);
				}
			}
		}

		Element layoutsElement = rootElement.addElement("layouts");

		for (Layout layout : layouts) {
			exportLayout(
				context, layoutConfigurationPortlet, layoutCache, portletIds,
				exportPermissions, exportUserPermissions, layout,
				layoutsElement);
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) {
			Element rolesElement = rootElement.addElement("roles");

			// Layout roles

			if (exportPermissions) {
				_permissionExporter.exportLayoutRoles(
					layoutCache, companyId, groupId, rolesElement);
			}
		}

		// Export portlets

		long previousScopeGroupId = context.getScopeGroupId();

		Element portletsElement = rootElement.addElement("portlets");

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			String portletId = (String)portletIdsEntry.getValue()[0];
			long plid = (Long)portletIdsEntry.getValue()[1];
			long scopeGroupId = (Long)portletIdsEntry.getValue()[2];
			String scopeLayoutUuid = (String)portletIdsEntry.getValue()[3];
			String scopeType = (String)portletIdsEntry.getValue()[4];

			Layout layout = LayoutUtil.findByPrimaryKey(plid);

			context.setPlid(layout.getPlid());
			context.setOldPlid(layout.getPlid());
			context.setScopeGroupId(scopeGroupId);
			context.setScopeLayoutUuid(scopeLayoutUuid);
			context.setScopeType(scopeType);

			boolean[] exportPortletControls = getExportPortletControls(
				companyId, portletId, context, parameterMap);

			_portletExporter.exportPortlet(
				context, layoutCache, portletId, layout, portletsElement,
				defaultUserId, exportPermissions, exportPortletArchivedSetups,
				exportPortletControls[0], exportPortletControls[1],
				exportPortletUserPreferences, exportUserPermissions);
		}

		context.setScopeGroupId(previousScopeGroupId);

		// Categories

		if (exportCategories) {
			exportCategories(context);
		}

		// Comments

		_portletExporter.exportComments(context, rootElement);

		// Locks

		_portletExporter.exportLocks(context, rootElement);

		// Portlet data permissions

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(context);
		}

		// Ratings

		_portletExporter.exportRatings(context, rootElement);

		// Tags

		_portletExporter.exportTags(context, rootElement);

		// Look and feel

		if (exportTheme && !context.isPerformDirectBinaryImport()) {
			exportTheme(layoutSet, zipWriter);
		}

		// Log

		if (_log.isInfoEnabled()) {
			if (stopWatch != null) {
				_log.info(
					"Exporting layouts takes " + stopWatch.getTime() + " ms");
			}
			else {
				_log.info("Exporting layouts is finished");
			}
		}

		// Zip

		context.addZipEntry("/manifest.xml", document.formattedString());

		try {
			return zipWriter.getFile();
		}
		finally {
			updateLastPublishDate(layoutSet, lastPublishDate);
		}
	}

	protected void exportCategories(PortletDataContext context)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("categories-hierarchy");

		Element vocabulariesElement = rootElement.addElement("vocabularies");

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyLocalServiceUtil.getGroupVocabularies(
				context.getGroupId());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			_portletExporter.exportVocabulary(
				context, vocabulariesElement, assetVocabulary);
		}

		Element categoriesElement = rootElement.addElement("categories");

		List<AssetCategory> assetCategories =
			AssetCategoryUtil.findByGroupId(context.getGroupId());

		for (AssetCategory assetCategory : assetCategories) {
			_portletExporter.exportCategory(
				context, vocabulariesElement, categoriesElement, assetCategory);
		}

		_portletExporter.exportCategories(context, rootElement);

		context.addZipEntry(
			context.getRootPath() + "/categories-hierarchy.xml",
			document.formattedString());
	}

	protected void exportLayout(
			PortletDataContext context, Portlet layoutConfigurationPortlet,
			LayoutCache layoutCache, Map<String, Object[]> portletIds,
			boolean exportPermissions, boolean exportUserPermissions,
			Layout layout, Element layoutsElement)
		throws Exception {

		String path = context.getLayoutPath(
			layout.getLayoutId()) + "/layout.xml";

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element layoutElement = layoutsElement.addElement("layout");

		layoutElement.addAttribute(
			"layout-uuid", layout.getUuid());
		layoutElement.addAttribute(
			"layout-id", String.valueOf(layout.getLayoutId()));

		long parentLayoutId = layout.getParentLayoutId();

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			Layout parentLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			if (parentLayout != null) {
				layoutElement.addAttribute(
					"parent-layout-uuid", parentLayout.getUuid());
			}
		}

		boolean deleteLayout = MapUtil.getBoolean(
			context.getParameterMap(), "delete_" + layout.getPlid());

		if (deleteLayout) {
			layoutElement.addAttribute("delete", String.valueOf(true));

			return;
		}

		context.setPlid(layout.getPlid());

		if (layout.isIconImage()) {
			Image image = ImageLocalServiceUtil.getImage(
				layout.getIconImageId());

			if (image != null) {
				String iconPath = getLayoutIconPath(context, layout, image);

				layoutElement.addElement("icon-image-path").addText(iconPath);

				context.addZipEntry(iconPath, image.getTextObj());
			}
		}

		_portletExporter.exportPortletData(
			context, layoutConfigurationPortlet, layout, null, layoutElement);

		// Layout permissions

		if (exportPermissions) {
			_permissionExporter.exportLayoutPermissions(
				context, layoutCache, context.getCompanyId(),
				context.getScopeGroupId(), layout, layoutElement,
				exportUserPermissions);
		}

		if (layout.isTypePortlet()) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			for (String portletId : layoutTypePortlet.getPortletIds()) {
				javax.portlet.PortletPreferences jxPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				String scopeLayoutUuid = GetterUtil.getString(
					jxPreferences.getValue("lfr-scope-layout-uuid", null));
				String scopeType = GetterUtil.getString(
					jxPreferences.getValue("lfr-scope-type", null));

				long scopeGroupId = context.getScopeGroupId();

				if (Validator.isNotNull(scopeType)) {
					Group scopeGroup = null;

					if (scopeType.equals(GroupConstants.GLOBAL)) {
						scopeGroup =
							GroupLocalServiceUtil.getCompanyGroup(
								layout.getScopeGroup().getCompanyId());
					}
					else {
						Layout scopeLayout =
							LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
								scopeLayoutUuid, context.getGroupId());

						scopeGroup = scopeLayout.getScopeGroup();

						if (scopeGroup != null) {
							scopeGroupId = scopeGroup.getGroupId();
						}
					}
				}

				String key = PortletPermissionUtil.getPrimaryKey(
					layout.getPlid(), portletId);

				portletIds.put(
					key,
					new Object[] {
						portletId, layout.getPlid(), scopeGroupId,
						scopeLayoutUuid, scopeType
					}
				);
			}
		}
		else if (layout.isTypeLinkToLayout()) {
			UnicodeProperties typeSettingsProperties =
				layout.getTypeSettingsProperties();

			long linkToLayoutId = GetterUtil.getLong(
				typeSettingsProperties.getProperty(
					"linkToLayoutId", StringPool.BLANK));

			if (linkToLayoutId > 0) {
				try {
					Layout linkedToLayout = LayoutUtil.findByG_P_L(
						context.getScopeGroupId(), layout.isPrivateLayout(),
						linkToLayoutId);

					exportLayout(
						context, layoutConfigurationPortlet, layoutCache,
						portletIds,	exportPermissions, exportUserPermissions,
						linkedToLayout, layoutsElement);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}

		fixTypeSettings(layout);

		layoutElement.addAttribute("path", path);

		context.addZipEntry(path, layout);
	}

	protected void exportTheme(LayoutSet layoutSet, ZipWriter zipWriter)
		throws Exception {

		Theme theme = layoutSet.getTheme();

		String lookAndFeelXML = ContentUtil.get(
			"com/liferay/portal/dependencies/liferay-look-and-feel.xml.tmpl");

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$TEMPLATE_EXTENSION$]", "[$VIRTUAL_PATH$]"
			},
			new String[] {
				theme.getTemplateExtension(), theme.getVirtualPath()
			}
		);

		String servletContextName = theme.getServletContextName();

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		if (servletContext == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return;
		}

		File themeZip = new File(zipWriter.getPath() + "/theme.zip");

		ZipWriter themeZipWriter = ZipWriterFactoryUtil.getZipWriter(themeZip);

		themeZipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		File cssPath = null;
		File imagesPath = null;
		File javaScriptPath = null;
		File templatesPath = null;

		if (!theme.isLoadFromServletContext()) {
			ThemeLoader themeLoader = ThemeLoaderFactory.getThemeLoader(
				servletContextName);

			if (themeLoader == null) {
				_log.error(
					servletContextName + " does not map to a theme loader");
			}
			else {
				String realPath =
					themeLoader.getFileStorage().getPath() + StringPool.SLASH +
						theme.getName();

				cssPath = new File(realPath + "/css");
				imagesPath = new File(realPath + "/images");
				javaScriptPath = new File(realPath + "/javascript");
				templatesPath = new File(realPath + "/templates");
			}
		}
		else {
			cssPath = new File(servletContext.getRealPath(theme.getCssPath()));
			imagesPath = new File(
				servletContext.getRealPath(theme.getImagesPath()));
			javaScriptPath = new File(
				servletContext.getRealPath(theme.getJavaScriptPath()));
			templatesPath = new File(
				servletContext.getRealPath(theme.getTemplatesPath()));
		}

		exportThemeFiles("css", cssPath, themeZipWriter);
		exportThemeFiles("images", imagesPath, themeZipWriter);
		exportThemeFiles("javascript", javaScriptPath, themeZipWriter);
		exportThemeFiles("templates", templatesPath, themeZipWriter);
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
		throws Exception {

		if ((dir == null) || (!dir.exists())) {
			return;
		}

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				exportThemeFiles(
					path + StringPool.SLASH + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
					path + StringPool.SLASH + file.getName(),
					FileUtil.getBytes(file));
			}
		}
	}

	protected void fixTypeSettings(Layout layout)
		throws Exception {

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

		int x = url.indexOf(CharPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		int y = url.indexOf(CharPool.SLASH, x + 1);

		if (y == -1) {
			return;
		}

		String friendlyURL = url.substring(x, y);
		String groupFriendlyURL = layout.getGroup().getFriendlyURL();

		if (!friendlyURL.equals(groupFriendlyURL)) {
			return;
		}

		typeSettings.setProperty(
			"url",
			url.substring(0, x) + SAME_GROUP_FRIENDLY_URL + url.substring(y));
	}

	protected boolean[] getExportPortletControls(
			long companyId, String portletId, PortletDataContext context,
			Map<String, String[]> parameterMap)
		throws Exception {

		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);

		if (_log.isDebugEnabled()) {
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug("Export portlet setup " + exportPortletSetup);
		}

		boolean exportCurPortletData = exportPortletData;
		boolean exportCurPortletSetup = exportPortletSetup;

		// If PORTLET_DATA_ALL is true, this means that staging has just been
		// activated and all data and setup must be exported. There is no
		// portlet export control to check in this case.

		if (exportPortletDataAll) {
			exportCurPortletData = true;
			exportCurPortletSetup = true;
		}
		else {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, portletId);

			if (portlet != null) {
				String portletDataHandlerClass =
					portlet.getPortletDataHandlerClass();

				// Checking if the portlet has a data handler, if it doesn't,
				// the default values are the ones set in PORTLET_DATA and
				// PORTLET_SETUP. If it has a data handler, iterate over each
				// portlet export control.

				if (portletDataHandlerClass != null) {
					String rootPortletId = PortletConstants.getRootPortletId(
						portletId);

					// PORTLET_DATA and the PORTLET_DATA for this specific
					// data handler must be true

					exportCurPortletData =
						exportPortletData &&
						MapUtil.getBoolean(
							parameterMap,
							PortletDataHandlerKeys.PORTLET_DATA +
								StringPool.UNDERLINE + rootPortletId);

					// PORTLET_DATA and the PORTLET_SETUP for this specific
					// data handler must be true

					exportCurPortletSetup =
						exportPortletData &&
						MapUtil.getBoolean(
							parameterMap,
							PortletDataHandlerKeys.PORTLET_SETUP +
								StringPool.UNDERLINE + rootPortletId);
				}
			}
		}

		return new boolean[] {exportCurPortletData, exportCurPortletSetup};
	}

	protected String getLayoutIconPath(
		PortletDataContext context, Layout layout, Image image) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getLayoutPath(layout.getLayoutId()));
		sb.append("/icons/");
		sb.append(image.getImageId());
		sb.append(StringPool.PERIOD);
		sb.append(image.getType());

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutExporter.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();
	private PortletExporter _portletExporter = new PortletExporter();

}