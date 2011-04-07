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

package com.liferay.portlet.dynamicdatalists.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatalists.EntryDDMStructureIdException;
import com.liferay.portlet.dynamicdatalists.EntryDuplicateEntryKeyException;
import com.liferay.portlet.dynamicdatalists.EntryEntryKeyException;
import com.liferay.portlet.dynamicdatalists.EntryNameException;
import com.liferay.portlet.dynamicdatalists.model.DDLEntry;
import com.liferay.portlet.dynamicdatalists.service.base.DDLEntryLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDLEntryLocalServiceImpl extends DDLEntryLocalServiceBaseImpl {

	public DDLEntry addEntry(
			long userId, long groupId, long ddmStructureId, String entryKey,
			boolean autoEntryKey, Map<Locale, String> nameMap,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

		entryKey = entryKey.trim().toUpperCase();

		if (autoEntryKey) {
			entryKey = String.valueOf(counterLocalService.increment());
		}

		Date now = new Date();

		validate(groupId, ddmStructureId, entryKey, autoEntryKey, nameMap);

		long entryId = counterLocalService.increment();

		DDLEntry entry = ddlEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setDDMStructureId(ddmStructureId);
		entry.setEntryKey(entryKey);
		entry.setNameMap(nameMap);
		entry.setDescription(description);

		ddlEntryPersistence.update(entry, false);

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

		return entry;
	}

	public void addEntryResources(
			DDLEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			DDLEntry.class.getName(), entry.getEntryId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addEntryResources(
			DDLEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			DDLEntry.class.getName(), entry.getEntryId(), communityPermissions,
			guestPermissions);
	}

	public void deleteEntry(DDLEntry entry)
		throws PortalException, SystemException {

		// Entry

		ddlEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), DDLEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, entry.getEntryId());

		// Entry items

		ddlEntryItemLocalService.deleteEntryItems(entry.getEntryId());
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		DDLEntry entry = ddlEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(long groupId, String entryKey)
		throws PortalException, SystemException {

		DDLEntry entry = ddlEntryPersistence.findByG_E(groupId, entryKey);

		deleteEntry(entry);
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		List<DDLEntry> entries = ddlEntryPersistence.findByGroupId(groupId);

		for (DDLEntry entry : entries) {
			deleteEntry(entry);
		}
	}

	public DDLEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return ddlEntryPersistence.findByPrimaryKey(entryId);
	}

	public DDLEntry getEntry(long groupId, String entryKey)
		throws PortalException, SystemException {

		return ddlEntryPersistence.findByG_E(groupId, entryKey);
	}

	public List<DDLEntry> getEntries(long groupId)
		throws SystemException {

		return ddlEntryPersistence.findByGroupId(groupId);
	}

	public int getEntriesCount(long groupId) throws SystemException {
		return ddlEntryPersistence.countByGroupId(groupId);
	}

	public List<DDLEntry> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddlEntryFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, long groupId, String keywords)
		throws SystemException {

		return ddlEntryFinder.countByKeywords(companyId, groupId, keywords);
	}

	public DDLEntry updateEntry(
			long groupId, long ddmStructureId, String entryKey,
			Map<Locale, String> nameMap, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		validateDDMStructureId(ddmStructureId);
		validateName(nameMap);

		DDLEntry entry = ddlEntryPersistence.findByG_E(groupId, entryKey);

		entry.setModifiedDate(serviceContext.getModifiedDate(null));
		entry.setDDMStructureId(ddmStructureId);
		entry.setNameMap(nameMap);
		entry.setDescription(description);

		ddlEntryPersistence.update(entry, false);

		return entry;
	}

	protected void validate(
			long groupId, long ddmStructureId, String entryKey,
			boolean autoEntryKey, Map<Locale, String> nameMap)
		throws PortalException, SystemException {

		validateDDMStructureId(ddmStructureId);

		if (!autoEntryKey) {
			validateEntryKey(entryKey);

			DDLEntry entry = ddlEntryPersistence.fetchByG_E(groupId, entryKey);

			if (entry != null) {
				throw new EntryDuplicateEntryKeyException();
			}
		}

		validateName(nameMap);
	}

	protected void validateDDMStructureId(long ddmStructureId)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = ddmStructurePersistence.fetchByPrimaryKey(
			ddmStructureId);

		if (ddmStructure == null) {
			throw new EntryDDMStructureIdException();
		}
	}

	protected void validateEntryKey(String entryKey)
		throws PortalException {

		if (Validator.isNull(entryKey) ||
				Validator.isNumber(entryKey) ||
				entryKey.contains(StringPool.SPACE)) {

			throw new EntryEntryKeyException();
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getDefault());

		if (Validator.isNull(name)) {
			throw new EntryNameException();
		}
	}

}