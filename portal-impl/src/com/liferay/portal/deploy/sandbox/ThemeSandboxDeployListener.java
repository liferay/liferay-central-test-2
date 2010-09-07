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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.deploy.ThemeDeployer;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class ThemeSandboxDeployListener
	extends ThemeDeployer implements SandboxDeployListener {

	public ThemeSandboxDeployListener() {
		if (!ServerDetector.isTomcat()) {
			return;
		}

		_engineHostDir = _getEngineHostDir();

		if (_engineHostDir == null) {
			return;
		}

		appServerType = ServerDetector.getServerId();

		String portalWebDir = PortalUtil.getPortalWebDir();

		themeTaglibDTD = portalWebDir + "/WEB-INF/tld/liferay-theme.tld";
		uiTaglibDTD = portalWebDir + "/WEB-INF/tld/liferay-util.tld";

		jars = new ArrayList<String>();

		String portalLibDir = PortalUtil.getPortalLibDir();

		jars.add(portalLibDir + "/commons-logging.jar");
		jars.add(portalLibDir + "/log4j.jar");
		jars.add(portalLibDir + "/util-java.jar");
		jars.add(portalLibDir + "/util-taglib.jar");
	}

	public void deploy(File dir) throws SandboxDeployException {
		try {
			if (!_isEnabled(dir)) {
				return;
			}

			String dirName = dir.getName();

			if (_log.isInfoEnabled()) {
				_log.info("Deploying " + dirName);
			}

			_copyTheme(dir);

			String themeName = _createThemeName(dirName);

			_createPluginPackageProperties(dir, themeName);

			PluginPackage pluginPackage = readPluginPackage(dir);

			String displayName = _createDisplayName(dirName);

			processPluginPackageProperties(dir, displayName, pluginPackage);

			copyJars(dir, pluginPackage);
			copyProperties(dir, pluginPackage);
			copyTlds(dir, pluginPackage);
			copyXmls(dir, displayName, pluginPackage);

			updateWebXml(
				new File(dir, "WEB-INF/web.xml"), dir, displayName,
				pluginPackage);

			_createContextXml(dir);
		}
		catch (Exception e) {
			throw new SandboxDeployException(e);
		}
	}

	public void undeploy(File dir) throws SandboxDeployException {
		try {
			if (!_isEnabled(dir)) {
				return;
			}

			String dirName = dir.getName();

			if (_log.isInfoEnabled()) {
				_log.info("Undeploying " + dirName);
			}

			_deleteContextXml(dir);
		}
		catch (Exception e) {
			throw new SandboxDeployException(e);
		}
	}

	protected String getDisplayName(File srcFile) {
		String displayName = super.getDisplayName(srcFile);

		return _createDisplayName(displayName);
	}

	private void _copyTheme(File dir) {
		String portalWebDir = PortalUtil.getPortalWebDir();

		CopyTask.copyDirectory(
			new File(portalWebDir, "html/themes/classic"), dir, null,
			"/_diffs/**", true, true);
	}

	private void _createContextXml(File dir) throws IOException {
		String displayName = _createDisplayName(dir.getName());

		File contextXml = new File(_engineHostDir, displayName + ".xml");

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\"?>\n");

		sb.append("<Context crossContext=\"true\" docBase=\"");
		sb.append(dir.getAbsolutePath());
		sb.append("\" ");
		sb.append("path=\"");
		sb.append(displayName);
		sb.append("\" />");

		FileUtil.write(contextXml, sb.toString());
	}

	private String _createDisplayName(String dirName) {
		String displayName = dirName.substring(0, dirName.length() - 6);

		StringBundler sb = new StringBundler(4);

		sb.append(displayName);
		sb.append(CharPool.DASH);
		sb.append(SANDBOX_MARKER);
		sb.append("-theme");

		return sb.toString();
	}

	private void _createPluginPackageProperties(File dir, String themeName)
		throws IOException {

		StringBundler sb = new StringBundler(10);

		sb.append("name=" + themeName + "\n");
		sb.append("module-group-id=liferay\n");
		sb.append("module-incremental-version=1\n");
		sb.append("tags=\n");
		sb.append("short-description=\n");
		sb.append("change-log=\n");
		sb.append("page-url=http://www.liferay.com\n");
		sb.append("author=Liferay, Inc.\n");
		sb.append("licenses=LGPL\n");
		sb.append("speed-filters-enabled=false\n");

		FileUtil.write(
			dir + "/WEB-INF/liferay-plugin-package.properties", sb.toString());
	}

	private String _createThemeName(String dirName) {
		String themeName = dirName.substring(0, dirName.length() - 6);

		themeName = StringUtil.replace(
			themeName, new String[] {StringPool.UNDERLINE, StringPool.MINUS},
			new String[] {StringPool.SPACE, StringPool.SPACE});

		return StringUtil.upperCaseFirstLetter(themeName);
	}

	private void _deleteContextXml(File dir) {
		String displayName = _createDisplayName(dir.getName());

		FileUtil.delete(_engineHostDir + "/" + displayName + ".xml");
	}

	private File _getEngineHostDir() {
		String catalinaBaseConf = System.getenv("CATALINA_BASE") + "/conf";

		String[] fileNames = FileUtil.find(
				catalinaBaseConf, "**/ROOT.xml", null);

		if (fileNames.length == 0) {
			_log.error("Unable to locate ROOT.xml under CATALINA_BASE/conf");

			return null;
		}

		File file = new File(catalinaBaseConf, fileNames[0]);

		return file.getParentFile();
	}

	private boolean _isEnabled(File dir) {
		if (_engineHostDir == null) {
			return false;
		}

		String dirName = dir.getName();

		if (!dirName.endsWith("-theme")) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeSandboxDeployListener.class);

	private File _engineHostDir;

}