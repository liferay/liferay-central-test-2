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

import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="PortletExporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Zsigmond Rab
 * @author Douglas Wong
 */
public class PortletExporter {

	public byte[] exportPortletInfo(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		File file = exportPortletInfoAsFile(
			plid, groupId, portletId, parameterMap, startDate, endDate);

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

	public File exportPortletInfoAsFile(
			long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws PortalException, SystemException {

		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA + "_" +
			PortletConstants.getRootPortletId(portletId));
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		boolean exportUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.USER_PERMISSIONS);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
			_log.debug(
				"Export portlet archived setups " +
					exportPortletArchivedSetups);
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
			_log.debug("Export portlet setup " + exportPortletSetup);
			_log.debug(
				"Export portlet user preferences " +
					exportPortletUserPreferences);
			_log.debug("Export user permissions " + exportUserPermissions);
		}

		if (exportPortletDataAll) {
			exportPortletData = true;
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (!layout.isTypeControlPanel() && !layout.isTypePanel() &&
			!layout.isTypePortlet()) {

			throw new LayoutImportException(
				"Layout type " + layout.getType() + " is not valid");
		}

		long companyId = layout.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		ZipWriter zipWriter = ZipWriterFactoryUtil.getZipWriter();

		long scopeGroupId = groupId;

		javax.portlet.PortletPreferences jxPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		long scopeLayoutId = GetterUtil.getLong(
			jxPreferences.getValue("lfr-scope-layout-id", null));

		if (scopeLayoutId != 0) {
			Group scopeGroup = layout.getScopeGroup();

			if (scopeGroup != null) {
				scopeGroupId = scopeGroup.getGroupId();
			}
		}

		PortletDataContext context = new PortletDataContextImpl(
			companyId, scopeGroupId, parameterMap, new HashSet<String>(),
			startDate, endDate, zipWriter);

		context.setPlid(plid);
		context.setOldPlid(plid);
		context.setScopeLayoutId(scopeLayoutId);

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

		header.addAttribute("type", "portlet");
		header.addAttribute("group-id", String.valueOf(scopeGroupId));
		header.addAttribute(
			"private-layout", String.valueOf(layout.isPrivateLayout()));
		header.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));

		// Portlet

		exportPortlet(
			context, layoutCache, portletId, layout, root, defaultUserId,
			exportPermissions, exportPortletArchivedSetups, exportPortletData,
			exportPortletSetup, exportPortletUserPreferences,
			exportUserPermissions);

		// Categories

		exportCategories(context, root);

		// Comments

		exportComments(context, root);

		// Portlet data permissions

		if (exportPermissions) {
			_permissionExporter.exportPortletDataPermissions(context);
		}

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
			context.addZipEntry("/manifest.xml", doc.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return zipWriter.getFile();
	}

	protected void exportCategories(
			PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("categories");

			Map<String, String[]> assetCategoryIdsMap =
				context.getAssetCategoryUuidsMap();

			for (Map.Entry<String, String[]> entry :
					assetCategoryIdsMap.entrySet()) {

				String[] categoryEntry = entry.getKey().split(StringPool.POUND);

				Element asset = root.addElement("asset");

				asset.addAttribute("class-name", categoryEntry[0]);
				asset.addAttribute("class-pk", categoryEntry[1]);
				asset.addAttribute(
					"category-uuids", StringUtil.merge(entry.getValue()));
			}

			context.addZipEntry(
				context.getRootPath() + "/categories.xml",
				doc.formattedString());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportComments(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("comments");

			Map<String, List<MBMessage>> commentsMap = context.getComments();

			for (Map.Entry<String, List<MBMessage>> entry :
					commentsMap.entrySet()) {

				String[] comment = entry.getKey().split(StringPool.POUND);

				String path = getCommentsPath(context, comment[0], comment[1]);

				Element asset = root.addElement("asset");

				asset.addAttribute("path", path);
				asset.addAttribute("class-name", comment[0]);
				asset.addAttribute("class-pk", comment[1]);

				List<MBMessage> messages = entry.getValue();

				for (MBMessage message : messages) {
					path = getCommentsPath(
						context, comment[0], comment[1], message);

					if (context.isPathNotProcessed(path)) {
						context.addZipEntry(path, message);
					}
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/comments.xml", doc.formattedString());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportPortlet(
			PortletDataContext context, LayoutCache layoutCache,
			String portletId, Layout layout, Element parentEl,
			long defaultUserId, boolean exportPermissions,
			boolean exportPortletArchivedSetups, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletUserPreferences,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		long companyId = context.getCompanyId();
		long groupId = context.getGroupId();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet " + portletId +
						" because the portlet does not exist");
			}

			return;
		}

		if ((!portlet.isInstanceable()) &&
			(!portlet.isPreferencesUniquePerLayout()) &&
			(context.hasNotUniquePerLayout(portletId))) {

			return;
		}

		Document doc = SAXReaderUtil.createDocument();

		Element portletEl = doc.addElement("portlet");

		portletEl.addAttribute("portlet-id", portletId);
		portletEl.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));
		portletEl.addAttribute("old-plid", String.valueOf(layout.getPlid()));
		portletEl.addAttribute(
			"scope-layout-id", String.valueOf(context.getScopeLayoutId()));

		// Data

		javax.portlet.PortletPreferences jxPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, portletId, StringPool.BLANK);

		if (exportPortletData) {
			if (!portlet.isPreferencesUniquePerLayout()) {
				String dataKey =
					portletId + StringPool.AT + context.getScopeLayoutId();

				if (!context.hasNotUniquePerLayout(dataKey)) {
					context.putNotUniquePerLayout(dataKey);

					exportPortletData(
						context, portlet, layout, jxPreferences, portletEl);
				}
			}
			else {
				exportPortletData(
					context, portlet, layout, jxPreferences, portletEl);
			}
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
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
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
				PortletItemLocalServiceUtil.getPortletItems(
					groupId, rootPortletId, PortletPreferences.class.getName());

			for (PortletItem portletItem: portletItems) {
				long ownerId = portletItem.getPortletItemId();
				int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;

				exportPortletPreferences(
					context, ownerId, ownerType, false, null,
					portletItem.getPortletId(), portletEl);
			}
		}

		// Permissions

		if (exportPermissions) {
			_permissionExporter.exportPortletPermissions(
				context, layoutCache, portletId, layout, portletEl);
		}

		// Zip

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(portletId));
		sb.append(StringPool.SLASH);
		sb.append(layout.getPlid());
		sb.append("/portlet.xml");

		String path = sb.toString();

		Element el = parentEl.addElement("portlet");

		el.addAttribute("portlet-id", portletId);
		el.addAttribute("layout-id", String.valueOf(layout.getLayoutId()));
		el.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			try {
				context.addZipEntry(path, doc.formattedString());
			}
			catch (IOException ioe) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioe.getMessage());
				}
			}

			context.addPrimaryKey(String.class, path);
		}
	}

	protected void exportPortletData(
			PortletDataContext context, Portlet portlet, Layout layout,
			javax.portlet.PortletPreferences portletPreferences,
			Element parentEl)
		throws SystemException {

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			return;
		}

		String portletId = portlet.getPortletId();

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		String data = null;

		long groupId = context.getGroupId();

		context.setGroupId(context.getScopeGroupId());

		try {
			data = portletDataHandler.exportData(
				context, portletId, portletPreferences);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			context.setGroupId(groupId);
		}

		if (Validator.isNull(data)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(portletId));

		if (portlet.isPreferencesUniquePerLayout()) {
			sb.append(StringPool.SLASH);
			sb.append(layout.getPlid());
		}

		sb.append("/portlet-data.xml");

		Element portletDataEl = parentEl.addElement("portlet-data");

		portletDataEl.addAttribute("path", sb.toString());

		context.addZipEntry(sb.toString(), data);
	}

	protected void exportPortletPreference(
			PortletDataContext context, long ownerId, int ownerType,
			boolean defaultUser, PortletPreferences portletPreferences,
			String portletId, long plid, Element parentEl)
		throws SystemException {

		try {
			Document preferencesDoc = SAXReaderUtil.read(
				portletPreferences.getPreferences());

			Element root = preferencesDoc.getRootElement();

			root.addAttribute("owner-id", String.valueOf(ownerId));
			root.addAttribute("owner-type", String.valueOf(ownerType));
			root.addAttribute("default-user", String.valueOf(defaultUser));
			root.addAttribute("plid", String.valueOf(plid));
			root.addAttribute("portlet-id", portletId);

			if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
				PortletItem portletItem =
					PortletItemLocalServiceUtil.getPortletItem(ownerId);

				User user = UserLocalServiceUtil.getUserById(
					portletItem.getUserId());

				root.addAttribute("archive-user-uuid", user.getUuid());
				root.addAttribute("archive-name", portletItem.getName());
			}

			String path = getPortletPreferencesPath(
				context, portletId, ownerId, ownerType, plid);

			parentEl.addElement(
				"portlet-preferences").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				context.addZipEntry(path, preferencesDoc.formattedString());
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
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
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

	protected void exportRatings(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("ratings");

			Map<String, List<RatingsEntry>> ratingsEntriesMap =
				context.getRatingsEntries();

			for (Map.Entry<String, List<RatingsEntry>> entry :
					ratingsEntriesMap.entrySet()) {

				String[] ratingsEntry = entry.getKey().split(StringPool.POUND);

				String ratingPath = getRatingsPath(
					context, ratingsEntry[0], ratingsEntry[1]);

				Element asset = root.addElement("asset");

				asset.addAttribute("path", ratingPath);
				asset.addAttribute("class-name", ratingsEntry[0]);
				asset.addAttribute("class-pk", ratingsEntry[1]);

				List<RatingsEntry> ratingsEntries = entry.getValue();

				for (RatingsEntry rating : ratingsEntries) {
					ratingPath = getRatingsPath(
						context, ratingsEntry[0], ratingsEntry[1], rating);

					context.addZipEntry(ratingPath, rating);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/ratings.xml", doc.formattedString());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportTags(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("tags");

			Map<String, String[]> assetTagNamesMap =
				context.getAssetTagNamesMap();

			for (Map.Entry<String, String[]> entry :
					assetTagNamesMap.entrySet()) {

				String[] tagsEntry = entry.getKey().split(StringPool.POUND);

				Element asset = root.addElement("asset");

				asset.addAttribute("class-name", tagsEntry[0]);
				asset.addAttribute("class-pk", tagsEntry[1]);
				asset.addAttribute("tags", StringUtil.merge(entry.getValue()));
			}

			context.addZipEntry(
				context.getRootPath() + "/tags.xml", doc.formattedString());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected String getCommentsPath(
		PortletDataContext context, String className, String classPK) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getRootPath());
		sb.append("/comments/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);

		return sb.toString();
	}

	protected String getCommentsPath(
		PortletDataContext context, String className, String classPK,
		MBMessage message) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getRootPath());
		sb.append("/comments/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(message.getMessageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getPortletDataPath(
		PortletDataContext context, String portletId) {

		return context.getPortletPath(portletId) + "/portlet-data.xml";
	}

	protected String getPortletPreferencesPath(
		PortletDataContext context, String portletId, long ownerId,
		int ownerType, long plid) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getPortletPath(portletId));
		sb.append("/preferences/");

		if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
			sb.append("company/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
			sb.append("group/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT) {
			sb.append("layout/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) {
			sb.append("user/");
		}
		else if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
			sb.append("archived/");
		}

		sb.append(ownerId);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(plid);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append("portlet-preferences.xml");

		return sb.toString();
	}

	protected String getRatingsPath(
		PortletDataContext context, String className, String classPK) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getRootPath());
		sb.append("/ratings/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);

		return sb.toString();
	}

	protected String getRatingsPath(
		PortletDataContext context, String className, String classPK,
		RatingsEntry rating) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getRootPath());
		sb.append("/ratings/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(rating.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(PortletExporter.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();

}