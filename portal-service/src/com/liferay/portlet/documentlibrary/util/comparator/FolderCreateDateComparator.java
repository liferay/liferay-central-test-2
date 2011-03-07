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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;

/**
 * @author Alexander Chow
 */
public class FolderCreateDateComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "createDate ASC";

	public static String ORDER_BY_DESC = "createDate DESC";

	public static String[] ORDER_BY_FIELDS = {"createDate"};

	public FolderCreateDateComparator() {
		this(false);
	}

	public FolderCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		Date createDate1 = null;
		Date createDate2 = null;

		if (obj1 instanceof DLFolder) {
			DLFolder dlFolder1 = (DLFolder)obj1;
			DLFolder dlFolder2 = (DLFolder)obj2;

			createDate1 = dlFolder1.getCreateDate();
			createDate2 = dlFolder2.getCreateDate();
		}
		else {
			Folder folder1 = (Folder)obj1;
			Folder folder2 = (Folder)obj2;

			createDate1 = folder1.getCreateDate();
			createDate2 = folder2.getCreateDate();
		}

		int value = DateUtil.compareTo(createDate1, createDate2);

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