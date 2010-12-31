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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Julio Camarero
 */
public class UpgradeAsset extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select classNameId, classPK from AssetEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				String className = PortalUtil.getClassName(classNameId);

				String[] tableAndColumn = getTableAndColumnName(className);

				if (Validator.isNull(tableAndColumn[0]) ||
					Validator.isNull(tableAndColumn[1])) {

					continue;
				}

				String uuid = getUuid(
					tableAndColumn[0], tableAndColumn[1], tableAndColumn[2],
					classPK);

				updateAssetEntry(classNameId, classPK, uuid);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected String[] getTableAndColumnName(String className) {
		String[] tableAndColumn = new String[3];

		if (className.equals("com.liferay.portal.model.Group")) {
		}
		else if (className.equals("com.liferay.portal.model.Organization")) {
		}
		else if (className.equals("com.liferay.portal.model.User")) {
			tableAndColumn[0] = "User_";
			tableAndColumn[1] = "userId";
			tableAndColumn[2] = "userId";
		}
		else if (className.equals(
					"com.liferay.portlet.blogs.model.BlogsEntry")) {

			tableAndColumn[0] = "BlogsEntry";
			tableAndColumn[1] = "entryId";
			tableAndColumn[2] = "entryId";
		}
		else if (className.equals(
					"com.liferay.portlet.bookmarks.model.BookmarksEntry")) {

			tableAndColumn[0] = "BookmarksEntry";
			tableAndColumn[1] = "entryId";
			tableAndColumn[2] = "entryId";
		}
		else if (className.equals(
					"com.liferay.portlet.calendar.model.CalEvent")) {

			tableAndColumn[0] = "CalEvent";
			tableAndColumn[1] = "eventId";
			tableAndColumn[2] = "eventId";
		}
		else if (className.equals(
					"com.liferay.portlet.documentlibrary.model.DLFileEntry")) {

			tableAndColumn[0] = "DLFileEntry";
			tableAndColumn[1] = "fileEntryId";
			tableAndColumn[2] = "fileEntryId";
		}
		else if (className.equals(
					"com.liferay.portlet.documentlibrary.model." +
						"DLFileShortcut")) {

			tableAndColumn[0] = "DLFileShortcut";
			tableAndColumn[1] = "fileShortcutId";
			tableAndColumn[2] = "fileShortcutId";
		}
		else if (className.equals(
					"com.liferay.portlet.imagegallery.model.IGImage")) {

			tableAndColumn[0] = "IGImage";
			tableAndColumn[1] = "imageId";
			tableAndColumn[2] = "imageId";
		}
		else if (className.equals(
					"com.liferay.portlet.journal.model.JournalArticle")) {

			tableAndColumn[0] = "JournalArticle";
			tableAndColumn[1] = "resourcePrimKey";
			tableAndColumn[2] = "id_";
		}
		else if (className.equals(
					"com.liferay.portlet.messageboards.model.MBMessage")) {

			tableAndColumn[0] = "MBMessage";
			tableAndColumn[1] = "messageId";
			tableAndColumn[2] = "messageId";
		}
		else if (className.equals(
					"com.liferay.portlet.wiki.model.WikiPage")) {

			tableAndColumn[0] = "WikiPage";
			tableAndColumn[1] = "resourcePrimKey";
			tableAndColumn[2] = "pageId";
		}

		return tableAndColumn;
	}

	protected String getUuid(
			String tableName, String columnName1, String columnName2,
			long classPK)
		throws Exception {

		String uuid = StringPool.BLANK;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select uuid_ from " + tableName + " where " + columnName1 +
					" = ? or " + columnName2 + " = ?");

			ps.setLong(1, classPK);
			ps.setLong(2, classPK);

			rs = ps.executeQuery();

			while (rs.next()) {
				uuid = rs.getString("uuid_");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return uuid;
	}

	protected void updateAssetEntry(long classNameId, long classPK, String uuid)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update AssetEntry set classUuid = ? where classNameId = ? " +
					"and classPK = ?");

			ps.setString(1, uuid);
			ps.setLong(2, classNameId);
			ps.setLong(3, classPK);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

}