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

import java.sql.ResultSet;

import java.util.Map;

/**
 * This implementation handles a simple upgrade of any mapping table.  It reads
 * and reinserts all of the table's entries with one id remapped.
 *
 * <p><a href="MapUpgradeTableImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class MapUpgradeTableImpl extends BaseUpgradeTableImpl {

	/**
	 * Constructor.
	 *
	 * @param	tableName name of table to upgrade
	 * @param	columns Columns specified by {name, sql.Type} pairs. Order does
	 *			not matter with the exception that the first entry should be the
	 *			primary key.
	 * @param	idMap id mapping between the old and new
	 */
	public MapUpgradeTableImpl(String tableName, Object[][] columns,
							   Map idMap) {

		super(tableName, columns);

		_idMap = idMap;
	}

	public String getExportedData(ResultSet rs) throws Exception {
		StringBuffer sb = new StringBuffer();

		Object[][] columns = getColumns();

		Object value = getValue(
			rs, (String)columns[0][0], (Integer)columns[0][1]);

		appendColumn(sb, _idMap.get(value));

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

	private Map _idMap;

}