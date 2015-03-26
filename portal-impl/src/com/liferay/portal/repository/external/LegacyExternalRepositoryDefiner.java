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

package com.liferay.portal.repository.external;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.capabilities.LiferayProcessorCapability;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;

/**
 * @author Adolfo Pérez
 */
public class LegacyExternalRepositoryDefiner extends BaseRepositoryDefiner {

	public LegacyExternalRepositoryDefiner(
		String className, RepositoryFactory repositoryFactory) {

		_className = className;
		_repositoryFactory = repositoryFactory;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Deprecated
	@Override
	public String[] getSupportedConfigurations() {
		try {
			BaseRepository baseRepository =
				ExternalRepositoryFactoryUtil.getInstance(getClassName());

			return baseRepository.getSupportedConfigurations();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Deprecated
	@Override
	public String[][] getSupportedParameters() {
		try {
			BaseRepository baseRepository =
				ExternalRepositoryFactoryUtil.getInstance(getClassName());

			return baseRepository.getSupportedParameters();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public boolean isExternalRepository() {
		return true;
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class, _processorCapability);
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	private final String _className;
	private final LiferayProcessorCapability _processorCapability =
		new LiferayProcessorCapability();
	private final RepositoryFactory _repositoryFactory;

}