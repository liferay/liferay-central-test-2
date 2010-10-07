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

import com.liferay.portal.kernel.deploy.Deployer;
import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSandboxHandler implements SandboxHandler {

	public BaseSandboxHandler(Deployer deployer) {
		_deployer = deployer;
		_engineHostDir = getEngineHostDir();
	}

	public void createContextXml(File dir) throws IOException {
		String displayName = getDisplayName(dir.getName());

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

	public void createPluginPackageProperties(
			File dir, String pluginPackageName)
		throws IOException {

		StringBundler sb = new StringBundler(10);

		sb.append("name=" + pluginPackageName + "\n");
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

	public void deleteContextXml(File dir) {
		String displayName = getDisplayName(dir.getName());

		FileUtil.delete(_engineHostDir + "/" + displayName + ".xml");
	}

	public void deploy(File dir) throws SandboxDeployException {
		try {
			if (!isEnabled(dir)) {
				return;
			}

			String dirName = dir.getName();

			if (_log.isInfoEnabled()) {
				_log.info("Deploying " + dirName);
			}

			clonePlugin(dir);

			String pluginPackageName = getPluginPackageName(dirName);

			createPluginPackageProperties(dir, pluginPackageName);

			PluginPackage pluginPackage = _deployer.readPluginPackage(dir);

			String displayName = getDisplayName(dirName);

			_deployer.processPluginPackageProperties(
				dir, displayName, pluginPackage);

			_deployer.copyJars(dir, pluginPackage);
			_deployer.copyProperties(dir, pluginPackage);
			_deployer.copyTlds(dir, pluginPackage);
			_deployer.copyXmls(dir, displayName, pluginPackage);

			_deployer.updateWebXml(
				new File(dir, "WEB-INF/web.xml"), dir, displayName,
				pluginPackage);

			createContextXml(dir);
		}
		catch (Exception e) {
			throw new SandboxDeployException(e);
		}
	}

	public String getDisplayName(String dirName) {
		String displayName = dirName.substring(0, dirName.length() - 6);

		StringBundler sb = new StringBundler(5);

		sb.append(displayName);
		sb.append(CharPool.DASH);
		sb.append(SANDBOX_MARKER);
		sb.append(StringPool.DASH);
		sb.append(getPluginType());

		return sb.toString();
	}

	public String getPluginPackageName(String dirName) {
		String pluginPackageName = dirName.substring(0, dirName.length() - 6);

		pluginPackageName = StringUtil.replace(
			pluginPackageName,
			new String[] {StringPool.UNDERLINE, StringPool.MINUS},
			new String[] {StringPool.SPACE, StringPool.SPACE});

		return StringUtil.upperCaseFirstLetter(pluginPackageName);
	}

	public boolean isEnabled(File dir) {
		if (_engineHostDir == null) {
			return false;
		}

		String dirName = dir.getName();

		if (!dirName.endsWith(StringPool.DASH.concat(getPluginType()))) {
			return false;
		}

		return true;
	}

	public void undeploy(File dir) throws SandboxDeployException {
		try {
			if (!isEnabled(dir)) {
				return;
			}

			String dirName = dir.getName();

			if (_log.isInfoEnabled()) {
				_log.info("Undeploying " + dirName);
			}

			deleteContextXml(dir);
		}
		catch (Exception e) {
			throw new SandboxDeployException(e);
		}
	}

	protected abstract void clonePlugin(File dir);

	protected File getEngineHostDir() {
		if (!ServerDetector.isTomcat()) {
			return null;
		}

		String dirName = System.getenv("CATALINA_BASE") + "/conf";

		String[] fileNames = FileUtil.find(dirName, "**/ROOT.xml", null);

		if (fileNames.length == 0) {
			_log.error("Unable to locate ROOT.xml under CATALINA_BASE/conf");

			return null;
		}

		File file = new File(fileNames[0]);

		return file.getParentFile();
	}

	protected abstract String getPluginType();

	private static Log _log = LogFactoryUtil.getLog(BaseSandboxHandler.class);

	private Deployer _deployer;
	private File _engineHostDir;

}