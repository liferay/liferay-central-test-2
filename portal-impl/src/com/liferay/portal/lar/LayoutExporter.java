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

package com.liferay.portal.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="LayoutExporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class LayoutExporter {

	public byte[] exportLayouts(
			long groupId, boolean privateLayout, long[] layoutIds,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.USER_PERMISSIONS);
		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
			_log.debug("Export user permissions " + exportUserPermissions);
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export portlet setup " + exportPortletSetup);
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

		ZipWriter zipWriter = new ZipWriter();

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet(), startDate, endDate,
			zipWriter);

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		// Build compatibility

		Document doc = DocumentHelper.createDocument();

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

		// Layouts

		Map<String, Long> portletIds = new LinkedHashMap<String, Long>();

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
			context.setPlid(layout.getPlid());

			Document layoutDoc = DocumentHelper.createDocument();

			Element layoutEl = layoutDoc.addElement("layout");

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

			Element permissionsEl = layoutEl.addElement("permissions");

			// Layout permissions

			if (exportPermissions) {
				exportLayoutPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl, exportUserPermissions);
			}

			if (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				for (String portletId : layoutTypePortlet.getPortletIds()) {
					if (!portletIds.containsKey(portletId)) {
						portletIds.put(portletId, new Long(layout.getPlid()));
					}
				}
			}

			String layoutPath = context.getLayoutPath(layout.getLayoutId()) +
				"/layout.xml";

			Element el = layoutsEl.addElement("layout");
			el.addAttribute("layout-id", String.valueOf(layout.getLayoutId()));
			el.addAttribute("path", layoutPath);

			try {
				context.addZipEntry(
					layoutPath, XMLFormatter.toString(layoutDoc));
			}
			catch (IOException ioe) {
			}
		}

		Element rolesEl = root.addElement("roles");

		// Layout roles

		if (exportPermissions) {
			exportLayoutRoles(layoutCache, companyId, groupId, rolesEl);
		}

		// Export Portlets

		Element portletsEl = root.addElement("portlets");

		for (Map.Entry<String, Long> portletIdsEntry : portletIds.entrySet()) {
			Layout layout = LayoutUtil.findByPrimaryKey(
				portletIdsEntry.getValue());

			context.setPlid(layout.getPlid());

			_portletExporter.exportPortlet(
				context, layoutCache, portletIdsEntry.getKey(), layout,
				portletsEl, defaultUserId, exportPortletData,
				exportPortletSetup, exportPortletArchivedSetups,
				exportPortletUserPreferences, exportPermissions,
				exportUserPermissions);
		}

		// Comments

		_portletExporter.exportComments(context, root);

		// Ratings

		_portletExporter.exportRatings(context, root);

		// Tags

		_portletExporter.exportTags(context, root);

		// Look and feel

		byte[] themeZip = null;

		try {
			if (exportTheme) {
				themeZip = exportTheme(layoutSet);

			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		// Log

		if (_log.isInfoEnabled()) {
			_log.info("Exporting layouts takes " + stopWatch.getTime() + " ms");
		}

		// Zip

		try {
			context.addZipEntry(
				"/manifest.xml", XMLFormatter.toString(doc));

			if (themeZip != null) {
				context.addZipEntry("/theme.zip", themeZip);
			}

			return zipWriter.finish();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = String.valueOf(layout.getPlid());

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
			permissionsEl, "location");

		_portletExporter.exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group");
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
			layoutCache, companyId, groupId, resourceName, "location", rolesEl);

		_portletExporter.exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected byte[] exportTheme(LayoutSet layoutSet) throws IOException {
		Theme theme = layoutSet.getTheme();

		ZipWriter zipWriter = new ZipWriter();

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

		zipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		String servletContextName = theme.getServletContextName();

		ServletContext ctx = VelocityContextPool.get(servletContextName);

		if (ctx == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return null;
		}

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
					themeLoader.getFileStorage().getPath() + "/" +
						theme.getName();

				cssPath = new File(realPath + "/css");
				imagesPath = new File(realPath + "/images");
				javaScriptPath = new File(realPath + "/javascript");
				templatesPath = new File(realPath + "/templates");
			}
		}
		else {
			cssPath = new File(ctx.getRealPath(theme.getCssPath()));
			imagesPath = new File(ctx.getRealPath(theme.getImagesPath()));
			javaScriptPath = new File(
				ctx.getRealPath(theme.getJavaScriptPath()));
			templatesPath = new File(ctx.getRealPath(theme.getTemplatesPath()));
		}

		exportThemeFiles("css", cssPath, zipWriter);
		exportThemeFiles("images", imagesPath, zipWriter);
		exportThemeFiles("javascript", javaScriptPath, zipWriter);
		exportThemeFiles("templates", templatesPath, zipWriter);

		return zipWriter.finish();
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
		throws IOException {

		if ((dir == null) || (!dir.exists())) {
			return;
		}

		File[] files = dir.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.isDirectory()) {
				exportThemeFiles(path + "/" + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
					path + "/" + file.getName(), FileUtil.getBytes(file));
			}
		}
	}

	protected String getLayoutIconPath(
		PortletDataContext context, Layout layout, Image image) {

		StringMaker sm = new StringMaker();

		sm.append(context.getLayoutPath(layout.getLayoutId()));
		sm.append("/icons/");
		sm.append(image.getImageId());
		sm.append(StringPool.PERIOD);
		sm.append(image.getType());

		return sm.toString();
	}

	private static Log _log = LogFactory.getLog(LayoutExporter.class);

	private PortletExporter _portletExporter = new PortletExporter();

}