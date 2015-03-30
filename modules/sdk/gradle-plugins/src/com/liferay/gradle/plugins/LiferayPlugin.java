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

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.util.StringUtil;
import com.liferay.gradle.plugins.util.Validator;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.ResolvedArtifact;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedModuleVersion;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.RelativePath;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.CopySpecResolver;
import org.gradle.api.java.archives.Manifest;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.War;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayPlugin extends BasePlugin {

	protected void configureDependencies() {
		configureDependenciesCompile();
		configureDependenciesProvidedCompile();
	}

	protected void configureDependencies(
		String configurationName, String... dependencyNotations) {

		DependencyHandler dependencyHandler = project.getDependencies();

		for (String dependencyNotation : dependencyNotations) {
			dependencyHandler.add(configurationName, dependencyNotation);
		}
	}

	protected void configureDependenciesCompile() {
		File serviceJarFile = project.file(
			"docroot/WEB-INF/lib/" + project.getName() + "-service.jar");

		if (serviceJarFile.exists()) {
			DependencyHandler dependencyHandler = project.getDependencies();

			dependencyHandler.add(
				JavaPlugin.COMPILE_CONFIGURATION_NAME,
				project.files(serviceJarFile));
		}
	}

	protected void configureDependenciesProvidedCompile() {
		configureDependencies(
			WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
			"biz.aQute.bnd:biz.aQute.bnd:2.4.1",
			"com.liferay.portal:portal-service:7.0.0-SNAPSHOT",
			"hsqldb:hsqldb:1.8.0.7", "javax.activation:activation:1.1",
			"javax.ccpp:ccpp:1.0", "javax.jms:jms:1.1", "javax.mail:mail:1.4",
			"javax.portlet:portlet-api:2.0", "javax.servlet.jsp:jsp-api:2.1",
			"javax.servlet:javax.servlet-api:3.0.1",
			"mysql:mysql-connector-java:5.1.23", "net.sf:jargs:1.0",
			"net.sourceforge.jtds:jtds:1.2.6",
			"org.eclipse.persistence:javax.persistence:2.0.0",
			"postgresql:postgresql:9.2-1002.jdbc4");

		String pluginType = _liferayExtension.getPluginType();

		if (!pluginType.equals("theme")) {
			configureDependencies(
				WarPlugin.PROVIDED_COMPILE_CONFIGURATION_NAME,
				"com.liferay.portal:util-bridges:7.0.0-SNAPSHOT",
				"com.liferay.portal:util-java:7.0.0-SNAPSHOT",
				"com.liferay.portal:util-taglib:7.0.0-SNAPSHOT",
				"commons-logging:commons-logging:1.1.1", "log4j:log4j:1.2.16");
		}
	}

	protected void configureRepositories() {
		RepositoryHandler repositoryHandler = project.getRepositories();

		repositoryHandler.maven(
			new Action<MavenArtifactRepository>() {

				@Override
				public void execute(
					MavenArtifactRepository mavenArtifactRepository) {

					mavenArtifactRepository.setUrl(REPOSITORY_URL);
				}

			});
	}

	protected void configureSourceSets() {
		SourceSet sourceSet = getSourceSet(SourceSet.MAIN_SOURCE_SET_NAME);

		SourceDirectorySet javaDirectorySet = sourceSet.getJava();

		Set<File> srcDirs = Collections.singleton(
			_liferayExtension.getPluginSrcDir());

		javaDirectorySet.setSrcDirs(srcDirs);

		SourceDirectorySet resourcesDirectorySet = sourceSet.getResources();

		resourcesDirectorySet.setSrcDirs(srcDirs);
	}

	protected void configureTaskClean() {
		Task cleanTask = getTask(CLEAN_TASK_NAME);

		configureTaskCleanDependsOn(cleanTask);
	}

	protected void configureTaskCleanDependsOn(Task cleanTask) {
		for (Task task : project.getTasks()) {
			String taskName =
				CLEAN_TASK_NAME + StringUtil.capitalize(task.getName());

			cleanTask.dependsOn(taskName);
		}

		Configuration compileConfiguration = getConfiguration(
			JavaPlugin.COMPILE_CONFIGURATION_NAME);

		Set<Dependency> compileDependencies =
			compileConfiguration.getAllDependencies();

		for (Dependency dependency : compileDependencies) {
			if (!(dependency instanceof ProjectDependency)) {
				continue;
			}

			ProjectDependency projectDependency = (ProjectDependency)dependency;

			Project dependencyProject =
				projectDependency.getDependencyProject();

			String taskName =
				dependencyProject.getPath() + Project.PATH_SEPARATOR +
					CLEAN_TASK_NAME;

			cleanTask.dependsOn(taskName);
		}
	}

	protected void configureTasks() {
		configureTaskClean();
		configureTaskWar();
	}

	protected void configureTaskWar() {
		War warTask = (War)getTask(WarPlugin.WAR_TASK_NAME);

		configureTaskWarDuplicatesStrategy(warTask);
		configureTaskWarExclude(warTask);
		configureTaskWarFilesMatching(warTask);
		configureTaskWarOutputs(warTask);
		configureTaskWarRenameDependencies(warTask);
	}

	protected void configureTaskWarDuplicatesStrategy(War warTask) {
		warTask.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTaskWarExclude(War warTask) {
		warTask.exclude("WEB-INF/lib");
	}

	protected void configureTaskWarFilesMatching(War warTask) {
		final Closure<String> filterLiferayHookXmlClosure =
			new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String line) {
				if (line.contains("content/Language*.properties")) {
					StringBuilder sb = new StringBuilder();

					File contentDir = new File(
						_liferayExtension.getPluginSrcDir(), "content");

					File[] files = contentDir.listFiles();

					for (int i = 0; i < files.length; i++) {
						File file = files[i];

						sb.append("\t<language-properties>content/");
						sb.append(file.getName());
						sb.append("</language-properties>");

						if ((i + 1) < files.length) {
							sb.append("\n");
						}
					}

					return sb.toString();
				}

				return line;
			}

		};

		warTask.filesMatching(
			"WEB-INF/liferay-hook.xml",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(filterLiferayHookXmlClosure);
				}

			});
	}

	protected void configureTaskWarManifest(War warTask) {
		File manifestFile = null;

		if (_liferayExtension.isOsgiPlugin()) {
			manifestFile = project.file("src/META-INF/MANIFEST.MF");
		}
		else {
			manifestFile = project.file("docroot/META-INF/MANIFEST.MF");
		}

		Manifest manifest = warTask.getManifest();

		if (manifestFile.exists()) {
			manifest.from(manifestFile);
		}
		else {
			CopySpecInternal copySpecInternal = warTask.getRootSpec();

			for (CopySpecInternal childCopySpecInternal :
					copySpecInternal.getChildren()) {

				CopySpecResolver copySpecResolver =
					childCopySpecInternal.buildRootResolver();

				RelativePath destRelativePath = copySpecResolver.getDestPath();

				String destRelativePathString =
					destRelativePath.getPathString();

				if (destRelativePathString.equals("META-INF")) {
					childCopySpecInternal.exclude("**");
				}
			}
		}
	}

	protected void configureTaskWarOutputs(War warTask) {
		TaskOutputs taskOutputs = warTask.getOutputs();

		taskOutputs.file(warTask.getArchivePath());
	}

	protected void configureTaskWarRenameDependencies(War warTask) {
		Closure<String> renameDependencyClosure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String name) {
				Map<String, String> newDependencyNames =
					_getNewDependencyNames();

				String newDependencyName = newDependencyNames.get(name);

				if (Validator.isNotNull(newDependencyName)) {
					return newDependencyName;
				}

				return name;
			}

			private Map<String, String> _getNewDependencyNames() {
				if (_newDependencyNames != null) {
					return _newDependencyNames;
				}

				_newDependencyNames = new HashMap<>();

				Configuration compileConfiguration = getConfiguration(
					JavaPlugin.COMPILE_CONFIGURATION_NAME);

				ResolvedConfiguration resolvedConfiguration =
					compileConfiguration.getResolvedConfiguration();

				for (ResolvedArtifact resolvedArtifact :
						resolvedConfiguration.getResolvedArtifacts()) {

					ResolvedModuleVersion resolvedModuleVersion =
						resolvedArtifact.getModuleVersion();

					ModuleVersionIdentifier moduleVersionIdentifier =
						resolvedModuleVersion.getId();

					String oldDependencyName =
						moduleVersionIdentifier.getName() + "-" +
							moduleVersionIdentifier.getVersion() + ".jar";
					String newDependencyName =
						moduleVersionIdentifier.getName() + ".jar";

					_newDependencyNames.put(
						oldDependencyName, newDependencyName);
				}

				return _newDependencyNames;
			}

			private Map<String, String> _newDependencyNames;

		};

		CopySpecInternal copySpecInternal = warTask.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			childCopySpecInternal.rename(renameDependencyClosure);
		}
	}

	protected void configureVersion() {
		String version = null;

		String moduleFullVersion = _liferayExtension.getPluginPackageProperty(
			"module-full-version");

		if (Validator.isNotNull(moduleFullVersion)) {
			version = moduleFullVersion;
		}
		else {
			String bundleVersion = _liferayExtension.getBndProperty(
				"Bundle-Version");

			if (Validator.isNotNull(bundleVersion)) {
				version = bundleVersion;
			}
			else {
				String moduleIncrementalVersion =
					_liferayExtension.getPluginPackageProperty(
						"module-incremental-version");

				version = PORTAL_VERSION + "." + moduleIncrementalVersion;
			}
		}

		project.setVersion(version);
	}

	protected void configureWebAppDirName() {
		WarPluginConvention warPluginConvention = getPluginConvention(
			WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	@Override
	protected void doApply() throws Exception {
		applyPlugin(WarPlugin.class);

		_liferayExtension = createExtension("liferay", LiferayExtension.class);

		configureDependencies();
		configureRepositories();
		configureSourceSets();
		configureVersion();
		configureWebAppDirName();

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					configureTasks();
				};

			});
	}

	private LiferayExtension _liferayExtension;
	private Map<String, String> _newDependencyNames;

}