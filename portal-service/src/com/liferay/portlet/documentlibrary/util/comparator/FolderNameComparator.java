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

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * @author Alexander Chow
 */
public class FolderNameComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "name ASC";

	public static String ORDER_BY_DESC = "name DESC";

	public static String[] ORDER_BY_FIELDS = {"name"};

	public FolderNameComparator() {
		this(false);
	}

	public FolderNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		String name1 = null;
		String name2 = null;

		if (obj1 instanceof DLFolder) {
			name1 = ((DLFolder)obj1).getName();
			name2 = ((DLFolder)obj2).getName();
		}
		else {
			name1 = ((Folder)obj1).getName();
			name2 = ((Folder)obj2).getName();
		}

		int value = name1.compareToIgnoreCase(name2);

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