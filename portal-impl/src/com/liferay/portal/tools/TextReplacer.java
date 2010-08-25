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

import com.liferay.portal.kernel.util.StringUtil;
import org.apache.tools.ant.DirectoryScanner;
import com.liferay.portal.util.FileImpl;

import java.io.FileOutputStream;
import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Brian Wing Shun Chan
 */
public class TextReplacer {

	public static void main(String[] args) {
		String dir = System.getProperty("text.replacer.dir");
		String includes = System.getProperty("text.replacer.includes");
		String excludes = System.getProperty("text.replacer.excludes");
		String tokenFile = System.getProperty("text.replacer.token.file");
		String valueFile = System.getProperty("text.replacer.value.file");

		new TextReplacer(dir, includes, excludes, tokenFile, valueFile);
	}

	public TextReplacer(
		String dir, String includes, String excludes, String tokenFile,
		String valueFile) {

		try {
			if (!dir.endsWith("/")) {
				dir += "/";
			}

			String token = _fileUtil.read(tokenFile);
			String value = _fileUtil.read(valueFile);

			DirectoryScanner directoryScanner = new DirectoryScanner();

			directoryScanner.setBasedir(dir);
			directoryScanner.setIncludes(StringUtil.split(includes));
			directoryScanner.setExcludes(StringUtil.split(excludes));

			directoryScanner.scan();

			for (String fileName : directoryScanner.getIncludedFiles()) {
				File file = new File(dir + fileName);

				String content = _fileUtil.read(file);

				String newContent = StringUtil.replace(content, token, value);

				if (!content.equals(newContent)) {
					_fileUtil.write(file, newContent);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

}