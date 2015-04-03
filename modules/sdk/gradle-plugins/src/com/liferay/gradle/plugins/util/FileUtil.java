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

package com.liferay.gradle.plugins.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.Properties;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static boolean exists(Project project, String fileName) {
		File file = project.file(fileName);

		return file.exists();
	}

	public static Properties readProperties(File file) throws Exception {
		Properties properties = new Properties();

		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.load(fileInputStream);
			}
		}

		return properties;
	}

	public static Properties readProperties(Project project, String fileName)
		throws Exception {

		File file = project.file(fileName);

		return readProperties(file);
	}

	public static void write(File file, List<String> lines) throws Exception {
		try (PrintWriter printWriter = new PrintWriter(
				new OutputStreamWriter(
					new FileOutputStream(file), StandardCharsets.UTF_8))) {

			for (String line : lines) {
				printWriter.println(line);
			}
		}
	}

}