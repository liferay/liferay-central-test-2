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
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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

		long companyId = TestPropsValues.getCompanyId();

		long groupId = TestPropsValues.getGroupId();

		try (DestinationReplacer destinationReplacer = new DestinationReplacer(
				"liferay/adaptive_media_processor")) {

			User user = _omniAdminUserDemoDataCreator.create(
				companyId, "alejandro.hernandez@liferay.com");

			Folder nonAdaptiveMediaFolder = _rootFolderDemoDataCreator.create(
				user.getUserId(), groupId, "Non Adaptive Media");

			for (int i = 0; i < 5; i++) {
				String fileName = String.format("image-without-%d.jpeg", i);

				_fileEntryDemoDataCreator.create(
					user.getUserId(), nonAdaptiveMediaFolder.getFolderId(),
					fileName);
			}

			_adaptiveMediaDemoDataCreator.create(companyId);

			Folder adaptiveMediaFolder = _rootFolderDemoDataCreator.create(
				user.getUserId(), groupId, "Adaptive Media");

			for (int i = 0; i < 2; i++) {
				String fileName = String.format("image-with-%d.jpeg", i);

				_fileEntryDemoDataCreator.create(
					user.getUserId(), adaptiveMediaFolder.getFolderId(),
					fileName);
			}
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_fileEntryDemoDataCreator.delete();
		_rootFolderDemoDataCreator.delete();
		_adaptiveMediaDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
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

	private class DestinationReplacer implements AutoCloseable {

		public DestinationReplacer(String destinationName) {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			_destination = messageBus.getDestination(destinationName);

			SynchronousDestination synchronousDestination =
				new SynchronousDestination();

			synchronousDestination.setName(destinationName);

			messageBus.replace(synchronousDestination);
		}

		@Override
		public void close() throws Exception {
			MessageBus messageBus = MessageBusUtil.getMessageBus();

			messageBus.replace(_destination);
		}

		private final Destination _destination;

	}

}