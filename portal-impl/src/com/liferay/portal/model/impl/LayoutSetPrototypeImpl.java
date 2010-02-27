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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;

/**
 * <a href="LayoutSetPrototypeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LayoutSetPrototypeImpl
	extends LayoutSetPrototypeModelImpl implements LayoutSetPrototype {

	public LayoutSetPrototypeImpl() {
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getLayoutSetPrototypeGroup(
				getCompanyId(), getLayoutSetPrototypeId());
		}
		catch (Exception e) {
		}

		return group;
	}

	public LayoutSet getLayoutSet() {
		LayoutSet layoutSet = null;

		try {
			layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				getGroup().getGroupId(), true);
		}
		catch (Exception e) {
		}

		return layoutSet;
	}

}