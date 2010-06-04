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

package com.liferay.portal.kernel.deploy.sandbox;

import java.io.File;

/**
 * <a href="SandboxDeployThemeListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class SandboxDeployThemeListener implements SandboxDeployListener {

	private static final String THEME = "theme";

	public void deploy(File dir) throws SandboxDeployException {
		String suffix = _getDirSuffix(dir);
		if (suffix.equals(THEME) == false) {
			return;
		}

		// copy default theme, create web xmls etc

		// deploy
	}

	public void undeploy(File dir) throws SandboxDeployException {
	}
	
	private String _getDirSuffix(File dir) {
		String dirName = dir.getName();
		int ndx = dirName.lastIndexOf('-');
		if (ndx == -1) {
			return null;
		}
		return dirName.substring(ndx + 1);
	}
}
