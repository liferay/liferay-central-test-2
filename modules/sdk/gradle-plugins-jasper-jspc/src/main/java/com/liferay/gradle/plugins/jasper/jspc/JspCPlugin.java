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

package com.liferay.gradle.plugins.jasper.jspc;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class JspCPlugin implements Plugin<Project> {

	public static final String COMPILE_JSP_TASK_NAME = "compileJSP";

	public static final String CONFIGURATION_NAME = "jspC";

	public static final String EXTENSION_NAME = "jspC";

	public static final String GENERATE_JSP_JAVA_TASK_NAME = "generateJSPJava";

	public static final String TOOL_CONFIGURATION_NAME = "jspCTool";

	@Override
	public void apply(Project project) {
		GradleUtil.applyPlugin(project, JavaPlugin.class);

		final JspCExtension jspCExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, JspCExtension.class);

		Configuration jspCConfiguration = addJspCConfiguration(project);
		Configuration jspCToolConfiguration = addJspCToolConfiguration(project);

		final CompileJSPTask generateJSPJavaTask = addTaskGenerateJSPJava(
			project, jspCConfiguration, jspCToolConfiguration);

		addTaskCompileJSP(
			generateJSPJavaTask, jspCConfiguration, jspCToolConfiguration);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					addJspCDependencies(project);
					configureJspcExtension(project, jspCExtension);

					jspCExtension.copyTo(generateJSPJavaTask);
				}

			});
	}

	protected Configuration addJspCConfiguration(Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, CONFIGURATION_NAME);

		configuration.setDescription(
			"Configures the classpath of the JSP compilation tasks.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addJspCDependencies(Project project) {
		DependencyHandler dependencyHandler = project.getDependencies();

		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		dependencyHandler.add(CONFIGURATION_NAME, sourceSet.getOutput());

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_CONFIGURATION_NAME);

		dependencyHandler.add(CONFIGURATION_NAME, configuration);
	}

	protected Configuration addJspCToolConfiguration(final Project project) {
		Configuration configuration = GradleUtil.addConfiguration(
			project, TOOL_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					addJspCToolDependencies(project);
				}

			});

		configuration.setDescription(
			"Configures Liferay Jasper JspC for this project.");
		configuration.setVisible(false);

		return configuration;
	}

	protected void addJspCToolDependencies(Project project) {
		GradleUtil.addDependency(
			project, TOOL_CONFIGURATION_NAME, "org.apache.ant", "ant", "1.9.4");
		GradleUtil.addDependency(
			project, TOOL_CONFIGURATION_NAME, "com.liferay",
			"com.liferay.jasper.jspc", "latest.release");
	}

	protected JavaCompile addTaskCompileJSP(
		CompileJSPTask generateJSPJavaTask, Configuration jspCConfiguration,
		Configuration jspCToolConfiguration) {

		JavaCompile javaCompile = GradleUtil.addTask(
			generateJSPJavaTask.getProject(), COMPILE_JSP_TASK_NAME,
			JavaCompile.class);

		javaCompile.setClasspath(jspCToolConfiguration.plus(jspCConfiguration));
		javaCompile.setDescription("Compile JSP files to check for errors.");
		javaCompile.setDestinationDir(javaCompile.getTemporaryDir());
		javaCompile.setGroup("verification");
		javaCompile.setSource(generateJSPJavaTask.getOutputs());

		return javaCompile;
	}

	protected CompileJSPTask addTaskGenerateJSPJava(
		Project project, Configuration jspCConfiguration,
		Configuration jspCToolConfiguration) {

		CompileJSPTask compileJSPTask = GradleUtil.addTask(
			project, GENERATE_JSP_JAVA_TASK_NAME, CompileJSPTask.class);

		compileJSPTask.setClasspath(jspCToolConfiguration);
		compileJSPTask.setDestinationDir(
			new File(project.getBuildDir(), "jspc"));
		compileJSPTask.setJspCClasspath(jspCConfiguration);

		return compileJSPTask;
	}

	protected void configureJspcExtension(
		Project project, JspCExtension jspCExtension) {

		configureJspCExtensionWebAppDir(project, jspCExtension);
	}

	protected void configureJspCExtensionWebAppDir(
		Project project, JspCExtension jspCExtension) {

		if (jspCExtension.getWebAppDir() != null) {
			return;
		}

		File webAppDir = null;

		PluginContainer pluginContainer = project.getPlugins();

		if (pluginContainer.hasPlugin(WarPlugin.class)) {
			WarPluginConvention warPluginConvention = GradleUtil.getConvention(
				project, WarPluginConvention.class);

			webAppDir = warPluginConvention.getWebAppDir();
		}
		else {
			SourceSet sourceSet = GradleUtil.getSourceSet(
				project, SourceSet.MAIN_SOURCE_SET_NAME);

			SourceDirectorySet sourceDirectorySet = sourceSet.getResources();

			Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

			Iterator<File> iterator = srcDirs.iterator();

			webAppDir = iterator.next();
		}

		jspCExtension.setWebAppDir(webAppDir);
	}

}