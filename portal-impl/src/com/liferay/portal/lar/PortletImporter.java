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

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.social.util.SocialActivityThreadLocal;

import java.io.File;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;

/**
 * <a href="PortletImporter.java.html"><b><i>View Source</i></b></a>
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
public class PortletImporter {

	public void importPortletInfo(
			long userId, long plid, long groupId, String portletId,
			Map<String, String[]> parameterMap, File file)
		throws PortalException, SystemException {

		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
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

		User user = UserUtil.findByPrimaryKey(userId);

		UserIdStrategy strategy = getUserIdStrategy(user, userIdStrategy);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext context = new PortletDataContextImpl(
			companyId, groupId, parameterMap, new HashSet<String>(),
			strategy, zipReader);

		context.setPlid(plid);
		context.setPrivateLayout(layout.isPrivateLayout());

		// Zip

		Element root = null;

		// Manifest

		String xml = context.getZipEntryAsString("/manifest.xml");

		try {
			Document doc = SAXReaderUtil.read(xml);

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

		// Import group id

		long sourceGroupId = GetterUtil.getLong(
			header.attributeValue("group-id"));

		context.setSourceGroupId(sourceGroupId);

		// Read categories, comments, locks, ratings, and tags to make them
		// available to the data handlers through the context

		readCategories(context, root);
		readComments(context, root);
		readLocks(context, root);
		readRatings(context, root);
		readTags(context, root);

		if (importPermissions) {
			_permissionImporter.readPortletDataPermissions(context);
		}

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
			Document portletDoc = SAXReaderUtil.read(
				context.getZipEntryAsString(
					portletRefEl.attributeValue("path")));

			portletEl = portletDoc.getRootElement();
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}

		// Portlet preferences

		importPortletPreferences(
			context, layout.getCompanyId(), groupId, layout, portletId,
			portletEl, importPortletSetup, importPortletArchivedSetups,
			importUserPreferences, true);

		// Portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Importing portlet data");
		}

		if (importPortletData) {
			Element portletDataRefEl = portletEl.element("portlet-data");

			if (portletDataRefEl != null) {
				importPortletData(context, portletId, plid, portletDataRefEl);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Could not import portlet data because it cannot be " +
							"found in the input");
				}
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Importing portlet data takes " + stopWatch.getTime() + " ms");
		}

		zipReader.close();
	}

	protected void deletePortletData(
			PortletDataContext context, String portletId, long plid)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences portletPreferences =
			PortletPreferencesUtil.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			portletPreferences =
				new com.liferay.portal.model.impl.PortletPreferencesImpl();
		}

		String xml = deletePortletData(
			context, portletId, portletPreferences);

		if (xml != null) {
			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
	}

	protected String deletePortletData(
			PortletDataContext context, String portletId,
			PortletPreferences portletPreferences)
		throws SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
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

		PortletPreferencesImpl preferencesImpl =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				portletPreferences.getPreferences());

		try {
			preferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.deleteData(
					context, portletId, preferencesImpl);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			context.setGroupId(context.getScopeGroupId());
		}

		if (preferencesImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(preferencesImpl);
	}

	protected UserIdStrategy getUserIdStrategy(
		User user, String userIdStrategy) {

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	protected void importPortletData(
			PortletDataContext context, String portletId, long plid,
			Element portletDataRefEl)
		throws SystemException {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		PortletPreferences portletPreferences =
			PortletPreferencesUtil.fetchByO_O_P_P(
				ownerId, ownerType, plid, portletId);

		if (portletPreferences == null) {
			portletPreferences =
				new com.liferay.portal.model.impl.PortletPreferencesImpl();
		}

		String xml = importPortletData(
			context, portletId, portletPreferences, portletDataRefEl);

		if (xml != null) {
			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
	}

	protected String importPortletData(
			PortletDataContext context, String portletId,
			PortletPreferences portletPreferences, Element portletDataRefEl)
		throws SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
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

		// Layout scope

		long groupId = context.getGroupId();

		long scopeLayoutId = context.getScopeLayoutId();

		if (scopeLayoutId == 0) {
			scopeLayoutId = GetterUtil.getLong(
				portletDataRefEl.getParent().attributeValue("scope-layout-id"));
		}

		if (scopeLayoutId > 0) {
			try {
				Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
					context.getGroupId(), context.isPrivateLayout(),
					scopeLayoutId);

				Group scopeGroup = null;

				if (scopeLayout.hasScopeGroup()) {
					scopeGroup = scopeLayout.getScopeGroup();
				}
				else {
					String name = String.valueOf(scopeLayout.getPlid());

					scopeGroup = GroupLocalServiceUtil.addGroup(
						context.getUserId(null), Layout.class.getName(),
						scopeLayout.getPlid(), name, null, 0, null, true, null);
				}

				context.setGroupId(scopeGroup.getGroupId());
			}
			catch (PortalException pe) {
			}
		}

		PortletPreferencesImpl preferencesImpl = null;

		if (portletPreferences != null) {
			preferencesImpl = (PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					portletPreferences.getPreferences());
		}

		String portletData = context.getZipEntryAsString(
			portletDataRefEl.attributeValue("path"));

		try {
			SocialActivityThreadLocal.setEnabled(false);
			WorkflowThreadLocal.setEnabled(false);

			preferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.importData(
					context, portletId, preferencesImpl, portletData);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			context.setGroupId(groupId);

			SocialActivityThreadLocal.setEnabled(true);
			WorkflowThreadLocal.setEnabled(true);
		}

		if (preferencesImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(preferencesImpl);
	}

	protected void importPortletPreferences(
			PortletDataContext context, long companyId, long groupId,
			Layout layout, String portletId, Element parentEl,
			boolean importPortletSetup, boolean importPortletArchivedSetups,
			boolean importUserPreferences, boolean preserveScopeLayoutId)
		throws PortalException, SystemException {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		long plid = 0;
		long scopeLayoutId = 0;

		if (layout != null) {
			plid = layout.getPlid();

			if (preserveScopeLayoutId && (portletId != null)) {
				javax.portlet.PortletPreferences jxPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				scopeLayoutId = GetterUtil.getLong(
					jxPreferences.getValue("lfr-scope-layout-id", null));

				context.setScopeLayoutId(scopeLayoutId);
			}
		}

		List<Element> preferencesEls = parentEl.elements("portlet-preferences");

		for (Element preferencesEl : preferencesEls) {
			String path = preferencesEl.attributeValue("path");

			if (context.isPathNotProcessed(path)) {
				Element el = null;
				String xml = null;

				try {
					xml = context.getZipEntryAsString(path);

					Document preferencesDoc = SAXReaderUtil.read(xml);

					el = preferencesDoc.getRootElement();
				}
				catch (DocumentException de) {
					throw new SystemException(de);
				}

				long ownerId = GetterUtil.getLong(
					el.attributeValue("owner-id"));
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
					portletId = PortletConstants.getRootPortletId(portletId);

					String userUuid = el.attributeValue("archive-user-uuid");
					String name = el.attributeValue("archive-name");

					long userId = context.getUserId(userUuid);

					PortletItem portletItem =
						PortletItemLocalServiceUtil.updatePortletItem(
							userId, groupId, name, portletId,
							PortletPreferences.class.getName());

					plid = 0;
					ownerId = portletItem.getPortletItemId();
				}

				if (defaultUser) {
					ownerId = defaultUserId;
				}

				PortletPreferencesLocalServiceUtil.updatePreferences(
					ownerId, ownerType, plid, portletId, xml);
			}
		}

		if (preserveScopeLayoutId && (layout != null)) {
			javax.portlet.PortletPreferences jxPreferences =
				PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					layout, portletId);

			try {
				jxPreferences.setValue(
					"lfr-scope-layout-id", String.valueOf(scopeLayoutId));

				jxPreferences.store();
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
			finally {
				context.setScopeLayoutId(scopeLayoutId);
			}
		}
	}

	protected void readComments(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/comments.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String path = asset.attributeValue("path");
				String className = asset.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));

				List<String> zipFolderEntries = context.getZipFolderEntries(
					path);

				List<MBMessage> messages = new ArrayList<MBMessage>();

				for (String zipFolderEntry : zipFolderEntries) {
					MBMessage message = (MBMessage)context.getZipEntryAsObject(
						zipFolderEntry);

					if (message != null) {
						messages.add(message);
					}
				}

				context.addComments(className, classPK, messages);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readLocks(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/locks.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String className = asset.attributeValue("class-name");
				String key = asset.attributeValue("key");
				String path = asset.attributeValue("path");

				Lock lock = (Lock)context.getZipEntryAsObject(path);

				if (lock != null) {
					context.addLocks(className, key, lock);
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readRatings(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/ratings.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String path = asset.attributeValue("path");
				String className = asset.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));

				List<String> zipFolderEntries = context.getZipFolderEntries(
					path);

				List<RatingsEntry> ratingsEntries =
					new ArrayList<RatingsEntry>();

				for (String zipFolderEntry : zipFolderEntries) {
					RatingsEntry ratingsEntry =
						(RatingsEntry)context.getZipEntryAsObject(
							zipFolderEntry);

					if (ratingsEntry != null) {
						ratingsEntries.add(ratingsEntry);
					}
				}

				context.addRatingsEntries(
					className, new Long(classPK), ratingsEntries);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readCategories(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/categories.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String className = GetterUtil.getString(
					asset.attributeValue("class-name"));
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));
				String[] assetCategoryUuids = StringUtil.split(
					GetterUtil.getString(
						asset.attributeValue("category-uuids")));

				long[] assetCategoryIds = new long[0];

				for (String assetCategoryUuid : assetCategoryUuids) {
					try {
						AssetCategory assetCategory =
							AssetCategoryUtil.findByUUID_G(
								assetCategoryUuid, context.getScopeGroupId());

						assetCategoryIds = ArrayUtil.append(
							assetCategoryIds, assetCategory.getCategoryId());
					}
					catch (NoSuchCategoryException nsce) {
					}
				}

				context.addAssetCategories(
					className, new Long(classPK), assetCategoryIds);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readTags(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/tags.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String className = GetterUtil.getString(
					asset.attributeValue("class-name"));
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));
				String assetTagNames = GetterUtil.getString(
					asset.attributeValue("tags"));

				context.addAssetTags(
					className, new Long(classPK),
					StringUtil.split(assetTagNames));
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletImporter.class);

	private PermissionImporter _permissionImporter = new PermissionImporter();

}