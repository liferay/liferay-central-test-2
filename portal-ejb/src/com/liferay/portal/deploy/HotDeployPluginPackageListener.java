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

package com.liferay.portal.deploy;

import com.liferay.portal.kernel.deploy.HotDeployEvent;
import com.liferay.portal.kernel.deploy.HotDeployException;
import com.liferay.portal.kernel.deploy.HotDeployListener;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.Http;
import com.liferay.util.xml.XMLSafeReader;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="HotDeployPluginPackageListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 *
 */
public class HotDeployPluginPackageListener implements HotDeployListener {

	public static PluginPackage readPluginPackage(ServletContext ctx)
		throws DocumentException, IOException {

		String servletContextName =  ctx.getServletContextName();

		String xml = Http.URLtoString(ctx.getResource(
			"/WEB-INF/liferay-plugin-package.xml"));

		if (xml == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Plugin package on context " + servletContextName +
						" cannot be tracked because this WAR does not " +
							"contain a liferay-plugin-package.xml file");
			}

			return null;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Reading plugin package for " + servletContextName);
		}

		SAXReader reader = SAXReaderFactory.getInstance(false);

		Document doc = reader.read(new XMLSafeReader(xml));

		Element root = doc.getRootElement();

		PluginPackage pluginPackage =
			PluginPackageUtil.readPluginPackageXml(root);

		return pluginPackage;
	}

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		String servletContextName = null;

		try {
			ServletContext ctx = event.getServletContext();

			servletContextName = ctx.getServletContextName();

			if (_log.isDebugEnabled()) {
				_log.debug("Invoking deploy for " + servletContextName);
			}

			PluginPackage pluginPackage = readPluginPackage(ctx);

			if (pluginPackage != null) {
				event.setPluginPackage(pluginPackage);

				PluginPackageUtil.registerPluginPackage(pluginPackage);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Plugin package " + pluginPackage.getModuleId() +
							" registered successfully");
				}
			}
		}
		catch (Exception e) {
			throw new HotDeployException(
				"Error registering plugins for " + servletContextName,
				e);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		String servletContextName = null;

		try {
			ServletContext ctx = event.getServletContext();

			servletContextName = ctx.getServletContextName();

			if (_log.isDebugEnabled()) {
				_log.debug("Invoking deploy for " + servletContextName);
			}

			PluginPackage pluginPackage = readPluginPackage(ctx);

			if (pluginPackage != null) {
				event.setPluginPackage(pluginPackage);

				PluginPackageUtil.unregisterPluginPackage(pluginPackage);

				if (_log.isInfoEnabled()) {
					_log.info(
						"Plugin package " + pluginPackage.getModuleId() +
							" unregistered successfully");
				}
			}
		}
		catch (Exception e) {
			throw new HotDeployException(
				"Error unregistering plugins for " + servletContextName,
				e);
		}
	}

	private static Log _log =
		LogFactory.getLog(HotDeployPluginPackageListener.class);

}