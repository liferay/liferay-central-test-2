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
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class FileEntryTitleComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "title ASC";

	public static String ORDER_BY_DESC = "title DESC";

	public static String[] ORDER_BY_FIELDS = {"title"};

	public FileEntryTitleComparator() {
		this(false);
	}

	public FileEntryTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		String title1 = null;
		String title2 = null;

		if (obj1 instanceof DLFileEntry) {
			DLFileEntry dlFileEntry1 = (DLFileEntry)obj1;
			DLFileEntry dlFileEntry2 = (DLFileEntry)obj2;

			title1 = dlFileEntry1.getTitle();
			title2 = dlFileEntry2.getTitle();
		}
		else {
			FileEntry fileEntry1 = (FileEntry)obj1;
			FileEntry fileEntry2 = (FileEntry)obj2;

			title1 = fileEntry1.getTitle();
			title2 = fileEntry2.getTitle();
		}

		int value = title1.compareToIgnoreCase(title2);

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