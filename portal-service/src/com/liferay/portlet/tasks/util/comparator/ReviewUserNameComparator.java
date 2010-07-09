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

package com.liferay.portlet.tasks.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.tasks.model.TasksReview;

/**
 * @author Brian Wing Shun Chan
 */
public class ReviewUserNameComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "stage, userName ASC";

	public static String ORDER_BY_DESC = "stage, userName DESC";

	public static String[] ORDER_BY_FIELDS = {"stage", "userName"};

	public ReviewUserNameComparator() {
		this(false);
	}

	public ReviewUserNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Object obj1, Object obj2) {
		TasksReview review1 = (TasksReview)obj1;
		TasksReview review2 = (TasksReview)obj2;

		int value = 0;

		if (review1.getStage() < review2.getStage()) {
			value = -1;
		}
		else if (review1.getStage() > review2.getStage()) {
			value = 1;
		}

		if (value == 0) {
			value = review1.getUserName().compareTo(review2.getUserName());
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