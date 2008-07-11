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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.Arrays;
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

	public static void main(String[] args) {
		String dir = null;

		if (args.length > 0) {
			dir = args[0];
		}
		else {
			dir = System.getProperty("plugins.dir");
		}

		if (Validator.isNull(dir)) {
			System.err.println("Please enter a valid plugins directory.");

			return;
		}

		new PluginsEnvironmentBuilder(new File(dir));
	}

	public PluginsEnvironmentBuilder(File dir) {
		System.out.println("Starting " + getClass().getName() + " for " + dir);

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(dir);
		ds.setIncludes(
			new String[] {
				"**\\liferay-plugin-package.properties",
			});

		ds.scan();

		String path = dir.getAbsolutePath();

		String[] fileNames = ds.getIncludedFiles();

		for (String fileName : fileNames) {
			fixSVNIgnores(new File(path + "/" + fileName));
		}

		System.out.println("Done.");
	}

	public void fixSVNIgnores(File propsFile) {
		File tempFile = null;

		try {
			Properties props = new Properties();

			props.load(new FileInputStream(propsFile));

			String[] dependencyJars = StringUtil.split(
				props.getProperty("portal.dependency.jars"));

			Arrays.sort(dependencyJars);

			File libDir = new File(propsFile.getParent() + "/lib");

			String libPath = libDir.getAbsolutePath();

			if (_isSourceControlled(libDir)) {
				String[] oldIgnores = _execSVNCommand(
					_SVN_GET_IGNORES + libPath);

				Arrays.sort(oldIgnores);

				if (Arrays.equals(oldIgnores, dependencyJars)) {
					System.out.println("SVN ignores are valid for " + libPath);

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
					_SVN_SET_IGNORES + tempFile.getAbsolutePath() + " " +
					libPath);

				String[] newIgnores = _execSVNCommand(
					_SVN_GET_IGNORES + libPath);

				if (newIgnores.length > 0) {
					System.out.println("SVN ignores set for " + libPath);

					Arrays.sort(newIgnores);

					System.out.println(
						"\tOld ignores: " +StringUtil.merge(oldIgnores));
					System.out.println(
						"\tNew ignores: " + StringUtil.merge(newIgnores));

					System.out.println("\tPlease manually commit " + libPath);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
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

	private void _writeToFile(File file, String string) throws Exception {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, false)));

			bw.write(string);
		}
		finally {
			bw.close();
		}
	}

	private static final String _SVN_GET_IGNORES = "svn propget svn:ignore ";

	private static final String _SVN_DEL_IGNORES = "svn propdel svn:ignore ";

	private static final String _SVN_SET_IGNORES = "svn propset svn:ignore -F ";

	private static final String _SVN_INFO = "svn info ";

}