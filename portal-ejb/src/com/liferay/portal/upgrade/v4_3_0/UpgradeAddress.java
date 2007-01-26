/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.upgrade.util.LongPKUpgradeTableImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;

import java.sql.Types;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeAddress.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class UpgradeAddress extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeAddress();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeAddress() throws Exception {
		UpgradeTable upgradeTable = new LongPKUpgradeTableImpl(
			_TABLE_NAME, _COLUMNS);

		upgradeTable.updateTable();
	}

	private static final Object[][] _COLUMNS = {
		{"addressId", new Integer(Types.BIGINT)},
		{"companyId", new Integer(Types.VARCHAR)},
		{"userId", new Integer(Types.VARCHAR)},
		{"userName", new Integer(Types.VARCHAR)},
		{"createDate", new Integer(Types.TIMESTAMP)},
		{"modifiedDate", new Integer(Types.TIMESTAMP)},
		{"className", new Integer(Types.VARCHAR)},
		{"classPK", new Integer(Types.VARCHAR)},
		{"street1", new Integer(Types.VARCHAR)},
		{"street2", new Integer(Types.VARCHAR)},
		{"street3", new Integer(Types.VARCHAR)},
		{"city", new Integer(Types.VARCHAR)},
		{"zip", new Integer(Types.VARCHAR)},
		{"regionId", new Integer(Types.VARCHAR)},
		{"countryId", new Integer(Types.VARCHAR)},
		{"typeId", new Integer(Types.INTEGER)},
		{"mailing", new Integer(Types.BOOLEAN)},
		{"primary_", new Integer(Types.BOOLEAN)}
	};

	private static final String _TABLE_NAME = "Address";

	private static Log _log = LogFactory.getLog(UpgradeAddress.class);

}