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

package com.liferay.gradle.plugins.lang.builder;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class LangBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_LANG_TASK_NAME = "buildLang";

	public static final String CONFIGURATION_NAME = "langBuilder";

	@Override
	public void apply(Project project) {
		addLangBuilderConfiguration(project);

		addBuildLangTask(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureBuildLangTask(project);
				}

			});
	}

	protected BuildLangTask addBuildLangTask(Project project) {
		BuildLangTask buildLangTask = GradleUtil.addTask(
			project, BUILD_LANG_TASK_NAME, BuildLangTask.class);

		buildLangTask.setDescription(
			"Runs Liferay Lang Builder to translate language property files.");

		return buildLangTask;
	}

	protected Configuration addLangBuilderConfiguration(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay Lang Builder for this project.");
		configuration.setVisible(false);

		GradleUtil.executeIfEmpty(
			configuration,
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					addLangBuilderDependencies(project);
				}

			});

		return configuration;
	}

	protected void addLangBuilderDependencies(Project project) {
		GradleUtil.addDependency(
			project, CONFIGURATION_NAME, "com.liferay",
			"com.liferay.lang.builder", "latest.release");
	}

	protected void configureBuildLangTask(BuildLangTask buildLangTask) {
		Project project = buildLangTask.getProject();

		if (Validator.isNull(buildLangTask.getTranslateClientId()) &&
			project.hasProperty("microsoftTranslatorClientId")) {

			buildLangTask.setTranslateClientId(
				(String)project.property("microsoftTranslatorClientId"));
		}

		if (Validator.isNull(buildLangTask.getTranslateClientSecret()) &&
			project.hasProperty("microsoftTranslatorClientSecret")) {

			buildLangTask.setTranslateClientSecret(
				(String)project.property("microsoftTranslatorClientSecret"));
		}
	}

	protected void configureBuildLangTask(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildLangTask.class,
			new Action<BuildLangTask>() {

				@Override
				public void execute(BuildLangTask buildLangTask) {
					configureBuildLangTask(buildLangTask);
				}

			});
	}

}