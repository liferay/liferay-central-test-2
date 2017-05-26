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

import com.liferay.adaptive.media.AdaptiveMediaException;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessor;
import com.liferay.adaptive.media.processor.AdaptiveMediaAsyncProcessorLocator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.event.RepositoryEventAware;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 * @author Sergio González
 */
public abstract class BaseOverridingRepositoryDefiner
	implements RepositoryDefiner {

	@Override
	public String getClassName() {
		return _overridenRepositoryDefiner.getClassName();
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration() {
		return _overridenRepositoryDefiner.getRepositoryConfiguration();
	}

	@Override
	public String getRepositoryTypeLabel(Locale locale) {
		return _overridenRepositoryDefiner.getRepositoryTypeLabel(locale);
	}

	@Override
	public boolean isExternalRepository() {
		return _overridenRepositoryDefiner.isExternalRepository();
	}

	@Override
	public void registerCapabilities(
		CapabilityRegistry<DocumentRepository> capabilityRegistry) {

		_overridenRepositoryDefiner.registerCapabilities(capabilityRegistry);

		capabilityRegistry.addSupportedCapability(
			AdaptiveMediaCapabiliy.class, new AdaptiveMediaCapabiliy());
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		_overridenRepositoryDefiner.registerRepositoryEventListeners(
			repositoryEventRegistry);
	}

	@Override
	public void registerRepositoryFactory(
		RepositoryFactoryRegistry repositoryFactoryRegistry) {

		_overridenRepositoryDefiner.registerRepositoryFactory(
			repositoryFactoryRegistry);
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaAsyncProcessorLocator(
		AdaptiveMediaAsyncProcessorLocator asyncProcessorLocator) {

		_asyncProcessorLocator = asyncProcessorLocator;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	public void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	protected void initializeOverridenRepositoryDefiner(String className) {
		List<RepositoryDefiner> repositoryDefiners = _getFieldValue(
			"_repositoryDefiners");

		_overridenRepositoryDefiner = repositoryDefiners.stream().filter(
			(repositoryDefiner) ->
				className.equals(repositoryDefiner.getClassName())).findFirst().
				orElseThrow(
					() -> new RepositoryException(
						"No repository found with className " + className));
	}

	protected void restoreOverridenRepositoryDefiner(String className) {
		Map<String, RepositoryClassDefinition> repositoryClassDefinitions =
			_getFieldValue("_repositoryClassDefinitions");

		RepositoryClassDefinition repositoryClassDefinition =
			RepositoryClassDefinition.fromRepositoryDefiner(
				_overridenRepositoryDefiner);

		repositoryClassDefinitions.put(className, repositoryClassDefinition);
	}

	private void _deleteAdaptiveMedia(FileEntry fileEntry) {
		try {
			AdaptiveMediaAsyncProcessor<FileVersion, ?> processor =
				_asyncProcessorLocator.locateForClass(FileVersion.class);

			List<FileVersion> fileVersions = fileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);

			for (FileVersion fileVersion : fileVersions) {
				processor.triggerCleanUp(
					fileVersion,
					String.valueOf(fileVersion.getFileVersionId()));
			}
		}
		catch (AdaptiveMediaException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	private <T> T _getFieldValue(String fieldName) {
		try {
			RepositoryClassDefinitionCatalog repositoryClassDefinitionCatalog =
				RepositoryClassDefinitionCatalogUtil.
					getRepositoryClassDefinitionCatalog();

			Class<? extends RepositoryClassDefinitionCatalog> clazz =
				repositoryClassDefinitionCatalog.getClass();

			Field field = clazz.getDeclaredField(fieldName);

			field.setAccessible(true);

			return (T)field.get(repositoryClassDefinitionCatalog);
		}
		catch (IllegalAccessException | NoSuchFieldException |
			   SecurityException e) {

			throw new RepositoryException(e);
		}
	}

	private void _updateAdaptiveMedia(FileEntry fileEntry) {
		try {
			AdaptiveMediaAsyncProcessor<FileVersion, ?> asyncProcessor =
				_asyncProcessorLocator.locateForClass(FileVersion.class);

			FileVersion latestFileVersion = fileEntry.getLatestFileVersion(
				true);

			asyncProcessor.triggerProcess(
				latestFileVersion,
				String.valueOf(latestFileVersion.getFileVersionId()));
		}
		catch (AdaptiveMediaException | PortalException e) {
			throw new RuntimeException(e);
		}
	}

	private AdaptiveMediaAsyncProcessorLocator _asyncProcessorLocator;
	private RepositoryDefiner _overridenRepositoryDefiner;

	private class AdaptiveMediaCapabiliy
		implements Capability, RepositoryEventAware {

		public void registerRepositoryEventListeners(
			RepositoryEventRegistry repositoryEventRegistry) {

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Add.class, FileEntry.class,
				BaseOverridingRepositoryDefiner.this::_updateAdaptiveMedia);

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Update.class, FileEntry.class,
				BaseOverridingRepositoryDefiner.this::_updateAdaptiveMedia);

			repositoryEventRegistry.registerRepositoryEventListener(
				RepositoryEventType.Delete.class, FileEntry.class,
				BaseOverridingRepositoryDefiner.this::_deleteAdaptiveMedia);
		}

	}

}