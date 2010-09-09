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

package com.liferay.portlet.social.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.social.model.SocialEquityLog;

/**
 * @author Zsolt Berentey
 */
@SuppressWarnings("serial")
public class SocialEquityLogPrimaryKeyComparator extends OrderByComparator {

	public static String ORDER_BY_ASC = "equityLogId ASC";

	public static String ORDER_BY_DESC = "equityLogId DESC";

	public static String[] ORDER_BY_FIELDS = {"equityLogId"};

	public SocialEquityLogPrimaryKeyComparator() {
		this(true);
	}

	public SocialEquityLogPrimaryKeyComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		SocialEquityLog equityLog1 = (SocialEquityLog)obj1;
		SocialEquityLog equityLog2 = (SocialEquityLog)obj2;

		int value = 0;

		if (equityLog1.getEquityLogId() > equityLog2.getEquityLogId()) {
			value = 1;
		}
		else if (equityLog1.getEquityLogId() < equityLog2.getEquityLogId()) {
			value = -1;
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