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

package com.liferay.dynamic.data.mapping.io;

import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesJSONSerializerUtil {

	public static DDMFormValuesJSONSerializer getDDMFormValuesJSONSerializer() {
		PortalRuntimePermission.checkGetBeanProperty(
			DDMFormValuesJSONSerializerUtil.class);

		return _serviceTracker.getService();
	}

	public static String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesJSONSerializer ddmFormValuesJSONSerializer =
			getDDMFormValuesJSONSerializer();

		return ddmFormValuesJSONSerializer.serialize(ddmFormValues);
	}

	private static final ServiceTracker
		<DDMFormValuesJSONSerializer, DDMFormValuesJSONSerializer>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			DDMFormValuesJSONSerializerUtil.class);

		_serviceTracker = new ServiceTracker<>(
			bundle.getBundleContext(), DDMFormValuesJSONSerializer.class, null);

		_serviceTracker.open();
	}

}