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

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.Http;
import com.liferay.util.SystemProperties;
import com.liferay.util.Time;
import com.liferay.util.ant.CopyTask;
import com.liferay.util.ant.DeleteTask;
import com.liferay.util.ant.ExpandTask;
import com.liferay.util.ant.UpToDateTask;
import com.liferay.util.ant.WarTask;
import com.liferay.util.xml.XMLFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

	public static final String DEPLOY_TO_PREFIX = "DEPLOY_TO__";

	public static void main(String[] args) {
		List wars = new ArrayList();
		List jars = new ArrayList();

		for (int i = 0; i < args.length; i++) {
			String fileName = args[i].toLowerCase();

			if (fileName.endsWith(".war")) {
				wars.add(args[i]);
			}
			else if (fileName.endsWith(".jar")) {
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
		jbossPrefix = GetterUtil.getString(
			System.getProperty("deployer.jboss.prefix"));
		tomcatLibDir = System.getProperty("deployer.tomcat.lib.dir");
		this.wars = wars;
		this.jars = jars;

		if (appServerType.startsWith("glassfish") ||
			appServerType.equals("pramati") ||
			appServerType.equals("weblogic")) {

			unpackWar = false;
		}

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

		if (Validator.isNotNull(jbossPrefix) &&
			!Validator.isNumber(jbossPrefix)) {

			jbossPrefix = "1";
		}
	}

	protected void copyDependencyXml(String fileName, String targetDir)
		throws Exception {

		copyDependencyXml(fileName, targetDir, null);
	}

	protected void copyDependencyXml(
			String fileName, String targetDir, Map filterMap)
		throws Exception {

		copyDependencyXml(fileName, targetDir, filterMap, false);
	}

	protected void copyDependencyXml(
			String fileName, String targetDir, Map filterMap, boolean overwrite)
		throws Exception {

		File file = new File(DeployUtil.getResourcePath(fileName));
		File targetFile = new File(targetDir + "/" + fileName);

		if (!targetFile.exists()) {
			CopyTask.copyFile(
				file, new File(targetDir), filterMap, overwrite, true);
		}
	}

	protected void copyJars(File srcFile, PluginPackage pluginPackage)
		throws Exception {

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

	protected void copyTlds(File srcFile, PluginPackage pluginPackage)
		throws Exception {

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

	protected void copyXmls(
		File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		copyDependencyXml("geronimo-web.xml", srcFile + "/WEB-INF");
		copyDependencyXml("web.xml", srcFile + "/WEB-INF");
	}

	protected void deploy() throws Exception  {
		try {
			File[] files = FileUtil.sortFiles(new File(baseDir).listFiles());

			for (int i = 0; i < files.length; i++) {
				File srcFile = files[i];

				String fileName = srcFile.getName().toLowerCase();

				boolean deploy = false;

				if (fileName.endsWith(".war") || fileName.endsWith(".zip")) {
					deploy = true;

					if ((wars.size() > 0) &&
						(!wars.contains(srcFile.getName()))) {

						deploy = false;
					}
				}

				if (deploy) {
					deployFile(srcFile);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void deployDirectory(
			File srcFile, String displayName, boolean override,
			PluginPackage pluginPackage)
		throws Exception {

		deployDirectory(srcFile, null, displayName, override, pluginPackage);
	}

	protected void deployDirectory(
			File srcFile, File deployDir, String displayName, boolean overwrite,
			PluginPackage pluginPackage)
		throws Exception {

		copyJars(srcFile, pluginPackage);
		copyTlds(srcFile, pluginPackage);
		copyXmls(srcFile, displayName, pluginPackage);

		updateGeronimoWebXml(srcFile, displayName, pluginPackage);

		File webXml = new File(srcFile + "/WEB-INF/web.xml");

		updateWebXml(webXml, srcFile, displayName, pluginPackage);

		if ((deployDir != null) && !baseDir.equals(destDir)) {
			updateDeployDirectory(srcFile);

			String excludes = StringPool.BLANK;

			if (appServerType.startsWith("jboss")) {
				excludes += "**/WEB-INF/lib/log4j.jar,";
			}
			else if (appServerType.equals("tomcat")) {
				String[] libs = FileUtil.listFiles(tomcatLibDir);

				for (int i = 0; i < libs.length; i++) {
					excludes += "**/WEB-INF/lib/" + libs[i] + ",";
				}

				File contextXml = new File(srcFile + "/META-INF/context.xml");

				if (contextXml.exists()) {
					String content = FileUtil.read(contextXml);

					if (content.indexOf(_PORTAL_CLASS_LOADER) != -1) {
						excludes += "**/WEB-INF/lib/util-bridges.jar,";
						excludes += "**/WEB-INF/lib/util-java.jar,";
						excludes += "**/WEB-INF/lib/util-taglib.jar,";
					}
				}

				try {

					// LEP-2990

					Class.forName("javax.el.ELContext");

					excludes += "**/WEB-INF/lib/el-api.jar,";
				}
				catch (ClassNotFoundException cnfe) {
				}
			}

			if (!unpackWar || appServerType.equals("websphere")) {
				File tempDir = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + Time.getTimestamp());

				WarTask.war(srcFile, tempDir, "WEB-INF/web.xml", webXml);

				if (!tempDir.renameTo(deployDir)) {
					WarTask.war(srcFile, deployDir, "WEB-INF/web.xml", webXml);
				}

				DeleteTask.deleteDirectory(tempDir);
			}
			else {

				// The deployer might only copy files that have been modified.
				// However, the deployer always copies and overwrites web.xml
				// after the other files have been copied because application
				// servers usually detect that a WAR has been modified based on
				// the web.xml time stamp.

				excludes += "**/WEB-INF/web.xml";

				CopyTask.copyDirectory(
					srcFile, deployDir, StringPool.BLANK, excludes, overwrite,
					true);

				CopyTask.copyDirectory(
					srcFile, deployDir, "**/WEB-INF/web.xml", StringPool.BLANK,
					true, false);

				if (appServerType.equals("tomcat")) {

					// See org.apache.catalina.startup.HostConfig to see how
					// Tomcat checks to make sure that web.xml was modified 5
					// seconds after WEB-INF

					File deployWebXml = new File(
						deployDir + "/WEB-INF/web.xml");

					deployWebXml.setLastModified(
						System.currentTimeMillis() + (Time.SECOND * 6));
				}
			}
		}
	}

	protected void deployFile(File srcFile) throws Exception {
		PluginPackage pluginPackage = readPluginPackage(srcFile);

		if (_log.isInfoEnabled()) {
			_log.info("Deploying " + srcFile.getName());
		}

		String deployDir = null;
		String displayName = null;
		boolean overwrite = false;
		String preliminaryContext = null;

		// File names starting with DEPLOY_TO_PREFIX should use the filename
		// after the prefix as the deployment context

		if (srcFile.getName().startsWith(DEPLOY_TO_PREFIX)) {
			displayName = srcFile.getName().substring(
				DEPLOY_TO_PREFIX.length(), srcFile.getName().length() - 4);

			overwrite = true;
			preliminaryContext = displayName;
		}

		if (preliminaryContext == null) {
			preliminaryContext = getDisplayName(srcFile);
		}

		if (pluginPackage != null) {
			if (!PluginPackageUtil.isCurrentVersionSupported(
					pluginPackage.getLiferayVersions())) {

				throw new AutoDeployException(
					srcFile.getName() +
						" does not support this version of Liferay");
			}

			if (displayName == null) {
				displayName = pluginPackage.getRecommendedDeploymentContext();
			}

			if (Validator.isNull(displayName)) {
				displayName = getDisplayName(srcFile);
			}

			pluginPackage.setContext(displayName);

			PluginPackageUtil.updateInstallingPluginPackage(
				preliminaryContext, pluginPackage);
		}

		if (Validator.isNotNull(displayName)) {
			deployDir = displayName + ".war";
		}
		else {
			deployDir = srcFile.getName();
			displayName = getDisplayName(srcFile);
		}

		if (appServerType.startsWith("jboss")) {
			deployDir = jbossPrefix + deployDir;
		}
		else if (appServerType.equals("jetty") ||
				 appServerType.equals("oc4j") ||
				 appServerType.equals("orion") ||
				 appServerType.equals("resin") ||
				 appServerType.equals("tomcat")) {

			if (unpackWar) {
				deployDir = deployDir.substring(0, deployDir.length() - 4);
			}
		}

		deployDir = destDir + "/" + deployDir;

		File deployDirFile = new File(deployDir);

		try {
			PluginPackage previousPluginPackage =
				readPluginPackage(deployDirFile);

			if ((pluginPackage != null) && (previousPluginPackage != null)) {
				if (_log.isInfoEnabled()) {
					String name = pluginPackage.getName();
					String previousVersion = previousPluginPackage.getVersion();
					String version = pluginPackage.getVersion();

					_log.info(
						"Updating " + name + " from version " +
							previousVersion + " to version " + version);
				}

				if (pluginPackage.isLaterVersionThan(
					previousPluginPackage)) {

					overwrite = true;
				}
			}

			if (srcFile.isDirectory()) {
				deployDirectory(
					srcFile, deployDirFile, displayName, overwrite,
					pluginPackage);
			}
			else {
				boolean deployed = deployFile(
					srcFile, deployDirFile, displayName, overwrite,
					pluginPackage);

				if (!deployed) {
					String context = preliminaryContext;

					if (pluginPackage != null) {
						context = pluginPackage.getContext();
					}

					PluginPackageUtil.endPluginPackageInstallation(context);
				}
			}
		}
		catch (Exception e) {
			if (pluginPackage != null) {
				PluginPackageUtil.endPluginPackageInstallation(
					pluginPackage.getContext());
			}

			throw e;
		}
	}

	protected boolean deployFile(
			File srcFile, File deployDir, String displayName, boolean overwrite,
			PluginPackage pluginPackage)
		throws Exception {

		if (!overwrite && UpToDateTask.isUpToDate(srcFile, deployDir)) {
			if (_log.isInfoEnabled()) {
				_log.info(deployDir + " is already up to date");
			}

			return false;
		}

		// Don't delete the deploy directory because it can cause problems in
		// certain application servers

		//DeleteTask.deleteDirectory(deployDir);

		File tempDir = new File(
			SystemProperties.get(SystemProperties.TMP_DIR) + File.separator +
				Time.getTimestamp());

		ExpandTask.expand(srcFile, tempDir);

		deployDirectory(
			tempDir, deployDir, displayName, overwrite, pluginPackage);

		DeleteTask.deleteDirectory(tempDir);

		return true;
	}

	protected String downloadJar(String jar) throws Exception {
		String tmpDir = SystemProperties.get(SystemProperties.TMP_DIR);

		File file = new File(
			tmpDir + "/liferay/com/liferay/portal/deploy/dependencies/" +
				jar);

		if (!file.exists()) {
			synchronized(this) {
				String url = PropsUtil.get(
					PropsUtil.LIBRARY_DOWNLOAD_URL + jar);

				if (_log.isInfoEnabled()) {
					_log.info("Downloading library from " + url);
				}

				byte[] bytes = Http.URLtoByteArray(url);

				FileUtil.write(file, bytes);
			}
		}

		return FileUtil.getAbsolutePath(file);
	}

	protected String getDisplayName(File srcFile) {
		String displayName = srcFile.getName();

		displayName = displayName.substring(0, displayName.length() - 4);

		if (appServerType.startsWith("jboss") &&
			Validator.isNotNull(jbossPrefix) &&
			displayName.startsWith(jbossPrefix)) {

			displayName = displayName.substring(1, displayName.length());
		}

		return displayName;
	}

	protected String getExtraContent(
			double webXmlVersion, File srcFile, String displayName)
		throws Exception {

		StringMaker sm = new StringMaker();

		sm.append("<display-name>");
		sm.append(displayName);
		sm.append("</display-name>");

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
			sm.append("<jsp-config>");
		}

		if (Validator.isNotNull(portletTaglibDTD)) {
			sm.append("<taglib>");
			sm.append("<taglib-uri>http://java.sun.com/portlet</taglib-uri>");
			sm.append("<taglib-location>");
			sm.append("/WEB-INF/tld/liferay-portlet.tld");
			sm.append("</taglib-location>");
			sm.append("</taglib>");
		}

		if (Validator.isNotNull(portletExtTaglibDTD)) {
			sm.append("<taglib>");
			sm.append("<taglib-uri>");
			sm.append("http://liferay.com/tld/portlet");
			sm.append("</taglib-uri>");
			sm.append("<taglib-location>");
			sm.append("/WEB-INF/tld/liferay-portlet-ext.tld");
			sm.append("</taglib-location>");
			sm.append("</taglib>");
		}

		if (Validator.isNotNull(securityTaglibDTD)) {
			sm.append("<taglib>");
			sm.append("<taglib-uri>");
			sm.append("http://liferay.com/tld/security");
			sm.append("</taglib-uri>");
			sm.append("<taglib-location>");
			sm.append("/WEB-INF/tld/liferay-security.tld");
			sm.append("</taglib-location>");
			sm.append("</taglib>");
		}

		if (Validator.isNotNull(themeTaglibDTD)) {
			sm.append("<taglib>");
			sm.append("<taglib-uri>http://liferay.com/tld/theme</taglib-uri>");
			sm.append("<taglib-location>");
			sm.append("/WEB-INF/tld/liferay-theme.tld");
			sm.append("</taglib-location>");
			sm.append("</taglib>");
		}

		if (Validator.isNotNull(uiTaglibDTD)) {
			sm.append("<taglib>");
			sm.append("<taglib-uri>http://liferay.com/tld/ui</taglib-uri>");
			sm.append("<taglib-location>");
			sm.append("/WEB-INF/tld/liferay-ui.tld");
			sm.append("</taglib-location>");
			sm.append("</taglib>");
		}

		if (Validator.isNotNull(utilTaglibDTD)) {
			sm.append("<taglib>");
			sm.append("<taglib-uri>http://liferay.com/tld/util</taglib-uri>");
			sm.append("<taglib-location>");
			sm.append("/WEB-INF/tld/liferay-util.tld");
			sm.append("</taglib-location>");
			sm.append("</taglib>");
		}

		if (hasTaglib && (webXmlVersion > 2.3)) {
			sm.append("</jsp-config>");
		}

		return sm.toString();
	}

	protected PluginPackage readPluginPackage(File file) {
		if (!file.exists()) {
			return null;
		}

		InputStream is = null;
		ZipFile zipFile = null;

		try {
			if (file.isDirectory()) {
				File pluginPackageXmlFile = new File(
					file.getPath() + "/WEB-INF/liferay-plugin-package.xml");

				if (pluginPackageXmlFile.exists()) {
					is = new FileInputStream(pluginPackageXmlFile);
				}
			}
			else {
				zipFile = new ZipFile(file);

				ZipEntry zipEntry = zipFile.getEntry(
					"WEB-INF/liferay-plugin-package.xml");

				if (zipEntry != null) {
					is = zipFile.getInputStream(zipEntry);
				}
			}

			if (is == null) {
				if (_log.isInfoEnabled()) {
					_log.info(
						file.getPath() + " does not have " +
							"WEB-INF/liferay-plugin-package.xml");
				}

				return null;
			}

			String xml = StringUtil.read(is);

			xml = XMLFormatter.fixProlog(xml);

			return PluginPackageUtil.readPluginPackageXml(xml);
		}
		catch (Exception e) {
			System.err.println(file.getPath() + ": " + e.toString());
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
				}
			}

			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioe) {
				}
			}
		}

		return null;
	}

	protected void updateDeployDirectory(File srcFile) throws Exception {
	}

	protected void updateGeronimoWebXml(
			File srcFile, String displayName, PluginPackage pluginPackage)
		throws Exception {

		File geronimoWebXml = new File(srcFile + "/WEB-INF/geronimo-web.xml");

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(geronimoWebXml);

		Element root = doc.getRootElement();

		Element environmentEl = root.element("environment");

		Element moduleIdEl = environmentEl.element("moduleId");

		Element artifactIdEl = moduleIdEl.element("artifactId");

		String artifactIdText = GetterUtil.getString(artifactIdEl.getText());

		if (!artifactIdText.equals(displayName)) {
			artifactIdEl.setText(displayName);

			String content = XMLFormatter.toString(doc);

			FileUtil.write(geronimoWebXml, content);

			if (_log.isInfoEnabled()) {
				_log.info("Modifying Geronimo " + geronimoWebXml);
			}
		}
	}

	protected void updateWebXml(
			File webXml, File srcFile, String displayName,
			PluginPackage pluginPackage)
		throws Exception {

		String content = FileUtil.read(webXml);

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
			newContent, "com.liferay.portal.shared.",
			"com.liferay.portal.kernel.");

		newContent = WebXMLBuilder.organizeWebXML(newContent);

		FileUtil.write(webXml, newContent, true);

		if (_log.isInfoEnabled()) {
			_log.info("Modifying Servlet " + webXmlVersion + " " + webXml);
		}
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
	protected String jbossPrefix;
	protected String tomcatLibDir;
	protected List wars;
	protected List jars;

	private static final String _PORTAL_CLASS_LOADER =
		"com.liferay.support.tomcat.loader.PortalClassLoader";

	private static Log _log = LogFactory.getLog(BaseDeployer.class);

}