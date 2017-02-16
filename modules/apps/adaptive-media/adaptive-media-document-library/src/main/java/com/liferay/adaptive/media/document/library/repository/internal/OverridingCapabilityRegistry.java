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

import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;

import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class OverridingCapabilityRegistry<T> implements CapabilityRegistry<T> {

	public OverridingCapabilityRegistry(
		CapabilityRegistry<T> capabilityRegistry,
		Set<Class<? extends Capability>> excludedCapabilityClasses) {

		_capabilityRegistry = capabilityRegistry;
		_excludedCapabilityClasses = excludedCapabilityClasses;
	}

	@Override
	public <S extends Capability> void addExportedCapability(
		Class<S> capabilityClass, S capability) {

		if (_excludedCapabilityClasses.contains(capabilityClass)) {
			return;
		}

		_capabilityRegistry.addExportedCapability(capabilityClass, capability);
	}

	@Override
	public <S extends Capability> void addSupportedCapability(
		Class<S> capabilityClass, S capability) {

		if (_excludedCapabilityClasses.contains(capabilityClass)) {
			return;
		}

		_capabilityRegistry.addSupportedCapability(capabilityClass, capability);
	}

	@Override
	public T getTarget() {
		return _capabilityRegistry.getTarget();
	}

	private final CapabilityRegistry<T> _capabilityRegistry;
	private final Set<Class<? extends Capability>> _excludedCapabilityClasses;

}