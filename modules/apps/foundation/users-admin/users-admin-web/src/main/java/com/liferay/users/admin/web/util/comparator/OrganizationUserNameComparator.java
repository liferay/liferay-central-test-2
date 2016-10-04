/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.users.admin.web.util.comparator;

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pei-Jung Lan
 */
public class OrganizationUserNameComparator extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC = "name ASC";

	public static final String ORDER_BY_DESC = "name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public OrganizationUserNameComparator() {
		this(false);
	}

	public OrganizationUserNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object object1, Object object2) {
		String name1 = getName(object1);
		String name2 = getName(object2);

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected String getName(Object obj) {
		if (obj instanceof Organization) {
			Organization organization = (Organization)obj;

			return organization.getName();
		}
		else {
			User user = (User)obj;

			return user.getLastName();
		}
	}

	private final boolean _ascending;

}