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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.app.javadoc.builder.AppJavadocBuilderExtension;
import com.liferay.gradle.plugins.app.javadoc.builder.AppJavadocBuilderPlugin;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.tasks.WritePropertiesTask;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.util.Properties;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.javadoc.Javadoc;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayAppDefaultsPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		String appDescription = null;
		String appTitle = null;
		String appVersion = null;

		try {
			Properties appBndProperties = FileUtil.readProperties(
				project, "app.bnd");

			appDescription = appBndProperties.getProperty(
				"Liferay-Releng-App-Description");

			File relengDir = LiferayRelengPlugin.getRelengDir(project);

			if (relengDir != null) {
				File appPropertiesFile = new File(relengDir, "app.properties");

				Properties appProperties = FileUtil.readProperties(
					appPropertiesFile);

				appTitle = appProperties.getProperty("app.marketplace.title");
				appVersion = appProperties.getProperty(
					"app.marketplace.version");
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}

		GradleUtil.applyPlugin(project, AppJavadocBuilderPlugin.class);

		configureAppJavadocBuilder(project);
		configureProject(project, appDescription, appVersion);
		configureTaskAppJavadoc(project, appTitle, appVersion);
	}

	protected void configureAppJavadocBuilder(Project project) {
		AppJavadocBuilderExtension appJavadocBuilderExtension =
			GradleUtil.getExtension(project, AppJavadocBuilderExtension.class);

		appJavadocBuilderExtension.setGroupNameClosure(
			new Closure<String>(project) {

				@SuppressWarnings("unused")
				public String doCall(Project subproject) {
					return getAppJavadocGroupName(subproject);
				}

			});
	}

	protected void configureProject(
		Project project, String description, String version) {

		if (Validator.isNotNull(description)) {
			project.setDescription(description);
		}

		if (Validator.isNotNull(version)) {
			project.setVersion(version);
		}
	}

	protected void configureTaskAppJavadoc(
		Project project, String appTitle, String appVersion) {

		if (Validator.isNull(appTitle) || Validator.isNull(appVersion)) {
			return;
		}

		Javadoc javadoc = (Javadoc)GradleUtil.getTask(
			project, AppJavadocBuilderPlugin.APP_JAVADOC_TASK_NAME);

		String title = String.format("%s %s API", appTitle, appVersion);

		javadoc.setTitle(title);
	}

	protected String getAppJavadocGroupName(Project project) {
		String groupName = project.getDescription();

		if (Validator.isNull(groupName)) {
			groupName = project.getName();
		}

		TaskContainer taskContainer = project.getTasks();

		WritePropertiesTask recordArtifactTask =
			(WritePropertiesTask)taskContainer.findByName(
				LiferayRelengPlugin.RECORD_ARTIFACT_TASK_NAME);

		if (recordArtifactTask != null) {
			Properties artifactProperties =
				LiferayRelengPlugin.getArtifactProperties(recordArtifactTask);

			String artifactURL = artifactProperties.getProperty("artifact.url");

			if (Validator.isNotNull(artifactURL)) {
				int start = artifactURL.lastIndexOf('/') + 1;
				int end = artifactURL.lastIndexOf('.');

				int pos = artifactURL.indexOf('-', start);

				String moduleName = artifactURL.substring(start, pos);
				String moduleVersion = artifactURL.substring(pos + 1, end);

				groupName =
					moduleName + ' ' + moduleVersion + " - " + groupName;
			}
		}

		return groupName;
	}

}