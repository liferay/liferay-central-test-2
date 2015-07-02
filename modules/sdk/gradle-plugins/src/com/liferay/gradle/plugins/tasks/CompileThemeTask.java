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
import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Andrea Di Giorgi
 */
public class CompileThemeTask extends DefaultTask {

	public CompileThemeTask() {
		_project = getProject();
	}

	@TaskAction
	public void compileTheme() {
		copyThemeParent();

		copyDiffs();
	}

	@InputDirectory
	@Optional
	public File getDiffsDir() {
		return _diffsDir;
	}

	@InputDirectory
	@Optional
	public File getFrontendThemesWebDir() {
		return _frontendThemesWebDir;
	}

	@InputFile
	@Optional
	public File getFrontendThemesWebFile() {
		return _frontendThemesWebFile;
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
		return _themeParent;
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
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			_project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

	@Input
	public String getThemeType() {
		return _themeType;
	}

	public void setDiffsDir(File diffsDir) {
		_diffsDir = diffsDir;
	}

	public void setFrontendThemesWebDir(File frontendThemesWebDir) {
		_frontendThemesWebDir = frontendThemesWebDir;
	}

	public void setFrontendThemesWebFile(File frontendThemesWebFile) {
		_frontendThemesWebFile = frontendThemesWebFile;
	}

	public void setThemeParent(String themeParent) {
		_themeParent = themeParent;
		_themeParentProject = null;
	}

	public void setThemeType(String themeType) {
		_themeType = themeType;
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
		String theme, final String[] excludes, final String include) {

		final String prefix = "html/themes/" + theme + "/";

		final File frontendThemesWebDir = getFrontendThemesWebDir();
		final File frontendThemesWebFile = getFrontendThemesWebFile();
		final File themeRootDir = getThemeRootDir();

		if (frontendThemesWebDir != null) {
			Closure<Void> closure = new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.from(new File(frontendThemesWebDir, prefix));

					if (ArrayUtil.isNotEmpty(excludes)) {
						copySpec.exclude(excludes);
					}

					copySpec.include(include);
					copySpec.into(themeRootDir);
				}

			};

			_project.copy(closure);
		}
		else if (frontendThemesWebFile != null) {
			String jarPrefix = "META-INF/resources/" + prefix;

			final String[] prefixedExcludes = StringUtil.prepend(
				excludes, jarPrefix);
			final String prefixedInclude = jarPrefix + include;

			Closure<Void> closure = new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					copySpec.eachFile(new StripPathSegmentsAction(5));

					if (ArrayUtil.isNotEmpty(prefixedExcludes)) {
						copySpec.exclude(prefixedExcludes);
					}

					copySpec.from(_project.zipTree(frontendThemesWebFile));
					copySpec.include(prefixedInclude);
					copySpec.into(themeRootDir);
					copySpec.setIncludeEmptyDirs(false);
				}

			};

			_project.copy(closure);
		}
		else {
			throw new GradleException("Unable to find frontend themes web");
		}
	}

	protected void copyThemeParent() {
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
		else if (themeParent.equals("classic") ||
				 themeParent.equals("control_panel")) {

			copyThemeParentPortal();
		}
		else if (!themeParent.equals("_unstyled")) {
			copyThemeParentProject();
		}
	}

	protected void copyThemeParentPortal() {
		String themeParent = getThemeParent();
		String themeType = getThemeType();

		copyPortalThemeDir(
			themeParent, new String[] {
				"**/.sass-cache/**", "_diffs/**", "templates/**"
			},
			"**");

		copyPortalThemeDir(themeParent, null, "templates/*." + themeType);
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

	protected void copyThemeParentStyled() {
		copyPortalThemeDir("_styled", null, "**");
	}

	protected void copyThemeParentUnstyled() {
		String themeType = getThemeType();

		copyPortalThemeDir("_unstyled", new String[] {"templates/**"}, "**");

		copyPortalThemeDir(
			"_unstyled", new String[] {"templates/init." + themeType},
			"templates/**/*." + themeType);
	}

	private static final String[] _PORTAL_THEMES = {
		"_styled", "_unstyled", "classic", "control_panel"
	};

	private static final String[] _THEME_DIR_NAMES = {
		"css", "images", "js", "templates"
	};

	private File _diffsDir;
	private File _frontendThemesWebDir;
	private File _frontendThemesWebFile;
	private final Project _project;
	private String _themeParent;
	private Project _themeParentProject;
	private String _themeType;

}