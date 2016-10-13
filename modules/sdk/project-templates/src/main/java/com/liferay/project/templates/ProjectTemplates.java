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

package com.liferay.project.templates;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.project.templates.internal.Archetyper;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.StringUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.internal.util.WorkspaceUtil;

import java.io.File;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.maven.archetype.ArchetypeGenerationResult;

/**
 * @author Andrea Di Giorgi
 */
public class ProjectTemplates {

	public static final String TEMPLATE_BUNDLE_PREFIX =
		"com.liferay.project.templates.";

	public static String[] getTemplates() throws Exception {
		List<String> templates = new ArrayList<>();

		File file = FileUtil.getJarFile();

		if (file.isDirectory()) {
			try (DirectoryStream<Path> directoryStream =
					Files.newDirectoryStream(
						file.toPath(), TEMPLATE_BUNDLE_PREFIX + "*")) {

				Iterator<Path> iterator = directoryStream.iterator();

				while (iterator.hasNext()) {
					Path templateBundleFile = iterator.next();

					Path templateBundleFileNamePath =
						templateBundleFile.getFileName();

					String template = templateBundleFileNamePath.toString();

					template = template.substring(
						TEMPLATE_BUNDLE_PREFIX.length(),
						template.lastIndexOf('-'));

					template = template.replace('.', '-');

					if (!template.startsWith(WorkspaceUtil.WORKSPACE)) {
						templates.add(template);
					}
				}
			}
		}
		else {
			try (JarFile jarFile = new JarFile(file)) {
				Enumeration<JarEntry> enumeration = jarFile.entries();

				while (enumeration.hasMoreElements()) {
					JarEntry jarEntry = enumeration.nextElement();

					if (jarEntry.isDirectory()) {
						continue;
					}

					String template = jarEntry.getName();

					if (template.startsWith(TEMPLATE_BUNDLE_PREFIX)) {
						template = template.substring(
							TEMPLATE_BUNDLE_PREFIX.length(),
							template.indexOf("-"));

						template = template.replace('.', '-');

						if (!template.startsWith(WorkspaceUtil.WORKSPACE)) {
							templates.add(template);
						}
					}
				}
			}
		}

		Collections.sort(templates);

		return templates.toArray(new String[templates.size()]);
	}

	public static void main(String[] args) throws Exception {
		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		JCommander jCommander = new JCommander(projectTemplatesArgs);

		try {
			File jarFile = FileUtil.getJarFile();

			if (jarFile.isFile()) {
				jCommander.setProgramName("java -jar " + jarFile.getName());
			}
			else {
				jCommander.setProgramName(ProjectTemplates.class.getName());
			}

			jCommander.parse(args);

			if (projectTemplatesArgs.isHelp()) {
				_printHelp(jCommander);
			}
			else if (projectTemplatesArgs.isList()) {
				_printList();
			}
			else {
				new ProjectTemplates(projectTemplatesArgs);
			}
		}
		catch (ParameterException pe) {
			System.err.println(pe.getMessage());

			_printHelp(jCommander);
		}
	}

	public ProjectTemplates(ProjectTemplatesArgs projectTemplatesArgs)
		throws Exception {

		_checkArgs(projectTemplatesArgs);

		File destinationDir = projectTemplatesArgs.getDestinationDir();

		Archetyper archetyper = new Archetyper();

		ArchetypeGenerationResult archetypeGenerationResult =
			archetyper.generateProject(projectTemplatesArgs, destinationDir);

		if (archetypeGenerationResult != null) {
			Exception cause = archetypeGenerationResult.getCause();

			if (cause != null) {
				throw cause;
			}
		}

		Path templateDirPath = destinationDir.toPath();

		templateDirPath = templateDirPath.resolve(
			projectTemplatesArgs.getName());

		FileUtil.extractDirectory("gradle-wrapper", templateDirPath);

		try {
			Files.setPosixFilePermissions(
				templateDirPath.resolve("gradlew"),
				PosixFilePermissions.fromString("rwxrwxr--"));
		}
		catch (UnsupportedOperationException uoe) {
		}

		if (WorkspaceUtil.isWorkspace(destinationDir)) {
			FileUtil.deleteDir(templateDirPath.resolve("gradle"));
			Files.delete(templateDirPath.resolve("gradlew"));
			Files.delete(templateDirPath.resolve("gradlew.bat"));
			Files.deleteIfExists(templateDirPath.resolve("settings.gradle"));
		}

		Files.delete(templateDirPath.resolve("pom.xml"));
	}

	private static void _printHelp(JCommander jCommander) throws Exception {
		System.out.println();

		System.out.println(
			"Create a new Liferay module project from several available " +
				"templates:");

		String[] templates = getTemplates();

		int lineLength = 0;

		for (int i = 0; i < templates.length; i++) {
			String template = templates[i];

			if ((lineLength + template.length() + 1) >
					jCommander.getColumnSize()) {

				System.out.println();

				lineLength = 0;
			}

			System.out.print(template);

			lineLength += template.length();

			if (i < (templates.length - 1)) {
				System.out.print(", ");

				lineLength += 2;
			}
		}

		System.out.println();
		System.out.println();

		jCommander.usage();
	}

	private static void _printList() throws Exception {
		for (String template : getTemplates()) {
			System.out.println(template);
		}
	}

	private void _checkArgs(ProjectTemplatesArgs projectTemplatesArgs) {
		String template = projectTemplatesArgs.getTemplate();

		if (Validator.isNull(projectTemplatesArgs.getTemplate())) {
			throw new IllegalArgumentException("Template is required");
		}

		String name = projectTemplatesArgs.getName();

		if ((Validator.isNull(name)) && !("workspace".equals(template))) {
			throw new IllegalArgumentException("Name is required");
		}

		File destinationDir = projectTemplatesArgs.getDestinationDir();

		if (destinationDir == null) {
			throw new IllegalArgumentException("Destination dir is required");
		}

		if (destinationDir.exists()) {
			String[] fileNames = destinationDir.list();

			if ((fileNames == null) || (fileNames.length > 0)) {
				throw new IllegalArgumentException(
					destinationDir + " is not empty or it is a file");
			}
		}

		String className = projectTemplatesArgs.getClassName();

		if ((Validator.isNull(className)) && Validator.isNotNull(name)) {
			className = _getClassName(name);
		}

		if (template.equals("activator") && !className.endsWith("Activator")) {
			className += "Activator";
		}
		else if ((template.equals("mvc-portlet") ||
				  template.equals("portlet")) &&
				 className.endsWith("Portlet")) {

			className = className.substring(0, className.length() - 7);
		}

		projectTemplatesArgs.setClassName(className);

		if (Validator.isNull(projectTemplatesArgs.getPackageName()) &&
				Validator.isNotNull(name)) {
			projectTemplatesArgs.setPackageName(_getPackageName(name));
		}
	}

	private String _getCapitalizedName(String name) {
		name = name.replace('-', ' ');
		name = name.replace('.', ' ');

		return StringUtil.capitalize(name, ' ');
	}

	private String _getClassName(String name) {
		name = _getCapitalizedName(name);

		return StringUtil.removeChar(name, ' ');
	}

	private String _getPackageName(String name) {
		name = name.replace('-', '.');
		name = name.replace(' ', '.');

		return name.toLowerCase();
	}

}