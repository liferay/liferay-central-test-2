/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.kernel.deploy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="AutoDeployDir.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public class AutoDeployDir {

	public AutoDeployDir(String name, File deployDir, File destDir,
						 long interval, List listeners) {

		_name = name;
		_deployDir = deployDir;
		_destDir = destDir;
		_interval = interval;
		_listeners = listeners;
		_inProcessFiles = Collections.synchronizedSet(new HashSet());
	}

	public String getName() {
		return _name;
	}

	public File getDeployDir() {
		return _deployDir;
	}

	public File getDestDir() {
		return _destDir;
	}

	public long getInterval() {
		return _interval;
	}

	public List getListeners() {
		return _listeners;
	}

	public void start() {
		if (!_deployDir.exists()) {
			_log.warn("Creating missing directory " + _deployDir);

			_deployDir.mkdirs();
		}

		if (_interval > 0) {
			try {
				_scanner = new AutoDeployScanner(
					Thread.currentThread().getThreadGroup(),
					AutoDeployScanner.class.getName(), this);

				_scanner.start();

				if (_log.isInfoEnabled()) {
					_log.info("Auto deploy scanner started for " + _deployDir);
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
				_log.info("Auto deploy scanning is disabled for " + _deployDir);
			}
		}
	}

	public void stop() {
		if (_scanner != null) {
			_scanner.pause();
		}
	}

	protected void scanDirectory() {
		File[] files = _deployDir.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.isFile() && file.getName().endsWith(".war")) {
				if (!_inProcessFiles.contains(file.getName())) {
					_inProcessFiles.add(file.getName());

					try {
						Iterator itr = _listeners.iterator();

						while (itr.hasNext()) {
							AutoDeployListener listener =
								(AutoDeployListener)itr.next();

							listener.deploy(file);
						}
					}
					catch (Exception e) {
						_log.error(e, e);
					}
					finally {
						if (file.delete()) {
							_inProcessFiles.remove(file.getName());
						}
						else {
							_log.error(
								"Auto deploy failed to remove " +
									file.getName());
						}
					}
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AutoDeployDir.class);

	private String _name;
	private File _deployDir;
	private File _destDir;
	private long _interval;
	private List _listeners;
	private Set _inProcessFiles;
	private AutoDeployScanner _scanner;

}