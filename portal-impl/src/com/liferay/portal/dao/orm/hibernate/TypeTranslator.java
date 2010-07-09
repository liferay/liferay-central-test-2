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

package com.liferay.portal.dao.orm.hibernate;

import com.liferay.portal.kernel.dao.orm.Type;

/**
 * @author Brian Wing Shun Chan
 */
public class TypeTranslator {

	public static org.hibernate.type.Type translate(Type type) {
		if (type == Type.BIG_DECIMAL) {
			return org.hibernate.Hibernate.BIG_DECIMAL;
		}
		else if (type == Type.BIG_INTEGER) {
			return org.hibernate.Hibernate.BIG_INTEGER;
		}
		else if (type == Type.BINARY) {
			return org.hibernate.Hibernate.BINARY;
		}
		else if (type == Type.BLOB) {
			return org.hibernate.Hibernate.BLOB;
		}
		else if (type == Type.BOOLEAN) {
			return org.hibernate.Hibernate.BOOLEAN;
		}
		else if (type == Type.BYTE) {
			return org.hibernate.Hibernate.BYTE;
		}
		else if (type == Type.CALENDAR) {
			return org.hibernate.Hibernate.CALENDAR;
		}
		else if (type == Type.CALENDAR_DATE) {
			return org.hibernate.Hibernate.CALENDAR_DATE;
		}
		else if (type == Type.CHAR_ARRAY) {
			return org.hibernate.Hibernate.CHAR_ARRAY;
		}
		else if (type == Type.CHARACTER) {
			return org.hibernate.Hibernate.CHARACTER;
		}
		else if (type == Type.CHARACTER_ARRAY) {
			return org.hibernate.Hibernate.CHARACTER_ARRAY;
		}
		else if (type == Type.CLASS) {
			return org.hibernate.Hibernate.CLASS;
		}
		else if (type == Type.CLOB) {
			return org.hibernate.Hibernate.CLOB;
		}
		else if (type == Type.CURRENCY) {
			return org.hibernate.Hibernate.CURRENCY;
		}
		else if (type == Type.DATE) {
			return org.hibernate.Hibernate.DATE;
		}
		else if (type == Type.DOUBLE) {
			return org.hibernate.Hibernate.DOUBLE;
		}
		else if (type == Type.FLOAT) {
			return org.hibernate.Hibernate.FLOAT;
		}
		else if (type == Type.INTEGER) {
			return org.hibernate.Hibernate.INTEGER;
		}
		else if (type == Type.LOCALE) {
			return org.hibernate.Hibernate.LOCALE;
		}
		else if (type == Type.LONG) {
			return org.hibernate.Hibernate.LONG;
		}
		else if (type == Type.OBJECT) {
			return org.hibernate.Hibernate.OBJECT;
		}
		else if (type == Type.SERIALIZABLE) {
			return org.hibernate.Hibernate.SERIALIZABLE;
		}
		else if (type == Type.SHORT) {
			return org.hibernate.Hibernate.SHORT;
		}
		else if (type == Type.STRING) {
			return org.hibernate.Hibernate.STRING;
		}
		else if (type == Type.TEXT) {
			return org.hibernate.Hibernate.TEXT;
		}
		else if (type == Type.TIMESTAMP) {
			return org.hibernate.Hibernate.TIMESTAMP;
		}
		else if (type == Type.TIMEZONE) {
			return org.hibernate.Hibernate.TIMEZONE;
		}
		else if (type == Type.TRUE_FALSE) {
			return org.hibernate.Hibernate.TRUE_FALSE;
		}
		else if (type == Type.WRAPPER_BINARY) {
			return org.hibernate.Hibernate.WRAPPER_BINARY;
		}
		else if (type == Type.YES_NO) {
			return org.hibernate.Hibernate.YES_NO;
		}
		else {
			return null;
		}
	}

}