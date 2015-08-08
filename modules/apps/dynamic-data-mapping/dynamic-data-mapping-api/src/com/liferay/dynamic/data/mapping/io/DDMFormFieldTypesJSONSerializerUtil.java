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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;

import java.util.List;

/**
 * @author Bruno Basto
 */
public class DDMFormFieldTypesJSONSerializerUtil {

	public static DDMFormFieldTypesJSONSerializer
		getDDMFormFieldTypesJSONSerializer() {

		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormFieldTypesJSONSerializerUtil.class);

		return _ddmFormFieldTypesJSONSerializer;
	}

	public static String serialize(List<DDMFormFieldType> ddmFormFieldTypes)
		throws PortalException {

		return getDDMFormFieldTypesJSONSerializer().serialize(
			ddmFormFieldTypes);
	}

	public void setDDMFormFieldTypesJSONSerializer(
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ddmFormFieldTypesJSONSerializer = ddmFormFieldTypesJSONSerializer;
	}

	private static DDMFormFieldTypesJSONSerializer
		_ddmFormFieldTypesJSONSerializer;

}