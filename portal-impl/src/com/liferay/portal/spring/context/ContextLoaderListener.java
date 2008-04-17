/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.spring.util.SpringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <a href="ContextLoaderListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class ContextLoaderListener extends
		org.springframework.web.context.ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		// Preinitialize Spring beans. See LEP-4734.

		ServletContext sc = event.getServletContext();

		ApplicationContext context =
			WebApplicationContextUtils.getWebApplicationContext(sc);

		SpringUtil.setContext(context);

		String[] beanDefinitionNames = context.getBeanDefinitionNames();

		for (int i = 0; i < beanDefinitionNames.length; i++) {
			String beanDefinitionName = beanDefinitionNames[i];

			BeanLocatorUtil.locate(beanDefinitionName, false);
		}
	}

}