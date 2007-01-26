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

package com.liferay.portal.upgrade.util;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;

import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * This implementation handles a simple upgrade of a table with a primary key of
 * type long.  It simply reads and reinserts all of the table's entries.
 *
 * <p><a href="LongPKUpgradeTableImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 * @author  Brian Wing Shun Chan
 *
 */
public class LongPKUpgradeTableImpl
	extends BaseUpgradeTableImpl implements PKUpgradeTable{

	/**
	 * Constructor.
	 *
	 * @param	tableName name of table to upgrade
	 * @param	columns Columns specified by {name, sql.Type} pairs. Order does
	 *			not matter with the exception that the first entry should be the
	 *			primary key.
	 */
	public LongPKUpgradeTableImpl(String tableName, Object[][] columns) {
		super(tableName, columns);

		_pkMap = null;
	}

	/**
	 * Constructor.
	 *
	 * @param	tableName name of table to upgrade
	 * @param	columns Columns specified by {name, sql.Type} pairs. Order does
	 *			not matter with the exception that the first entry should be the
	 *			primary key.
	 * @param	usePKMap boolean value for whether or not to update the pk map
	 */
	public LongPKUpgradeTableImpl(
			String tableName, Object[][] columns, boolean usePKMap) {

		super(tableName, columns);

		_pkMap = new HashMap();
	}

	public void appendPKMap(Object oldPK, Object newPK) {
		_pkMap.put(oldPK, newPK);
	}

	public String getExportedData(ResultSet rs) throws Exception {
		StringBuffer sb = new StringBuffer();

		Object[][] columns = getColumns();

		Long id = new Long(
			CounterLocalServiceUtil.increment(Counter.class.getName()));

		if (_pkMap != null) {
			appendPKMap(new Long(rs.getLong((String)columns[0][0])), id);
		}

		appendColumn(sb, id);

		for (int i = 1; i < columns.length; i++) {
			boolean last = false;

			if (i == columns.length - 1) {
				last = true;
			}

			appendColumn(
				sb, rs, (String)columns[i][0], (Integer)columns[i][1], last);
		}

		return sb.toString();
	}

	public Map getPKMap() throws Exception {
		if (_pkMap == null) {
			throw new UpgradeException(
				LongPKUpgradeTableImpl.class.getName() +
				" was instantiated without the usePKMap flag set to true");
		}

		return _pkMap;
	}

	private Map _pkMap;

}