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

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public interface StorageAdapter {

	public long create(
			long companyId, long structureId, Map<String, Serializable> fields,
			ServiceContext serviceContext)
		throws StorageException;

	public void delete(long classPK) throws StorageException;

	public void deleteAll(long structureId) throws StorageException;

	public List<Map<String, Serializable>> getAllFields(
			long structureId, List<String> fieldNames)
		throws StorageException;

	public List<Map<String, Serializable>> getAllFields(
			long structureId, List<String> fieldNames, OrderByComparator obc)
		throws StorageException;

	public Map<Long, List<Map<String, Serializable>>> getAllFields(
			long[] structureIds, List<String> fieldNames)
		throws StorageException;

	public Map<Long, List<Map<String, Serializable>>> getAllFields(
			long[] structureIds, List<String> fieldNames,
			OrderByComparator obc)
		throws StorageException;

	public Map<String, Serializable> getFields(long classPK)
		throws StorageException;

	public Map<String, Serializable> getFields(
			long classPK, List<String> fieldNames)
		throws StorageException;

	public Map<Long, Map<String, Serializable>> getFields(long[] classPKs)
		throws StorageException;

	public Map<Long, Map<String, Serializable>> getFields(
			long[] classPKs, List<String> fieldNames)
		throws StorageException;

	public List<Map<String, Serializable>> getFields(
			long[] classPKs, List<String> fieldNames, OrderByComparator obc)
		throws StorageException;

	public List<Map<String, Serializable>> getFields(
			long[] classPKs, OrderByComparator obc)
		throws StorageException;

	public List<Map<String, Serializable>> query(
			long structureId, List<String> fieldNames, String whereClause,
			OrderByComparator obc)
		throws StorageException;

	public int queryCount(long structureId, String whereClause)
		throws StorageException;

	public void update(
			long classPK, Map<String, Serializable> fields, boolean merge,
			ServiceContext serviceContext)
		throws StorageException;

	public void update(
			long classPK, Map<String, Serializable> fields,
			ServiceContext serviceContext)
		throws StorageException;

}