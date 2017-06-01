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

package com.liferay.gradle.plugins.js.transpiler;

import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.node.tasks.DownloadNodeModuleTask;
import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask;
import com.liferay.gradle.plugins.node.tasks.NpmInstallTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskDependency;

/**
 * @author Andrea Di Giorgi
 */
public class JSTranspilerPlugin implements Plugin<Project> {

	public static final String DOWNLOAD_METAL_CLI_TASK_NAME =
		"downloadMetalCli";

	public static final String JS_COMPILE_CONFIGURATION_NAME = "jsCompile";

	public static final String SOY_COMPILE_CONFIGURATION_NAME = "soyCompile";

	public static final String TRANSPILE_JS_TASK_NAME = "transpileJS";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, NodePlugin.class);

		final NpmInstallTask npmInstallTask =
			(NpmInstallTask)GradleUtil.getTask(
				project, NodePlugin.NPM_INSTALL_TASK_NAME);

		final DownloadNodeModuleTask downloadMetalCliTask =
			_addTaskDownloadMetalCli(project);

		final Configuration jsCompileConfiguration = _addConfigurationJSCompile(
			project);
		final Configuration soyCompileConfiguration =
			_addConfigurationSoyCompile(project);

		final TranspileJSTask transpileJSTask = _addTaskTranspileJS(project);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_addTasksExpandJSCompileDependencies(
						transpileJSTask, npmInstallTask,
						jsCompileConfiguration);
					_addTasksExpandSoyCompileDependencies(
						transpileJSTask, soyCompileConfiguration);

					_configureTasksTranspileJS(
						project, downloadMetalCliTask, npmInstallTask);
				}

			});
	}

	private Configuration _addConfigurationJSCompile(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, JS_COMPILE_CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures additional JavaScript dependencies.");
		configuration.setVisible(false);

		return configuration;
	}

	private Configuration _addConfigurationSoyCompile(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, SOY_COMPILE_CONFIGURATION_NAME);

		configuration.setDescription("Configures additional Soy dependencies.");
		configuration.setVisible(false);

		return configuration;
	}

	private DownloadNodeModuleTask _addTaskDownloadMetalCli(Project project) {
		DownloadNodeModuleTask downloadNodeModuleTask = GradleUtil.addTask(
			project, DOWNLOAD_METAL_CLI_TASK_NAME,
			DownloadNodeModuleTask.class);

		downloadNodeModuleTask.setModuleName("metal-cli");
		downloadNodeModuleTask.setModuleVersion(_METAL_CLI_VERSION);

		return downloadNodeModuleTask;
	}

	private Copy _addTaskExpandCompileDependency(
		Project project, File file, File destinationDir, String taskNamePrefix,
		RenameDependencyClosure renameDependencyClosure) {

		String taskName = GradleUtil.getTaskName(taskNamePrefix, file);

		Copy copy = GradleUtil.addTask(project, taskName, Copy.class);

		copy.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Copy copy = (Copy)task;

					Project project = copy.getProject();

					project.delete(copy.getDestinationDir());
				}

			});

		copy.from(project.zipTree(file));

		String name = renameDependencyClosure.call(file.getName());

		name = name.substring(0, name.length() - 4);

		destinationDir = new File(destinationDir, name);

		copy.setDescription(
			"Expands " + file.getName() + " into " +
				project.relativePath(destinationDir) + ".");
		copy.setDestinationDir(destinationDir);

		return copy;
	}

	private void _addTasksExpandJSCompileDependencies(
		TranspileJSTask transpileJSTask, NpmInstallTask npmInstallTask,
		Configuration configuration) {

		Project project = transpileJSTask.getProject();

		RenameDependencyClosure renameDependencyClosure =
			new RenameDependencyClosure(project, configuration.getName());

		Iterable<TaskDependency> taskDependencies = _getTaskDependencies(
			configuration);

		for (File file : configuration) {
			Copy copy = _addTaskExpandCompileDependency(
				project, file, npmInstallTask.getNodeModulesDir(),
				"expandJSCompileDependency", renameDependencyClosure);

			copy.dependsOn(taskDependencies);
			copy.mustRunAfter(npmInstallTask);

			transpileJSTask.dependsOn(copy);
		}
	}

	private void _addTasksExpandSoyCompileDependencies(
		TranspileJSTask transpileJSTask, Configuration configuration) {

		Project project = transpileJSTask.getProject();

		RenameDependencyClosure renameDependencyClosure =
			new RenameDependencyClosure(project, configuration.getName());

		Iterable<TaskDependency> taskDependencies = _getTaskDependencies(
			configuration);

		for (File file : configuration) {
			Copy copy = _addTaskExpandCompileDependency(
				project, file, project.getBuildDir(),
				"expandSoyCompileDependency", renameDependencyClosure);

			copy.dependsOn(taskDependencies);

			transpileJSTask.dependsOn(copy);

			String path = FileUtil.getAbsolutePath(copy.getDestinationDir());

			path += "/META-INF/resources/**/*.soy";

			transpileJSTask.soyDependency(path);
		}
	}

	private TranspileJSTask _addTaskTranspileJS(Project project) {
		final TranspileJSTask transpileJSTask = GradleUtil.addTask(
			project, TRANSPILE_JS_TASK_NAME, TranspileJSTask.class);

		transpileJSTask.setDescription("Transpiles JS files.");
		transpileJSTask.setGroup(BasePlugin.BUILD_GROUP);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureTaskTranspileJSForJavaPlugin(transpileJSTask);
				}

			});

		return transpileJSTask;
	}

	private void _configureTasksTranspileJS(
		Project project, final DownloadNodeModuleTask downloadMetalCliTask,
		final ExecuteNpmTask npmInstallTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			TranspileJSTask.class,
			new Action<TranspileJSTask>() {

				@Override
				public void execute(TranspileJSTask transpileJSTask) {
					_configureTaskTranspileJS(
						transpileJSTask, downloadMetalCliTask, npmInstallTask);
				}

			});
	}

	private void _configureTaskTranspileJS(
		TranspileJSTask transpileJSTask,
		final DownloadNodeModuleTask downloadMetalCliTask,
		final ExecuteNpmTask npmInstallTask) {

		FileCollection fileCollection = transpileJSTask.getSourceFiles();

		if (!transpileJSTask.isEnabled() ||
			(transpileJSTask.isSkipWhenEmpty() && fileCollection.isEmpty())) {

			transpileJSTask.setDependsOn(Collections.emptySet());
			transpileJSTask.setEnabled(false);

			return;
		}

		transpileJSTask.dependsOn(downloadMetalCliTask, npmInstallTask);

		transpileJSTask.setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						downloadMetalCliTask.getModuleDir(), "index.js");
				}

			});

		transpileJSTask.soyDependency(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return npmInstallTask.getWorkingDir() +
						"/node_modules/lexicon*/src/**/*.soy";
				}

			},
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return npmInstallTask.getWorkingDir() +
						"/node_modules/metal*/src/**/*.soy";
				}

			});
	}

	private void _configureTaskTranspileJSForJavaPlugin(
		TranspileJSTask transpileJSTask) {

		transpileJSTask.mustRunAfter(JavaPlugin.PROCESS_RESOURCES_TASK_NAME);

		Project project = transpileJSTask.getProject();

		final SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		transpileJSTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = _getSrcDir(sourceSet.getResources());

					return new File(resourcesDir, "META-INF/resources");
				}

			});

		transpileJSTask.setWorkingDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					SourceSetOutput sourceSetOutput = sourceSet.getOutput();

					return new File(
						sourceSetOutput.getResourcesDir(),
						"META-INF/resources");
				}

			});

		Task classesTask = GradleUtil.getTask(
			project, JavaPlugin.CLASSES_TASK_NAME);

		classesTask.dependsOn(transpileJSTask);
	}

	private File _getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private Iterable<TaskDependency> _getTaskDependencies(
		Configuration configuration) {

		Set<TaskDependency> taskDependencies = new HashSet<>();

		DependencySet dependencySet = configuration.getAllDependencies();

		for (ProjectDependency projectDependency : dependencySet.withType(
				ProjectDependency.class)) {

			taskDependencies.add(projectDependency.getBuildDependencies());
		}

		return taskDependencies;
	}

	private static final String _METAL_CLI_VERSION = "1.3.1";

}