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

import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.upgrade.util.ValueMapperFactoryUtil;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalStructurePKUpgradeColumnImpl extends PKUpgradeColumnImpl {

	public JournalStructurePKUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn groupIdColumn) {

		super("id_", new Integer(Types.VARCHAR), false);

		_companyIdColumn = companyIdColumn;
		_groupIdColumn = groupIdColumn;
		_journalStructureIdMapper = ValueMapperFactoryUtil.getValueMapper();
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Object newValue = super.getNewValue(oldValue);

		String companyId = (String)_companyIdColumn.getOldValue();
		Long groupId = (Long)_groupIdColumn.getOldValue();
		String structureId = (String)oldValue;

		String oldIdValue =
			"{companyId=" + companyId + ", groupId=" + groupId +
				", structureId=" + structureId + "}";

		_journalStructureIdMapper.mapValue(oldIdValue, newValue);

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _journalStructureIdMapper;
	}

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _groupIdColumn;
	private ValueMapper _journalStructureIdMapper;

}