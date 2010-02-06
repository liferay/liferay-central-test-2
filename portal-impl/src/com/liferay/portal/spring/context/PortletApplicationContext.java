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

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.spring.util.FilterClassLoader;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * <a href="PortletApplicationContext.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This web application context will first load bean definitions in the
 * portalContextConfigLocation parameter in web.xml. Then, the context will load
 * bean definitions specified by the property "spring.configs" in
 * service.properties.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see	   PortletContextLoader
 * @see	   PortletContextLoaderListener
 */
public class PortletApplicationContext extends XmlWebApplicationContext {

	protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
		ClassLoader beanClassLoader =
			AggregateClassLoader.getAggregateClassLoader(
				new ClassLoader[] {
					PortletClassLoaderUtil.getClassLoader(),
					PortalClassLoaderUtil.getClassLoader()
				});

		beanClassLoader = new FilterClassLoader(beanClassLoader);

		reader.setBeanClassLoader(beanClassLoader);
	}

	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) {
		String[] configLocations = getPortletConfigLocations();

		if (configLocations == null) {
			return;
		}

		for (String configLocation : configLocations) {
			try {
				reader.loadBeanDefinitions(configLocation);
			}
			catch (Exception e) {
				Throwable cause = e.getCause();

				if (cause instanceof FileNotFoundException) {
					if (_log.isWarnEnabled()) {
						_log.warn(cause.getMessage());
					}
				}
				else {
					_log.error(e, e);
				}
			}
		}
	}

	protected String[] getPortletConfigLocations() {
		String[] configLocations = getConfigLocations();

		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

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

			return configLocations;
		}

		return ArrayUtil.append(
			configLocations,
			serviceBuilderPropertiesConfiguration.getArray(
				PropsKeys.SPRING_CONFIGS));
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortletApplicationContext.class);

}