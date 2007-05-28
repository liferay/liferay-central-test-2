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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.Http;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PHPPortletAutoDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PHPPortletAutoDeployer extends PortletAutoDeployer {

	protected PHPPortletAutoDeployer() {
	}

	protected void copyXmls(File srcFile, String displayName)
		throws Exception {
		copyFile(srcFile, "/WEB-INF", "portlet.xml", displayName);
		copyFile(srcFile, "/WEB-INF", "liferay-portlet.xml", displayName);
		copyFile(srcFile, "/WEB-INF", "liferay-display.xml", displayName);
		copyFile(srcFile, "/WEB-INF", "web.xml", displayName);
		copyFile(srcFile, "/WEB-INF", "geronimo-web.xml", displayName);
	}

	private void copyFile(
		File srcFile, String targetDir, String fileName, String displayName)
		throws IOException {

		File tempFile = new File(_basePath + fileName);

		if (!tempFile.exists()) {
			String fullFilePath =
				"com/liferay/portal/deploy/dependencies/" + fileName;

			ClassLoader classLoader = getClass().getClassLoader();

			String content = StringUtil.read(classLoader, fullFilePath);

			FileUtil.write(tempFile, content);
		}

		File destDir = new File(srcFile.getPath() + targetDir);
		destDir.mkdir();

		Map filterMap = new HashMap();

		filterMap.put("portlet_name", displayName);
		filterMap.put("portlet_display_name", displayName);
		filterMap.put("portlet_class", "com.liferay.util.php.PHPPortlet");

		CopyTask.copyFile(tempFile, destDir, filterMap, false, true);
	}

	public void init()
		throws AutoDeployException {

		_basePath =
			SystemProperties.get(SystemProperties.TMP_DIR) +
				"/liferay/com/liferay/portal/deploy/dependencies/";

		String[] gplLibs = {"quercus.jar", "resin-util.jar", "script-10.jar"};

		try {
			for (int i = 0; i < gplLibs.length; i++) {
				String gplLib = gplLibs[i];

				if (!new File(_basePath + gplLib).exists()) {
					synchronized(this) {
						String url = PropsUtil.get(
							PropsUtil.LIBRARY_DOWNLOAD_URL + gplLib);

						_log.info("Downloading library from " + url);

						byte[] bytes = Http.URLtoByteArray(url);
						FileUtil.write(_basePath + gplLib, bytes);
					}
				}

				jars.add(_basePath + gplLib);
			}

			jars.add(DeployUtil.getResourcePath("portals-bridges.jar"));
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	private static Log _log = LogFactory.getLog(PHPPortletAutoDeployer.class);

	private String _basePath;
	
}