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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FileImpl;
import com.liferay.util.xml.DocUtil;
import com.liferay.util.xml.XMLFormatter;

import java.util.Arrays;

import org.apache.tools.ant.DirectoryScanner;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="ExtInfoBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExtInfoBuilder {

	public static void main(String[] args) throws Exception {
		if (args.length == 3) {
			new ExtInfoBuilder(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	@SuppressWarnings("deprecation")
	public ExtInfoBuilder(
			String basedir, String outputDir, String servletContextName)
		throws Exception {

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setExcludes(
			new String[] {
				"ext-impl/ext-impl.jar", "ext-impl/src/**",
				"ext-service/ext-service.jar", "ext-service/src/**",
				"ext-util-bridges/ext-util-bridges.jar",
				"ext-util-bridges/src/**",
				"ext-util-java/ext-util-java.jar",
				"ext-util-java/src/**",
				"ext-util-taglib/ext-util-taglib.jar",
				"ext-util-taglib/src/**",
				"liferay-plugin-package.properties"
			});

		ds.scan();

		String[] files = ds.getIncludedFiles();

		Arrays.sort(files);

		Element rootElement = DocumentHelper.createElement("ext-info");

		Document document = DocumentHelper.createDocument(rootElement);

		DocUtil.add(rootElement, "servlet-context-name", servletContextName);

		Element filesElement = rootElement.addElement("files");

		for (String file : files) {
			DocUtil.add(
				filesElement, "file",
				StringUtil.replace(
					file, StringPool.BACK_SLASH, StringPool.SLASH));
		}

		_fileUtil.write(
			outputDir + "/ext-" + servletContextName + ".xml",
			XMLFormatter.toString(document));
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

}