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

package com.liferay.portal.service.impl;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortalException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.comm.CommLink;
import com.liferay.portal.kernel.job.PublishToLiveEvent;
import com.liferay.portal.kernel.job.SchedulingRequest;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.lar.AlwaysCurrentUserIdStrategy;
import com.liferay.portal.lar.CurrentUserIdStrategy;
import com.liferay.portal.lar.PortletDataContextImpl;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.base.LayoutLocalServiceBaseImpl;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.util.JSONUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.Normalizer;
import com.liferay.util.Time;
import com.liferay.util.xml.XMLFormatter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="LayoutLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class LayoutLocalServiceImpl extends LayoutLocalServiceBaseImpl {

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), description, type, hidden,
			friendlyURL);
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL,
			DLFolderImpl.DEFAULT_PARENT_FOLDER_ID);
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, String name, String title, String description,
			String type, boolean hidden, String friendlyURL, long dlFolderId)
		throws PortalException, SystemException {

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();

		Locale defaultLocale = LocaleUtil.getDefault();

		localeNamesMap.put(defaultLocale, name);

		return addLayout(
			userId, groupId, privateLayout, parentLayoutId, localeNamesMap,
			new HashMap<Locale, String>(), description, type, hidden,
			friendlyURL, dlFolderId);
	}

	public Layout addLayout(
			long userId, long groupId, boolean privateLayout,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL, long dlFolderId)
		throws PortalException, SystemException {

		// Layout

		User user = userPersistence.findByPrimaryKey(userId);
		long layoutId = getNextLayoutId(groupId, privateLayout);
		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		friendlyURL = getFriendlyURL(layoutId, friendlyURL);
		int priority = getNextPriority(groupId, privateLayout, parentLayoutId);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, type, hidden,
			friendlyURL);

		long plid = counterLocalService.increment();

		Layout layout = layoutPersistence.create(plid);

		layout.setGroupId(groupId);
		layout.setCompanyId(user.getCompanyId());
		layout.setPrivateLayout(privateLayout);
		layout.setLayoutId(layoutId);
		layout.setParentLayoutId(parentLayoutId);
		layout.setDescription(description);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);
		layout.setPriority(priority);
		layout.setDlFolderId(dlFolderId);

		setLocalizedAttributes(layout, localeNamesMap, localeTitlesMap);

		layoutPersistence.update(layout, false);

		// Resources

		resourceLocalService.addResources(
			user.getCompanyId(), groupId, user.getUserId(),
			Layout.class.getName(), layout.getPlid(), false, true, true);

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);

		return layout;
	}

	public void deleteLayout(long plid)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		deleteLayout(layout, true);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		deleteLayout(layout, true);
	}

	public void deleteLayout(Layout layout, boolean updateLayoutSet)
		throws PortalException, SystemException {

		// Child layouts

		List<Layout> childLayouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		for (Layout childLayout : childLayouts) {
			deleteLayout(childLayout, updateLayoutSet);
		}

		// Portlet preferences

		portletPreferencesLocalService.deletePortletPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		// Tasks

		tasksProposalLocalService.deleteProposal(
			Layout.class.getName(), String.valueOf(layout.getPlid()));

		// Ratings

		ratingsStatsLocalService.deleteStats(
			Layout.class.getName(), layout.getPlid());

		// Message boards

		mbMessageLocalService.deleteDiscussionMessages(
			Layout.class.getName(), layout.getPlid());

		// Journal content searches

		journalContentSearchLocalService.deleteLayoutContentSearches(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getLayoutId());

		// Icon

		ImageLocalUtil.deleteImage(layout.getIconImageId());

		// Resources

		String primKey =
			layout.getPlid() + PortletConstants.LAYOUT_SEPARATOR + "%";

		List<Resource> resources = resourceFinder.findByC_P(
			layout.getCompanyId(), primKey);

		for (Resource resource : resources) {
			resourceLocalService.deleteResource(resource);
		}

		resourceLocalService.deleteResource(
			layout.getCompanyId(), Layout.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, layout.getPlid());

		// Layout

		layoutPersistence.remove(layout.getPlid());

		// Layout set

		if (updateLayoutSet) {
			layoutSetLocalService.updatePageCount(
				layout.getGroupId(), layout.isPrivateLayout());
		}
	}

	public void deleteLayouts(long groupId, boolean privateLayout)
		throws PortalException, SystemException {

		// Layouts

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		for (Layout layout : layouts) {
			try {
				deleteLayout(layout, false);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	public void deleteScheduledPublishToLiveEvent(
			long liveGroupId, String jobName)
		throws SystemException {

		try {
			SchedulingRequest sr = new SchedulingRequest(
				getSchedulerGroupName(liveGroupId), jobName,
				SchedulingRequest.UNREGISTER_TYPE);

			MessageBusUtil.sendMessage(
				DestinationNames.SCHEDULER, JSONUtil.serialize(sr));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public byte[] exportLayouts(
			long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		return exportLayouts(
			groupId, privateLayout, null, parameterMap, startDate, endDate);
	}

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

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();
		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		ZipWriter zipWriter = new ZipWriter();

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet(), startDate, endDate,
			zipWriter);

		Group guestGroup = groupLocalService.getGroup(
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
			layouts = getLayouts(groupId, privateLayout);
		}
		else {
			layouts = getLayouts(groupId, privateLayout, layoutIds);
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
				Image image = ImageLocalUtil.getImage(layout.getIconImageId());

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
			Layout layout = layoutPersistence.findByPrimaryKey(
				portletIdsEntry.getValue().longValue());

			context.setPlid(layout.getPlid());

			exportPortlet(
				context, layoutCache, portletIdsEntry.getKey(), layout,
				portletsEl, defaultUserId, exportPortletData,
				exportPortletSetup, exportPortletArchivedSetups,
				exportPortletUserPreferences, exportPermissions,
				exportUserPermissions);
		}

		// Comments

		exportComments(context, root);

		// Ratings

		exportRatings(context, root);

		// Tags

		exportTags(context, root);

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

	public byte[] exportPortletInfo(
			long plid, String portletId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException {

		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.USER_PERMISSIONS);
		boolean exportPortletData = true;
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);

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
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (!layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
			throw new LayoutImportException(
				"Layout type " + layout.getType() + " is not valid");
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layoutTypePortlet.hasPortletId(portletId)) {
			throw new LayoutImportException(
				"The specified layout does not have portlet " + portletId);
		}

		long companyId = layout.getCompanyId();
		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		ZipWriter zipWriter = new ZipWriter();

		PortletDataContext context = new PortletDataContextImpl(
			companyId, layout.getGroupId(), parameterMap, new HashSet(),
			startDate, endDate, zipWriter);

		context.setPlid(plid);

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

		header.addAttribute("type", "portlet");
		header.addAttribute("group-id", String.valueOf(layout.getGroupId()));
		header.addAttribute(
			"private-layout", String.valueOf(layout.isPrivateLayout()));
		header.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));

		// Portlet

		exportPortlet(
			context, layoutCache, portletId, layout, root, defaultUserId,
			exportPortletData, exportPortletSetup, exportPortletArchivedSetups,
			exportPortletUserPreferences, exportPermissions,
			exportUserPermissions);

		// Comments

		exportComments(context, root);

		// Ratings

		exportRatings(context, root);

		// Tags

		exportTags(context, root);

		// Log

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet took " + stopWatch.getTime() + " ms");
		}

		// Zip

		try {
			context.addZipEntry(
				"/manifest.xml", XMLFormatter.toString(doc));

			return zipWriter.finish();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public long getDefaultPlid(long groupId) throws SystemException {
		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByGroupId(
				groupId, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws SystemException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout, 0, 1);

			if (layouts.size() > 0) {
				Layout layout = layouts.get(0);

				return layout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public long getDefaultPlid(
			long groupId, boolean privateLayout, String portletId)
		throws SystemException {

		if (groupId > 0) {
			List<Layout> layouts = layoutPersistence.findByG_P(
				groupId, privateLayout);

			for (Layout layout : layouts) {
				if (layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
					LayoutTypePortlet layoutTypePortlet =
						(LayoutTypePortlet)layout.getLayoutType();

					if (layoutTypePortlet.hasPortletId(portletId)) {
						return layout.getPlid();
					}
				}
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	public Layout getDLFolderLayout(long dlFolderId)
		throws PortalException, SystemException {

		return layoutPersistence.findByDLFolderId(dlFolderId);
	}

	public Layout getFriendlyURLLayout(
			long groupId, boolean privateLayout, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchLayoutException();
		}

		friendlyURL = getFriendlyURL(friendlyURL);

		Layout layout = layoutPersistence.fetchByG_P_F(
			groupId, privateLayout, friendlyURL);

		if ((layout == null) &&
			(friendlyURL.startsWith(StringPool.SLASH))) {

			long layoutId = GetterUtil.getLong(friendlyURL.substring(1));

			layout = layoutPersistence.fetchByG_P_L(
				groupId, privateLayout, layoutId);
		}

		if (layout == null) {
			throw new NoSuchLayoutException();
		}

		return layout;
	}

	public Layout getLayout(long plid)
		throws PortalException, SystemException {

		return layoutPersistence.findByPrimaryKey(plid);
	}

	public Layout getLayout(long groupId, boolean privateLayout, long layoutId)
		throws PortalException, SystemException {

		return layoutPersistence.findByG_P_L(groupId, privateLayout, layoutId);
	}

	public Layout getLayoutByIconImageId(long iconImageId)
		throws PortalException, SystemException {

		return layoutPersistence.findByIconImageId(iconImageId);
	}

	public List<Layout> getLayouts(long groupId, boolean privateLayout)
		throws SystemException {

		return layoutPersistence.findByG_P(groupId, privateLayout);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		return layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, String type)
		throws SystemException {

		return layoutPersistence.findByG_P_T(groupId, privateLayout, type);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long parentLayoutId, int start,
			int end)
		throws SystemException {

		return layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end);
	}

	public List<Layout> getLayouts(
			long groupId, boolean privateLayout, long[] layoutIds)
		throws PortalException, SystemException {

		List<Layout> layouts = new ArrayList<Layout>();

		for (long layoutId : layoutIds) {
			Layout layout = getLayout(groupId, privateLayout, layoutId);

			layouts.add(layout);
		}

		return layouts;
	}

	public LayoutReference[] getLayouts(
			long companyId, String portletId, String prefsKey,
			String prefsValue)
		throws SystemException {

		List<LayoutReference> layoutReferences = layoutFinder.findByC_P_P(
			companyId, portletId, prefsKey, prefsValue);

		return layoutReferences.toArray(
			new LayoutReference[layoutReferences.size()]);
	}

	public List<Layout> getNullFriendlyURLLayouts() throws SystemException {
		return layoutFinder.findByNullFriendlyURL();
	}

	public String getScheduledPublishToLiveEventsJSON(long groupId)
		throws SystemException {

		try {
			JSONArray events = new JSONArray();

			SchedulingRequest sr = new SchedulingRequest(
				getSchedulerGroupName(groupId),
				SchedulingRequest.RETRIEVE_TYPE);

			String message = MessageBusUtil.sendSynchronizedMessage(
				DestinationNames.SCHEDULER, JSONUtil.serialize(sr));

			if (message != null) {
				JSONObject jsonObj = new JSONObject(message);
				String requests = jsonObj.getString("requests");

				Collection<SchedulingRequest> scheduledRequests =
					(Collection<SchedulingRequest>)JSONUtil.deserialize(
						requests);

				for (SchedulingRequest schedulingRequest : scheduledRequests) {
					JSONObject jsonObject = new JSONObject();
					JSONUtil.put(
						jsonObject, "description",
						schedulingRequest.getDescription());
					JSONUtil.put(
						jsonObject, "jobName", schedulingRequest.getJobName());

					events.put(jsonObject);
				}
			}

			return events.toString();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		try {
			importLayouts(
				userId, groupId, privateLayout, parameterMap,
				new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, byte[] bytes)
		throws PortalException, SystemException {

		importLayouts(
			userId, groupId, privateLayout, parameterMap,
			new ByteArrayInputStream(bytes));
	}

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		boolean addAsNewLayouts = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.ADD_AS_NEW_LAYOUTS);
		boolean deleteMissingLayouts = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.booleanValue());
		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
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
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet data " + deletePortletData);
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

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();

		User user = userPersistence.findByPrimaryKey(userId);

		UserIdStrategy strategy = getUserIdStrategy(user, userIdStrategy);

		ZipReader zipReader = new ZipReader(is);

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet(), strategy,
			zipReader);

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupImpl.GUEST);

		// Zip

		Element root = null;
		byte[] themeZip = null;

		// Manifest

		String xml = context.getZipEntryAsString("/manifest.xml");

		try {
			Document doc = DocumentUtil.readDocumentFromXML(xml);

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

		long importGroupId = GetterUtil.getLong(
			header.attributeValue("group-id"));

		context.setImportGroupId(importGroupId);

		// Look and feel

		if (importTheme) {
			themeZip = context.getZipEntryAsByteArray("theme.zip");
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

		layoutSetLocalService.updateLookAndFeel(
			groupId, privateLayout, themeId, colorSchemeId, StringPool.BLANK,
			wapTheme);

		// Read comments, ratings, and tags to make them available to the data
		// handlers through the context

		readComments(context, root);
		readRatings(context, root);
		readTags(context, root);

		// Layouts

		Set<Long> newLayoutIds = new HashSet<Long>();

		Map <Long, Long> newLayoutIdPlidMap =
			context.getNewPrimaryKeysMap(Layout.class);

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

			String layoutPath = layoutRefEl.attributeValue("path");

			Element layoutEl = null;

			try {
				Document layoutDoc = DocumentUtil.readDocumentFromXML(
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

			if (addAsNewLayouts) {
				layoutId = getNextLayoutId(groupId, privateLayout);
				friendlyURL = StringPool.SLASH + layoutId;
			}
			else {
				layout = layoutPersistence.fetchByG_P_L(
					groupId, privateLayout, layoutId);
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
				long plid = counterLocalService.increment();

				layout = layoutPersistence.create(plid);

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
			layout.setTypeSettings(typeSettings);
			layout.setHidden(hidden);
			layout.setFriendlyURL(friendlyURL);

			if (iconImage) {
				layout.setIconImage(iconImage);

				if (layout.isNew()) {
					long iconImageId = counterLocalService.increment();

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

			layoutPersistence.update(layout, false);

			if ((iconBytes != null) && (iconBytes.length > 0)) {
				ImageLocalUtil.updateImage(layout.getIconImageId(), iconBytes);
			}

			context.setPlid(layout.getPlid());

			newLayoutIdPlidMap.put(oldLayoutId, layout.getPlid());

			newLayoutIds.add(layoutId);

			Element permissionsEl = layoutEl.element("permissions");

			// Layout permissions

			if (importPermissions) {
				importLayoutPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl, importUserPermissions);
			}
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

				deletePortletData(context, portletId, plid);
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

			Layout layout = layoutPersistence.findByPrimaryKey(plid);

			context.setPlid(plid);

			Element portletEl = null;

			try {
				Document portletDoc = DocumentUtil.readDocumentFromXML(
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

			importPortletPreferences(
				context, layoutSet.getCompanyId(), layout.getGroupId(),
				layout.getPlid(), null, portletEl, importPortletSetup,
				importPortletArchivedSetups, importPortletUserPreferences);

			// Portlet data

			Element portletDataEl = portletEl.element("portlet-data");

			if (importPortletData && portletDataEl != null) {
				importPortletData(
					context, portletId, plid, portletDataEl);
			}

			// Portlet permissions

			Element permissionsEl = portletEl.element("permissions");

			if (importPermissions) {
				importPortletPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl, importUserPermissions);
			}

			// Archived setups

			importPortletPreferences(
				context, layoutSet.getCompanyId(), groupId, 0, null, portletEl,
				importPortletSetup, importPortletArchivedSetups,
				importPortletUserPreferences);

			// Portlet roles

			Element rolesEl = portletEl.element("roles");

			if (importPermissions) {
				importPortletRoles(layoutCache, companyId, groupId, portletEl);
				importPortletRoles(
					layoutCache, companyId, groupId, portletId, rolesEl);
			}
		}

		Element rolesEl = root.element("roles");

		// Layout roles

		if (importPermissions) {
			importLayoutRoles(layoutCache, companyId, groupId, rolesEl);
		}

		// Delete missing layouts

		if (deleteMissingLayouts) {
			deleteMissingLayouts(groupId, privateLayout, newLayoutIds);
		}

		// Page count

		layoutSetLocalService.updatePageCount(groupId, privateLayout);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}
	}

	public void importPortletInfo(
			long userId, long plid, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		try {
			importPortletInfo(
				userId, plid, portletId, parameterMap,
				new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
	}

	public void importPortletInfo(
			long userId, long plid, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		long companyId = layout.getCompanyId();

		User user = userPersistence.findByPrimaryKey(userId);

		UserIdStrategy strategy = getUserIdStrategy(user, userIdStrategy);

		ZipReader zipReader = new ZipReader(is);

		PortletDataContext context = new PortletDataContextImpl(
			companyId, layout.getGroupId(), parameterMap, new HashSet(),
			strategy, zipReader);

		context.setPlid(plid);

		// Zip

		Element root = null;

		// Manifest

		String xml = context.getZipEntryAsString("/manifest.xml");

		try {
			Document doc = DocumentUtil.readDocumentFromXML(xml);

			root = doc.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException(
				"Cannot locate a manifest in this LAR file.");
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

		String type = header.attributeValue("type");

		if (!type.equals("portlet")) {
			throw new LARTypeException(
				"Invalid type of LAR file (" + type + ")");
		}

		// Portlet compatibility

		String rootPortletId = header.attributeValue("root-portlet-id");

		if (!PortletConstants.getRootPortletId(portletId).equals(
				rootPortletId)) {

			throw new PortletIdException("Invalid portlet id " + rootPortletId);
		}

		// Import GroupId

		long importGroupId = GetterUtil.getLong(
			header.attributeValue("group-id"));

		context.setImportGroupId(importGroupId);

		// Read comments, ratings, and tags to make them available to the data
		// handlers through the context

		readComments(context, root);
		readRatings(context, root);
		readTags(context, root);

		// Delete portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting portlet data");
		}

		if (deletePortletData) {
			deletePortletData(context, portletId, plid);
		}

		Element portletRefEl = root.element("portlet");
		Element portletEl = null;

		try {
			Document portletDoc = DocumentUtil.readDocumentFromXML(
				context.getZipEntryAsString(
					portletRefEl.attributeValue("path")));

			portletEl = portletDoc.getRootElement();
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}

		// Portlet preferences

		importPortletPreferences(
			context, layout.getCompanyId(), layout.getGroupId(), plid,
			portletId, portletEl, importPortletSetup,
			importPortletArchivedSetups, importUserPreferences);

		// Portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Importing portlet data");
		}

		if (importPortletData) {
			importPortletData(
				context, portletId, plid, portletEl.element("portlet-data"));
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Importing portlet data takes " + stopWatch.getTime() + " ms");
		}
	}

	public void schedulePublishToLiveEvent(
			long userId, long stagingGroupId, long liveGroupId,
			boolean privateLayout, Map<String, String[]> parameterMap,
			String cronText, String scope, Map<Long, Boolean> layoutIdMap,
			Date startDate, Date endDate, String description)
		throws SystemException {

		try {
			PublishToLiveEvent event = new PublishToLiveEvent(
				userId, stagingGroupId, liveGroupId, privateLayout,
				parameterMap, scope, layoutIdMap);

			SchedulingRequest sr = new SchedulingRequest(
				cronText, DestinationNames.STAGING,
				getSchedulerGroupName(liveGroupId),
				JSONUtil.serialize(event), startDate, endDate,
				SchedulingRequest.REGISTER_TYPE, description);

			MessageBusUtil.sendMessage(
				DestinationNames.SCHEDULER, JSONUtil.serialize(sr));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void setLayouts(
			long groupId, boolean privateLayout, long parentLayoutId,
			long[] layoutIds)
		throws PortalException, SystemException {

		if (layoutIds == null) {
			return;
		}

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			if (layoutIds.length < 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}

			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutIds[0]);

			if (!layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_TYPE);
			}

			if (layout.isHidden()) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_HIDDEN);
			}
		}

		Set<Long> layoutIdsSet = new LinkedHashSet<Long>();

		for (int i = 0; i < layoutIds.length; i++) {
			layoutIdsSet.add(layoutIds[i]);
		}

		Set<Long> newLayoutIdsSet = new HashSet<Long>();

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			if (!layoutIdsSet.contains(layout.getLayoutId())) {
				deleteLayout(layout, true);
			}
			else {
				newLayoutIdsSet.add(layout.getLayoutId());
			}
		}

		int priority = 0;

		for (long layoutId : layoutIdsSet) {
			Layout layout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, layoutId);

			layout.setPriority(priority++);

			layoutPersistence.update(layout, false);
		}

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	public Layout updateFriendlyURL(long plid, String friendlyURL)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		friendlyURL = getFriendlyURL(layout.getLayoutId(), friendlyURL);

		validateFriendlyURL(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			friendlyURL);

		layout.setFriendlyURL(friendlyURL);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		return updateLayout(
			groupId, privateLayout, layoutId, parentLayoutId, localeNamesMap,
			localeTitlesMap, description, type, hidden, friendlyURL, null,
			null);
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, Map<Locale, String> localeNamesMap,
			Map<Locale, String> localeTitlesMap, String description,
			String type, boolean hidden, String friendlyURL, Boolean iconImage,
			byte[] iconBytes)
		throws PortalException, SystemException {

		// Layout

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);
		friendlyURL = getFriendlyURL(layoutId, friendlyURL);

		validate(
			groupId, privateLayout, layoutId, parentLayoutId, type, hidden,
			friendlyURL);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			layout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);
		layout.setDescription(description);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		setLocalizedAttributes(layout, localeNamesMap, localeTitlesMap);

		if (iconImage != null) {
			layout.setIconImage(iconImage.booleanValue());

			if (iconImage.booleanValue()) {
				long iconImageId = layout.getIconImageId();

				if (iconImageId <= 0) {
					iconImageId = counterLocalService.increment();

					layout.setIconImageId(iconImageId);
				}
			}
		}

		layoutPersistence.update(layout, false);

		// Icon

		if (iconImage != null) {
			if (!iconImage.booleanValue()) {
				ImageLocalUtil.deleteImage(layout.getIconImageId());
			}
			else if ((iconBytes != null) && (iconBytes.length > 0)) {
				ImageLocalUtil.updateImage(layout.getIconImageId(), iconBytes);
			}
		}

		try {
			if (layout.getDlFolderId() > 0) {
				DLFolder folder = dlFolderLocalService.getFolder(
					layout.getDlFolderId());

				String name = layout.getName(LocaleUtil.getDefault());

				if (!name.equals(folder.getName())) {
					dlFolderLocalService.updateFolder(
						folder.getFolderId(), folder.getParentFolderId(), name,
						folder.getDescription());
				}
			}
		}
		catch (DuplicateFolderNameException dfne) {
			if (_log.isDebugEnabled()) {
				_log.debug(dfne);
			}
		}
		catch (NoSuchFolderException nsfe) {
		}

		return layout;
	}

	public Layout updateLayout(
			long groupId, boolean privateLayout, long layoutId,
			String typeSettings)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		layout.setTypeSettings(typeSettings);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateLookAndFeel(
			long groupId, boolean privateLayout, long layoutId, String themeId,
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (wapTheme) {
			layout.setWapThemeId(themeId);
			layout.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setCss(css);
		}

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateName(long plid, String name, String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updateName(layout, name, languageId);
	}

	public Layout updateName(
			long groupId, boolean privateLayout, long layoutId, String name,
			String languageId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updateName(layout, name, languageId);
	}

	public Layout updateName(Layout layout, String name, String languageId)
		throws PortalException, SystemException {

		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		layoutPersistence.update(layout, false);

		try {
			if (layout.getDlFolderId() > 0) {
				DLFolder folder = dlFolderLocalService.getFolder(
					layout.getDlFolderId());

				dlFolderLocalService.updateFolder(
					folder.getFolderId(), folder.getParentFolderId(),
					layout.getName(LocaleUtil.getDefault()),
					folder.getDescription());
			}
		}
		catch (NoSuchFolderException nsfe) {
		}

		return layout;
	}

	public Layout updateParentLayoutId(long plid, long parentPlid)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;

		if (parentPlid > 0) {
			try {
				Layout parentLayout = layoutPersistence.findByPrimaryKey(
					parentPlid);

				parentLayoutId = parentLayout.getLayoutId();
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		parentLayoutId = getParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

		validateParentLayoutId(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			parentLayoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			int priority = getNextPriority(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			layout.setPriority(priority);
		}

		layout.setParentLayoutId(parentLayoutId);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		parentLayoutId = getParentLayoutId(
			groupId, privateLayout, parentLayoutId);

		validateParentLayoutId(
			groupId, privateLayout, layoutId, parentLayoutId);

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {
			layout.setPriority(
				getNextPriority(groupId, privateLayout, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);

		layoutPersistence.update(layout, false);

		return layout;
	}

	public Layout updatePriority(long plid, int priority)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByPrimaryKey(plid);

		return updatePriority(layout, priority);
	}

	public Layout updatePriority(
			long groupId, boolean privateLayout, long layoutId, int priority)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		return updatePriority(layout, priority);
	}

	public Layout updatePriority(Layout layout, int priority)
		throws PortalException, SystemException {

		if (layout.getPriority() == priority) {
			return layout;
		}

		boolean lessThan = false;

		if (layout.getPriority() < priority) {
			lessThan = true;
		}

		layout.setPriority(priority);

		layoutPersistence.update(layout, false);

		priority = 0;

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId());

		Collections.sort(
			layouts, new LayoutPriorityComparator(layout, lessThan));

		for (Layout curLayout : layouts) {
			curLayout.setPriority(priority++);

			layoutPersistence.update(curLayout, false);

			if (curLayout.equals(layout)) {
				layout = curLayout;
			}
		}

		return layout;
	}

	protected void deleteMissingLayouts(
			long groupId, boolean privateLayout, Set<Long> newLayoutIds)
		throws PortalException, SystemException {

		// Layouts

		if (_log.isDebugEnabled()) {
			if (newLayoutIds.size() > 0) {
				_log.debug("Delete missing layouts");
			}
		}

		List<Layout> layouts = layoutPersistence.findByG_P(
			groupId, privateLayout);

		for (Layout layout : layouts) {
			if (!newLayoutIds.contains(layout.getLayoutId())) {
				try {
					deleteLayout(layout, false);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}

		// Layout set

		layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	protected void deletePortletData(
			PortletDataContext context, String portletId, long plid)
		throws PortalException, SystemException {

		try {
			PortletPreferences portletPreferences =
				portletPreferencesPersistence.findByO_O_P_P(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
					portletId);

			String preferences = deletePortletData(
				context, portletId, portletPreferences);

			if (preferences != null) {
				portletPreferences.setPreferences(preferences);

				portletPreferencesPersistence.update(
					portletPreferences, false);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
		}
	}

	protected String deletePortletData(
			PortletDataContext context, String portletId,
			PortletPreferences portletPreferences)
		throws PortalException, SystemException {

		Portlet portlet = portletLocalService.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return null;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " + portletId +
						" because the portlet does not have a " +
							"PortletDataHandler");
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting data for " + portletId);
		}

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				portletPreferences.getPreferences());

		prefsImpl = (PortletPreferencesImpl)portletDataHandler.deleteData(
			context, portletId, prefsImpl);

		if (prefsImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(prefsImpl);
	}

	protected void exportComments(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("comments");

			Map<String, List> commentsMap = context.getComments();

			for (Map.Entry<String, List> entry : commentsMap.entrySet()) {
				String[] comment = entry.getKey().split(StringPool.POUND);

				String path = getCommentPath(context, comment[0], comment[1]);

				Element asset = root.addElement("asset");
				asset.addAttribute("path", path);
				asset.addAttribute("class-name", comment[0]);
				asset.addAttribute("class-pk", comment[1]);

				List<MBMessage> messages = entry.getValue();

				for (MBMessage message : messages) {
					path = getCommentPath(
						context, comment[0], comment[1], message);

					context.addZipEntry(path, message);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/comments.xml",
				XMLFormatter.toString(doc));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected Element exportGroupPermissions(
			long companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		List<Permission> permissions =
			permissionLocalService.getGroupPermissions(
				groupId, companyId, resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

		List<String> actions = ResourceActionsUtil.getActions(permissions);

		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i);

			Element actionKeyEl = el.addElement("action-key");

			actionKeyEl.addText(action);
		}

		return el;
	}

	protected void exportGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		List<Role> roles = layoutCache.getGroupRoles(groupId);

		Element groupEl = exportRoles(
			companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), parentEl, entityName + "-roles", roles);

		if (groupEl.elements().isEmpty()) {
			parentEl.remove(groupEl);
		}
	}

	protected void exportInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element parentEl, String entityName)
		throws PortalException, SystemException {

		Element entityPermissionsEl = DocumentHelper.createElement(
			entityName + "-permissions");

		Map<String, Long> entityMap = layoutCache.getEntityMap(
			companyId, entityName);

		Iterator<Map.Entry<String, Long>> itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Long> entry = itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = entry.getValue();

			Element entityEl = exportGroupPermissions(
				companyId, entityGroupId, resourceName, resourcePrimKey,
				entityPermissionsEl, entityName + "-actions");

			if (entityEl.elements().isEmpty()) {
				entityPermissionsEl.remove(entityEl);
			}
			else {
				entityEl.addAttribute("name", name);
			}
		}

		if (!entityPermissionsEl.elements().isEmpty()) {
			parentEl.add(entityPermissionsEl);
		}
	}

	protected void exportInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = DocumentHelper.createElement(
			entityName + "-roles");

		Map<String, Long> entityMap = layoutCache.getEntityMap(
			companyId, entityName);

		Iterator<Map.Entry<String, Long>> itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Long> entry = itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = entry.getValue();

			List<Role> entityRoles = layoutCache.getGroupRoles(entityGroupId);

			Element entityEl = exportRoles(
				companyId, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), entityRolesEl, entityName,
				entityRoles);

			if (entityEl.elements().isEmpty()) {
				entityRolesEl.remove(entityEl);
			}
			else {
				entityEl.addAttribute("name", name);
			}
		}

		if (!entityRolesEl.elements().isEmpty()) {
			parentEl.add(entityRolesEl);
		}
	}

	protected void exportLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = String.valueOf(layout.getPlid());

		exportGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions");

		if (groupId != guestGroup.getGroupId()) {
			exportGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions");
		}

		if (exportUserPermissions) {
			exportUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl);
		}

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "location");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group");
	}

	protected void exportLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		exportUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location", rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected void exportPortlet(
			PortletDataContext context, LayoutCache layoutCache,
			String portletId, Layout layout, Element parentEl,
			long defaultUserId, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletArchivedSetups,
			boolean exportPortletUserPreferences, boolean exportPermissions,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		long companyId = context.getCompanyId();
		long groupId = context.getGroupId();

		Document portletDoc = DocumentHelper.createDocument();

		Element portletEl = portletDoc.addElement("portlet");

		portletEl.addAttribute("portlet-id", portletId);
		portletEl.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));

		// Data

		javax.portlet.PortletPreferences jxPrefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, portletId, StringPool.BLANK);

		if (exportPortletData) {
			exportPortletData(context, portletId, jxPrefs, portletEl);
		}

		// Portlet preferences

		if (exportPortletSetup) {
			exportPortletPreferences(
				context, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout, portletId,
				portletEl);

			exportPortletPreferences(
				context, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
				layout, portletId, portletEl);

			exportPortletPreferences(
				context, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, false,
				layout, portletId, portletEl);
		}

		// Portlet preferences

		if (exportPortletUserPreferences) {
			exportPortletPreferences(
				context, defaultUserId, PortletKeys.PREFS_OWNER_TYPE_USER,
				true, layout, portletId, portletEl);

			try {
				PortletPreferences groupPortletPreferences =
					portletPreferencesLocalService.getPortletPreferences(
						groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
						PortletKeys.PREFS_PLID_SHARED, portletId);

				exportPortletPreference(
					context, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
					groupPortletPreferences, portletId,
					PortletKeys.PREFS_PLID_SHARED, portletEl);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			List<PortletItem> portletItems =
				portletItemLocalService.getPortletItems(
					groupId, rootPortletId, PortletPreferences.class.getName());

			for (PortletItem portletItem: portletItems) {
				long ownerId = portletItem.getPortletItemId();
				int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;

				exportPortletPreferences(
					context, ownerId, ownerType, false, null,
					portletItem.getPortletId(), portletEl);
			}
		}

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupImpl.GUEST);

		// Permissions

		if (exportPermissions) {
			Element permissionsEl = portletEl.addElement("permissions");

			exportPortletPermissions(
				layoutCache, companyId, groupId, guestGroup, layout.getPlid(),
				portletId, permissionsEl, exportUserPermissions);

			Element rolesEl = portletEl.addElement("roles");

			exportPortletRoles(
				layoutCache, companyId, groupId, portletId, rolesEl);
		}

		// Zip

		String portletPath = context.getPortletPath(portletId) + "/portlet.xml";

		Element el = parentEl.addElement("portlet");
		el.addAttribute("portlet-id", portletId);
		el.addAttribute(
			"layout-id", String.valueOf(layout.getLayoutId()));
		el.addAttribute("path", portletPath);

		try {
			context.addZipEntry(portletPath, XMLFormatter.toString(portletDoc));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportPortletData(
			PortletDataContext context, String portletId,
			javax.portlet.PortletPreferences portletPreferences,
			Element parentEl)
		throws PortalException, SystemException {

		Portlet portlet = portletLocalService.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		Map parameterMap = context.getParameterMap();

		boolean exportData = false;

		if (MapUtil.getBoolean(
				parameterMap,
				PortletDataHandlerKeys.PORTLET_DATA + "_" +
					portlet.getRootPortletId()) ||
			MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL)) {

			exportData = true;
		}

		if (!exportData) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because it was not selected by the user");
			}

			return;
		}

		String data = portletDataHandler.exportData(
			context, portletId, portletPreferences);

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		String portletDataPath = getPortletDataPath(context, portletId);

		parentEl.addElement("portlet-data").addAttribute(
			"path", portletDataPath);

		context.addZipEntry(portletDataPath, data);
	}

	protected void exportPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, long plid, String portletId,
			Element permissionsEl, boolean exportUserPermissions)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(portletId);
		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			plid, portletId);

		exportGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions");

		if (groupId != guestGroup.getGroupId()) {
			exportGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions");
		}

		if (exportUserPermissions) {
			exportUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl);
		}

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "location");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group");
	}

	protected void exportPortletPreference(
			PortletDataContext context, long ownerId, int ownerType,
			boolean defaultUser, PortletPreferences portletPreferences,
			String portletId, long plid, Element parentEl)
		throws PortalException, SystemException {

		try {
			Document prefsDoc = DocumentUtil.readDocumentFromXML(
				portletPreferences.getPreferences());

			Element root = prefsDoc.getRootElement();

			root.addAttribute("owner-id", String.valueOf(ownerId));
			root.addAttribute("owner-type", String.valueOf(ownerType));
			root.addAttribute("default-user", String.valueOf(defaultUser));
			root.addAttribute("plid", String.valueOf(plid));
			root.addAttribute("portlet-id", portletId);

			if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
				PortletItem portletItem =
					portletItemLocalService.getPortletItem(ownerId);

				User user = userLocalService.getUserById(
					portletItem.getUserId());

				root.addAttribute("archive-user-uuid", user.getUuid());
				root.addAttribute("archive-name", portletItem.getName());
			}

			String path = getPortletPreferencesPath(
				context, portletId, ownerId, ownerType, plid);

			parentEl.addElement("portlet-preference").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				context.addZipEntry(
					path, XMLFormatter.toString(prefsDoc));
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportPortletPreferences(
			PortletDataContext context, long ownerId, int ownerType,
			boolean defaultUser, Layout layout, String portletId,
			Element parentEl)
		throws PortalException, SystemException {

		PortletPreferences portletPreferences = null;

		long plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;

		if (layout != null) {
			plid = layout.getPlid();
		}

		if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED)) {

			plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		}

		try {
			portletPreferences =
				portletPreferencesLocalService.getPortletPreferences(
					ownerId, ownerType, plid, portletId);

			LayoutTypePortlet layoutTypePortlet = null;

			if (layout != null) {
				layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
			}

			if ((layoutTypePortlet == null) ||
				(layoutTypePortlet.hasPortletId(portletId))) {

				exportPortletPreference(
					context, ownerId, ownerType, defaultUser,
					portletPreferences, portletId, plid, parentEl);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
		}
	}

	protected void exportPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(
			portletId);

		Element portletEl = rolesEl.addElement("portlet");

		portletEl.addAttribute("portlet-id", portletId);

		exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			portletEl);

		exportUserRoles(
			layoutCache, companyId, groupId, resourceName, portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location",
			portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			portletEl);

		if (portletEl.elements().isEmpty()) {
			rolesEl.remove(portletEl);
		}
	}

	protected Element exportRoles(
			long companyId, String resourceName, int scope,
			String resourcePrimKey, Element parentEl, String elName,
			List<Role> roles)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		Map<String, List<String>> resourceRoles =
			roleLocalService.getResourceRoles(
				companyId, resourceName, scope, resourcePrimKey);

		Iterator<Map.Entry<String, List<String>>> itr =
			resourceRoles.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, List<String>> entry = itr.next();

			String roleName = entry.getKey().toString();

			if (hasRole(roles, roleName)) {
				Element roleEl = el.addElement("role");

				roleEl.addAttribute("name", roleName);

				List<String> actions = entry.getValue();

				for (int i = 0; i < actions.size(); i++) {
					String action = actions.get(i);

					Element actionKeyEl = roleEl.addElement("action-key");

					actionKeyEl.addText(action);
					actionKeyEl.addAttribute("scope", String.valueOf(scope));
				}
			}
		}

		return el;
	}

	protected void exportRatings(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("ratings");

			Map<String, List> ratingsEntriesMap = context.getRatingsEntries();

			for (Map.Entry<String, List> entry : ratingsEntriesMap.entrySet()) {
				String[] ratingsEntry = entry.getKey().split(StringPool.POUND);

				String ratingPath = getRatingPath(
					context, ratingsEntry[0], ratingsEntry[1]);

				Element asset = root.addElement("asset");
				asset.addAttribute("path", ratingPath);
				asset.addAttribute("class-name", ratingsEntry[0]);
				asset.addAttribute("class-pk", ratingsEntry[1]);

				List<RatingsEntry> ratingsEntries = entry.getValue();

				for (RatingsEntry rating : ratingsEntries) {
					ratingPath = getRatingPath(
						context, ratingsEntry[0], ratingsEntry[1], rating);

					context.addZipEntry(ratingPath, rating);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/ratings.xml",
				XMLFormatter.toString(doc));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportTags(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("tags");

			Map<String, String[]> tagsEntries = context.getTagsEntries();

			for (Map.Entry<String, String[]> entry : tagsEntries.entrySet()) {
				String[] tagsEntry = entry.getKey().split(StringPool.POUND);

				Element asset = root.addElement("asset");
				asset.addAttribute("class-name", tagsEntry[0]);
				asset.addAttribute("class-pk", tagsEntry[1]);
				asset.addAttribute(
					"entries", StringUtil.merge(entry.getValue(), ","));
			}

			context.addZipEntry(
				context.getRootPath() + "/tags.xml",
				XMLFormatter.toString(doc));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected byte[] exportTheme(LayoutSet layoutSet)
		throws IOException, PortalException, SystemException {

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

	protected void exportUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Element userPermissionsEl = DocumentHelper.createElement(
			"user-permissions");

		List<User> users = layoutCache.getGroupUsers(groupId);

		for (User user : users) {
			String emailAddress = user.getEmailAddress();

			Element userActionsEl =
				DocumentHelper.createElement("user-actions");

			List<Permission> permissions =
				permissionLocalService.getUserPermissions(
					user.getUserId(), companyId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

			List<String> actions = ResourceActionsUtil.getActions(permissions);

			for (String action : actions) {
				Element actionKeyEl = userActionsEl.addElement("action-key");

				actionKeyEl.addText(action);
			}

			if (!userActionsEl.elements().isEmpty()) {
				userActionsEl.addAttribute("email-address", emailAddress);
				userPermissionsEl.add(userActionsEl);
			}
		}

		if (!userPermissionsEl.elements().isEmpty()) {
			parentEl.add(userPermissionsEl);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Export user permissions for {" + resourceName + ", " +
					resourcePrimKey + "} with " + users.size() +
						" users takes " + stopWatch.getTime() + " ms");
		}
	}

	protected void exportUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = DocumentHelper.createElement("user-roles");

		List<User> users = layoutCache.getGroupUsers(groupId);

		for (User user : users) {
			long userId = user.getUserId();
			String emailAddress = user.getEmailAddress();

			List<Role> userRoles = layoutCache.getUserRoles(userId);

			Element userEl = exportRoles(
				companyId, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), userRolesEl, "user", userRoles);

			if (userEl.elements().isEmpty()) {
				userRolesEl.remove(userEl);
			}
			else {
				userEl.addAttribute("email-address", emailAddress);
			}
		}

		if (!userRolesEl.elements().isEmpty()) {
			parentEl.add(userRolesEl);
		}
	}

	protected void fixTypeSettings(Layout layout) {
		if (layout.getType().equals(LayoutConstants.TYPE_URL)) {
			Properties typeSettings = layout.getTypeSettingsProperties();

			String url = GetterUtil.getString(typeSettings.getProperty("url"));

			String friendlyURLPrivateGroupPath =
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
			String friendlyURLPrivateUserPath =
				PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
			String friendlyURLPublicPath =
				PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

			if (url.startsWith(friendlyURLPrivateGroupPath) ||
				url.startsWith(friendlyURLPrivateUserPath) ||
				url.startsWith(friendlyURLPublicPath)) {

				int x = url.indexOf(StringPool.SLASH, 1);

				if (x > 0) {
					int y = url.indexOf(StringPool.SLASH, x + 1);

					if (y > x) {
						String fixedUrl = url.substring(0, x) +

						layout.getGroup().getFriendlyURL() +

						url.substring(y);

						typeSettings.setProperty("url", fixedUrl);
					}
				}
			}
		}
	}

	protected List<String> getActions(Element el) {
		List<String> actions = new ArrayList<String>();

		Iterator<Element> itr = el.elements("action-key").iterator();

		while (itr.hasNext()) {
			Element actionEl = itr.next();

			actions.add(actionEl.getText());
		}

		return actions;
	}

	protected String getCommentPath(
			PortletDataContext context, String className, String classPK) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/comments/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);

		return sm.toString();
	}

	protected String getCommentPath(
			PortletDataContext context, String className, String classPK,
			MBMessage message) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/comments/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(message.getMessageId());
		sm.append(".xml");

		return sm.toString();
	}

	protected String getFriendlyURL(String friendlyURL) {
		friendlyURL = GetterUtil.getString(friendlyURL);

		return Normalizer.normalizeToAscii(friendlyURL.trim().toLowerCase());
	}

	protected String getFriendlyURL(long layoutId, String friendlyURL) {
		friendlyURL = getFriendlyURL(friendlyURL);

		if (Validator.isNull(friendlyURL)) {
			friendlyURL = StringPool.SLASH + layoutId;
		}

		return friendlyURL;
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

	protected long getNextLayoutId(long groupId, boolean privateLayout)
		throws SystemException {

		long layoutId = 0;

		List<Layout> layouts = layoutPersistence.findByG_P(
			groupId, privateLayout);

		for (Layout curLayout : layouts) {
			long curLayoutId = curLayout.getLayoutId();

			if (curLayoutId > layoutId) {
				layoutId = curLayoutId;
			}
		}

		return ++layoutId;
	}

	protected int getNextPriority(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws SystemException {

		List<Layout> layouts = layoutPersistence.findByG_P_P(
			groupId, privateLayout, parentLayoutId);

		if (layouts.size() == 0) {
			return 0;
		}

		Layout layout = layouts.get(layouts.size() - 1);

		return layout.getPriority() + 1;
	}

	protected long getParentLayoutId(
			long groupId, boolean privateLayout, long parentLayoutId)
		throws PortalException, SystemException {

		if (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			// Ensure parent layout exists

			try {
				layoutPersistence.findByG_P_L(
					groupId, privateLayout, parentLayoutId);
			}
			catch (NoSuchLayoutException nsfe) {
				parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
			}
		}

		return parentLayoutId;
	}

	protected String getPortletDataPath(
			PortletDataContext context, String portletId) {

		return context.getPortletPath(portletId) + "/portlet-data.xml";
	}

	protected String getPortletPreferencesPath(
			PortletDataContext context, String portletId, long ownerId,
			int ownerType, long plid) {
		StringMaker preferencePath = new StringMaker();
		preferencePath.append(context.getPortletPath(portletId));
		preferencePath.append("/preferences/");

		switch(ownerType) {
			case PortletKeys.PREFS_OWNER_TYPE_COMPANY:
				preferencePath.append("company/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_GROUP:
				preferencePath.append("group/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_LAYOUT:
				preferencePath.append("layout/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_USER:
				preferencePath.append("user/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_ARCHIVED:
				preferencePath.append("archived/");
				break;
		}

		preferencePath.append(ownerId);
		preferencePath.append(CharPool.FORWARD_SLASH);
		preferencePath.append(plid);
		preferencePath.append(CharPool.FORWARD_SLASH);
		preferencePath.append("portlet-preferences.xml");

		return preferencePath.toString();
	}

	protected String getRatingPath(
			PortletDataContext context, String className, String classPK) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/ratings/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);

		return sm.toString();
	}

	protected String getRatingPath(
			PortletDataContext context, String className, String classPK,
			RatingsEntry rating) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/ratings/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(rating.getEntryId());
		sm.append(".xml");

		return sm.toString();
	}

	protected String getSchedulerGroupName(long liveGroupId) {
		return liveGroupId + StringPool.AT + DestinationNames.STAGING;
	}

	protected UserIdStrategy getUserIdStrategy(
		User user, String userIdStrategy) {

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	protected boolean hasRole(List<Role> roles, String roleName) {
		if ((roles == null) || (roles.size() == 0)) {
			return false;
		}

		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);

			if (role.getName().equals(roleName)) {
				return true;
			}
		}

		return false;
	}

	protected void importGroupPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			String elName, boolean portletActions)
		throws PortalException, SystemException {

		Element actionEl = parentEl.element(elName);

		if (actionEl == null) {
			return;
		}

		List<String> actions = getActions(actionEl);

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName,
			ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
			portletActions);

		permissionLocalService.setGroupPermissions(
			groupId, actions.toArray(new String[actions.size()]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName,
			Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		importRolePermissions(
			layoutCache, companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesEl, true);
	}

	protected void importInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element permissionsEl, String entityName,
			boolean portletActions)
		throws PortalException, SystemException {

		Element entityPermissionsEl = permissionsEl.element(
			entityName + "-permissions");

		if (entityPermissionsEl == null) {
			return;
		}

		List<Element> actionsEls = entityPermissionsEl.elements(
			entityName + "-actions");

		for (int i = 0; i < actionsEls.size(); i++) {
			Element actionEl = actionsEls.get(i);

			String name = actionEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited permissions for entity " + entityName +
						" with name " + name);
			}
			else {
				Element parentEl = DocumentHelper.createElement("parent");

				parentEl.add(actionEl.createCopy());

				importGroupPermissions(
					layoutCache, companyId, entityGroupId, resourceName,
					resourcePrimKey, parentEl, entityName + "-actions",
					portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		List<Element> entityEls = entityRolesEl.elements(entityName);

		for (int i = 0; i < entityEls.size(); i++) {
			Element entityEl = entityEls.get(i);

			String name = entityEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					entityEl, false);
			}
		}
	}

	protected void importLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = String.valueOf(layout.getPlid());

		importGroupPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl, "community-actions", false);

		if (groupId != guestGroup.getGroupId()) {
			importGroupPermissions(
				layoutCache, companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions", false);
		}

		if (importUserPermissions) {
			importUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);
		}

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "location", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group", false);
	}

	protected void importLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		importGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		importUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location", rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
	}

	protected void importPortletData(
			PortletDataContext context, Layout layout, Element parentEl)
		throws PortalException, SystemException {

		Iterator<Element> itr = parentEl.elements("portlet-data").iterator();

		while (itr.hasNext()) {
			Element el = itr.next();

			String portletId = el.attributeValue("portlet-id");

			importPortletData(context, portletId, layout.getPlid(), parentEl);
		}
	}

	protected void importPortletData(
			PortletDataContext context, String portletId, long plid,
			Element portletDataRefEl)
		throws PortalException, SystemException {

		try {
			PortletPreferences portletPreferences =
				portletPreferencesPersistence.findByO_O_P_P(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
					portletId);

			String preferences = importPortletData(
				context, portletId, portletPreferences, portletDataRefEl);

			if (preferences != null) {
				portletPreferences.setPreferences(preferences);

				portletPreferencesPersistence.update(
					portletPreferences, false);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
		}
	}

	protected String importPortletData(
			PortletDataContext context, String portletId,
			PortletPreferences portletPreferences, Element portletDataRefEl)
		throws PortalException, SystemException {

		Portlet portlet = portletLocalService.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return null;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet data for " + portletId +
						" because the portlet does not have a " +
							"PortletDataHandler");
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Importing data for " + portletId);
		}

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				portletPreferences.getPreferences());

		String portletData = context.getZipEntryAsString(
			portletDataRefEl.attributeValue("path"));

		prefsImpl = (PortletPreferencesImpl)portletDataHandler.importData(
			context, portletId, prefsImpl, portletData);

		if (prefsImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(prefsImpl);
	}

	protected void importPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl,
			boolean importUserPermissions)
		throws PortalException, SystemException {

		Iterator<Element> itr = permissionsEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);
			String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			Portlet portlet = portletLocalService.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet permissions for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletEl, "community-actions", true);

				if (groupId != guestGroup.getGroupId()) {
					importGroupPermissions(
						layoutCache, companyId, guestGroup.getGroupId(),
						resourceName, resourcePrimKey, portletEl,
						"guest-actions", true);
				}

				if (importUserPermissions) {
					importUserPermissions(
						layoutCache, companyId, groupId, resourceName,
						resourcePrimKey, portletEl, true);
				}

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "organization", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "location", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "user-group", true);
			}
		}
	}

	protected void importPortletPreferences(
			PortletDataContext context, long companyId, long groupId, long plid,
			String portletId, Element parentEl, boolean importPortletSetup,
			boolean importPortletArchivedSetups, boolean importUserPreferences)
		throws PortalException, SystemException {

		long defaultUserId = userLocalService.getDefaultUserId(companyId);

		List<Element> prefsEls = parentEl.elements("portlet-preference");

		for (Element prefEl : prefsEls) {
			String path = prefEl.attributeValue("path");

			if (context.isPathNotProcessed(path)) {

				Element el = null;
				String preferences = null;

				try {
					preferences = context.getZipEntryAsString(path);

					Document prefsDoc = DocumentUtil.readDocumentFromXML(
						preferences);

					el = prefsDoc.getRootElement();
				}
				catch (DocumentException de) {
					throw new SystemException(de);
				}

				long ownerId = GetterUtil.getLong(el.attributeValue("owner-id"));
				int ownerType = GetterUtil.getInteger(
					el.attributeValue("owner-type"));

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
					continue;
				}

				if (((ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
					 (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) &&
					!importPortletSetup) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) &&
					!importPortletArchivedSetups) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) &&
					(ownerId != PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
					!importUserPreferences) {

					continue;
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
					plid = PortletKeys.PREFS_PLID_SHARED;
					ownerId = context.getGroupId();
				}

				boolean defaultUser = GetterUtil.getBoolean(
					el.attributeValue("default-user"));

				if (portletId == null) {
					portletId = el.attributeValue("portlet-id");
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
					String userUuid = el.attributeValue("archive-user-uuid");
					String name = el.attributeValue("archive-name");

					long userId = context.getUserId(userUuid);

					PortletItem portletItem =
						portletItemLocalService.updatePortletItem(
							userId, groupId, name, portletId,
							PortletPreferences.class.getName());

					plid = 0;
					ownerId = portletItem.getPortletItemId();
				}

				if (defaultUser) {
					ownerId = defaultUserId;
				}

				try {
					portletPreferencesLocalService.deletePortletPreferences(
						ownerId, ownerType, plid, portletId);
				}
				catch (NoSuchPortletPreferencesException nsppe) {
				}

				long portletPreferencesId = counterLocalService.increment();

				PortletPreferences portletPreferences =
					portletPreferencesPersistence.create(
						portletPreferencesId);

				portletPreferences.setOwnerId(ownerId);
				portletPreferences.setOwnerType(ownerType);
				portletPreferences.setPlid(plid);
				portletPreferences.setPortletId(portletId);

				portletPreferences.setPreferences(preferences);

				portletPreferencesPersistence.update(
					portletPreferences, true);
			}
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(portletId);

		Portlet portlet = portletLocalService.getPortletById(
			companyId, resourceName);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet roles for " + portletId +
						" because the portlet does not exist");
			}
		}
		else {
			importGroupRoles(
				layoutCache, companyId, groupId, resourceName, "community",
				rolesEl);

			importUserRoles(
				layoutCache, companyId, groupId, resourceName, rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName,
				"organization", rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "location",
				rolesEl);

			importInheritedRoles(
				layoutCache, companyId, groupId, resourceName, "user-group",
				rolesEl);
		}
	}

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		Iterator<Element> itr = rolesEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletConstants.getRootPortletId(portletId);

			Portlet portlet = portletLocalService.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet roles for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupRoles(
					layoutCache, companyId, groupId, resourceName, "community",
					portletEl);

				importUserRoles(
					layoutCache, companyId, groupId, resourceName, portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName,
					"organization", portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "location",
					portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "user-group",
					portletEl);
			}
		}
	}

	protected void importRolePermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			int scope, String resourcePrimKey, Element parentEl,
			boolean communityRole)
		throws PortalException, SystemException {

		List<Element> roleEls = parentEl.elements("role");

		for (int i = 0; i < roleEls.size(); i++) {
			Element roleEl = roleEls.get(i);

			String roleName = roleEl.attributeValue("name");

			Role role = layoutCache.getRole(companyId, roleName);

			if (role == null) {
				_log.warn(
					"Ignoring permissions for role with name " + roleName);
			}
			else {
				List<String> actions = getActions(roleEl);

				permissionLocalService.setRolePermissions(
					role.getRoleId(), companyId, resourceName, scope,
					resourcePrimKey,
					actions.toArray(new String[actions.size()]));

				if (communityRole) {
					long[] groupIds = {GetterUtil.getLong(resourcePrimKey)};

					groupLocalService.addRoleGroups(role.getRoleId(), groupIds);
				}
			}
		}
	}

	protected String importTheme(LayoutSet layoutSet, byte[] themeZip)
		throws IOException {

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader == null) {
			_log.error("No theme loaders are deployed");

			return null;
		}

		ZipReader zipReader = new ZipReader(new ByteArrayInputStream(themeZip));

		Map<String, byte[]> entries = zipReader.getEntries();

		String lookAndFeelXML = new String(
			entries.get("liferay-look-and-feel.xml"));

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

		FileUtil.deltree(themeLoader.getFileStorage() + "/" + themeId);

		Iterator<Map.Entry<String, byte[]>> itr = entries.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, byte[]> entry = itr.next();

			String key = entry.getKey();
			byte[] value = entry.getValue();

			if (key.equals("liferay-look-and-feel.xml")) {
				value = lookAndFeelXML.getBytes();
			}

			FileUtil.write(
				themeLoader.getFileStorage() + "/" + themeId + "/" + key,
				value);
		}

		themeLoader.loadThemes();

		CommLink commLink = CommLink.getInstance();

		MethodWrapper methodWrapper = new MethodWrapper(
			ThemeLoaderFactory.class.getName(), "loadThemes");

		commLink.send(methodWrapper);

		themeId +=
			PortletConstants.WAR_SEPARATOR +
				themeLoader.getServletContextName();

		return PortalUtil.getJsSafePortletId(themeId);
	}

	protected void importUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Element userPermissionsEl = parentEl.element("user-permissions");

		if (userPermissionsEl == null) {
			return;
		}

		List<Element> userActionsEls = userPermissionsEl.elements(
			"user-actions");

		for (int i = 0; i < userActionsEls.size(); i++) {
			Element userActionsEl = userActionsEls.get(i);

			String emailAddress = userActionsEl.attributeValue("email-address");

			User user = layoutCache.getUser(companyId, groupId, emailAddress);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with email address " +
							emailAddress);
				}
			}
			else {
				List<String> actions = getActions(userActionsEl);

				Resource resource = layoutCache.getResource(
					companyId, groupId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey,
					portletActions);

				permissionLocalService.setUserPermissions(
					user.getUserId(),
					actions.toArray(new String[actions.size()]),
					resource.getResourceId());
			}
		}
	}

	protected void importUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = parentEl.element("user-roles");

		if (userRolesEl == null) {
			return;
		}

		List<Element> userEls = userRolesEl.elements("user");

		for (int i = 0; i < userEls.size(); i++) {
			Element userEl = userEls.get(i);

			String emailAddress = userEl.attributeValue("email-address");

			User user = layoutCache.getUser(companyId, groupId, emailAddress);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring roles for user with email address " +
							emailAddress);
				}
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					userEl, false);
			}
		}
	}

	protected boolean isDescendant(Layout layout, long layoutId)
		throws PortalException, SystemException {

		if (layout.getLayoutId() == layoutId) {
			return true;
		}
		else {
			for (Layout childLayout : layout.getChildren()) {
				if (isDescendant(childLayout, layoutId)) {
					return true;
				}
			}

			return false;
		}
	}

	protected void readComments(PortletDataContext context, Element parentEl)
		throws PortalException, SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getImportRootPath() + "/comments.xml");

			Document doc = DocumentUtil.readDocumentFromXML(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String path = asset.attributeValue("path");
				String className = asset.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));

				List<ObjectValuePair<String, byte[]>> entries =
					context.getZipFolderEntries(path);

				List<MBMessage> messages = new ArrayList<MBMessage>();

				for (ObjectValuePair<String, byte[]> entry : entries) {
					MBMessage message =
						(MBMessage)context.fromXML(entry.getValue());

					messages.add(message);
				}

				context.addComments(className, new Long(classPK), messages);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readRatings(PortletDataContext context, Element parentEl)
		throws PortalException, SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getImportRootPath() + "/ratings.xml");

			Document doc = DocumentUtil.readDocumentFromXML(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String path = asset.attributeValue("path");
				String className = asset.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));

				List<ObjectValuePair<String, byte[]>> entries =
					context.getZipFolderEntries(path);

				List<RatingsEntry> ratings = new ArrayList<RatingsEntry>();

				for (ObjectValuePair<String, byte[]> entry : entries) {
					RatingsEntry rating =
						(RatingsEntry)context.fromXML(entry.getValue());

					ratings.add(rating);
				}

				context.addRatingsEntries(className, new Long(classPK), ratings);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readTags(PortletDataContext context, Element parentEl)
		throws PortalException, SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getImportRootPath() + "/tags.xml");

			Document doc = DocumentUtil.readDocumentFromXML(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String className = GetterUtil.getString(
					asset.attributeValue("class-name"));
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));
				String entries = GetterUtil.getString(
					asset.attributeValue("entries"));

				context.addTagsEntries(
					className, new Long(classPK),
					StringUtil.split(entries, ","));
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void setLocalizedAttributes(
		Layout layout, Map<Locale, String> localeNamesMap,
		Map<Locale, String> localeTitlesMap) {

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (Locale locale : locales) {
			String name = localeNamesMap.get(locale);
			String title = localeTitlesMap.get(locale);

			layout.setName(name, locale);
			layout.setTitle(title, locale);
		}
	}

	protected void validate(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		boolean firstLayout = false;

		if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
			List<Layout> layouts = layoutPersistence.findByG_P_P(
				groupId, privateLayout, parentLayoutId, 0, 1);

			if (layouts.size() == 0) {
				firstLayout = true;
			}
			else {
				long firstLayoutId = layouts.get(0).getLayoutId();

				if (firstLayoutId == layoutId) {
					firstLayout = true;
				}
			}
		}

		if (firstLayout) {
			validateFirstLayout(type, hidden);
		}

		if (!PortalUtil.isLayoutParentable(type)) {
			if (layoutPersistence.countByG_P_P(
					groupId, privateLayout, layoutId) > 0) {

				throw new LayoutTypeException(
					LayoutTypeException.NOT_PARENTABLE);
			}
		}

		validateFriendlyURL(groupId, privateLayout, layoutId, friendlyURL);
	}

	protected void validateFirstLayout(String type, boolean hidden)
		throws PortalException {

		if (!type.equals(LayoutConstants.TYPE_PORTLET)) {
			throw new LayoutTypeException(LayoutTypeException.FIRST_LAYOUT);
		}

		if (hidden) {
			throw new LayoutHiddenException();
		}
	}

	protected void validateFriendlyURL(
			long groupId, boolean privateLayout, long layoutId,
			String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			return;
		}

		int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

		if (exceptionType != -1) {
			throw new LayoutFriendlyURLException(exceptionType);
		}

		try {
			Layout layout = layoutPersistence.findByG_P_F(
				groupId, privateLayout, friendlyURL);

			if (layout.getLayoutId() != layoutId) {
				throw new LayoutFriendlyURLException(
					LayoutFriendlyURLException.DUPLICATE);
			}
		}
		catch (NoSuchLayoutException nsle) {
		}

		LayoutImpl.validateFriendlyURLKeyword(friendlyURL);

		List<FriendlyURLMapper> friendlyURLMappers =
			portletLocalService.getFriendlyURLMappers();

		for (FriendlyURLMapper friendlyURLMapper : friendlyURLMappers) {
			if (friendlyURL.indexOf(friendlyURLMapper.getMapping()) != -1) {
				LayoutFriendlyURLException lfurle =
					new LayoutFriendlyURLException(
						LayoutFriendlyURLException.KEYWORD_CONFLICT);

				lfurle.setKeywordConflict(friendlyURLMapper.getMapping());

				throw lfurle;
			}
		}

		String layoutIdFriendlyURL = friendlyURL.substring(1);

		if (Validator.isNumber(layoutIdFriendlyURL) &&
			!layoutIdFriendlyURL.equals(String.valueOf(layoutId))) {

			LayoutFriendlyURLException lfurle = new LayoutFriendlyURLException(
				LayoutFriendlyURLException.POSSIBLE_DUPLICATE);

			lfurle.setKeywordConflict(layoutIdFriendlyURL);

			throw lfurle;
		}
	}

	protected void validateParentLayoutId(
			long groupId, boolean privateLayout, long layoutId,
			long parentLayoutId)
		throws PortalException, SystemException {

		Layout layout = layoutPersistence.findByG_P_L(
			groupId, privateLayout, layoutId);

		if (parentLayoutId != layout.getParentLayoutId()) {

			// Layouts can always be moved to the root level

			if (parentLayoutId == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				return;
			}

			// Layout cannot become a child of a layout that is not parentable

			Layout parentLayout = layoutPersistence.findByG_P_L(
				groupId, privateLayout, parentLayoutId);

			if (!PortalUtil.isLayoutParentable(parentLayout)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.NOT_PARENTABLE);
			}

			// Layout cannot become descendant of itself

			if (isDescendant(layout, parentLayoutId)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.SELF_DESCENDANT);
			}

			// If layout is moved, the new first layout must be valid

			if (layout.getParentLayoutId() ==
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

				List<Layout> layouts = layoutPersistence.findByG_P_P(
					groupId, privateLayout,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, 0, 2);

				// You can only reach this point if there are more than two
				// layouts at the root level because of the descendant check

				long firstLayoutId = layouts.get(0).getLayoutId();

				if (firstLayoutId == layoutId) {
					Layout secondLayout = layouts.get(1);

					try {
						validateFirstLayout(
							secondLayout.getType(), secondLayout.getHidden());
					}
					catch (LayoutHiddenException lhe) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN);
					}
					catch (LayoutTypeException lte) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE);
					}
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(LayoutLocalServiceImpl.class);

}