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

package com.liferay.portal.tools.servicebuilder;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.TextFormatter;

/**
 * <a href="EntityColumn.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Charles May
 */
public class EntityColumn implements Cloneable {

	public EntityColumn(String name) {
		this(
			name, null, null, false, null, null, null, true, true, null, null,
			null, true, false);
	}

	public EntityColumn(
		String name, String dbName, String type, boolean primary,
		String ejbName, String mappingKey, String mappingTable,
		boolean caseSensitive, boolean orderByAscending, String comparator,
		String idType, String idParam, boolean convertNull, boolean localized) {

		_name = name;
		_dbName = dbName;
		_type = type;
		_primary = primary;
		_methodName = TextFormatter.format(name, TextFormatter.G);
		_ejbName = ejbName;
		_mappingKey = mappingKey;
		_mappingTable = mappingTable;
		_caseSensitive = caseSensitive;
		_orderByAscending = orderByAscending;
		_comparator = comparator;
		_idType = idType;
		_idParam = idParam;
		_convertNull = convertNull;
		_localized = localized;
	}

	public EntityColumn(
		String name, String dbName, String type, boolean primary,
		String ejbName, String mappingKey, String mappingTable, String idType,
		String idParam, boolean convertNull, boolean localized) {

		this(
			name, dbName, type, primary, ejbName, mappingKey, mappingTable,
			true, true, null, idType, idParam, convertNull, localized);
	}

	public Object clone() {
		return new EntityColumn(
			getName(), getDBName(), getType(), isPrimary(), getEJBName(),
			getMappingKey(), getMappingTable(), isCaseSensitive(),
			isOrderByAscending(), getComparator(), getIdType(), getIdParam(),
			isConvertNull(), isLocalized());
	}

	public boolean equals(Object obj) {
		EntityColumn col = (EntityColumn)obj;

		String name = col.getName();

		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getComparator() {
		return _comparator;
	}

	public String getDBName() {
		return _dbName;
	}

	public String getEJBName() {
		return _ejbName;
	}

	public String getIdParam() {
		return _idParam;
	}

	public String getIdType() {
		return _idType;
	}

	public String getMappingKey() {
		return _mappingKey;
	}

	public String getMappingTable() {
		return _mappingTable;
	}

	public String getMethodName() {
		return _methodName;
	}

	public String getMethodNames() {
		return TextFormatter.formatPlural(new String(_methodName));
	}

	public String getMethodUserUuidName() {
		return _methodName.substring(0, _methodName.length() - 2) + "Uuid";
	}

	public String getName() {
		return _name;
	}

	public String getNames() {
		return TextFormatter.formatPlural(new String(_name));
	}

	public String getType() {
		return _type;
	}

	public String getUserUuidName() {
		return _name.substring(0, _name.length() - 2) + "Uuid";
	}

	public int hashCode() {
		return _name.hashCode();
	}

	public boolean isCaseSensitive() {
		return _caseSensitive;
	}

	public boolean isCollection() {
		if (_type.equals("Collection")) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isConvertNull() {
		return _convertNull;
	}

	public boolean isFetchFinderPath() {
		return _fetchFinderPath;
	}

	public boolean isLocalized() {
		return _localized;
	}

	public boolean isMappingManyToMany() {
		return Validator.isNotNull(_mappingTable);
	}

	public boolean isMappingOneToMany() {
		return Validator.isNotNull(_mappingKey);
	}

	public boolean isOrderByAscending() {
		return _orderByAscending;
	}

	public boolean isPrimary() {
		return _primary;
	}

	public boolean isPrimitiveType() {
		if (Character.isLowerCase(_type.charAt(0))) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isUserUuid() {
		if (_type.equals("long") && _methodName.endsWith("UserId")) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setCaseSensitive(boolean caseSensitive) {
		_caseSensitive = caseSensitive;
	}

	public void setComparator(String comparator) {
		_comparator = comparator;
	}

	public void setConvertNull(boolean convertNull) {
		_convertNull = convertNull;
	}

	public void setDBName(String dbName) {
		_dbName = dbName;
	}

	public void setFetchFinderPath(boolean fetchFinderPath) {
		_fetchFinderPath = fetchFinderPath;
	}

	public void setIdParam(String idParam) {
		_idParam = idParam;
	}

	public void setIdType(String idType) {
		_idType = idType;
	}

	public void setLocalized(boolean localized) {
		_localized = localized;
	}

	public void setOrderByAscending(boolean orderByAscending) {
		_orderByAscending = orderByAscending;
	}

	private boolean _caseSensitive;
	private String _comparator;
	private boolean _convertNull;
	private String _dbName;
	private String _ejbName;
	private boolean _fetchFinderPath;
	private String _idParam;
	private String _idType;
	private boolean _localized;
	private String _mappingKey;
	private String _mappingTable;
	private String _methodName;
	private String _name;
	private boolean _orderByAscending;
	private boolean _primary;
	private String _type;

}