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

package com.liferay.gradle.plugins.tld.formatter;

import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;

/**
 * @author Andrea Di Giorgi
 */
public class TLDFormatterPlugin implements Plugin<Project> {

	public static final String CONFIGURATION_NAME = "tldFormatter";

	public static final String FORMAT_TLD_TASK_NAME = "formatTLD";

	@Override
	public void apply(Project project) {
		addTLDFormatterConfiguration(project);

		addFormatTLDTask(project);
	}

	protected FormatTLDTask addFormatTLDTask(Project project) {
		FormatTLDTask formatTLDTask = GradleUtil.addTask(
			project, FORMAT_TLD_TASK_NAME, FormatTLDTask.class);

		formatTLDTask.setDescription(
			"Runs Liferay TLD Formatter to format files.");

		return formatTLDTask;
	}

	protected Configuration addTLDFormatterConfiguration(
		final Project project) {

		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay TLD Formatter for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addTLDFormatterDependencies(project);
				}

			});

		return configuration;
	}

	protected void addTLDFormatterDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.tld.formatter", "latest.release");
	}

}