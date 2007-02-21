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

package com.liferay.portal.events;

import com.liferay.portal.jcr.jackrabbit.JCRFactoryImpl;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.JavaProps;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.log.CommonsLogFactoryImpl;
import com.liferay.portal.security.jaas.PortalConfiguration;
import com.liferay.portal.struts.ActionException;
import com.liferay.portal.struts.SimpleAction;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.velocity.LiferayResourceLoader;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.SystemProperties;
import com.liferay.util.Time;
import com.liferay.util.Validator;

import java.io.File;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import javax.security.auth.login.Configuration;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.NullEnumeration;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="InitAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class InitAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {

		// Set default locale

		String userLanguage = SystemProperties.get("user.language");
		String userCountry = SystemProperties.get("user.country");
		String userVariant = SystemProperties.get("user.variant");

		if (Validator.isNull(userVariant)) {
			Locale.setDefault(new Locale(userLanguage, userCountry));
		}
		else {
			Locale.setDefault(
				new Locale(userLanguage, userCountry, userVariant));
		}

		// Set default time zone

		TimeZone.setDefault(
			TimeZone.getTimeZone(SystemProperties.get("user.timezone")));

		// Shared class loader

		try {
			PortalClassLoaderUtil.setClassLoader(
				Thread.currentThread().getContextClassLoader());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Shared log

		try {
			LogFactoryUtil.setLogFactory(new CommonsLogFactoryImpl());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Log4J

		if (GetterUtil.getBoolean(SystemProperties.get(
				"log4j.configure.on.startup"), true) &&
			!ServerDetector.isSun()) {

			ClassLoader classLoader = getClass().getClassLoader();

			configureLog4J(
				classLoader.getResource("META-INF/portal-log4j.xml"));
			configureLog4J(
				classLoader.getResource("META-INF/portal-log4j-ext.xml"));
		}

		// Java properties

		JavaProps.isJDK5();

		// JAAS

		if ((GetterUtil.getBoolean(PropsUtil.get(
				PropsUtil.PORTAL_CONFIGURATION))) &&
			(ServerDetector.isJBoss() || ServerDetector.isPramati() ||
			 ServerDetector.isSun() || ServerDetector.isWebLogic())) {

			PortalConfiguration portalConfig = new PortalConfiguration(
				Configuration.getConfiguration());

			Configuration.setConfiguration(portalConfig);
		}

		// JCR

		try {
			File repistoryRoot = new File(JCRFactoryImpl.REPOSITORY_ROOT);

			if (!repistoryRoot.exists()) {
				repistoryRoot.mkdirs();

				File tempFile = new File(
					SystemProperties.get(SystemProperties.TMP_DIR) +
						File.separator + Time.getTimestamp());

				String content = StringUtil.read(
					getClass().getClassLoader(),
					"com/liferay/portal/jcr/jackrabbit/dependencies/" +
						"repository.xml");

				FileUtil.write(tempFile, content);

				FileUtil.copyFile(
					tempFile, new File(JCRFactoryImpl.CONFIG_FILE_PATH));

				tempFile.delete();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Velocity

		LiferayResourceLoader.setListeners(PropsUtil.getArray(
			PropsUtil.VELOCITY_ENGINE_RESOURCE_LISTENERS));

		ExtendedProperties props = new ExtendedProperties();

		props.setProperty(RuntimeConstants.RESOURCE_LOADER, "servlet");

		props.setProperty(
			"servlet." + RuntimeConstants.RESOURCE_LOADER + ".class",
			LiferayResourceLoader.class.getName());

		props.setProperty(
			RuntimeConstants.RESOURCE_MANAGER_CLASS,
			PropsUtil.get(PropsUtil.VELOCITY_ENGINE_RESOURCE_MANAGER));

		props.setProperty(
			RuntimeConstants.RESOURCE_MANAGER_CACHE_CLASS,
			PropsUtil.get(PropsUtil.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE));

		props.setProperty(
			"velocimacro.library",
			PropsUtil.get(PropsUtil.VELOCITY_ENGINE_VELOCIMACRO_LIBRARY));

		props.setProperty(
			RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsUtil.VELOCITY_ENGINE_LOGGER));

		props.setProperty(
			"runtime.log.logsystem.log4j.category",
			PropsUtil.get(PropsUtil.VELOCITY_ENGINE_LOGGER_CATEGORY));

		Velocity.setExtendedProperties(props);

		try {
			Velocity.init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void configureLog4J(URL url) {
		if (url == null) {
			return;
		}

		if (Logger.getRootLogger().getAllAppenders() instanceof
				NullEnumeration) {

			DOMConfigurator.configure(url);
		}
		else {
			Set currentLoggerNames = new HashSet();

			Enumeration enu = LogManager.getCurrentLoggers();

			while (enu.hasMoreElements()) {
				Logger logger = (Logger)enu.nextElement();

				currentLoggerNames.add(logger.getName());
			}

			try {
				SAXReader reader = new SAXReader();

				Document doc = reader.read(url);

				Element root = doc.getRootElement();

				Iterator itr = root.elements("category").iterator();

				while (itr.hasNext()) {
					Element category = (Element)itr.next();

					String name = category.attributeValue("name");
					String priority =
						category.element("priority").attributeValue("value");

					Logger logger = Logger.getLogger(name);

					logger.setLevel(Level.toLevel(priority));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}