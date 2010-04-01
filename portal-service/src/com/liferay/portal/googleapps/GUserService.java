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

package com.liferay.portal.googleapps;

import java.util.List;

/**
 * <a href="GUserService.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface GUserService {

	public void addUser(
			long userId, String password, String firstName, String lastName)
		throws GoogleAppsException;

	public void deleteUser(long userId) throws GoogleAppsException;

	public List<GUser> getUsers() throws GoogleAppsException;

	public void updatePassword(long userId, String password)
		throws GoogleAppsException;

}