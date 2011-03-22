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

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class XMLStorageAdapter extends BaseStorageAdapter {

	protected long doCreate(
			long companyId, long structureId, Map<String, Serializable> fields,
			ServiceContext serviceContext)
		throws Exception {

		return 0;
	}

	protected void doDeleteByClass(long classPK) throws Exception {
	}

	protected void doDeleteByStructure(long structureId) throws Exception {
	}

	protected Map<Long, Map<String, Serializable>> doGetFieldsByClasses(
		long[] classPKs, List<String> fieldNames) throws Exception {

		return null;
	}

	protected List<Map<String, Serializable>> doGetFieldsByClasses(
			long[] classPKs, List<String> fieldNames, OrderByComparator obc)
		throws Exception {

		return null;
	}

	protected List<Map<String, Serializable>> doGetFieldsByStructure(
			long structureId, List<String> fieldNames, OrderByComparator obc)
		throws Exception {

		return null;
	}

	protected List<Map<String, Serializable>> doQuery(
			long structureId, List<String> fieldNames, String whereClause,
			OrderByComparator obc)
		throws Exception {

		return null;
	}

	protected int doQueryCount(long structureId, String whereClause)
		throws Exception {

		return 0;
	}

	protected void doUpdate(
			long classPK, Map<String, Serializable> fields, boolean merge,
			ServiceContext serviceContext)
		throws Exception {
	}

}