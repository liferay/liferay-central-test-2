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

package com.liferay.portal.spring.util;

import com.liferay.portal.bean.BeanLocatorImpl;
import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.spring.context.ArrayApplicationContext;
import com.liferay.portal.util.PropsValues;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;

/**
 * <a href="SpringUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * In most cases, SpringUtil.setContext() would have been called by
 * com.liferay.portal.spring.context.PortalContextLoaderListener, configured in
 * web.xml for the web application. However, there will be times in which
 * SpringUtil will be called in a non-web application and, therefore, require
 * manual instantiation of the application context.
 * </p>
 *
 * @author Michael Young
 */
public class SpringUtil {

	public static void loadContext() {
		List<String> configLocations = ListUtil.fromArray(
			PropsValues.SPRING_CONFIGS);

		if (PropsValues.PERSISTENCE_PROVIDER.equalsIgnoreCase("jpa")) {
			configLocations.remove("META-INF/hibernate-spring.xml");
		}
		else {
			configLocations.remove("META-INF/jpa-spring.xml");
		}

		AbstractApplicationContext applicationContext =
			new ArrayApplicationContext(
				configLocations.toArray(new String[configLocations.size()]));

		applicationContext.registerShutdownHook();

		BeanLocator beanLocator = new BeanLocatorImpl(
			PortalClassLoaderUtil.getClassLoader(), applicationContext);

		PortalBeanLocatorUtil.setBeanLocator(beanLocator);
	}

}