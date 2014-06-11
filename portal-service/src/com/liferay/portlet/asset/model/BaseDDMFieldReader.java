/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseDDMFieldReader implements DDMFieldReader {

	@Override
	public Fields getFields(String ddmType) throws PortalException {
		Fields filteredFields = new Fields();

		for (Field field : getFields()) {
			if (ddmType.equals(field.getDataType())) {
				filteredFields.put(field);
			}
		}

		return filteredFields;
	}

}