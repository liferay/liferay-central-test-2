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

import java.io.File;
import java.io.InputStream;

/**
 * <a href="MimeTypesUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MimeTypesUtil {

	public static String getContentType(File file) {
		return getMimeTypes().getContentType(file);
	}

	/**
	 * This function will attempt to determine the mime type based on the
	 * inputstream and the filename, giving priority to the filename.
	 * 
	 * @param inputSream Stream of file to be checked
	 * @param fileName Full name or extension of file (e.g., "Test.doc", ".doc")
	 * @return Extracted text from file, if it is of a supported format.
	 */
	public static String getContentType(
		InputStream inputStream, String fileName) {

		return getMimeTypes().getContentType(inputStream, fileName);
	}

	/**
	 * This function will attempt to determine the mime type based on the
	 * filename.
	 * 
	 * @param fileName Full name or extension of file (e.g., "Test.doc", ".doc")
	 * @return Extracted text from file, if it is of a supported format.
	 */
	public static String getContentType(String fileName) {
		return getMimeTypes().getContentType(fileName);
	}

	public static MimeTypes getMimeTypes() {
		return _mimeTypes;
	}

	public void setMimeTypes(MimeTypes mimeTypes) {
		_mimeTypes = mimeTypes;
	}

	private static MimeTypes _mimeTypes;

}