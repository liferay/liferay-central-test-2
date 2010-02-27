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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="CurrentUserIdStrategy.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class CurrentUserIdStrategy implements UserIdStrategy {

	public CurrentUserIdStrategy(User user) {
		_user = user;
	}

	public long getUserId(String userUuid) throws SystemException {
		if (Validator.isNull(userUuid)) {
			return _user.getUserId();
		}

		List<User> users = UserUtil.findByUuid(userUuid);

		Iterator<User> itr = users.iterator();

		if (itr.hasNext()) {
			User user = itr.next();

			return user.getUserId();
		}
		else {
			return _user.getUserId();
		}
	}

	private User _user;

}