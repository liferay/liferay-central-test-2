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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassNameIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public ClassNameIdUpgradeColumnImpl() {
		super("classNameId", new Integer(Types.VARCHAR));
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String className = (String)oldValue;

		return new Long(PortalUtil.getClassNameId(className));
	}

}