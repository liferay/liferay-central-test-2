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

package com.liferay.portal.upgrade.v6_0_2;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.wiki.model.WikiPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UpgradeExpando.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		long classNameId = ClassNameLocalServiceUtil.getClassNameId(
			JournalArticle.class.getName());

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading Expando for Journal");
		}

		updateTables(classNameId);

		classNameId = ClassNameLocalServiceUtil.getClassNameId(
			WikiPage.class.getName());

		if (_log.isDebugEnabled()) {
			_log.debug("Upgrading Expando for Wiki");
		}

		updateTables(classNameId);
	}

	protected boolean existsRow(long companyId, long tableId, Long classPK)
		throws Exception{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoRow where companyId = ? and tableId = ?" +
					" and classPK = ?");

			ps.setLong(1, companyId);
			ps.setLong(2, tableId);
			ps.setLong(3, classPK);

			rs = ps.executeQuery();

			return rs.next();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	protected boolean existsValue(
			long companyId, long tableId, long columnId, long rowId)
		throws Exception{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoValue where companyId = ? and tableId =" +
					" ? and columnId = ? and rowId_ = ?");

			ps.setLong(1, companyId);
			ps.setLong(2, tableId);
			ps.setLong(3, columnId);
			ps.setLong(4, rowId);

			rs = ps.executeQuery();

			return rs.next();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

	}

	protected List<Long> getJournalArticleIds(long resourcePrimKey)
		throws Exception {

		ArrayList<Long> ids = new ArrayList<Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select id_ from JournalArticle where resourcePrimKey = ?");

			ps.setLong(1, resourcePrimKey);

			rs = ps.executeQuery();

			while (rs.next()) {
				long id = rs.getLong("id_");

				ids.add(id);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return ids;
	}

	protected void updateRows(long tableId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoRow where tableId = ?");

			ps.setLong(1, tableId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long rowId = rs.getLong("rowId_");
				long companyId = rs.getLong("companyId");
				long classPK = rs.getLong("classPK");

				List<Long> ids = getJournalArticleIds(classPK);

				for (Long id : ids) {
					if (!existsRow(companyId, tableId, id)) {
						long targetRowId = increment();

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Copying row " + rowId + " into " +
									targetRowId);
						}

						runSQL(
							"insert into ExpandoRow (rowId_, companyId, " +
								"tableId, classPK) values (" + targetRowId +
									", " + companyId + ", " + tableId + ", " +
										id + ")");

						updateValues(tableId, rowId, targetRowId, classPK, id);
					}

				}

				if (!ids.isEmpty()) {
					runSQL("delete from ExpandoValue where rowId_ = " + rowId);
					runSQL("delete from ExpandoRow where rowId_ = " + rowId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateTables(long classNameId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoTable where classNameId = ? and " +
					"name = ?");

			ps.setLong(1, classNameId);
			ps.setString(2, ExpandoTableConstants.DEFAULT_TABLE_NAME);

			rs = ps.executeQuery();

			while (rs.next()) {
				long tableId = rs.getLong("tableId");

				updateRows(tableId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateValues(
			long tableId, long rowId, long targetRowId, long classPK,
			long targetClassPK)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from ExpandoValue where tableId = ? and rowId_ = ? " +
					"and classPK = ?");

			ps.setLong(1, tableId);
			ps.setLong(2, rowId);
			ps.setLong(3, classPK);

			rs = ps.executeQuery();

			while (rs.next()) {
				long valueId = rs.getLong("valueId");
				long companyId = rs.getLong("companyId");
				long columnId = rs.getLong("columnId");
				long classNameId = rs.getLong("classNameId");
				String data = rs.getString("data_");

				long targetValueId = increment();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Copying value " + valueId + " into " + targetValueId);
				}

				if (!existsValue(companyId, tableId, columnId, targetRowId)) {
					runSQL(
						"insert into ExpandoValue (valueId, companyId, " +
							"tableId, columnId, rowId_, classNameId, classPK," +
								" data_) values (" + targetValueId + ", " +
									companyId + ", " + tableId + ", " +
										columnId + ", " + targetRowId + ", " +
											classNameId + ", " + targetClassPK +
												", '" + data + "')");
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeExpando.class);

}