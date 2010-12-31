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
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.LazyPKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ClassNameIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKContainer;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.MBCategoryIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.MBCategoryTable;
import com.liferay.portal.upgrade.v4_3_0.util.MBDiscussionTable;
import com.liferay.portal.upgrade.v4_3_0.util.MBMessageAttachmentsUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.MBMessageFlagTable;
import com.liferay.portal.upgrade.v4_3_0.util.MBMessageTable;
import com.liferay.portal.upgrade.v4_3_0.util.MBStatsUserTable;
import com.liferay.portal.upgrade.v4_3_0.util.MBThreadTable;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// MBCategory

		UpgradeColumn upgradeCompanyIdColumn = new SwapUpgradeColumnImpl(
			"companyId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getCompanyIdMapper());

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn =
			new MBCategoryIdUpgradeColumnImpl();

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBCategoryTable.TABLE_NAME, MBCategoryTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(MBCategoryTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(MBCategoryTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper categoryIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setMBCategoryIdMapper(categoryIdMapper);

		UpgradeColumn upgradeParentCategoryIdColumn = new SwapUpgradeColumnImpl(
			"parentCategoryId", categoryIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBCategoryTable.TABLE_NAME, MBCategoryTable.TABLE_COLUMNS,
			upgradeParentCategoryIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeCategoryIdColumn = new SwapUpgradeColumnImpl(
			"categoryId", categoryIdMapper);

		// MBMessage

		upgradePKColumn = new PKUpgradeColumnImpl("messageId", true);

		PKUpgradeColumnImpl upgradeThreadIdPKColumn =
			new LazyPKUpgradeColumnImpl("threadId");

		UpgradeColumn upgradeAttachmentsColumn =
			new MBMessageAttachmentsUpgradeColumnImpl(
				upgradePKColumn, upgradeCompanyIdColumn,
			upgradeThreadIdPKColumn);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBMessageTable.TABLE_NAME, MBMessageTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeCompanyIdColumn, upgradeUserIdColumn,
			upgradeCategoryIdColumn, upgradeThreadIdPKColumn,
			upgradeAttachmentsColumn);

		upgradeTable.setCreateSQL(MBMessageTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(MBMessageTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		ValueMapper messageIdMapper = new DefaultPKMapper(
			upgradePKColumn.getValueMapper());

		AvailableMappersUtil.setMBMessageIdMapper(messageIdMapper);

		ValueMapper threadIdMapper = upgradeThreadIdPKColumn.getValueMapper();

		AvailableMappersUtil.setMBThreadIdMapper(threadIdMapper);

		UpgradeColumn upgradeParentMessageIdColumn = new SwapUpgradeColumnImpl(
			"parentMessageId", messageIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBMessageTable.TABLE_NAME, MBMessageTable.TABLE_COLUMNS,
			upgradeParentMessageIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeMessageIdColumn = new SwapUpgradeColumnImpl(
			"messageId", messageIdMapper);

		UpgradeColumn upgradeRootMessageIdColumn = new SwapUpgradeColumnImpl(
			"rootMessageId", messageIdMapper);

		// MBMessageFlag

		upgradePKColumn = new PKUpgradeColumnImpl("messageFlagId", true);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBMessageFlagTable.TABLE_NAME, MBMessageFlagTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeUserIdColumn, upgradeMessageIdColumn);

		upgradeTable.setCreateSQL(MBMessageFlagTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(MBMessageFlagTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// MBStatsUser

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBStatsUserTable.TABLE_NAME, MBStatsUserTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("statsUserId", false),
			upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(MBStatsUserTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(MBStatsUserTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// MBThread

		UpgradeColumn upgradeThreadIdColumn = new SwapUpgradeColumnImpl(
			"threadId", threadIdMapper);

		UpgradeColumn upgradeLastPostByUserIdColumn = new SwapUpgradeColumnImpl(
			"lastPostByUserId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBThreadTable.TABLE_NAME, MBThreadTable.TABLE_COLUMNS,
			upgradeThreadIdColumn, upgradeCategoryIdColumn,
			upgradeRootMessageIdColumn, upgradeLastPostByUserIdColumn);

		upgradeTable.setCreateSQL(MBThreadTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(MBThreadTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();

		// MBDiscussion

		ClassNameIdUpgradeColumnImpl classNameIdColumn =
			new ClassNameIdUpgradeColumnImpl();

		Map<Long, ClassPKContainer> classPKContainers =
			new HashMap<Long, ClassPKContainer>();

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(BlogsEntry.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getBlogsEntryIdMapper(), true));

		UpgradeColumn upgradeClassPKColumn = new ClassPKUpgradeColumnImpl(
			classNameIdColumn, classPKContainers);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBDiscussionTable.TABLE_NAME, MBDiscussionTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("discussionId", false),
			classNameIdColumn, upgradeClassPKColumn, upgradeThreadIdColumn);

		upgradeTable.setCreateSQL(MBDiscussionTable.TABLE_SQL_CREATE);
		upgradeTable.setIndexesSQL(MBDiscussionTable.TABLE_SQL_ADD_INDEXES);

		upgradeTable.updateTable();
	}

}