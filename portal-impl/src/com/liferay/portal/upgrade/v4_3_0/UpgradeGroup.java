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

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.LayoutSetImpl;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.model.impl.OrgGroupRoleImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultPKMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SkipUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.ValueMapperFactory;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ClassNameIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKContainer;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.LayoutOwnerIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.LayoutPlidUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
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
import com.liferay.util.ArrayUtil;
import com.liferay.util.CollectionFactory;

import java.sql.Types;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeGroup.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeGroup extends UpgradeProcess {

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

		// Group_

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(
			"groupId", true);

		ClassNameIdUpgradeColumnImpl classNameIdColumn =
			new ClassNameIdUpgradeColumnImpl();

		Map classPKContainers = CollectionFactory.getHashMap();

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Organization.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getOrganizationIdMapper(), true));

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(User.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getUserIdMapper(), false));

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(UserGroup.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getUserGroupIdMapper(), true));

		UpgradeColumn upgradeClassPKColumn = new ClassPKUpgradeColumnImpl(
			classNameIdColumn, classPKContainers);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			GroupImpl.TABLE_NAME, GroupImpl.TABLE_COLUMNS, pkUpgradeColumn,
			classNameIdColumn, upgradeClassPKColumn);

		upgradeTable.updateTable();

		ValueMapper groupIdMapper = new DefaultPKMapper(
			pkUpgradeColumn.getValueMapper());

		AvailableMappersUtil.setGroupIdMapper(groupIdMapper);

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", groupIdMapper);

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

		// Layout

		TempUpgradeColumnImpl upgradeLayoutOwnerIdColumn =
			new TempUpgradeColumnImpl("ownerId");

		LayoutOwnerIdUpgradeColumnImpl upgradeLayoutOwnerIdGroupIdColumn =
			new LayoutOwnerIdUpgradeColumnImpl(
				"groupId", upgradeLayoutOwnerIdColumn, groupIdMapper);

		LayoutOwnerIdUpgradeColumnImpl upgradeLayoutOwnerIdPrivateLayoutColumn =
			new LayoutOwnerIdUpgradeColumnImpl(
				"privateLayout", upgradeLayoutOwnerIdColumn, groupIdMapper);

		TempUpgradeColumnImpl upgradeLayoutIdColumn =
			new TempUpgradeColumnImpl("layoutId");

		ValueMapper layoutPlidMapper = ValueMapperFactory.getValueMapper();

		AvailableMappersUtil.setLayoutPlidMapper(layoutPlidMapper);

		UpgradeColumn upgradeLayoutPlidColumn = new LayoutPlidUpgradeColumnImpl(
			upgradeLayoutOwnerIdGroupIdColumn,
			upgradeLayoutOwnerIdPrivateLayoutColumn, upgradeLayoutIdColumn,
			layoutPlidMapper);

		Object[][] layoutColumns1 = {{"ownerId", new Integer(Types.VARCHAR)}};
		Object[][] layoutColumns2 =
			(Object[][])LayoutImpl.TABLE_COLUMNS.clone();
		Object[][] layoutColumns =
			new Object[layoutColumns1.length + layoutColumns2.length][];

		ArrayUtil.combine(layoutColumns1, layoutColumns2, layoutColumns);

		upgradeTable = new DefaultUpgradeTableImpl(
			LayoutImpl.TABLE_NAME, layoutColumns, upgradeLayoutOwnerIdColumn,
			upgradeLayoutOwnerIdGroupIdColumn,
			upgradeLayoutOwnerIdPrivateLayoutColumn, upgradeLayoutIdColumn,
			upgradeLayoutPlidColumn);

		upgradeTable.updateTable();

		// LayoutSet

		Object[][] layoutSetColumns1 =
			{{"ownerId", new Integer(Types.VARCHAR)}};
		Object[][] layoutSetColumns2 =
			(Object[][])LayoutSetImpl.TABLE_COLUMNS.clone();
		Object[][] layoutSetColumns =
			new Object[layoutSetColumns1.length + layoutSetColumns2.length][];

		ArrayUtil.combine(
			layoutSetColumns1, layoutSetColumns2, layoutSetColumns);

		upgradeTable = new DefaultUpgradeTableImpl(
			LayoutSetImpl.TABLE_NAME, layoutSetColumns,
			new PKUpgradeColumnImpl("layoutSetId", false),
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

		// PollsQuestion

		upgradeTable = new DefaultUpgradeTableImpl(
			PollsQuestionImpl.TABLE_NAME, PollsQuestionImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingCart

		UpgradeColumn upgradeCartIdColumn = new SkipUpgradeColumnImpl(
			"cartId", new Integer(Types.VARCHAR));

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCartImpl.TABLE_NAME, ShoppingCartImpl.TABLE_COLUMNS,
			upgradeCartIdColumn, upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingCategory

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCategoryImpl.TABLE_NAME, ShoppingCategoryImpl.TABLE_COLUMNS,
			upgradeGroupIdColumn);

		upgradeTable.updateTable();

		// ShoppingCoupon

		UpgradeColumn upgradeCouponIdColumn = new SkipUpgradeColumnImpl(
			"couponId", new Integer(Types.VARCHAR));

		upgradeTable = new DefaultUpgradeTableImpl(
			ShoppingCouponImpl.TABLE_NAME, ShoppingCouponImpl.TABLE_COLUMNS,
			upgradeCouponIdColumn, upgradeGroupIdColumn);

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

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter_column_type Group_ classNameId LONG",
		"alter_column_type Group_ classPK LONG",
		"update Group_ set name = classPK where classPK > 0",

		"alter table Layout drop primary key",
		"alter table Layout add primary key (plid)",
		"alter table Layout drop ownerId",

		"alter table LayoutSet drop primary key",
		"alter table LayoutSet add primary key (layoutSetId)",
		"alter table LayoutSet drop ownerId"
	};

	private static Log _log = LogFactory.getLog(UpgradeGroup.class);

}