/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.DuplicateCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.base.AssetCategoryLocalServiceBaseImpl;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.util.Autocomplete;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="AssetCategoryLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class AssetCategoryLocalServiceImpl
	extends AssetCategoryLocalServiceBaseImpl {

	public AssetCategory addCategory(
			long userId, long parentCategoryId, String name, long vocabularyId,
			String[] properties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Category

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		name = name.trim();

		if (properties == null) {
			properties = new String[0];
		}

		Date now = new Date();

		validate(0, parentCategoryId, name);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		assetCategoryVocabularyPersistence.findByPrimaryKey(vocabularyId);

		long categoryId = counterLocalService.increment();

		AssetCategory category = assetCategoryPersistence.create(categoryId);

		category.setGroupId(groupId);
		category.setCompanyId(user.getCompanyId());
		category.setUserId(user.getUserId());
		category.setUserName(user.getFullName());
		category.setCreateDate(now);
		category.setModifiedDate(now);
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setVocabularyId(vocabularyId);

		assetCategoryPersistence.update(category, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addCategoryResources(
				category, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addCategoryResources(
				category, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Properties

		for (int i = 0; i < properties.length; i++) {
			String[] property = StringUtil.split(
				properties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (property.length > 1) {
				key = GetterUtil.getString(property[1]);
			}

			String value = StringPool.BLANK;

			if (property.length > 2) {
				value = GetterUtil.getString(property[2]);
			}

			if (Validator.isNotNull(key)) {
				assetCategoryPropertyLocalService.addCategoryProperty(
					userId, categoryId, key, value);
			}
		}

		return category;
	}

	public void addCategoryResources(
			AssetCategory category, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addCategoryResources(
			AssetCategory category, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			category.getCompanyId(), category.getGroupId(),
			category.getUserId(), AssetCategory.class.getName(),
			category.getCategoryId(), communityPermissions, guestPermissions);
	}

	public void deleteCategory(AssetCategory category)
		throws PortalException, SystemException {

		// Properties

		assetCategoryPropertyLocalService.deleteCategoryProperties(
			category.getCategoryId());

		// Resources

		resourceLocalService.deleteResource(
			category.getCompanyId(), AssetCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, category.getCategoryId());

		// Category

		assetCategoryPersistence.remove(category);
	}

	public void deleteCategory(long categoryId)
		throws PortalException, SystemException {

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		deleteCategory(category);
	}

	public void deleteVocabularyCategories(long vocabularyId)
		throws PortalException, SystemException {

		List<AssetCategory> categories =
			assetCategoryPersistence.findByVocabularyId(vocabularyId);

		for (AssetCategory category : categories) {
			deleteCategory(category);
		}
	}

	public List<AssetCategory> getAssetCategories(long assetId)
		throws SystemException {

		return assetCategoryFinder.findByAssetId(assetId);
	}

	public List<AssetCategory> getCategories() throws SystemException {
		return assetCategoryPersistence.findAll();
	}

	public List<AssetCategory> getCategories(long classNameId, long classPK)
		throws SystemException {

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			return new ArrayList<AssetCategory>();
		}
		else {
			return getAssetCategories(asset.getAssetId());
		}
	}

	public List<AssetCategory> getCategories(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getCategories(classNameId, classPK);
	}

	public AssetCategory getCategory(long categoryId)
		throws PortalException, SystemException {

		return assetCategoryPersistence.findByPrimaryKey(categoryId);
	}

	public List<AssetCategory> getChildCategories(long parentCategoryId)
		throws SystemException {

		return assetCategoryPersistence.findByParentCategoryId(
			parentCategoryId);
	}

	public List<AssetCategory> getVocabularyCategories(long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.findByVocabularyId(vocabularyId);
	}

	public List<AssetCategory> getVocabularyRootCategories(long vocabularyId)
		throws SystemException {

		return assetCategoryPersistence.findByP_V(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, vocabularyId);
	}

	public void mergeCategories(long fromCategoryId, long toCategoryId)
		throws PortalException, SystemException {

		List<TagsAsset> assets = assetCategoryPersistence.getTagsAssets(
			fromCategoryId);

		assetCategoryPersistence.addTagsAssets(toCategoryId, assets);

		List<AssetCategoryProperty> properties =
			assetCategoryPropertyPersistence.findByCategoryId(fromCategoryId);

		for (AssetCategoryProperty fromProperty : properties) {
			AssetCategoryProperty toProperty =
				assetCategoryPropertyPersistence.fetchByCA_K(
					toCategoryId, fromProperty.getKey());

			if (toProperty == null) {
				fromProperty.setCategoryId(toCategoryId);

				assetCategoryPropertyPersistence.update(fromProperty, false);
			}
		}

		deleteCategory(fromCategoryId);
	}

	public JSONArray search(
			long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		List<AssetCategory> list = assetCategoryFinder.findByG_N_P(
			groupId, name, properties, start, end);

		return Autocomplete.listToJson(list, "name", "name");
	}

	public AssetCategory updateCategory(
			long userId, long categoryId, long parentCategoryId, String name,
			long vocabularyId, String[] properties)
		throws PortalException, SystemException {

		// Category

		name = name.trim();

		validate(categoryId, parentCategoryId, name);

		if (parentCategoryId > 0) {
			assetCategoryPersistence.findByPrimaryKey(parentCategoryId);
		}

		assetCategoryVocabularyPersistence.findByPrimaryKey(vocabularyId);

		AssetCategory category = assetCategoryPersistence.findByPrimaryKey(
			categoryId);

		category.setModifiedDate(new Date());
		category.setParentCategoryId(parentCategoryId);
		category.setName(name);
		category.setVocabularyId(vocabularyId);

		assetCategoryPersistence.update(category, false);

		// Properties

		Set<Long> newProperties = new HashSet<Long>();

		List<AssetCategoryProperty> oldProperties =
			assetCategoryPropertyPersistence.findByCategoryId(categoryId);

		for (int i = 0; i < properties.length; i++) {
			String[] property = StringUtil.split(
				properties[i], StringPool.COLON);

			long propertyId = 0;

			if (property.length > 0) {
				propertyId = GetterUtil.getLong(property[0]);
			}

			String key = StringPool.BLANK;

			if (property.length > 1) {
				key = GetterUtil.getString(property[1]);
			}

			String value = StringPool.BLANK;

			if (property.length > 2) {
				value = GetterUtil.getString(property[2]);
			}

			if (propertyId == 0) {
				if (Validator.isNotNull(key)) {
					assetCategoryPropertyLocalService.addCategoryProperty(
						userId, categoryId, key, value);
				}
			}
			else {
				if (Validator.isNull(key)) {
					assetCategoryPropertyLocalService.deleteCategoryProperty(
						propertyId);
				}
				else {
					assetCategoryPropertyLocalService.updateCategoryProperty(
						propertyId, key, value);

					newProperties.add(propertyId);
				}
			}
		}

		for (AssetCategoryProperty property : oldProperties) {
			if (!newProperties.contains(property.getCategoryPropertyId())) {
				assetCategoryPropertyLocalService.deleteCategoryProperty(
					property);
			}
		}

		return category;
	}

	protected String[] getCategoryNames(List <AssetCategory>categories) {
		return StringUtil.split(ListUtil.toString(categories, "name"));
	}

	protected void validate(long categoryId, long parentCategoryId, String name)
		throws PortalException, SystemException {

		List<AssetCategory> categories = assetCategoryPersistence.findByP_N(
			parentCategoryId, name);

		if ((categories.size() > 0) &&
			(categories.get(0).getCategoryId() != categoryId)) {

			StringBuilder sb = new StringBuilder();

			sb.append("There is another category category named ");
			sb.append(name);
			sb.append(" as a child of category ");
			sb.append(parentCategoryId);

			throw new DuplicateCategoryException(sb.toString());
		}
	}

}