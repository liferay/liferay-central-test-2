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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.ListDuplicateListKeyException;
import com.liferay.portlet.dynamicdatamapping.ListListKeyException;
import com.liferay.portlet.dynamicdatamapping.ListNameException;
import com.liferay.portlet.dynamicdatamapping.ListStructureIdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMList;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMListLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDMListLocalServiceImpl extends DDMListLocalServiceBaseImpl {

	public DDMList addList(
			long userId, long groupId, String listKey, boolean autoListKey,
			Map<Locale, String> nameMap, String description, long structureId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		listKey = listKey.trim().toUpperCase();

		if (autoListKey) {
			listKey = String.valueOf(counterLocalService.increment());
		}

		Date now = new Date();

		validate(groupId, listKey, autoListKey, nameMap, structureId);

		long listId = counterLocalService.increment();

		DDMList list = ddmListPersistence.create(listId);

		list.setUuid(serviceContext.getUuid());
		list.setGroupId(groupId);
		list.setCompanyId(user.getCompanyId());
		list.setUserId(user.getUserId());
		list.setUserName(user.getFullName());
		list.setCreateDate(serviceContext.getCreateDate(now));
		list.setModifiedDate(serviceContext.getModifiedDate(now));
		list.setListKey(listKey);
		list.setNameMap(nameMap);
		list.setDescription(description);
		list.setStructureId(structureId);

		ddmListPersistence.update(list, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addListResources(
				list, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addListResources(
				list, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return list;
	}

	public void addListResources(
			DDMList list, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			list.getCompanyId(), list.getGroupId(), list.getUserId(),
			DDMList.class.getName(), list.getListId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addListResources(
			DDMList list, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			list.getCompanyId(), list.getGroupId(), list.getUserId(),
			DDMList.class.getName(), list.getListId(), communityPermissions,
			guestPermissions);
	}

	public void deleteList(DDMList list)
		throws PortalException, SystemException {

		// Dynamic data mapping list

		ddmListPersistence.remove(list);

		// Dynamic data mapping list entries

		ddmListEntryLocalService.deleteListEntries(list.getListId());

		// Resources

		resourceLocalService.deleteResource(
			list.getCompanyId(), DDMList.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, list.getListId());
	}

	public void deleteList(long listId)
		throws PortalException, SystemException {

		DDMList list = ddmListPersistence.findByPrimaryKey(listId);

		deleteList(list);
	}

	public void deleteList(long groupId, String listKey)
		throws PortalException, SystemException {

		DDMList list = ddmListPersistence.findByG_L(groupId, listKey);

		deleteList(list);
	}

	public void deleteLists(long groupId)
		throws PortalException, SystemException {

		List<DDMList> lists = ddmListPersistence.findByGroupId(groupId);

		for (DDMList list : lists) {
			deleteList(list);
		}
	}

	public DDMList getList(long listId)
		throws PortalException, SystemException {

		return ddmListPersistence.findByPrimaryKey(listId);
	}

	public DDMList getList(long groupId, String listKey)
		throws PortalException, SystemException {

		return ddmListPersistence.findByG_L(groupId, listKey);
	}

	public List<DDMList> getLists(long groupId)
		throws SystemException {

		return ddmListPersistence.findByGroupId(groupId);
	}

	public int getListsCount(long groupId) throws SystemException {
		return ddmListPersistence.countByGroupId(groupId);
	}

	public List<DDMList> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddmListFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, long groupId, String keywords)
		throws SystemException {

		return ddmListFinder.countByKeywords(companyId, groupId, keywords);
	}

	public DDMList updateList(
			long groupId, String listKey, Map<Locale, String> nameMap,
			String description, long structureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(nameMap, structureId);

		DDMList list = ddmListPersistence.findByG_L(groupId, listKey);

		list.setModifiedDate(serviceContext.getModifiedDate(null));
		list.setNameMap(nameMap);
		list.setDescription(description);
		list.setStructureId(structureId);

		ddmListPersistence.update(list, false);

		return list;
	}

	protected void validate(
			long groupId, String listKey, boolean autoListKey,
			Map<Locale, String> nameMap, long structureId)
		throws PortalException, SystemException {

		if (!autoListKey) {
			validateListKey(listKey);

			DDMList list = ddmListPersistence.fetchByG_L(groupId, listKey);

			if (list != null) {
				throw new ListDuplicateListKeyException();
			}
		}

		validate(nameMap, structureId);
	}

	protected void validate(Map<Locale, String> nameMap, long structureId)
		throws PortalException, SystemException {

		validateName(nameMap);
		validateStructureId(structureId);
	}

	protected void validateListKey(String listKey)
		throws PortalException {

		if (Validator.isNull(listKey) ||
				Validator.isNumber(listKey) ||
				listKey.contains(StringPool.SPACE)) {

			throw new ListListKeyException();
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getDefault());

		if (Validator.isNull(name)) {
			throw new ListNameException();
		}
	}

	protected void validateStructureId(long structureId)
		throws PortalException, SystemException {

		DDMStructure structure = ddmStructurePersistence.fetchByPrimaryKey(
			structureId);

		if (structure == null) {
			throw new ListStructureIdException();
		}
	}

}