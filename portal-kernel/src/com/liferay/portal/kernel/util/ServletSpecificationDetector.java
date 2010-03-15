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

package com.liferay.portal.kernel.util;

import javax.servlet.ServletContext;

/**
 * <a href="ServletSpecificationDetector.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Shuyang Zhou
 */
public class ServletSpecificationDetector {

	public static boolean is2_5Plus() {
		return _IS_2_5_PLUS;
	}

	static {
		try {
			Class<ServletContext> clazz = ServletContext.class;
			clazz.getMethod("getContextPath");
			_IS_2_5_PLUS = true;
		}
		catch (Exception ex) {
			_IS_2_5_PLUS = false;
		}
	}

	private static boolean _IS_2_5_PLUS;

}