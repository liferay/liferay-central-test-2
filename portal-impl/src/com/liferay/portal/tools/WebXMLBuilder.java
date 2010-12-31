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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.servlet.filters.absoluteredirects.AbsoluteRedirectsFilter;
import com.liferay.portal.util.HtmlImpl;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.xml.DocumentImpl;
import com.liferay.util.xml.XMLMerger;
import com.liferay.util.xml.descriptor.WebXML23Descriptor;
import com.liferay.util.xml.descriptor.WebXML24Descriptor;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 * @author Tang Ying Jian
 * @author Brian Myunghun Kim
 * @author Minhchau Dang
 */
public class WebXMLBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 3) {
			new WebXMLBuilder(args[0], args[1], args[2]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static String organizeWebXML(String webXML)
		throws DocumentException, IOException {

		Html html = new HtmlImpl();

		webXML = html.stripComments(webXML);

		Document document = SAXReaderUtil.read(webXML);

		Element rootElement = document.getRootElement();

		double version = 2.3;

		version = GetterUtil.getDouble(
			rootElement.attributeValue("version"), version);

		XMLMerger xmlMerger = null;

		if (version == 2.3) {
			xmlMerger = new XMLMerger(new WebXML23Descriptor());
		}
		else {
			xmlMerger = new XMLMerger(new WebXML24Descriptor());
		}

		DocumentImpl documentImpl = (DocumentImpl)document;

		xmlMerger.organizeXML(documentImpl.getWrappedDocument());

		webXML = document.formattedString();

		return webXML;
	}

	public WebXMLBuilder(
		String originalWebXML, String customWebXML, String mergedWebXML) {

		try {
			String customContent = FileUtil.read(customWebXML);

			int x = customContent.indexOf("<web-app");

			x = customContent.indexOf(">", x) + 1;

			int y = customContent.indexOf("</web-app>");

			customContent = customContent.substring(x, y);

			String originalContent = FileUtil.read(originalWebXML);

			int z = getInsertionIndex(originalContent);

			String mergedContent =
				originalContent.substring(0, z) + customContent +
					originalContent.substring(z, originalContent.length());

			mergedContent = organizeWebXML(mergedContent);

			FileUtil.write(mergedWebXML, mergedContent, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected int getInsertionIndex(String content) {
		int x = content.indexOf(AbsoluteRedirectsFilter.class.getName());

		if (x == -1) {
			x = content.indexOf("<web-app");
			x = content.indexOf(">", x) + 1;

			return x;
		}
		else {
			x = content.lastIndexOf("<filter-name", x);
			x = content.indexOf(">", x) + 1;

			int y = content.indexOf("</filter-name>", x);

			String filterName = content.substring(x, y);

			x = content.lastIndexOf(filterName);

			y = content.indexOf("</filter-mapping>", x);
			y = content.indexOf(">", y) + 1;

			return y;
		}
	}

}