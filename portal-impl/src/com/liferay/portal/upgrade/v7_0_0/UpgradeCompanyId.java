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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("deprecation")
public class UpgradeCompanyId
	extends com.liferay.portal.upgrade.util.UpgradeCompanyId {

	@Override
	protected TableUpdater[] getTableUpdaters() {
		return new TableUpdater[] {
			new TableUpdater("AnnouncementsFlag", "User_", "userId"),
			new TableUpdater(
				"AssetEntries_AssetCategories", "AssetCategory", "categoryId"),
			new TableUpdater("AssetEntries_AssetTags", "AssetTag", "tagId"),
			new TableUpdater("AssetTagStats", "AssetTag", "tagId"),
			new TableUpdater("BrowserTracker", "User_", "userId"),
			new TableUpdater(
				"DLFileEntryMetadata", "DLFileEntry", "fileEntryId"),
			new TableUpdater(
				"DLFileEntryTypes_DLFolders", "DLFolder", "folderId"),
			new DLSyncEventTableUpdater("DLSyncEvent"),
			new TableUpdater("Groups_Orgs", "Group_", "groupId"),
			new TableUpdater("Groups_Roles", "Group_", "groupId"),
			new TableUpdater("Groups_UserGroups", "Group_", "groupId"),
			new TableUpdater(
				"Image", "imageId",
				new String[][] {
					{"BlogsEntry", "smallImageId"}, {"Company", "logoId"},
					{"DDMTemplate", "smallImageId"},
					{"DLFileEntry", "largeImageId"},
					{"JournalArticle", "smallImageId"},
					{"Layout", "iconImageId"},
					{"LayoutRevision", "iconImageId"},
					{"LayoutSetBranch", "logoId"}, {"Organization_", "logoId"},
					{"User_", "portraitId"}
				}),
			new TableUpdater("MBStatsUser", "Group_", "groupId"),
			new TableUpdater("OrgGroupRole", "Organization_", "organizationId"),
			new TableUpdater("OrgLabor", "Organization_", "organizationId"),
			new TableUpdater(
				"PasswordPolicyRel", "PasswordPolicy", "passwordPolicyId"),
			new TableUpdater("PasswordTracker", "User_", "userId"),
			new PortletPreferencesTableUpdater("PortletPreferences"),
			new TableUpdater(
				"RatingsStats", "classPK",
				new String[][] {
					{"BookmarksEntry", "entryId"},
					{"BookmarksFolder", "folderId"}, {"BlogsEntry", "entryId"},
					{"DDLRecord", "recordId"}, {"DLFileEntry", "fileEntryId"},
					{"DLFolder", "folderId"},
					{"JournalArticle", "resourcePrimKey"},
					{"JournalFolder", "folderId"},
					{"MBDiscussion", "discussionId"},
					{"MBMessage", "messageId"}, {"WikiPage", "pageId"}
				}),
			new TableUpdater(
				"ResourceBlockPermission", "ResourceBlock", "resourceBlockId"),
			new TableUpdater("TrashVersion", "TrashEntry", "entryId"),
			new TableUpdater("UserGroupGroupRole", "UserGroup", "userGroupId"),
			new TableUpdater("UserGroupRole", "User_", "userId"),
			new TableUpdater("UserGroups_Teams", "UserGroup", "userGroupId"),
			new TableUpdater("UserIdMapper", "User_", "userId"),
			new TableUpdater("Users_Groups", "User_", "userId"),
			new TableUpdater("Users_Orgs", "User_", "userId"),
			new TableUpdater("Users_Roles", "User_", "userId"),
			new TableUpdater("Users_Teams", "User_", "userId"),
			new TableUpdater("Users_UserGroups", "User_", "userId"),
			new TableUpdater("UserTrackerPath", "UserTracker", "userTrackerId")
		};
	}

	protected class DLSyncEventTableUpdater extends TableUpdater {

		public DLSyncEventTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		public void update(Connection connection)
			throws IOException, SQLException {

			// DLFileEntry

			String selectSQL =
				"select companyId from DLFileEntry where DLSyncEvent.type_ = " +
					"'file' and DLFileEntry.fileEntryId = DLSyncEvent.typePK";

			runSQL(connection, getUpdateSQL(selectSQL));

			// DLFolder

			selectSQL =
				"select companyId from DLFolder where DLSyncEvent.type_ = " +
					"'folder' and DLFolder.folderId = DLSyncEvent.typePK";

			runSQL(connection, getUpdateSQL(selectSQL));
		}

	}

	protected class PortletPreferencesTableUpdater extends TableUpdater {

		public PortletPreferencesTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		public void update(Connection connection)
			throws IOException, SQLException {

			List<Long> companyIds = getCompanyIds(connection);

			if (companyIds.size() == 1) {
				String selectSQL = String.valueOf(companyIds.get(0));

				runSQL(connection, getUpdateSQL(selectSQL));

				return;
			}

			// Company

			String updateSQL = _getUpdateSQL(
				"Company", "companyId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_COMPANY);

			runSQL(connection, updateSQL);

			// Group

			updateSQL = _getUpdateSQL(
				"Group_", "groupId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_GROUP);

			runSQL(connection, updateSQL);

			// Layout

			updateSQL = _getUpdateSQL(
				"Layout", "plid", "plid", PortletKeys.PREFS_OWNER_TYPE_LAYOUT);

			runSQL(connection, updateSQL);

			// LayoutRevision

			updateSQL = _getUpdateSQL(
				"LayoutRevision", "layoutRevisionId", "plid",
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT);

			runSQL(connection, updateSQL);

			// Organization

			updateSQL = _getUpdateSQL(
				"Organization_", "organizationId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION);

			runSQL(connection, updateSQL);

			// User_

			updateSQL = _getUpdateSQL(
				"User_", "userId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_USER);

			runSQL(connection, updateSQL);

			// PortletItem

			updateSQL = _getUpdateSQL(
				"PortletItem", "portletItemId", "ownerId",
				PortletKeys.PREFS_OWNER_TYPE_ARCHIVED);

			runSQL(connection, updateSQL);
		}

		private String _getSelectSQL(
				String foreignTableName, String foreignColumnName,
				String columnName)
			throws IOException, SQLException {

			List<Long> companyIds = getCompanyIds(connection, foreignTableName);

			if (companyIds.size() == 1) {
				return String.valueOf(companyIds.get(0));
			}

			StringBundler sb = new StringBundler(10);

			sb.append("select companyId from ");
			sb.append(foreignTableName);
			sb.append(" where ");
			sb.append(foreignTableName);
			sb.append(".");
			sb.append(foreignColumnName);
			sb.append(" = ");
			sb.append(getTableName());
			sb.append(".");
			sb.append(columnName);

			return sb.toString();
		}

		private String _getUpdateSQL(
				String foreignTableName, String foreignColumnName,
				String columnName, int ownerType)
			throws IOException, SQLException {

			String selectSQL = _getSelectSQL(
				foreignTableName, foreignColumnName, columnName);

			StringBundler sb = new StringBundler(4);

			sb.append(getUpdateSQL(selectSQL));
			sb.append(" where ownerType = ");
			sb.append(ownerType);
			sb.append(" and (companyId is null or companyId = 0)");

			return sb.toString();
		}

	}

}