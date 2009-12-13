/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.WebDirDetector;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.tools.WebXMLBuilder;
import com.liferay.portal.util.ExtRegistry;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * <a href="ExtHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ExtHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error registering extension environment for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering extension environment for ", t);
		}
	}

	protected void copyJar(
			ServletContext servletContext, String dir, String jarName)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		String jarFullName = "/WEB-INF/" + jarName + "/" + jarName + ".jar";

		InputStream is = servletContext.getResourceAsStream(jarFullName);

		if (is == null) {
			throw new HotDeployException(jarFullName + " does not exist");
		}

		String newJarFullName =
			dir + "ext-" + servletContextName + jarName.substring(3) + ".jar";

		StreamUtil.transfer(is, new FileOutputStream(new File(newJarFullName)));
	}

	protected void installExt(
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		String globalLibDir = PortalUtil.getGlobalLibDir();
		String portalWebDir = PortalUtil.getPortalWebDir();
		String portalLibDir = PortalUtil.getPortalLibDir();
		String pluginWebDir = WebDirDetector.getRootDir(portletClassLoader);

		copyJar(servletContext, globalLibDir, "ext-service");
		copyJar(servletContext, portalLibDir, "ext-impl");
		copyJar(servletContext, portalLibDir, "ext-util-bridges");
		copyJar(servletContext, portalLibDir, "ext-util-java");
		copyJar(servletContext, portalLibDir, "ext-util-taglib");

		mergeWebXml(portalWebDir, pluginWebDir);

		CopyTask.copyDirectory(
			pluginWebDir + "WEB-INF/ext-web/docroot", portalWebDir,
			StringPool.BLANK, "**/WEB-INF/web.xml", true, false);

		FileUtil.copyFile(
			pluginWebDir + "WEB-INF/ext-" + servletContextName + ".xml",
			portalWebDir + "WEB-INF/ext-" + servletContextName + ".xml");

		ExtRegistry.registerExt(servletContext);
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String xml = HttpUtil.URLtoString(servletContext.getResource(
			"/WEB-INF/ext-" + servletContextName + ".xml"));

		if (xml == null) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering extension environment for " + servletContextName);
		}

		if (ExtRegistry.isRegistered(servletContextName)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Extension environment for " + servletContextName +
						" has been applied.");
			}

			return;
		}

		Map<String, Set<String>> conflicts = ExtRegistry.getConflicts(
			servletContext);

		if (!conflicts.isEmpty()) {
			StringBuilder sb = new StringBuilder();

			sb.append(
				"Extension environment for " + servletContextName +
					" cannot be applied because of detected conflicts:");

			Iterator<Map.Entry<String, Set<String>>> itr =
				conflicts.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Set<String>> entry = itr.next();

				String conflictServletContextName = entry.getKey();
				Set<String> conflictFiles = entry.getValue();

				sb.append("\n\t");
				sb.append(conflictServletContextName);
				sb.append(":");

				for (String conflictFile : conflictFiles) {
					sb.append("\n\t\t");
					sb.append(conflictFile);
				}
			}

			_log.error(sb.toString());

			return;
		}

		installExt(servletContext, event.getContextClassLoader());

		if (_log.isInfoEnabled()) {
			_log.info(
				"Extension environment for " + servletContextName +
					" has been applied. You must reboot the server and " +
						"redeploy all other plugins.");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Extension environment for " +
					servletContextName + " will not be undeployed");
		}
	}

	protected void mergeWebXml(String portalWebDir, String pluginWebDir) {
		if (!FileUtil.exists(
				pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF/web.xml")) {

			return;
		}

		String tmpDir =
			SystemProperties.get(SystemProperties.TMP_DIR) + StringPool.SLASH +
				Time.getTimestamp();

		WebXMLBuilder.main(
			new String[] {
				portalWebDir + "WEB-INF/web.xml",
				pluginWebDir + "WEB-INF/ext-web/docroot/WEB-INF/web.xml",
				tmpDir + "/web.xml"
			});

		File portalWebXml = new File(portalWebDir + "WEB-INF/web.xml");
		File tmpWebXml = new File(tmpDir + "/web.xml");

		tmpWebXml.setLastModified(portalWebXml.lastModified());

		CopyTask.copyFile(
			tmpWebXml, new File(portalWebDir + "WEB-INF"), true, true);

		FileUtil.deltree(tmpDir);
	}

	private static Log _log = LogFactoryUtil.getLog(ExtHotDeployListener.class);

}