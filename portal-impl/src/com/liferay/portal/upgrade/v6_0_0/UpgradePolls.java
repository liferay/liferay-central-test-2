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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v6_0_0.util.PollsChoiceTable;
import com.liferay.portal.upgrade.v6_0_0.util.PollsQuestionTable;

/**
 * <a href="UpgradePolls.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero Puras
 */
public class UpgradePolls extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type PollsChoice description STRING null");
		}
		catch (Exception e) {

			// PollsChoice

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				PollsChoiceTable.TABLE_NAME, PollsChoiceTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(PollsChoiceTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		try {
			runSQL("alter_column_type PollsQuestion title STRING null");
		}
		catch (Exception e) {

			// PollsQuestion

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				PollsQuestionTable.TABLE_NAME,
				PollsQuestionTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(PollsQuestionTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

	}

}