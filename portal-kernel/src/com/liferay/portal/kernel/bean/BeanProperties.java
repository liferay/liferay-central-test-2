/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bean;

/**
 * <a href="BeanProperties.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface BeanProperties {

	public void copyProperties(Object source, Object target);

	public void copyProperties(Object source, Object target, Class<?> editable);

	public void copyProperties(
		Object source, Object target, String[] ignoreProperties);

	public boolean getBoolean(Object bean, String param);

	public boolean getBoolean(Object bean, String param, boolean defaultValue);

	public double getDouble(Object bean, String param);

	public double getDouble(Object bean, String param, double defaultValue);

	public int getInteger(Object bean, String param);

	public int getInteger(Object bean, String param, int defaultValue);

	public long getLong(Object bean, String param);

	public long getLong(Object bean, String param, long defaultValue);

	public Object getObject(Object bean, String param);

	public Object getObject(Object bean, String param, Object defaultValue);

	public String getString(Object bean, String param);

	public String getString(Object bean, String param, String defaultValue);

	public void setProperty(Object bean, String param, Object value);

}