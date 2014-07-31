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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryRegistryPlugin;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.repository.external.LegacyExternalRepositoryRegistryPlugin;
import com.liferay.portal.repository.liferayrepository.LiferayRepositoryRegistryPlugin;
import com.liferay.portal.repository.util.ExternalRepositoryFactory;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryImpl;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.util.PropsValues;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Adolfo Pérez
 */
public class RepositoryCatalogImpl implements RepositoryCatalog {

	@Override
	public Collection<String> getExternalRepositoryClassNames() {
		return _externalRepositoriesClassNames;
	}

	@Override
	public RepositoryConfiguration getRepositoryConfiguration(
		long classNameId) {

		return _repositoryConfigurations.get(classNameId);
	}

	public void loadDefaultRepositoryRegistryPlugins() throws PortalException {
		RepositoryCatalogLifecycle repositoryCatalogLifecycle =
			new RepositoryCatalogLifecycle();

		repositoryCatalogLifecycle.registerPortalLifecycle(
			PortalLifecycle.METHOD_INIT);
	}

	@Override
	public void registerLegacyExternalRepositoryFactory(
			String className,
			ExternalRepositoryFactory externalRepositoryFactory)
		throws PortalException {

		ExternalRepositoryFactoryUtil.registerExternalRepositoryFactory(
			className, externalRepositoryFactory);

		long classNameId = _classNameLocalService.getClassNameId(className);

		RepositoryRegistryPlugin repositoryRegistryPlugin =
			new LegacyExternalRepositoryRegistryPlugin(
				classNameId, _legacyExternalRepositoryCreator);

		registerRepositoryRegistryPlugin(repositoryRegistryPlugin);
	}

	@Override
	public void registerRepositoryRegistryPlugin(
			RepositoryRegistryPlugin repositoryRegistryPlugin)
		throws PortalException {

		if (repositoryRegistryPlugin.isExternalRepository()) {
			long classNameId = repositoryRegistryPlugin.getClassNameId();

			ClassName className = _classNameLocalService.getClassName(
				classNameId);

			_externalRepositoriesClassNames.add(className.getValue());
		}

		_repositoryConfigurations.put(
			repositoryRegistryPlugin.getClassNameId(),
			createRepositoryConfiguration(repositoryRegistryPlugin));
	}

	public void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	public void setLegacyExternalRepositoryCreator(
		RepositoryCreator repositoryCreator) {

		_legacyExternalRepositoryCreator = repositoryCreator;
	}

	public void setLiferayRepositoryRegistryPlugin(
		RepositoryRegistryPlugin liferayRepositoryRegistryPlugin) {

		_liferayRepositoryRegistryPlugin = liferayRepositoryRegistryPlugin;
	}

	@Override
	public void unregisterLegacyExternalRepositoryFactory(String className) {
		ExternalRepositoryFactoryUtil.unregisterExternalRepositoryFactory(
			className);

		long classNameId = _classNameLocalService.getClassNameId(className);

		_repositoryConfigurations.remove(classNameId);
	}

	@Override
	public void unregisterRepositoryRegistryPlugin(String className) {
		_externalRepositoriesClassNames.remove(className);

		long classNameId = _classNameLocalService.getClassNameId(className);

		_repositoryConfigurations.remove(classNameId);
	}

	protected RepositoryConfiguration createRepositoryConfiguration(
		RepositoryRegistryPlugin repositoryRegistryPlugin) {

		DefaultRepositoryRegistry defaultRepositoryRegistry =
			new DefaultRepositoryRegistry();

		repositoryRegistryPlugin.registerCapabilities(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryCreator(
			defaultRepositoryRegistry);
		repositoryRegistryPlugin.registerRepositoryEventListeners(
			defaultRepositoryRegistry);

		return defaultRepositoryRegistry;
	}

	private static Log _log = LogFactoryUtil.getLog(
		RepositoryCatalogImpl.class);

	private ClassNameLocalService _classNameLocalService;
	private Set<String> _externalRepositoriesClassNames =
		new ConcurrentHashSet<String>();
	private RepositoryCreator _legacyExternalRepositoryCreator;
	private RepositoryRegistryPlugin _liferayRepositoryRegistryPlugin;
	private Map<Long, RepositoryConfiguration> _repositoryConfigurations =
		new ConcurrentHashMap<Long, RepositoryConfiguration>();

	private class RepositoryCatalogLifecycle extends BasePortalLifecycle {

		@Override
		protected void doPortalDestroy() throws Exception {
		}

		@Override
		protected void doPortalInit() throws Exception {
			registerLiferayRepositoryRegistryPlugin();

			registerLegacyRepositoryRegistryPlugin();
		}

		protected void registerLegacyRepositoryRegistryPlugin() {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			for (String className : PropsValues.DL_REPOSITORY_IMPL) {
				try {
					ExternalRepositoryFactory externalRepositoryFactory =
						new ExternalRepositoryFactoryImpl(
							className, classLoader);

					registerLegacyExternalRepositoryFactory(
						className, externalRepositoryFactory);
				}
				catch (PortalException pe) {
					_log.error("Unable to register " + className, pe);
				}
			}
		}

		protected void registerLiferayRepositoryRegistryPlugin() {
			try {
				registerRepositoryRegistryPlugin(
					_liferayRepositoryRegistryPlugin);
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to register " +
						LiferayRepositoryRegistryPlugin.class.getName(),
					pe);
			}
		}

	}

}