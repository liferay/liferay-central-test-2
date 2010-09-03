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

package com.liferay.portal.deploy.sandbox;

import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployException;
import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployListener;
import com.liferay.portal.kernel.io.DirectoryFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.deploy.ThemeDeployer;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class ThemeSandboxDeployListener extends ThemeDeployer implements
		SandboxDeployListener {

	public ThemeSandboxDeployListener() {
		String portalDir = PortalUtil.getPortalWebDir();
		String portalLibDir = PortalUtil.getPortalLibDir();

		themeTaglibDTD = portalDir + "/WEB-INF/tld/liferay-theme.tld";

		uiTaglibDTD = portalDir + "/WEB-INF/tld/liferay-util.tld";

		jars = new ArrayList<String>();
		jars.add(portalLibDir + "/commons-logging.jar");
		jars.add(portalLibDir + "/log4j.jar");
		jars.add(portalLibDir + "/util-java.jar");
		jars.add(portalLibDir + "/util-taglib.jar");

		appServerType = ServerDetector.getServerId();
	}

	public void deploy(File dir) throws SandboxDeployException {
		String dirName = dir.getName();

		if (dirName.endsWith(THEME_SUFFIX) == false) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Deploying sandbox theme: " + dirName);
		}

		String displayName = _createDisplayName(dirName);
		String themeName = _createThemeName(dirName);

		try {

			_copyDefaultTheme(dir);

			_createPluginPackageProperties(dir, themeName);
			PluginPackage pp = readPluginPackage(dir);

			copyProperties(dir, pp);
			copyTlds(dir, pp);
			copyJars(dir, pp);
			copyXmls(dir, displayName, pp);

			updateWebXml(
					new File(dir, "WEB-INF/web.xml"), dir, displayName, pp);

			processPluginPackageProperties(dir, displayName, pp);

			_createContextXml(dir);

		} catch (Exception ex) {
			throw new SandboxDeployException(ex);
		}
	}

	public void undeploy(File dir) throws SandboxDeployException {

		String dirName = dir.getName();

		if (_log.isInfoEnabled()) {
			_log.info("Undeploying sandbox theme: " + dirName);
		}

		if (dirName.endsWith(THEME_SUFFIX) == false) {
			return;
		}

		try {
			_deleteContextXml(dir);
		} catch (IOException ie) {
			throw new SandboxDeployException(ie);
		}
	}

	@Override
	protected String getDisplayName(File srcFile) {
		String displayName = super.getDisplayName(srcFile);

		return _createDisplayName(displayName);
	}

	private void _copyDefaultTheme(File targetDir) {

		String portalDir = PortalUtil.getPortalWebDir();

		File themeFolder = new File(portalDir, "html/themes/classic");

		CopyTask.copyDirectory(
				themeFolder, targetDir, null, "/_diffs/**", true, true);
	}

	private void _createContextXml(File dir)
			throws IOException {

		String displayName = _createDisplayName(dir.getName());

		File contextXml = new File(
				_resolveEngineHostFolder(), displayName + ".xml");

		StringBundler sb = new StringBundler();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		sb.append("<Context path=\"").append(displayName).append("\" ");
		sb.append("crossContext=\"true\" docBase=\"");
		sb.append(dir.getAbsolutePath());
		sb.append("\">\n</Context>");

		FileUtil.write(contextXml, sb.toString());
	}

	private String _createDisplayName(String dirName) {

		String displayName = dirName.substring(
				0, dirName.length() - THEME_SUFFIX.length());

		return displayName + '-' + SANDBOX_MARKER + THEME_SUFFIX;
	}

	private void _createPluginPackageProperties(
			File targetDir, String themeName) throws IOException {

		StringBundler sb = new StringBundler();
		sb.append("name=").append(themeName).append(StringPool.NEW_LINE);
		sb.append("module-group-id=liferay\n");
		sb.append("module-incremental-version=1\n");
		sb.append("tags=\n");
		sb.append("short-description=\n");
		sb.append("change-log=\n");
		sb.append("page-url=http://www.liferay.com\n");
		sb.append("author=Liferay, Inc.\n");
		sb.append("licenses=LGPL\n");
		sb.append("speed-filters-enabled=false\n");

		File targetFile = new File(
				targetDir, "WEB-INF/liferay-plugin-package.properties");

		FileUtil.write(targetFile, sb.toString());
	}

	private String _createThemeName(String dirName) {

		String themeName = dirName.substring(
				0, dirName.length() - THEME_SUFFIX.length());

		themeName = StringUtil.replace(themeName,
			new String[] {StringPool.UNDERLINE, StringPool.MINUS},
			new String[] {StringPool.SPACE, StringPool.SPACE});

		return StringUtil.upperCaseFirstLetter(themeName);
	}

	private void _deleteContextXml(File dir) throws IOException {

		String displayName = _createDisplayName(dir.getName());

		File contextXml = new File(
			_resolveEngineHostFolder(), displayName + ".xml");

		if (contextXml.exists() && contextXml.isFile()) {
			contextXml.delete();
		}
	}

	private File _resolveEngineHostFolder() throws IOException {

		File confFolder = new File(System.getenv("CATALINA_BASE"), "conf");

		File rootXml = null;

		File[] firstLevelFolders = confFolder.listFiles(_directoryFilter);

		loop:
		for (File firstLevel : firstLevelFolders) {

			File[] secondLevelFolders = firstLevel.listFiles(_directoryFilter);

			for (File secondLevel : secondLevelFolders) {

				File[] thirdLevelFiles = secondLevel.listFiles();

				for (File file : thirdLevelFiles) {

					if (file.getName().equals("ROOT.xml")) {
						rootXml = file;
						break loop;
					}
				}
			}
		}

		if (rootXml == null) {
			throw new FileNotFoundException(
					"Unable to locate ROOT.xml under CATALINA_BASE/conf");
		}

		return rootXml.getParentFile();
	}

	private static Log _log = LogFactoryUtil.getLog(
			ThemeSandboxDeployListener.class);

	private FileFilter _directoryFilter = new DirectoryFilter();

}