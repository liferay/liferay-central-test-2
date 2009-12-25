/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
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
import java.util.Arrays;
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
				File libDir = new File(propertiesFile.getParent() + "/lib");
				File projectDir = new File(
					propertiesFile.getParent() + "/../..");

				Properties properties = new Properties();

				properties.load(new FileInputStream(propertiesFile));

				List<String> dependencyJars = ListUtil.toList(StringUtil.split(
					properties.getProperty(
						"portal-dependency-jars",
						properties.getProperty("portal.dependency.jars"))));

				if (svn) {
					List<String> jars = new ArrayList<String>(dependencyJars);

					jars.add("commons-logging.jar");
					jars.add("log4j.jar");
					jars.add("util-bridges.jar");
					jars.add("util-java.jar");
					jars.add("util-taglib.jar");

					jars = ListUtil.sort(jars);

					updateLibIgnores(
						libDir, jars.toArray(new String[jars.size()]));
				}

				if (eclipse) {
					updateEclipseFiles(libDir, projectDir, dependencyJars);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateEclipseFiles(
			File libDir, File projectDir, List<String> dependencyJars)
		throws Exception {

		String libDirPath = libDir.getPath();

		libDirPath = StringUtil.replace(
			libDirPath, StringPool.BACK_SLASH, StringPool.SLASH);

		String projectDirName = projectDir.getCanonicalPath();
		String projectName = StringUtil.extractLast(
			projectDirName, File.separator);

		// .project

		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<projectDescription>\n");
		sb.append("\t<name>" + projectName + "</name>\n");
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

		List<String> portalJars = new ArrayList<String>(dependencyJars);

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

		sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n");
		sb.append("<classpath>\n");

		if (FileUtil.exists(projectDirName + "/docroot/WEB-INF/service")) {
			sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
			sb.append("kind=\"src\" path=\"docroot/WEB-INF/service\" />\n");
		}

		sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
		sb.append("kind=\"src\" path=\"docroot/WEB-INF/src\" />\n");
		sb.append("\t<classpathentry kind=\"src\" path=\"/portal\" />\n");
		sb.append("\t<classpathentry kind=\"con\" ");
		sb.append("path=\"org.eclipse.jdt.launching.JRE_CONTAINER\" />\n");

		if (FileUtil.exists(projectDirName + "/docroot/WEB-INF/test")) {
			sb.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" ");
			sb.append("kind=\"src\" path=\"docroot/WEB-INF/test\" />\n");
		}

		_addClasspathEntry(sb, "/portal/lib/development/activation.jar");
		_addClasspathEntry(sb, "/portal/lib/development/jsp-api.jar");
		_addClasspathEntry(sb, "/portal/lib/development/mail.jar");
		_addClasspathEntry(sb, "/portal/lib/development/servlet-api.jar");
		_addClasspathEntry(sb, "/portal/lib/global/annotations.jar");
		_addClasspathEntry(sb, "/portal/lib/global/portlet.jar");

		for (String jar : portalJars) {
			_addClasspathEntry(sb, "/portal/lib/portal/" + jar);
		}

		_addClasspathEntry(sb, "/portal/portal-kernel/portal-kernel.jar");
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

	public void updateLibIgnores(File libDir, String[] jars) throws Exception {
		if (!_isSVNDir(libDir)) {
			return;
		}

		File tempFile = null;

		try {
			String libDirName = "\"" + libDir.getCanonicalPath() + "\"";

			String[] oldIgnores = _exec(_SVN_GET_IGNORES + libDirName);

			Arrays.sort(oldIgnores);

			if (Arrays.equals(oldIgnores, jars)) {
				return;
			}

			tempFile = File.createTempFile("svn-ignores-", null, null);

			_exec(_SVN_DEL_IGNORES + libDirName);

			StringBuilder sb = new StringBuilder();

			for (String jar : jars) {
				sb.append(jar + "\n");
			}

			FileUtil.write(tempFile, sb.toString());

			_exec(
				_SVN_SET_IGNORES + "-F \"" + tempFile.getCanonicalPath() +
					"\" \"" + libDirName + "\"");

			String[] newIgnores = _exec(
				_SVN_GET_IGNORES + "\"" + libDirName + "\"");

			if (newIgnores.length > 0) {
				Arrays.sort(newIgnores);
			}
		}
		finally {
			if (tempFile != null) {
				FileUtil.delete(tempFile);
			}
		}
	}

	private void _addClasspathEntry(StringBuilder sb, String jar)
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
			StringBuilder sb = new StringBuilder();

			sb.append("Received errors in executing '" + cmd + "'\n");

			for (String err : stderr) {
				sb.append("\t" + err + "\n");
			}

			throw new Exception(sb.toString());
		}

		return stdout;
	}

	private String[] _getExecOutput(InputStream is) throws IOException {
		List<String> list = new ArrayList<String>();

		UnsyncBufferedReader br = null;

		try {
			br = new UnsyncBufferedReader(new InputStreamReader(is));

			String line = br.readLine();

			while (line != null) {
				line = line.trim();

				if (Validator.isNotNull(line)) {
					list.add(line);
				}

				line = br.readLine();
			}
		}
		finally {
			if (br != null) {
				try {
					br.close();
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

	private static final String _SVN_GET_IGNORES = "svn propget svn:ignore ";

	private static final String _SVN_INFO = "svn info ";

	private static final String _SVN_SET_IGNORES = "svn propset svn:ignore ";

	private boolean _svn;

}