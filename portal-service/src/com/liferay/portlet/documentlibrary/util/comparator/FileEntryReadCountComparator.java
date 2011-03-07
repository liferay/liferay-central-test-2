/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Brian Wing Shun Chan
 */
public class FileEntryReadCountComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "readCount ASC";

	public static String ORDER_BY_DESC = "readCount DESC";

	public static String[] ORDER_BY_FIELDS = {"readCount"};

	public FileEntryReadCountComparator() {
		this(false);
	}

	public FileEntryReadCountComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		DLFileEntry dlFileEntry1 = (DLFileEntry)obj1;
		DLFileEntry dlFileEntry2 = (DLFileEntry)obj2;

		int value = 0;

		if (dlFileEntry1.getReadCount() < dlFileEntry2.getReadCount()) {
			value = -1;
		}
		else if (dlFileEntry1.getReadCount() > dlFileEntry2.getReadCount()) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	public boolean isAscending() {
		return _ascending;
	}

	private boolean _ascending;

}