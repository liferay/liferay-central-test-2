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

package com.liferay.message.boards.web.portlet.action;

import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.portal.struts.BasePortletPageFinder;
import com.liferay.portal.struts.PortletPageFinder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portlet.messageboards.model.MBCategory",
		"model.class.name=com.liferay.portlet.messageboards.model.MBMessage",
		"model.class.name=com.liferay.portlet.messageboards.model.MBThread"
	},
	service = PortletPageFinder.class
)
public class CategoryPortletPageFinder extends BasePortletPageFinder {

	@Override
	protected String[] getPortletIds() {
		return _PORTLET_IDS;
	}

	// Order is important. See LPS-23770.

	private static final String[] _PORTLET_IDS = new String[] {
		MBPortletKeys.MESSAGE_BOARDS_ADMIN, MBPortletKeys.MESSAGE_BOARDS
	};

}