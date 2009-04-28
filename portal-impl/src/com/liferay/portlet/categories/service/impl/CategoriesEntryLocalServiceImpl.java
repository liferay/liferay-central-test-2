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

package com.liferay.portlet.categories.service.impl;

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
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.categories.service.base.CategoriesEntryLocalServiceBaseImpl;
import com.liferay.portlet.categories.model.CategoriesEntry;
import com.liferay.portlet.categories.model.CategoriesProperty;
import com.liferay.portlet.categories.model.CategoriesEntryConstants;
import com.liferay.portlet.categories.DuplicateEntryException;
import com.liferay.util.Autocomplete;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="CategoriesEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class CategoriesEntryLocalServiceImpl
	extends CategoriesEntryLocalServiceBaseImpl {

	public CategoriesEntry addEntry(
			long userId, long vocabularyId, long parentEntryId, String name,
			String[] properties, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		if (properties == null) {
			properties = new String[0];
		}

		name = name.trim();

		validate(parentEntryId, 0, name);

		Date now = new Date();

		long entryId = counterLocalService.increment();

		CategoriesEntry entry = categoriesEntryPersistence.create(entryId);

		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setName(name);

		categoriesVocabularyPersistence.findByPrimaryKey(vocabularyId);

		entry.setVocabularyId(vocabularyId);

		categoriesEntryPersistence.findByPrimaryKey(parentEntryId);

		entry.setParentEntryId(parentEntryId);

		categoriesEntryPersistence.update(entry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addEntryResources(
				entry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getCommunityPermissions(),
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
				categoriesPropertyLocalService.addProperty(
					userId, entryId, key, value);
			}
		}

		return entry;
	}

	public void addEntryResources(
			CategoriesEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			CategoriesEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			CategoriesEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			CategoriesEntry.class.getName(), entry.getEntryId(),
			communityPermissions, guestPermissions);
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		CategoriesEntry entry = categoriesEntryPersistence.findByPrimaryKey(
			entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(CategoriesEntry entry)
		throws PortalException, SystemException {

		// Properties

		categoriesPropertyLocalService.deleteProperties(entry.getEntryId());

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), CategoriesEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		categoriesEntryPersistence.remove(entry);
	}

	public void deleteVocabularyEntries(long vocabularyId)
		throws PortalException, SystemException {

		List<CategoriesEntry> entries =
			categoriesEntryPersistence.findByVocabularyId(vocabularyId);

		for (CategoriesEntry entry : entries) {
			deleteEntry(entry);
		}
	}

	public List<CategoriesEntry> getAssetEntries(long assetId)
		throws SystemException {

		return categoriesEntryFinder.findByAssetId(assetId);
	}

	public List<CategoriesEntry> getEntries() throws SystemException {

		return categoriesEntryPersistence.findAll();
	}

	public List<CategoriesEntry> getEntries(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEntries(classNameId, classPK);
	}

	public List<CategoriesEntry> getEntries(long classNameId, long classPK)
		throws SystemException {

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			return new ArrayList<CategoriesEntry>();
		}
		else {
			return getAssetEntries(asset.getAssetId());
		}
	}

//	public List<CategoriesEntry> getEntries(
//			long groupId, long classNameId, String name)
//		throws SystemException {
//
//		return categoriesEntryFinder.findByG_C_N(groupId, classNameId, name);
//	}
//
//	public List<CategoriesEntry> getEntries(
//			long groupId, long classNameId, String name, int start, int end)
//		throws SystemException {
//
//		return categoriesEntryFinder.findByG_C_N(
//			groupId, classNameId, name, start, end);
//	}
//
//	public int getEntriesSize(long groupId, long classNameId, String name)
//		throws SystemException {
//
//		return categoriesEntryFinder.countByG_C_N(
//			groupId, classNameId, name);
//	}

	public CategoriesEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return categoriesEntryPersistence.findByPrimaryKey(entryId);
	}

	public List<CategoriesEntry> getChildEntries(long parentEntryId)
		throws PortalException, SystemException {

		return categoriesEntryPersistence.findByParentId(
			parentEntryId);
	}

	public List<CategoriesEntry> getRootVocabularyEntries(long vocabularyId)
		throws PortalException, SystemException {

		return categoriesEntryPersistence.findByV_P(
			CategoriesEntryConstants.DEFAULT_PARENT_ENTRY_ID, vocabularyId);
	}

	public void mergeEntries(long fromEntryId, long toEntryId)
		throws PortalException, SystemException {

		List<TagsAsset> assets = categoriesEntryPersistence.getTagsAssets(
			fromEntryId);

		categoriesEntryPersistence.addTagsAssets(toEntryId, assets);

		List<CategoriesProperty> properties = categoriesPropertyPersistence.findByEntryId(
			fromEntryId);

		for (CategoriesProperty fromProperty : properties) {
			CategoriesProperty toProperty = categoriesPropertyPersistence.fetchByE_K(
				toEntryId, fromProperty.getKey());

			if (toProperty == null) {
				fromProperty.setEntryId(toEntryId);

				categoriesPropertyPersistence.update(fromProperty, false);
			}
		}

		deleteEntry(fromEntryId);
	}

	public JSONArray search(
			long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		List<CategoriesEntry> list = categoriesEntryFinder.findByG_N_P(
			groupId, name, properties, start, end);

		return Autocomplete.listToJson(list, "name", "name");
	}

	public CategoriesEntry updateEntry(
			long userId, long entryId, long vocabularyId, long parentEntryId,
			String name, String[] properties)
		throws PortalException, SystemException {

		// Entry

		CategoriesEntry entry = categoriesEntryPersistence.findByPrimaryKey(
			entryId);

		entry.setModifiedDate(new Date());

		categoriesVocabularyPersistence.findByPrimaryKey(vocabularyId);

		entry.setVocabularyId(vocabularyId);

		name = name.trim();

		validate(parentEntryId, entryId, name);

		entry.setName(name);
		entry.setParentEntryId(parentEntryId);

		categoriesEntryPersistence.update(entry, false);

		// Properties

		Set<Long> newProperties = new HashSet<Long>();

		List<CategoriesProperty> oldProperties =
			categoriesPropertyPersistence.findByEntryId(entryId);

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
					categoriesPropertyLocalService.addProperty(
						userId, entryId, key, value);
				}
			}
			else {
				if (Validator.isNull(key)) {
					categoriesPropertyLocalService.deleteProperty(propertyId);
				}
				else {
					categoriesPropertyLocalService.updateProperty(
						propertyId, key, value);

					newProperties.add(propertyId);
				}
			}
		}

		for (CategoriesProperty property : oldProperties) {
			if (!newProperties.contains(property.getPropertyId())) {
				categoriesPropertyLocalService.deleteProperty(property);
			}
		}

		return entry;
	}

	protected String[] getEntryNames(List <CategoriesEntry>entries) {
		return StringUtil.split(ListUtil.toString(entries, "name"));
	}

	protected void validate(long parentEntryId, long entryId, String name)
		throws PortalException, SystemException {
		List<CategoriesEntry> entries = categoriesEntryPersistence.findByP_N(
			parentEntryId, name);

		if ((entries.size() > 0) && (entries.get(0).getEntryId() != entryId)) {
			StringBuilder sb = new StringBuilder();

			sb.append("There is another category entry named ");
			sb.append(name);
			sb.append(" as a child of category ");
			sb.append(parentEntryId);

			throw new DuplicateEntryException(sb.toString());
		}
	}

}