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

package com.liferay.portal.lar;

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LayoutPrototypeException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.MissingReferenceException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.NoSuchLayoutSetPrototypeException;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.MissingReference;
import com.liferay.portal.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.PortletDataHandlerStatusMessageSenderUtil;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.servlet.filters.cache.CacheUtil;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.portlet.sites.util.Sites;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.time.StopWatch;

/**
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 * @author Wesley Gong
 * @author Zsigmond Rab
 * @author Douglas Wong
 * @author Julio Camarero
 * @author Zsolt Berentey
 */
public class LayoutImporter {

	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws Exception {

		try {
			ExportImportThreadLocal.setLayoutImportInProcess(true);

			doImportLayouts(userId, groupId, privateLayout, parameterMap, file);
		}
		finally {
			ExportImportThreadLocal.setLayoutImportInProcess(false);

			CacheUtil.clearCache();
			JournalContentUtil.clearCache();
			PermissionCacheUtil.clearCache();
		}
	}

	public MissingReferences validateFile(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws Exception {

		try {
			ExportImportThreadLocal.setLayoutValidationInProcess(true);

			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				groupId, privateLayout);

			ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

			PortletDataContext portletDataContext =
				PortletDataContextFactoryUtil.createImportPortletDataContext(
					layoutSet.getCompanyId(), groupId, parameterMap, null,
					zipReader);

			validateFile(portletDataContext);

			MissingReferences missingReferences =
				ExportImportHelperUtil.validateMissingReferences(
					userId, groupId, parameterMap, file);

			Map<String, MissingReference> dependencyMissingReferences =
				missingReferences.getDependencyMissingReferences();

			if (!dependencyMissingReferences.isEmpty()) {
				throw new MissingReferenceException(missingReferences);
			}

			return missingReferences;
		}
		finally {
			ExportImportThreadLocal.setLayoutValidationInProcess(false);
		}
	}

	protected void deleteMissingLayouts(
			PortletDataContext portletDataContext,
			List<String> sourceLayoutUuids, List<Layout> previousLayouts,
			ServiceContext serviceContext)
		throws Exception {

		if (_log.isDebugEnabled() && !sourceLayoutUuids.isEmpty()) {
			_log.debug("Delete missing layouts");
		}

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		for (Layout layout : previousLayouts) {
			if (!sourceLayoutUuids.contains(layout.getUuid()) &&
				!layoutPlids.containsValue(layout.getPlid())) {

				try {
					LayoutLocalServiceUtil.deleteLayout(
						layout, false, serviceContext);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}
	}

	protected void doImportLayouts(
			long userId, long groupId, boolean privateLayout,
			Map<String, String[]> parameterMap, File file)
		throws Exception {

		boolean deleteMissingLayouts = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.booleanValue());
		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importLogo = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LOGO);
		boolean importLayoutSetSettings = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS);

		boolean layoutSetPrototypeLinkEnabled = MapUtil.getBoolean(
			parameterMap,
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED, true);

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isLayoutSetPrototype()) {
			layoutSetPrototypeLinkEnabled = false;
		}

		String layoutsImportMode = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		if (_log.isDebugEnabled()) {
			_log.debug("Delete portlet data " + deletePortletData);
			_log.debug("Import permissions " + importPermissions);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			groupId, privateLayout);

		long companyId = layoutSet.getCompanyId();

		User user = UserUtil.findByPrimaryKey(userId);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(companyId);
			serviceContext.setSignedIn(false);
			serviceContext.setUserId(userId);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}

		UserIdStrategy strategy = _portletImporter.getUserIdStrategy(
			user, userIdStrategy);

		ManifestSummary manifestSummary =
			ExportImportHelperUtil.getManifestSummary(
				userId, groupId, parameterMap, file);

		if (BackgroundTaskThreadLocal.hasBackgroundTask()) {
			PortletDataHandlerStatusMessageSenderUtil.sendStatusMessage(
				"layout", manifestSummary);
		}

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				companyId, groupId, parameterMap, strategy, zipReader);

		portletDataContext.setManifestSummary(manifestSummary);
		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));

		portletDataContext.setPrivateLayout(privateLayout);

		// Zip

		validateFile(portletDataContext);

		// Company id

		long sourceCompanyId = GetterUtil.getLong(
			_headerElement.attributeValue("company-id"));

		portletDataContext.setSourceCompanyId(sourceCompanyId);

		// Company group id

		long sourceCompanyGroupId = GetterUtil.getLong(
			_headerElement.attributeValue("company-group-id"));

		portletDataContext.setSourceCompanyGroupId(sourceCompanyGroupId);

		// Group id

		long sourceGroupId = GetterUtil.getLong(
			_headerElement.attributeValue("group-id"));

		portletDataContext.setSourceGroupId(sourceGroupId);

		// User personal site group id

		long sourceUserPersonalSiteGroupId = GetterUtil.getLong(
			_headerElement.attributeValue("user-personal-site-group-id"));

		portletDataContext.setSourceUserPersonalSiteGroupId(
			sourceUserPersonalSiteGroupId);

		// Layout and layout set prototype

		String layoutSetPrototypeUuid = _layoutsElement.attributeValue(
			"layout-set-prototype-uuid");

		String larType = _headerElement.attributeValue("type");

		if (group.isLayoutPrototype() && larType.equals("layout-prototype")) {
			deleteMissingLayouts = false;

			LayoutPrototype layoutPrototype =
				LayoutPrototypeLocalServiceUtil.getLayoutPrototype(
					group.getClassPK());

			String layoutPrototypeUuid = GetterUtil.getString(
				_headerElement.attributeValue("type-uuid"));

			LayoutPrototype existingLayoutPrototype = null;

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				try {
					existingLayoutPrototype =
						LayoutPrototypeLocalServiceUtil.
							getLayoutPrototypeByUuidAndCompanyId(
								layoutPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutPrototypeException nslpe) {
				}
			}

			if (existingLayoutPrototype == null) {
				List<Layout> layouts =
					LayoutLocalServiceUtil.getLayoutsByLayoutPrototypeUuid(
						layoutPrototype.getUuid());

				layoutPrototype.setUuid(layoutPrototypeUuid);

				LayoutPrototypeLocalServiceUtil.updateLayoutPrototype(
					layoutPrototype);

				for (Layout layout : layouts) {
					layout.setLayoutPrototypeUuid(layoutPrototypeUuid);

					LayoutLocalServiceUtil.updateLayout(layout);
				}
			}
		}
		else if (group.isLayoutSetPrototype() &&
				 larType.equals("layout-set-prototype")) {

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(
					group.getClassPK());

			String importedLayoutSetPrototypeUuid = GetterUtil.getString(
				_headerElement.attributeValue("type-uuid"));

			LayoutSetPrototype existingLayoutSetPrototype = null;

			if (Validator.isNotNull(importedLayoutSetPrototypeUuid)) {
				try {
					existingLayoutSetPrototype =
						LayoutSetPrototypeLocalServiceUtil.
							getLayoutSetPrototypeByUuidAndCompanyId(
								importedLayoutSetPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutSetPrototypeException nslspe) {
				}
			}

			if (existingLayoutSetPrototype == null) {
				List<LayoutSet> layoutSets =
					LayoutSetLocalServiceUtil.
						getLayoutSetsByLayoutSetPrototypeUuid(
							layoutSetPrototype.getUuid());

				layoutSetPrototype.setUuid(importedLayoutSetPrototypeUuid);

				LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
					layoutSetPrototype);

				for (LayoutSet curLayoutSet : layoutSets) {
					curLayoutSet.setLayoutSetPrototypeUuid(
						importedLayoutSetPrototypeUuid);

					LayoutSetLocalServiceUtil.updateLayoutSet(curLayoutSet);
				}
			}
		}
		else if (larType.equals("layout-set-prototype")) {
			layoutSetPrototypeUuid = GetterUtil.getString(
				_headerElement.attributeValue("type-uuid"));
		}

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			layoutSet.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
			layoutSet.setLayoutSetPrototypeLinkEnabled(
				layoutSetPrototypeLinkEnabled);

			LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
		}

		// Look and feel

		if (importLogo) {
			String logoPath = _headerElement.attributeValue("logo-path");

			byte[] iconBytes = portletDataContext.getZipEntryAsByteArray(
				logoPath);

			if (ArrayUtil.isNotEmpty(iconBytes)) {
				LayoutSetLocalServiceUtil.updateLogo(
					groupId, privateLayout, true, iconBytes);
			}
			else {
				LayoutSetLocalServiceUtil.updateLogo(
					groupId, privateLayout, false, (File)null);
			}
		}

		_themeImporter.importTheme(portletDataContext, layoutSet);

		if (importLayoutSetSettings) {
			String settings = GetterUtil.getString(
				_headerElement.elementText("settings"));

			LayoutSetLocalServiceUtil.updateSettings(
				groupId, privateLayout, settings);
		}

		// Read asset tags, expando tables, locks, and permissions to make them
		// available to the data handlers through the context

		Element portletsElement = _rootElement.element("portlets");

		List<Element> portletElements = portletsElement.elements("portlet");

		if (importPermissions) {
			for (Element portletElement : portletElements) {
				String portletPath = portletElement.attributeValue("path");

				Document portletDocument = SAXReaderUtil.read(
					portletDataContext.getZipEntryAsString(portletPath));

				_permissionImporter.checkRoles(
					layoutCache, companyId, groupId, userId,
					portletDocument.getRootElement());
			}

			_permissionImporter.readPortletDataPermissions(portletDataContext);
		}

		_portletImporter.readAssetTags(portletDataContext);
		_portletImporter.readExpandoTables(portletDataContext);
		_portletImporter.readLocks(portletDataContext);

		// Layouts

		List<Layout> previousLayouts = LayoutUtil.findByG_P(
			groupId, privateLayout);

		Set<Layout> modifiedLayouts = new HashSet<Layout>();

		// Remove layouts that were deleted from the layout set prototype

		if (Validator.isNotNull(layoutSetPrototypeUuid) &&
			layoutSetPrototypeLinkEnabled) {

			LayoutSetPrototype layoutSetPrototype =
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);

			for (Layout layout : previousLayouts) {
				String sourcePrototypeLayoutUuid =
					layout.getSourcePrototypeLayoutUuid();

				if (Validator.isNull(layout.getSourcePrototypeLayoutUuid())) {
					continue;
				}

				if (SitesUtil.isLayoutModifiedSinceLastMerge(layout)) {
					modifiedLayouts.add(layout);

					continue;
				}

				Layout sourcePrototypeLayout = LayoutUtil.fetchByUUID_G_P(
					sourcePrototypeLayoutUuid, layoutSetPrototype.getGroupId(),
					true);

				if (sourcePrototypeLayout == null) {
					LayoutLocalServiceUtil.deleteLayout(
						layout, false, serviceContext);
				}
			}
		}

		if (_log.isDebugEnabled()) {
			if (!_layoutElements.isEmpty()) {
				_log.debug("Importing layouts");
			}
		}

		List<String> sourceLayoutsUuids = new ArrayList<String>();

		for (Element layoutElement : _layoutElements) {
			importLayout(portletDataContext, sourceLayoutsUuids, layoutElement);
		}

		// Delete portlet data

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		if (deletePortletData) {
			if (_log.isDebugEnabled()) {
				if (!portletElements.isEmpty()) {
					_log.debug("Deleting portlet data");
				}
			}

			for (Element portletElement : portletElements) {
				String portletId = portletElement.attributeValue("portlet-id");
				long layoutId = GetterUtil.getLong(
					portletElement.attributeValue("layout-id"));

				Layout layout = layouts.get(layoutId);

				long plid = layout.getPlid();

				portletDataContext.setPlid(plid);

				_portletImporter.deletePortletData(
					portletDataContext, portletId, plid);
			}
		}

		// Import portlets

		if (_log.isDebugEnabled()) {
			if (!portletElements.isEmpty()) {
				_log.debug("Importing portlets");
			}
		}

		for (Element portletElement : portletElements) {
			String portletPath = portletElement.attributeValue("path");
			String portletId = portletElement.attributeValue("portlet-id");
			long layoutId = GetterUtil.getLong(
				portletElement.attributeValue("layout-id"));
			long oldPlid = GetterUtil.getLong(
				portletElement.attributeValue("old-plid"));

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				portletDataContext.getCompanyId(), portletId);

			if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				continue;
			}

			Layout layout = layouts.get(layoutId);

			long plid = LayoutConstants.DEFAULT_PLID;

			if (layout != null) {
				plid = layout.getPlid();

				if (modifiedLayouts.contains(layout)) {
					continue;
				}
			}

			portletDataContext.setPlid(plid);
			portletDataContext.setOldPlid(oldPlid);

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(portletPath));

			portletElement = portletDocument.getRootElement();

			// The order of the import is important. You must always import the
			// portlet preferences first, then the portlet data, then the
			// portlet permissions. The import of the portlet data assumes that
			// portlet preferences already exist.

			setPortletScope(portletDataContext, portletElement);

			long portletPreferencesGroupId = groupId;

			Element portletDataElement = portletElement.element("portlet-data");

			Map<String, Boolean> importPortletControlsMap =
				ExportImportHelperUtil.getImportPortletControlsMap(
					companyId, portletId, parameterMap, portletDataElement,
					manifestSummary);

			try {
				if (layout != null) {
					portletPreferencesGroupId = layout.getGroupId();
				}

				// Portlet preferences

				_portletImporter.importPortletPreferences(
					portletDataContext, layoutSet.getCompanyId(),
					portletPreferencesGroupId, layout, null, portletElement,
					false,
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_SETUP),
					importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));

				// Portlet data

				if (importPortletControlsMap.get(
						PortletDataHandlerKeys.PORTLET_DATA)) {

					_portletImporter.importPortletData(
						portletDataContext, portletId, plid,
						portletDataElement);
				}
			}
			finally {
				_portletImporter.resetPortletScope(
					portletDataContext, portletPreferencesGroupId);
			}

			// Portlet permissions

			if (importPermissions) {
				_permissionImporter.importPortletPermissions(
					layoutCache, companyId, groupId, userId, layout,
					portletElement, portletId);
			}

			// Archived setups

			_portletImporter.importPortletPreferences(
				portletDataContext, layoutSet.getCompanyId(), groupId, null,
				null, portletElement, false,
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_DATA),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_SETUP),
				importPortletControlsMap.get(
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES));
		}

		if (importPermissions) {
			if (userId > 0) {
				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					User.class);

				indexer.reindex(userId);
			}
		}

		// Asset links

		_portletImporter.readAssetLinks(portletDataContext);

		// Delete missing layouts

		if (deleteMissingLayouts) {
			deleteMissingLayouts(
				portletDataContext, sourceLayoutsUuids, previousLayouts,
				serviceContext);
		}

		// Page count

		layoutSet = LayoutSetLocalServiceUtil.updatePageCount(
			groupId, privateLayout);

		// Site

		GroupLocalServiceUtil.updateSite(groupId, true);

		// Last merge time is updated only if there aren't any modified layouts

		if (layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			long lastMergeTime = System.currentTimeMillis();

			for (Layout layout : layouts.values()) {
				layout = LayoutLocalServiceUtil.getLayout(layout.getPlid());

				if (modifiedLayouts.contains(layout)) {
					continue;
				}

				UnicodeProperties typeSettingsProperties =
					layout.getTypeSettingsProperties();

				typeSettingsProperties.setProperty(
					Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

				LayoutUtil.update(layout);
			}

			// The layout set may be stale because LayoutUtil#update(layout)
			// triggers LayoutSetPrototypeLayoutListener and that may have
			// updated this layout set

			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				layoutSet.getLayoutSetId());

			UnicodeProperties settingsProperties =
				layoutSet.getSettingsProperties();

			String mergeFailFriendlyURLLayouts =
				settingsProperties.getProperty(
					Sites.MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

			if (Validator.isNull(mergeFailFriendlyURLLayouts) &&
				(modifiedLayouts.size() == 0)) {

				settingsProperties.setProperty(
					Sites.LAST_MERGE_TIME, String.valueOf(lastMergeTime));

				LayoutSetLocalServiceUtil.updateLayoutSet(layoutSet);
			}
		}

		// Page priorities

		updateLayoutPriorities(portletDataContext);

		// Deletion system events

		_deletionSystemEventImporter.importDeletionSystemEvents(
			portletDataContext);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}

		zipReader.close();
	}

	protected void importLayout(
			PortletDataContext portletDataContext,
			List<String> sourceLayoutsUuids, Element layoutElement)
		throws Exception {

		String action = layoutElement.attributeValue(Constants.ACTION);

		if (!action.equals(Constants.SKIP)) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, layoutElement);
		}

		if (!action.equals(Constants.DELETE)) {
			sourceLayoutsUuids.add(layoutElement.attributeValue("uuid"));
		}
	}

	protected void readXML(PortletDataContext portletDataContext)
		throws Exception {

		if ((_rootElement != null) && (_headerElement != null) &&
			(_layoutsElement != null) && (_layoutElements != null)) {

			return;
		}

		String xml = portletDataContext.getZipEntryAsString("/manifest.xml");

		if (xml == null) {
			throw new LARFileException("manifest.xml not found in the LAR");
		}

		try {
			Document document = SAXReaderUtil.read(xml);

			_rootElement = document.getRootElement();

			portletDataContext.setImportDataRootElement(_rootElement);
		}
		catch (Exception e) {
			throw new LARFileException(e);
		}

		_headerElement = _rootElement.element("header");

		_layoutsElement = portletDataContext.getImportDataGroupElement(
			Layout.class);

		_layoutElements = _layoutsElement.elements();
	}

	protected void setPortletScope(
		PortletDataContext portletDataContext, Element portletElement) {

		// Portlet data scope

		String scopeLayoutUuid = GetterUtil.getString(
			portletElement.attributeValue("scope-layout-uuid"));
		String scopeLayoutType = GetterUtil.getString(
			portletElement.attributeValue("scope-layout-type"));

		portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
		portletDataContext.setScopeType(scopeLayoutType);

		// Layout scope

		try {
			Group scopeGroup = null;

			if (scopeLayoutType.equals("company")) {
				scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());
			}
			else if (Validator.isNotNull(scopeLayoutUuid)) {
				boolean privateLayout = GetterUtil.getBoolean(
					portletElement.attributeValue("private-layout"));

				Layout scopeLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, portletDataContext.getGroupId(),
						privateLayout);

				if (scopeLayout.hasScopeGroup()) {
					scopeGroup = scopeLayout.getScopeGroup();
				}
				else {
					String name = String.valueOf(scopeLayout.getPlid());

					scopeGroup = GroupLocalServiceUtil.addGroup(
						portletDataContext.getUserId(null),
						GroupConstants.DEFAULT_PARENT_GROUP_ID,
						Layout.class.getName(), scopeLayout.getPlid(),
						GroupConstants.DEFAULT_LIVE_GROUP_ID, name, null, 0,
						true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
						null, false, true, null);
				}

				Group group = scopeLayout.getGroup();

				if (group.isStaged() && !group.isStagedRemotely()) {
					try {
						Layout oldLayout =
							LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								portletDataContext.getSourceGroupId(),
								privateLayout);

						Group oldScopeGroup = oldLayout.getScopeGroup();

						if (group.isStagingGroup()) {
							scopeGroup.setLiveGroupId(
								oldScopeGroup.getGroupId());

							GroupLocalServiceUtil.updateGroup(scopeGroup);
						}
						else {
							oldScopeGroup.setLiveGroupId(
								scopeGroup.getGroupId());

							GroupLocalServiceUtil.updateGroup(oldScopeGroup);
						}
					}
					catch (NoSuchLayoutException nsle) {
						if (_log.isWarnEnabled()) {
							_log.warn(nsle);
						}
					}
				}
			}

			if (scopeGroup != null) {
				portletDataContext.setScopeGroupId(scopeGroup.getGroupId());
			}
		}
		catch (PortalException pe) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void updateLayoutPriorities(PortletDataContext portletDataContext)
		throws PortalException, SystemException {

		Map<Long, Layout> layouts =
			(Map<Long, Layout>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class + ".layout");

		Map<Long, NavigableSet<Layout>> navigableSets =
			new HashMap<Long, NavigableSet<Layout>>();

		List<Layout> importedLayouts = new LinkedList<Layout>();

		// Gathering imported layouts and create a navigable set for each parent
		// layout ID to handle each priority queue separately

		for (Element layoutElement : _layoutElements) {
			String action = layoutElement.attributeValue(Constants.ACTION);

			if (action.equals(Constants.ADD)) {
				long layoutId = GetterUtil.getLong(
					layoutElement.attributeValue("layout-id"));

				Layout layout = layouts.get(layoutId);

				NavigableSet<Layout> navigableSet = navigableSets.get(
					layout.getParentLayoutId());

				if (navigableSet == null) {
					navigableSets.put(
						layout.getParentLayoutId(),
						new TreeSet<Layout>(new LayoutPriorityComparator()));
				}

				importedLayouts.add(
					LayoutLocalServiceUtil.getLayout(layout.getPlid()));
			}
		}

		List<Layout> unmodifiedLayouts = new LinkedList<Layout>(
			LayoutLocalServiceUtil.getLayouts(
				portletDataContext.getGroupId(),
				portletDataContext.isPrivateLayout()));

		unmodifiedLayouts.removeAll(importedLayouts);

		// Priorities are up to date if there are no unmodified layouts

		if (unmodifiedLayouts.isEmpty()) {
			return;
		}

		// Fill the navigable sets with layouts that were not updated by the
		// import

		for (Layout unmodifiedLayout : unmodifiedLayouts) {
			NavigableSet<Layout> navigableSet = navigableSets.get(
				unmodifiedLayout.getParentLayoutId());

			if (navigableSet != null) {
				navigableSet.add(unmodifiedLayout);
			}
		}

		for (Layout importedLayout : importedLayouts) {
			NavigableSet<Layout> navigableSet = navigableSets.get(
				importedLayout.getParentLayoutId());

			if (navigableSet.isEmpty()) {
				continue;
			}

			// Ensure that priorities are unique

			Set<Layout> tailLayouts = navigableSet.tailSet(
				importedLayout, true);

			int priority = importedLayout.getPriority();

			for (Layout tailLayout : tailLayouts) {
				if (tailLayout.getPriority() == priority) {
					tailLayout.setPriority(++priority);
				}
				else {
					break;
				}
			}

			navigableSet.add(importedLayout);
		}

		for (NavigableSet<Layout> navigableSet : navigableSets.values()) {
			for (Layout layout : navigableSet) {
				LayoutUtil.update(layout);
			}
		}
	}

	protected void validateFile(PortletDataContext portletDataContext)
		throws Exception {

		// Build compatibility

		readXML(portletDataContext);

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			_headerElement.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type

		String larType = _headerElement.attributeValue("type");

		if (!larType.equals("layout-prototype") &&
			!larType.equals("layout-set") &&
			!larType.equals("layout-set-prototype")) {

			throw new LARTypeException(larType);
		}

		Group group = GroupLocalServiceUtil.fetchGroup(
			portletDataContext.getGroupId());

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE);

		if (larType.equals("layout-prototype") && !group.isLayoutPrototype() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			throw new LARTypeException(
				"A page template can only be imported to a page template");
		}

		if (larType.equals("layout-set")) {
			if (group.isLayoutPrototype() || group.isLayoutSetPrototype()) {
				throw new LARTypeException(
					"A site can only be imported to a site");
			}

			long sourceCompanyGroupId = GetterUtil.getLong(
				_headerElement.attributeValue("company-group-id"));
			long sourceGroupId = GetterUtil.getLong(
				_headerElement.attributeValue("group-id"));

			if (group.isCompany() ^ (sourceCompanyGroupId == sourceGroupId)) {
				throw new LARTypeException(
					"A company site can only be imported to a company site");
			}
		}

		if (larType.equals("layout-set-prototype") &&
			!group.isLayoutSetPrototype() &&
			!layoutsImportMode.equals(
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

			throw new LARTypeException(
				"A site template can only be imported to a site template");
		}

		// Available locales

		Locale[] sourceAvailableLocales = LocaleUtil.fromLanguageIds(
			StringUtil.split(
				_headerElement.attributeValue("available-locales")));

		Locale[] targetAvailableLocales = LanguageUtil.getAvailableLocales(
			portletDataContext.getScopeGroupId());

		for (Locale sourceAvailableLocale : sourceAvailableLocales) {
			if (!ArrayUtil.contains(
					targetAvailableLocales, sourceAvailableLocale)) {

				LocaleException le = new LocaleException(
					LocaleException.TYPE_EXPORT_IMPORT);

				le.setSourceAvailableLocales(sourceAvailableLocales);
				le.setTargetAvailableLocales(targetAvailableLocales);

				throw le;
			}
		}

		// Layout prototypes validity

		validateLayoutPrototypes(
			portletDataContext.getCompanyId(), _layoutsElement,
			_layoutElements);
	}

	protected void validateLayoutPrototypes(
			long companyId, Element layoutsElement,
			List<Element> layoutElements)
		throws Exception {

		List<Tuple> missingLayoutPrototypes = new ArrayList<Tuple>();

		String layoutSetPrototypeUuid = layoutsElement.attributeValue(
			"layout-set-prototype-uuid");

		if (Validator.isNotNull(layoutSetPrototypeUuid)) {
			try {
				LayoutSetPrototypeLocalServiceUtil.
					getLayoutSetPrototypeByUuidAndCompanyId(
						layoutSetPrototypeUuid, companyId);
			}
			catch (NoSuchLayoutSetPrototypeException nlspe) {
				String layoutSetPrototypeName = layoutsElement.attributeValue(
					"layout-set-prototype-name");

				missingLayoutPrototypes.add(
					new Tuple(
						LayoutSetPrototype.class.getName(),
						layoutSetPrototypeUuid, layoutSetPrototypeName));
			}
		}

		for (Element layoutElement : layoutElements) {
			String action = layoutElement.attributeValue(Constants.ACTION);

			if (action.equals(Constants.SKIP)) {
				continue;
			}

			String layoutPrototypeUuid = GetterUtil.getString(
				layoutElement.attributeValue("layout-prototype-uuid"));

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				try {
					LayoutPrototypeLocalServiceUtil.
						getLayoutPrototypeByUuidAndCompanyId(
							layoutPrototypeUuid, companyId);
				}
				catch (NoSuchLayoutPrototypeException nslpe) {
					String layoutPrototypeName = GetterUtil.getString(
						layoutElement.attributeValue("layout-prototype-name"));

					missingLayoutPrototypes.add(
						new Tuple(
							LayoutPrototype.class.getName(),
							layoutPrototypeUuid, layoutPrototypeName));
				}
			}
		}

		if (!missingLayoutPrototypes.isEmpty()) {
			throw new LayoutPrototypeException(missingLayoutPrototypes);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutImporter.class);

	private DeletionSystemEventImporter _deletionSystemEventImporter =
		new DeletionSystemEventImporter();
	private Element _headerElement;
	private List<Element> _layoutElements;
	private Element _layoutsElement;
	private PermissionImporter _permissionImporter = new PermissionImporter();
	private PortletImporter _portletImporter = new PortletImporter();
	private Element _rootElement;
	private ThemeImporter _themeImporter = new ThemeImporter();

}