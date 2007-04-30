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

import com.liferay.portal.tools.util.DBUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeCompany.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class UpgradeCompany extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeCompany();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private String _getUpdateSQL(
		String tableName, long companyId, String webId) {

		return "update " + tableName + " set companyId = '" + companyId +
			"' where companyId = '" + companyId + "';";
	}

	private void _upgradeCompany() throws Exception {
		DBUtil dbUtil = DBUtil.getInstance();

		String[] webIds = new String[0];

		for (int i = 0; i < webIds.length; i++) {
			String webId = webIds[i];

			long companyId = 0;

			for (int j = 0; j < _TABLES.length; j++) {
				dbUtil.executeSQL(_getUpdateSQL(_TABLES[j], companyId, webId));
			}
		}

		for (int i = 0; i < _TABLES.length; i++) {
			dbUtil.executeSQL(
				"alter_column_type " + _TABLES + " companyId LONG");
		}
	}

	private static final String[] _TABLES = new String[] {
		"Address", "BlogsCategory"
	};

	private static Log _log = LogFactory.getLog(UpgradeCompany.class);

}