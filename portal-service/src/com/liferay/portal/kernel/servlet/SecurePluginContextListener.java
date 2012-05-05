/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Brian Wing Shun Chan
 */
public class SecurePluginContextListener
	extends PluginContextListener
	implements HttpSessionActivationListener, HttpSessionAttributeListener,
			   HttpSessionBindingListener, HttpSessionListener,
			   ServletRequestAttributeListener, ServletRequestListener {

	public void attributeAdded(
		HttpSessionBindingEvent httpSessionBindingEvent) {

		if (_httpSessionAttributeListeners == null) {
			return;
		}

		for (HttpSessionAttributeListener httpSessionAttributeListener :
				_httpSessionAttributeListeners) {

			httpSessionAttributeListener.attributeAdded(
				httpSessionBindingEvent);
		}
	}

	public void attributeAdded(
		ServletRequestAttributeEvent servletRequestAttributeEvent) {

		if (_servletRequestAttributeListeners == null) {
			return;
		}

		for (ServletRequestAttributeListener servletRequestAttributeListener :
				_servletRequestAttributeListeners) {

			servletRequestAttributeListener.attributeAdded(
				servletRequestAttributeEvent);
		}
	}

	public void attributeRemoved(
		HttpSessionBindingEvent httpSessionBindingEvent) {

		if (_httpSessionAttributeListeners == null) {
			return;
		}

		for (HttpSessionAttributeListener httpSessionAttributeListener :
				_httpSessionAttributeListeners) {

			httpSessionAttributeListener.attributeRemoved(
				httpSessionBindingEvent);
		}

	}

	public void attributeRemoved(
		ServletRequestAttributeEvent servletRequestAttributeEvent) {

		if (_servletRequestAttributeListeners == null) {
			return;
		}

		for (ServletRequestAttributeListener servletRequestAttributeListener :
				_servletRequestAttributeListeners) {

			servletRequestAttributeListener.attributeRemoved(
				servletRequestAttributeEvent);
		}
	}

	public void attributeReplaced(
		HttpSessionBindingEvent httpSessionBindingEvent) {

		if (_httpSessionAttributeListeners == null) {
			return;
		}

		for (HttpSessionAttributeListener httpSessionAttributeListener :
				_httpSessionAttributeListeners) {

			httpSessionAttributeListener.attributeReplaced(
				httpSessionBindingEvent);
		}
	}

	public void attributeReplaced(
		ServletRequestAttributeEvent servletRequestAttributeEvent) {

		if (_servletRequestAttributeListeners == null) {
			return;
		}

		for (ServletRequestAttributeListener servletRequestAttributeListener :
				_servletRequestAttributeListeners) {

			servletRequestAttributeListener.attributeReplaced(
				servletRequestAttributeEvent);
		}
	}

	public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
		if (_servletRequestListeners == null) {
			return;
		}

		for (ServletRequestListener servletRequestListener :
				_servletRequestListeners) {

			servletRequestListener.requestDestroyed(servletRequestEvent);
		}
	}

	public void requestInitialized(ServletRequestEvent servletRequestEvent) {
		if (_servletRequestListeners == null) {
			return;
		}

		for (ServletRequestListener servletRequestListener :
				_servletRequestListeners) {

			servletRequestListener.requestInitialized(servletRequestEvent);
		}
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		if (_httpSessionListeners == null) {
			return;
		}

		for (HttpSessionListener httpSessionListener : _httpSessionListeners) {
			httpSessionListener.sessionCreated(httpSessionEvent);
		}
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		if (_httpSessionListeners == null) {
			return;
		}

		for (HttpSessionListener httpSessionListener : _httpSessionListeners) {
			httpSessionListener.sessionDestroyed(httpSessionEvent);
		}
	}

	public void sessionDidActivate(HttpSessionEvent httpSessionEvent) {
		if (_httpSessionActivationListeners == null) {
			return;
		}

		for (HttpSessionActivationListener httpSessionActivationListener :
				_httpSessionActivationListeners) {

			httpSessionActivationListener.sessionDidActivate(httpSessionEvent);
		}
	}

	public void sessionWillPassivate(HttpSessionEvent httpSessionEvent) {
		if (_httpSessionActivationListeners == null) {
			return;
		}

		for (HttpSessionActivationListener httpSessionActivationListener :
				_httpSessionActivationListeners) {

			httpSessionActivationListener.sessionWillPassivate(
				httpSessionEvent);
		}
	}

	public void valueBound(HttpSessionBindingEvent httpSessionBindingEvent) {
		if (_httpSessionBindingListeners == null) {
			return;
		}

		for (HttpSessionBindingListener httpSessionBindingListener :
				_httpSessionBindingListeners) {

			httpSessionBindingListener.valueBound(httpSessionBindingEvent);
		}
	}

	public void valueUnbound(HttpSessionBindingEvent httpSessionBindingEvent) {
		if (_httpSessionBindingListeners == null) {
			return;
		}

		for (HttpSessionBindingListener httpSessionBindingListener :
				_httpSessionBindingListeners) {

			httpSessionBindingListener.valueUnbound(httpSessionBindingEvent);
		}
	}

	@Override
	protected void fireDeployEvent() {
		HotDeployUtil.fireDeployEvent(
			new HotDeployEvent(servletContext, pluginClassLoader));

		super.fireDeployEvent();

		if (_servletContextListeners != null) {
			ServletContextEvent servletContextEvent = new ServletContextEvent(
				servletContext);

			for (ServletContextListener servletContextListener :
					_servletContextListeners) {

				servletContextListener.contextInitialized(servletContextEvent);
			}
		}
	}

	@Override
	protected void fireUndeployEvent() {
		if (_servletContextListeners != null) {
			ServletContextEvent servletContextEvent = new ServletContextEvent(
				servletContext);

			for (ServletContextListener servletContextListener :
					_servletContextListeners) {

				servletContextListener.contextDestroyed(servletContextEvent);
			}
		}

		super.fireUndeployEvent();
	}

	protected void instantiatingListener(String listenerClassName)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Instantiating " + listenerClassName);
		}

		Object listener = InstanceFactory.newInstance(
			pluginClassLoader, listenerClassName);

		if (listener instanceof HttpSessionActivationListener) {
			if (_httpSessionActivationListeners == null) {
				_httpSessionActivationListeners =
					new CopyOnWriteArrayList<HttpSessionActivationListener>();
			}

			_httpSessionActivationListeners.add(
				(HttpSessionActivationListener)listener);
		}

		if (listener instanceof HttpSessionAttributeListener) {
			if (_httpSessionAttributeListeners == null) {
				_httpSessionAttributeListeners =
					new CopyOnWriteArrayList<HttpSessionAttributeListener>();
			}

			_httpSessionAttributeListeners.add(
				(HttpSessionAttributeListener)listener);
		}

		if (listener instanceof HttpSessionBindingListener) {
			if (_httpSessionBindingListeners == null) {
				_httpSessionBindingListeners =
					new CopyOnWriteArrayList<HttpSessionBindingListener>();
			}

			_httpSessionBindingListeners.add(
				(HttpSessionBindingListener)listener);
		}

		if (listener instanceof HttpSessionListener) {
			if (_httpSessionListeners == null) {
				_httpSessionListeners =
					new CopyOnWriteArrayList<HttpSessionListener>();
			}

			_httpSessionListeners.add((HttpSessionListener)listener);
		}

		if (listener instanceof ServletContextListener) {
			if (_servletContextListeners == null) {
				_servletContextListeners =
					new CopyOnWriteArrayList<ServletContextListener>();
			}

			_servletContextListeners.add((ServletContextListener)listener);
		}

		if (listener instanceof ServletRequestAttributeListener) {
			if (_servletRequestAttributeListeners == null) {
				_servletRequestAttributeListeners =
					new CopyOnWriteArrayList<ServletRequestAttributeListener>();
			}

			_servletRequestAttributeListeners.add(
				(ServletRequestAttributeListener)listener);
		}

		if (listener instanceof ServletRequestListener) {
			if (_servletRequestListeners == null) {
				_servletRequestListeners =
					new CopyOnWriteArrayList<ServletRequestListener>();
			}

			_servletRequestListeners.add((ServletRequestListener)listener);
		}
	}

	protected void instantiatingListeners() throws Exception {
		String[] listenerClassNames = StringUtil.split(
			servletContext.getInitParameter("portalListenerClasses"));

		for (String listenerClassName : listenerClassNames) {
			instantiatingListener(listenerClassName);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SecurePluginContextListener.class);

	private List<HttpSessionActivationListener> _httpSessionActivationListeners;
	private List<HttpSessionAttributeListener> _httpSessionAttributeListeners;
	private List<HttpSessionBindingListener> _httpSessionBindingListeners;
	private List<HttpSessionListener> _httpSessionListeners;
	private List<ServletContextListener> _servletContextListeners;
	private List<ServletRequestAttributeListener>
		_servletRequestAttributeListeners;
	private List<ServletRequestListener> _servletRequestListeners;

}