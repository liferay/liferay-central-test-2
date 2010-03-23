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

package com.liferay.portal.util.comparator;

import com.liferay.portal.model.Group;

import java.io.Serializable;

import java.util.Comparator;

/**
 * <a href="GroupFriendlyURLComparator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class GroupFriendlyURLComparator
	implements Comparator<Group>, Serializable {

	public GroupFriendlyURLComparator() {
		this(true);
	}

	public GroupFriendlyURLComparator(boolean ascending) {
		_ascending = ascending;
	}

	public int compare(Group group1, Group group2) {
		int value = group1.getFriendlyURL().compareTo(group2.getFriendlyURL());

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	private boolean _ascending;

}