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

import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 */
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
					{"SCProductScreenshot", "fullImageId"},
					{"SCProductScreenshot", "thumbnailId"},
					{"User_", "portraitId"}
				}),
			new TableUpdater("MBStatsUser", "Group_", "groupId"),
			new TableUpdater("OrgGroupRole", "Organization_", "organizationId"),
			new TableUpdater("OrgLabor", "Organization_", "organizationId"),
			new TableUpdater(
				"PasswordPolicyRel", "PasswordPolicy", "passwordPolicyId"),
			new TableUpdater("PasswordTracker", "User_", "userId"),
			new TableUpdater(
				"PortletPreferences", "portletId",
				new String[][] {{"Layout", "plid"}, {"Portlet", "portletId"}}),
			new TableUpdater(
				"RatingsStats", "classPK",
				new String[][] {
					{"BookmarksEntry", "entryId"},
					{"BookmarksFolder", "folderId"}, {"BlogsEntry", "entryId"},
					{"CalendarBooking", "calendarBookingId"},
					{"DDLRecord", "recordId"}, {"DLFileEntry", "fileEntryId"},
					{"DLFolder", "folderId"}, {"JournalArticle", "articleId"},
					{"JournalFolder", "folderId"},
					{"MBDiscussion", "discussionId"},
					{"MBMessage", "messageId"}, {"WikiPage", "pageId"}
				}),
			new TableUpdater(
				"ResourceBlockPermission", "ResourceBlock", "resourceBlockId"),
			new TableUpdater(
				"SCFrameworkVersi_SCProductVers", "SCFrameworkVersion",
				"frameworkVersionId"),
			new SCLicenseTableUpdater("SCLicense"),
			new TableUpdater(
				"SCLicenses_SCProductEntries", "SCProductEntry",
				"productEntryId"),
			new TableUpdater("TrashVersion", "TrashEntry", "entryId"),
			new TableUpdater("UserGroupGroupRole", "UserGroup", "userGroupId"),
			new TableUpdater("UserGroupRole", "UserGroup", "userGroupId"),
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
		public void update() throws IOException, SQLException {

			// DLFileEntries

			String updateDLFileEntry =
				"update DLSyncEvent " +
					"set companyId = (select dlfe.companyId " +
						"from DLFileEntry dlfe " +
						"where DLSyncEvent.type_='file' and " +
						"dlfe.fileEntryId = DLSyncEvent.typePK);";

			runSQL(updateDLFileEntry);

			// DLFolders

			String updateDLFolder =
				"update DLSyncEvent " +
					"set companyId = (select dlf.companyId " +
					"from DLFolder dlf " +
					"where DLSyncEvent.type_='folder' and " +
					"dlf.folderId = DLSyncEvent.typePK);";

			runSQL(updateDLFolder);
		}

	}

	protected class SCLicenseTableUpdater extends TableUpdater {

		public SCLicenseTableUpdater(String tableName) {
			super(tableName, "", "");
		}

		@Override
		protected String getSelectSQL(
			String foreignTableName, String foreignColumnName) {

			StringBundler sb = new StringBundler(6);
			
			sb.append("select SCProductEntry.companyId from ");
			sb.append("SCLicenses_SCProductEntries, SCProductEntry where ");
			sb.append("SCLicenses_SCProductEntries.licenseId = ");
			sb.append("SCLicense.licenseId and ");
			sb.append("SCLicenses_SCProductEntries.productEntryId = ");
			sb.append("SCProductEntry.productEntryId");
			
			return sb.toString();
		}

	}

}