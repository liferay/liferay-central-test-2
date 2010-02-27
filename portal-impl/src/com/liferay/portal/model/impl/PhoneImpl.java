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
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.Phone;
import com.liferay.portal.service.ListTypeServiceUtil;

/**
 * <a href="PhoneImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PhoneImpl extends PhoneModelImpl implements Phone {

	public PhoneImpl() {
	}

	public ListType getType() {
		ListType type = null;

		try {
			type = ListTypeServiceUtil.getListType(getTypeId());
		}
		catch (Exception e) {
			type = new ListTypeImpl();

			_log.error(e);
		}

		return type;
	}

	private static Log _log = LogFactoryUtil.getLog(PhoneImpl.class);

}