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
		boolean importUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
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

		context.setPortetDataContextListener(
			new PortletDataContextListenerImpl(context));

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

		if (importPermissions) {
			_permissionImporter.readPortletDataPermissions(context);
		}

		readCategories(context);
		readComments(context, root);
		readLocks(context, root);
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

		// Portlet permissions

		if (_log.isDebugEnabled()) {
			_log.debug("Importing portlet permissions");
		}

		if (importPermissions) {
			LayoutCache layoutCache = new LayoutCache();

			_permissionImporter.importPortletPermissions(
				layoutCache, layout.getCompanyId(), groupId, userId, layout,
				portletEl, portletId, importUserPermissions);
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

		PortletPreferencesImpl portletPreferencesImpl =
			(PortletPreferencesImpl)
				PortletPreferencesFactoryUtil.fromDefaultXML(
					portletPreferences.getPreferences());

		try {
			portletPreferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.deleteData(
					context, portletId, portletPreferencesImpl);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			context.setGroupId(context.getScopeGroupId());
		}

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	protected String getCategoryPath(
		PortletDataContext context, long categoryId) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getSourceRootPath());
		sb.append("/categories/");
		sb.append(categoryId);
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

	protected void importCategory(
			PortletDataContext context, Map<Long, Long> vocabularyPKs,
			Map<Long, Long> categoryPKs, Element categoryEl,
			AssetCategory category)
		throws Exception {

		long userId = context.getUserId(category.getUserUuid());
		long vocabularyId = MapUtil.getLong(
			vocabularyPKs, category.getVocabularyId(),
			category.getVocabularyId());
		long parentCategoryId = MapUtil.getLong(
			categoryPKs, category.getParentCategoryId(),
			category.getParentCategoryId());

		if ((parentCategoryId !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId == category.getParentCategoryId())) {

			String path = getCategoryPath(context, parentCategoryId);

			AssetCategory parentCategory =
				(AssetCategory)context.getZipEntryAsObject(path);

			Node parentCategoryNode = categoryEl.getParent().selectSingleNode(
				"./category[@path='" + path + "']");

			if (parentCategoryNode != null) {
				importCategory(
					context, vocabularyPKs, categoryPKs,
					(Element)parentCategoryNode, parentCategory);

				parentCategoryId = MapUtil.getLong(
					categoryPKs, category.getParentCategoryId(),
					category.getParentCategoryId());
			}
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(category.getCreateDate());
		serviceContext.setModifiedDate(category.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		AssetCategory importedCategory = null;

		try {
			if (parentCategoryId !=
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				AssetCategoryUtil.findByPrimaryKey(parentCategoryId);
			}

			List<Element> propertyEls = categoryEl.elements("property");

			String[] properties = new String[propertyEls.size()];

			for (int i = 0; i < properties.length; i++) {
				Element propertyEl = propertyEls.get(i);
				String key = propertyEl.attributeValue("key");
				String value = propertyEl.attributeValue("value");

				properties[i] = key.concat(StringPool.COLON).concat(value);
			}

			AssetCategory existingCategory = AssetCategoryUtil.fetchByP_N_V(
				parentCategoryId, category.getName(), vocabularyId);

			if (existingCategory == null) {
				serviceContext.setUuid(category.getUuid());

				importedCategory = AssetCategoryLocalServiceUtil.addCategory(
					userId, parentCategoryId, category.getTitleMap(),
					category.getDescriptionMap(), vocabularyId, properties,
					serviceContext);
			}
			else {
				importedCategory = AssetCategoryLocalServiceUtil.updateCategory(
					userId, existingCategory.getCategoryId(), parentCategoryId,
					category.getTitleMap(), category.getDescriptionMap(),
					vocabularyId, properties, serviceContext);
			}

			categoryPKs.put(
				category.getCategoryId(), importedCategory.getCategoryId());

			context.importPermissions(
				AssetCategory.class, category.getCategoryId(),
				importedCategory.getCategoryId());
		}
		catch (NoSuchCategoryException nsce) {
			_log.error(
				"Could not find the parent category for category " +
					category.getCategoryId());
		}
	}

	protected void importVocabulary(
			PortletDataContext context, Map<Long, Long> vocabularyPKs,
			Element vocabularyEl, AssetVocabulary vocabulary)
		throws Exception {

		long userId = context.getUserId(vocabulary.getUserUuid());
		long groupId = context.getScopeGroupId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(vocabulary.getCreateDate());
		serviceContext.setModifiedDate(vocabulary.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		AssetVocabulary importedVocabulary = null;

		AssetVocabulary existingVocabulary = AssetVocabularyUtil.fetchByG_N(
			groupId, vocabulary.getName());

		if (existingVocabulary == null) {
			serviceContext.setUuid(vocabulary.getUuid());

			importedVocabulary = AssetVocabularyLocalServiceUtil.addVocabulary(
				userId, vocabulary.getTitle(), vocabulary.getTitleMap(),
				vocabulary.getDescriptionMap(), vocabulary.getSettings(),
				serviceContext);
		}
		else {
			importedVocabulary =
				AssetVocabularyLocalServiceUtil.updateVocabulary(
					existingVocabulary.getVocabularyId(), vocabulary.getTitle(),
					vocabulary.getTitleMap(), vocabulary.getDescriptionMap(),
					vocabulary.getSettings(),serviceContext);
		}

		vocabularyPKs.put(
			vocabulary.getVocabularyId(), importedVocabulary.getVocabularyId());

		context.importPermissions(
			AssetVocabulary.class, vocabulary.getVocabularyId(),
			importedVocabulary.getVocabularyId());
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

		String scopeType = context.getScopeType();

		String scopeLayoutUuid = context.getScopeLayoutUuid();

		if (Validator.isNull(scopeType)) {
			scopeLayoutUuid = GetterUtil.getString(
				portletDataRefEl.getParent().attributeValue(
					"scope-layout-uuid"));
		}

		try {
			Group scopeGroup = null;

			if (scopeType.equals("company")) {
				scopeGroup = GroupLocalServiceUtil.getCompanyGroup(
					context.getCompanyId());
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
						context.getUserId(null), Layout.class.getName(),
						scopeLayout.getPlid(), name, null, 0, null, true, null);
				}

				Group group = scopeLayout.getGroup();

				if (group.isStaged() && !group.isStagedRemotely()) {
					try {
						Layout oldLayout =
							LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
								scopeLayoutUuid, context.getSourceGroupId());

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

				context.setScopeGroupId(scopeGroup.getGroupId());
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

		String portletData = context.getZipEntryAsString(
			portletDataRefEl.attributeValue("path"));

		try {
			SocialActivityThreadLocal.setEnabled(false);
			WorkflowThreadLocal.setEnabled(false);

			portletPreferencesImpl =
				(PortletPreferencesImpl)portletDataHandler.importData(
					context, portletId, portletPreferencesImpl, portletData);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new SystemException(e);
		}
		finally {
			context.setScopeGroupId(groupId);

			SocialActivityThreadLocal.setEnabled(true);
			WorkflowThreadLocal.setEnabled(true);
		}

		if (portletPreferencesImpl == null) {
			return null;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferencesImpl);
	}

	protected void importPortletPreferences(
			PortletDataContext context, long companyId, long groupId,
			Layout layout, String portletId, Element parentEl,
			boolean importPortletSetup, boolean importPortletArchivedSetups,
			boolean importUserPreferences, boolean preserveScopeLayoutId)
		throws PortalException, SystemException {

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

				context.setScopeType(scopeType);
				context.setScopeLayoutUuid(scopeLayoutUuid);
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
				jxPreferences.setValue("lfr-scope-type", scopeType);
				jxPreferences.setValue(
					"lfr-scope-layout-uuid", scopeLayoutUuid);

				jxPreferences.store();
			}
			catch (Exception e) {
				throw new PortalException(e);
			}
			finally {
				context.setScopeType(scopeType);
				context.setScopeLayoutUuid(scopeLayoutUuid);
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

				context.addRatingsEntries(className, classPK, ratingsEntries);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readCategories(PortletDataContext context)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getSourceRootPath() + "/categories-hierarchy.xml");

			if (xml == null) {
				return;
			}

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<Element> vocabularyEls = root.element("vocabularies").elements(
				"vocabulary");

			Map<Long, Long> vocabularyPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(
					AssetVocabulary.class);

			for (Element vocabularyEl : vocabularyEls) {
				String path = vocabularyEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				AssetVocabulary vocabulary =
					(AssetVocabulary)context.getZipEntryAsObject(path);

				importVocabulary(
					context, vocabularyPKs, vocabularyEl, vocabulary);
			}

			List<Element> categoryEls = root.element("categories").elements(
				"category");

			Map<Long, Long> categoryPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(
					AssetCategory.class);

			for (Element categoryEl : categoryEls) {
				String path = categoryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				AssetCategory category =
					(AssetCategory)context.getZipEntryAsObject(path);

				importCategory(
					context, vocabularyPKs, categoryPKs, categoryEl, category);
			}

			List<Element> assets = root.element("assets").elements("asset");

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
					AssetCategory assetCategory =
						AssetCategoryUtil.fetchByUUID_G(
							assetCategoryUuid, context.getScopeGroupId());

					if (assetCategory != null) {
						assetCategoryIds = ArrayUtil.append(
							assetCategoryIds, assetCategory.getCategoryId());
					}
				}

				context.addAssetCategories(
					className, classPK, assetCategoryIds);
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

				context.addAssetTags(className, classPK,
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