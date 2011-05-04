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

package com.liferay.portlet.dynamicdatalists.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDLRecordImpl
	extends DDLRecordModelImpl implements DDLRecord {

	public DDLRecordImpl() {
	}

	public Field getField(String fieldName) throws StorageException {
		return getFields().get(fieldName);
	}

	public Serializable getFieldDataType(String fieldName) throws Exception {
		DDMStructure structure = getRecordSet().getDDMStructure();

		return structure.getFieldDataType(fieldName);
	}

	public Fields getFields() throws StorageException {
		return StorageEngineUtil.getFields(getClassPK());
	}

	public Serializable getFieldType(String fieldName) throws Exception {
		DDMStructure structure = getRecordSet().getDDMStructure();

		return structure.getFieldType(fieldName);
	}

	public Serializable getFieldValue(String fieldName)
		throws StorageException {

		return getField(fieldName).getValue();
	}

	public DDLRecordSet getRecordSet() throws PortalException, SystemException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getRecordSetId());
	}

}