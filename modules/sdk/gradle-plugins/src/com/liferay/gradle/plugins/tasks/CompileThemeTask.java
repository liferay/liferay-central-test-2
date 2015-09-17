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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.plugins.LiferayThemePlugin;
import com.liferay.gradle.util.ArrayUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.StripPathSegmentsAction;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.TaskAction;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CompileThemeTask extends DefaultTask {

	public CompileThemeTask() {
		_project = getProject();
	}

	@TaskAction
	public void compileTheme() throws Exception {
		copyThemeParent();

		copyDiffs();
	}

	@InputDirectory
	@Optional
	public File getDiffsDir() {
		return GradleUtil.toFile(_project, _diffsDir);
	}

	@InputFiles
	@Optional
	public FileCollection getFrontendThemeFiles() {
		return _frontendThemeFiles;
	}

	@OutputDirectories
	public FileCollection getThemeDirs() {
		if (getDiffsDir() == null) {
			return _project.files();
		}

		List<File> themeDirs = new ArrayList<>(_THEME_DIR_NAMES.length);

		File themeRootDir = getThemeRootDir();

		for (String dirName : _THEME_DIR_NAMES) {
			File dir = new File(themeRootDir, dirName);

			themeDirs.add(dir);
		}

		return _project.files(themeDirs);
	}

	@Input
	@Optional
	public String getThemeParent() {
		return GradleUtil.toString(_themeParent);
	}

	public Project getThemeParentProject() {
		String themeParent = getThemeParent();

		if (Validator.isNull(themeParent) ||
			ArrayUtil.contains(_PORTAL_THEMES, themeParent)) {

			return null;
		}

		if (_themeParentProject == null) {
			File themeParentDir = _project.file(themeParent);

			_themeParentProject = GradleUtil.getProject(
				_project.getRootProject(), themeParentDir);
		}

		return _themeParentProject;
	}

	public File getThemeRootDir() {
		return GradleUtil.toFile(_project, _themeRootDir);
	}

	@Input
	public Set<String> getThemeTypes() {
		return _themeTypes;
	}

	public void setDiffsDir(Object diffsDir) {
		_diffsDir = diffsDir;
	}

	public void setFrontendThemeFiles(FileCollection frontendThemeFiles) {
		_frontendThemeFiles = frontendThemeFiles;
	}

	public void setThemeParent(Object themeParent) {
		_themeParent = themeParent;
		_themeParentProject = null;
	}

	public void setThemeRootDir(Object themeRootDir) {
		_themeRootDir = themeRootDir;
	}

	public void setThemeTypes(Iterable<String> themeTypes) {
		_themeTypes.clear();

		themeTypes(themeTypes);
	}

	public CompileThemeTask themeTypes(Iterable<String> themeTypes) {
		GUtil.addToCollection(_themeTypes, themeTypes);

		return this;
	}

	public CompileThemeTask themeTypes(String ... themeTypes) {
		return themeTypes(Arrays.asList(themeTypes));
	}

	protected void copyDiffs() {
		final File diffsDir = getDiffsDir();

		if ((diffsDir == null) || !diffsDir.exists()) {
			return;
		}

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				copySpec.from(diffsDir);
				copySpec.into(getThemeRootDir());
			}

		};

		_project.copy(closure);
	}

	protected void copyPortalThemeDir(
			String theme, String[] excludes, String include)
		throws Exception {

		copyPortalThemeDir(theme, excludes, new String[] {include});
	}

	protected void copyPortalThemeDir(
			String theme, String[] excludes, String[] includes)
		throws Exception {

		String prefix = "META-INF/resources/" + theme + "/";

		final File frontendThemeFile = getFrontendThemeFile(theme);
		final String[] prefixedExcludes = StringUtil.prepend(excludes, prefix);
		final String[] prefixedIncludes = StringUtil.prepend(includes, prefix);

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				copySpec.eachFile(new StripPathSegmentsAction(3));

				if (ArrayUtil.isNotEmpty(prefixedExcludes)) {
					copySpec.exclude(prefixedExcludes);
				}

				copySpec.from(_project.zipTree(frontendThemeFile));
				copySpec.include(prefixedIncludes);
				copySpec.into(getThemeRootDir());
				copySpec.setIncludeEmptyDirs(false);
			}

		};

		_project.copy(closure);
	}

	protected void copyThemeParent() throws Exception {
		String themeParent = getThemeParent();

		if (Validator.isNull(themeParent)) {
			return;
		}

		if (themeParent.equals("_styled") || themeParent.equals("_unstyled")) {
			copyThemeParentUnstyled();
		}

		if (themeParent.equals("_styled")) {
			copyThemeParentStyled();
		}
		else if (themeParent.equals("admin") || themeParent.equals("classic")) {
			copyThemeParentPortal();
		}
		else if (!themeParent.equals("_unstyled")) {
			copyThemeParentProject();
		}
	}

	protected void copyThemeParentPortal() throws Exception {
		String themeParent = getThemeParent();

		copyPortalThemeDir(
			themeParent, new String[] {
				"**/.sass-cache/**", "_diffs/**", "templates/**"
			},
			"**");

		Set<String> themeTypes = getThemeTypes();

		String[] includes = StringUtil.prepend(
			themeTypes.toArray(new String[themeTypes.size()]), "templates/*.");

		copyPortalThemeDir(themeParent, null, includes);
	}

	protected void copyThemeParentProject() {
		Project themeParentProject = getThemeParentProject();

		CompileThemeTask compileParentThemeTask =
			(CompileThemeTask)GradleUtil.getTask(
				themeParentProject, LiferayThemePlugin.COMPILE_THEME_TASK_NAME);

		final File parentThemeRootDir =
			compileParentThemeTask.getThemeRootDir();

		Closure<Void> closure = new Closure<Void>(null) {

			@SuppressWarnings("unused")
			public void doCall(CopySpec copySpec) {
				copySpec.from(parentThemeRootDir);

				for (String dirName : _THEME_DIR_NAMES) {
					copySpec.include(dirName + "/**");
				}

				copySpec.into(getThemeRootDir());
			}

		};

		_project.copy(closure);
	}

	protected void copyThemeParentStyled() throws Exception {
		copyPortalThemeDir(
			"_styled",
			new String[] {"**/*.css", "npm-debug.log", "package.json"}, "**");
	}

	protected void copyThemeParentUnstyled() throws Exception {
		copyPortalThemeDir(
			"_unstyled",
			new String[] {
				"**/*.css", "npm-debug.log", "package.json", "templates/**"
			},
			"**");

		Set<String> themeTypes = getThemeTypes();

		String[] themeTypesArray = themeTypes.toArray(
			new String[themeTypes.size()]);

		String[] includes = StringUtil.prepend(
			themeTypesArray, "templates/**/*.");

		copyPortalThemeDir("_unstyled", null, includes);
	}

	protected File getFrontendThemeFile(String theme) throws Exception {
		for (File file : getFrontendThemeFiles()) {
			try (JarFile jarFile = new JarFile(file)) {
				JarEntry jarEntry = jarFile.getJarEntry(
					"META-INF/resources/" + theme);

				if (jarEntry != null) {
					return file;
				}
			}
		}

		return null;
	}

	private static final String[] _PORTAL_THEMES = {
		"_styled", "_unstyled", "admin", "classic"
	};

	private static final String[] _THEME_DIR_NAMES = {
		"css", "images", "js", "templates"
	};

	private Object _diffsDir;
	private FileCollection _frontendThemeFiles;
	private final Project _project;
	private Object _themeParent;
	private Project _themeParentProject;
	private Object _themeRootDir;
	private final Set<String> _themeTypes = new HashSet<>();

}