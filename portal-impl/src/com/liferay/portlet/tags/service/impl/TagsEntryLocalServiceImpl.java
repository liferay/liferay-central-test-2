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

package com.liferay.portlet.tags.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.tags.DuplicateEntryException;
import com.liferay.portlet.tags.NoSuchEntryException;
import com.liferay.portlet.tags.NoSuchVocabularyException;
import com.liferay.portlet.tags.TagsEntryException;
import com.liferay.portlet.tags.model.TagsAsset;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsEntryConstants;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.model.TagsVocabulary;
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
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class TagsEntryLocalServiceImpl extends TagsEntryLocalServiceBaseImpl {

	public TagsEntry addEntry(
			long userId, String parentEntryName, String name,
			String vocabularyName, String[] properties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		if (Validator.isNull(vocabularyName)) {
			vocabularyName = PropsValues.TAGS_VOCABULARY_DEFAULT;
		}

		if (properties == null) {
			properties = new String[0];
		}

		Date now = new Date();

		long entryId = counterLocalService.increment();

		TagsEntry entry = tagsEntryPersistence.create(entryId);

		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);

		TagsVocabulary vocabulary = null;

		try {
			vocabulary = tagsVocabularyPersistence.findByG_N(
				groupId, vocabularyName);
		}
		catch (NoSuchVocabularyException nsve) {
			if (vocabularyName.equals(PropsValues.TAGS_VOCABULARY_DEFAULT)) {
				ServiceContext vocabularyServiceContext = new ServiceContext();

				vocabularyServiceContext.setAddCommunityPermissions(true);
				vocabularyServiceContext.setAddGuestPermissions(true);
				vocabularyServiceContext.setScopeGroupId(groupId);

				vocabulary = tagsVocabularyLocalService.addVocabulary(
					userId, vocabularyName, TagsEntryConstants.FOLKSONOMY_TAG,
					vocabularyServiceContext);
			}
			else {
				throw nsve;
			}
		}

		entry.setVocabularyId(vocabulary.getVocabularyId());

		boolean folksonomy = vocabulary.isFolksonomy();

		name = name.trim();

		if (folksonomy) {
			name = name.toLowerCase();
		}

		if (hasEntry(groupId, name, folksonomy)) {
			throw new DuplicateEntryException(
				"A tag entry with the name " + name + " already exists");
		}

		validate(name);

		entry.setName(name);

		if (Validator.isNotNull(parentEntryName)) {
			TagsEntry parentEntry = getEntry(
				groupId, parentEntryName, folksonomy);

			entry.setParentEntryId(parentEntry.getEntryId());
		}
		else {
			entry.setParentEntryId(TagsEntryConstants.DEFAULT_PARENT_ENTRY_ID);
		}

		tagsEntryPersistence.update(entry, false);

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
				tagsPropertyLocalService.addProperty(
					userId, entryId, key, value);
			}
		}

		return entry;
	}

	public void addEntryResources(
			TagsEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			TagsEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			TagsEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			TagsEntry.class.getName(), entry.getEntryId(), communityPermissions,
			guestPermissions);
	}

	public void checkEntries(long userId, long groupId, String[] names)
		throws PortalException, SystemException {

		for (String name : names) {
			try {
				getEntry(groupId, name, TagsEntryConstants.FOLKSONOMY_TAG);
			}
			catch (NoSuchEntryException nsee) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddCommunityPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(groupId);

				addEntry(
					userId, null, name, null,
					PropsValues.TAGS_PROPERTIES_DEFAULT, serviceContext);
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

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), TagsEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry

		tagsEntryPersistence.remove(entry);
	}

	public void deleteVocabularyEntries(long vocabularyId)
		throws PortalException, SystemException {

		List<TagsEntry> entries = tagsEntryPersistence.findByVocabularyId(
			vocabularyId);

		for (TagsEntry entry : entries) {
			deleteEntry(entry);
		}
	}

	public boolean hasEntry(long groupId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		try {
			getEntry(groupId, name, folksonomy);

			return true;
		}
		catch (NoSuchEntryException nsee) {
			return false;
		}
	}

	public List<TagsEntry> getAssetEntries(long assetId, boolean folksonomy)
		throws SystemException {

		return tagsEntryFinder.findByA_F(assetId, folksonomy);
	}

	public List<TagsEntry> getEntries() throws SystemException {
		return getEntries(TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public List<TagsEntry> getEntries(boolean folksonomy)
		throws SystemException {

		return tagsEntryFinder.findByFolksonomy(folksonomy);
	}

	public List<TagsEntry> getEntries(String className, long classPK)
		throws SystemException {

		return getEntries(
			className, classPK, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public List<TagsEntry> getEntries(long classNameId, long classPK)
		throws SystemException {

		return getEntries(
			classNameId, classPK, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public List<TagsEntry> getEntries(
			String className, long classPK, boolean folksonomy)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEntries(classNameId, classPK, folksonomy);
	}

	public List<TagsEntry> getEntries(
			long classNameId, long classPK, boolean folksonomy)
		throws SystemException {

		TagsAsset asset = tagsAssetPersistence.fetchByC_C(classNameId, classPK);

		if (asset == null) {
			return new ArrayList<TagsEntry>();
		}
		else {
			return getAssetEntries(asset.getAssetId(), folksonomy);
		}
	}

	public List<TagsEntry> getEntries(
			long groupId, long classNameId, String name)
		throws SystemException {

		return tagsEntryFinder.findByG_C_N_F(
			groupId, classNameId, name, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public List<TagsEntry> getEntries(
			long groupId, long classNameId, String name, int start, int end)
		throws SystemException {

		return tagsEntryFinder.findByG_C_N_F(
			groupId, classNameId, name,
			TagsEntryConstants.FOLKSONOMY_TAG, start, end);
	}

	public int getEntriesSize(long groupId, long classNameId, String name)
		throws SystemException {

		return tagsEntryFinder.countByG_C_N_F(
			groupId, classNameId, name, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public TagsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return tagsEntryPersistence.findByPrimaryKey(entryId);
	}

	public TagsEntry getEntry(long groupId, String name)
		throws PortalException, SystemException {

		return getEntry(groupId, name, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public TagsEntry getEntry(long groupId, String name, boolean folksonomy)
		throws PortalException, SystemException {

		return tagsEntryFinder.findByG_N_F(groupId, name, folksonomy);
	}

	public long[] getEntryIds(long groupId, String[] names)
		throws PortalException, SystemException {

		return getEntryIds(groupId, names, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public long[] getEntryIds(long groupId, String[] names, boolean folksonomy)
		throws PortalException, SystemException {

		List<Long> entryIds = new ArrayList<Long>(names.length);

		for (String name : names) {
			try {
				TagsEntry entry = getEntry(groupId, name, folksonomy);

				entryIds.add(entry.getEntryId());
			}
			catch (NoSuchEntryException nsee) {
			}
		}

		return ArrayUtil.toArray(entryIds.toArray(new Long[entryIds.size()]));
	}

	public String[] getEntryNames() throws SystemException {
		return getEntryNames(TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public String[] getEntryNames(String className, long classPK)
		throws SystemException {

		return getEntryNames(
			className, classPK, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public String[] getEntryNames(long classNameId, long classPK)
		throws SystemException {

		return getEntryNames(
			classNameId, classPK, TagsEntryConstants.FOLKSONOMY_TAG);
	}

	public String[] getEntryNames(boolean folksonomy) throws SystemException {
		return getEntryNames(getEntries(folksonomy));
	}

	public String[] getEntryNames(
			String className, long classPK, boolean folksonomy)
		throws SystemException {

		return getEntryNames(getEntries(className, classPK, folksonomy));
	}

	public String[] getEntryNames(
			long classNameId, long classPK, boolean folksonomy)
		throws SystemException {

		return getEntryNames(getEntries(classNameId, classPK, folksonomy));
	}

	public List<TagsEntry> getGroupVocabularyEntries(
			long groupId, String vocabularyName)
		throws PortalException, SystemException {

		TagsVocabulary vocabulary =
			tagsVocabularyLocalService.getGroupVocabulary(
				groupId, vocabularyName);

		return tagsEntryPersistence.findByVocabularyId(
			vocabulary.getVocabularyId());
	}

	public List<TagsEntry> getGroupVocabularyEntries(
			long groupId, String parentEntryName, String vocabularyName)
		throws PortalException, SystemException {

		TagsVocabulary vocabulary =
			tagsVocabularyLocalService.getGroupVocabulary(
				groupId, vocabularyName);

		TagsEntry entry = getEntry(
			groupId, parentEntryName, vocabulary.isFolksonomy());

		return tagsEntryPersistence.findByP_V(
			entry.getEntryId(), vocabulary.getVocabularyId());
	}

	public List<TagsEntry> getGroupVocabularyRootEntries(
			long groupId, String vocabularyName)
		throws PortalException, SystemException {

		TagsVocabulary vocabulary =
			tagsVocabularyLocalService.getGroupVocabulary(
				groupId, vocabularyName);

		return tagsEntryPersistence.findByP_V(
			TagsEntryConstants.DEFAULT_PARENT_ENTRY_ID,
			vocabulary.getVocabularyId());
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

	public JSONArray search(
			long groupId, String name, String[] properties, int start, int end)
		throws SystemException {

		List<TagsEntry> list = tagsEntryFinder.findByG_N_F_P(
			groupId, name, TagsEntryConstants.FOLKSONOMY_TAG, properties, start,
			end);

		return Autocomplete.listToJson(list, "name", "name");
	}

	public TagsEntry updateEntry(
			long userId, long entryId, String parentEntryName, String name,
			String vocabularyName, String[] properties)
		throws PortalException, SystemException {

		// Entry

		if (Validator.isNull(vocabularyName)) {
			vocabularyName = PropsValues.TAGS_VOCABULARY_DEFAULT;
		}

		TagsEntry entry = tagsEntryPersistence.findByPrimaryKey(entryId);

		entry.setModifiedDate(new Date());

		TagsVocabulary vocabulary = null;

		try {
			vocabulary = tagsVocabularyPersistence.findByG_N(
				entry.getGroupId(), vocabularyName);
		}
		catch (NoSuchVocabularyException nsve) {
			if (vocabularyName.equals(PropsValues.TAGS_VOCABULARY_DEFAULT)) {
				ServiceContext vocabularyServiceContext = new ServiceContext();

				vocabularyServiceContext.setAddCommunityPermissions(true);
				vocabularyServiceContext.setAddGuestPermissions(true);
				vocabularyServiceContext.setScopeGroupId(entry.getGroupId());

				vocabulary = tagsVocabularyLocalService.addVocabulary(
					userId, vocabularyName, TagsEntryConstants.FOLKSONOMY_TAG,
					vocabularyServiceContext);
			}
			else {
				throw nsve;
			}
		}

		entry.setVocabularyId(vocabulary.getVocabularyId());

		boolean folksonomy = vocabulary.isFolksonomy();

		name = name.trim();

		if (folksonomy) {
			name = name.toLowerCase();

			if (!entry.getName().equals(name) &&
				hasEntry(entry.getGroupId(), name, folksonomy)) {

				throw new DuplicateEntryException(
					"A tag entry with the name " + name + " already exists");
			}
		}

		if (!entry.getName().equals(name)) {
			try {
				TagsEntry existingEntry = getEntry(
					entry.getGroupId(), name, folksonomy);

				if (existingEntry.getEntryId() != entryId) {
					throw new DuplicateEntryException(
						"A tag entry with the name " + name +
							" already exists");
				}
			}
			catch (NoSuchEntryException nsee) {
			}
		}

		validate(name);

		entry.setName(name);

		if (Validator.isNotNull(parentEntryName)) {
			TagsEntry parentEntry = getEntry(
				entry.getGroupId(), parentEntryName, folksonomy);

			entry.setParentEntryId(parentEntry.getEntryId());
		}
		else {
			entry.setParentEntryId(TagsEntryConstants.DEFAULT_PARENT_ENTRY_ID);
		}

		tagsEntryPersistence.update(entry, false);

		// Properties

		Set<Long> newProperties = new HashSet<Long>();

		List<TagsProperty> oldProperties =
			tagsPropertyPersistence.findByEntryId(entryId);

		for (TagsProperty property : oldProperties) {
			tagsPropertyLocalService.deleteProperty(property);
		}

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

	protected String[] getEntryNames(List <TagsEntry>entries) {
		return StringUtil.split(ListUtil.toString(entries, "name"));
	}

	protected void validate(String name) throws PortalException {
		if (!TagsUtil.isValidWord(name)) {
			throw new TagsEntryException(TagsEntryException.INVALID_CHARACTER);
		}
	}

}