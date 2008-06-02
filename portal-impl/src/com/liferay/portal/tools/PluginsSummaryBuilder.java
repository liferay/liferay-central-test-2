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
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="PluginsSummaryBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PluginsSummaryBuilder {

	public static void main(String[] args) {
		File pluginsDir = new File(System.getProperty("plugins.dir"));

		new PluginsSummaryBuilder(pluginsDir);
	}

	public PluginsSummaryBuilder(File pluginsDir) {
		try {
			_createPluginsSummary(pluginsDir);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void _createPluginsSummary(File pluginsDir) throws Exception {
		StringMaker sm = new StringMaker();

		sm.append("<plugins-summary>\n");

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(pluginsDir);
		ds.setIncludes(new String[] {"**\\liferay-plugin-package.properties"});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		Arrays.sort(files);

		for (String file : files) {
			int x = file.indexOf(File.separator);

			String type = file.substring(0, x);

			if (type.endsWith("s")) {
				type = type.substring(0, type.length() - 1);
			}

			x = file.indexOf(File.separator, x) + 1;
			int y = file.indexOf(File.separator, x);

			String artifactId = file.substring(x, y);

			Properties props = PropertiesUtil.load(_fileUtil.read(file));

			String name = _getValue(props, "name");
			String tags = _getValue(props, "tags");
			String shortDescription = _getValue(props, "short-description");
			String changeLog = _getValue(props, "change-log");
			String pageURL = _getValue(props, "page-url");
			String author = _getValue(props, "author");
			String licenses = _getValue(props, "licenses");

			_distinctAuthors.add(author);
			_distinctLicenses.add(licenses);

			sm.append("\t<plugin>\n");
			sm.append("\t\t<artifact-id>");
			sm.append(artifactId);
			sm.append("</artifact-id>\n");
			sm.append("\t\t<name>");
			sm.append(name);
			sm.append("</name>\n");
			sm.append("\t\t<type>");
			sm.append(type);
			sm.append("</type>\n");
			sm.append("\t\t<tags>");
			sm.append(tags);
			sm.append("</tags>\n");
			sm.append("\t\t<short-description>");
			sm.append(shortDescription);
			sm.append("</short-description>\n");
			sm.append("\t\t<change-log>");
			sm.append(changeLog);
			sm.append("</change-log>\n");
			sm.append("\t\t<page-url>");
			sm.append(pageURL);
			sm.append("</page-url>\n");
			sm.append("\t\t<author>");
			sm.append(author);
			sm.append("</author>\n");
			sm.append("\t\t<licenses>");
			sm.append(licenses);
			sm.append("</licenses>\n");
			sm.append("\t</plugin>\n");
		}

		for (String author : _distinctAuthors) {
			sm.append("\t<author>");
			sm.append(author);
			sm.append("</author>\n");
		}

		for (String license : _distinctLicenses) {
			sm.append("\t<license>");
			sm.append(license);
			sm.append("</license>\n");
		}

		sm.append("</plugins-summary>");

		_fileUtil.write(
			pluginsDir + File.separator + "summary.xml", sm.toString());
	}

	public String _getValue(Properties props, String key) {
		return GetterUtil.getString(props.getProperty(key));
	}

	private static FileImpl _fileUtil = new FileImpl();

	private Set<String> _distinctAuthors = new TreeSet<String>();
	private Set<String> _distinctLicenses = new TreeSet<String>();

}