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

import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
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
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;
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
		throws Exception {

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
		throws Exception {

		boolean exportCategories = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.CATEGORIES);
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
			_log.debug("Export categories " + exportCategories);
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

		if (endDate == null) {
			endDate = new Date();
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

		String scopeLayoutUuid = GetterUtil.getString(
			jxPreferences.getValue("lfr-scope-layout-uuid", null));

		if (Validator.isNotNull(scopeLayoutUuid)) {
			Group scopeGroup = layout.getScopeGroup();

			if (scopeGroup != null) {
				scopeGroupId = scopeGroup.getGroupId();
			}
		}

		PortletDataContext context = new PortletDataContextImpl(
			companyId, scopeGroupId, parameterMap, new HashSet<String>(),
			startDate, endDate, zipWriter);

		context.setPortetDataContextListener(
			new PortletDataContextListenerImpl(context));

		context.setPlid(plid);
		context.setOldPlid(plid);
		context.setScopeLayoutUuid(scopeLayoutUuid);

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

		if (exportCategories) {
			exportCategories(context);
		}

		// Comments

		exportComments(context, root);

		// Locks

		exportLocks(context, root);

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

	protected void exportCategories(PortletDataContext context)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("categories-hierarchy");

			exportCategories(context, root);

			context.addZipEntry(
				context.getRootPath() + "/categories-hierarchy.xml",
				doc.formattedString());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportCategories(PortletDataContext context, Element root)
		throws SystemException {

		try {
			Element vocabulariesEl = root.element("vocabularies");

			if (vocabulariesEl == null) {
				vocabulariesEl = root.addElement("vocabularies");
			}

			Element assetsEl = root.addElement("assets");

			Element categoriesEl = root.addElement("categories");

			Map<String, String[]> assetCategoryUuidsMap =
				context.getAssetCategoryUuidsMap();

			for (Map.Entry<String, String[]> entry :
					assetCategoryUuidsMap.entrySet()) {

				String[] categoryEntry = entry.getKey().split(StringPool.POUND);

				String className = categoryEntry[0];
				long classPK = GetterUtil.getLong(categoryEntry[1]);

				Element asset = assetsEl.addElement("asset");

				asset.addAttribute("class-name", className);
				asset.addAttribute("class-pk", String.valueOf(classPK));
				asset.addAttribute(
					"category-uuids", StringUtil.merge(entry.getValue()));

				List<AssetCategory> assetCategories =
					AssetCategoryServiceUtil.getCategories(className, classPK);

				for (AssetCategory assestCategory : assetCategories) {
					exportCategory(
						context, vocabulariesEl, categoriesEl, assestCategory);
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportCategory(
			PortletDataContext context, Element vocabulariesEl,
			Element categoryEls, long assetCategoryId)
		throws Exception {

		AssetCategory assetCategory = AssetCategoryUtil.fetchByPrimaryKey(
			assetCategoryId);

		if (assetCategory != null) {
			exportCategory(context, vocabulariesEl, categoryEls, assetCategory);
		}
	}

	protected void exportCategory(
			PortletDataContext context, Element vocabulariesEl,
			Element categoriesEl, AssetCategory assetCategory)
		throws Exception {

		exportVocabulary(
			context, vocabulariesEl, assetCategory.getVocabularyId());

		if (assetCategory.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			exportCategory(
				context, vocabulariesEl, categoriesEl,
				assetCategory.getParentCategoryId());
		}

		String path = getCategoryPath(context, assetCategory.getCategoryId());

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element categoryEl = categoriesEl.addElement("category");

		categoryEl.addAttribute("path", path);

		assetCategory.setUserUuid(assetCategory.getUserUuid());

		context.addZipEntry(path, assetCategory);

		List<AssetCategoryProperty> assetCategoryProperties =
			AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
				assetCategory.getCategoryId());

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			Element propertyEl = categoryEl.addElement("property");

			propertyEl.addAttribute(
				"userUuid", assetCategoryProperty.getUserUuid());
			propertyEl.addAttribute("key", assetCategoryProperty.getKey());
			propertyEl.addAttribute("value", assetCategoryProperty.getValue());
		}

		context.addPermissions(
			AssetCategory.class, assetCategory.getCategoryId());
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

	protected void exportLocks(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("locks");

			Map<String, Lock> locksMap = context.getLocks();

			for (Map.Entry<String, Lock> entry : locksMap.entrySet()) {
				Lock lock = entry.getValue();

				String entryKey = entry.getKey();

				int index = entryKey.indexOf(CharPool.POUND);

				String className = entryKey.substring(0, index);
				String key = entryKey.substring(index + 1);
				String path = getLocksPath(context, className, key, lock);

				Element asset = root.addElement("asset");

				asset.addAttribute("class-name", className);
				asset.addAttribute("key", key);
				asset.addAttribute("path", path);

				if (context.isPathNotProcessed(path)) {
					context.addZipEntry(path, lock);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/locks.xml", doc.formattedString());
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
			"scope-layout-uuid", context.getScopeLayoutUuid());

		// Data

		javax.portlet.PortletPreferences jxPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, portletId, StringPool.BLANK);

		if (exportPortletData) {
			if (!portlet.isPreferencesUniquePerLayout()) {
				String dataKey =
					portletId + StringPool.AT + context.getScopeLayoutUuid();

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
			javax.portlet.PortletPreferences jxPreferences,
			Element parentEl)
		throws PortalException, SystemException {

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			return;
		}

		String portletId = portlet.getPortletId();

		Group liveGroup = layout.getGroup();

		if (liveGroup.isStagingGroup()) {
			liveGroup = liveGroup.getLiveGroup();
		}

		boolean staged = liveGroup.isStagedPortlet(portlet.getRootPortletId());

		if (!staged) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because it is configured not to be staged");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(portletId));
		sb.append(StringPool.SLASH);

		if (portlet.isPreferencesUniquePerLayout()) {
			sb.append(layout.getPlid());
		}
		else {
			sb.append(context.getScopeGroupId());
		}

		sb.append("/portlet-data.xml");

		String path = sb.toString();

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		long lastPublishDate = GetterUtil.getLong(
			jxPreferences.getValue("last-publish-date", StringPool.BLANK));

		Date startDate = context.getStartDate();

		if ((lastPublishDate > 0) && (startDate != null) &&
			(lastPublishDate < startDate.getTime())) {

			context.setStartDate(new Date(lastPublishDate));
		}

		String data = null;

		long groupId = context.getGroupId();

		context.setGroupId(context.getScopeGroupId());

		try {
			data = portletDataHandler.exportData(
				context, portletId, jxPreferences);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			context.setGroupId(groupId);
			context.setStartDate(startDate);
		}

		if (Validator.isNull(data)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		Element portletDataEl = parentEl.addElement("portlet-data");

		portletDataEl.addAttribute("path", path);

		context.addZipEntry(path, data);

		if (context.getEndDate() != null) {
			try {
				jxPreferences.setValue(
					"last-publish-date",
					String.valueOf(context.getEndDate().getTime()));

				jxPreferences.store();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
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

				root.addAttribute(
					"archive-user-uuid", portletItem.getUserUuid());
				root.addAttribute("archive-name", portletItem.getName());
			}

			List<Node> nodes = preferencesDoc.selectNodes(
				"/portlet-preferences/preference[name/text() = " +
					"'last-publish-date']");

			for (Node node : nodes) {
				preferencesDoc.remove(node);
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

	protected void exportVocabulary(
			PortletDataContext context, Element vocabulariesEl,
			long assetVocabularyId)
		throws Exception {

		AssetVocabulary assetVocabulary = AssetVocabularyUtil.findByPrimaryKey(
			assetVocabularyId);

		exportVocabulary(context, vocabulariesEl, assetVocabulary);
	}

	protected void exportVocabulary(
			PortletDataContext context, Element vocabulariesEl,
			AssetVocabulary assetVocabulary)
		throws Exception {

		String path = getVocabulariesPath(
			context, assetVocabulary.getVocabularyId());

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element vocabularyEl = vocabulariesEl.addElement("vocabulary");

		vocabularyEl.addAttribute("path", path);

		assetVocabulary.setUserUuid(assetVocabulary.getUserUuid());

		context.addZipEntry(path, assetVocabulary);

		context.addPermissions(
			AssetVocabulary.class, assetVocabulary.getVocabularyId());
	}

	protected String getCategoryPath(
		PortletDataContext context, long assetCategoryId) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getRootPath());
		sb.append("/categories/");
		sb.append(assetCategoryId);
		sb.append(".xml");

		return sb.toString();
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

	protected String getLocksPath(
		PortletDataContext context, String className, String key, Lock lock) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getRootPath());
		sb.append("/locks/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(key);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(lock.getLockId());
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
		RatingsEntry ratingsEntry) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getRootPath());
		sb.append("/ratings/");
		sb.append(PortalUtil.getClassNameId(className));
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(classPK);
		sb.append(CharPool.FORWARD_SLASH);
		sb.append(ratingsEntry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getVocabulariesPath(
		PortletDataContext context, long assetVocabularyId) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getRootPath());
		sb.append("/vocabularies/");
		sb.append(assetVocabularyId);
		sb.append(".xml");

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(PortletExporter.class);

	private PermissionExporter _permissionExporter = new PermissionExporter();

}