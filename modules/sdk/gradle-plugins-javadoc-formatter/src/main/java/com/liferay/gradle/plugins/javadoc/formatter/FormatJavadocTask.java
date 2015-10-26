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

package com.liferay.gradle.plugins.javadoc.formatter;

import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.javadoc.formatter.JavadocFormatterArgs;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.JavaExec;
import org.gradle.process.JavaExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class FormatJavadocTask extends JavaExec {

	@Override
	public JavaExecSpec args(Iterable<?> args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec args(Object... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JavaExec classpath(Object... paths) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exec() {
		super.setArgs(getArgs());
		super.setClasspath(getClasspath());
		super.setWorkingDir(getWorkingDir());

		super.exec();
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("javadoc.author=" + getAuthor());
		args.add("javadoc.init=" + isInitializeMissingJavadocs());
		args.add(
			"javadoc.input.dir=" + FileUtil.getAbsolutePath(getInputDir()));
		args.add("javadoc.limit=" + StringUtil.merge(getLimits(), ","));
		args.add(
			"javadoc.lowest.supported.java.version=" +
				getLowestSupportedJavaVersion());
		args.add("javadoc.output.file.prefix=" + getOutputFilePrefix());
		args.add("javadoc.update=" + isUpdateJavadocs());

		return args;
	}

	public String getAuthor() {
		return _javadocFormatterArgs.getAuthor();
	}

	@Override
	public FileCollection getClasspath() {
		Project project = getProject();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(
			JavadocFormatterPlugin.CONFIGURATION_NAME);
	}

	public File getInputDir() {
		Project project = getProject();

		return project.file(_javadocFormatterArgs.getInputDirName());
	}

	public String[] getLimits() {
		return _javadocFormatterArgs.getLimits();
	}

	public double getLowestSupportedJavaVersion() {
		return _javadocFormatterArgs.getLowestSupportedJavaVersion();
	}

	@Override
	public String getMain() {
		return "com.liferay.javadoc.formatter.JavadocFormatter";
	}

	public String getOutputFilePrefix() {
		return _javadocFormatterArgs.getOutputFilePrefix();
	}

	@Override
	public File getWorkingDir() {
		Project project = getProject();

		return project.getProjectDir();
	}

	public boolean isInitializeMissingJavadocs() {
		return _javadocFormatterArgs.isInitializeMissingJavadocs();
	}

	public boolean isUpdateJavadocs() {
		return _javadocFormatterArgs.isUpdateJavadocs();
	}

	@Override
	public JavaExec setArgs(Iterable<?> applicationArgs) {
		throw new UnsupportedOperationException();
	}

	public void setAuthor(String author) {
		_javadocFormatterArgs.setAuthor(author);
	}

	@Override
	public JavaExec setClasspath(FileCollection classpath) {
		throw new UnsupportedOperationException();
	}

	public void setInitializeMissingJavadocs(
		boolean initializeMissingJavadocs) {

		_javadocFormatterArgs.setInitializeMissingJavadocs(
			initializeMissingJavadocs);
	}

	public void setInputDirName(String inputDirName) {
		_javadocFormatterArgs.setInputDirName(inputDirName);
	}

	public void setLimits(String[] limits) {
		_javadocFormatterArgs.setLimits(limits);
	}

	public void setLowestSupportedJavaVersion(
		double lowestSupportedJavaVersion) {

		_javadocFormatterArgs.setLowestSupportedJavaVersion(
			lowestSupportedJavaVersion);
	}

	public void setOutputFilePrefix(String outputFilePrefix) {
		_javadocFormatterArgs.setOutputFilePrefix(outputFilePrefix);
	}

	public void setUpdateJavadocs(boolean updateJavadocs) {
		_javadocFormatterArgs.setUpdateJavadocs(updateJavadocs);
	}

	@Override
	public void setWorkingDir(Object dir) {
		throw new UnsupportedOperationException();
	}

	private final JavadocFormatterArgs _javadocFormatterArgs =
		new JavadocFormatterArgs();

}