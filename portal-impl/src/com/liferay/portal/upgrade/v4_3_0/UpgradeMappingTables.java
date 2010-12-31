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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ValueMapperUtil;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeMappingTables extends UpgradeProcess {

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

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_GROUPS_ORGS, _COLUMNS_GROUPS_ORGS, upgradeGroupIdColumn,
			upgradeOrganizationIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_ORGS);

		upgradeTable.updateTable();

		// Groups_Permissions

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_GROUPS_PERMISSIONS, _COLUMNS_GROUPS_PERMISSIONS,
			upgradeGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_PERMISSIONS);

		upgradeTable.updateTable();

		// Groups_Roles

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_GROUPS_ROLES, _COLUMNS_GROUPS_ROLES,
			upgradeGroupIdColumn, upgradeRoleIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_ROLES);

		upgradeTable.updateTable();

		// Groups_UserGroups

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_GROUPS_USERGROUPS, _COLUMNS_GROUPS_USERGROUPS,
			upgradeGroupIdColumn, upgradeUserGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_GROUPS_USERGROUPS);

		upgradeTable.updateTable();

		// Roles_Permissions

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_ROLES_PERMISSIONS, _COLUMNS_ROLES_PERMISSIONS,
			upgradeRoleIdColumn);

		upgradeTable.setCreateSQL(_CREATE_ROLES_PERMISSIONS);

		upgradeTable.updateTable();

		// Users_Groups

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_USERS_GROUPS, _COLUMNS_USERS_GROUPS, upgradeUserIdColumn,
			upgradeGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_GROUPS);

		upgradeTable.updateTable();

		// Users_Orgs

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_USERS_ORGS, _COLUMNS_USERS_ORGS, upgradeUserIdColumn,
			upgradeOrganizationIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_ORGS);

		upgradeTable.updateTable();

		// Users_Permissions

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_USERS_PERMISSIONS, _COLUMNS_USERS_PERMISSIONS,
			upgradeUserIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_PERMISSIONS);

		upgradeTable.updateTable();

		// Users_Roles

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_USERS_ROLES, _COLUMNS_USERS_ROLES, upgradeUserIdColumn,
			upgradeRoleIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_ROLES);

		upgradeTable.updateTable();

		// Users_UserGroups

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			_TABLE_USERS_USERGROUPS, _COLUMNS_USERS_USERGROUPS,
			upgradeUserIdColumn, upgradeUserGroupIdColumn);

		upgradeTable.setCreateSQL(_CREATE_USERS_USERGROUPS);

		upgradeTable.updateTable();

		// Persist value mappers in case the portal was customized with
		// additional tables that referenced these ids. This allows developers
		// to retrieve the keys at a later point and build scripts to upgrade
		// the other tables.

		ValueMapperUtil.persist(
			AvailableMappersUtil.getBlogsEntryIdMapper(), "blogs-entry-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getBookmarksFolderIdMapper(),
			"bookmarks-folder-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getBookmarksEntryIdMapper(),
			"bookmarks-entry-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getCalEventIdMapper(), "cal-event-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getCompanyIdMapper(), "company-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getContactIdMapper(), "contact-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getDLFileEntryIdMapper(), "dl-file-entry-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getDLFileShortcutIdMapper(),
			"dl-file-shortcut-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getDLFolderIdMapper(), "dl-folder-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getGroupIdMapper(), "group-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getIGFolderIdMapper(), "ig-folder-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getIGImageIdMapper(), "ig-image-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getImageIdMapper(), "image-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getJournalArticleIdMapper(),
			"journal-article-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getJournalStructureIdMapper(),
			"journal-structure-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getJournalTemplateIdMapper(),
			"journal-template-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getLayoutPlidMapper(), "layout-plid");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getMBCategoryIdMapper(), "mb-category-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getMBMessageIdMapper(), "mb-message-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getMBThreadIdMapper(), "mb-thread-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getOrganizationIdMapper(), "organization-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getPollsQuestionIdMapper(),
			"polls-question-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getRoleIdMapper(), "role-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getShoppingCategoryIdMapper(),
			"shopping-category-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getShoppingItemIdMapper(), "shopping-item-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getUserGroupIdMapper(), "user-group-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getUserIdMapper(), "user-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getWikiNodeIdMapper(), "wiki-node-id");

		ValueMapperUtil.persist(
			AvailableMappersUtil.getWikiPageIdMapper(), "wiki-page-id");
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

}