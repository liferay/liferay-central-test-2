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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.model.impl.OrgGroupRoleImpl;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.OwnerIdMapper;
import com.liferay.portal.upgrade.v4_3_0.util.PreferencesUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PrimKeyUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.TempScopeUpgradeColumnImpl;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalContentSearchImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBStatsUserImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCartImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCategoryImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingCouponImpl;
import com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class UpgradeGroup extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeGroupIds();
			_upgradeOwnerIds();
			_upgradeResources();
			_upgradeLucene();
			_upgradeCounter();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeGroupIds() throws Exception {

		// Group_

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			GroupImpl.TABLE_NAME, GroupImpl.TABLE_COLUMNS, pkUpgradeColumn);

		upgradeTable.updateTable();

		// Groups_Orgs

		_groupIdMapper = pkUpgradeColumn.getValueMapper();

		_groupIdMapper.appendException(
			new Long(GroupImpl.DEFAULT_PARENT_GROUP_ID));
		_groupIdMapper.appendException(new Long(0));
		_groupIdMapper.appendException(StringPool.NULL);

		_ownerIdMapper = new OwnerIdMapper(_groupIdMapper);

		UpgradeColumn upgradeGroupIdColumn =
			new SwapUpgradeColumnImpl("groupId", _groupIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ORGS, _COLUMNS_GROUPS_ORGS, upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// Groups_Permissions

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_PERMISSIONS, _COLUMNS_GROUPS_PERMISSIONS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// Groups_Roles

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_ROLES, _COLUMNS_GROUPS_ROLES,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// Groups_UserGroups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_GROUPS_USERGROUPS, _COLUMNS_GROUPS_USERGROUPS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// OrgGroupPermission

		upgradeTable = new DefaultUpgradeTableImpl(
			OrgGroupPermissionImpl.TABLE_NAME,
			OrgGroupPermissionImpl.TABLE_COLUMNS, upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// OrgGroupRole

		upgradeTable = new DefaultUpgradeTableImpl(
			OrgGroupRoleImpl.TABLE_NAME, OrgGroupRoleImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// Users_Groups

		upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_USERS_GROUPS, _COLUMNS_USERS_GROUPS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// BlogsEntry

		upgradeTable = new DefaultUpgradeTableImpl(
			BlogsEntryImpl.TABLE_NAME, BlogsEntryImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// BookmarksFolder

		upgradeTable = new DefaultUpgradeTableImpl(
			BookmarksFolderImpl.TABLE_NAME, BookmarksFolderImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// CalEvent

		upgradeTable = new DefaultUpgradeTableImpl(
			CalEventImpl.TABLE_NAME, CalEventImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// DLFolder

		upgradeTable = new DefaultUpgradeTableImpl(
			DLFolderImpl.TABLE_NAME, DLFolderImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// IGFolder

		upgradeTable = new DefaultUpgradeTableImpl(
			IGFolderImpl.TABLE_NAME, IGFolderImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// JournalArticle

		upgradeTable = new DefaultUpgradeTableImpl(
			JournalArticleImpl.TABLE_NAME, JournalArticleImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// JournalStructure

		upgradeTable = new DefaultUpgradeTableImpl(
			JournalStructureImpl.TABLE_NAME, JournalStructureImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// JournalTemplate

		upgradeTable = new DefaultUpgradeTableImpl(
			JournalTemplateImpl.TABLE_NAME, JournalTemplateImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// MBCategory

		upgradeTable = new DefaultUpgradeTableImpl(
			MBCategoryImpl.TABLE_NAME, MBCategoryImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// MBStatsUser

		upgradeTable = new DefaultUpgradeTableImpl(
			MBStatsUserImpl.TABLE_NAME, MBStatsUserImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// PollsQuestion

		upgradeTable = new DefaultUpgradeTableImpl(
			PollsQuestionImpl.TABLE_NAME, PollsQuestionImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingCart

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCartImpl.TABLE_NAME, ShoppingCartImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingCategory

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCategoryImpl.TABLE_NAME, ShoppingCategoryImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingCoupon

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCouponImpl.TABLE_NAME, ShoppingCouponImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingOrder

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingOrderImpl.TABLE_NAME, ShoppingOrderImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// WikiNode

		upgradeTable = new DefaultUpgradeTableImpl(
			WikiNodeImpl.TABLE_NAME, WikiNodeImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();
	}

	private void _upgradeLucene() throws Exception {
		PropsUtil.set(PropsUtil.INDEX_ON_STARTUP, "true");
	}

	private void _upgradeOwnerIds() throws Exception {
		UpgradeColumn upgradeOwnerIdColumn =
			new SwapUpgradeColumnImpl("ownerId", _ownerIdMapper);

		UpgradeColumn upgradeGroupIdColumn =
			new SwapUpgradeColumnImpl("groupId", _groupIdMapper);

		// Layout

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			LayoutImpl.TABLE_NAME, LayoutImpl.TABLE_COLUMNS,
			upgradeOwnerIdColumn);

		upgradeTable.updateTable();

		// LayoutSet

		upgradeTable = new DefaultUpgradeTableImpl(
			LayoutSetImpl.TABLE_NAME, LayoutSetImpl.TABLE_COLUMNS,
			upgradeOwnerIdColumn, upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// PortletPreferences

		UpgradeColumn upgradePreferencesColumn =
			new PreferencesUpgradeColumnImpl(_groupIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			PortletPreferencesImpl.TABLE_NAME,
			PortletPreferencesImpl.TABLE_COLUMNS,
			upgradeOwnerIdColumn, upgradePreferencesColumn);

		upgradeTable.updateTable();

		// JournalContentSearch

		upgradeTable = new DefaultUpgradeTableImpl(
			JournalContentSearchImpl.TABLE_NAME,
			JournalContentSearchImpl.TABLE_COLUMNS, upgradeGroupIdColumn,
			upgradeOwnerIdColumn);

		upgradeTable.updateTable();
	}

	private void _upgradeResources() throws Exception {
		TempScopeUpgradeColumnImpl upgradeScopeColumn =
			new TempScopeUpgradeColumnImpl();

		UpgradeColumn upgradePrimKeyColumn =
			new PrimKeyUpgradeColumnImpl(
				upgradeScopeColumn, _groupIdMapper, _ownerIdMapper);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ResourceImpl.TABLE_NAME, ResourceImpl.TABLE_COLUMNS,
			upgradeScopeColumn, upgradePrimKeyColumn);

		upgradeTable.updateTable();
	}

	private void _upgradeCounter() throws Exception {
		CounterLocalServiceUtil.reset(Group.class.getName());
	}

	private ValueMapper _groupIdMapper;

	private ValueMapper _ownerIdMapper;

	private static final String _TABLE_GROUPS_ORGS = "Groups_Orgs";

	private static final String _TABLE_GROUPS_PERMISSIONS =
		"Groups_Permissions";

	private static final String _TABLE_GROUPS_ROLES = "Groups_Roles";

	private static final String _TABLE_GROUPS_USERGROUPS = "Groups_UserGroups";

	private static final String _TABLE_USERS_GROUPS = "Users_Groups";

	private static final Object[][] _COLUMNS_GROUPS_ORGS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"organizationId", new Integer(Types.VARCHAR)}
	};

	private static final Object[][] _COLUMNS_GROUPS_PERMISSIONS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"permissionId", new Integer(Types.BIGINT)}
	};

	private static final Object[][] _COLUMNS_GROUPS_ROLES = {
		{"groupId", new Integer(Types.BIGINT)},
		{"roleId", new Integer(Types.VARCHAR)}
	};

	private static final Object[][] _COLUMNS_GROUPS_USERGROUPS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"userGroupId", new Integer(Types.VARCHAR)}
	};

	private static final Object[][] _COLUMNS_USERS_GROUPS = {
		{"groupId", new Integer(Types.BIGINT)},
		{"userId", new Integer(Types.VARCHAR)}
	};

	private static Log _log = LogFactory.getLog(UpgradeGroup.class);

}