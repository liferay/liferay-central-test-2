/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.tags.DuplicateEntryException;
import com.liferay.portlet.tags.EntryNameException;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.service.base.TagsEntryLocalServiceBaseImpl;
import com.liferay.portlet.tags.util.TagsUtil;
import com.liferay.util.Autocomplete;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <a href="TagsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryLocalServiceImpl extends TagsEntryLocalServiceBaseImpl {

	public static String[] DEFAULT_PROPERTIES = new String[] {
		"0:category:no category"
	};

	public TagsEntry addEntry(long userId, String name)
		throws PortalException, SystemException {

		return addEntry(userId, name, new String[0]);
	}

	public TagsEntry addEntry(long userId, String name, String[] properties)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();
		name = name.trim().toLowerCase();

		validate(name);

		if (hasEntry(user.getCompanyId(), name)) {
			throw new DuplicateEntryException(
				"A tag entry with the name " + name + " already exists");
		}

		long entryId = counterLocalService.increment();

		TagsEntry entry = tagsEntryPersistence.create(entryId);

		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setName(name);

		tagsEntryPersistence.update(entry, false);

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
				tagsPropertyLocalService.addProperty(
					userId, entryId, key, value);
			}
		}

		return entry;

	}

	public void checkEntries(long userId, String[] names)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		for (int i = 0; i < names.length; i++) {
			String name = names[i].trim().toLowerCase();

			TagsEntry entry = tagsEntryPersistence.fetchByC_N(
				user.getCompanyId(), name);

			if (entry == null) {
				addEntry(userId, names[i], DEFAULT_PROPERTIES);
			}
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		TagsEntry entry = tagsEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(TagsEntry entry)
		throws PortalException, SystemException {

		// Properties

		tagsPropertyLocalService.deleteProperties(entry.getEntryId());

		// Entry

		tagsEntryPersistence.remove(entry.getEntryId());
	}

	public boolean hasEntry(long companyId, String name)
		throws SystemException {

		if (tagsEntryPersistence.fetchByC_N(companyId, name) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public List<TagsEntry> getAssetEntries(long assetId)
		throws SystemException {

		return tagsAssetPersistence.getTagsEntries(assetId);
	}

	public List<TagsEntry> getEntries() throws SystemException {
		return tagsEntryPersistence.findAll();
	}

	public List<TagsEntry> getEntries(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEntries(classNameId, classPK);
	}

	public List<TagsEntry> getEntries(long classNameId, long classPK)
		throws SystemException {

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			return new ArrayList<TagsEntry>();
		}
		else {
			return tagsAssetPersistence.getTagsEntries(asset.getAssetId());
		}
	}

	public List<TagsEntry> getEntries(
			long groupId, long companyId, long classNameId, String name)
		throws SystemException {

		return tagsEntryFinder.findByG_C_C_N(
			groupId, companyId, classNameId, name);
	}

	public List<TagsEntry> getEntries(
			long groupId, long companyId, long classNameId, String name,
			int start, int end)
		throws SystemException {

		return tagsEntryFinder.findByG_C_C_N(
			groupId, companyId, classNameId, name, start, end);
	}

	public int getEntriesSize(
			long groupId, long companyId, long classNameId, String name)
		throws SystemException {

		return tagsEntryFinder.countByG_C_C_N(
			groupId, companyId, classNameId, name);
	}

	public TagsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return tagsEntryPersistence.findByPrimaryKey(entryId);
	}

	public TagsEntry getEntry(long companyId, String name)
		throws PortalException, SystemException {

		return tagsEntryPersistence.findByC_N(companyId, name);
	}

	public long[] getEntryIds(long companyId, String[] names)
		throws SystemException {

		List<TagsEntry> list = new ArrayList<TagsEntry>(names.length);

		for (String name : names) {
			TagsEntry entry = tagsEntryPersistence.fetchByC_N(companyId, name);

			if (entry != null) {
				list.add(entry);
			}
		}

		long[] entryIds = new long[list.size()];

		for (int i = 0; i < list.size(); i++) {
			TagsEntry entry = list.get(i);

			entryIds[i] = entry.getEntryId();
		}

		return entryIds;
	}

	public String[] getEntryNames() throws SystemException {
		return getEntryNames(getEntries());
	}

	public String[] getEntryNames(String className, long classPK)
		throws SystemException {

		return getEntryNames(getEntries(className, classPK));
	}

	public String[] getEntryNames(long classNameId, long classPK)
		throws SystemException {

		return getEntryNames(getEntries(classNameId, classPK));
	}

	public void mergeEntries(long fromEntryId, long toEntryId)
		throws PortalException, SystemException {

		List<TagsAsset> assets = tagsEntryPersistence.getTagsAssets(
			fromEntryId);

		tagsEntryPersistence.addTagsAssets(toEntryId, assets);

		List<TagsProperty> properties = tagsPropertyPersistence.findByEntryId(
			fromEntryId);

		for (TagsProperty fromProperty : properties) {
			TagsProperty toProperty = tagsPropertyPersistence.fetchByE_K(
				toEntryId, fromProperty.getKey());

			if (toProperty == null) {
				fromProperty.setEntryId(toEntryId);

				tagsPropertyPersistence.update(fromProperty, false);
			}
		}

		deleteEntry(fromEntryId);
	}

	public List<TagsEntry> search(
			long companyId, String name, String[] properties)
		throws SystemException {

		return tagsEntryFinder.findByC_N_P(companyId, name, properties);
	}

	public List<TagsEntry> search(
			long companyId, String name, String[] properties, int start,
			int end)
		throws SystemException {

		return tagsEntryFinder.findByC_N_P(
			companyId, name, properties, start, end);
	}

	public JSONArray searchAutocomplete(
			long companyId, String name, String[] properties, int start,
			int end)
		throws SystemException {

		List<TagsEntry> list = tagsEntryFinder.findByC_N_P(
			companyId, name, properties, start, end);

		return Autocomplete.listToJson(list, "name", "name");
	}

	public int searchCount(long companyId, String name, String[] properties)
		throws SystemException {

		return tagsEntryFinder.countByC_N_P(companyId, name, properties);
	}

	public TagsEntry updateEntry(long entryId, String name)
		throws PortalException, SystemException {

		name = name.trim().toLowerCase();

		validate(name);

		TagsEntry entry = tagsEntryPersistence.findByPrimaryKey(entryId);

		if (!entry.getName().equals(name)) {
			if (hasEntry(entry.getCompanyId(), name)) {
				throw new DuplicateEntryException();
			}
		}

		entry.setModifiedDate(new Date());
		entry.setName(name);

		tagsEntryPersistence.update(entry, false);

		return entry;
	}

	public TagsEntry updateEntry(
			long userId, long entryId, String name, String[] properties)
		throws PortalException, SystemException {

		TagsEntry entry = updateEntry(entryId, name);

		Set<Long> newProperties = new HashSet<Long>();

		List<TagsProperty> oldProperties =
			tagsPropertyPersistence.findByEntryId(entryId);

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
					tagsPropertyLocalService.addProperty(
						userId, entryId, key, value);
				}
			}
			else {
				if (Validator.isNull(key)) {
					tagsPropertyLocalService.deleteProperty(propertyId);
				}
				else {
					tagsPropertyLocalService.updateProperty(
						propertyId, key, value);

					newProperties.add(propertyId);
				}
			}
		}

		for (TagsProperty property : oldProperties) {
			if (!newProperties.contains(property.getPropertyId())) {
				tagsPropertyLocalService.deleteProperty(property);
			}
		}

		return entry;
	}

	protected String[] getEntryNames(List <TagsEntry>entries) {
		return StringUtil.split(ListUtil.toString(entries, "name"));
	}

	protected void validate(String name) throws PortalException {
		if (!TagsUtil.isValidWord(name)) {
			throw new EntryNameException();
		}
	}

}