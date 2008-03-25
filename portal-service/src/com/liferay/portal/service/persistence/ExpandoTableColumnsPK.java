/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * <a href="ExpandoTableColumnsPK.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableColumnsPK implements Comparable<ExpandoTableColumnsPK>,
	Serializable {
	public long tableId;
	public long columnId;

	public ExpandoTableColumnsPK() {
	}

	public ExpandoTableColumnsPK(long tableId, long columnId) {
		this.tableId = tableId;
		this.columnId = columnId;
	}

	public long getTableId() {
		return tableId;
	}

	public void setTableId(long tableId) {
		this.tableId = tableId;
	}

	public long getColumnId() {
		return columnId;
	}

	public void setColumnId(long columnId) {
		this.columnId = columnId;
	}

	public int compareTo(ExpandoTableColumnsPK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		if (tableId < pk.tableId) {
			value = -1;
		}
		else if (tableId > pk.tableId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		if (columnId < pk.columnId) {
			value = -1;
		}
		else if (columnId > pk.columnId) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ExpandoTableColumnsPK pk = null;

		try {
			pk = (ExpandoTableColumnsPK)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		if ((tableId == pk.tableId) && (columnId == pk.columnId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (String.valueOf(tableId) + String.valueOf(columnId)).hashCode();
	}

	public String toString() {
		StringMaker sm = new StringMaker();

		sm.append(StringPool.OPEN_CURLY_BRACE);

		sm.append("tableId");
		sm.append(StringPool.EQUAL);
		sm.append(tableId);

		sm.append(StringPool.COMMA);
		sm.append(StringPool.SPACE);
		sm.append("columnId");
		sm.append(StringPool.EQUAL);
		sm.append(columnId);

		sm.append(StringPool.CLOSE_CURLY_BRACE);

		return sm.toString();
	}
}