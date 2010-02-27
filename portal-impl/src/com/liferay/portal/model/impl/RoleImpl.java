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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;

/**
 * <a href="RoleImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class RoleImpl extends RoleModelImpl implements Role {

	public RoleImpl() {
	}

	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	public String getTitle(String languageId, boolean useDefault) {
		String value = super.getTitle(languageId, useDefault);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	public String getTypeLabel() {
		return RoleConstants.getTypeLabel(getType());
	}

}