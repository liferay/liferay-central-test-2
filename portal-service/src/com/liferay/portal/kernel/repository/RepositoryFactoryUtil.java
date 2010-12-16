/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.repository;

/**
 * @author Alexander Chow
 */
public class RepositoryFactoryUtil {

	public static LocalRepository getLocalRepository(long repositoryId) {
		return getRepositoryFactory().getLocalRepository(repositoryId);
	}

	public static Repository getRepository(long repositoryId) {
		return getRepositoryFactory().getRepository(repositoryId);
	}

	public static RepositoryFactory getRepositoryFactory() {
		return _repositoryFactory;
	}

	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		_repositoryFactory = repositoryFactory;
	}

	private static RepositoryFactory _repositoryFactory;

}