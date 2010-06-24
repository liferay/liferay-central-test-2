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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeAsset.java.html"><b><i>View Source</i></b></a>
 *
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
				"select * from AssetEntry");

			rs = ps.executeQuery();

			while (rs.next()) {
				long classPK = rs.getLong("classPK");
				long classNameId = rs.getLong("classNameId");

				PreparedStatement classPs = con.prepareStatement(
					"select * from ClassName_ where classNameId = ?");

				classPs.setLong(1, classNameId);

				ResultSet classRs = classPs.executeQuery();

				classRs.next();

				String className = classRs.getString("value");

				String[] tableAndColumn = getTableAndColumnName(className);

				if (Validator.isNull(tableAndColumn[0]) ||
					Validator.isNull(tableAndColumn[1])) {
						continue;
				}

				classPs = con.prepareStatement(
					"select * from " + tableAndColumn[0] + " where " +
						tableAndColumn[1] + " = ? or " + tableAndColumn[2] +
						" = ?");

				classPs.setLong(1, classPK);
				classPs.setLong(2, classPK);

				classRs = classPs.executeQuery();

				classRs.next();

				String uuid = classRs.getString("uuid_");

				classPs = con.prepareStatement(
					"update AssetEntry set classUuid = ? where classNameId = ? "
						+ "and classPK = ?" );

				classPs.setString(1, uuid);
				classPs.setLong(2, classNameId);
				classPs.setLong(3, classPK);

				classPs.executeUpdate();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private String[] getTableAndColumnName(String className)  {
		String[] tableAndColumn = new String[3];

		if (className.equals("com.liferay.portal.model.User")) {
			tableAndColumn[0] = "User_";
			tableAndColumn[1] = "userId";
			tableAndColumn[2] = "userId";
		}
		else if (className.equals("com.liferay.portal.model.Group")) {

		}
		else if (className.equals("com.liferay.portal.model.Organization")) {

		}
		else if (className.equals(
			"com.liferay.portlet.blogs.model.BlogsEntry")) {

			tableAndColumn[0] = "BlogsEntry";
			tableAndColumn[1] = "entryId";
			tableAndColumn[2] = "entryId";
		}
		if (className.equals(
			"com.liferay.portlet.bookmarks.model.BookmarksEntry")) {

			tableAndColumn[0] = "BookmarksEntry";
			tableAndColumn[1] = "entryId";
			tableAndColumn[2] = "entryId";
		}
		if (className.equals("com.liferay.portlet.calendar.model.CalEvent")) {
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
			"com.liferay.portlet.documentlibrary.model.DLFileShortcut")) {

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

}