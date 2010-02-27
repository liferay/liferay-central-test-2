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
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import java.util.List;

/**
 * <a href="LayoutPrototypeImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class LayoutPrototypeImpl
	extends LayoutPrototypeModelImpl implements LayoutPrototype {

	public LayoutPrototypeImpl() {
	}

	public Group getGroup() {
		Group group = null;

		try {
			group = GroupLocalServiceUtil.getLayoutPrototypeGroup(
				getCompanyId(), getLayoutPrototypeId());
		}
		catch (Exception e) {
		}

		return group;
	}

	public Layout getLayout() {
		Layout layout = null;

		try {
			Group group = getGroup();

			if ((group != null) && (group.getPrivateLayoutsPageCount() > 0)) {
				List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
					group.getGroupId(), true);

				layout = layouts.get(0);
			}
		}
		catch (Exception e) {
		}

		return layout;
	}

}