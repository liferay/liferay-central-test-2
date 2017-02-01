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

package com.liferay.portal.upgrade.v7_0_3;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void deleteOrphanExpandoRow() throws Exception {
		StringBundler sb = new StringBundler();

		sb.append("select rowId_ from ExpandoRow ");
		sb.append("where rowId_ not in ");
		sb.append("(select distinct ExpandoRow.rowId_ ");
		sb.append("from  ExpandoRow inner join ExpandoValue on ");
		sb.append("ExpandoRow.rowId_ = ExpandoValue.rowId_)");

		System.out.println("HOL\n" + sb.toString());

		try (PreparedStatement ps1 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, "delete from ExpandoRow where rowId_ = ?");

			PreparedStatement ps2 = connection.prepareStatement(
				sb.toString())) {

			try (ResultSet rs = ps2.executeQuery()) {
				while (rs.next()) {
					long rowId = rs.getInt(1);

					ps1.setLong(1, rowId);

					ps1.addBatch();
				}

				ps1.executeBatch();
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		deleteOrphanExpandoRow();
	}

}