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

package com.liferay.portal.json.web.service.extender.internal;

import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManager;
import com.liferay.portal.util.ClassLoaderUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true, service = JSONWebServiceTracker.class)
public class JSONWebServiceTracker
	implements ServiceTrackerCustomizer<Object, Object> {

	@Activate
	public void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		BundleContext bundleContext = componentContext.getBundleContext();

		try {
			_serviceTracker = new ServiceTracker<Object, Object>(
				bundleContext,
				bundleContext.createFilter(
					"(&(json.web.service.context.name=*)(json.web.service." +
						"context.path=*))"),
					this);

			_serviceTracker.open();
		}
		catch (InvalidSyntaxException ise) {
			throw new RuntimeException(
				"Unable to activate Liferay Portal JSON Web Service Tracker",
				ise);
		}
	}

	@Override
	public Object addingService(ServiceReference<Object> serviceReference) {
		return registerService(serviceReference);
	}

	@Deactivate
	public void deactivate() {
		_componentContext = null;

		_serviceTracker.close();

		_serviceTracker = null;
	}

	@Override
	public void modifiedService(
		ServiceReference<Object> serviceReference, Object service) {

		unregisterService(service);

		registerService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<Object> serviceReference, Object service) {

		unregisterService(service);
	}

	protected ClassLoader getBundleClassLoader(Bundle bundle) {
		BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

		return bundleWiring.getClassLoader();
	}

	protected Object getService(ServiceReference<Object> serviceReference) {
		BundleContext bundleContext = _componentContext.getBundleContext();

		return bundleContext.getService(serviceReference);
	}

	protected Object registerService(
		ServiceReference<Object> serviceReference) {

		String contextName = (String)serviceReference.getProperty(
			"json.web.service.context.name");
		String contextPath = (String)serviceReference.getProperty(
			"json.web.service.context.path");
		Object service = getService(serviceReference);

		ClassLoader contextClassLoader =
			ClassLoaderUtil.getContextClassLoader();

		ClassLoader classLoader = getBundleClassLoader(
			serviceReference.getBundle());

		ClassLoaderUtil.setContextClassLoader(classLoader);

		try {
			_jsonWebServiceActionsManager.registerService(
				contextName, contextPath, service);
		}
		finally {
			ClassLoaderUtil.setContextClassLoader(contextClassLoader);
		}

		return service;
	}

	@Reference
	protected void setJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = jsonWebServiceActionsManager;
	}

	protected void unregisterService(Object service) {
		_jsonWebServiceActionsManager.unregisterJSONWebServiceActions(service);
	}

	protected void unsetJSONWebServiceActionsManager(
		JSONWebServiceActionsManager jsonWebServiceActionsManager) {

		_jsonWebServiceActionsManager = null;
	}

	private ComponentContext _componentContext;
	private JSONWebServiceActionsManager _jsonWebServiceActionsManager;
	private ServiceTracker<Object, Object> _serviceTracker;

}