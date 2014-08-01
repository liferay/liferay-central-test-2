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

import com.liferay.portal.kernel.repository.registry.BaseRepositoryRegistryPlugin;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryCreatorRegistry;

/**
 * @author Adolfo Pérez
 */
public class LegacyExternalRepositoryRegistryPlugin
	extends BaseRepositoryRegistryPlugin {

	public LegacyExternalRepositoryRegistryPlugin(
		String className, long classNameId,
		RepositoryCreator repositoryCreator) {

		_className = className;
		_classNameId = classNameId;
		_repositoryCreator = repositoryCreator;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public long getClassNameId() {
		return _classNameId;
	}

	@Override
	public boolean isExternalRepository() {
		return true;
	}

	@Override
	public void registerRepositoryCreator(
		RepositoryCreatorRegistry repositoryCreatorRegistry) {

		repositoryCreatorRegistry.setRepositoryCreator(_repositoryCreator);
	}

	private String _className;
	private long _classNameId;
	private RepositoryCreator _repositoryCreator;

}