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

package com.liferay.portal.repository.capabilities;

import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.CommentCapability;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.PortalCapabilityLocator;
import com.liferay.portal.kernel.repository.capabilities.ProcessorCapability;
import com.liferay.portal.kernel.repository.capabilities.RelatedModelCapability;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.capabilities.ThumbnailCapability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.repository.capabilities.util.DLAppServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileEntryServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFileVersionServiceAdapter;
import com.liferay.portal.repository.capabilities.util.DLFolderServiceAdapter;
import com.liferay.portal.repository.capabilities.util.GroupServiceAdapter;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryChecker;
import com.liferay.portal.repository.capabilities.util.RepositoryEntryConverter;
import com.liferay.portal.repository.capabilities.util.RepositoryServiceAdapter;
import com.liferay.trash.kernel.service.TrashEntryLocalService;
import com.liferay.trash.kernel.service.TrashVersionLocalService;

/**
 * @author Adolfo Pérez
 */
public class PortalCapabilityLocatorImpl implements PortalCapabilityLocator {

	@Override
	public BulkOperationCapability getBulkOperationCapability(
		DocumentRepository documentRepository) {

		return new LiferayBulkOperationCapability(
			documentRepository,
			DLFileEntryServiceAdapter.create(documentRepository),
			DLFolderServiceAdapter.create(documentRepository));
	}

	@Override
	public CommentCapability getCommentCapability(
		DocumentRepository documentRepository) {

		return _commentCapability;
	}

	@Override
	public ConfigurationCapability getConfigurationCapability(
		DocumentRepository documentRepository) {

		return new ConfigurationCapabilityImpl(
			documentRepository,
			RepositoryServiceAdapter.create(documentRepository));
	}

	@Override
	public ProcessorCapability getProcessorCapability(
		DocumentRepository documentRepository) {

		return _processorCapability;
	}

	@Override
	public RelatedModelCapability getRelatedModelCapability(
		DocumentRepository documentRepository) {

		RepositoryEntryChecker repositoryEntryChecker =
			new RepositoryEntryChecker(documentRepository);

		return new LiferayRelatedModelCapability(
			_repositoryEntryConverter, repositoryEntryChecker);
	}

	@Override
	public SyncCapability getSyncCapability(
		DocumentRepository documentRepository) {

		return new LiferaySyncCapability(
			GroupServiceAdapter.create(documentRepository),
			dlSyncEventLocalService);
	}

	@Override
	public TemporaryFileEntriesCapability getTemporaryFileEntriesCapability(
		DocumentRepository documentRepository) {

		return new TemporaryFileEntriesCapabilityImpl(documentRepository);
	}

	@Override
	public ThumbnailCapability getThumbnailCapability(
		DocumentRepository documentRepository) {

		RepositoryEntryChecker repositoryEntryChecker =
			new RepositoryEntryChecker(documentRepository);

		return new LiferayThumbnailCapability(
			_repositoryEntryConverter, repositoryEntryChecker);
	}

	@Override
	public TrashCapability getTrashCapability(
		DocumentRepository documentRepository) {

		return new LiferayTrashCapability(
			dlAppHelperLocalService,
			DLAppServiceAdapter.create(documentRepository),
			DLFileEntryServiceAdapter.create(documentRepository),
			DLFolderServiceAdapter.create(documentRepository),
			RepositoryServiceAdapter.create(documentRepository),
			trashEntryLocalService, trashVersionLocalService);
	}

	@Override
	public WorkflowCapability getWorkflowCapability(
		DocumentRepository documentRepository,
		WorkflowCapability.OperationMode operationMode) {

		if (operationMode == WorkflowCapability.OperationMode.MINIMAL) {
			return new MinimalWorkflowCapability(
				DLFileEntryServiceAdapter.create(documentRepository));
		}

		return new LiferayWorkflowCapability(
			DLFileEntryServiceAdapter.create(documentRepository),
			DLFileVersionServiceAdapter.create(documentRepository));
	}

	@BeanReference(type = DLAppHelperLocalService.class)
	protected DLAppHelperLocalService dlAppHelperLocalService;

	@BeanReference(type = DLSyncEventLocalService.class)
	protected DLSyncEventLocalService dlSyncEventLocalService;

	@BeanReference(type = TrashEntryLocalService.class)
	protected TrashEntryLocalService trashEntryLocalService;

	@BeanReference(type = TrashVersionLocalService.class)
	protected TrashVersionLocalService trashVersionLocalService;

	private final CommentCapability _commentCapability =
		new LiferayCommentCapability();
	private final ProcessorCapability _processorCapability =
		new LiferayProcessorCapability();
	private final RepositoryEntryConverter _repositoryEntryConverter =
		new RepositoryEntryConverter();

}