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
import com.liferay.portlet.dynamicdatalists.RecordSetDDMStructureIdException;
import com.liferay.portlet.dynamicdatalists.RecordSetDuplicateRecordSetKeyException;
import com.liferay.portlet.dynamicdatalists.RecordSetNameException;
import com.liferay.portlet.dynamicdatalists.RecordSetRecordSetKeyException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.base.DDLRecordSetLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public class DDLRecordSetLocalServiceImpl
	extends DDLRecordSetLocalServiceBaseImpl {

	public DDLRecordSet addRecordSet(
			long userId, long groupId, long ddmStructureId, String recordSetKey,
			boolean autoRecordSetKey, Map<Locale, String> nameMap,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Record set

		User user = userPersistence.findByPrimaryKey(userId);

		recordSetKey = recordSetKey.trim().toUpperCase();

		if (autoRecordSetKey) {
			recordSetKey = String.valueOf(counterLocalService.increment());
		}

		Date now = new Date();

		validate(
			groupId, ddmStructureId, recordSetKey, autoRecordSetKey, nameMap);

		long recordSetId = counterLocalService.increment();

		DDLRecordSet recordSet = ddlRecordSetPersistence.create(recordSetId);

		recordSet.setUuid(serviceContext.getUuid());
		recordSet.setGroupId(groupId);
		recordSet.setCompanyId(user.getCompanyId());
		recordSet.setUserId(user.getUserId());
		recordSet.setUserName(user.getFullName());
		recordSet.setCreateDate(serviceContext.getCreateDate(now));
		recordSet.setModifiedDate(serviceContext.getModifiedDate(now));
		recordSet.setDDMStructureId(ddmStructureId);
		recordSet.setRecordSetKey(recordSetKey);
		recordSet.setNameMap(nameMap);
		recordSet.setDescription(description);

		ddlRecordSetPersistence.update(recordSet, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addRecordSetResources(
				recordSet, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addRecordSetResources(
				recordSet, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return recordSet;
	}

	public void addRecordSetResources(
			DDLRecordSet recordSet, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			recordSet.getCompanyId(), recordSet.getGroupId(),
			recordSet.getUserId(), DDLRecordSet.class.getName(),
			recordSet.getRecordSetId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addRecordSetResources(
			DDLRecordSet recordSet, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			recordSet.getCompanyId(), recordSet.getGroupId(),
			recordSet.getUserId(), DDLRecordSet.class.getName(),
			recordSet.getRecordSetId(), communityPermissions, guestPermissions);
	}

	public void deleteRecordSet(DDLRecordSet recordSet)
		throws PortalException, SystemException {

		// Record set

		ddlRecordSetPersistence.remove(recordSet);

		// Resources

		resourceLocalService.deleteResource(
			recordSet.getCompanyId(), DDLRecordSet.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, recordSet.getRecordSetId());

		// Records

		ddlRecordLocalService.deleteRecords(recordSet.getRecordSetId());
	}

	public void deleteRecordSet(long recordSetId)
		throws PortalException, SystemException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByPrimaryKey(
			recordSetId);

		deleteRecordSet(recordSet);
	}

	public void deleteRecordSet(long groupId, String recordSetKey)
		throws PortalException, SystemException {

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByG_R(
			groupId, recordSetKey);

		deleteRecordSet(recordSet);
	}

	public void deleteRecordSets(long groupId)
		throws PortalException, SystemException {

		List<DDLRecordSet> recordSets = ddlRecordSetPersistence.findByGroupId(
			groupId);

		for (DDLRecordSet recordSet : recordSets) {
			deleteRecordSet(recordSet);
		}
	}

	public DDLRecordSet getRecordSet(long recordSetId)
		throws PortalException, SystemException {

		return ddlRecordSetPersistence.findByPrimaryKey(recordSetId);
	}

	public DDLRecordSet getRecordSet(long groupId, String recordSetKey)
		throws PortalException, SystemException {

		return ddlRecordSetPersistence.findByG_R(groupId, recordSetKey);
	}

	public List<DDLRecordSet> getRecordSets(long groupId)
		throws SystemException {

		return ddlRecordSetPersistence.findByGroupId(groupId);
	}

	public int getRecordSetsCount(long groupId) throws SystemException {
		return ddlRecordSetPersistence.countByGroupId(groupId);
	}

	public List<DDLRecordSet> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddlRecordSetFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	public List<DDLRecordSet> search(
			long companyId, long groupId, String recordSetKey, String name,
			String description, boolean andOperator, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return ddlRecordSetFinder.findByC_G_R_N_D(
			companyId, groupId, recordSetKey, name, description, andOperator,
			start, end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return ddlRecordSetFinder.countByKeywords(companyId, groupId, keywords);
	}

	public int searchCount(
			long companyId, long groupId, String recordSetKey, String name,
			String description, boolean andOperator)
		throws SystemException {

		return ddlRecordSetFinder.countByC_G_R_N_D(
			companyId, groupId, recordSetKey, name, description, andOperator);
	}

	public DDLRecordSet updateRecordSet(
			long groupId, long ddmStructureId, String recordSetKey,
			Map<Locale, String> nameMap, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		validateDDMStructureId(ddmStructureId);
		validateName(nameMap);

		DDLRecordSet recordSet = ddlRecordSetPersistence.findByG_R(
			groupId, recordSetKey);

		recordSet.setModifiedDate(serviceContext.getModifiedDate(null));
		recordSet.setDDMStructureId(ddmStructureId);
		recordSet.setNameMap(nameMap);
		recordSet.setDescription(description);

		ddlRecordSetPersistence.update(recordSet, false);

		return recordSet;
	}

	protected void validate(
			long groupId, long ddmStructureId, String recordSetKey,
			boolean autoRecordSetKey, Map<Locale, String> nameMap)
		throws PortalException, SystemException {

		validateDDMStructureId(ddmStructureId);

		if (!autoRecordSetKey) {
			validateRecordSetKey(recordSetKey);

			DDLRecordSet recordSet = ddlRecordSetPersistence.fetchByG_R(
				groupId, recordSetKey);

			if (recordSet != null) {
				throw new RecordSetDuplicateRecordSetKeyException();
			}
		}

		validateName(nameMap);
	}

	protected void validateDDMStructureId(long ddmStructureId)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = ddmStructurePersistence.fetchByPrimaryKey(
			ddmStructureId);

		if (ddmStructure == null) {
			throw new RecordSetDDMStructureIdException();
		}
	}

	protected void validateName(Map<Locale, String> nameMap)
		throws PortalException {

		String name = nameMap.get(LocaleUtil.getDefault());

		if (Validator.isNull(name)) {
			throw new RecordSetNameException();
		}
	}

	protected void validateRecordSetKey(String recordSetKey)
		throws PortalException {

		if (Validator.isNull(recordSetKey) ||
				Validator.isNumber(recordSetKey) ||
				recordSetKey.contains(StringPool.SPACE)) {

			throw new RecordSetRecordSetKeyException();
		}
	}

}