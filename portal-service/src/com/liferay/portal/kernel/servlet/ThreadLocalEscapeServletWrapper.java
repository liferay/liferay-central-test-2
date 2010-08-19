/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Shuyang Zhou
 */
public class ThreadLocalEscapeServletWrapper implements Servlet {

	public void init(ServletConfig servletConfig) throws ServletException {
		String className = servletConfig.getInitParameter("class-name");
		if (className == null) {
			throw new IllegalArgumentException(
				"class-name init parameter is missing.");
		}
		try {
			Class<?> servletClass =
				Thread.currentThread().getContextClassLoader().loadClass(
					className);
			if (!Servlet.class.isAssignableFrom(servletClass)) {
				throw new ServletException(
					className + " is not a sub class of javax.servlet.Servlet");
			}
			_servlet = (Servlet) servletClass.newInstance();
		}
		catch (ClassNotFoundException cnfe) {
			throw new ServletException(
				"Unable to load decorating servlet class", cnfe);
		}
		catch (Exception e) {
			throw new ServletException(
				"Fail to create an instance for " + className, e);
		}
		InitThread initThread = new InitThread(servletConfig);
		initThread.start();
		try {
			initThread.join();
		} catch (InterruptedException ie) {
			throw new ServletException("Interrupted in initialization", ie);
		}

		Throwable t = initThread.getInitializeThrowable();
		if (t != null) {
			if (t instanceof ServletException) {
				throw (ServletException)t;
			}
			else {
				throw new ServletException(
					"Error occured during initialization", t);
			}
		}
	}

	public ServletConfig getServletConfig() {
		return _servlet.getServletConfig();
	}

	public void service(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws ServletException, IOException {
		_servlet.service(servletRequest, servletResponse);
	}

	public String getServletInfo() {
		return _servlet.getServletInfo();
	}

	public void destroy() {
		DestroyThread destroyThread = new DestroyThread();
		destroyThread.start();
		try {
			destroyThread.join();
		}
		catch (InterruptedException ie) {
			throw new RuntimeException("Interrupted in destroy", ie);
		}

		Throwable t = destroyThread.getDestroyThrowable();
		if (t != null) {
			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}
			else {
				throw new RuntimeException("Error occured during destroy", t);
			}
		}
	}

	private Servlet _servlet;

	private class InitThread extends Thread {

		public InitThread(ServletConfig servletConfig) {
			_servletConfig = servletConfig;
			setDaemon(true);
		}

		public void run() {
			try {
				_servlet.init(_servletConfig);
			}
			catch (Throwable t) {
				_initializeThrowable = t;
			}
		}

		public Throwable getInitializeThrowable() {
			return _initializeThrowable;
		}

		private ServletConfig _servletConfig;
		private Throwable _initializeThrowable;

	}

	private class DestroyThread extends Thread {

		public DestroyThread() {
			setDaemon(true);
		}

		public void run() {
			try {
				_servlet.destroy();
			}
			catch (Throwable t) {
				_destroyThrowable = t;
			}
		}

		public Throwable getDestroyThrowable() {
			return _destroyThrowable;
		}

		private Throwable _destroyThrowable;

	}

}