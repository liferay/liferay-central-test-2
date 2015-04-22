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

package com.liferay.javadoc.formatter;

import java.io.File;

/**
 * @author Andrea Di Giorgi
 */
public class JavadocFormatterArgs {

	public String getAuthor() {
		return _author;
	}

	public File getInputDir() {
		return _inputDir;
	}

	public String[] getLimits() {
		return _limits;
	}

	public double getLowestSupportedJavaVersion() {
		return _lowestSupportedJavaVersion;
	}

	public String getOutputFilePrefix() {
		return _outputFilePrefix;
	}

	public boolean isInitializeMissingJavadocs() {
		return _initializeMissingJavadocs;
	}

	public boolean isUpdateJavadocs() {
		return _updateJavadocs;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public void setInitializeMissingJavadocs(
		boolean initializeMissingJavadocs) {

		_initializeMissingJavadocs = initializeMissingJavadocs;
	}

	public void setInputDir(File inputDir) {
		_inputDir = inputDir;
	}

	public void setLimits(String[] limits) {
		_limits = limits;
	}

	public void setLowestSupportedJavaVersion(
		double lowestSupportedJavaVersion) {

		_lowestSupportedJavaVersion = lowestSupportedJavaVersion;
	}

	public void setOutputFilePrefix(String outputFilePrefix) {
		_outputFilePrefix = outputFilePrefix;
	}

	public void setUpdateJavadocs(boolean updateJavadocs) {
		_updateJavadocs = updateJavadocs;
	}

	private String _author = JavadocFormatter.AUTHOR;
	private boolean _initializeMissingJavadocs;
	private File _inputDir;
	private String[] _limits;
	private double _lowestSupportedJavaVersion =
		JavadocFormatter.LOWEST_SUPPORTED_JAVA_VERSION;
	private String _outputFilePrefix = JavadocFormatter.OUTPUT_FILE_PREFIX;
	private boolean _updateJavadocs;

}