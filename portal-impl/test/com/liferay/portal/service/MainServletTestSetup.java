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

package com.liferay.portal.service;

import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.util.TestPropsValues;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * <a href="MainServletTestSetup.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class MainServletTestSetup extends TestSetup {

	public MainServletTestSetup(Test test) {
		super(test);
	}

	public void setUp() throws Exception {
		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();

		MockServletContext mockServletContext = new MockServletContext(
			TestPropsValues.RESOURCE_BASE_PATH, new FileSystemResourceLoader());

		MockServletConfig mockServletConfig = new MockServletConfig(
			mockServletContext);

		_mainServlet = new MainServlet(mockServletConfig);

		_mainServlet.init();
	}

	public void tearDown() throws Exception {
		_mainServlet.destroy();

		ServiceTestUtil.destroyServices();
	}

	private MainServlet _mainServlet;

}