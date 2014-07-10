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
package com.liferay.portal.kernel.spring.osgi;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Raymond Augé
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OSGiBeanProperties {

	public String portalPropertyPrefix() default "";

	public boolean portalPropertiesRemovePrefix() default true;

	public String[] property() default {};

	public static class Convert {

		public static Map<String, Object> fromObject(Object object) {
			Class<? extends Object> clazz = object.getClass();

			OSGiBeanProperties osgiBeanProperties = clazz.getAnnotation(
				OSGiBeanProperties.class);

			if (osgiBeanProperties == null) {
				return null;
			}

			return toMap(osgiBeanProperties);
		}

		public static Map<String, Object> toMap(
			OSGiBeanProperties osgiBeanProperties) {

			Map<String, Object> properties = new HashMap<String, Object>();

			String[] propertiesArray = osgiBeanProperties.property();

			for (String propertyString : propertiesArray) {
				String[] parts = propertyString.split(StringPool.EQUAL, 1);

				if (parts.length <= 1) {
					continue;
				}

				Object object = properties.get(parts[0]);

				if (object == null) {
					properties.put(parts[0], parts[1]);
				}
				else if (object instanceof String) {
					properties.put(
						parts[0], new String[] {(String)object, parts[1]});
				}
				else if (object.getClass().isArray()) {
					properties.put(
						parts[0], ArrayUtil.append((String[])object, parts[1]));
				}
			}

			String portalPropertyPrefix =
				osgiBeanProperties.portalPropertyPrefix();

			if (Validator.isNotNull(portalPropertyPrefix)) {
				Properties portalProperties = PropsUtil.getProperties(
					portalPropertyPrefix,
					osgiBeanProperties.portalPropertiesRemovePrefix());

				properties.putAll(PropertiesUtil.toMap(portalProperties));
			}

			return properties;
		}

	}

}