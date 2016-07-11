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

package com.liferay.adaptive.media.document.library.repository;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

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

	@Activate
	protected void activate() {
		initializeOverridenRepositoryDefiner(_CLASS_NAME);
	}

	@Deactivate
	protected void deactivate() {
		restoreOverridenRepositoryDefiner(_CLASS_NAME);
	}

	private void _createAdaptiveImages(FileEntry fileEntry) {
	}

	private void _deleteAdaptiveImages(FileEntry fileEntry) {
	}

	private void _updateAdaptiveImages(FileEntry fileEntry) {
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.repository.liferayrepository.LiferayRepository";

	private class AdaptiveMediaCapabiliy
		implements Capability, RepositoryEventAware {

		public void registerRepositoryEventListeners(
			RepositoryEventRegistry repositoryEventRegistry) {

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Add.class, FileEntry.class,
				AdaptiveMediaRepositoryDefiner.this::_createAdaptiveImages);

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Update.class, FileEntry.class,
				AdaptiveMediaRepositoryDefiner.this::_updateAdaptiveImages);

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Delete.class, FileEntry.class,
				AdaptiveMediaRepositoryDefiner.this::_deleteAdaptiveImages);
		}

	}

	@Reference(
		target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-"
	)
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

}