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

package com.liferay.gradle.plugins.poshi.runner;

import com.liferay.gradle.plugins.poshi.runner.util.GradleUtil;
import com.liferay.gradle.plugins.poshi.runner.util.OSDetector;

import java.io.File;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.file.FileTree;
import org.gradle.api.reporting.DirectoryReport;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.TestTaskReports;
import org.gradle.api.tasks.testing.logging.TestLoggingContainer;

/**
 * @author Andrea Di Giorgi
 */
public class PoshiRunnerPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		final PoshiRunnerExtension poshiRunnerExtension =
			GradleUtil.addExtension(
				project, "poshiRunner", PoshiRunnerExtension.class);

		addPoshiRunnerConfiguration(project, poshiRunnerExtension);

		addTasksExpandPoshiRunner(project);
		addTasksRunPoshi(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasksExpandPoshiRunner(project);
					configureTasksRunPoshi(project, poshiRunnerExtension);
				}

			});
	}

	protected void addPoshiRunnerConfiguration(
		final Project project,
		final PoshiRunnerExtension poshiRunnerExtension) {

		final Configuration configuration = GradleUtil.addConfiguration(
			project, _POSHI_RUNNER_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures Poshi Runner for this project.");
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
						addPoshiRunnerDependenciesPoshiRunner(
							project, poshiRunnerExtension);
						addPoshiRunnerDependenciesSikuli(
							project, poshiRunnerExtension);
					}
				}

			});
	}

	protected void addPoshiRunnerDependenciesPoshiRunner(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

		GradleUtil.addDependency(
			project, _POSHI_RUNNER_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.poshi.runner", poshiRunnerExtension.getVersion(),
			null);
	}

	protected void addPoshiRunnerDependenciesSikuli(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

		String bitMode = OSDetector.getBitmode();

		if (bitMode.equals("32")) {
			bitMode = "x86";
		}
		else {
			bitMode = "x86_64";
		}

		String os = "linux";

		if (OSDetector.isApple()) {
			os = "macosx";
		}
		else if (OSDetector.isWindows()) {
			os = "windows";
		}

		String classifier = os + "-" + bitMode;

		GradleUtil.addDependency(
			project, _POSHI_RUNNER_CONFIGURATION_NAME,
			"org.bytedeco.javacpp-presets", "opencv",
			poshiRunnerExtension.getOpenCVVersion(), classifier);
	}

	protected void addTasksExpandPoshiRunner(Project project) {
		Copy copy = GradleUtil.addTask(
			project, _EXPAND_POSHI_RUNNER_TASK_NAME, Copy.class);

		copy.into(getExpandedPoshiRunnerDir(project));
	}

	protected void addTasksRunPoshi(Project project) {
		Test test = GradleUtil.addTask(
			project, _RUN_POSHI_TASK_NAME, Test.class);

		test.dependsOn(_EXPAND_POSHI_RUNNER_TASK_NAME);

		test.include("com/liferay/poshi/runner/PoshiRunner.class");

		Configuration configuration = GradleUtil.getConfiguration(
			test.getProject(), _POSHI_RUNNER_CONFIGURATION_NAME);

		test.setClasspath(configuration);
		test.setDescription("Execute tests using Poshi Runner.");
		test.setGroup("verification");
		test.setScanForTestClasses(false);
		test.setTestClassesDir(getExpandedPoshiRunnerDir(project));

		TestLoggingContainer testLoggingContainer = test.getTestLogging();

		testLoggingContainer.setShowStandardStreams(true);

		test.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Test test = (Test)task;

					Map<String, Object> systemProperties =
						test.getSystemProperties();

					if (!systemProperties.containsKey("test.name")) {
						throw new GradleException(
							"Please set the property poshiTestName.");
					}
				}

		});
	}

	protected void configureTasksExpandPoshiRunner(Project project) {
		Copy copy = (Copy)GradleUtil.getTask(
			project, _EXPAND_POSHI_RUNNER_TASK_NAME);

		configureTasksExpandPoshiRunnerFrom(copy);
	}

	protected void configureTasksExpandPoshiRunnerFrom(Copy copy) {
		Project project = copy.getProject();

		Configuration configuration = GradleUtil.getConfiguration(
			project, _POSHI_RUNNER_CONFIGURATION_NAME);

		Iterator<File> iterator = configuration.iterator();

		while (iterator.hasNext()) {
			File file = iterator.next();

			String fileName = file.getName();

			if (fileName.startsWith("com.liferay.poshi.runner-")) {
				FileTree fileTree = project.zipTree(file);

				copy.from(fileTree);

				return;
			}
		}
	}

	protected void configureTasksRunPoshi(
		Project project, PoshiRunnerExtension poshiRunnerExtension) {

		Test test = (Test)GradleUtil.getTask(project, _RUN_POSHI_TASK_NAME);

		configureTasksRunPoshiBinResultsDir(test);
		configureTasksRunPoshiReports(test);
		configureTasksRunPoshiSystemProperties(test, poshiRunnerExtension);
	}

	protected void configureTasksRunPoshiBinResultsDir(Test test) {
		if (test.getBinResultsDir() != null) {
			return;
		}

		Project project = test.getProject();

		test.setBinResultsDir(
			project.file("test-results/binary/" + _RUN_POSHI_TASK_NAME));
	}

	protected void configureTasksRunPoshiReports(Test test) {
		Project project = test.getProject();
		TestTaskReports testTaskReports = test.getReports();

		DirectoryReport directoryReport = testTaskReports.getHtml();

		if (directoryReport.getDestination() == null) {
			directoryReport.setDestination(project.file("tests"));
		}

		directoryReport = testTaskReports.getJunitXml();

		if (directoryReport.getDestination() == null) {
			directoryReport.setDestination(project.file("test-results"));
		}
	}

	protected void configureTasksRunPoshiSystemProperties(
		Test test, PoshiRunnerExtension poshiRunnerExtension) {

		Map<String, Object> systemProperties = test.getSystemProperties();

		systemProperties.putAll(poshiRunnerExtension.getPoshiProperties());

		systemProperties.put("test.basedir", poshiRunnerExtension.getBaseDir());

		Project project = test.getProject();

		if (project.hasProperty("poshiTestName")) {
			systemProperties.put(
				"test.name", project.property("poshiTestName"));
		}
	}

	protected File getExpandedPoshiRunnerDir(Project project) {
		return new File(project.getBuildDir(), "poshi-runner");
	}

	private static final String _EXPAND_POSHI_RUNNER_TASK_NAME =
		"expandPoshiRunner";

	private static final String _POSHI_RUNNER_CONFIGURATION_NAME =
		"poshiRunner";

	private static final String _RUN_POSHI_TASK_NAME = "runPoshi";

}