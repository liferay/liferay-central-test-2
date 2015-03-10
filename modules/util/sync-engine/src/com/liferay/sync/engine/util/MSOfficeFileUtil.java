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

package com.liferay.sync.engine.util;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;

/**
 * @author Shinn Lok
 */
public class MSOfficeFileUtil {

	public static boolean isExcelFile(Path filePath) {
		if (Files.isDirectory(filePath)) {
			return false;
		}

		String extension = FilenameUtils.getExtension(filePath.toString());

		if (extension == null) {
			return false;
		}

		if (_excelExtensions.contains(extension.toLowerCase())) {
			return true;
		}

		return false;
	}

	public static boolean isTempCreatedFile(Path filePath) {
		if (Files.isDirectory(filePath)) {
			return false;
		}

		String fileName = String.valueOf(filePath.getFileName());

		if (fileName.startsWith("~$") ||
			(fileName.startsWith("~") && fileName.endsWith(".tmp"))) {

			return true;
		}

		return false;
	}

	public static boolean isTempRenamedFile(Path filePath) {
		if (Files.isDirectory(filePath)) {
			return false;
		}

		String fileName = String.valueOf(filePath.getFileName());

		Matcher matcher = _pattern.matcher(fileName);

		if (matcher.matches()) {
			return true;
		}

		return false;
	}

	private static final Set<String> _excelExtensions = new HashSet(
		Arrays.asList("csv", "xls", "xlsb", "xlsm", "xlsx", "xltx"));
	private static final Pattern _pattern = Pattern.compile(
		"[0-9A-F]{8}(.tmp)?");

}