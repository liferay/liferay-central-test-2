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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodeProperties;
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
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
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
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="LayoutExporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Karthik Sudarshan
 */
public class LayoutExporter {

	public static final String SAME_GROUP_FRIENDLY_URL =
		"/[$SAME_GROUP_FRIENDLY_URL$]";

	public static List<Portlet> getAlwaysExportablePortlets(long companyId)
		throws SystemException {

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

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		File file = exportLayoutsAsFile(
			groupId, privateLayout, layoutIds, parameterMap, startDate,
			endDate);

		try {
			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		finally {
			file.delete();
		}
	}

	public File exportLayoutsAsFile(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
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

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		// Build compatibility

		Document doc = SAXReaderUtil.createDocument();

		Element root = doc.addElement("root");

		Element header = root.addElement("header");

		header.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		header.addAttribute("export-date", Time.getRFC822());

		if (context.hasDateRange()) {
			header.addAttribute(
				"start-date", String.valueOf(context.getStartDate()));
			header.addAttribute(
				"end-date", String.valueOf(context.getEndDate()));
		}

		header.addAttribute("type", "layout-set");
		header.addAttribute("group-id", String.valueOf(groupId));
		header.addAttribute("private-layout", String.valueOf(privateLayout));
		header.addAttribute("theme-id", layoutSet.getThemeId());
		header.addAttribute("color-scheme-id", layoutSet.getColorSchemeId());

		// Layout Configuration Portlet

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

		Element layoutsEl = root.addElement("layouts");

		for (Layout layout : layouts) {
			boolean deleteLayout = MapUtil.getBoolean(
				parameterMap, "delete_" + layout.getPlid());

			if (deleteLayout) {
				Element el = layoutsEl.addElement("layout");

				el.addAttribute(
					"layout-id", String.valueOf(layout.getLayoutId()));
				el.addAttribute("delete", String.valueOf(true));

				continue;
			}

			fixTypeSettings(layout);

			context.setPlid(layout.getPlid());

			Document layoutDoc = SAXReaderUtil.createDocument();

			Element layoutEl = layoutDoc.addElement("layout");

			layoutEl.addAttribute("old-plid", String.valueOf(layout.getPlid()));
			layoutEl.addAttribute(
				"layout-id", String.valueOf(layout.getLayoutId()));
			layoutEl.addElement("parent-layout-id").addText(
				String.valueOf(layout.getParentLayoutId()));
			layoutEl.addElement("name").addCDATA(layout.getName());
			layoutEl.addElement("title").addCDATA(layout.getTitle());
			layoutEl.addElement("description").addText(layout.getDescription());
			layoutEl.addElement("type").addText(layout.getType());
			layoutEl.addElement("type-settings").addCDATA(
				layout.getTypeSettings());
			layoutEl.addElement("hidden").addText(
				String.valueOf(layout.getHidden()));
			layoutEl.addElement("friendly-url").addText(
				layout.getFriendlyURL());
			layoutEl.addElement("icon-image").addText(
				String.valueOf(layout.getIconImage()));

			if (layout.isIconImage()) {
				Image image = ImageLocalServiceUtil.getImage(
					layout.getIconImageId());

				if (image != null) {
					String iconPath = getLayoutIconPath(context, layout, image);

					layoutEl.addElement("icon-image-path").addText(
						iconPath);

					context.addZipEntry(iconPath, image.getTextObj());
				}
			}

			layoutEl.addElement("theme-id").addText(layout.getThemeId());
			layoutEl.addElement("color-scheme-id").addText(
				layout.getColorSchemeId());
			layoutEl.addElement("wap-theme-id").addText(layout.getWapThemeId());
			layoutEl.addElement("wap-color-scheme-id").addText(
				layout.getWapColorSchemeId());
			layoutEl.addElement("css").addCDATA(layout.getCss());
			layoutEl.addElement("priority").addText(
				String.valueOf(layout.getPriority()));

			// Layout permissions

			if (exportPermissions) {
				Element permissionsEl = layoutEl.addElement("permissions");

				String resourceName = Layout.class.getName();
				String resourcePrimKey = String.valueOf(layout.getPlid());

				if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
					exportLayoutPermissions_5(
						layoutCache, companyId, groupId, resourceName,
						resourcePrimKey, permissionsEl);
				}
				else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
					exportLayoutPermissions_6(
						layoutCache, companyId, groupId, resourceName,
						resourcePrimKey, permissionsEl);
				}
				else {
					exportLayoutPermissions_4(
						layoutCache, companyId, groupId, guestGroup,
						resourceName, resourcePrimKey, permissionsEl,
						exportUserPermissions);
				}
			}

			if (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				long scopeGroupId = groupId;

				for (String portletId : layoutTypePortlet.getPortletIds()) {
					javax.portlet.PortletPreferences jxPreferences =
						PortletPreferencesFactoryUtil.getLayoutPortletSetup(
							layout, portletId);

					long scopeLayoutId = GetterUtil.getLong(
						jxPreferences.getValue("lfr-scope-layout-id", null));

					if (scopeLayoutId != 0) {
						Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
							groupId, layout.isPrivateLayout(), scopeLayoutId);

						Group scopeGroup = scopeLayout.getScopeGroup();

						if (scopeGroup != null) {
							scopeGroupId = scopeGroup.getGroupId();
						}
					}

					String key = PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), portletId);

					portletIds.put(
						key,
						new Object[] {
							portletId, layout.getPlid(), scopeGroupId,
							scopeLayoutId
						}
					);
				}
			}

			List<Portlet> portlets = getAlwaysExportablePortlets(
				context.getCompanyId());

			for (Portlet portlet : portlets) {
				String portletId = portlet.getRootPortletId();

				if (portlet.isScopeable() && layout.hasScopeGroup()) {
					String key = PortletPermissionUtil.getPrimaryKey(
						layout.getPlid(), portletId);

					portletIds.put(
						key,
						new Object[] {
							portletId, layout.getPlid(),
							layout.getScopeGroup().getGroupId(),
							layout.getLayoutId()
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
								portletId, layout.getPlid(), groupId, 0L
							}
						);
					}
				}
			}

			String layoutPath = context.getLayoutPath(layout.getLayoutId()) +
				"/layout.xml";

			Element el = layoutsEl.addElement("layout");

			el.addAttribute("layout-id", String.valueOf(layout.getLayoutId()));
			el.addAttribute("path", layoutPath);

			_portletExporter.exportPortletData(
				context, layoutConfigurationPortlet, layout, null, layoutEl);

			try {
				context.addZipEntry(layoutPath, layoutDoc.formattedString());
			}
			catch (IOException ioe) {
			}
		}

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) {
			Element rolesEl = root.addElement("roles");

			// Layout roles

			if (exportPermissions) {
				exportLayoutRoles(layoutCache, companyId, groupId, rolesEl);
			}
		}

		// Export Portlets

		long previousScopeGroupId = context.getScopeGroupId();

		Element portletsEl = root.addElement("portlets");

		for (Map.Entry<String, Object[]> portletIdsEntry :
				portletIds.entrySet()) {

			String portletId = (String)portletIdsEntry.getValue()[0];
			long plid = (Long)portletIdsEntry.getValue()[1];
			long scopeGroupId = (Long)portletIdsEntry.getValue()[2];
			long scopeLayoutId = (Long)portletIdsEntry.getValue()[3];

			Layout layout = LayoutUtil.findByPrimaryKey(plid);

			context.setPlid(layout.getPlid());
			context.setOldPlid(layout.getPlid());
			context.setScopeGroupId(scopeGroupId);
			context.setScopeLayoutId(scopeLayoutId);

			boolean[] exportPortletControls = getExportPortletControls(
				context.getCompanyId(), portletId, context, parameterMap);

			_portletExporter.exportPortlet(
				context, layoutCache, portletId, layout, portletsEl,
				defaultUserId, exportPermissions, exportPortletArchivedSetups,
				exportPortletControls[0], exportPortletControls[1],
				exportPortletUserPreferences, exportUserPermissions);
		}

		context.setScopeGroupId(previousScopeGroupId);

		// Categories

		if (exportCategories) {
			exportCategories(context);
		}

		_portletExporter.exportCategories(context, root);

		// Comments

		_portletExporter.exportComments(context, root);

		// Portlet Data Permissions

		_portletExporter.exportPortletDataPermissions(context);

		// Ratings

		_portletExporter.exportRatings(context, root);

		// Tags

		_portletExporter.exportTags(context, root);

		// Look and feel

		try {
			if (exportTheme) {
				exportTheme(layoutSet, zipWriter);
			}

			// Log

			if (_log.isInfoEnabled()) {
				_log.info(
					"Exporting layouts takes " + stopWatch.getTime() + " ms");
			}

			// Zip

			context.addZipEntry("/manifest.xml", doc.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return zipWriter.getFile();
	}

	protected void exportCategories(PortletDataContext context)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("categories-hierarchy");

			List<AssetVocabulary> assetVocabularies =
				AssetVocabularyLocalServiceUtil.getGroupVocabularies(
					context.getGroupId());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				Element vocabularyEl = root.addElement("vocabulary");

				long vocabularyId = assetVocabulary.getVocabularyId();

				vocabularyEl.addAttribute("uuid", assetVocabulary.getUuid());
				vocabularyEl.addAttribute("id", String.valueOf(vocabularyId));
				vocabularyEl.addAttribute("name", assetVocabulary.getName());
				vocabularyEl.addAttribute(
					"userUuid", assetVocabulary.getUserUuid());

				List<AssetCategory> assetCategories =
					AssetCategoryLocalServiceUtil.getVocabularyCategories(
						assetVocabulary.getVocabularyId());

				assetCategories = ListUtil.copy(assetCategories);

				orderCategories(
					assetCategories, vocabularyEl,
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
			}

			context.addZipEntry(
				context.getRootPath() + "/categories-hierarchy.xml",
				doc.formattedString());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportLayoutPermissions_4(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, String resourceName, String resourcePrimKey,
			Element permissionsEl, boolean exportUserPermissions)
		throws PortalException, SystemException {

		_portletExporter.exportGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions");

		if (groupId != guestGroup.getGroupId()) {
			_portletExporter.exportGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions");
		}

		if (exportUserPermissions) {
			_portletExporter.exportUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl);
		}

		_portletExporter.exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization");

		_portletExporter.exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group");
	}

	protected void exportLayoutPermissions_5(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element permissionsEl)
		throws PortalException, SystemException {

		boolean portletActions = false;

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		_portletExporter.exportPermissions_5(
			layoutCache, groupId, resourceName, resource.getResourceId(),
			permissionsEl);
	}

	protected void exportLayoutPermissions_6(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element permissionsEl)
		throws PortalException, SystemException {

		boolean portletActions = false;

		_portletExporter.exportPermissions_6(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl, portletActions);
	}

	protected void exportLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		_portletExporter.exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		_portletExporter.exportUserRoles(
		layoutCache, companyId, groupId, resourceName, rolesEl);

		_portletExporter.exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		_portletExporter.exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected void exportTheme(LayoutSet layoutSet, ZipWriter zipWriter)
		throws IOException, SystemException {

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

		ServletContext servletContext = VelocityContextPool.get(
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
		throws IOException {

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
		throws PortalException, SystemException {

		if (!layout.getType().equals(LayoutConstants.TYPE_URL)) {
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
		throws SystemException {

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

	protected void orderCategories(
			List<AssetCategory> assetCategories, Element parentEl,
			long parentCategoryId)
		throws PortalException, SystemException {

		List<AssetCategory> parentCategories = new ArrayList<AssetCategory>();

		Iterator<AssetCategory> itr = assetCategories.iterator();

		while (itr.hasNext()) {
			AssetCategory assetCategory = itr.next();

			if (assetCategory.getParentCategoryId() == parentCategoryId) {
				Element categoryEl = parentEl.addElement("category");

				categoryEl.addAttribute("uuid", assetCategory.getUuid());
				categoryEl.addAttribute("name", assetCategory.getName());
				categoryEl.addAttribute(
					"userUuid", assetCategory.getUserUuid());

				if (parentCategoryId > 0) {
					AssetCategory parentCategory =
						AssetCategoryLocalServiceUtil.getCategory(
							parentCategoryId);

					categoryEl.addAttribute(
						"parentCategoryUuid", parentCategory.getUuid());
				}

				parentCategories.add(assetCategory);

				itr.remove();
			}
		}

		for (AssetCategory parentCategory : parentCategories) {
			orderCategories(
				assetCategories, parentEl, parentCategory.getCategoryId());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutExporter.class);

	private PortletExporter _portletExporter = new PortletExporter();

}