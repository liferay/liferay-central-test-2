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
import com.liferay.portlet.dynamicdatamapping.storage.query.Condition;

import java.util.List;
import java.util.Map;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public class ExpandoStorageAdapter extends BaseStorageAdapter {

	protected long doCreate(
			long companyId, long structureId, Fields fields,
			ServiceContext serviceContext)
		throws Exception {

		return 0;
	}

	protected void doDeleteByClass(long classPK) throws Exception {
	}

	protected void doDeleteByStructure(long structureId) throws Exception {
	}

	protected List<Fields> doGetFieldsListByClasses(
			long structureId, long[] classPKs, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected List<Fields> doGetFieldsListByStructure(
			long structureId, List<String> fieldNames,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected Map<Long, Fields> doGetFieldsMapByClasses(
			long structureId, long[] classPKs, List<String> fieldNames)
		throws Exception {

		return null;
	}

	protected List<Fields> doQuery(
			long structureId, List<String> fieldNames, Condition whereCondition,
			OrderByComparator orderByComparator)
		throws Exception {

		return null;
	}

	protected int doQueryCount(long structureId, Condition whereCondition)
		throws Exception {

		return 0;
	}

	protected void doUpdate(
			long classPK, Fields fields, ServiceContext serviceContext,
			boolean merge)
		throws Exception {
	}

}