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

package com.liferay.arquillian.extension.persistence.internal.instanceproducer;

import com.liferay.portal.test.util.PersistenceTestInitializer;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.spi.ServiceLoader;

/**
 * @author Cristina González
 */
public class ExtensionInstanceProducer {

	public void createInstanceProducer(
		@Observes ArquillianDescriptor arquillianDescriptor) {

		ServiceLoader serviceLoader = _serviceLoaderInstance.get();

		_persistenceTestInitializerInstanceProducer.set(
			serviceLoader.onlyOne(PersistenceTestInitializer.class));
	}

	@Inject
	private Instance<Injector> _injectorInstance;

	@ApplicationScoped
	@Inject
	private InstanceProducer<PersistenceTestInitializer>
		_persistenceTestInitializerInstanceProducer;

	@Inject
	private Instance<ServiceLoader> _serviceLoaderInstance;

}