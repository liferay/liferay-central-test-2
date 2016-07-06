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

import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.RepositoryConfiguration;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalogUtil;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Adolfo Pérez
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

	protected void initializeOverridenRepositoryDefiner(String className) {
		List<RepositoryDefiner> repositoryDefiners = _getFieldValue(
			"_repositoryDefiners");

		_overridenRepositoryDefiner = repositoryDefiners.stream().
			filter(
				(repositoryDefiner) ->
					className.equals(repositoryDefiner.getClassName())).
			findFirst().
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

	private RepositoryDefiner _overridenRepositoryDefiner;

}