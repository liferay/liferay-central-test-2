/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.plugin.PluginPackageImpl;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portal.service.ServiceComponentLocalServiceUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

/**
 * <a href="PluginPackageHotDeployListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Jorge Ferrer
 */
public class PluginPackageHotDeployListener extends BaseHotDeployListener {

	public static PluginPackage readPluginPackage(ServletContext servletContext)
		throws DocumentException, IOException {

		PluginPackage pluginPackage = null;

		String servletContextName = servletContext.getServletContextName();

		String xml = HttpUtil.URLtoString(
			servletContext.getResource("/WEB-INF/liferay-plugin-package.xml"));

		if (_log.isInfoEnabled()) {
			if (servletContextName == null) {
				_log.info("Reading plugin package for the root context");
			}
			else {
				_log.info("Reading plugin package for " + servletContextName);
			}
		}

		if (xml == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Reading plugin package from MANIFEST.MF");
			}

			Attributes attributes = null;

			InputStream is = servletContext.getResourceAsStream(
				"/META-INF/MANIFEST.MF");

			if (is != null) {
				Manifest manifest = new Manifest(is);

				attributes = manifest.getMainAttributes();
			}
			else {
				attributes = new Attributes();
			}

			String artifactGroupId = attributes.getValue(
				"Implementation-Vendor-Id");

			if (Validator.isNull(artifactGroupId)) {
				artifactGroupId = attributes.getValue("Implementation-Vendor");
			}

			if (Validator.isNull(artifactGroupId)) {
				artifactGroupId = GetterUtil.getString(
					attributes.getValue("Bundle-Vendor"), servletContextName);
			}

			String artifactId = attributes.getValue("Implementation-Title");

			if (Validator.isNull(artifactId)) {
				artifactId = GetterUtil.getString(
					attributes.getValue("Bundle-Name"), servletContextName);
			}

			String version = attributes.getValue("Implementation-Version");

			if (Validator.isNull(version)) {
				version = GetterUtil.getString(
					attributes.getValue("Bundle-Version"), Version.UNKNOWN);
			}

			if (version.equals(Version.UNKNOWN) && _log.isWarnEnabled()) {
				_log.warn(
					"Plugin package on context " + servletContextName +
						" cannot be tracked because this WAR does not " +
							"contain a liferay-plugin-package.xml file");
			}

			pluginPackage =
				new PluginPackageImpl(
					artifactGroupId + StringPool.SLASH + artifactId +
						StringPool.SLASH + version + StringPool.SLASH +
							"war");

			pluginPackage.setName(artifactId);

			String shortDescription = attributes.getValue("Bundle-Description");

			if (Validator.isNotNull(shortDescription)) {
				pluginPackage.setShortDescription(shortDescription);
			}

			String pageURL = attributes.getValue("Bundle-DocURL");

			if (Validator.isNotNull(pageURL)) {
				pluginPackage.setPageURL(pageURL);
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Reading plugin package from liferay-plugin-package.xml");
			}

			pluginPackage = PluginPackageUtil.readPluginPackageXml(xml);
		}

		pluginPackage.setContext(servletContextName);

		return pluginPackage;
	}

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(event, "Error registering plugins for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering plugins for ", t);
		}
	}

	protected void destroyServiceComponent(
			ServletContext servletContext, ClassLoader classLoader)
		throws Exception {

		ServiceComponentLocalServiceUtil.destroyServiceComponent(
			servletContext, classLoader);
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		if (servletContext.getResource(
				"/WEB-INF/liferay-theme-loader.xml") != null) {

			return;
		}

		PluginPackage pluginPackage = readPluginPackage(servletContext);

		if (pluginPackage == null) {
			return;
		}

		pluginPackage.setContext(servletContextName);

		event.setPluginPackage(pluginPackage);

		PluginPackageUtil.registerInstalledPluginPackage(pluginPackage);

		ClassLoader portletClassLoader = event.getContextClassLoader();

		initServiceComponent(servletContext, portletClassLoader);

		registerClpMessageListeners(servletContext, portletClassLoader);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Plugin package " + pluginPackage.getModuleId() +
					" registered successfully. It's now ready to be used.");
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		PluginPackage pluginPackage = readPluginPackage(servletContext);

		if (pluginPackage == null) {
			return;
		}

		event.setPluginPackage(pluginPackage);

		PluginPackageUtil.unregisterInstalledPluginPackage(pluginPackage);

		destroyServiceComponent(servletContext, event.getContextClassLoader());

		unregisterClpMessageListeners(servletContext);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Plugin package " + pluginPackage.getModuleId() +
					" unregistered successfully");
		}
	}

	protected void initServiceComponent(
			ServletContext servletContext, ClassLoader classLoader)
		throws Exception {

		Configuration serviceBuilderPropertiesConfiguration = null;

		try {
			serviceBuilderPropertiesConfiguration =
				ConfigurationFactoryUtil.getConfiguration(
					classLoader, "service");
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to read service.properties");
			}

			return;
		}

		Properties serviceBuilderProperties =
			serviceBuilderPropertiesConfiguration.getProperties();

		if (serviceBuilderProperties.size() == 0) {
			return;
		}

		String buildNamespace = GetterUtil.getString(
			serviceBuilderProperties.getProperty("build.namespace"));
		long buildNumber = GetterUtil.getLong(
			serviceBuilderProperties.getProperty("build.number"));
		long buildDate = GetterUtil.getLong(
			serviceBuilderProperties.getProperty("build.date"));
		boolean buildAutoUpgrade = GetterUtil.getBoolean(
			serviceBuilderProperties.getProperty("build.auto.upgrade"), true);

		if (_log.isDebugEnabled()) {
			_log.debug("Build namespace " + buildNamespace);
			_log.debug("Build number " + buildNumber);
			_log.debug("Build date " + buildDate);
			_log.debug("Build auto upgrade " + buildAutoUpgrade);
		}

		if (Validator.isNull(buildNamespace)) {
			return;
		}

		ServiceComponentLocalServiceUtil.initServiceComponent(
			servletContext, classLoader, buildNamespace, buildNumber,
			buildDate, buildAutoUpgrade);
	}

	private static Log _log = LogFactoryUtil.getLog(
		PluginPackageHotDeployListener.class);

}