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

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.StorageException;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public interface StorageEngine {

	public long create(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws StorageException;

	public void deleteByClass(long classPK) throws StorageException;

	public void deleteByStructure(long structureId) throws StorageException;

	public Fields getFields(long classPK) throws StorageException;

	public Fields getFields(long classPK, List<String> fieldNames)
		throws StorageException;

	public List<Fields> getFieldsListByClasses(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException;

	public List<Fields> getFieldsListByClasses(
			long structureId, long[] classPKs, OrderByComparator obc)
		throws StorageException;

	public List<Fields> getFieldsListByStructure(
			long structureId, List<String> fieldNames)
		throws StorageException;

	public List<Fields> getFieldsListByStructure(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws StorageException;

	public Map<Long, Fields> getFieldsMapByClasses(
			long structureId, long[] classPKs)
		throws StorageException;

	public Map<Long, Fields> getFieldsMapByClasses(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws StorageException;

	public List<Fields> query(
			long structureId, List<String> fieldNames, String whereClause,
			OrderByComparator orderByComparator)
		throws StorageException;

	public int queryCount(long structureId, String whereClause)
		throws StorageException;

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext)
		throws StorageException;

	public void update(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws StorageException;

}