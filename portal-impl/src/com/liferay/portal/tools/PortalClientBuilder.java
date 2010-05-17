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

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.ant.Wsdl2JavaTask;

import java.io.File;

import java.util.Iterator;

/**
 * <a href="PortalClientBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortalClientBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 4) {
			new PortalClientBuilder(args[0], args[1], args[2], args[3]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public PortalClientBuilder(
		String fileName, String outputDir, String mappingFile, String url) {

		try {
			Document doc = SAXReaderUtil.read(new File(fileName));

			Element root = doc.getRootElement();

			Iterator<Element> itr = root.elements("service").iterator();

			while (itr.hasNext()) {
				Element service = itr.next();

				String name = service.attributeValue("name");

				if (name.startsWith("Portal_") || name.startsWith("Portlet_")) {
					Wsdl2JavaTask.generateJava(
						url + "/" +  name + "?wsdl", outputDir, mappingFile);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		File testNamespace = new File(outputDir + "/com/liferay/portal");

		if (testNamespace.exists()) {
			throw new RuntimeException(
				"Please update " + mappingFile + " to namespace " +
					"com.liferay.portal to com.liferay.client.soap.portal");
		}
	}

}