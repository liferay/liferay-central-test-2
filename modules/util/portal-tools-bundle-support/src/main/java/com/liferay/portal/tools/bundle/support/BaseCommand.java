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

package com.liferay.portal.tools.bundle.support;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

import java.nio.file.Path;

/**
 * @author David Truong
 */
public abstract class BaseCommand implements Command {

	public BaseCommand() {
	}

	public BaseCommand(File liferayHomeDir) {
		_liferayHomeDir = liferayHomeDir;
	}

	public File getLiferayHomeDir() {
		return _liferayHomeDir;
	}

	public void setLiferayHomeDir(File liferayHomeDir) {
		_liferayHomeDir = liferayHomeDir;
	}

	protected Path getLiferayHomePath() {
		return _liferayHomeDir.toPath();
	}

	@Parameter(
		converter = FileConverter.class,
		description = "The directory of your Liferay home.",
		names = {"-l", "--liferay"}, required = true
	)
	private File _liferayHomeDir;

}