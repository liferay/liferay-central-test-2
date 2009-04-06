/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;

/**
 * <a href="BaseUpgradeTableImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public abstract class BaseUpgradeTableImpl extends Table {

	public BaseUpgradeTableImpl(String tableName) {
		super(tableName);
	}

	public BaseUpgradeTableImpl(String tableName, Object[][] columns) {
		super(tableName, columns);
	}

	public void setCreateSQL(String createSQL) throws Exception {
		if (_calledUpdateTable) {
			throw new UpgradeException(
				"setCreateSQL is called after updateTable");
		}

		super.setCreateSQL(createSQL);
	}

	public void updateTable() throws Exception {
		_calledUpdateTable = true;

		String tempFileName = generateTempFile();

		try {
			DBUtil dbUtil = DBUtil.getInstance();

			String createSQL = getCreateSQL();

			if (Validator.isNotNull(createSQL)) {
				dbUtil.runSQL("drop table " + getTableName());

				dbUtil.runSQL(createSQL);
			}

			if (Validator.isNotNull(tempFileName)) {
				String deleteSQL = getDeleteSQL();

				dbUtil.runSQL(deleteSQL);

				populateTable(tempFileName);
			}
		}
		finally {
			if (Validator.isNotNull(tempFileName)) {
				FileUtil.delete(tempFileName);
			}
		}
	}

	static Log _log = LogFactoryUtil.getLog(BaseUpgradeTableImpl.class);

	private boolean _calledUpdateTable;

}