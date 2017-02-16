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

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = RepositoryDefiner.class)
public class AdaptiveMediaLiferayRepositoryDefiner
	extends BaseOverridingRepositoryDefiner {

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		Set<Class<? extends Capability>> excludedCapabilities = new HashSet<>();

		excludedCapabilities.add(ProcessorCapability.class);

		super.registerCapabilities(
			new OverridingCapabilityRegistry<>(
				capabilityRegistry, excludedCapabilities));
	}

	@Activate
	protected void activate() {
		initializeOverridenRepositoryDefiner(_CLASS_NAME);
	}

	@Deactivate
	protected void deactivate() {
		restoreOverridenRepositoryDefiner(_CLASS_NAME);
	}

	private static final String _CLASS_NAME =
		"com.liferay.portal.repository.liferayrepository.LiferayRepository";

}