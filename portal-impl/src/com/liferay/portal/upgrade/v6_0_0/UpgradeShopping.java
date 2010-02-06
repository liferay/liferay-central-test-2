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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.upgrade.v6_0_0.util.ShoppingItemTable;

/**
 * <a href="UpgradeShopping.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class UpgradeShopping extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("update ShoppingItem set groupId = (select groupId from ");
		sb.append("ShoppingCategory where ShoppingCategory.categoryId = ");
		sb.append("ShoppingItem.categoryId)");

		runSQL(sb.toString());

		try {
			runSQL("alter_column_type ShoppingItem smallImageURL STRING null");
			runSQL("alter_column_type ShoppingItem mediumImageURL STRING null");
			runSQL("alter_column_type ShoppingItem largeImageURL STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				ShoppingItemTable.TABLE_NAME, ShoppingItemTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(ShoppingItemTable.TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}
	}

}