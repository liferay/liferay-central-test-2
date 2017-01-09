/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.adaptive.media.image.jaxrs.media.test.internal.activator.configuration;

import com.liferay.adaptive.media.demo.data.creator.ImageAdaptiveMediaConfigurationDemoDataCreator;
import com.liferay.document.library.demo.data.creator.FileEntryDemoDataCreator;
import com.liferay.document.library.demo.data.creator.RootFolderDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author Alejandro Hernández
 */
public class TestBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_adaptiveMediaDemoDataCreator = _getService(
			bundleContext,
			ImageAdaptiveMediaConfigurationDemoDataCreator.class);

		_fileEntryDemoDataCreator = _getService(
			bundleContext, FileEntryDemoDataCreator.class);

		_omniAdminUserDemoDataCreator = _getService(
			bundleContext, OmniAdminUserDemoDataCreator.class);

		_rootFolderDemoDataCreator = _getService(
			bundleContext, RootFolderDemoDataCreator.class);

	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {

	}

	private <T> T _getService(BundleContext bundleContext, Class<T> clazz) {
		ServiceReference<T> serviceReference =
			bundleContext.getServiceReference(clazz);

		return bundleContext.getService(serviceReference);
	}

	private ImageAdaptiveMediaConfigurationDemoDataCreator
		_adaptiveMediaDemoDataCreator;
	private FileEntryDemoDataCreator _fileEntryDemoDataCreator;
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;
	private RootFolderDemoDataCreator _rootFolderDemoDataCreator;

}