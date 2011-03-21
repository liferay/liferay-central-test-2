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

import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchLayoutException;
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
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
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyUtil;
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
		throws Exception {

		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		String userIdStrategyString = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		User user = UserUtil.findByPrimaryKey(userId);

		UserIdStrategy userIdStrategy = getUserIdStrategy(
			user, userIdStrategyString);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext = new PortletDataContextImpl(
			layout.getCompanyId(), groupId, parameterMap, new HashSet<String>(),
			userIdStrategy, zipReader);

		portletDataContext.setPortetDataContextListener(
			new PortletDataContextListenerImpl(portletDataContext));

		portletDataContext.setPlid(plid);
		portletDataContext.setPrivateLayout(layout.isPrivateLayout());

		// Manifest

		String xml = portletDataContext.getZipEntryAsString("/manifest.xml");

		Element rootElement = null;

		try {
			Document document = SAXReaderUtil.read(xml);

			rootElement = document.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException("Unable to read /manifest.xml");
		}

		// Build compatibility

		Element headerElement = rootElement.element("header");

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			headerElement.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type compatibility

		String type = headerElement.attributeValue("type");

		if (!type.equals("portlet")) {
			throw new LARTypeException(
				"Invalid type of LAR file (" + type + ")");
		}

		// Portlet compatibility

		String rootPortletId = headerElement.attributeValue("root-portlet-id");

		if (!PortletConstants.getRootPortletId(portletId).equals(
				rootPortletId)) {

			throw new PortletIdException("Invalid portlet id " + rootPortletId);
		}

		// Import group id

		long sourceGroupId = GetterUtil.getLong(
			headerElement.attributeValue("group-id"));

		portletDataContext.setSourceGroupId(sourceGroupId);

		// Read asset categories, asset tags, comments, locks, and ratings
		// entries to make them available to the data handlers through the
		// context

		if (importPermissions) {
			_permissionImporter.readPortletDataPermissions(portletDataContext);
		}

		readAssetCategories(portletDataContext);
		readAssetTags(portletDataContext, rootElement);
		readComments(portletDataContext, rootElement);
		readLocks(portletDataContext, rootElement);
		readRatingsEntries(portletDataContext, rootElement);

		// Delete portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting portlet data");
		}

		if (deletePortletData) {
			deletePortletData(portletDataContext, portletId, plid);
		}

		Element portletElement = null;

		try {
			portletElement = rootElement.element("portlet");

			Document portletDocument = SAXReaderUtil.read(
				portletDataContext.getZipEntryAsString(
					portletElement.attributeValue("path")));

			portletElement = portletDocument.getRootElement();
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}

		// Portlet preferences

		importPortletPreferences(
			portletDataContext, layout.getCompanyId(), groupId, layout,
			portletId, portletElement, importPortletSetup,
			importPortletArchivedSetups, importPortletUserPreferences, true);

		// Portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Importing portlet data");
		}

		if (importPortletData) {
			Element portletDataElement = portletElement.element("portlet-data");

			if (portletDataElement != null) {
				importPortletData(
					portletDataContext, portletId, plid, portletDataElement);
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

		// Portlet permissions

		if (_log.isDebugEnabled()) {
			_log.debug("Importing portlet permissions");
		}

		if (importPermissions) {
			LayoutCache layoutCache = new LayoutCache();

			_permissionImporter.importPortletPermissions(
				layoutCache, layout.getCompanyId(), groupId, userId, layout,
				portletElement, portletId, importUserPermissions);
		}

		zipReader.close();
	}

	protected void deletePortletData(
			PortletDataContext portletDataContext, String portletId, long plid)
		throws Exception {

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
			portletDataContext, portletId, portletPreferences);

		if (xml != null) {
			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
	}

	protected String deletePortletData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDataContext.getCompanyId(), portletId);

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

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)
				PortletPreferencesFactoryUtil.fromDefaultXML(
					portletPreferences.getPreferences());

		try {
			portletPreferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.deleteData(
					portletDataContext, portletId, portletPreferencesImpl);
		}
		finally {
			portletDataContext.setGroupId(portletDataContext.getScopeGroupId());
		}

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	protected String getAssetCategoryPath(
		PortletDataContext portletDataContext, long assetCategoryId) {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getSourceRootPath());
		sb.append("/categories/");
		sb.append(assetCategoryId);
		sb.append(".xml");

		return sb.toString();
	}

	protected UserIdStrategy getUserIdStrategy(
		User user, String userIdStrategy) {

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	protected void importAssetCategory(
			PortletDataContext portletDataContext,
			Map<Long, Long> assetVocabularyPKs,
			Map<Long, Long> assetCategoryPKs, Element assetCategoryElement,
			AssetCategory assetCategory)
		throws Exception {

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());
		long assetVocabularyId = MapUtil.getLong(
			assetVocabularyPKs, assetCategory.getVocabularyId(),
			assetCategory.getVocabularyId());
		long parentAssetCategoryId = MapUtil.getLong(
			assetCategoryPKs, assetCategory.getParentCategoryId(),
			assetCategory.getParentCategoryId());

		if ((parentAssetCategoryId !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentAssetCategoryId == assetCategory.getParentCategoryId())) {

			String path = getAssetCategoryPath(
				portletDataContext, parentAssetCategoryId);

			AssetCategory parentAssetCategory =
				(AssetCategory)portletDataContext.getZipEntryAsObject(path);

			Node parentCategoryNode =
				assetCategoryElement.getParent().selectSingleNode(
					"./category[@path='" + path + "']");

			if (parentCategoryNode != null) {
				importAssetCategory(
					portletDataContext, assetVocabularyPKs, assetCategoryPKs,
					(Element)parentCategoryNode, parentAssetCategory);

				parentAssetCategoryId = MapUtil.getLong(
					assetCategoryPKs, assetCategory.getParentCategoryId(),
					assetCategory.getParentCategoryId());
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(assetCategory.getCreateDate());
		serviceContext.setModifiedDate(assetCategory.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		AssetCategory importedAssetCategory = null;

		try {
			if (parentAssetCategoryId !=
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				AssetCategoryUtil.findByPrimaryKey(parentAssetCategoryId);
			}

			List<Element> propertyElements = assetCategoryElement.elements(
				"property");

			String[] properties = new String[propertyElements.size()];

			for (int i = 0; i < propertyElements.size(); i++) {
				Element propertyElement = propertyElements.get(i);

				String key = propertyElement.attributeValue("key");
				String value = propertyElement.attributeValue("value");

				properties[i] = key.concat(StringPool.COLON).concat(value);
			}

			AssetCategory existingAssetCategory =
				AssetCategoryUtil.fetchByP_N_V(
					parentAssetCategoryId, assetCategory.getName(),
					assetVocabularyId);

			if (existingAssetCategory == null) {
				serviceContext.setUuid(assetCategory.getUuid());

				importedAssetCategory =
					AssetCategoryLocalServiceUtil.addCategory(
						userId, parentAssetCategoryId,
						assetCategory.getTitleMap(),
						assetCategory.getDescriptionMap(), assetVocabularyId,
						properties, serviceContext);
			}
			else {
				importedAssetCategory =
					AssetCategoryLocalServiceUtil.updateCategory(
						userId, existingAssetCategory.getCategoryId(),
						parentAssetCategoryId, assetCategory.getTitleMap(),
						assetCategory.getDescriptionMap(), assetVocabularyId,
						properties, serviceContext);
			}

			assetCategoryPKs.put(
				assetCategory.getCategoryId(),
				importedAssetCategory.getCategoryId());

			portletDataContext.importPermissions(
				AssetCategory.class, assetCategory.getCategoryId(),
				importedAssetCategory.getCategoryId());
		}
		catch (NoSuchCategoryException nsce) {
			_log.error(
				"Could not find the parent category for category " +
					assetCategory.getCategoryId());
		}
	}

	protected void importAssetVocabulary(
			PortletDataContext portletDataContext,
			Map<Long, Long> assetVocabularyPKs, Element assetVocabularyElement,
			AssetVocabulary assetVocabulary)
		throws Exception {

		long userId = portletDataContext.getUserId(
			assetVocabulary.getUserUuid());
		long groupId = portletDataContext.getScopeGroupId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(assetVocabulary.getCreateDate());
		serviceContext.setModifiedDate(assetVocabulary.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		AssetVocabulary importedAssetVocabulary = null;

		AssetVocabulary existingAssetVocabulary =
			AssetVocabularyUtil.fetchByG_N(groupId, assetVocabulary.getName());

		if (existingAssetVocabulary == null) {
			serviceContext.setUuid(assetVocabulary.getUuid());

			importedAssetVocabulary =
				AssetVocabularyLocalServiceUtil.addVocabulary(
					userId, assetVocabulary.getTitle(),
					assetVocabulary.getTitleMap(),
					assetVocabulary.getDescriptionMap(),
					assetVocabulary.getSettings(), serviceContext);
		}
		else {
			importedAssetVocabulary =
				AssetVocabularyLocalServiceUtil.updateVocabulary(
					existingAssetVocabulary.getVocabularyId(),
					assetVocabulary.getTitle(), assetVocabulary.getTitleMap(),
					assetVocabulary.getDescriptionMap(),
					assetVocabulary.getSettings(), serviceContext);
		}

		assetVocabularyPKs.put(
			assetVocabulary.getVocabularyId(),
			importedAssetVocabulary.getVocabularyId());

		portletDataContext.importPermissions(
			AssetVocabulary.class, assetVocabulary.getVocabularyId(),
			importedAssetVocabulary.getVocabularyId());
	}

	protected void importPortletData(
			PortletDataContext portletDataContext, String portletId, long plid,
			Element portletDataElement)
		throws Exception {

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
			portletDataContext, portletId, portletPreferences,
			portletDataElement);

		if (xml != null) {
			PortletPreferencesLocalServiceUtil.updatePreferences(
				ownerId, ownerType, plid, portletId, xml);
		}
	}

	protected String importPortletData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, Element portletDataElement)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			portletDataContext.getCompanyId(), portletId);

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

		long groupId = portletDataContext.getGroupId();

		String scopeType = portletDataContext.getScopeType();

		String scopeLayoutUuid = portletDataContext.getScopeLayoutUuid();

		if (Validator.isNull(scopeType)) {
			Element portletDataParentElement = portletDataElement.getParent();

			scopeLayoutUuid = GetterUtil.getString(
				portletDataParentElement.attributeValue("scope-layout-uuid"));
		}

		try {
			Group scopeGroup = null;

			if (scopeType.equals("company")) {
				scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
					portletDataContext.getCompanyId());
			}
			else if (Validator.isNotNull(scopeLayoutUuid)) {
				Layout scopeLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, groupId);

				if (scopeLayout.hasScopeGroup()) {
					scopeGroup = scopeLayout.getScopeGroup();
				}
				else {
					String name = String.valueOf(scopeLayout.getPlid());

					scopeGroup = GroupLocalServiceUtil.addGroup(
						portletDataContext.getUserId(null),
						Layout.class.getName(), scopeLayout.getPlid(), name,
						null, 0, null, true, null);
				}

				Group group = scopeLayout.getGroup();

				if (group.isStaged() && !group.isStagedRemotely()) {
					try {
						Layout oldLayout =
							LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
								scopeLayoutUuid,
								portletDataContext.getSourceGroupId());

						Group oldScopeGroup = oldLayout.getScopeGroup();

						oldScopeGroup.setLiveGroupId(scopeGroup.getGroupId());

						GroupLocalServiceUtil.updateGroup(oldScopeGroup, true);
					}
					catch (NoSuchLayoutException nsle) {
						if (_log.isWarnEnabled()) {
							_log.warn(nsle);
						}
					}
				}

				portletDataContext.setScopeGroupId(scopeGroup.getGroupId());
			}
		}
		catch (PortalException pe) {
		}

		PortletPreferencesImpl portletPreferencesImpl = null;

		if (portletPreferences != null) {
			portletPreferencesImpl =
				(PortletPreferencesImpl)
					PortletPreferencesFactoryUtil.fromDefaultXML(
						portletPreferences.getPreferences());
		}

		String portletData = portletDataContext.getZipEntryAsString(
			portletDataElement.attributeValue("path"));

		try {
			SocialActivityThreadLocal.setEnabled(false);
			WorkflowThreadLocal.setEnabled(false);

			portletPreferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.importData(
					portletDataContext, portletId, portletPreferencesImpl,
					portletData);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			portletDataContext.setScopeGroupId(groupId);

			SocialActivityThreadLocal.setEnabled(true);
			WorkflowThreadLocal.setEnabled(true);
		}

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	protected void importPortletPreferences(
			PortletDataContext portletDataContext, long companyId, long groupId,
			Layout layout, String portletId, Element parentElement,
			boolean importPortletSetup, boolean importPortletArchivedSetups,
			boolean importPortletUserPreferences, boolean preserveScopeLayoutId)
		throws Exception {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);
		long plid = 0;
		String scopeType = StringPool.BLANK;
		String scopeLayoutUuid = StringPool.BLANK;

		if (layout != null) {
			plid = layout.getPlid();

			if (preserveScopeLayoutId && (portletId != null)) {
				javax.portlet.PortletPreferences jxPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						layout, portletId);

				scopeType = GetterUtil.getString(
					jxPreferences.getValue("lfr-scope-type", null));
				scopeLayoutUuid = GetterUtil.getString(
					jxPreferences.getValue("lfr-scope-layout-uuid", null));

				portletDataContext.setScopeType(scopeType);
				portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}

		List<Element> portletPreferencesElements = parentElement.elements(
			"portlet-preferences");

		for (Element portletPreferencesElement : portletPreferencesElements) {
			String path = portletPreferencesElement.attributeValue("path");

			if (portletDataContext.isPathNotProcessed(path)) {
				String xml = null;

				Element element = null;

				try {
					xml = portletDataContext.getZipEntryAsString(path);

					Document preferencesDocument = SAXReaderUtil.read(xml);

					element = preferencesDocument.getRootElement();
				}
				catch (DocumentException de) {
					throw new SystemException(de);
				}

				long ownerId = GetterUtil.getLong(
					element.attributeValue("owner-id"));
				int ownerType = GetterUtil.getInteger(
					element.attributeValue("owner-type"));

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
					!importPortletUserPreferences) {

					continue;
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
					plid = PortletKeys.PREFS_PLID_SHARED;
					ownerId = portletDataContext.getGroupId();
				}

				boolean defaultUser = GetterUtil.getBoolean(
					element.attributeValue("default-user"));

				if (portletId == null) {
					portletId = element.attributeValue("portlet-id");
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
					portletId = PortletConstants.getRootPortletId(portletId);

					String userUuid = element.attributeValue(
						"archive-user-uuid");
					String name = element.attributeValue("archive-name");

					long userId = portletDataContext.getUserId(userUuid);

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
				jxPreferences.setValue("lfr-scope-type", scopeType);
				jxPreferences.setValue(
					"lfr-scope-layout-uuid", scopeLayoutUuid);

				jxPreferences.store();
			}
			finally {
				portletDataContext.setScopeType(scopeType);
				portletDataContext.setScopeLayoutUuid(scopeLayoutUuid);
			}
		}
	}

	protected void readAssetCategories(PortletDataContext portletDataContext)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			portletDataContext.getSourceRootPath() +
				"/categories-hierarchy.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		Element assetVocabulariesElement = rootElement.element("vocabularies");

		List<Element> assetVocabularyElements =
			assetVocabulariesElement.elements("vocabulary");

		Map<Long, Long> assetVocabularyPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		for (Element assetVocabularyElement : assetVocabularyElements) {
			String path = assetVocabularyElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			AssetVocabulary assetVocabulary =
				(AssetVocabulary)portletDataContext.getZipEntryAsObject(path);

			importAssetVocabulary(
				portletDataContext, assetVocabularyPKs, assetVocabularyElement,
				assetVocabulary);
		}

		Element assetCategoriesElement = rootElement.element("categories");

		List<Element> assetCategoryElements = assetCategoriesElement.elements(
			"category");

		Map<Long, Long> assetCategoryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		for (Element assetCategoryElement : assetCategoryElements) {
			String path = assetCategoryElement.attributeValue("path");

			if (!portletDataContext.isPathNotProcessed(path)) {
				continue;
			}

			AssetCategory assetCategory =
				(AssetCategory)portletDataContext.getZipEntryAsObject(path);

			importAssetCategory(
				portletDataContext, assetVocabularyPKs, assetCategoryPKs,
				assetCategoryElement, assetCategory);
		}

		Element assetsElement = rootElement.element("assets");

		List<Element> assetElements = assetsElement.elements("asset");

		for (Element assetElement : assetElements) {
			String className = GetterUtil.getString(
				assetElement.attributeValue("class-name"));
			long classPK = GetterUtil.getLong(
				assetElement.attributeValue("class-pk"));
			String[] assetCategoryUuids = StringUtil.split(
				GetterUtil.getString(
					assetElement.attributeValue("category-uuids")));

			long[] assetCategoryIds = new long[0];

			for (String assetCategoryUuid : assetCategoryUuids) {
				AssetCategory assetCategory = AssetCategoryUtil.fetchByUUID_G(
					assetCategoryUuid, portletDataContext.getScopeGroupId());

				if (assetCategory != null) {
					assetCategoryIds = ArrayUtil.append(
						assetCategoryIds, assetCategory.getCategoryId());
				}
			}

			portletDataContext.addAssetCategories(
				className, classPK, assetCategoryIds);
		}
	}

	protected void readAssetTags(
			PortletDataContext portletDataContext, Element parentElement)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			portletDataContext.getSourceRootPath() + "/tags.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetElements = rootElement.elements("asset");

		for (Element assetElement : assetElements) {
			String className = GetterUtil.getString(
				assetElement.attributeValue("class-name"));
			long classPK = GetterUtil.getLong(
				assetElement.attributeValue("class-pk"));
			String assetTagNames = GetterUtil.getString(
				assetElement.attributeValue("tags"));

			portletDataContext.addAssetTags(
				className, classPK, StringUtil.split(assetTagNames));
		}
	}

	protected void readComments(
			PortletDataContext portletDataContext, Element parentElement)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			portletDataContext.getSourceRootPath() + "/comments.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetElements = rootElement.elements("asset");

		for (Element assetElement : assetElements) {
			String path = assetElement.attributeValue("path");
			String className = assetElement.attributeValue("class-name");
			long classPK = GetterUtil.getLong(
				assetElement.attributeValue("class-pk"));

			List<String> zipFolderEntries =
				portletDataContext.getZipFolderEntries(path);

			List<MBMessage> mbMessages = new ArrayList<MBMessage>();

			for (String zipFolderEntry : zipFolderEntries) {
				MBMessage mbMessage =
					(MBMessage)portletDataContext.getZipEntryAsObject(
						zipFolderEntry);

				if (mbMessage != null) {
					mbMessages.add(mbMessage);
				}
			}

			portletDataContext.addComments(className, classPK, mbMessages);
		}
	}

	protected void readLocks(
			PortletDataContext portletDataContext, Element parentElement)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			portletDataContext.getSourceRootPath() + "/locks.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetElements = rootElement.elements("asset");

		for (Element assetElement : assetElements) {
			String path = assetElement.attributeValue("path");
			String className = assetElement.attributeValue("class-name");
			String key = assetElement.attributeValue("key");

			Lock lock = (Lock)portletDataContext.getZipEntryAsObject(path);

			if (lock != null) {
				portletDataContext.addLocks(className, key, lock);
			}
		}
	}

	protected void readRatingsEntries(
			PortletDataContext portletDataContext, Element parentElement)
		throws Exception {

		String xml = portletDataContext.getZipEntryAsString(
			portletDataContext.getSourceRootPath() + "/ratings.xml");

		if (xml == null) {
			return;
		}

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> assetElements = rootElement.elements("asset");

		for (Element assetElement : assetElements) {
			String path = assetElement.attributeValue("path");
			String className = assetElement.attributeValue("class-name");
			long classPK = GetterUtil.getLong(
				assetElement.attributeValue("class-pk"));

			List<String> zipFolderEntries =
				portletDataContext.getZipFolderEntries(path);

			List<RatingsEntry> ratingsEntries = new ArrayList<RatingsEntry>();

			for (String zipFolderEntry : zipFolderEntries) {
				RatingsEntry ratingsEntry =
					(RatingsEntry)portletDataContext.getZipEntryAsObject(
						zipFolderEntry);

				if (ratingsEntry != null) {
					ratingsEntries.add(ratingsEntry);
				}
			}

			portletDataContext.addRatingsEntries(
				className, classPK, ratingsEntries);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletImporter.class);

	private PermissionImporter _permissionImporter = new PermissionImporter();

}