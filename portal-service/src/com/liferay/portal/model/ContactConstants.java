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

import com.liferay.portal.security.auth.FullNameGeneratorFactory;

/**
 * <a href="ContactConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ContactConstants {

	public static final long DEFAULT_PARENT_CONTACT_ID = 0;

	public static String getFullName(
		String firstName, String middleName, String lastName) {

		FullNameGeneratorFactory fullNameGeneratorFactory =
			FullNameGeneratorFactory.getInstance();

		return fullNameGeneratorFactory.getFullName(
			firstName, middleName, lastName);
	}

}