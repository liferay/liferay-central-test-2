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

package com.liferay.portlet.imagegallery.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.imagegallery.model.IGImage;

/**
 * <a href="ImageDescriptionComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ImageDescriptionComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "description ASC";

	public static String ORDER_BY_DESC = "description DESC";

	public static String[] ORDER_BY_FIELDS = {"description"};

	public ImageDescriptionComparator() {
		this(false);
	}

	public ImageDescriptionComparator(boolean asc) {
		_asc = asc;
	}

	public int compare(Object obj1, Object obj2) {
		IGImage image1 = (IGImage)obj1;
		IGImage image2 = (IGImage)obj2;

		int value =
			image1.getDescription().toLowerCase().compareTo(
				image2.getDescription().toLowerCase());

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