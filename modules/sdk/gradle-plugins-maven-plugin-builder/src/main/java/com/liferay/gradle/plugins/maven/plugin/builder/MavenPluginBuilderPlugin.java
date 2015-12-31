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

package com.liferay.gradle.plugins.maven.plugin.builder;

import com.liferay.gradle.util.GradleUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.plugins.osgi.OsgiHelper;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;

/**
 * @author Andrea Di Giorgi
 */
public class MavenPluginBuilderPlugin implements Plugin<Project> {

	public static final String BUILD_PLUGIN_DESCRIPTOR_TASK_NAME =
		"buildPluginDescriptor";

	@Override
	public void apply(Project project) {
		addTaskBuildPluginDescriptor(project);
	}

	protected BuildPluginDescriptorTask addTaskBuildPluginDescriptor(
		final Project project) {

		BuildPluginDescriptorTask buildPluginDescriptorTask =
			GradleUtil.addTask(
				project, BUILD_PLUGIN_DESCRIPTOR_TASK_NAME,
				BuildPluginDescriptorTask.class);

		buildPluginDescriptorTask.dependsOn(JavaPlugin.COMPILE_JAVA_TASK_NAME);

		buildPluginDescriptorTask.setClassesDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getClassesDir(project);
				}

			});

		buildPluginDescriptorTask.setDescription(
			"Generates the Maven plugin descriptor for the project.");
		buildPluginDescriptorTask.setGroup(BasePlugin.BUILD_GROUP);

		buildPluginDescriptorTask.setOutputDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					File resourcesDir = getResourcesDir(project);

					return new File(resourcesDir, "META-INF/maven");
				}

			});

		buildPluginDescriptorTask.setPomArtifactId(
			new Callable<String>() {

				@Override
				public String call() throws Exception {
					return _osgiHelper.getBundleSymbolicName(project);
				}

			});

		buildPluginDescriptorTask.setPomGroupId(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getGroup();
				}

			});

		buildPluginDescriptorTask.setPomVersion(
			new Callable<Object>() {

				@Override
				public Object call() throws Exception {
					return project.getVersion();
				}

			});

		buildPluginDescriptorTask.setSourceDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return getJavaDir(project);
				}

			});

		return buildPluginDescriptorTask;
	}

	protected File getClassesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		return sourceSetOutput.getClassesDir();
	}

	protected File getJavaDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getJava());
	}

	protected File getResourcesDir(Project project) {
		SourceSet sourceSet = GradleUtil.getSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME);

		return getSrcDir(sourceSet.getResources());
	}

	protected File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	private static final OsgiHelper _osgiHelper = new OsgiHelper();

}