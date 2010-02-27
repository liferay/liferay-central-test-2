/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="FileEntryReadCountComparator.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class FileEntryReadCountComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "readCount ASC";

	public static String ORDER_BY_DESC = "readCount DESC";

	public static String[] ORDER_BY_FIELDS = {"readCount"};

	public FileEntryReadCountComparator() {
		this(false);
	}

	public FileEntryReadCountComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		DLFileEntry fileEntry1 = (DLFileEntry)obj1;
		DLFileEntry fileEntry2 = (DLFileEntry)obj2;

		int value = 0;

		if (fileEntry1.getReadCount() < fileEntry2.getReadCount()) {
			value = -1;
		}
		else if (fileEntry1.getReadCount() > fileEntry2.getReadCount()) {
			value = 1;
		}

		if (_asc) {
			return value;
		}
		else {
			return -value;
		}
	}

	public String getOrderBy() {
		if (_asc) {
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
		return _asc;
	}

	private boolean _asc;

}