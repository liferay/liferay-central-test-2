/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.oro.io.GlobFilenameFilter;
import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="PluginsEnvironmentBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class PluginsEnvironmentBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		File dir = new File(System.getProperty("plugins.env.dir"));
		boolean svn = GetterUtil.getBoolean(
			System.getProperty("plugins.env.svn"));
		boolean eclipse = GetterUtil.getBoolean(
			System.getProperty("plugins.env.eclipse"));

		new PluginsEnvironmentBuilder(dir, svn, eclipse);
	}

	public PluginsEnvironmentBuilder(File dir, boolean svn, boolean eclipse) {
		try {
			_svn = svn;

			DirectoryScanner ds = new DirectoryScanner();

			ds.setBasedir(dir);
			ds.setIncludes(
				new String[] {
					"**\\liferay-plugin-package.properties",
				});

			ds.scan();

			String dirName = dir.getCanonicalPath();

			String[] fileNames = ds.getIncludedFiles();

			for (String fileName : fileNames) {
				File propertiesFile = new File(dirName + "/" + fileName);

				Properties properties = new Properties();

				properties.load(new FileInputStream(propertiesFile));

				String[] dependencyJars = StringUtil.split(
					properties.getProperty(
						"portal-dependency-jars",
						properties.getProperty("portal.dependency.jars")));

				if (svn) {
					updateClientIgnores(propertiesFile);
					updateLibIgnores(propertiesFile, dependencyJars);
				}

				if (eclipse) {
					updateEclipseFiles(propertiesFile, dependencyJars);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateClientIgnores(File propertiesFile) throws Exception {
		File clientDir = new File(propertiesFile.getParent() + "/client");

		if (!clientDir.exists()) {
			return;
		}

		if (!_isSVNDir(clientDir)) {
			_exec(_SVN_ADD + "--non-recursive " + "\"" + clientDir + "\"");
		}

		File tempFile = File.createTempFile("svn-ignores-", null, null);

		try {
			FileUtil.write(tempFile, "classes\nsrc");

			_exec(
				_SVN_SET_IGNORES + "-F \"" + tempFile.getCanonicalPath() +
					"\" \"" + clientDir + "\"");
		}
		finally {
			FileUtil.delete(tempFile);
		}
	}

	public void updateEclipseFiles(File propertiesFile, String[] dependencyJars)
		throws Exception {

		File projectDir = new File(propertiesFile.getParent() + "/../..");
		File libDir = new File(propertiesFile.getParent() + "/lib");

		String projectDirName = projectDir.getCanonicalPath();
		String projectName = StringUtil.extractLast(
			projectDirName, File.separator);
		String libDirPath = StringUtil.replace(
			libDir.getPath(), StringPool.BACK_SLASH, StringPool.SLASH);

		// .project

		StringBundler sb = new StringBundler(17);

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<projectDescription>\n");
		sb.append("\t<name>");
		sb.append(projectName);
		sb.append("</name>\n");
		sb.append("\t<comment></comment>\n");
		sb.append("\t<projects></projects>\n");
		sb.append("\t<buildSpec>\n");
		sb.append("\t\t<buildCommand>\n");
		sb.append("\t\t\t<name>org.eclipse.jdt.core.javabuilder</name>\n");
		sb.append("\t\t\t<arguments></arguments>\n");
		sb.append("\t\t</buildCommand>\n");
		sb.append("\t</buildSpec>\n");
		sb.append("\t<natures>\n");
		sb.append("\t\t<nature>org.eclipse.jdt.core.javanature</nature>\n");
		sb.append("\t</natures>\n");
		sb.append("</projectDescription>");

		File projectFile = new File(projectDirName + "/.project");

		System.out.println("Updating " + projectFile);

		FileUtil.write(projectFile, sb.toString());

		// .classpath

		List<String> developmentJars = new ArrayList<String>();

		developmentJars.add("activation.jar");
		developmentJars.add("annotations.jar");
		developmentJars.add("jsp-api.jar");
		developmentJars.add("mail.jar");
		developmentJars.add("servlet-api.jar");

		developmentJars = ListUtil.sort(developmentJars);

		List<String> portalJars = ListUtil.toList(dependencyJars);

		portalJars.add("commons-logging.jar");
		portalJars.add("log4j.jar");

		portalJars = ListUtil.sort(portalJars);

		String[] customJarsArray = libDir.list(new GlobFilenameFilter("*.jar"));

		List<String> customJars = null;

		if (customJarsArray != null) {
			customJars = ListUtil.toList(customJarsArray);
			customJars = ListUtil.sort(customJars);

			for (String jar : portalJars) {
				customJars.remove(jar);
			}

			customJars.remove(projectName + "-service.jar");
			customJars.remove("util-bridges.jar");
			customJars.remove("util-java.jar");
			customJars.remove("util-taglib.jar");
		}
		else {
			customJars = new ArrayList<String>();
		}

		sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<classpath>\n");

		if (FileUtil.exists(projectDirName + "/docroot/WEB-INF/service")) {
			sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
			sb.append("kind=\"src\" path=\"docroot/WEB-INF/service\" />\n");
		}

		sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
		sb.append("kind=\"src\" path=\"docroot/WEB-INF/src\" />\n");

		if (FileUtil.exists(projectDirName + "/docroot/WEB-INF/test")) {
			sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
			sb.append("kind=\"src\" path=\"docroot/WEB-INF/test\" />\n");
		}

		sb.append("\t<classpathentry kind=\"src\" path=\"/portal\" />\n");
		sb.append("\t<classpathentry kind=\"con\" ");
		sb.append("path=\"org.eclipse.jdt.launching.JRE_CONTAINER\" />\n");

		for (String jar : developmentJars) {
			_addClasspathEntry(sb, "/portal/lib/development/" + jar);
		}

		_addClasspathEntry(sb, "/portal/lib/global/portlet.jar");

		for (String jar : portalJars) {
			_addClasspathEntry(sb, "/portal/lib/portal/" + jar);
		}

		_addClasspathEntry(sb, "/portal/portal-service/portal-service.jar");
		_addClasspathEntry(sb, "/portal/util-bridges/util-bridges.jar");
		_addClasspathEntry(sb, "/portal/util-java/util-java.jar");
		_addClasspathEntry(sb, "/portal/util-taglib/util-taglib.jar");

		for (String jar : customJars) {
			if (libDirPath.contains("/tmp/WEB-INF/lib")) {
				_addClasspathEntry(sb, "tmp/WEB-INF/lib/" + jar);
			}
			else {
				_addClasspathEntry(sb, "docroot/WEB-INF/lib/" + jar);
			}
		}

		sb.append("\t<classpathentry kind=\"output\" path=\"bin\" />\n");
		sb.append("</classpath>");

		File classpathFile = new File(projectDirName + "/.classpath");

		System.out.println("Updating " + classpathFile);

		FileUtil.write(classpathFile, sb.toString());

		// SVN

		if (_svn) {
			String projectFileName = "\"" + projectFile + "\"";

			try {
				_exec(_SVN_INFO + projectFileName);
			}
			catch (Exception e) {
				_exec(_SVN_ADD + projectFileName);
			}

			String classpathFileName = "\"" + classpathFile + "\"";

			try {
				_exec(_SVN_INFO + classpathFileName);
			}
			catch (Exception e) {
				_exec(_SVN_ADD + classpathFileName);
			}

			File tempFile = File.createTempFile("svn-ignores-", null, null);

			try {
				FileUtil.write(tempFile, "bin\ntmp");

				_exec(
					_SVN_SET_IGNORES + "-F \"" + tempFile.getCanonicalPath() +
						"\" \"" + projectDirName + "\"");
			}
			finally {
				FileUtil.delete(tempFile);
			}
		}
	}

	public void updateLibIgnores(File propertiesFile, String[] dependencyJars)
		throws Exception {

		File libDir = new File(propertiesFile.getParent() + "/lib");

		if (!_isSVNDir(libDir)) {
			return;
		}

		File tempFile = File.createTempFile("svn-ignores-", null, null);

		try {
			List<String> jars = ListUtil.toList(dependencyJars);

			jars.add("commons-logging.jar");
			jars.add("log4j.jar");
			jars.add("util-bridges.jar");
			jars.add("util-java.jar");
			jars.add("util-taglib.jar");

			ListUtil.distinct(jars, new StringComparator(true, false));

			FileUtil.write(tempFile, StringUtil.merge(jars, "\n"));

			_exec(_SVN_DEL_IGNORES + "\"" + libDir.getCanonicalPath() + "\"");
			_exec(
				_SVN_SET_IGNORES + "-F \"" + tempFile.getCanonicalPath() +
					"\" \"" + libDir.getCanonicalPath() + "\"");
		}
		finally {
			FileUtil.delete(tempFile);
		}
	}

	private void _addClasspathEntry(StringBundler sb, String jar)
		throws Exception {

		sb.append("\t<classpathentry kind=\"lib\" path=\"");
		sb.append(jar);
		sb.append("\" />\n");
	}

	private String[] _exec(String cmd) throws Exception {
		Process process = Runtime.getRuntime().exec(cmd);

		String[] stdout = _getExecOutput(process.getInputStream());
		String[] stderr = _getExecOutput(process.getErrorStream());

		if (stderr.length > 0) {
			StringBundler sb = new StringBundler(stderr.length * 3 + 3);

			sb.append("Received errors in executing '");
			sb.append(cmd);
			sb.append("'\n");

			for (String err : stderr) {
				sb.append("\t");
				sb.append(err);
				sb.append("\n");
			}

			throw new Exception(sb.toString());
		}

		return stdout;
	}

	private String[] _getExecOutput(InputStream is) throws IOException {
		List<String> list = new ArrayList<String>();

		UnsyncBufferedReader unsyncBufferedReader = null;

		try {
			unsyncBufferedReader = new UnsyncBufferedReader(
				new InputStreamReader(is));

			String line = unsyncBufferedReader.readLine();

			while (line != null) {
				line = line.trim();

				if (Validator.isNotNull(line)) {
					list.add(line);
				}

				line = unsyncBufferedReader.readLine();
			}
		}
		finally {
			if (unsyncBufferedReader != null) {
				try {
					unsyncBufferedReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		return list.toArray(new String[] {});
	}

	private boolean _isSVNDir(File libDir) {
		if (!libDir.exists()) {
			return false;
		}

		try {
			_exec(_SVN_INFO + "\"" + libDir + "\"");
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	private static final String _SVN_ADD = "svn add ";

	private static final String _SVN_DEL_IGNORES = "svn propdel svn:ignore ";

	private static final String _SVN_INFO = "svn info ";

	private static final String _SVN_SET_IGNORES = "svn propset svn:ignore ";

	private boolean _svn;

}