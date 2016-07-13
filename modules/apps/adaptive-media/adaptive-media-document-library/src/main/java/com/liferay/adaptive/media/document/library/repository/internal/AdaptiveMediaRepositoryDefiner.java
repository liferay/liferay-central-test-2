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

package com.liferay.adaptive.media.document.library.repository.internal;

import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorException;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessorLocator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = RepositoryDefiner.class)
public class AdaptiveMediaRepositoryDefiner
	extends BaseOverridingRepositoryDefiner {

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		super.registerCapabilities(capabilityRegistry);

		capabilityRegistry.addSupportedCapability(
			AdaptiveMediaCapabiliy.class, new AdaptiveMediaCapabiliy());
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaProcessorLocator(
		AdaptiveMediaProcessorLocator processorLocator) {

		_processorLocator = processorLocator;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Activate
	protected void activate() {
		initializeOverridenRepositoryDefiner(_CLASS_NAME);
	}

	@Deactivate
	protected void deactivate() {
		restoreOverridenRepositoryDefiner(_CLASS_NAME);
	}

	private void _deleteAdaptiveMedia(FileEntry fileEntry) {
		try {
			AdaptiveMediaProcessor<FileVersion, ?> processor =
				_processorLocator.locateForClass(FileVersion.class);

			List<FileVersion> fileVersions = fileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);

			for (FileVersion fileVersion : fileVersions) {
				processor.cleanUp(fileVersion);
			}
		}
		catch (AdaptiveMediaProcessorException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	private void _updateAdaptiveMedia(FileEntry fileEntry) {
		try {
			AdaptiveMediaProcessor<FileVersion, ?> processor =
				_processorLocator.locateForClass(FileVersion.class);

			processor.process(fileEntry.getLatestFileVersion(true));
		}
		catch (AdaptiveMediaProcessorException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.repository.liferayrepository.LiferayRepository";

	private AdaptiveMediaProcessorLocator _processorLocator;

	private class AdaptiveMediaCapabiliy
		implements Capability, RepositoryEventAware {

		public void registerRepositoryEventListeners(
			RepositoryEventRegistry repositoryEventRegistry) {

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Add.class, FileEntry.class,
				AdaptiveMediaRepositoryDefiner.this::_updateAdaptiveMedia);

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Update.class, FileEntry.class,
				AdaptiveMediaRepositoryDefiner.this::_updateAdaptiveMedia);

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Delete.class, FileEntry.class,
				AdaptiveMediaRepositoryDefiner.this::_deleteAdaptiveMedia);
		}

	}

}