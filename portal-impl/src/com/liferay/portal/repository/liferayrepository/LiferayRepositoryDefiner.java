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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.model.FileContentReference;
import com.liferay.portal.kernel.repository.model.ModelValidator;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.repository.util.ModelValidatorUtil;
import com.liferay.portal.repository.capabilities.LiferayBulkOperationCapability;
import com.liferay.portal.repository.capabilities.LiferayProcessorCapability;
import com.liferay.portal.repository.capabilities.LiferaySyncCapability;
import com.liferay.portal.repository.capabilities.LiferayTrashCapability;
import com.liferay.portal.repository.capabilities.LiferayWorkflowCapability;

/**
 * @author Adolfo Pérez
 */
public class LiferayRepositoryDefiner extends BaseRepositoryDefiner {

	public static final String CLASS_NAME = LiferayRepository.class.getName();

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean isExternalRepository() {
		return false;
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		capabilityRegistry.addExportedCapability(
			TrashCapability.class, _liferayTrashCapability);

		DocumentRepository documentRepository = capabilityRegistry.getTarget();

		BulkOperationCapability bulkOperationCapability =
			new LiferayBulkOperationCapability(documentRepository);

		capabilityRegistry.addExportedCapability(
			BulkOperationCapability.class, bulkOperationCapability);

		capabilityRegistry.addSupportedCapability(
			ProcessorCapability.class, _processorCapability);
		capabilityRegistry.addSupportedCapability(
			SyncCapability.class,
			new LiferaySyncCapability(bulkOperationCapability));
		capabilityRegistry.addSupportedCapability(
			WorkflowCapability.class, _liferayWorkflowCapability);
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		repositoryFactoryRegistry.setRepositoryFactory(_repositoryFactory);
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = new LiferayRepositoryFactoryWrapper(
			repositoryFactory);
	}

	private final LiferayTrashCapability _liferayTrashCapability =
		new LiferayTrashCapability();
	private final LiferayWorkflowCapability _liferayWorkflowCapability =
		new LiferayWorkflowCapability();
	private final ProcessorCapability _processorCapability =
		new LiferayProcessorCapability();
	private RepositoryFactory _repositoryFactory;

	private class LiferayRepositoryFactoryWrapper implements RepositoryFactory {

		public LiferayRepositoryFactoryWrapper(
			RepositoryFactory repositoryFactory) {

			_repositoryFactory = repositoryFactory;
		}

		@Override
		public LocalRepository createLocalRepository(long repositoryId)
			throws PortalException {

			LocalRepository localRepository =
				_repositoryFactory.createLocalRepository(repositoryId);

			ModelValidator<FileContentReference> modelValidator =
				ModelValidatorUtil.getDefaultDLFileEntryModelValidator();

			return new ModelValidatorLocalRepositoryWrapper(
				localRepository, modelValidator);
		}

		@Override
		public Repository createRepository(long repositoryId)
			throws PortalException {

			Repository repository = _repositoryFactory.createRepository(
				repositoryId);

			ModelValidator<FileContentReference> modelValidator =
				ModelValidatorUtil.getDefaultDLFileEntryModelValidator();

			return new ModelValidatorRepositoryWrapper(
				repository, modelValidator);
		}

		private final RepositoryFactory _repositoryFactory;

	}

}