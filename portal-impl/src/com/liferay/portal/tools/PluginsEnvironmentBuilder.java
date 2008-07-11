/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="PluginsEnvironmentBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class PluginsEnvironmentBuilder {

	public static void main(String[] args) throws Exception {
		String dir = null;
		boolean svn = false;

		if (args.length > 0) {
			dir = args[0];

			if (args.length == 2) {
				svn = GetterUtil.getBoolean(args[1]);
			}
		}

		if (Validator.isNull(dir)) {
			System.err.println("Please enter a valid plugins directory.");

			return;
		}

		new PluginsEnvironmentBuilder(new File(dir), svn);
	}

	public PluginsEnvironmentBuilder(File dir, boolean svn) throws Exception {
		System.out.println("Starting " + getClass().getName() + " for " + dir);

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(dir);
		ds.setIncludes(
			new String[] {
				"**\\liferay-plugin-package.properties",
			});

		ds.scan();

		String path = dir.getCanonicalPath();

		String[] fileNames = ds.getIncludedFiles();

		for (String fileName : fileNames) {
			try {
				File propsFile = new File(path + "/" + fileName);
				File libDir = new File(propsFile.getParent() + "/lib");
				File projDir = new File(propsFile.getParent() + "/../..");
				Properties props = new Properties();

				props.load(new FileInputStream(propsFile));

				String[] dependencyJars = StringUtil.split(
					props.getProperty("portal.dependency.jars"));

				Arrays.sort(dependencyJars);

				fixEclipseFiles(libDir, projDir, dependencyJars, svn);

				if (svn) {
					fixSVNIgnores(libDir, dependencyJars);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Done.");
	}

	public void fixEclipseFiles(
			File libDir, File projDir, String[] dependencyJars, boolean svn)
		throws Exception {

		String projPath = projDir.getCanonicalPath();
		String projName = StringUtil.extractLast(projPath, "/");

		// Generate .project

		StringBuilder project = new StringBuilder();

		project.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		project.append("<projectDescription>\n");
        project.append("\t<name>" + projName + "</name>\n");
        project.append("\t<comment></comment>\n");
        project.append("\t<projects></projects>\n");
        project.append("\t<buildSpec>\n");
        project.append("\t\t<buildCommand>\n");
        project.append("\t\t\t<name>org.eclipse.jdt.core.javabuilder</name>\n");
        project.append("\t\t\t<arguments></arguments>\n");
        project.append("\t\t</buildCommand>\n");
        project.append("\t</buildSpec>\n");
        project.append("\t<natures>\n");
        project.append("\t\t<nature>org.eclipse.jdt.core.javanature</nature>\n");
        project.append("\t</natures>\n");
		project.append("</projectDescription>");

		File dotProject = new File(projPath + "/.project");

		if(_rewriteToFile(dotProject, project.toString())) {
			System.out.println("- Created new " + dotProject);
		}
		else {
			System.out.println("- Up-to-date " + dotProject);
		}

		// Generate .classpath

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		};

		List<String> portalJars = ListUtil.toList(dependencyJars);

		portalJars.add("commons-logging.jar");
		portalJars.add("log4j.jar");

		ListUtil.distinct(portalJars);
		Collections.sort(portalJars);

		List<String> customJars = ListUtil.toList(libDir.list(filter));

		for (String jar : portalJars) {
			customJars.remove(jar);
		}

		Collections.sort(customJars);

		StringBuilder classpath = new StringBuilder();

		classpath.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        classpath.append("<classpath>\n");
        classpath.append("\t<classpathentry excluding=\"**/.svn/**|.svn/\" kind=\"src\" path=\"docroot/WEB-INF/src\"/>\n");
        classpath.append("\t<classpathentry kind=\"src\" path=\"/portal\"/>\n");
        classpath.append("\t<classpathentry kind=\"con\" path=\"org.eclipse.jdt.launching.JRE_CONTAINER\"/>\n");

    	classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/lib/development/mail.jar\"/>\n");
    	classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/lib/development/servlet.jar\"/>\n");
    	classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/lib/global/container.jar\"/>\n");
    	classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/lib/global/portlet-container.jar\"/>\n");
    	classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/lib/global/portlet.jar\"/>\n");

        for (String jar : portalJars) {
        	classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/lib/portal/" + jar + "\"/>\n");
        }

        classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/portal-kernel/portal-kernel.jar\"/>\n");
        classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/portal-service/portal-service.jar\"/>\n");
        classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/util-bridges/util-bridges.jar\"/>\n");
        classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/util-java/util-java.jar\"/>\n");
        classpath.append("\t<classpathentry kind=\"lib\" path=\"/portal/util-taglib/util-taglib.jar\"/>\n");

        for (String jar : customJars) {
        	classpath.append("\t<classpathentry kind=\"lib\" path=\"docroot/WEB-INF/lib/" + jar + "\"/>\n");
        }

        classpath.append("\t<classpathentry kind=\"output\" path=\"bin\"/>\n");
        classpath.append("</classpath>");

        File dotClasspath = new File(projPath + "/.classpath");

		if(_rewriteToFile(dotClasspath, classpath.toString())) {
			System.out.println("- Created new " + dotClasspath);
		}
		else {
			System.out.println("- Up-to-date " + dotClasspath);
		}

		// Add to SVN

		if (svn) {
			try {
				_execSVNCommand(_SVN_INFO + dotProject);
			}
			catch (Exception e) {
				_execSVNCommand(_SVN_ADD + dotProject);
			}

			try {
				_execSVNCommand(_SVN_INFO + dotClasspath);
			}
			catch (Exception e) {
				_execSVNCommand(_SVN_ADD + dotClasspath);
			}

			_execSVNCommand(_SVN_SET_IGNORES + "bin " + projPath);
		}
	}

	public void fixSVNIgnores(File libDir, String[] dependencyJars)
		throws Exception {

		File tempFile = null;

		try {
			String libPath = libDir.getCanonicalPath();

			if (_isSourceControlled(libDir)) {
				String[] oldIgnores = _execSVNCommand(
					_SVN_GET_IGNORES + libPath);

				Arrays.sort(oldIgnores);

				if (Arrays.equals(oldIgnores, dependencyJars)) {
					System.out.println("- SVN ignores are valid for " + libPath);

					return;
				}

				tempFile = File.createTempFile("svn-ignore-", null, null);

				_execSVNCommand(_SVN_DEL_IGNORES + libPath);

				StringBuilder sb = new StringBuilder();

				for (String jar : dependencyJars) {
					sb.append(jar + "\n");
				}

				_writeToFile(tempFile, sb.toString());

				_execSVNCommand(
					_SVN_SET_IGNORES + "-F " + tempFile.getCanonicalPath() + 
					" " + libPath);

				String[] newIgnores = _execSVNCommand(
					_SVN_GET_IGNORES + libPath);

				if (newIgnores.length > 0) {
					System.out.println("- SVN ignores set for " + libPath);

					Arrays.sort(newIgnores);

					System.out.println(
						"\tOld ignores: " +StringUtil.merge(oldIgnores));
					System.out.println(
						"\tNew ignores: " + StringUtil.merge(newIgnores));

					System.out.println("\tPlease manually commit " + libPath);
				}
			}
		}
		finally {
			if (tempFile != null) {
				tempFile.delete();
			}
		}
	}

	private String[] _execSVNCommand(String cmd) throws Exception {
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

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(is));

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

	private boolean _isSourceControlled(File libDir) {
		boolean controlled = true;

		if (!libDir.exists()) {
			controlled = false;
		}

		try {
			_execSVNCommand(_SVN_INFO + libDir);
		}
		catch (Exception e) {
			controlled = false;
		}

		return controlled;
	}

	private boolean _rewriteToFile(File file, String content) throws Exception {
		FileInputStream fis = null;
		String oldContent = "";
		boolean write = true;

		if (file.exists()) {
			try {
				fis = new FileInputStream(file);

				byte[] bytes = new byte[fis.available()];

				fis.read(bytes);

				oldContent = new String(bytes, StringPool.UTF8);
			}
			finally {
				if (fis != null) {
					fis.close();
				}
			}

			if (content.equals(oldContent)) {
				write = false;
			}
		}

		if (write) {
			_writeToFile(file, content);
		}

		return write;
	}

	private void _writeToFile(File file, String content) throws Exception {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, false)));

			bw.write(content);
		}
		finally {
			bw.close();
		}
	}

	private static final String _SVN_ADD = "svn add ";

	private static final String _SVN_GET_IGNORES = "svn propget svn:ignore ";

	private static final String _SVN_DEL_IGNORES = "svn propdel svn:ignore ";

	private static final String _SVN_SET_IGNORES = "svn propset svn:ignore ";

	private static final String _SVN_INFO = "svn info ";

}