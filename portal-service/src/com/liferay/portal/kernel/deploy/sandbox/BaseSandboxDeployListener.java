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

import com.liferay.portal.kernel.io.DirectoryFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSandboxDeployListener
	implements SandboxDeployListener {

	public static final String THEME_RESOURCE_ROOT =
		"com/liferay/portal/deploy/dependencies/theme/";

	protected File getEngineHostFolder() throws IOException {

		File rootXml = _findRootXml();

		if (rootXml == null) {
			throw new IOException("Unable to locate ROOT.xml");
		}

		return rootXml.getParentFile();
	}

	private File _findRootXml() {
		File confFolder = new File(System.getenv("CATALINA_BASE"), "conf");

		File[] firstLevelFolders = confFolder.listFiles(_directoryFilter);
		for (File firstLevel : firstLevelFolders) {

			File[] secondLevelFolders = firstLevel.listFiles(_directoryFilter);

			for (File secondLevel : secondLevelFolders) {

				File[] thirdLevelFiles = secondLevel.listFiles();

				for (File thirdLevel : thirdLevelFiles) {

					if (thirdLevel.getName().equals("ROOT.xml")) {
						return thirdLevel;
					}
				}
			}
		}
		return null;
	}

	private FileFilter _directoryFilter = new DirectoryFilter();

}