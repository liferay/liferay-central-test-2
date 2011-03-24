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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.StorageFieldNameException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public abstract class BaseStorageAdapter implements StorageAdapter {

	public long create(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException {

		try {
			validateStructureFields(structureId, fields);

			return doCreate(companyId, structureId, fields, serviceContext);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void deleteByClass(long classPK) throws StorageException {
		try {
			doDeleteByClass(classPK);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void deleteByStructure(long structureId) throws StorageException {
		try {
			doDeleteByStructure(structureId);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public Fields getFields(long classPK) throws StorageException {
		return getFields(classPK, null);
	}

	public Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException {

		try {
			DDMStorageLink storageLink =
				DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

			Map<Long, Fields> fieldsMapByClasses = getFieldsMap(
				storageLink.getStructureId(), new long[] {classPK}, fieldNames);

			return fieldsMapByClasses.get(classPK);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public List<Fields> getFieldsList(long structureId, List<String> fieldNames)
		throws StorageException {

		return getFieldsList(structureId, fieldNames, null);
	}

	public List<Fields> getFieldsList(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		try {
			return doGetFieldsListByStructure(
				structureId, fieldNames, orderByComparator);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public List<Fields> getFieldsList(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException {

		try {
			return doGetFieldsListByClasses(
				structureId, classPKs, fieldNames, orderByComparator);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public List<Fields> getFieldsList(
			long structureId, long[] classPKs,
			OrderByComparator orderByComparator)
		throws StorageException {

		return getFieldsList(structureId, classPKs, null, orderByComparator);
	}

	public Map<Long, Fields> getFieldsMap(long structureId, long[] classPKs)
		throws StorageException {

		return getFieldsMap(structureId, classPKs, null);
	}

	public Map<Long, Fields> getFieldsMap(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws StorageException {

		try {
			return doGetFieldsMapByClasses(structureId, classPKs, fieldNames);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public List<Fields> query(
			long structureId, List<String> fieldNames, Condition whereCondition,
			OrderByComparator orderByComparator)
		throws StorageException {

		try {
			return doQuery(
				structureId, fieldNames, whereCondition, orderByComparator);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public int queryCount(long structureId, Condition whereCondition)
		throws StorageException {

		try {
			return doQueryCount(structureId, whereCondition);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException {

		update(classPK, fields, serviceContext, false);
	}

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws StorageException {

		try {
			validateClassFields(classPK, fields);

			doUpdate(classPK, fields, serviceContext, merge);
		}
		catch (StorageException se) {
			throw se;
		}
		catch (Exception e) {
			throw new StorageException(e);
		}
	}

	protected abstract long doCreate(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception;

	protected abstract void doDeleteByClass(long classPK) throws Exception;

	protected abstract void doDeleteByStructure(long structureId)
		throws Exception;

	protected abstract List<Fields> doGetFieldsListByClasses(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception;

	protected abstract List<Fields> doGetFieldsListByStructure(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception;

	protected abstract Map<Long, Fields> doGetFieldsMapByClasses(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws Exception;

	protected abstract  List<Fields> doQuery(
			long structureId, List<String> fieldNames, Condition whereCondition,
			OrderByComparator orderByComparator)
		throws Exception;

	protected abstract int doQueryCount(
			long structureId, Condition whereCondition)
		throws Exception;

	protected abstract void doUpdate(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws Exception;

	protected void validateClassFields(long classPK, Fields fields)
		throws PortalException, SystemException {

		DDMStorageLink storageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(classPK);

		validateStructureFields(storageLink.getStructureId(), fields);
	}

	protected void validateStructureFields(long structureId, Fields fields)
		throws PortalException, SystemException {

		DDMStructure structure = DDMStructureLocalServiceUtil.getDDMStructure(
			structureId);

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			if (!structure.hasField(field.getName())) {
				throw new StorageFieldNameException();
			}
		}
	}

}