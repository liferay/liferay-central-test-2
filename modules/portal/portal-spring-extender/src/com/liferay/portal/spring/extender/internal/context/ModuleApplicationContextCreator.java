/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.extender.internal.context;

import com.liferay.portal.spring.extender.internal.blueprint.ModuleBeanFactoryPostProcessor;
import com.liferay.portal.spring.extender.internal.bundle.CompositeResourceLoaderBundle;
import com.liferay.portal.spring.extender.internal.classloader.BundleResolverClassLoader;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import org.eclipse.gemini.blueprint.context.DelegatedExecutionOsgiBundleApplicationContext;
import org.eclipse.gemini.blueprint.context.support.OsgiBundleXmlApplicationContext;
import org.eclipse.gemini.blueprint.extender.OsgiApplicationContextCreator;
import org.eclipse.gemini.blueprint.extender.support.internal.ConfigUtils;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.springframework.context.ApplicationContext;

/**
 * @author Miguel Pastor
 */
public class ModuleApplicationContextCreator
	implements OsgiApplicationContextCreator {

	@Override
	public DelegatedExecutionOsgiBundleApplicationContext
			createApplicationContext(BundleContext bundleContext)
		throws Exception {

		Bundle bundle = bundleContext.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders();

		String configs = headers.get("Spring-Context");

		if (configs == null) {
			return null;
		}

		Bundle extenderBundle = _getExtenderBundle();

		ClassLoader classLoader = new BundleResolverClassLoader(
			bundle, extenderBundle);

		Bundle compositeResourceLoaderBundle =
			new CompositeResourceLoaderBundle(bundle, extenderBundle);

		ServiceBuilderApplicationContext serviceBuilderApplicationContext =
			new ServiceBuilderApplicationContext(
				compositeResourceLoaderBundle, _buildLocations(headers));

		serviceBuilderApplicationContext.setBundleContext(bundleContext);

		serviceBuilderApplicationContext.addBeanFactoryPostProcessor(
			new ModuleBeanFactoryPostProcessor(classLoader));

		return serviceBuilderApplicationContext;
	}

	private String[] _buildLocations(Dictionary<String, String> headers) {
		List<String> locations = new ArrayList<String>();

		Collections.addAll(locations, ConfigUtils.getHeaderLocations(headers));

		String liferayService = headers.get("Liferay-Service");

		if (liferayService != null) {
			locations.add(0, "META-INF/spring/parent/*.xml");
		}

		return locations.toArray(new String[locations.size()]);
	}

	private ApplicationContext _buildParentContext(
			Bundle bundle, ClassLoader classLoader)
		throws IOException {

		OsgiBundleXmlApplicationContext osgiBundleXmlApplicationContext =
			new OsgiBundleXmlApplicationContext(
				new String[] {"META-INF/spring/parent/*.xml"});

		osgiBundleXmlApplicationContext.setBundleContext(
			bundle.getBundleContext());
		osgiBundleXmlApplicationContext.setClassLoader(classLoader);

		osgiBundleXmlApplicationContext.refresh();

		return osgiBundleXmlApplicationContext;
	}

	private Bundle _getExtenderBundle() {
		return FrameworkUtil.getBundle(ModuleApplicationContextCreator.class);
	}

}