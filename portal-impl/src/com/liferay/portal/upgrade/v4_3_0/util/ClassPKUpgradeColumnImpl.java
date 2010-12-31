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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.GetterUtil;

import java.sql.Types;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassPKUpgradeColumnImpl extends TempUpgradeColumnImpl {

	public ClassPKUpgradeColumnImpl(
		ClassNameIdUpgradeColumnImpl classNameIdColumn,
		Map<Long, ClassPKContainer> classPKContainers) {

		super("classPK", new Integer(Types.VARCHAR));

		_classNameIdColumn = classNameIdColumn;
		_classPKContainers = classPKContainers;
	}

	public Integer getNewColumnType(Integer defaultType) {
		return new Integer(Types.BIGINT);
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Long classNameId = (Long)_classNameIdColumn.getNewValue();

		ClassPKContainer classPKContainer = _classPKContainers.get(classNameId);

		if (classPKContainer != null) {
			ValueMapper valueMapper = classPKContainer.getValueMapper();

			if (classPKContainer.isLong()) {
				return valueMapper.getNewValue(
					new Long(GetterUtil.getLong((String)oldValue)));
			}
			else {
				return valueMapper.getNewValue(oldValue);
			}
		}
		else {
			if (oldValue instanceof String) {
				return new Long(GetterUtil.getLong((String)oldValue));
			}
			else {
				return oldValue;
			}
		}
	}

	private ClassNameIdUpgradeColumnImpl _classNameIdColumn;
	private Map<Long, ClassPKContainer> _classPKContainers;

}