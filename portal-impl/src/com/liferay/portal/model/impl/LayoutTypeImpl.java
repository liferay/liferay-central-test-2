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

import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutType;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutTypeImpl implements LayoutType {

	public LayoutTypeImpl(Layout layout) {
		setLayout(layout);
	}

	public Layout getLayout() {
		return _layout;
	}

	public UnicodeProperties getTypeSettingsProperties() {
		return _layout.getTypeSettingsProperties();
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	private Layout _layout;

}