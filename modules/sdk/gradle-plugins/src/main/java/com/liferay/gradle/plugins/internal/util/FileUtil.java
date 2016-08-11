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

package com.liferay.gradle.plugins.internal.util;

import com.liferay.gradle.util.ArrayUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.gradle.api.AntBuilder;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static FileTree getJarsFileTree(
		Project project, File dir, String... excludes) {

		Map<String, Object> args = new HashMap<>();

		args.put("dir", dir);

		if (ArrayUtil.isNotEmpty(excludes)) {
			args.put("excludes", Arrays.asList(excludes));
		}

		args.put("include", "*.jar");

		return project.fileTree(args);
	}

	public static String getRelativePath(Project project, File file) {
		String relativePath = project.relativePath(file);

		return relativePath.replace('\\', '/');
	}

	public static void touchFile(File file, long time) {
		boolean success = file.setLastModified(time);

		if (!success) {
			_logger.error("Unable to touch " + file);
		}
	}

	public static void touchFiles(
		Project project, File dir, long time, String... includes) {

		Map<String, Object> args = new HashMap<>();

		args.put("dir", dir);
		args.put("includes", Arrays.asList(includes));

		FileTree fileTree = project.fileTree(args);

		for (File file : fileTree) {
			touchFile(file, time);
		}
	}

	public static void unzip(
		Project project, final File file, final File destinationDir) {

		project.ant(
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(AntBuilder antBuilder) {
					_invokeAntMethodUnzip(antBuilder, file, destinationDir);
				}

			});
	}

	private static void _invokeAntMethodUnzip(
		AntBuilder antBuilder, File file, File destinationDir) {

		Map<String, Object> args = new HashMap<>();

		args.put("dest", destinationDir);
		args.put("src", file);

		antBuilder.invokeMethod("unzip", args);
	}

	private static final Logger _logger = Logging.getLogger(FileUtil.class);

}