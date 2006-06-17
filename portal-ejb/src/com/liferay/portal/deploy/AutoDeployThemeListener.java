/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.deploy;

import com.liferay.portal.shared.deploy.AutoDeployException;
import com.liferay.portal.shared.deploy.AutoDeployListener;

import java.io.File;
import java.io.IOException;

import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AutoDeployThemeListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public class AutoDeployThemeListener implements AutoDeployListener {

	public AutoDeployThemeListener() {
		_deployer = new AutoThemeDeployer();
	}

	public void deploy(File file) throws AutoDeployException {
		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(file);

			if (zipFile.getEntry("WEB-INF/liferay-look-and-feel.xml") == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						file.getPath() +
							" does not have WEB-INF/liferay-look-and-feel.xml");
				}

				return;
			}
		}
		catch (IOException ioe) {
			throw new AutoDeployException(ioe.getMessage());
		}
		finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				}
				catch (IOException ioe) {
				}
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Copying themes for " + file.getPath());
		}

		_deployer.deploy(file.getName());

		if (_log.isInfoEnabled()) {
			_log.info("Themes for " + file.getPath() + " copied successfully");
		}
	}

	private static Log _log =
		LogFactory.getLog(AutoDeployThemeListener.class);

	private AutoDeployer _deployer;

}