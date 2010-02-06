/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="UpgradeMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
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

		upgradeTable.updateTable();

		// MBStatsUser

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			MBStatsUserTable.TABLE_NAME, MBStatsUserTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("statsUserId", false),
			upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(MBStatsUserTable.TABLE_SQL_CREATE);

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

		upgradeTable.updateTable();
	}

}