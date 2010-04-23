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

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Comparator;

/**
 * <a href="OrderByComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public abstract class OrderByComparator implements Comparator, Serializable {

	public abstract int compare(Object obj1, Object obj2);

	public String getOrderBy() {
		return null;
	}

	public String[] getOrderByFields() {
		String orderBy = getOrderBy();

		if (orderBy == null) {
			return null;
		}

		String[] parts = StringUtil.split(orderBy);

		String[] fields = new String[parts.length];

		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];

			int x = part.indexOf(StringPool.PERIOD);
			int y = part.indexOf(StringPool.SPACE, x);

			if (y == -1) {
				y = part.length();
			}

			fields[i] = part.substring(x + 1, y);
		}

		return fields;
	}

	public Object[] getOrderByValues(Object obj) {
		try {
			Class<?> clazz = obj.getClass();
			String[] fieldNames = getOrderByFields();
			Object[] values = new Object[fieldNames.length];
			for(int i = 0; i< fieldNames.length; i++) {
				String fieldName = fieldNames[i];

				StringBuilder getterNameBuilder = new StringBuilder(
					fieldName.length() + 3);
				getterNameBuilder.append("get");
				getterNameBuilder.append(
					Character.toUpperCase(fieldName.charAt(0)));
				getterNameBuilder.append(fieldName.substring(1));

				String getterName = getterNameBuilder.toString();
				Method getterMethod = clazz.getMethod(getterName);
				getterMethod.setAccessible(true);
				values[i] = getterMethod.invoke(obj);
			}
			return values;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isAscending() {
		String orderBy = getOrderBy();

		if ((orderBy == null) ||
			(orderBy.toUpperCase().endsWith(_ORDER_BY_DESC))) {

			return false;
		}
		else {
			return true;
		}
	}

	public String toString() {
		return getOrderBy();
	}

	private static final String _ORDER_BY_DESC = " DESC";

}