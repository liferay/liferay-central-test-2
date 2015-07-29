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

package com.liferay.asset.publisher.web.util;

import com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfigurationValues;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PrimitiveLongList;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletInstance;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.StrictPortletPreferencesImpl;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.ClassType;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetEntryQueryProcessor;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Provides utility methods for managing the configuration, managing scopes of
 * content, and obtaining lists of assets for the Asset Publisher portlet.
 *
 * @author Raymond Augé
 * @author Julio Camarero
 */
@Component
public class AssetPublisherUtil {

	public static final String SCOPE_ID_CHILD_GROUP_PREFIX = "ChildGroup_";

	public static final String SCOPE_ID_GROUP_PREFIX = "Group_";

	public static final String SCOPE_ID_LAYOUT_PREFIX = "Layout_";

	public static final String SCOPE_ID_LAYOUT_UUID_PREFIX = "LayoutUuid_";

	public static final String SCOPE_ID_PARENT_GROUP_PREFIX = "ParentGroup_";

	public static void addAndStoreSelection(
			PortletRequest portletRequest, String className, long classPK,
			int assetEntryOrder)
		throws Exception {

		String portletId = PortalUtil.getPortletId(portletRequest);

		String rootPortletId = PortletConstants.getRootPortletId(portletId);

		if (!rootPortletId.equals(AssetPublisherPortletKeys.ASSET_PUBLISHER)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			themeDisplay.getPlid());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences instanceof StrictPortletPreferencesImpl) {
			return;
		}

		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (selectionStyle.equals("dynamic")) {
			return;
		}

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		addSelection(
			themeDisplay, portletPreferences, portletId,
			assetEntry.getEntryId(), assetEntryOrder, className);

		portletPreferences.store();
	}

	public static void addSelection(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences, String portletId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long assetEntryId = ParamUtil.getLong(portletRequest, "assetEntryId");
		int assetEntryOrder = ParamUtil.getInteger(
			portletRequest, "assetEntryOrder");
		String assetEntryType = ParamUtil.getString(
			portletRequest, "assetEntryType");

		addSelection(
			themeDisplay, portletPreferences, portletId, assetEntryId,
			assetEntryOrder, assetEntryType);
	}

	public static void addSelection(
			ThemeDisplay themeDisplay, PortletPreferences portletPreferences,
			String portletId, long assetEntryId, int assetEntryOrder,
			String assetEntryType)
		throws Exception {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			assetEntryId);

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		String assetEntryXml = _getAssetEntryXml(
			assetEntryType, assetEntry.getClassUuid());

		if (!ArrayUtil.contains(assetEntryXmls, assetEntryXml)) {
			if (assetEntryOrder > -1) {
				assetEntryXmls[assetEntryOrder] = assetEntryXml;
			}
			else {
				assetEntryXmls = ArrayUtil.append(
					assetEntryXmls, assetEntryXml);
			}

			portletPreferences.setValues("assetEntryXml", assetEntryXmls);
		}

		long plid = themeDisplay.getRefererPlid();

		if (plid == 0) {
			plid = themeDisplay.getPlid();
		}

		List<AssetEntry> assetEntries = new ArrayList<>();

		assetEntries.add(assetEntry);

		notifySubscribers(portletPreferences, plid, portletId, assetEntries);
	}

	public static void addUserAttributes(
			User user, String[] customUserAttributeNames,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		if ((user == null) || (customUserAttributeNames.length == 0)) {
			return;
		}

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			user.getCompanyId());

		long[] allCategoryIds = assetEntryQuery.getAllCategoryIds();

		PrimitiveLongList allCategoryIdsList = new PrimitiveLongList(
			allCategoryIds.length + customUserAttributeNames.length);

		allCategoryIdsList.addAll(allCategoryIds);

		for (String customUserAttributeName : customUserAttributeNames) {
			ExpandoBridge userCustomAttributes = user.getExpandoBridge();

			Serializable userCustomFieldValue = null;

			try {
				userCustomFieldValue = userCustomAttributes.getAttribute(
					customUserAttributeName);
			}
			catch (Exception e) {
			}

			if (userCustomFieldValue == null) {
				continue;
			}

			String userCustomFieldValueString = userCustomFieldValue.toString();

			List<AssetCategory> assetCategories =
				AssetCategoryLocalServiceUtil.search(
					companyGroup.getGroupId(), userCustomFieldValueString,
					new String[0], QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (AssetCategory assetCategory : assetCategories) {
				allCategoryIdsList.add(assetCategory.getCategoryId());
			}
		}

		assetEntryQuery.setAllCategoryIds(allCategoryIdsList.getArray());
	}

	public static void checkAssetEntries() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			PortletPreferencesLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property property = PropertyFactoryUtil.forName(
						"portletId");

					PortletInstance portletInstance = new PortletInstance(
						AssetPublisherPortletKeys.ASSET_PUBLISHER,
						StringPool.PERCENT);

					dynamicQuery.add(
						property.like(portletInstance.getPortletInstanceKey()));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					_checkAssetEntries(
						(com.liferay.portal.model.PortletPreferences)object);
				}

			});

		actionableDynamicQuery.performActions();
	}

	public static long[] getAssetCategoryIds(
			PortletPreferences portletPreferences)
		throws Exception {

		long[] assetCategoryIds = new long[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Validator.equals(queryName, "assetCategories") &&
				queryContains && queryAndOperator) {

				assetCategoryIds = GetterUtil.getLongValues(queryValues);
			}
		}

		return assetCategoryIds;
	}

	public static List<AssetEntry> getAssetEntries(
			PortletPreferences portletPreferences, Layout layout,
			long scopeGroupId, int max, boolean checkPermission)
		throws PortalException {

		long[] groupIds = getGroupIds(portletPreferences, scopeGroupId, layout);

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			portletPreferences, groupIds, null, null);

		assetEntryQuery.setGroupIds(groupIds);

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue("anyAssetType", null), true);

		if (!anyAssetType) {
			long[] availableClassNameIds =
				AssetRendererFactoryRegistryUtil.getClassNameIds(
					layout.getCompanyId());

			long[] classNameIds = getClassNameIds(
				portletPreferences, availableClassNameIds);

			assetEntryQuery.setClassNameIds(classNameIds);
		}

		long[] classTypeIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classTypeIds", null));

		assetEntryQuery.setClassTypeIds(classTypeIds);

		boolean enablePermissions = GetterUtil.getBoolean(
			portletPreferences.getValue("enablePermissions", null));

		assetEntryQuery.setEnablePermissions(enablePermissions);

		assetEntryQuery.setEnd(max);

		boolean excludeZeroViewCount = GetterUtil.getBoolean(
			portletPreferences.getValue("excludeZeroViewCount", null));

		assetEntryQuery.setExcludeZeroViewCount(excludeZeroViewCount);

		boolean showOnlyLayoutAssets = GetterUtil.getBoolean(
			portletPreferences.getValue("showOnlyLayoutAssets", null));

		if (showOnlyLayoutAssets) {
			assetEntryQuery.setLayout(layout);
		}

		String orderByColumn1 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn1", "modifiedDate"));

		assetEntryQuery.setOrderByCol1(orderByColumn1);

		String orderByColumn2 = GetterUtil.getString(
			portletPreferences.getValue("orderByColumn2", "title"));

		assetEntryQuery.setOrderByCol2(orderByColumn2);

		String orderByType1 = GetterUtil.getString(
			portletPreferences.getValue("orderByType1", "DESC"));

		assetEntryQuery.setOrderByType1(orderByType1);

		String orderByType2 = GetterUtil.getString(
			portletPreferences.getValue("orderByType2", "ASC"));

		assetEntryQuery.setOrderByType2(orderByType2);

		assetEntryQuery.setStart(0);

		if (checkPermission) {
			return AssetEntryServiceUtil.getEntries(assetEntryQuery);
		}
		else {
			return AssetEntryLocalServiceUtil.getEntries(assetEntryQuery);
		}
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		List<AssetEntry> assetEntries = new ArrayList<>();

		List<String> missingAssetEntryUuids = new ArrayList<>();

		for (String assetEntryXml : assetEntryXmls) {
			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			String assetEntryUuid = rootElement.elementText("asset-entry-uuid");

			AssetEntry assetEntry = null;

			for (long groupId : groupIds) {
				assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
					groupId, assetEntryUuid);

				if (assetEntry != null) {
					break;
				}
			}

			if (assetEntry == null) {
				if (deleteMissingAssetEntries) {
					missingAssetEntryUuids.add(assetEntryUuid);
				}

				continue;
			}

			if (!assetEntry.isVisible()) {
				continue;
			}

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(
						assetEntry.getClassName());

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK());

			if (!assetRendererFactory.isActive(
					permissionChecker.getCompanyId())) {

				if (deleteMissingAssetEntries) {
					missingAssetEntryUuids.add(assetEntryUuid);
				}

				continue;
			}

			if (checkPermission &&
				(!assetRenderer.isDisplayable() ||
				 !assetRenderer.hasViewPermission(permissionChecker))) {

				continue;
			}

			assetEntries.add(assetEntry);
		}

		if (deleteMissingAssetEntries) {
			AssetPublisherUtil.removeAndStoreSelection(
				missingAssetEntryUuids, portletPreferences);

			if (!missingAssetEntryUuids.isEmpty()) {
				SessionMessages.add(
					portletRequest, "deletedMissingAssetEntries",
					missingAssetEntryUuids);
			}
		}

		return assetEntries;
	}

	public static List<AssetEntry> getAssetEntries(
			PortletRequest portletRequest,
			PortletPreferences portletPreferences,
			PermissionChecker permissionChecker, long[] groupIds,
			long[] allCategoryIds, String[] allTagNames,
			boolean deleteMissingAssetEntries, boolean checkPermission)
		throws Exception {

		List<AssetEntry> assetEntries = getAssetEntries(
			portletRequest, portletPreferences, permissionChecker, groupIds,
			deleteMissingAssetEntries, checkPermission);

		if (assetEntries.isEmpty() ||
			(ArrayUtil.isEmpty(allCategoryIds) &&
			 ArrayUtil.isEmpty(allTagNames))) {

			return assetEntries;
		}

		if (!ArrayUtil.isEmpty(allCategoryIds)) {
			assetEntries = _filterAssetCategoriesAssetEntries(
				assetEntries, allCategoryIds);
		}

		if (!ArrayUtil.isEmpty(allTagNames)) {
			assetEntries = _filterAssetTagNamesAssetEntries(
				assetEntries, allTagNames);
		}

		return assetEntries;
	}

	public static AssetEntryQuery getAssetEntryQuery(
		PortletPreferences portletPreferences, long[] scopeGroupIds,
		long[] overrideAllAssetCategoryIds, String[] overrideAllAssetTagNames) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		long[] allAssetCategoryIds = new long[0];
		long[] anyAssetCategoryIds = new long[0];
		long[] notAllAssetCategoryIds = new long[0];
		long[] notAnyAssetCategoryIds = new long[0];

		String[] allAssetTagNames = new String[0];
		String[] anyAssetTagNames = new String[0];
		String[] notAllAssetTagNames = new String[0];
		String[] notAnyAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (Validator.equals(queryName, "assetCategories")) {
				long[] assetCategoryIds = GetterUtil.getLongValues(queryValues);

				if (queryContains && queryAndOperator) {
					allAssetCategoryIds = assetCategoryIds;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetCategoryIds = assetCategoryIds;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetCategoryIds = assetCategoryIds;
				}
				else {
					notAnyAssetCategoryIds = assetCategoryIds;
				}
			}
			else {
				if (queryContains && queryAndOperator) {
					allAssetTagNames = queryValues;
				}
				else if (queryContains && !queryAndOperator) {
					anyAssetTagNames = queryValues;
				}
				else if (!queryContains && queryAndOperator) {
					notAllAssetTagNames = queryValues;
				}
				else {
					notAnyAssetTagNames = queryValues;
				}
			}
		}

		if (overrideAllAssetCategoryIds != null) {
			allAssetCategoryIds = overrideAllAssetCategoryIds;
		}

		allAssetCategoryIds = _checkAssetCategories(allAssetCategoryIds);

		assetEntryQuery.setAllCategoryIds(allAssetCategoryIds);

		if (overrideAllAssetTagNames != null) {
			allAssetTagNames = overrideAllAssetTagNames;
		}

		long[] siteGroupIds = getSiteGroupIds(scopeGroupIds);

		for (String assetTagName : allAssetTagNames) {
			long[] allAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
				siteGroupIds, assetTagName);

			assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
		}

		assetEntryQuery.setAnyCategoryIds(anyAssetCategoryIds);

		long[] anyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			siteGroupIds, anyAssetTagNames);

		assetEntryQuery.setAnyTagIds(anyAssetTagIds);

		assetEntryQuery.setNotAllCategoryIds(notAllAssetCategoryIds);

		for (String assetTagName : notAllAssetTagNames) {
			long[] notAllAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
				siteGroupIds, assetTagName);

			assetEntryQuery.addNotAllTagIdsArray(notAllAssetTagIds);
		}

		assetEntryQuery.setNotAnyCategoryIds(notAnyAssetCategoryIds);

		long[] notAnyAssetTagIds = AssetTagLocalServiceUtil.getTagIds(
			siteGroupIds, notAnyAssetTagNames);

		assetEntryQuery.setNotAnyTagIds(notAnyAssetTagIds);

		return assetEntryQuery;
	}

	public static String[] getAssetTagNames(
			PortletPreferences portletPreferences)
		throws Exception {

		String[] allAssetTagNames = new String[0];

		for (int i = 0; true; i++) {
			String[] queryValues = portletPreferences.getValues(
				"queryValues" + i, null);

			if (ArrayUtil.isEmpty(queryValues)) {
				break;
			}

			boolean queryContains = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryContains" + i, StringPool.BLANK));
			boolean queryAndOperator = GetterUtil.getBoolean(
				portletPreferences.getValue(
					"queryAndOperator" + i, StringPool.BLANK));
			String queryName = portletPreferences.getValue(
				"queryName" + i, StringPool.BLANK);

			if (!Validator.equals(queryName, "assetCategories") &&
				queryContains && queryAndOperator) {

				allAssetTagNames = queryValues;
			}
		}

		return allAssetTagNames;
	}

	public static String getClassName(
		AssetRendererFactory assetRendererFactory) {

		Class<?> clazz = assetRendererFactory.getClass();

		String className = clazz.getName();

		int pos = className.lastIndexOf(StringPool.PERIOD);

		return className.substring(pos + 1);
	}

	public static long[] getClassNameIds(
		PortletPreferences portletPreferences, long[] availableClassNameIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyAssetType", Boolean.TRUE.toString()));
		String selectionStyle = portletPreferences.getValue(
			"selectionStyle", "dynamic");

		if (anyAssetType || selectionStyle.equals("manual")) {
			return availableClassNameIds;
		}

		long defaultClassNameId = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		if (defaultClassNameId > 0) {
			return new long[] {defaultClassNameId};
		}

		long[] classNameIds = GetterUtil.getLongValues(
			portletPreferences.getValues("classNameIds", null));

		if (ArrayUtil.isNotEmpty(classNameIds)) {
			return classNameIds;
		}
		else {
			return availableClassNameIds;
		}
	}

	public static Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		List<ClassType> availableClassTypes) {

		Long[] availableClassTypeIds = new Long[availableClassTypes.size()];

		for (int i = 0; i < availableClassTypeIds.length; i++) {
			ClassType classType = availableClassTypes.get(i);

			availableClassTypeIds[i] = classType.getClassTypeId();
		}

		return getClassTypeIds(
			portletPreferences, className, availableClassTypeIds);
	}

	public static Long[] getClassTypeIds(
		PortletPreferences portletPreferences, String className,
		Long[] availableClassTypeIds) {

		boolean anyAssetType = GetterUtil.getBoolean(
			portletPreferences.getValue(
				"anyClassType" + className, Boolean.TRUE.toString()));

		if (anyAssetType) {
			return availableClassTypeIds;
		}

		long defaultClassTypeId = GetterUtil.getLong(
			portletPreferences.getValue("anyClassType" + className, null));

		if (defaultClassTypeId > 0) {
			return new Long[] {defaultClassTypeId};
		}

		Long[] classTypeIds = ArrayUtil.toArray(
			StringUtil.split(
				portletPreferences.getValue(
					"classTypeIds" + className, null), 0L));

		if (classTypeIds != null) {
			return classTypeIds;
		}
		else {
			return availableClassTypeIds;
		}
	}

	public static Map<Locale, String> getEmailAssetEntryAddedBodyMap(
		PortletPreferences portletPreferences) {

		return LocalizationUtil.getLocalizationMap(
			portletPreferences, "emailAssetEntryAddedBody",
			AssetPublisherWebConfigurationValues.EMAIL_ASSET_ENTRY_ADDED_BODY);
	}

	public static boolean getEmailAssetEntryAddedEnabled(
		PortletPreferences portletPreferences) {

		String emailAssetEntryAddedEnabled = portletPreferences.getValue(
			"emailAssetEntryAddedEnabled", StringPool.BLANK);

		if (Validator.isNotNull(emailAssetEntryAddedEnabled)) {
			return GetterUtil.getBoolean(emailAssetEntryAddedEnabled);
		}
		else {
			return
				AssetPublisherWebConfigurationValues.
					EMAIL_ASSET_ENTRY_ADDED_ENABLED;
		}
	}

	public static Map<Locale, String> getEmailAssetEntryAddedSubjectMap(
		PortletPreferences portletPreferences) {

		return LocalizationUtil.getLocalizationMap(
			portletPreferences, "emailAssetEntryAddedSubject",
			AssetPublisherWebConfigurationValues.
				EMAIL_ASSET_ENTRY_ADDED_SUBJECT);
	}

	public static Map<String, String> getEmailDefinitionTerms(
		PortletRequest portletRequest, String emailFromAddress,
		String emailFromName) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String> definitionTerms = new LinkedHashMap<>();

		definitionTerms.put(
			"[$ASSET_ENTRIES$]",
			LanguageUtil.get(themeDisplay.getLocale(), "the-list-of-assets"));
		definitionTerms.put(
			"[$COMPANY_ID$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-id-associated-with-the-assets"));
		definitionTerms.put(
			"[$COMPANY_MX$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-mx-associated-with-the-assets"));
		definitionTerms.put(
			"[$COMPANY_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-company-name-associated-with-the-assets"));
		definitionTerms.put(
			"[$FROM_ADDRESS$]", HtmlUtil.escape(emailFromAddress));
		definitionTerms.put("[$FROM_NAME$]", HtmlUtil.escape(emailFromName));

		Company company = themeDisplay.getCompany();

		definitionTerms.put("[$PORTAL_URL$]", company.getVirtualHostname());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		definitionTerms.put(
			"[$PORTLET_NAME$]", HtmlUtil.escape(portletDisplay.getTitle()));

		definitionTerms.put(
			"[$SITE_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-site-name-associated-with-the-assets"));
		definitionTerms.put(
			"[$TO_ADDRESS$]",
			LanguageUtil.get(
				themeDisplay.getLocale(),
				"the-address-of-the-email-recipient"));
		definitionTerms.put(
			"[$TO_NAME$]",
			LanguageUtil.get(
				themeDisplay.getLocale(), "the-name-of-the-email-recipient"));

		return definitionTerms;
	}

	public static String getEmailFromAddress(
		PortletPreferences portletPreferences, long companyId) {

		return PortalUtil.getEmailFromAddress(
			portletPreferences, companyId,
			AssetPublisherWebConfigurationValues.EMAIL_FROM_ADDRESS);
	}

	public static String getEmailFromName(
		PortletPreferences portletPreferences, long companyId) {

		return PortalUtil.getEmailFromName(
			portletPreferences, companyId,
			AssetPublisherWebConfigurationValues.EMAIL_FROM_NAME);
	}

	public static long getGroupIdFromScopeId(
			String scopeId, long siteGroupId, boolean privateLayout)
		throws PortalException {

		if (scopeId.startsWith(SCOPE_ID_CHILD_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_CHILD_GROUP_PREFIX.length());

			long childGroupId = GetterUtil.getLong(scopeIdSuffix);

			Group childGroup = GroupLocalServiceUtil.getGroup(childGroupId);

			if (!childGroup.hasAncestor(siteGroupId)) {
				throw new PrincipalException();
			}

			return childGroupId;
		}
		else if (scopeId.startsWith(SCOPE_ID_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_GROUP_PREFIX.length());

			if (scopeIdSuffix.equals(GroupConstants.DEFAULT)) {
				return siteGroupId;
			}

			long scopeGroupId = GetterUtil.getLong(scopeIdSuffix);

			Group scopeGroup = GroupLocalServiceUtil.fetchGroup(scopeGroupId);

			if (scopeGroup == null) {
				throw new PrincipalException();
			}

			return scopeGroupId;
		}
		else if (scopeId.startsWith(SCOPE_ID_LAYOUT_UUID_PREFIX)) {
			String layoutUuid = scopeId.substring(
				SCOPE_ID_LAYOUT_UUID_PREFIX.length());

			Layout scopeIdLayout =
				LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
					layoutUuid, siteGroupId, privateLayout);

			Group scopeIdGroup = null;

			if (scopeIdLayout.hasScopeGroup()) {
				scopeIdGroup = scopeIdLayout.getScopeGroup();
			}
			else {
				Map<Locale, String> nameMap = new HashMap<>();

				nameMap.put(
					LocaleUtil.getDefault(),
					String.valueOf(scopeIdLayout.getPlid()));

				scopeIdGroup = GroupLocalServiceUtil.addGroup(
					PrincipalThreadLocal.getUserId(),
					GroupConstants.DEFAULT_PARENT_GROUP_ID,
					Layout.class.getName(), scopeIdLayout.getPlid(),
					GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null, 0,
					true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null,
					false, true, null);
			}

			return scopeIdGroup.getGroupId();
		}
		else if (scopeId.startsWith(SCOPE_ID_LAYOUT_PREFIX)) {

			// Legacy portlet preferences

			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_LAYOUT_PREFIX.length());

			long scopeIdLayoutId = GetterUtil.getLong(scopeIdSuffix);

			Layout scopeIdLayout = LayoutLocalServiceUtil.getLayout(
				siteGroupId, privateLayout, scopeIdLayoutId);

			Group scopeIdGroup = scopeIdLayout.getScopeGroup();

			return scopeIdGroup.getGroupId();
		}
		else if (scopeId.startsWith(SCOPE_ID_PARENT_GROUP_PREFIX)) {
			String scopeIdSuffix = scopeId.substring(
				SCOPE_ID_PARENT_GROUP_PREFIX.length());

			long parentGroupId = GetterUtil.getLong(scopeIdSuffix);

			Group parentGroup = GroupLocalServiceUtil.getGroup(parentGroupId);

			if (!SitesUtil.isContentSharingWithChildrenEnabled(parentGroup)) {
				throw new PrincipalException();
			}

			Group group = GroupLocalServiceUtil.getGroup(siteGroupId);

			if (!group.hasAncestor(parentGroupId)) {
				throw new PrincipalException();
			}

			return parentGroupId;
		}
		else {
			throw new IllegalArgumentException("Invalid scope ID " + scopeId);
		}
	}

	public static long[] getGroupIds(
		PortletPreferences portletPreferences, long scopeGroupId,
		Layout layout) {

		String[] scopeIds = portletPreferences.getValues(
			"scopeIds", new String[] {SCOPE_ID_GROUP_PREFIX + scopeGroupId});

		List<Long> groupIds = new ArrayList<>();

		for (String scopeId : scopeIds) {
			try {
				long groupId = getGroupIdFromScopeId(
					scopeId, scopeGroupId, layout.isPrivateLayout());

				groupIds.add(groupId);
			}
			catch (Exception e) {
				continue;
			}
		}

		return ArrayUtil.toLongArray(groupIds);
	}

	public static String getScopeId(Group group, long scopeGroupId)
		throws PortalException {

		String key = null;

		if (group.isLayout()) {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				group.getClassPK());

			key = SCOPE_ID_LAYOUT_UUID_PREFIX + layout.getUuid();
		}
		else if (group.isLayoutPrototype() ||
				 (group.getGroupId() == scopeGroupId)) {

			key = SCOPE_ID_GROUP_PREFIX + GroupConstants.DEFAULT;
		}
		else {
			Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);

			if (scopeGroup.hasAncestor(group.getGroupId()) &&
				SitesUtil.isContentSharingWithChildrenEnabled(group)) {

				key = SCOPE_ID_PARENT_GROUP_PREFIX + group.getGroupId();
			}
			else if (group.hasAncestor(scopeGroup.getGroupId())) {
				key = SCOPE_ID_CHILD_GROUP_PREFIX + group.getGroupId();
			}
			else {
				key = SCOPE_ID_GROUP_PREFIX + group.getGroupId();
			}
		}

		return key;
	}

	public static long getSubscriptionClassPK(long plid, String portletId)
		throws PortalException {

		com.liferay.portal.model.PortletPreferences portletPreferencesModel =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);

		return portletPreferencesModel.getPortletPreferencesId();
	}

	public static boolean isScopeIdSelectable(
			PermissionChecker permissionChecker, String scopeId,
			long companyGroupId, Layout layout)
		throws PortalException {

		long groupId = getGroupIdFromScopeId(
			scopeId, layout.getGroupId(), layout.isPrivateLayout());

		if (scopeId.startsWith(SCOPE_ID_CHILD_GROUP_PREFIX)) {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (!group.hasAncestor(layout.getGroupId())) {
				return false;
			}
		}
		else if (scopeId.startsWith(SCOPE_ID_PARENT_GROUP_PREFIX)) {
			Group siteGroup = layout.getGroup();

			if (!siteGroup.hasAncestor(groupId)) {
				return false;
			}

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			if (SitesUtil.isContentSharingWithChildrenEnabled(group)) {
				return true;
			}

			if (!PrefsPropsUtil.getBoolean(
					layout.getCompanyId(),
					PropsKeys.
						SITES_CONTENT_SHARING_THROUGH_ADMINISTRATORS_ENABLED)) {

				return false;
			}

			return GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.UPDATE);
		}
		else if (groupId != companyGroupId) {
			return GroupPermissionUtil.contains(
				permissionChecker, groupId, ActionKeys.UPDATE);
		}

		return true;
	}

	public static boolean isSubscribed(
			long companyId, long userId, long plid, String portletId)
		throws PortalException {

		return SubscriptionLocalServiceUtil.isSubscribed(
			companyId, userId,
			com.liferay.portal.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	public static void notifySubscribers(
			PortletPreferences portletPreferences, long plid, String portletId,
			List<AssetEntry> assetEntries)
		throws PortalException {

		if (!getEmailAssetEntryAddedEnabled(portletPreferences) ||
			assetEntries.isEmpty()) {

			return;
		}

		AssetEntry assetEntry = assetEntries.get(0);

		String fromName = getEmailFromName(
			portletPreferences, assetEntry.getCompanyId());
		String fromAddress = getEmailFromAddress(
			portletPreferences, assetEntry.getCompanyId());

		Map<Locale, String> localizedSubjectMap =
			getEmailAssetEntryAddedSubjectMap(portletPreferences);
		Map<Locale, String> localizedBodyMap = getEmailAssetEntryAddedBodyMap(
			portletPreferences);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setCompanyId(assetEntry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$ASSET_ENTRIES$]",
			ListUtil.toString(
				assetEntries, _titleAccessor, StringPool.COMMA_AND_SPACE));
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(localizedBodyMap);
		subscriptionSender.setLocalizedSubjectMap(localizedSubjectMap);
		subscriptionSender.setMailId("asset_entry", assetEntry.getEntryId());
		subscriptionSender.setPortletId(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);
		subscriptionSender.setReplyToAddress(fromAddress);

		subscriptionSender.addPersistedSubscribers(
			com.liferay.portal.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));

		subscriptionSender.flushNotificationsAsync();
	}

	public static void processAssetEntryQuery(
			User user, PortletPreferences portletPreferences,
			AssetEntryQuery assetEntryQuery)
		throws Exception {

		for (AssetEntryQueryProcessor assetEntryQueryProcessor :
				_instance._assetEntryQueryProcessors) {

			assetEntryQueryProcessor.processAssetEntryQuery(
				user, portletPreferences, assetEntryQuery);
		}
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void registerAssetQueryProcessor(
		String assetQueryProcessorClassName,
		AssetEntryQueryProcessor assetQueryProcessor) {
	}

	public static void removeAndStoreSelection(
			List<String> assetEntryUuids, PortletPreferences portletPreferences)
		throws Exception {

		if (assetEntryUuids.isEmpty()) {
			return;
		}

		String[] assetEntryXmls = portletPreferences.getValues(
			"assetEntryXml", new String[0]);

		List<String> assetEntryXmlsList = ListUtil.fromArray(assetEntryXmls);

		Iterator<String> itr = assetEntryXmlsList.iterator();

		while (itr.hasNext()) {
			String assetEntryXml = itr.next();

			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			String assetEntryUuid = rootElement.elementText("asset-entry-uuid");

			if (assetEntryUuids.contains(assetEntryUuid)) {
				itr.remove();
			}
		}

		portletPreferences.setValues(
			"assetEntryXml",
			assetEntryXmlsList.toArray(new String[assetEntryXmlsList.size()]));

		portletPreferences.store();
	}

	public static void subscribe(
			PermissionChecker permissionChecker, long groupId, long plid,
			String portletId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		PortletPermissionUtil.check(
			permissionChecker, 0, layout, portletId, ActionKeys.SUBSCRIBE,
			false, false);

		SubscriptionLocalServiceUtil.addSubscription(
			permissionChecker.getUserId(), groupId,
			com.liferay.portal.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static void unregisterAssetQueryProcessor(
		String assetQueryProcessorClassName) {
	}

	public static void unsubscribe(
			PermissionChecker permissionChecker, long plid, String portletId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

		PortletPermissionUtil.check(
			permissionChecker, 0, layout, portletId, ActionKeys.SUBSCRIBE,
			false, false);

		SubscriptionLocalServiceUtil.deleteSubscription(
			permissionChecker.getUserId(),
			com.liferay.portal.model.PortletPreferences.class.getName(),
			getSubscriptionClassPK(plid, portletId));
	}

	protected static long[] getSiteGroupIds(long[] groupIds) {
		Set<Long> siteGroupIds = new HashSet<>();

		for (long groupId : groupIds) {
			siteGroupIds.add(PortalUtil.getSiteGroupId(groupId));
		}

		return ArrayUtil.toLongArray(siteGroupIds);
	}

	@Activate
	protected void activate() {
		_instance = this;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		_assetEntryQueryProcessors.add(assetEntryQueryProcessor);
	}

	protected void unsetAssetEntryQueryProcessor(
		AssetEntryQueryProcessor assetEntryQueryProcessor) {

		_assetEntryQueryProcessors.remove(assetEntryQueryProcessor);
	}

	private static long[] _checkAssetCategories(long[] assetCategoryIds) {
		List<Long> assetCategoryIdsList = new ArrayList<>();

		for (long assetCategoryId : assetCategoryIds) {
			AssetCategory category =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					assetCategoryId);

			if (category == null) {
				continue;
			}

			assetCategoryIdsList.add(assetCategoryId);
		}

		return ArrayUtil.toArray(
			assetCategoryIdsList.toArray(
				new Long[assetCategoryIdsList.size()]));
	}

	private static void _checkAssetEntries(
			com.liferay.portal.model.PortletPreferences
				portletPreferencesModel)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.getLayout(
			portletPreferencesModel.getPlid());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				layout.getCompanyId(), portletPreferencesModel.getOwnerId(),
				portletPreferencesModel.getOwnerType(),
				portletPreferencesModel.getPlid(),
				portletPreferencesModel.getPortletId(),
				portletPreferencesModel.getPreferences());

		if (!getEmailAssetEntryAddedEnabled(portletPreferences)) {
			return;
		}

		List<AssetEntry> assetEntries = getAssetEntries(
			portletPreferences, layout, layout.getGroupId(),
			AssetPublisherWebConfigurationValues.DYNAMIC_SUBSCRIPTION_LIMIT,
			false);

		if (assetEntries.isEmpty()) {
			return;
		}

		long[] notifiedAssetEntryIds = GetterUtil.getLongValues(
			portletPreferences.getValues("notifiedAssetEntryIds", null));

		List<AssetEntry> newAssetEntries = new ArrayList<>();

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			if (!ArrayUtil.contains(
					notifiedAssetEntryIds, assetEntry.getEntryId())) {

				newAssetEntries.add(assetEntry);
			}
		}

		notifySubscribers(
			portletPreferences, portletPreferencesModel.getPlid(),
			portletPreferencesModel.getPortletId(), newAssetEntries);

		try {
			portletPreferences.setValues(
				"notifiedAssetEntryIds",
				StringUtil.split(
					ListUtil.toString(
						assetEntries, AssetEntry.ENTRY_ID_ACCESSOR)));

			portletPreferences.store();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PortletException pe) {
			throw new SystemException(pe);
		}
	}

	private static List<AssetEntry> _filterAssetCategoriesAssetEntries(
			List<AssetEntry> assetEntries, long[] assetCategoryIds)
		throws Exception {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			if (ArrayUtil.containsAll(
					assetEntry.getCategoryIds(), assetCategoryIds)) {

				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	private static List<AssetEntry> _filterAssetTagNamesAssetEntries(
			List<AssetEntry> assetEntries, String[] assetTagNames)
		throws Exception {

		List<AssetEntry> filteredAssetEntries = new ArrayList<>();

		for (AssetEntry assetEntry : assetEntries) {
			List<AssetTag> assetTags = assetEntry.getTags();

			String[] assetEntryAssetTagNames = new String[assetTags.size()];

			for (int i = 0; i < assetTags.size(); i++) {
				AssetTag assetTag = assetTags.get(i);

				assetEntryAssetTagNames[i] = assetTag.getName();
			}

			if (ArrayUtil.containsAll(assetEntryAssetTagNames, assetTagNames)) {
				filteredAssetEntries.add(assetEntry);
			}
		}

		return filteredAssetEntries;
	}

	private static String _getAssetEntryXml(
		String assetEntryType, String assetEntryUuid) {

		String xml = null;

		try {
			Document document = SAXReaderUtil.createDocument(StringPool.UTF8);

			Element assetEntryElement = document.addElement("asset-entry");

			Element assetEntryTypeElement = assetEntryElement.addElement(
				"asset-entry-type");

			assetEntryTypeElement.addText(assetEntryType);

			Element assetEntryUuidElement = assetEntryElement.addElement(
				"asset-entry-uuid");

			assetEntryUuidElement.addText(assetEntryUuid);

			xml = document.formattedString(StringPool.BLANK);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe);
			}
		}

		return xml;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetPublisherUtil.class);

	private static AssetPublisherUtil _instance;

	private static final Accessor<AssetEntry, String> _titleAccessor =
		new Accessor<AssetEntry, String>() {

			@Override
			public String get(AssetEntry assetEntry) {
				return assetEntry.getTitle(LocaleUtil.getSiteDefault());
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<AssetEntry> getTypeClass() {
				return AssetEntry.class;
			}

		};

	private final List<AssetEntryQueryProcessor>
		_assetEntryQueryProcessors = new CopyOnWriteArrayList<>();

}