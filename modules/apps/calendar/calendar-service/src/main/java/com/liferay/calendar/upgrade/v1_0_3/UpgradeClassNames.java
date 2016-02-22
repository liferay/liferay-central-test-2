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

package com.liferay.calendar.upgrade.v1_0_3;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.upgrade.v7_0_0.UpgradeKernelPackage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Cristina González
 */
public class UpgradeClassNames extends UpgradeKernelPackage {

	@Override
	public void doUpgrade() throws UpgradeException {
		_cleanCalEventClassName();

		_cleanRepeatedResources();

		super.doUpgrade();
	}

	@Override
	protected String[][] getClassNames() {
		return new String[0][0];
	}

	@Override
	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	private void _cleanCalEventClassName() throws UpgradeException {
		try {
			runSQL(
				"delete from Counter where name like '" +
					_CAL_EVENT_CLASS_NAME + "%'");

			runSQL(
				"delete from ClassName_ where value like '" +
					_CAL_EVENT_CLASS_NAME + "%'");

			runSQL(
				"delete from ResourceAction where name like '" +
					_CAL_EVENT_CLASS_NAME + "%'");

			runSQL(
				"delete from ResourceBlock where name like '" +
					_CAL_EVENT_CLASS_NAME + "%'");

			runSQL(
				"delete from ResourcePermission where name like '" +
					_CAL_EVENT_CLASS_NAME + "%'");
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _cleanRepeatedResources() throws UpgradeException {
		String oldName = _RESOURCE_NAMES[0][0];
		String newName = _RESOURCE_NAMES[0][1];

		String selectSQL =
			"select actionId from ResourceAction where name = '" + newName +
				"'";

		try (PreparedStatement ps = connection.prepareStatement(selectSQL);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				runSQL(
					"delete from ResourceAction where actionId = '" +
						rs.getString(1) + "' and name= '"+ oldName + "'");
			}
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private static final String _CAL_EVENT_CLASS_NAME =
		"com.liferay.portlet.calendar.model.CalEvent";

	private static final String[][] _RESOURCE_NAMES = new String[][] {
		{
			"com.liferay.portlet.calendar", "com.liferay.calendar"
		}
	};

}