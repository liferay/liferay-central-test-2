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
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceUtil;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradePolls.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradePolls extends UpgradeProcess {

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

		// PollsQuestion

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			PollsQuestionImpl.TABLE_NAME, PollsQuestionImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		ValueMapper questionIdMapper = pkUpgradeColumn.getValueMapper();

		UpgradeColumn upgradeQuestionIdColumn = new SwapUpgradeColumnImpl(
			"questionId", questionIdMapper);

		// PollsChoice

		upgradeTable = new DefaultUpgradeTableImpl(
			PollsChoiceImpl.TABLE_NAME, PollsChoiceImpl.TABLE_COLUMNS,
			upgradeQuestionIdColumn);

		// PollsVote

		upgradeTable = new DefaultUpgradeTableImpl(
			PollsVoteImpl.TABLE_NAME, PollsVoteImpl.TABLE_COLUMNS,
			upgradeQuestionIdColumn);

		upgradeTable.updateTable();

		// Resource

		ResourceUtil.upgradePrimKey(
			questionIdMapper, PollsQuestion.class.getName());

		// Counter

		CounterLocalServiceUtil.reset(PollsQuestion.class.getName());
	}

	private static Log _log = LogFactory.getLog(UpgradePolls.class);

}