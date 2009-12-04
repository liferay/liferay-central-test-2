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
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.tools.WebXMLBuilder;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.ant.CopyTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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

		String jarFullName = "/WEB-INF/" + jarName + "/" + jarName + ".jar";

		InputStream is = servletContext.getResourceAsStream(jarFullName);

		if (is == null) {
			throw new HotDeployException(jarFullName + " does not exist");
		}

		StreamUtil.transfer(
			is, new FileOutputStream(new File(dir + jarName + ".jar")));
	}

	protected void installExt(
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		String globalLibDir = PortalUtil.getGlobalLibDir();
		String portalWebDir = PortalUtil.getPortalWebDir();
		String portalLibDir = PortalUtil.getPortalLibDir();
		String pluginWebDir = WebDirDetector.getRootDir(portletClassLoader);

		if (FileUtil.exists(portalWebDir + _EXT_DEPLOYMENT)) {
			String extServletContextName = FileUtil.read(
				portalWebDir + _EXT_DEPLOYMENT);

			if (!extServletContextName.equals(servletContextName)) {
				throw new HotDeployException(
					"Not installing extension environment " +
						servletContextName + " because " +
							extServletContextName + " is already installed");
			}
		}

		copyJar(servletContext, globalLibDir, "ext-service");
		copyJar(servletContext, portalLibDir, "ext-impl");
		copyJar(servletContext, portalLibDir, "ext-util-bridges");
		copyJar(servletContext, portalLibDir, "ext-util-java");
		copyJar(servletContext, portalLibDir, "ext-util-taglib");

		CopyTask.copyDirectory(
			pluginWebDir + "WEB-INF/ext-web/docroot", portalWebDir,
			StringPool.BLANK, "**/WEB-INF/web.xml", true, false);

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

		FileUtil.write(portalWebDir + _EXT_DEPLOYMENT, servletContextName);
		FileUtil.write(pluginWebDir + _EXT_DEPLOYMENT, Time.getTimestamp());
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Registering extension environment for " + servletContextName);
		}

		InputStream is = servletContext.getResourceAsStream(_EXT_DEPLOYMENT);

		if (is != null) {
			is.close();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Extension environment for " + servletContextName +
						" has been applied. No reboot or redeploy of plugins " +
							"is required.");
			}
		}
		else {
			installExt(servletContext, event.getContextClassLoader());

			if (_log.isInfoEnabled()) {
				_log.info(
					"Extension environment for " + servletContextName +
						" has been applied. You must reboot the server and " +
							"redeploy all other plugins.");
			}
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

	private static final String _EXT_DEPLOYMENT = "WEB-INF/ext-deployment";

	private static Log _log = LogFactoryUtil.getLog(ExtHotDeployListener.class);

}