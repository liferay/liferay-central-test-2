/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
public class AssetCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetCategory> {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws SystemException {

		AssetCategory category =
			AssetCategoryLocalServiceUtil.fetchAssetCategoryByUuidAndGroupId(
				uuid, groupId);

		if (category != null) {
			AssetCategoryLocalServiceUtil.deleteAssetCategory(category);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetCategory category) {
		return category.getTitleCurrentValue();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetCategory category) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(category.getCreateDate());
		serviceContext.setModifiedDate(category.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			AssetCategory parentCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					category.getParentCategoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, parentCategory,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}
		else {
			AssetVocabulary vocabulary =
				AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
					category.getVocabularyId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, vocabulary,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element categoryElement = portletDataContext.getExportDataElement(
			category);

		category.setUserUuid(category.getUserUuid());

		List<AssetCategoryProperty> categoryProperties =
			AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
				category.getCategoryId());

		for (AssetCategoryProperty categoryProperty : categoryProperties) {
			Element propertyElement = categoryElement.addElement("property");

			propertyElement.addAttribute(
				"userUuid", categoryProperty.getUserUuid());
			propertyElement.addAttribute("key", categoryProperty.getKey());
			propertyElement.addAttribute("value", categoryProperty.getValue());
		}

		String categoryPath = ExportImportPathUtil.getModelPath(category);

		categoryElement.addAttribute("path", categoryPath);

		portletDataContext.addPermissions(
			AssetCategory.class, category.getCategoryId());

		portletDataContext.addZipEntry(categoryPath, category);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		long userId = portletDataContext.getUserId(category.getUserUuid());

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, category, AssetCategory.class,
				category.getParentCategoryId());
		}
		else {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, category, AssetVocabulary.class,
				category.getVocabularyId());
		}

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		long vocabularyId = MapUtil.getLong(
			vocabularyIds, category.getVocabularyId(),
			category.getVocabularyId());

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, category.getParentCategoryId(),
			category.getParentCategoryId());

		Element categoryElement = portletDataContext.getImportDataElement(
			category);

		List<Element> propertyElements = categoryElement.elements("property");

		String[] properties = new String[propertyElements.size()];

		for (int i = 0; i < propertyElements.size(); i++) {
			Element propertyElement = propertyElements.get(i);

			String key = propertyElement.attributeValue("key");
			String value = propertyElement.attributeValue("value");

			properties[i] = key.concat(
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR).concat(
					value);
		}

		ServiceContext serviceContext = createServiceContext(
			portletDataContext, category);

		AssetCategory importedCategory = null;

		AssetCategory existingCategory = AssetCategoryUtil.fetchByUUID_G(
			category.getUuid(), portletDataContext.getScopeGroupId());

		if (existingCategory == null) {
			existingCategory = AssetCategoryUtil.fetchByUUID_G(
				category.getUuid(), portletDataContext.getCompanyGroupId());
		}

		if (existingCategory == null) {
			String name = getCategoryName(
				null, portletDataContext.getScopeGroupId(), parentCategoryId,
				category.getName(), category.getVocabularyId(), 2);

			serviceContext.setUuid(category.getUuid());

			importedCategory = AssetCategoryLocalServiceUtil.addCategory(
				userId, parentCategoryId,
				getCategoryTitleMap(
					portletDataContext.getScopeGroupId(), category, name),
				category.getDescriptionMap(), vocabularyId, properties,
				serviceContext);
		}
		else {
			String name = getCategoryName(
				category.getUuid(), portletDataContext.getScopeGroupId(),
				parentCategoryId, category.getName(),
				category.getVocabularyId(), 2);

			importedCategory = AssetCategoryLocalServiceUtil.updateCategory(
				userId, existingCategory.getCategoryId(), parentCategoryId,
				getCategoryTitleMap(
					portletDataContext.getScopeGroupId(), category, name),
				category.getDescriptionMap(), vocabularyId, properties,
				serviceContext);
		}

		categoryIds.put(
			category.getCategoryId(), importedCategory.getCategoryId());

		Map<String, String> categoryUuids =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class + ".uuid");

		categoryUuids.put(category.getUuid(), importedCategory.getUuid());

		portletDataContext.importPermissions(
			AssetCategory.class, category.getCategoryId(),
			importedCategory.getCategoryId());
	}

	protected String getCategoryName(
			String uuid, long groupId, long parentCategoryId, String name,
			long vocabularyId, int count)
		throws Exception {

		AssetCategory category = AssetCategoryUtil.fetchByG_P_N_V_First(
			groupId, parentCategoryId, name, vocabularyId, null);

		if ((category == null) ||
			(Validator.isNotNull(uuid) && uuid.equals(category.getUuid()))) {

			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getCategoryName(
			uuid, groupId, parentCategoryId, name, vocabularyId, ++count);
	}

	protected Map<Locale, String> getCategoryTitleMap(
			long groupId, AssetCategory category, String name)
		throws PortalException, SystemException {

		Map<Locale, String> titleMap = category.getTitleMap();

		if (titleMap == null) {
			titleMap = new HashMap<Locale, String>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		AssetCategory category =
			AssetCategoryLocalServiceUtil.fetchAssetCategoryByUuidAndGroupId(
				uuid, groupId);

		if (category == null) {
			return false;
		}

		return true;
	}

}