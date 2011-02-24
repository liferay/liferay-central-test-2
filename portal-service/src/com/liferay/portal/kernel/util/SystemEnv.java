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

package com.liferay.portal.kernel.util;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SystemEnv {

	public static Properties getProperties() {
		Properties properties = new Properties();

		properties.putAll(System.getenv());

		return properties;
	}

	public static void setProperties(Properties props) {
		Set<Map.Entry<String, String>> entrySet = System.getenv().entrySet();

		for (Map.Entry<String, String> entry : entrySet) {
			props.setProperty("env." + entry.getKey(), entry.getValue());
		}
	}

}