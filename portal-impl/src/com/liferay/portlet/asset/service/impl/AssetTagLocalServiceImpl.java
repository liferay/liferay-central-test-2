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
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
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
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.asset.DuplicateTagException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.base.AssetTagLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.util.Autocomplete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="AssetTagLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alvaro del Castillo
 * @author Jorge Ferrer
 * @author Bruno Farache
 */
public class AssetTagLocalServiceImpl extends AssetTagLocalServiceBaseImpl {

	public AssetTag addTag(
			long userId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Tag

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		if (tagProperties == null) {
			tagProperties = new String[0];
		}

		Date now = new Date();

		long tagId = counterLocalService.increment();

		AssetTag tag = assetTagPersistence.create(tagId);

		tag.setGroupId(groupId);
		tag.setCompanyId(user.getCompanyId());
		tag.setUserId(user.getUserId());
		tag.setUserName(user.getFullName());
		tag.setCreateDate(now);
		tag.setModifiedDate(now);

		name = name.trim();
		name = name.toLowerCase();

		if (hasTag(groupId, name)) {
			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addTagResources(
				tag, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addTagResources(
				tag, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Properties

		for (int i = 0; i < tagProperties.length; i++) {
			String[] tagProperty = StringUtil.split(
				tagProperties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (tagProperty.length > 1) {
				key = GetterUtil.getString(tagProperty[1]);
			}

			String value = StringPool.BLANK;

			if (tagProperty.length > 2) {
				value = GetterUtil.getString(tagProperty[2]);
			}

			if (Validator.isNotNull(key)) {
				assetTagPropertyLocalService.addTagProperty(
					userId, tagId, key, value);
			}
		}

		return tag;
	}

	public void addTagResources(
			AssetTag tag, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addTagResources(
			AssetTag tag, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			tag.getCompanyId(), tag.getGroupId(), tag.getUserId(),
			AssetTag.class.getName(), tag.getTagId(), communityPermissions,
			guestPermissions);
	}

	public void checkTags(long userId, long groupId, String[] names)
		throws PortalException, SystemException {

		for (String name : names) {
			try {
				getTag(groupId, name);
			}
			catch (NoSuchTagException nste) {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddCommunityPermissions(true);
				serviceContext.setAddGuestPermissions(true);
				serviceContext.setScopeGroupId(groupId);

				addTag(
					userId, name, PropsValues.ASSET_TAG_PROPERTIES_DEFAULT,
					serviceContext);
			}
		}
	}

	public AssetTag decrementAssetCount(long tagId, long classNameId)
		throws PortalException, SystemException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(Math.max(0, tag.getAssetCount() - 1));

		assetTagPersistence.update(tag, false);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	public void deleteTag(AssetTag tag)
		throws PortalException, SystemException {

		// Entries

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			tag.getTagId());

		// Tag

		assetTagPersistence.remove(tag);

		// Resources

		resourceLocalService.deleteResource(
			tag.getCompanyId(), AssetTag.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, tag.getTagId());

		// Properties

		assetTagPropertyLocalService.deleteTagProperties(tag.getTagId());

		// Indexer

		reIndex(entries);
	}

	public void deleteTag(long tagId) throws PortalException, SystemException {
		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		deleteTag(tag);
	}

	public List<AssetTag> getEntryTags(long entryId) throws SystemException {
		return assetTagFinder.findByEntryId(entryId);
	}

	public List<AssetTag> getGroupTags(long groupId) throws SystemException {
		return assetTagPersistence.findByGroupId(groupId);
	}

	public AssetTag getTag(long tagId) throws PortalException, SystemException {
		return assetTagPersistence.findByPrimaryKey(tagId);
	}

	public AssetTag getTag(long groupId, String name)
		throws PortalException, SystemException {

		return assetTagFinder.findByG_N(groupId, name);
	}

	public long[] getTagIds(long groupId, String[] names)
		throws PortalException, SystemException {

		List<Long> tagIds = new ArrayList<Long>(names.length);

		for (String name : names) {
			try {
				AssetTag tag = getTag(groupId, name);

				tagIds.add(tag.getTagId());
			}
			catch (NoSuchTagException nste) {
			}
		}

		return ArrayUtil.toArray(tagIds.toArray(new Long[tagIds.size()]));
	}

	public String[] getTagNames() throws SystemException {
		return getTagNames(getTags());
	}

	public String[] getTagNames(long classNameId, long classPK)
		throws SystemException {

		return getTagNames(getTags(classNameId, classPK));
	}

	public String[] getTagNames(String className, long classPK)
		throws SystemException {

		return getTagNames(getTags(className, classPK));
	}

	public List<AssetTag> getTags() throws SystemException {
		return getTags();
	}

	public List<AssetTag> getTags(long classNameId, long classPK)
		throws SystemException {

		return assetTagFinder.findByC_C(classNameId, classPK);
	}

	public List<AssetTag> getTags(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.findByG_C_N(groupId, classNameId, name);
	}

	public List<AssetTag> getTags(
			long groupId, long classNameId, String name, int start, int end)
		throws SystemException {

		return assetTagFinder.findByG_C_N(
			groupId, classNameId, name, start, end);
	}

	public List<AssetTag> getTags(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTags(classNameId, classPK);
	}

	public int getTagsSize(long groupId, long classNameId, String name)
		throws SystemException {

		return assetTagFinder.countByG_C_N(groupId, classNameId, name);
	}

	public boolean hasTag(long groupId, String name)
		throws PortalException, SystemException {

		try {
			getTag(groupId, name);

			return true;
		}
		catch (NoSuchTagException nste) {
			return false;
		}
	}

	public AssetTag incrementAssetCount(long tagId, long classNameId)
		throws PortalException, SystemException {

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setAssetCount(tag.getAssetCount() + 1);

		assetTagPersistence.update(tag, false);

		assetTagStatsLocalService.updateTagStats(tagId, classNameId);

		return tag;
	}

	public void mergeTags(long fromTagId, long toTagId)
		throws PortalException, SystemException {

		List<AssetEntry> entries = assetTagPersistence.getAssetEntries(
			fromTagId);

		assetTagPersistence.addAssetEntries(toTagId, entries);

		List<AssetTagProperty> tagProperties =
			assetTagPropertyPersistence.findByTagId(fromTagId);

		for (AssetTagProperty fromTagProperty : tagProperties) {
			AssetTagProperty toTagProperty =
				assetTagPropertyPersistence.fetchByT_K(
					toTagId, fromTagProperty.getKey());

			if (toTagProperty == null) {
				fromTagProperty.setTagId(toTagId);

				assetTagPropertyPersistence.update(fromTagProperty, false);
			}
		}

		deleteTag(fromTagId);
	}

	public JSONArray search(
			long groupId, String name, String[] tagProperties, int start,
			int end)
		throws SystemException {

		List<AssetTag> list = assetTagFinder.findByG_N_P(
			groupId, name, tagProperties, start, end);

		return Autocomplete.listToJson(list, "name", "name");
	}

	public AssetTag updateTag(
			long userId, long tagId, String name, String[] tagProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Tag

		AssetTag tag = assetTagPersistence.findByPrimaryKey(tagId);

		tag.setModifiedDate(new Date());

		name = name.trim();
		name = name.toLowerCase();

		if (tagProperties == null) {
			tagProperties = new String[0];
		}

		if (!tag.getName().equals(name) &&
			hasTag(tag.getGroupId(), name)) {

			throw new DuplicateTagException(
				"A tag with the name " + name + " already exists");
		}

		if (!tag.getName().equals(name)) {
			try {
				AssetTag existingAssetTag = getTag(tag.getGroupId(), name);

				if (existingAssetTag.getTagId() != tagId) {
					throw new DuplicateTagException(
						"A tag with the name " + name + " already exists");
				}
			}
			catch (NoSuchTagException nste) {
			}
		}

		validate(name);

		tag.setName(name);

		assetTagPersistence.update(tag, false);

		// Properties

		List<AssetTagProperty> oldTagProperties =
			assetTagPropertyPersistence.findByTagId(tagId);

		for (AssetTagProperty tagProperty : oldTagProperties) {
			assetTagPropertyLocalService.deleteTagProperty(tagProperty);
		}

		for (int i = 0; i < tagProperties.length; i++) {
			String[] tagProperty = StringUtil.split(
				tagProperties[i], StringPool.COLON);

			String key = StringPool.BLANK;

			if (tagProperty.length > 0) {
				key = GetterUtil.getString(tagProperty[0]);
			}

			String value = StringPool.BLANK;

			if (tagProperty.length > 1) {
				value = GetterUtil.getString(tagProperty[1]);
			}

			if (Validator.isNotNull(key)) {
				assetTagPropertyLocalService.addTagProperty(
					userId, tagId, key, value);
			}
		}

		return tag;
	}

	protected String[] getTagNames(List <AssetTag>tags) {
		return StringUtil.split(ListUtil.toString(tags, "name"));
	}

	protected void reIndex(List<AssetEntry> entries) throws PortalException {
		for (AssetEntry entry : entries) {
			String className = PortalUtil.getClassName(entry.getClassNameId());

			Indexer indexer = IndexerRegistryUtil.getIndexer(className);

			indexer.reIndex(className, entry.getClassPK());
		}
	}

	protected void validate(String name) throws PortalException {
		if (!AssetUtil.isValidWord(name)) {
			throw new AssetTagException(AssetTagException.INVALID_CHARACTER);
		}
	}

}