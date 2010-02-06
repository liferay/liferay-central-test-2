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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v5_2_3.util.UserTable;

/**
 * <a href="UpgradeUser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeUser extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type User_ greeting VARCHAR(255) null");
		}
		catch (Exception e) {

			// User_

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				UserTable.TABLE_NAME, UserTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(UserTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		StringBundler sb = new StringBundler(9);

		sb.append("update User_ set firstName = (select Contact_.firstName ");
		sb.append("from Contact_ where Contact_.contactId = ");
		sb.append("User_.contactId), middleName = (select ");
		sb.append("Contact_.middleName from Contact_ where ");
		sb.append("Contact_.contactId = User_.contactId), lastName = ");
		sb.append("(select Contact_.lastName from Contact_ where ");
		sb.append("Contact_.contactId = User_.contactId), jobTitle = (select ");
		sb.append("Contact_.jobTitle from Contact_ where ");
		sb.append("Contact_.contactId = User_.contactId)");

		runSQL(sb.toString());
	}

}