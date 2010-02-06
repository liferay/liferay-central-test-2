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
import com.liferay.portal.upgrade.v6_0_0.util.JournalArticleTable;
import com.liferay.portal.upgrade.v6_0_0.util.JournalFeedTable;
import com.liferay.portal.upgrade.v6_0_0.util.JournalTemplateTable;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsigmond Rab
 */
public class UpgradeJournal extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		try {
			runSQL(
				"alter_column_type JournalArticle smallImageURL STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalArticleTable.TABLE_NAME,
				JournalArticleTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalArticleTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		try {
			runSQL(
				"alter_column_type JournalFeed targetLayoutFriendlyUrl " +
				"VARCHAR(255) null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalFeedTable.TABLE_NAME, JournalFeedTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalFeedTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		try {
			runSQL(
				"alter_column_type JournalTemplate smallImageURL STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalTemplateTable.TABLE_NAME,
				JournalTemplateTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalTemplateTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}
	}

}