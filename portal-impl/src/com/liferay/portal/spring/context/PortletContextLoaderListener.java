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

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;

import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <a href="PortletContextLoaderListener.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @see	   PortletApplicationContext
 * @see	   PortletContextLoader
 */
public class PortletContextLoaderListener extends ContextLoaderListener {

	public void contextDestroyed(ServletContextEvent event) {
		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		ServletContext servletContext = event.getServletContext();

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {null});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), null);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		super.contextDestroyed(event);
	}

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		ClassLoader classLoader = PortletClassLoaderUtil.getClassLoader();

		ServletContext servletContext = event.getServletContext();

		ApplicationContext applicationContext =
			WebApplicationContextUtils.getWebApplicationContext(servletContext);

		BeanLocator beanLocator = new BeanLocatorImpl(
			classLoader, applicationContext);

		try {
			Class<?> beanLocatorUtilClass = Class.forName(
				"com.liferay.util.bean.PortletBeanLocatorUtil", true,
				classLoader);

			Method setBeanLocatorMethod = beanLocatorUtilClass.getMethod(
				"setBeanLocator", new Class[] {BeanLocator.class});

			setBeanLocatorMethod.invoke(
				beanLocatorUtilClass, new Object[] {beanLocator});

			PortletBeanLocatorUtil.setBeanLocator(
				servletContext.getServletContextName(), beanLocator);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		servletContext.removeAttribute(
			WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

	protected ContextLoader createContextLoader() {
		return new PortletContextLoader();
	}

	private static Log _log =
		LogFactoryUtil.getLog(PortletContextLoaderListener.class);

}