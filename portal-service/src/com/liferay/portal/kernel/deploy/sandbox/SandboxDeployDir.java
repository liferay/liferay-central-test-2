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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <a href="SandboxDeployDir.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 * @author Brian Wing Shun Chan
 */
public class SandboxDeployDir {

	public static final String DEFAULT_NAME = "defaultSandboxDeployDir";

	public SandboxDeployDir(
		String name, File deployDir, long interval,
		List<SandboxDeployListener> sandboxDeployListeners) {

		_name = name;
		_deployDir = deployDir;
		_interval = interval;
		_sandboxDeployListeners =
			new CopyOnWriteArrayList<SandboxDeployListener>(
				sandboxDeployListeners);
	}

	public File getDeployDir() {
		return _deployDir;
	}

	public long getInterval() {
		return _interval;
	}

	public List<SandboxDeployListener> getListeners() {
		return _sandboxDeployListeners;
	}

	public String getName() {
		return _name;
	}

	public void registerListener(SandboxDeployListener listener) {
		_sandboxDeployListeners.add(listener);
	}

	public void start() {
		if (!_deployDir.exists()) {
			if (_log.isInfoEnabled()) {
				_log.info("Creating missing directory " + _deployDir);
			}

			boolean created = _deployDir.mkdirs();

			if (!created) {
				_log.error("Directory " + _deployDir + " could not be created");
			}
		}

		if (_interval > 0) {
			try {
				Thread currentThread = Thread.currentThread();

				_sandboxDeployScanner = new SandboxDeployScanner(
					currentThread.getThreadGroup(),
					SandboxDeployScanner.class.getName(), this);

				_sandboxDeployScanner.start();

				if (_log.isInfoEnabled()) {
					_log.info(
						"Sandbox deploy scanner started for " + _deployDir);
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				stop();

				return;
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Sandbox deploy scanning is disabled for " + _deployDir);
			}
		}
	}

	public void stop() {
		if (_sandboxDeployScanner != null) {
			_sandboxDeployScanner.pause();
		}
	}

	public void unregisterListener(
		SandboxDeployListener sandboxDeployListener) {

		_sandboxDeployListeners.remove(sandboxDeployListener);
	}

	protected void processFile(File file) {
		String fileName = file.getName();

		if (!file.canRead()) {
			_log.error("Unable to read " + fileName);

			return;
		}

		if (!file.canWrite()) {
			_log.error("Unable to write " + fileName);

			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Processing " + fileName);
		}

		try {
			for (SandboxDeployListener sandboxDeployListener :
					_sandboxDeployListeners) {

				sandboxDeployListener.deploy(file);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void scanDirectory() {
		File[] files = _deployDir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				processFile(file);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SandboxDeployDir.class);

	private File _deployDir;
	private long _interval;
	private String _name;
	private List<SandboxDeployListener> _sandboxDeployListeners;
	private SandboxDeployScanner _sandboxDeployScanner;

}