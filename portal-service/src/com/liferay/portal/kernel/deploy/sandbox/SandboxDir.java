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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;
import java.io.FileFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a href="SandboxDir.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class SandboxDir extends Thread {

	public SandboxDir(File sandboxDir, long interval) {
		_interval = interval;
		_sandboxDir = sandboxDir;

		if (_sandboxDir.exists() == false) {
			_sandboxDir.mkdirs();
		}
		if (_sandboxDir.isDirectory() == false) {
			_log.error("Invalid Sandbox directory: " + sandboxDir);
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Sandbox dir: " + _sandboxDir);
		}

		_existingDirs = new ArrayList<File>();
		for (File dir : _listSubdirs()) {
			_existingDirs.add(dir);
		}

		_listeners = new HashMap<String, SandboxListener>();

		setContextClassLoader(getClass().getClassLoader());
//		setDaemon(true);
		setPriority(MIN_PRIORITY);
	}

	@Override
	public void run() {

		try {
			sleep(10000);
		}
		catch (InterruptedException ignored) {
		}

		while (_working) {

			File[] currentDirs = _listSubdirs();

			if (currentDirs.length != _existingDirs.size()) {

				// check for new folders
				for (File dir : currentDirs) {
					if (_existingDirs.contains(dir) == false) {
						_existingDirs.add(dir);
						_onNewFolder(dir);
					}
				}
			}

			// check for removed folders - this will handle renaming as well
			Iterator<File> iterator = _existingDirs.iterator();
			while (iterator.hasNext()) {
				File dir = iterator.next();
				if (dir.exists() == false) {
					iterator.remove();
					_onDeletedFolder(dir);
				}
			}

			try {
				Thread.sleep(_interval);
			}
			catch (InterruptedException ignored) {
			}

		}

	}

	// ------------------------------------------------------ handlers

	private void _onNewFolder(File dir) {
		if (_log.isDebugEnabled()) {
			_log.debug("new sandbox folder added: " + dir);
		}
		String suffix = _getDirType(dir);
		if (suffix != null) {
			SandboxListener sandboxListener = _listeners.get(suffix);
			if (sandboxListener != null) {
				sandboxListener.deploy(dir);
			}
		}
	}

	private void _onDeletedFolder(File dir) {
		if (_log.isDebugEnabled()) {
			_log.debug("sandbox folder removed: " + dir);
		}
	}

	// ------------------------------------------------------ listeners

	public void registerListener(SandboxListener sandboxListener) {
		if (_listeners.put(
			sandboxListener.getType(), sandboxListener) != null) {
			_log.warn("Duplicated sandbox listener type: " +
					sandboxListener.getType());
		}
	}

	// ------------------------------------------------------ util

	private File[] _listSubdirs() {
		File[] dirs = _sandboxDir.listFiles(dirOnlyFileFilter);
		if (dirs == null) {
			dirs = new File[0];
		}
		return dirs;
	}

	private String _getDirType(File dir) {
		String dirName = dir.getName();
		int ndx = dirName.lastIndexOf('-');
		if (ndx == -1) {
			return null;
		}
		return dirName.substring(ndx + 1);
	}

	public void stopThread() {
		_working = false;
	}

	private static final FileFilter dirOnlyFileFilter = new FileFilter() {
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	private static Log _log = LogFactoryUtil.getLog(SandboxUtil.class);

	private Map<String, SandboxListener> _listeners;
	private List<File> _existingDirs;
	private final long _interval;
	private final File _sandboxDir;
	private boolean _working = true;

	public static void main(String[] args) {
		SandboxDir sd = new SandboxDir(
			new File("c:/liferay/bundles/sandbox"), 2000);
		sd.registerListener(new SandboxThemeListener());
		sd.start();
	}

}