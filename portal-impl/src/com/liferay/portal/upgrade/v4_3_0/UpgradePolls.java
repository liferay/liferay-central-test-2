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
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.PollsChoiceIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PollsChoiceTable;
import com.liferay.portal.upgrade.v4_3_0.util.PollsQuestionTable;
import com.liferay.portal.upgrade.v4_3_0.util.PollsVoteChoiceIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.PollsVoteTable;

import java.sql.Types;

/**
 * <a href="UpgradePolls.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradePolls extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// PollsQuestion

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		PKUpgradeColumnImpl upgradePKColumn = new PKUpgradeColumnImpl(
			"questionId", true);

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			PollsQuestionTable.TABLE_NAME, PollsQuestionTable.TABLE_COLUMNS,
			upgradePKColumn, upgradeGroupIdColumn, upgradeUserIdColumn);

		upgradeTable.setCreateSQL(PollsQuestionTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper questionIdMapper = upgradePKColumn.getValueMapper();

		AvailableMappersUtil.setPollsQuestionIdMapper(questionIdMapper);

		UpgradeColumn upgradeQuestionIdColumn = new SwapUpgradeColumnImpl(
			"questionId", questionIdMapper);

		// PollsChoice

		PKUpgradeColumnImpl upgradeChoiceId =
			new PollsChoiceIdUpgradeColumnImpl(upgradeQuestionIdColumn);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			PollsChoiceTable.TABLE_NAME, PollsChoiceTable.TABLE_COLUMNS,
			upgradeQuestionIdColumn, upgradeChoiceId);

		upgradeTable.setCreateSQL(PollsChoiceTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper choiceIdMapper = upgradeChoiceId.getValueMapper();

		// PollsVote

		UpgradeColumn upgradeVoteChoiceIdColumn =
			new PollsVoteChoiceIdUpgradeColumnImpl(
				upgradeQuestionIdColumn, choiceIdMapper);

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			PollsVoteTable.TABLE_NAME, PollsVoteTable.TABLE_COLUMNS,
			new PKUpgradeColumnImpl("voteId", false), upgradeUserIdColumn,
			upgradeQuestionIdColumn, upgradeVoteChoiceIdColumn);

		upgradeTable.setCreateSQL(PollsVoteTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

}