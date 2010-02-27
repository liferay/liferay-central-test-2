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

import java.io.InputStream;

/**
 * <a href="DocumentConversionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class DocumentConversionUtil {

	public static InputStream convert(
			String id, InputStream is, String sourceExtension,
			String targetExtension)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, "convert",
			new Object[] {id, is, sourceExtension, targetExtension}, false);

		if (returnObj != null) {
			return (InputStream)returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getConversions(String extension) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			_CLASS, "getConversions", new Object[] {extension}, false);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS =
		"com.liferay.portlet.documentlibrary.util.DocumentConversionUtil";

}