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

package com.liferay.portal.kernel.util;

/**
 * @author José Manuel Navarro
 */
public class TableNameOrderByComparator extends OrderByComparator {

	public TableNameOrderByComparator(
		OrderByComparator orderByComparator, String tableName) {

		_orderByComparator = orderByComparator;

		setTableName(tableName);
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		return _orderByComparator.compare(obj1, obj2);
	}

	public OrderByComparator getDecoratedComparator() {
		return _orderByComparator;
	}

	@Override
	public String getOrderBy() {
		String originalOrderBy = _orderByComparator.getOrderBy();

		if (_tableName == null) {
			return originalOrderBy;
		}

		String[] sortedFields = StringUtil.split(originalOrderBy);

		StringBundler finalOrderBy = new StringBundler(
			(3 * sortedFields.length) - 1);

		for (int i = 0; i < sortedFields.length; ++i) {
			String field = sortedFields[i];

			if (field.indexOf(CharPool.PERIOD) == -1) {
				finalOrderBy.append(_tableName);
			}

			finalOrderBy.append(StringUtil.trim(field));

			if (i < (sortedFields.length - 1)) {
				finalOrderBy.append(_ORDER_BY_SEPARATOR);
			}
		}

		return finalOrderBy.toString();
	}

	@Override
	public String[] getOrderByConditionFields() {
		return _orderByComparator.getOrderByConditionFields();
	}

	@Override
	public Object[] getOrderByConditionValues(Object obj) {
		return _orderByComparator.getOrderByConditionValues(obj);
	}

	@Override
	public String[] getOrderByFields() {
		return _orderByComparator.getOrderByFields();
	}

	@Override
	public boolean isAscending() {
		return _orderByComparator.isAscending();
	}

	@Override
	public boolean isAscending(String field) {
		return _orderByComparator.isAscending(field);
	}

	public void setTableName(String tableName) {
		if ((tableName != null) && (tableName.length() > 0)) {
			if (tableName.endsWith(StringPool.PERIOD)) {
				_tableName = tableName;
			}
			else {
				_tableName = tableName + CharPool.PERIOD;
			}
		}
		else {
			_tableName = null;
		}
	}

	@Override
	public String toString() {
		return _orderByComparator.toString();
	}

	private static final String _ORDER_BY_SEPARATOR = ", ";

	private OrderByComparator _orderByComparator;
	private String _tableName;

}