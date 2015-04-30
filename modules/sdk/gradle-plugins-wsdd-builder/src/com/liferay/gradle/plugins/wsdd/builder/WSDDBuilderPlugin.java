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

package com.liferay.gradle.plugins.wsdd.builder;

import com.liferay.gradle.plugins.wsdd.builder.util.Validator;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;

import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskOutputs;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_WSDD_TASK_NAME = "buildWSDD";

	public static final String CONFIGURATION_NAME = "wsddBuilder";

	@Override
	public void apply(Project project) {
		addWSDDBuilderConfiguration(project);

		final WSDDBuilderArgs wsddBuilderArgs = addWSDDBuilderExtension(
			project);

		addBuildWSDDTask(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureWSDDBuilderExtension(project, wsddBuilderArgs);
				}

			});
	}

	protected Task addBuildWSDDTask(Project project) {
		TaskContainer taskContainer = project.getTasks();

		Task task = taskContainer.create(
			BUILD_WSDD_TASK_NAME, WSDDBuilderTask.class);

		task.setDescription("Runs Liferay WSDD Builder.");
		task.setGroup("build");

		return task;
	}

	protected Configuration addWSDDBuilderConfiguration(final Project project) {
		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final Configuration configuration = configurationContainer.create(
			CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Liferay WSDD Builder for this project.");
		configuration.setVisible(false);

		ResolvableDependencies resolvableDependencies =
			configuration.getIncoming();

		resolvableDependencies.beforeResolve(
			new Action<ResolvableDependencies>() {

				@Override
				public void execute(
					ResolvableDependencies resolvableDependencies) {

					Set<Dependency> dependencies =
						configuration.getDependencies();

					if (dependencies.isEmpty()) {
						addWSDDBuilderDependencies(project);
					}
				}

			});

		return configuration;
	}

	protected void addWSDDBuilderDependencies(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		dependencyHandler.add(
			CONFIGURATION_NAME,
			"com.liferay:com.liferay.portal.tools.wsdd.builder:latest.release");
	}

	protected WSDDBuilderArgs addWSDDBuilderExtension(Project project) {
		ExtensionContainer extensionContainer = project.getExtensions();

		return extensionContainer.create("wsddBuilder", WSDDBuilderArgs.class);
	}

	protected void configureWSDDBuilderExtension(
		Project project, WSDDBuilderArgs wsddBuilderArgs) {

		if (Validator.isNotNull(wsddBuilderArgs.getClassPath())) {
			return;
		}

		TaskContainer taskContainer = project.getTasks();

		Task compileJavaTask = taskContainer.findByName("compileJava");

		if (compileJavaTask == null) {
			return;
		}

		Task buildWSDDTask = taskContainer.getByName(BUILD_WSDD_TASK_NAME);

		buildWSDDTask.dependsOn(compileJavaTask);

		TaskOutputs taskOutputs = compileJavaTask.getOutputs();

		FileCollection fileCollection = taskOutputs.getFiles();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration configuration = configurationContainer.findByName(
			"runtime");

		if (configuration != null) {
			fileCollection = fileCollection.plus(configuration);
		}

		wsddBuilderArgs.setClassPath(fileCollection.getAsPath());
	}

}