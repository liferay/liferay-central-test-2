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

package com.liferay.wiki.engine.impl;

import com.liferay.wiki.engine.WikiEngine;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Iv�n Zaera
 */
@Component(
	immediate = true, service = WikiEngineTracker.class
)
public class WikiEngineTracker {

	public Collection<String> getFormats() {
		return _serviceReferences.keySet();
	}

	public String getProperty(String format, String key) {
		ServiceReference<WikiEngine> serviceReference = _serviceReferences.get(
			format);

		return (String)serviceReference.getProperty(key);
	}

	public WikiEngine getWikiEngine(String format) {
		return _bundleContext.getService(_serviceReferences.get(format));
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY, service = WikiEngine.class,
		target = "(enabled=true)", unbind = "removedService",
		updated = "modifiedService"
	)
	protected void addingService(
		ServiceReference<WikiEngine> serviceReference) {

		String format = (String)serviceReference.getProperty("format");

		_serviceReferences.put(format, serviceReference);
	}

	protected void modifiedService(
		ServiceReference<WikiEngine> serviceReference) {

		removedService(serviceReference);

		addingService(serviceReference);
	}

	protected void removedService(
		ServiceReference<WikiEngine> serviceReference) {

		String format = (String)serviceReference.getProperty("format");

		_serviceReferences.remove(format);
	}

	private BundleContext _bundleContext;
	private final ConcurrentMap<String, ServiceReference<WikiEngine>>
		_serviceReferences = new ConcurrentHashMap<>();

}