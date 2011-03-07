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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

/**
 * @author Alexander Chow
 */
public class FileEntrySizeComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "size ASC";

	public static String ORDER_BY_DESC = "size DESC";

	public static String[] ORDER_BY_FIELDS = {"size"};

	public FileEntrySizeComparator() {
		this(false);
	}

	public FileEntrySizeComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		Long size1 = null;
		Long size2 = null;

		if (obj1 instanceof DLFileEntry) {
			size1 = ((DLFileEntry)obj1).getSize();
			size2 = ((DLFileEntry)obj2).getSize();
		}
		else {
			size1 = ((FileEntry)obj1).getSize();
			size2 = ((FileEntry)obj2).getSize();
		}

		int value = size1.compareTo(size2);

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