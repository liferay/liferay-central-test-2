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

package com.liferay.portal.upgrade.v4_3_5;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePermission extends UpgradeProcess {

	protected void copyPermissions(long defaultUserId, long guestGroupId)
		throws Exception {

		if ((defaultUserId == 0) || (guestGroupId == 0)) {
			return;
		}

		runSQL("delete from Users_Permissions where userId = " + defaultUserId);

		runSQL(
			"insert into Users_Permissions (userId, permissionId) select " +
				defaultUserId + ", Groups_Permissions.permissionId from " +
					"Groups_Permissions where groupId = " + guestGroupId);

		for (long plid : getPlids(guestGroupId)) {
			deletePortletPermissionIds(plid, guestGroupId);
		}

		deletePermissionIds(
			Layout.class.getName(), "Layout", "plid", guestGroupId);

		deletePermissionIds(
			BlogsEntry.class.getName(), "BlogsEntry", "entryId", guestGroupId);

		deletePermissionIds(
			BookmarksFolder.class.getName(), "BookmarksFolder", "folderId",
			guestGroupId);
		deletePermissionIds(
			BookmarksEntry.class.getName(), "BookmarksEntry", "entryId",
			"BookmarksFolder", "folderId", guestGroupId);

		deletePermissionIds(
			CalEvent.class.getName(), "CalEvent", "eventId", guestGroupId);

		deletePermissionIds(
			DLFolder.class.getName(), "DLFolder", "folderId", guestGroupId);
		deletePermissionIds(
			DLFileEntry.class.getName(), "DLFileEntry", "fileEntryId",
			"DLFolder", "folderId", guestGroupId);
		deletePermissionIds(
			DLFileShortcut.class.getName(), "DLFileShortcut", "fileShortcutId",
			"DLFolder", "folderId", guestGroupId);

		deletePermissionIds(
			IGFolder.class.getName(), "IGFolder", "folderId", guestGroupId);
		deletePermissionIds(
			IGImage.class.getName(), "IGImage", "imageId", "IGFolder",
			"folderId", guestGroupId);

		deletePermissionIds(
			JournalArticle.class.getName(), "JournalArticle", "resourcePrimKey",
			guestGroupId);
		deletePermissionIds(
			JournalStructure.class.getName(), "JournalStructure", "id_",
			guestGroupId);
		deletePermissionIds(
			JournalTemplate.class.getName(), "JournalTemplate", "id_",
			guestGroupId);

		deletePermissionIds(
			MBCategory.class.getName(), "MBCategory", "categoryId",
			guestGroupId);
		deletePermissionIds(
			MBMessage.class.getName(), "MBMessage", "messageId", "MBCategory",
			"categoryId", guestGroupId);

		deletePermissionIds(
			PollsQuestion.class.getName(), "PollsQuestion", "questionId",
			guestGroupId);

		deletePermissionIds(
			SCFrameworkVersion.class.getName(), "SCFrameworkVersion",
			"frameworkVersionId", guestGroupId);
		deletePermissionIds(
			SCProductEntry.class.getName(), "SCProductEntry", "productEntryId",
			guestGroupId);

		deletePermissionIds(
			ShoppingCategory.class.getName(), "ShoppingCategory", "categoryId",
			guestGroupId);
		deletePermissionIds(
			ShoppingItem.class.getName(), "ShoppingItem", "itemId",
			"ShoppingCategory", "categoryId", guestGroupId);

		deletePermissionIds(
			WikiNode.class.getName(), "WikiNode", "nodeId", guestGroupId);
		deletePermissionIds(
			WikiPage.class.getName(), "WikiPage", "resourcePrimKey", "WikiNode",
			"nodeId", guestGroupId);
	}

	protected void deletePermissionIds(
			String className, String tableName, String tablePKCol,
			long guestGroupId)
		throws Exception {

		List<Long> permissionIds = getPermissionIds(
			className, tableName, tablePKCol, guestGroupId);

		deletePermissionIds(permissionIds, guestGroupId);
	}

	protected void deletePermissionIds(
			String className, String tableName1, String tablePKCol1,
			String tableName2, String tablePKCol2, long guestGroupId)
		throws Exception {

		List<Long> permissionIds = getPermissionIds(
			className, tableName1, tablePKCol1, tableName2, tablePKCol2,
			guestGroupId);

		deletePermissionIds(permissionIds, guestGroupId);
	}

	protected void deletePermissionIds(
			List<Long> permissionIds, long guestGroupId)
		throws Exception {

		for (long permissionId : permissionIds) {
			runSQL(
				"delete from Groups_Permissions where groupId = " +
					guestGroupId + " and permissionId = " + permissionId);
		}
	}

	protected void deletePortletPermissionIds(long plid, long guestGroupId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select primKey from Resource_ where primKey like ?");

			ps.setString(1, plid + PortletConstants.LAYOUT_SEPARATOR + "%");

			rs = ps.executeQuery();

			while (rs.next()) {
				String primKey = rs.getString("primKey");

				List<Long> permissionIds = getPermissionIds(
					primKey, guestGroupId);

				deletePermissionIds(permissionIds, guestGroupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void doUpgrade() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			long defaultUserId = getDefaultUserId(companyId);
			long guestGroupId = getGuestGroupId(companyId);

			copyPermissions(defaultUserId, guestGroupId);
		}
	}

	protected long getDefaultUserId(long companyId) throws Exception {
		long userId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_DEFAULT_USER_ID);

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			while (rs.next()) {
				userId = rs.getLong("userId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return userId;
	}

	protected long getGuestGroupId(long companyId) throws Exception {
		long groupId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_GUEST_GROUP_ID);

			ps.setLong(1, companyId);
			ps.setString(2, GroupConstants.GUEST);

			rs = ps.executeQuery();

			while (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected List<Long> getPermissionIds(String primKey, long guestGroupId)
		throws Exception {

		List<Long> permissionIds = new ArrayList<Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_PERMISSION_IDS_1);

			ps.setLong(1, guestGroupId);
			ps.setString(2, primKey);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				permissionIds.add(permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return permissionIds;
	}

	protected List<Long> getPermissionIds(
			String className, String tableName, String tablePKCol,
			long guestGroupId)
		throws Exception {

		List<Long> permissionIds = new ArrayList<Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select " + tablePKCol + " from " + tableName + " " +
				"where groupId != " + guestGroupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String primKey = String.valueOf(rs.getLong(tablePKCol));

				permissionIds.addAll(
					getPermissionIds(className, primKey, guestGroupId));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return permissionIds;
	}

	protected List<Long> getPermissionIds(
			String className, String tableName1, String tablePKCol1,
			String tableName2, String tablePKCol2, long guestGroupId)
		throws Exception {

		List<Long> permissionIds = new ArrayList<Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select " + tablePKCol1 + " from " + tableName1 + " " +
				"inner join " + tableName2 + " on " + tableName2 + "." +
					tablePKCol2 + " = " + tableName1 + "." + tablePKCol2 + " " +
				"where groupId != " + guestGroupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String primKey = String.valueOf(rs.getLong(tablePKCol1));

				permissionIds.addAll(
					getPermissionIds(className, primKey, guestGroupId));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return permissionIds;
	}

	protected List<Long> getPermissionIds(
			String className, String primKey, long guestGroupId)
		throws Exception {

		List<Long> permissionIds = new ArrayList<Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_PERMISSION_IDS_2);

			ps.setLong(1, guestGroupId);
			ps.setString(2, primKey);
			ps.setString(3, className);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				permissionIds.add(permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return permissionIds;
	}

	protected List<Long> getPlids(long guestGroupId) throws Exception {
		List<Long> plids = new ArrayList<Long>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(_GET_PLIDS);

			ps.setLong(1, guestGroupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");

				plids.add(plid);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return plids;
	}

	private static final String _GET_DEFAULT_USER_ID =
		"select userId from User_ where companyId = ? and defaultUser = ?";

	private static final String _GET_GUEST_GROUP_ID =
		"select groupId from Group_ where companyId = ? and name = ?";

	private static final String _GET_PERMISSION_IDS_1 =
		"select Groups_Permissions.permissionId from Groups_Permissions " +
		"inner join Permission_ on Permission_.permissionId = " +
			"Groups_Permissions.permissionId " +
		"inner join Resource_ on Resource_.resourceId = " +
			"Permission_.resourceId " +
		"inner join ResourceCode on ResourceCode.codeId = Resource_.codeId " +
		"where Groups_Permissions.groupId = ? and Resource_.primKey = ?";

	private static final String _GET_PERMISSION_IDS_2 =
		"select Groups_Permissions.permissionId from Groups_Permissions " +
		"inner join Permission_ on Permission_.permissionId = " +
			"Groups_Permissions.permissionId " +
		"inner join Resource_ on Resource_.resourceId = " +
			"Permission_.resourceId " +
		"inner join ResourceCode on ResourceCode.codeId = Resource_.codeId " +
		"where Groups_Permissions.groupId = ? and Resource_.primKey = ? and " +
			"ResourceCode.name = ?";

	private static final String _GET_PLIDS =
		"select plid from Layout where Layout.groupId != ?";

}