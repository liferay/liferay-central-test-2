/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 * @author Zsigmond Rab
 */
public class EARBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 2) {
			new EARBuilder(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public EARBuilder(String originalApplicationXML,
		String pluginFileNamesToMerge) {

		try {
			Document document =
				SAXReaderUtil.read(new File(originalApplicationXML));

			Element rootElement = document.getRootElement();

			String[] pluginFileNames =
				pluginFileNamesToMerge.split(StringPool.COMMA);

			for (int i = 0; i < pluginFileNames.length; i++) {
				String pluginFileName = pluginFileNames[i];

				Element module = rootElement.addElement("module");

				Element web = module.addElement("web");

				Element webURI = web.addElement("web-uri");

				webURI.addText(pluginFileName);

				Element contextRoot = web.addElement("context-root");

				contextRoot.addText(_contextRoot(pluginFileName));
			}

			FileUtil.write(
				originalApplicationXML, document.formattedString(), true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String _contextRoot(String pluginFileName) {
		String contextRoot = pluginFileName;

		int warIndex = contextRoot.lastIndexOf(".war");

		if (warIndex != -1) {
			contextRoot = contextRoot.substring(0, warIndex);
		}

		if (contextRoot.equals("liferay-portal")) {
			contextRoot = PropsUtil.get(PropsKeys.PORTAL_CTX);

			if (contextRoot.equals(StringPool.SLASH)) {
				contextRoot = "";
			}
			else if (contextRoot.startsWith(StringPool.SLASH)) {
				contextRoot = contextRoot.substring(1);
			}
		}

		return StringPool.SLASH + contextRoot;
	}

}