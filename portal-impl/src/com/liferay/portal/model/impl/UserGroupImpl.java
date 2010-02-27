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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.GroupLocalServiceUtil;

/**
 * <a href="UserGroupImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class UserGroupImpl extends UserGroupModelImpl implements UserGroup {

	public UserGroupImpl() {
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getUserGroupGroup(
				getCompanyId(), getUserGroupId());
		}
		catch (Exception e) {
			group = new GroupImpl();

			_log.error(e);
		}

		return group;
	}

	public int getPrivateLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPrivateLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getPublicLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPublicLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserGroupImpl.class);

}