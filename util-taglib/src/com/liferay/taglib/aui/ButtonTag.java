/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseButtonTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class ButtonTag extends BaseButtonTag {

	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		String value = _value;

		if (Validator.isNull(value)) {
			if (_type.equals("submit")) {
				value = "save";
			}
			else if (_type.equals("cancel")) {
				value = "cancel";
			}
			else if (_type.equals("reset")) {
				value = "reset";
			}
		}

		setNamespacedAttribute(request, "value", value);
	}

}