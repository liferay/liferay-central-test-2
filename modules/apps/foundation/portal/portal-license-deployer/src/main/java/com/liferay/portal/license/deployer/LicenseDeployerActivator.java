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

package com.liferay.portal.license.deployer;

import com.liferay.portal.license.deployer.installer.LicenseInstaller;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.fileinstall.ArtifactInstaller;
import org.apache.felix.fileinstall.ArtifactListener;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class LicenseDeployerActivator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_artifactListenerServiceRegistration = registerArtifactListener(
			bundleContext);
	}

	@Deactivate
	protected void deactivate() {
		_artifactListenerServiceRegistration.unregister();
	}

	protected ServiceRegistration<?> registerArtifactListener(
		BundleContext bundleContext) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("lpkg.deployer.artifact.installer.type", "license");

		return bundleContext.registerService(
			new String[] {
				ArtifactInstaller.class.getName(),
				ArtifactListener.class.getName()
			},
			new LicenseInstaller(), properties);
	}

	private ServiceRegistration<?> _artifactListenerServiceRegistration;

}