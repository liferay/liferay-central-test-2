/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.Html;
import com.liferay.util.ant.CopyTask;
import com.liferay.util.xml.XMLFormatter;
import com.liferay.util.xml.XMLMerger;
import com.liferay.util.xml.descriptor.WebXML23Descriptor;
import com.liferay.util.xml.descriptor.WebXML24Descriptor;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="WebXMLBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Tang Ying Jian
 * @author Brian Myunghun Kim
 *
 */
public class WebXMLBuilder {

	public static void main(String[] args) {
		new WebXMLBuilder();
	}

	public static String organizeWebXML(String webXML)
		throws DocumentException, IOException {

		webXML = Html.stripComments(webXML);

		double version = 2.3;

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new StringReader(webXML));

		Element root = doc.getRootElement();

		version = GetterUtil.getDouble(root.attributeValue("version"), version);

		XMLMerger merger = null;

		if (version == 2.3) {
			merger = new XMLMerger(new WebXML23Descriptor());
		}
		else {
			merger = new XMLMerger(new WebXML24Descriptor());
		}

		merger.organizeXML(doc);

		webXML = XMLFormatter.toString(doc);

		return webXML;
	}

	public WebXMLBuilder() {
		try {
			String rootDir = "../";
			String portalWebDir = "portal-web/docroot/";
			String webSitesDir = "web-sites/";

			if (!FileUtil.exists(
					rootDir + portalWebDir + "WEB-INF/web.xml") &&
				!FileUtil.exists(rootDir + "ext-web/tmp/WEB-INF/web.xml")) {

				rootDir = "";
			}

			if (!FileUtil.exists(
					rootDir + portalWebDir + "WEB-INF/web.xml")) {

				portalWebDir = "ext-web/tmp/";
			}

			String webXML = FileUtil.read(
				rootDir + portalWebDir + "WEB-INF/web.xml");

			int x = webXML.indexOf("<web-app");

			x = webXML.indexOf(">", x) + 1;

			int y = webXML.indexOf("</web-app>");

			webXML = webXML.substring(x, y);

			File webSite = new File(rootDir + "web-sites");

			String[] webSites = webSite.list();

			for (int i = 0; i < webSites.length; i++) {
				if (webSites[i].endsWith("-web")) {

					// web.xml

					File webSiteXML = new File(
						rootDir + webSitesDir + webSites[i] +
						"/docroot/WEB-INF/web.xml");

					String content = FileUtil.read(webSiteXML);

					int z = content.indexOf("<web-app");

					z = content.indexOf(">", z) + 1;

					String newContent =
						content.substring(0, z) +
						webXML +
						content.substring(z, content.length());

					newContent = organizeWebXML(newContent);

					if ((newContent != null) && !content.equals(newContent)) {
						FileUtil.write(webSiteXML, newContent);
					}

					for (int j = 0; j < _FILES.length; j++) {
						FileUtil.copyFile(
							rootDir + portalWebDir + "WEB-INF/" +
								_FILES[j],
							rootDir + webSitesDir + webSites[i] +
								"/docroot/WEB-INF/" + _FILES[j]);
					}

					CopyTask.copyDirectory(
						rootDir + portalWebDir + "html/layouttpl",
						rootDir + webSitesDir + webSites[i] +
							"/docroot/html/layouttpl");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String[] _FILES = new String[] {
		"liferay-display.xml", "liferay-layout-templates.xml",
		"liferay-layout-templates-ext.xml", "liferay-look-and-feel.xml",
		"liferay-look-and-feel-ext.xml", "liferay-plugin-package.xml",
		"liferay-portlet.xml", "liferay-portlet-ext.xml",
		PortalUtil.PORTLET_XML_FILE_NAME_STANDARD,
		PortalUtil.PORTLET_XML_FILE_NAME_CUSTOM, "portlet-ext.xml",
		"struts-config.xml", "struts-config-ext.xml", "tiles-defs.xml",
		"tiles-defs-ext.xml"
	};

}