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
import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultPKMapper;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBDiscussionImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeMessageBoards.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeMessageBoards extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgrade() throws Exception {

		// MBCategory

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			MBCategoryImpl.TABLE_NAME, MBCategoryImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper categoryIdMapper = new DefaultPKMapper(
			pkUpgradeColumn.getValueMapper());

		UpgradeColumn upgradeParentCategoryIdColumn = new SwapUpgradeColumnImpl(
			"parentCategoryId", categoryIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			MBCategoryImpl.TABLE_NAME, MBCategoryImpl.TABLE_COLUMNS,
			upgradeParentCategoryIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeCategoryIdColumn = new SwapUpgradeColumnImpl(
			"categoryId", categoryIdMapper);

		// MBMessage

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			MBMessageImpl.TABLE_NAME, MBMessageImpl.TABLE_COLUMNS,
			pkUpgradeColumn, upgradeCategoryIdColumn);

		upgradeTable.updateTable();

		ValueMapper messageIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeParentMessageIdColumn = new SwapUpgradeColumnImpl(
			"parentMessageId", messageIdMapper);

		upgradeTable = new DefaultUpgradeTableImpl(
			MBMessageImpl.TABLE_NAME, MBMessageImpl.TABLE_COLUMNS,
			upgradeParentMessageIdColumn);

		upgradeTable.updateTable();

		UpgradeColumn upgradeMessageIdColumn = new SwapUpgradeColumnImpl(
			"messageId", messageIdMapper);

		UpgradeColumn upgradeRootMessageIdColumn = new SwapUpgradeColumnImpl(
			"rootMessageId", messageIdMapper);

		// MBMessageFlag

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			MBMessageFlagImpl.TABLE_NAME, MBMessageFlagImpl.TABLE_COLUMNS,
			pkUpgradeColumn, upgradeMessageIdColumn);

		upgradeTable.updateTable();

		// MBThread

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			MBThreadImpl.TABLE_NAME, MBThreadImpl.TABLE_COLUMNS,
			pkUpgradeColumn, upgradeCategoryIdColumn,
			upgradeRootMessageIdColumn);

		upgradeTable.updateTable();

		ValueMapper threadIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeThreadIdColumn = new SwapUpgradeColumnImpl(
			"threadId", threadIdMapper);

		// MBMessage

		upgradeTable = new DefaultUpgradeTableImpl(
			MBMessageImpl.TABLE_NAME, MBMessageImpl.TABLE_COLUMNS,
			upgradeThreadIdColumn);

		upgradeTable.updateTable();

		// MBDiscussion

		upgradeTable = new DefaultUpgradeTableImpl(
			MBDiscussionImpl.TABLE_NAME, MBDiscussionImpl.TABLE_COLUMNS,
			new PKUpgradeColumnImpl(), upgradeThreadIdColumn);

		upgradeTable.updateTable();

		// Resource

		//ResourceUtil.upgradePrimKey(categoryIdMapper, MBCategory.class.getName());
		//ResourceUtil.upgradePrimKey(messageIdMapper, MBMessage.class.getName());

		// Counter

		CounterLocalServiceUtil.reset(MBCategory.class.getName());
		CounterLocalServiceUtil.reset(MBMessage.class.getName());
		CounterLocalServiceUtil.reset(MBThread.class.getName());
		CounterLocalServiceUtil.reset(
			"com.liferay.portlet.messageboards.model.MBTopic");

		// Schema

		DBUtil.getInstance().executeSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table MBMessage drop primary key",
		"alter table MBMessage add primary key (messageId)",
		"alter table MBMessage drop topicId",

		"alter table MBMessageFlag drop primary key",
		"alter table MBMessageFlag add primary key (messageFlagId)",

		"alter table MBStatsUser drop primary key",
		"alter table MBStatsUser add primary key (statsUserId)",

		"alter table MBThread drop topicId"
	};

	private static Log _log = LogFactory.getLog(UpgradeMessageBoards.class);

}