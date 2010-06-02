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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jodd.util.PropertiesUtil;

import org.apache.tools.ant.DirectoryScanner;

import static com.liferay.util.SystemProperties.TMP_DIR;

/**
 * Helper for {@link SourceFormatter}. Used for scanning directories for
 * matched files and as a single point of reporting errors.
 * Optionally, it supports caching, so only modified files will be
 * returned during directory scanning.
 *
 * <a href="SourceFormatterHelper.java.html"><b><i>View Source</i></b></a>
 */
public class SourceFormatterHelper {

	protected final boolean useCache;
	protected Properties timestamps;
	protected File timestampsFile;
	protected long start;

	SourceFormatterHelper(boolean useCache) {
		if (useCache) {
			System.out.println("(i) caching is enabled");
		}
		this.useCache = useCache;
	}

	/**
	 * Scans directories and returns matched files. If caching is turned onm
	 * only new files will be returned - those with the timestamp later
	 * then cached one. If caching is turned off all files will be returned.
	 * <p>
	 * Directory scanning is the actual performance bottle neck. To optimize
	 * this, caching should be implemented inside of DirectoryScanner.
	 */
	public String[] scanForFiles(DirectoryScanner ds) {
		ds.scan();
		String[] allFiles = ds.getIncludedFiles();
		if (useCache == false) {
			return allFiles;
		}
		List<String> newFiles = new ArrayList<String>(allFiles.length);
		for (String fileName : allFiles) {
			boolean isNewFile = true;
			File file = new File(fileName);
			String storedTime = timestamps.getProperty(fileName);
			if (storedTime != null) {

				// existing file, compare the timestamps

				String ts = timestamps.getProperty(fileName);
				long timestamp = ts == null ? 0 : Long.parseLong(ts);
				if (timestamp <= file.lastModified()) {
					isNewFile = false;
				}
			}
			if (isNewFile) {
				newFiles.add(fileName);
				timestamps.setProperty(fileName,
						String.valueOf(file.lastModified()));
			}
		}
		return newFiles.toArray(new String[newFiles.size()]);
	}

	/**
	 * Returns Liferay temp folder.
	 */
	protected static File getLiferayTempFolder() {
		File tempFolder = new File(SystemProperties.get(TMP_DIR), "liferay");
		tempFolder.mkdirs();
		return tempFolder;
	}

	/**
	 * Initializes helper.
	 */
	public void init() throws IOException {
		start = System.currentTimeMillis();
		if (useCache == false) {
			return;
		}
		String baseName = new File("./portal-iml").getAbsolutePath();
		baseName = StringUtil.replace(
			baseName,
			new String[] {".", ":", "/", "\\"},
			new String[] {"_", "_", "_", "_"});

		timestampsFile = new File(
			getLiferayTempFolder(), baseName + ".properties");
		timestampsFile.createNewFile();
		timestamps = PropertiesUtil.createFromFile(timestampsFile);
	}

	/**
	 * Closes helper.
	 */
	public void close() throws IOException {
		if (useCache == true) {
			PropertiesUtil.writeToFile(timestamps, timestampsFile);
		}
		System.out.println(
			"elapsed: " + (System.currentTimeMillis() - start) + " ms.");
	}

	/**
	 * Prints out format error message. All errors must be reported
	 * through this method.
	 * @see #printFmtError(String, java.io.File)
	 */
	public void printFmtError(String fileName, String message) {
		if (useCache == true) {
			timestamps.remove(fileName);
		}
		System.out.println(message);
	}

	/**
	 * Prints out file name that has format error. All errors must be
	 * reported through this method.
	 * @see #printFmtError(String, String)
	 */
	public void printFmtError(String fileName, File file) {
		printFmtError(fileName, file.toString());
	}

}