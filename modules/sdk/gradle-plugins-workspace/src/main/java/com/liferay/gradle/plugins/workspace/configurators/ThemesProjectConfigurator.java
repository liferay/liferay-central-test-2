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

package com.liferay.gradle.plugins.workspace.configurators;

import com.liferay.gradle.plugins.LiferayJavaPlugin;
import com.liferay.gradle.plugins.gulp.ExecuteGulpTask;
import com.liferay.gradle.plugins.gulp.GulpPlugin;
import com.liferay.gradle.plugins.node.NodePlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import groovy.json.JsonOutput;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.dsl.ArtifactHandler;
import org.gradle.api.file.ConfigurableFileTree;
import org.gradle.api.file.CopySpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.tasks.Delete;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class ThemesProjectConfigurator extends BaseProjectConfigurator {

	public static final String CREATE_LIFERAY_THEME_JSON_TASK_NAME =
		"createLiferayThemeJson";

	public ThemesProjectConfigurator(Settings settings) {
		super(settings);
	}

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		// liferay-theme-tasks already uses the "build" directory

		project.setBuildDir("build_gradle");

		GradleUtil.applyPlugin(project, BasePlugin.class);
		GradleUtil.applyPlugin(project, GulpPlugin.class);

		Task createLiferayThemeJsonTask = addTaskCreateLiferayThemeJson(
			project, workspaceExtension);

		addTaskDeploy(project);
		configureArtifacts(project);
		configureTaskAssemble(project);
		configureTaskClean(project);
		configureTasksExecuteGulp(project, createLiferayThemeJsonTask);

		configureRootTaskDistBundle(
			project, RootProjectConfigurator.DIST_BUNDLE_TAR_TASK_NAME);
		configureRootTaskDistBundle(
			project, RootProjectConfigurator.DIST_BUNDLE_ZIP_TASK_NAME);
	}

	@Override
	public String getName() {
		return _NAME;
	}

	protected Task addTaskCreateLiferayThemeJson(
		Project project, final WorkspaceExtension workspaceExtension) {

		Task task = project.task(CREATE_LIFERAY_THEME_JSON_TASK_NAME);

		final File liferayThemeJsonFile = project.file("liferay-theme.json");

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					Map<String, Object> map = new HashMap<>();

					File appServerDir = new File(
						workspaceExtension.getHomeDir(), "tomcat-8.0.30");

					map.put("appServerPath", appServerDir.getAbsolutePath());

					File appServerThemeDir = new File(
						appServerDir, "webapps/" + project.getName());

					map.put(
						"appServerPathTheme",
						appServerThemeDir.getAbsolutePath());

					map.put("deployed", false);

					File deployDir = new File(
						workspaceExtension.getHomeDir(), "osgi/modules");

					map.put("deployPath", deployDir.getAbsolutePath());
					map.put("themeName", project.getName());

					String json = JsonOutput.toJson(
						Collections.singletonMap("LiferayTheme", map));

					try {
						Files.write(
							liferayThemeJsonFile.toPath(),
							json.getBytes(StandardCharsets.UTF_8));
					}
					catch (IOException ioe) {
						throw new GradleException(ioe.getMessage(), ioe);
					}
				}

			});

		return task;
	}

	protected Task addTaskDeploy(Project project) {
		Task task = project.task(LiferayJavaPlugin.DEPLOY_TASK_NAME);

		task.dependsOn(_GULP_DEPLOY_TASK_NAME);
		task.setDescription("Assembles the theme and deploys it to Liferay.");

		return task;
	}

	protected void configureRootTaskDistBundle(
		final Project project, String rootTaskName) {

		CopySpec copySpec = (CopySpec)GradleUtil.getTask(
			project.getRootProject(), rootTaskName);

		copySpec.into(
			"osgi/modules",
			new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					ConfigurableFileTree fileTree = project.fileTree("dist");

					fileTree.builtBy(_GULP_DEPLOY_TASK_NAME);
					fileTree.include("*.war");

					copySpec.from(fileTree);
				}

			});
	}

	protected void configureArtifacts(Project project) {
		ArtifactHandler artifactHandler = project.getArtifacts();

		String projectName = project.getName();

		String outputFileName = "dist/" + projectName + ".war";

		File outputFile = new File(outputFileName);

		artifactHandler.add(Dependency.ARCHIVES_CONFIGURATION, outputFile);
	}

	protected void configureTaskAssemble(Project project) {
		Task task = GradleUtil.getTask(project, BasePlugin.ASSEMBLE_TASK_NAME);

		task.dependsOn(_GULP_BUILD_TASK_NAME);
	}

	protected void configureTaskClean(Project project) {
		Delete delete = (Delete)GradleUtil.getTask(
			project, BasePlugin.CLEAN_TASK_NAME);

		delete.delete("build", "dist");
		delete.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(NodePlugin.NPM_INSTALL_TASK_NAME));
	}

	protected void configureTaskExecuteGulp(
		ExecuteGulpTask executeGulpTask, Task createLiferayThemeJsonTask) {

		executeGulpTask.dependsOn(
			createLiferayThemeJsonTask, NodePlugin.NPM_INSTALL_TASK_NAME);
	}

	protected void configureTasksExecuteGulp(
		Project project, final Task createLiferayThemeJsonTask) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			ExecuteGulpTask.class,
			new Action<ExecuteGulpTask>() {

				@Override
				public void execute(ExecuteGulpTask executeGulpTask) {
					configureTaskExecuteGulp(
						executeGulpTask, createLiferayThemeJsonTask);
				}

			});
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path dirNamePath = dirPath.getFileName();

					String dirName = dirNamePath.toString();

					if (dirName.equals("build") ||
						dirName.equals("build_gradle") ||
						dirName.equals("node_modules")) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					if (Files.exists(dirPath.resolve("package.json"))) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	private static final String _GULP_BUILD_TASK_NAME = "gulpBuild";

	private static final String _GULP_DEPLOY_TASK_NAME = "gulpDeploy";

	private static final String _NAME = "themes";

}