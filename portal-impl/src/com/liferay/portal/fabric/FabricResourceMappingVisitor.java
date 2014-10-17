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

package com.liferay.portal.fabric;

import com.liferay.portal.fabric.repository.RepositoryHelperUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.util.ObjectGraphUtil.AnnotatedFieldMappingVisitor;

import java.io.File;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.nio.file.Path;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class FabricResourceMappingVisitor extends AnnotatedFieldMappingVisitor {

	public FabricResourceMappingVisitor(
		Class<? extends Annotation> annotationClass,
		Path remoteRepositoryPath) {

		super(
			Collections.<Class<?>>singleton(ProcessCallable.class),
			Collections.<Class<? extends Annotation>>singleton(annotationClass),
			Collections.<Class<?>>singleton(File.class));

		_remoteRepositoryPath = remoteRepositoryPath;
	}

	public Map<Path, Path> getResourceMap() {
		return _resourceMap;
	}

	@Override
	protected Object doMap(Field field, Object value) {
		File file = (File)value;

		Path path = file.toPath();

		Path mappedPath = RepositoryHelperUtil.getRepositoryFilePath(
			_remoteRepositoryPath, path);

		mappedPath = mappedPath.toAbsolutePath();

		_resourceMap.put(path.toAbsolutePath(), mappedPath);

		return mappedPath.toFile();
	}

	private final Path _remoteRepositoryPath;
	private final Map<Path, Path> _resourceMap = new HashMap<Path, Path>();

}