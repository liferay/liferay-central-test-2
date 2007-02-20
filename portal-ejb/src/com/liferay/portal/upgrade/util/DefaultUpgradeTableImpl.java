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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.upgrade.StagnantRowException;

import java.sql.ResultSet;

/**
 * <a href="DefaultUpgradeTableImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class DefaultUpgradeTableImpl
	extends BaseUpgradeTableImpl implements UpgradeTable {

	public DefaultUpgradeTableImpl(String tableName, Object[][] columns,
								   UpgradeColumn upgradeColumn1) {

		this(tableName, columns, upgradeColumn1, null);
	}

	public DefaultUpgradeTableImpl(String tableName, Object[][] columns,
								   UpgradeColumn upgradeColumn1,
								   UpgradeColumn upgradeColumn2) {

		this(tableName, columns, upgradeColumn1, upgradeColumn2, null);
	}

	public DefaultUpgradeTableImpl(String tableName, Object[][] columns,
								   UpgradeColumn upgradeColumn1,
								   UpgradeColumn upgradeColumn2,
								   UpgradeColumn upgradeColumn3) {

		super(tableName, columns);

		_upgradeColumns = new UpgradeColumn[columns.length];

		prepareUpgradeColumns(upgradeColumn1);
		prepareUpgradeColumns(upgradeColumn2);
		prepareUpgradeColumns(upgradeColumn3);
	}

	public String getExportedData(ResultSet rs) throws Exception {
		StringBuffer sb = new StringBuffer();

		Object[][] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
			boolean last = false;

			if ((i + 1) == columns.length) {
				last = true;
			}

			if (_upgradeColumns[i] == null) {
				appendColumn(
					sb, rs, (String)columns[i][0], (Integer)columns[i][1],
					last);
			}
			else {
				try {
					Object oldValue = getValue(
						rs, (String)columns[i][0], (Integer)columns[i][1]);

					Object newValue = _upgradeColumns[i].getNewValue(oldValue);

					appendColumn(sb, newValue, last);
				}
				catch (StagnantRowException sre) {
					throw new StagnantRowException(
						"Column " + columns[i][0] + " with value " +
							sre.getMessage(),
						sre);
				}
			}
		}

		return sb.toString();
	}

	protected void prepareUpgradeColumns(UpgradeColumn upgradeColumn) {
		if (upgradeColumn == null) {
			return;
		}

		Object[][] columns = getColumns();

		for (int i = 0; i < columns.length; i++) {
			String name = (String)columns[i][0];

			if (upgradeColumn.isApplicable(i, name)) {
				_upgradeColumns[i] = upgradeColumn;
			}
		}
	}

	private UpgradeColumn[] _upgradeColumns;

}