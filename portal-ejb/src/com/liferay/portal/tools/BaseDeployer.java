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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.ant.CopyTask;
import com.liferay.util.ant.DeleteTask;
import com.liferay.util.ant.ExpandTask;
import com.liferay.util.ant.UpToDateTask;
import com.liferay.util.ant.WarTask;
import com.liferay.util.xml.XMLFormatter;

import java.io.File;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="BaseDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class BaseDeployer {

	public static void main(String[] args) {
		List wars = new ArrayList();
		List jars = new ArrayList();

		for (int i = 0; i < args.length; i++) {
			if (args[i].endsWith(".war")) {
				wars.add(args[i]);
			}
			else if (args[i].endsWith(".jar")) {
				jars.add(args[i]);
			}
		}

		new BaseDeployer(wars, jars);
	}

	protected BaseDeployer() {
	}

	protected BaseDeployer(List wars, List jars) {
		baseDir = System.getProperty("deployer.base.dir");
		destDir = System.getProperty("deployer.dest.dir");
		appServerType = System.getProperty("deployer.app.server.type");
		portletTaglibDTD = System.getProperty("deployer.portlet.taglib.dtd");
		portletExtTaglibDTD = System.getProperty(
			"deployer.portlet.ext.taglib.dtd");
		securityTaglibDTD = System.getProperty("deployer.security.taglib.dtd");
		themeTaglibDTD = System.getProperty("deployer.theme.taglib.dtd");
		uiTaglibDTD = System.getProperty("deployer.ui.taglib.dtd");
		utilTaglibDTD = System.getProperty("deployer.util.taglib.dtd");
		unpackWar = GetterUtil.getBoolean(
			System.getProperty("deployer.unpack.war"), true);
		tomcatLibDir = System.getProperty("deployer.tomcat.lib.dir");
		this.wars = wars;
		this.jars = jars;

		checkArguments();

		try {
			deploy();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void checkArguments() {
		if (Validator.isNull(baseDir)) {
			throw new IllegalArgumentException(
				"The system property deployer.base.dir is not set");
		}

		if (Validator.isNull(destDir)) {
			throw new IllegalArgumentException(
				"The system property deployer.dest.dir is not set");
		}

		if (Validator.isNull(appServerType)) {
			throw new IllegalArgumentException(
				"The system property deployer.app.server.type is not set");
		}

		if (!appServerType.startsWith("geronimo") &&
			!appServerType.startsWith("glassfish") &&
			!appServerType.startsWith("jboss") &&
			!appServerType.startsWith("jonas") &&
			!appServerType.equals("jetty") &&
			!appServerType.equals("oc4j") &&
			!appServerType.equals("orion") &&
			!appServerType.equals("pramati") &&
			!appServerType.equals("resin") &&
			!appServerType.equals("tomcat") &&
			!appServerType.equals("weblogic") &&
			!appServerType.equals("websphere")) {

			throw new IllegalArgumentException(
				appServerType + " is not a valid application server type");
		}
	}

	protected void copyJars(File srcFile) throws Exception {
		for (int i = 0; i < jars.size(); i++) {
			String jarFullName = (String)jars.get(i);
			String jarName = jarFullName.substring(
				jarFullName.lastIndexOf("/") + 1, jarFullName.length());

			if ((!appServerType.equals("tomcat")) ||
				(appServerType.equals("tomcat") &&
					!jarFullName.equals("util-java.jar"))) {

				FileUtil.copyFile(
					jarFullName, srcFile + "/WEB-INF/lib/" + jarName, true);
			}
		}

		FileUtil.delete(srcFile + "/WEB-INF/lib/util-jsf.jar");
	}

	protected void copyTlds(File srcFile) throws Exception {
		if (Validator.isNotNull(portletTaglibDTD)) {
			FileUtil.copyFile(
				portletTaglibDTD, srcFile + "/WEB-INF/tld/liferay-portlet.tld",
				true);
		}

		if (Validator.isNotNull(themeTaglibDTD)) {
			FileUtil.copyFile(
				themeTaglibDTD, srcFile + "/WEB-INF/tld/liferay-theme.tld",
				true);
		}

		if (Validator.isNotNull(utilTaglibDTD)) {
			FileUtil.copyFile(
				utilTaglibDTD, srcFile + "/WEB-INF/tld/liferay-util.tld", true);
		}
	}

	protected void deploy() throws Exception  {
		try {
			File[] files = FileUtil.sortFiles(new File(baseDir).listFiles());

			for (int i = 0; i < files.length; i++) {
				File srcFile = files[i];

				boolean deploy = false;

				if (srcFile.getName().endsWith(".war")) {
					deploy = true;

					if ((wars.size() > 0) &&
						(!wars.contains(srcFile.getName()))) {

						deploy = false;
					}
				}

				if (deploy) {
					System.out.println("\nDeploying " + srcFile.getName());

					String deployDir = srcFile.getName();

					if (appServerType.equals("jetty") ||
						appServerType.equals("oc4j") ||
						appServerType.equals("orion") ||
						appServerType.equals("resin") ||
						appServerType.equals("tomcat")) {

						if (unpackWar) {
							deployDir =
								deployDir.substring(0, deployDir.length() - 4);
						}
					}

					deployDir = destDir + "/" + deployDir;

					if (srcFile.isDirectory()) {
						deployDirectory(
							srcFile, new File(deployDir),
							getDisplayName(srcFile));
					}
					else {
						deployFile(srcFile, new File(deployDir));
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void deployDirectory(File srcFile, String displayName)
		throws Exception {

		deployDirectory(srcFile, null, displayName);
	}

	protected void deployDirectory(
			File srcFile, File deployDir, String displayName)
		throws Exception {

		copyJars(srcFile);
		copyTlds(srcFile);

		updateGeronimoWebXML(srcFile, displayName);

		File webXML = new File(srcFile + "/WEB-INF/web.xml");

		updateWebXML(webXML, srcFile, displayName);

		if ((deployDir != null) && !baseDir.equals(destDir)) {
			String excludes = StringPool.BLANK;

			if (appServerType.equals("tomcat")) {
				String[] libs = FileUtil.listFiles(tomcatLibDir);

				for (int i = 0; i < libs.length; i++) {
					excludes += "**/WEB-INF/lib/" + libs[i] + ",";
				}
			}

			if (!unpackWar || appServerType.equals("websphere")) {
				WarTask.war(srcFile, deployDir, "WEB-INF/web.xml", webXML);
			}
			else {

				// The deployer only copies files that have been modified.
				// However, the deployer always copies and overwrites web.xml
				// after the other files have been copied because application
				// servers usually detect that a WAR has been modified based on
				// the web.xml time stamp.

				excludes += "**/WEB-INF/web.xml";

				CopyTask.copyDirectory(
					srcFile, deployDir, StringPool.BLANK, excludes, false,
					true);

				CopyTask.copyDirectory(
					srcFile, deployDir, "**/WEB-INF/web.xml", StringPool.BLANK,
					true, false);

				if (appServerType.equals("tomcat")) {

					// See org.apache.catalina.startup.HostConfig to see how
					// Tomcat checks to make sure that web.xml was modified 5
					// seconds after WEB-INF

					File deployWebXML = new File(
						deployDir + "/WEB-INF/web.xml");

					deployWebXML.setLastModified(
						System.currentTimeMillis() + (Time.SECOND * 6));
				}
			}
		}
	}

	protected void deployFile(File srcFile, File deployDir) throws Exception {
		if (UpToDateTask.isUpToDate(srcFile, deployDir)) {
			return;
		}

		// Don't delete the deploy directory because it can cause problems in
		// certain application servers

		//DeleteTask.deleteDirectory(deployDir);

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR) + File.separator +
				Time.getTimestamp());

		ExpandTask.expand(srcFile, tempDir);

		deployDirectory(tempDir, deployDir, getDisplayName(srcFile));

		DeleteTask.deleteDirectory(tempDir);
	}

	protected String getDisplayName(File srcFile) {
		String displayName = srcFile.getName();

		return displayName.substring(0, displayName.length() - 4);
	}

	protected String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		String extraContent =
			"<display-name>" + displayName + "</display-name>";

		boolean hasTaglib = false;

		if (Validator.isNotNull(portletTaglibDTD) ||
			Validator.isNotNull(portletExtTaglibDTD) ||
			Validator.isNotNull(securityTaglibDTD) ||
			Validator.isNotNull(themeTaglibDTD) ||
			Validator.isNotNull(uiTaglibDTD) ||
			Validator.isNotNull(utilTaglibDTD)) {

			hasTaglib = true;
		}

		if (hasTaglib && (webXmlVersion > 2.3)) {
			extraContent += "<jsp-config>";
		}

		if (Validator.isNotNull(portletTaglibDTD)) {
			extraContent +=
				"<taglib>" +
				"<taglib-uri>http://java.sun.com/portlet</taglib-uri>" +
				"<taglib-location>" +
				"/WEB-INF/tld/liferay-portlet.tld" +
				"</taglib-location>" +
				"</taglib>";
		}

		if (Validator.isNotNull(portletExtTaglibDTD)) {
			extraContent +=
				"<taglib>" +
				"<taglib-uri>http://liferay.com/tld/portlet</taglib-uri>" +
				"<taglib-location>" +
				"/WEB-INF/tld/liferay-portlet-ext.tld" +
				"</taglib-location>" +
				"</taglib>";
		}

		if (Validator.isNotNull(securityTaglibDTD)) {
			extraContent +=
				"<taglib>" +
				"<taglib-uri>http://liferay.com/tld/security</taglib-uri>" +
				"<taglib-location>" +
				"/WEB-INF/tld/liferay-security.tld" +
				"</taglib-location>" +
				"</taglib>";
		}

		if (Validator.isNotNull(themeTaglibDTD)) {
			extraContent +=
				"<taglib>" +
				"<taglib-uri>http://liferay.com/tld/theme</taglib-uri>" +
				"<taglib-location>" +
				"/WEB-INF/tld/liferay-theme.tld" +
				"</taglib-location>" +
				"</taglib>";
		}

		if (Validator.isNotNull(uiTaglibDTD)) {
			extraContent +=
				"<taglib>" +
				"<taglib-uri>http://liferay.com/tld/ui</taglib-uri>" +
				"<taglib-location>" +
				"/WEB-INF/tld/liferay-ui.tld" +
				"</taglib-location>" +
				"</taglib>";
		}

		if (Validator.isNotNull(utilTaglibDTD)) {
			extraContent +=
				"<taglib>" +
				"<taglib-uri>http://liferay.com/tld/util</taglib-uri>" +
				"<taglib-location>" +
				"/WEB-INF/tld/liferay-util.tld" +
				"</taglib-location>" +
				"</taglib>";
		}

		if (hasTaglib && (webXmlVersion > 2.3)) {
			extraContent += "</jsp-config>";
		}

		return extraContent;
	}

	protected void updateGeronimoWebXML(File srcFile, String displayName)
		throws Exception {

		File geronimoWebXML = new File(srcFile + "/WEB-INF/geronimo-web.xml");

		if (geronimoWebXML.exists()) {
			return;
		}

		String content = "";

		content +=
			"<web-app " +
				"xmlns=\"http://geronimo.apache.org/xml/ns/j2ee/web-1.1\">";
		content += "<environment>";
		content += "<moduleId>";
		content += "<artifactId>" + displayName + "</artifactId>";
		content += "</moduleId>";
		content += "<dependencies>";
		content += "<dependency>";
		content += "<groupId>liferay</groupId>";
		content += "<artifactId>liferay-portal-tomcat</artifactId>";
		content += "</dependency>";
		content += "</dependencies>";
		content += "</environment>";
		content += "</web-app>";

		content = XMLFormatter.toString(content);

		FileUtil.write(geronimoWebXML, content);

		System.out.println("  Adding Geronimo " + geronimoWebXML);
	}

	protected void updateWebXML(
			File webXML, File srcFile, String displayName)
		throws Exception {

		if (!webXML.exists()) {
			String content = "";

			content += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
			content +=
				"<!DOCTYPE web-app PUBLIC \"-//Sun Microsystems, Inc.//" +
					"DTD Web Application 2.3//EN\" " +
						"\"http://java.sun.com/dtd/web-app_2_3.dtd\">\n\n";
			content += "<web-app>\n";
			content += "</web-app>";

			FileUtil.write(webXML, content);

			System.out.println("  Adding Servlet " + webXML);
		}

		String content = FileUtil.read(webXML);

		int pos = content.indexOf("<web-app");
		pos = content.indexOf(">", pos) + 1;

		double webXmlVersion = 2.3;

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document webXmlDoc = reader.read(new StringReader(content));

		Element webXmlRoot = webXmlDoc.getRootElement();

		webXmlVersion = GetterUtil.getDouble(
			webXmlRoot.attributeValue("version"), webXmlVersion);

		// Merge extra content

		String extraContent = getExtraContent(
			webXmlVersion, srcFile, displayName);

		String newContent =
			content.substring(0, pos) + extraContent +
			content.substring(pos, content.length());

		// Replace old package names

		newContent = StringUtil.replace(
			newContent, "com.liferay.portal.servlet.",
			"com.liferay.portal.kernel.servlet.");

		newContent = StringUtil.replace(
			newContent, "com.liferay.portal.shared.",
			"com.liferay.portal.kernel.");

		newContent = WebXMLBuilder.organizeWebXML(newContent);

		FileUtil.write(webXML, newContent, true);

		System.out.println(
			"  Modifying Servlet " + webXmlVersion + " " + webXML);
	}

	protected String baseDir;
	protected String destDir;
	protected String appServerType;
	protected String portletTaglibDTD;
	protected String portletExtTaglibDTD;
	protected String securityTaglibDTD;
	protected String themeTaglibDTD;
	protected String uiTaglibDTD;
	protected String utilTaglibDTD;
	protected boolean unpackWar;
	protected String tomcatLibDir;
	protected List wars;
	protected List jars;

}