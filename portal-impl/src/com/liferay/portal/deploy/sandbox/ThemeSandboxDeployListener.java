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

import com.liferay.portal.kernel.deploy.sandbox.BaseSandboxDeployListener;
import com.liferay.portal.kernel.deploy.sandbox.SandboxDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class ThemeSandboxDeployListener extends BaseSandboxDeployListener {

	public void deploy(File dir) throws SandboxDeployException {
		String dirName = dir.getName();

		int ndx = dirName.lastIndexOf('-');
		if (ndx == -1) {
			return;
		}

		if (dirName.substring(ndx + 1).equals("theme") == false) {
			return;
		}

		String folderName = dirName.substring(0, ndx);
		String themeId = _createThemeId(folderName);
		String themeName = _createThemeName(folderName);

		if (_log.isDebugEnabled()) {
			_log.debug("Deploying sandbox theme: " + themeId);
		}

		try {

			_copyDefaultTheme(dir);

			_copyClassesLibAndTld(dir);

			_copyThemeDescriptors(dir);

			_resolveAllThemeProperties(dir, themeId, themeName);

			_createContextXml(dir, themeId);

		} catch (IOException ie) {
			throw new SandboxDeployException(ie);
		}
	}

	public void undeploy(File dir) throws SandboxDeployException {

		String dirName = dir.getName();

		int ndx = dirName.lastIndexOf('-');
		if (ndx == -1) {
			return;
		}

		if (dirName.substring(ndx + 1).equals("theme") == false) {
			return;
		}

		String folderName = dirName.substring(0, ndx);
		String themeId = _createThemeId(folderName);

		try {
			_deleteContextXml(themeId);
		} catch (IOException ie) {
			throw new SandboxDeployException(ie);
		}
	}

	private void _copyClassesLibAndTld(File targetDir) {

		String portalDir = PortalUtil.getPortalWebDir();

		File webInfDir = new File(portalDir, "WEB-INF");

		targetDir = new File(targetDir, "WEB-INF");

		String[] files = {
				"classes/log4j.properties",
				"classes/logging.properties",
				"lib/commons-logging.jar",
				"lib/log4j.jar",
				"lib/util-java.jar",
				"lib/util-taglib.jar",
				"tld/liferay-theme.tld",
				"tld/liferay-util.tld",
		};

		for (String file : files) {
			FileUtil.copyFile(
					new File(webInfDir, file),
					new File(targetDir, file)
			);
		}

	}

	private void _copyDefaultTheme(File targetDir) {

		String portalDir = PortalUtil.getPortalWebDir();

		File themeFolder = new File(portalDir, "html/themes/classic");

		CopyTask.copyDirectory(
				themeFolder, targetDir, null, "/_diffs/**", true, true);
	}

	private void _copyThemeDescriptors(File targetDir) throws IOException {

		URL url = getClass().getResource(
				'/' + THEME_RESOURCE_ROOT + "theme.txt");

		if (url == null) {
			throw new IOException("Theme descriptors not found.");
		}

		String urlProtocol = url.getProtocol();

		if (urlProtocol.equals("jar")) {

			String jarFileName = url.getFile();

			int ndx = jarFileName.indexOf('!');
			if ((ndx == -1) || (jarFileName.startsWith("file:/") == false)) {
				throw new IOException("Malformed URL.");
			}

			jarFileName = jarFileName.substring(6, ndx);

			ZipFile jarFile = new ZipFile(jarFileName);
			Enumeration en = jarFile.entries();

			while (en.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) en.nextElement();

				String entryName = entry.getName();
				if (entryName.startsWith(THEME_RESOURCE_ROOT) == false) {
					continue;
				}

				entryName = entryName.substring(THEME_RESOURCE_ROOT.length());

				File file = new File(targetDir, entryName);
				if (entry.isDirectory()) {

					file.mkdirs();
				} else {

					File parent = file.getParentFile();
					if (parent != null && parent.exists() == false) {
						parent.mkdirs();
					}

					InputStream in = jarFile.getInputStream(entry);
					OutputStream out = null;

					try {
						out = new FileOutputStream(file);
						StreamUtil.transfer(in, out);
					} finally {
						StreamUtil.cleanUp(out);
						StreamUtil.cleanUp(in);
					}
				}
			}
		} else if (urlProtocol.equals("file")) {

			File sourceDir = new File(url.getFile()).getParentFile();

			CopyTask.copyDirectory(
					sourceDir, targetDir, null, null, true, true);
		} else {
			throw new IOException(
					"Unsupported protocol: " + urlProtocol);
		}
	}

	private void _createContextXml(File dir, String themeId)
			throws IOException {

		File contextXml = new File(
			getEngineHostFolder(), themeId + "-theme.xml");

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		sb.append("<Context path=\"").append(themeId).append("-theme\" ");
		sb.append("crossContext=\"true\" docBase=\"");
		sb.append(dir.getAbsolutePath());
		sb.append("\">\n</Context>");

		FileUtil.write(contextXml, sb.toString());
	}

	private String _createThemeId(String name) {

		String themeId = StringUtil.replace(name,
			new String[] {StringPool.SPACE},
			new String[] {StringPool.MINUS});

		return StringUtil.lowerCase(themeId) + '-' + SANDBOX_MARKER;
	}

	private String _createThemeName(String themeId) {

		String themeName = StringUtil.replace(themeId,
			new String[] {StringPool.UNDERLINE, StringPool.MINUS},
			new String[] {StringPool.SPACE, StringPool.SPACE});

		return StringUtil.upperCaseFirstLetter(themeName);
	}

	private void _deleteContextXml(String themeId) throws IOException {

		File contextXml = new File(
			getEngineHostFolder(), themeId + "-theme.xml");

		if (contextXml.isFile()) {
			contextXml.delete();
		}
	}

	private void _resolveAllThemeProperties(
			File themeDir, String themeId, String themeName)
			throws IOException {

		String[] filesToChange = new String[] {
			"theme.txt",
			"WEB-INF/liferay-look-and-feel.xml",
			"WEB-INF/liferay-plugin-package.properties",
			"WEB-INF/liferay-plugin-package.xml",
			"WEB-INF/web.xml"
		};

		for (String file : filesToChange) {

			_resolvePropertiesInFile(
				new File(themeDir, file), themeId, themeName);
		}
	}

	private void _resolvePropertiesInFile(
		File target, String themeId, String themeName) throws IOException {

		String content = FileUtil.read(target);

		String portalVersion = ReleaseInfo.getVersion();

		content = StringUtil.replace(content,
			new String[] {"${theme-id}", "${theme-name}", "${portal-version}"},
			new String[] {themeId, themeName, portalVersion});

		FileUtil.write(target, content);
	}

	private static Log _log = LogFactoryUtil.getLog(
			ThemeSandboxDeployListener.class);

}