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

import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceCodeUpgradeColumnImpl;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeResource.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class UpgradeResource extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeResource();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeResource() throws Exception {
		TempUpgradeColumnImpl companyIdColumn =
			new TempUpgradeColumnImpl("companyId");
		TempUpgradeColumnImpl nameColumn = new TempUpgradeColumnImpl("name");
		TempUpgradeColumnImpl scopeColumn = new TempUpgradeColumnImpl("scope");

		UpgradeColumn codeColumn = new ResourceCodeUpgradeColumnImpl(
			companyIdColumn, nameColumn, scopeColumn);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			_TABLE_NAME, _TABLE_COLUMNS, companyIdColumn, nameColumn,
			scopeColumn, codeColumn);

		upgradeTable.updateTable();
	}

	public static String _TABLE_NAME = "Resource_";

	public static Object[][] _TABLE_COLUMNS = {
		{"resourceId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.VARCHAR)},
		{"name", new Integer(Types.VARCHAR)},
		{"scope", new Integer(Types.VARCHAR)},
		{"codeId", new Integer(Types.BIGINT)},
		{"primKey", new Integer(Types.VARCHAR)}
	};

	private static Log _log = LogFactory.getLog(UpgradeResource.class);

}