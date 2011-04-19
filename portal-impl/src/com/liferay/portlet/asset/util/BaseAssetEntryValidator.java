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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ClassNameServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Juan Fernández
 */
public class BaseAssetEntryValidator implements AssetEntryValidator {

	@SuppressWarnings("unused")
	public void validate(
			long groupId, String className, long[] categoryIds,
			String[] entryNames)
		throws PortalException, SystemException {

		List<AssetVocabulary> groupVocabularies =
			AssetVocabularyLocalServiceUtil.getGroupVocabularies(groupId);

		groupVocabularies = ListUtil.copy(groupVocabularies);

		Group group = GroupServiceUtil.getGroup(groupId);

		if (!group.isCompany()) {
			Group companyGroup =
				GroupLocalServiceUtil.getCompanyGroup(group.getCompanyId());

			groupVocabularies.addAll(
				AssetVocabularyLocalServiceUtil.getGroupVocabularies(
					companyGroup.getGroupId()));
		}

		long classNameId = ClassNameServiceUtil.getClassNameId(className);

		for(AssetVocabulary vocabulary : groupVocabularies) {
			UnicodeProperties settingsProperties =
				vocabulary.getSettingsProperties();

			long[] selectedClassNameIds = StringUtil.split(
				settingsProperties.getProperty("selectedClassNameIds"), 0L);

			if ((selectedClassNameIds.length > 0) &&
				((selectedClassNameIds[0] == 0) ||
				ArrayUtil.contains(selectedClassNameIds, classNameId))) {

				// Check required

				long[] requiredClassNameIds = StringUtil.split(
					settingsProperties.getProperty("requiredClassNameIds"), 0L);

				List<AssetCategory> vocabularyCategories =
					AssetCategoryLocalServiceUtil.getVocabularyCategories(
						vocabulary.getVocabularyId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null);

				if ((requiredClassNameIds.length > 0) &&
					((requiredClassNameIds[0] == 0) ||
					ArrayUtil.contains(requiredClassNameIds, classNameId))) {

					boolean found = false;

					int i = 0;

					while (i < vocabularyCategories.size() && !found) {

						if (ArrayUtil.contains(categoryIds,
							vocabularyCategories.get(i).getCategoryId())) {
							found = true;
						}

						i++;
					}

					if (!found && (vocabularyCategories.size() > 0)) {
						throw new AssetCategoryException(
							AssetCategoryException.AT_LEAST_ONE_CATEGORY);
					}
				}

				// Check single valued

				boolean multiValued = GetterUtil.getBoolean(
					settingsProperties.getProperty("multiValued"), true);

				if (!multiValued) {
					ListIterator vocabularyCategoriesIterator =
						vocabularyCategories.listIterator();

					boolean existed = false;

					while (vocabularyCategoriesIterator.hasNext()) {
						AssetCategory category =
							(AssetCategory)vocabularyCategoriesIterator.next();

						if (ArrayUtil.contains(
								categoryIds, category.getCategoryId())) {

							if (!existed) {
								existed = true;
							}
							else {
								throw new AssetCategoryException(
									AssetCategoryException.TOO_MANY_CATEGORIES);
							}
						}
					}
				}
			}
		}
	}

}