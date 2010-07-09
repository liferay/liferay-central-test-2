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

package com.liferay.portal.model;

/**
 * @author Brian Wing Shun Chan
 */
public class RoleConstants {

	public static final String ADMINISTRATOR = "Administrator";

	public static final String GUEST = "Guest";

	public static final String OWNER = "Owner";

	public static final String POWER_USER = "Power User";

	public static final String USER = "User";

	public static final String[] SYSTEM_ROLES = {
		ADMINISTRATOR, GUEST, OWNER, POWER_USER, USER
	};

	public static final String COMMUNITY_ADMINISTRATOR =
		"Community Administrator";

	public static final String COMMUNITY_MEMBER = "Community Member";

	public static final String COMMUNITY_OWNER = "Community Owner";

	public static final String[] SYSTEM_COMMUNITY_ROLES = {
		COMMUNITY_ADMINISTRATOR, COMMUNITY_MEMBER, COMMUNITY_OWNER
	};

	public static final String ORGANIZATION_ADMINISTRATOR =
		"Organization Administrator";

	public static final String ORGANIZATION_MEMBER = "Organization Member";

	public static final String ORGANIZATION_OWNER = "Organization Owner";

	public static final String[] SYSTEM_ORGANIZATION_ROLES = {
		ORGANIZATION_ADMINISTRATOR, ORGANIZATION_MEMBER, ORGANIZATION_OWNER
	};

	public static final int TYPE_COMMUNITY = 2;

	public static final String TYPE_COMMUNITY_LABEL = "community";

	public static final int TYPE_ORGANIZATION = 3;

	public static final String TYPE_ORGANIZATION_LABEL = "organization";

	public static final int TYPE_PROVIDER = 4;

	public static final int TYPE_REGULAR = 1;

	public static final String TYPE_REGULAR_LABEL = "regular";

	public static String getTypeLabel(int type) {
		if (type == TYPE_COMMUNITY) {
			return TYPE_COMMUNITY_LABEL;
		}
		else if (type == TYPE_ORGANIZATION) {
			return TYPE_ORGANIZATION_LABEL;
		}
		else {
			return TYPE_REGULAR_LABEL;
		}
	}

}