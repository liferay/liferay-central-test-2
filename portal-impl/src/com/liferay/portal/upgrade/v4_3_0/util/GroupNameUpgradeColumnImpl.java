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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupNameUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public GroupNameUpgradeColumnImpl(
		UpgradeColumn groupIdColumn, UpgradeColumn classPKColumn) {

		super("name", new Integer(Types.VARCHAR));

		_groupIdColumn = groupIdColumn;
		_classPKColumn = classPKColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Long classPK = (Long)_classPKColumn.getNewValue();

		if (classPK.longValue() > 0) {
			return _groupIdColumn.getNewValue().toString();
		}
		else {
			return oldValue;
		}
	}

	private UpgradeColumn _groupIdColumn;
	private UpgradeColumn _classPKColumn;

}