/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.support.tomcat.startup;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringComparator;

import java.io.File;

import java.util.Arrays;

import org.apache.catalina.startup.HostConfig;

public class PortalHostConfig extends HostConfig {

	public PortalHostConfig() {
		super();
	}

	protected void deployDescriptor(
		String contextPath, File contextXml, String file) {

		if (_log.isDebugEnabled()) {
			_log.debug(
				"deployDescriptor " + contextPath + " " + contextXml + " " +
					file);
		}

		super.deployDescriptor(contextPath, contextXml, file);
	}

	protected void deployDescriptors(File configBase, String[] files) {
		super.deployDescriptors(configBase, sortFiles(files));
	}

	protected void deployDirectories(File appBase, String[] files) {
		super.deployDirectories(appBase, sortFiles(files));
	}

	protected void deployWARs(File appBase, String[] files) {
		super.deployWARs(appBase, sortFiles(files));
	}

	protected String[] sortFiles(String[] files) {
		Arrays.sort(files, new StringComparator(true, true));

		if (_log.isDebugEnabled()) {
			_log.debug("Sort " + files.length + " files");

			for (int i = 0; i < files.length; i++) {
				_log.debug("File " + i + " " + files[i]);
			}
		}

		return files;
	}

	private static Log _log = LogFactoryUtil.getLog(PortalHostConfig.class);

}