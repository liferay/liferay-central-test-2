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

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.bean.ContextClassLoaderBeanHandler;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * <a href="BaseHotDeployListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class BaseHotDeployListener implements HotDeployListener {

	public void throwHotDeployException(
			HotDeployEvent event, String msg, Throwable t)
		throws HotDeployException {

		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		throw new HotDeployException(msg + servletContextName, t);
	}

	protected Object newInstance(
			ClassLoader portletClassLoader, Class<?> interfaceClass,
			String implClassName)
		throws Exception {

		Object instance = portletClassLoader.loadClass(
			implClassName).newInstance();

		return Proxy.newProxyInstance(
			portletClassLoader, new Class[] {interfaceClass},
			new ContextClassLoaderBeanHandler(instance, portletClassLoader));
	}

	protected void registerClpMessageListeners(
			ServletContext servletContext, ClassLoader portletClassLoader)
		throws Exception {

		List<MessageListener> clpMessageListeners =
			(List<MessageListener>)servletContext.getAttribute(
				WebKeys.CLP_MESSAGE_LISTENERS);

		if (clpMessageListeners != null) {
			return;
		}

		clpMessageListeners = new ArrayList<MessageListener>();

		Set<String> classNames = ServletContextUtil.getClassNames(
			servletContext);

		for (String className : classNames) {
			if (className.endsWith(".ClpMessageListener")) {
				Class<?> clpMessageListenerClass = portletClassLoader.loadClass(
					className);

				MessageListener clpMessageListener =
					(MessageListener)clpMessageListenerClass.newInstance();

				Field servletContextNameField =
					clpMessageListenerClass.getField(
						"SERVLET_CONTEXT_NAME");

				String clpServletContextName = servletContextNameField.get(
					clpMessageListener).toString();

				if (clpServletContextName.equals(
						servletContext.getServletContextName())) {

					continue;
				}

				clpMessageListeners.add(clpMessageListener);

				MessageBusUtil.registerMessageListener(
					DestinationNames.HOT_DEPLOY, clpMessageListener);
			}
		}

		servletContext.setAttribute(
			WebKeys.CLP_MESSAGE_LISTENERS, clpMessageListeners);
	}

	protected void unregisterClpMessageListeners(ServletContext servletContext)
		throws Exception {

		List<MessageListener> clpMessageListeners =
			(List<MessageListener>)servletContext.getAttribute(
				WebKeys.CLP_MESSAGE_LISTENERS);

		if (clpMessageListeners != null) {
			servletContext.removeAttribute(WebKeys.CLP_MESSAGE_LISTENERS);

			for (MessageListener clpMessageListener : clpMessageListeners) {
				MessageBusUtil.unregisterMessageListener(
					DestinationNames.HOT_DEPLOY, clpMessageListener);
			}
		}
	}

}