/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.SystemProperties;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.sql.Types;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeMappingTables.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeMappingTables extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {

		// Mappings

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeOrganizationIdColumn = new SwapUpgradeColumnImpl(
			"organizationId", AvailableMappersUtil.getOrganizationIdMapper());

		UpgradeColumn upgradeRoleIdColumn = new SwapUpgradeColumnImpl(
			"roleId", AvailableMappersUtil.getRoleIdMapper());

		UpgradeColumn upgradeUserGroupIdColumn = new SwapUpgradeColumnImpl(
			"userGroupId", AvailableMappersUtil.getUserGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		// Groups_Orgs

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ORGS, _COLUMNS_GROUPS_ORGS, upgradeGroupIdColumn,
			upgradeOrganizationIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_ORGS);

		upgradeTable.updateTable();

		// Groups_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_PERMISSIONS, _COLUMNS_GROUPS_PERMISSIONS,
			upgradeGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_PERMISSIONS);

		upgradeTable.updateTable();

		// Groups_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ROLES, _COLUMNS_GROUPS_ROLES,
			upgradeGroupIdColumn, upgradeRoleIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_ROLES);

		upgradeTable.updateTable();

		// Groups_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_USERGROUPS, _COLUMNS_GROUPS_USERGROUPS,
			upgradeGroupIdColumn, upgradeUserGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_USERGROUPS);

		upgradeTable.updateTable();

		// Roles_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_ROLES_PERMISSIONS, _COLUMNS_ROLES_PERMISSIONS,
			upgradeRoleIdColumn);

		upgradeTable.setCreateSQL(_CREATE_ROLES_PERMISSIONS);

		upgradeTable.updateTable();

		// Users_Groups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_GROUPS, _COLUMNS_USERS_GROUPS, upgradeUserIdColumn,
			upgradeGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_GROUPS);

		upgradeTable.updateTable();

		// Users_Orgs

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_ORGS, _COLUMNS_USERS_ORGS, upgradeUserIdColumn,
			upgradeOrganizationIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_ORGS);

		upgradeTable.updateTable();

		// Users_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_PERMISSIONS, _COLUMNS_USERS_PERMISSIONS,
			upgradeUserIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_PERMISSIONS);

		upgradeTable.updateTable();

		// Users_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_ROLES, _COLUMNS_USERS_ROLES, upgradeUserIdColumn,
			upgradeRoleIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_ROLES);

		upgradeTable.updateTable();

		// Users_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_USERGROUPS, _COLUMNS_USERS_USERGROUPS,
			upgradeUserIdColumn, upgradeUserGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_USERGROUPS);

		upgradeTable.updateTable();

		// Persist value mappers in case the portal was customized with
		// additional tables that referenced these ids. This allows developers
		// to retrieve the keys at a later point and build scripts to upgrade
		// the other tables.

		persistValueMapper(
			AvailableMappersUtil.getBlogsCategoryIdMapper(),
			"blogs-category-id");

		persistValueMapper(
			AvailableMappersUtil.getBlogsEntryIdMapper(), "blogs-entry-id");

		persistValueMapper(
			AvailableMappersUtil.getBookmarksFolderIdMapper(),
			"bookmarks-folder-id");

		persistValueMapper(
			AvailableMappersUtil.getBookmarksEntryIdMapper(),
			"bookmarks-entry-id");

		persistValueMapper(
			AvailableMappersUtil.getCalEventIdMapper(), "cal-event-id");

		persistValueMapper(
			AvailableMappersUtil.getCompanyIdMapper(), "company-id");

		persistValueMapper(
			AvailableMappersUtil.getContactIdMapper(), "contact-id");

		persistValueMapper(
			AvailableMappersUtil.getDLFileEntryIdMapper(), "dl-file-entry-id");

		persistValueMapper(
			AvailableMappersUtil.getDLFileShortcutIdMapper(),
			"dl-file-shortcut-id");

		persistValueMapper(
			AvailableMappersUtil.getDLFolderIdMapper(), "dl-folder-id");

		persistValueMapper(AvailableMappersUtil.getGroupIdMapper(), "group-id");

		persistValueMapper(
			AvailableMappersUtil.getIGFolderIdMapper(), "ig-folder-id");

		persistValueMapper(
			AvailableMappersUtil.getIGImageIdMapper(), "ig-image-id");

		persistValueMapper(AvailableMappersUtil.getImageIdMapper(), "image-id");

		persistValueMapper(
			AvailableMappersUtil.getJournalArticleIdMapper(),
			"journal-article-id");

		persistValueMapper(
			AvailableMappersUtil.getJournalStructureIdMapper(),
			"journal-structure-id");

		persistValueMapper(
			AvailableMappersUtil.getJournalTemplateIdMapper(),
			"journal-template-id");

		persistValueMapper(
			AvailableMappersUtil.getLayoutPlidMapper(), "layout-plid");

		persistValueMapper(
			AvailableMappersUtil.getMBCategoryIdMapper(), "mb-category-id");

		persistValueMapper(
			AvailableMappersUtil.getMBMessageIdMapper(), "mb-message-id");

		persistValueMapper(
			AvailableMappersUtil.getOrganizationIdMapper(), "organization-id");

		persistValueMapper(
			AvailableMappersUtil.getPollsQuestionIdMapper(),
			"polls-question-id");

		persistValueMapper(AvailableMappersUtil.getRoleIdMapper(), "role-id");

		persistValueMapper(
			AvailableMappersUtil.getShoppingCategoryIdMapper(),
			"shopping-category-id");

		persistValueMapper(
			AvailableMappersUtil.getShoppingItemIdMapper(), "shopping-item-id");

		persistValueMapper(
			AvailableMappersUtil.getUserGroupIdMapper(), "user-group-id");

		persistValueMapper(AvailableMappersUtil.getUserIdMapper(), "user-id");

		persistValueMapper(
			AvailableMappersUtil.getWikiNodeIdMapper(), "wiki-node-id");

		persistValueMapper(
			AvailableMappersUtil.getWikiPageIdMapper(), "wiki-page-id");
	}

	protected void persistValueMapper(ValueMapper valueMapper, String fileName)
		throws Exception {

		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		tmpDir += "/com/liferay/portal/upgrade/v4_3_0/UpgradeMappingTables";

		FileUtil.mkdirs(tmpDir);

		BufferedWriter bw = new BufferedWriter(
			new FileWriter(tmpDir + "/" + fileName + ".txt"));

		try {
			Iterator itr = valueMapper.iterator();

			while (itr.hasNext()) {
				Object oldValue = itr.next();

				Object newValue = valueMapper.getNewValue(oldValue);

				bw.write(oldValue + "=" + newValue);

				if (itr.hasNext()) {
					bw.write("\n");
				}
			}
		}
		finally {
			bw.close();
		}
	}

	private static final String _TABLE_GROUPS_ORGS = "Groups_Orgs";

	private static final String _TABLE_GROUPS_ROLES = "Groups_Roles";

	private static final String _TABLE_GROUPS_PERMISSIONS =
		"Groups_Permissions";

	private static final String _TABLE_GROUPS_USERGROUPS = "Groups_UserGroups";

	private static final String _TABLE_ROLES_PERMISSIONS = "Roles_Permissions";

	private static final String _TABLE_USERS_GROUPS = "Users_Groups";

	private static final String _TABLE_USERS_ORGS = "Users_Orgs";

	private static final String _TABLE_USERS_PERMISSIONS = "Users_Permissions";

	private static final String _TABLE_USERS_ROLES = "Users_Roles";

	private static final String _TABLE_USERS_USERGROUPS = "Users_UserGroups";

	private static final Object[][] _COLUMNS_GROUPS_ORGS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_GROUPS_PERMISSIONS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_GROUPS_ROLES = {
		{"groupId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_GROUPS_USERGROUPS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_ROLES_PERMISSIONS = {
		{"roleId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_GROUPS = {
		{"userId", new Integer(Types.BIGINT)},
		{"groupId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_ORGS = {
		{"userId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_PERMISSIONS = {
		{"userId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_ROLES = {
		{"userId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_USERS_USERGROUPS = {
		{"userId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.BIGINT)}
	};

	private static final String _CREATE_GROUPS_ORGS =
		"create table Groups_Orgs (" +
			"groupId LONG not null," +
			"organizationId LONG not null," +
			"primary key (groupId, organizationId)" +
		")";

	private static final String _CREATE_GROUPS_PERMISSIONS =
		"create table Groups_Permissions (" +
			"groupId LONG not null," +
			"permissionId LONG not null," +
			"primary key (groupId, permissionId)" +
		")";

	private static final String _CREATE_GROUPS_ROLES =
		"create table Groups_Roles (" +
			"groupId LONG not null," +
			"roleId LONG not null," +
			"primary key (groupId, roleId)" +
		")";

	private static final String _CREATE_GROUPS_USERGROUPS =
		"create table Groups_UserGroups (" +
			"groupId LONG not null," +
			"userGroupId LONG not null," +
			"primary key (groupId, userGroupId)" +
		")";

	private static final String _CREATE_ROLES_PERMISSIONS =
		"create table Roles_Permissions (" +
			"roleId LONG not null," +
			"permissionId LONG not null," +
			"primary key (roleId, permissionId)" +
		")";

	private static final String _CREATE_USERS_GROUPS =
		"create table Users_Groups (" +
			"userId LONG not null," +
			"groupId LONG not null," +
			"primary key (userId, groupId)" +
		")";

	private static final String _CREATE_USERS_ORGS =
		"create table Users_Orgs (" +
			"userId LONG not null," +
			"organizationId LONG not null," +
			"primary key (userId, organizationId)" +
		")";

	private static final String _CREATE_USERS_PERMISSIONS =
		"create table Users_Permissions (" +
			"userId LONG not null," +
			"permissionId LONG not null," +
			"primary key (userId, permissionId)" +
		")";

	private static final String _CREATE_USERS_ROLES =
		"create table Users_Roles (" +
			"userId LONG not null," +
			"roleId LONG not null," +
			"primary key (userId, roleId)" +
		")";

	private static final String _CREATE_USERS_USERGROUPS =
		"create table Users_UserGroups (" +
			"userId LONG not null," +
			"userGroupId LONG not null," +
			"primary key (userId, userGroupId)" +
		")";

	private static Log _log = LogFactory.getLog(UpgradeMappingTables.class);

}