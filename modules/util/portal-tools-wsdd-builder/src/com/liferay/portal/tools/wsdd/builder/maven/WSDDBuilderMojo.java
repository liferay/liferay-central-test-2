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

package com.liferay.portal.tools.wsdd.builder.maven;

import com.liferay.portal.tools.wsdd.builder.WSDDBuilderArgs;
import com.liferay.portal.tools.wsdd.builder.WSDDBuilderInvoker;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @author Andrea Di Giorgi
 */
public class WSDDBuilderMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			WSDDBuilderInvoker.invoke(baseDir, _wsddBuilderArgs);
		}
		catch (Exception e) {
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	public void setClassPath(String classPath) {
		_wsddBuilderArgs.setClassPath(classPath);
	}

	public void setInputFileName(String inputFileName) {
		_wsddBuilderArgs.setFileName(inputFileName);
	}

	public void setOutputDirName(String outputDirName) {
		_wsddBuilderArgs.setOutputPath(outputDirName);
	}

	public void setServerConfigFileName(String serverConfigFileName) {
		_wsddBuilderArgs.setServerConfigFileName(serverConfigFileName);
	}

	public void setServiceNamespace(String serviceNamespace) {
		_wsddBuilderArgs.setServiceNamespace(serviceNamespace);
	}

	protected File baseDir;

	private final WSDDBuilderArgs _wsddBuilderArgs = new WSDDBuilderArgs();

}