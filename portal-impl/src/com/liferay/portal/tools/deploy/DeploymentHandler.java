/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.io.File;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.shared.factories.DeploymentFactoryManager;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;
import javax.enterprise.deploy.spi.status.ProgressObject;

/**
 * <a href="DeploymentHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Sandeep Soni
 * @author Brian Wing Shun Chan
 */
public class DeploymentHandler {

	public DeploymentHandler(
		String dmId, String dmUser, String dmPassword, String dfClassName) {

		try {
			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			DeploymentFactoryManager deploymentFactoryManager =
				DeploymentFactoryManager.getInstance();

			DeploymentFactory deploymentFactory =
				(DeploymentFactory)classLoader.loadClass(
					dfClassName).newInstance();

			deploymentFactoryManager.registerDeploymentFactory(
				deploymentFactory);

			_deploymentManager = deploymentFactoryManager.getDeploymentManager(
				dmId, dmUser, dmPassword);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public DeploymentManager getDeploymentManager() {
		return _deploymentManager;
	}

	public void deploy(File warDir, String warContext) throws Exception {
		setStarted(false);

		ProgressObject deployProgress = null;

		TargetModuleID[] targetModuleIDs =
			_deploymentManager.getAvailableModules(
				ModuleType.WAR, _deploymentManager.getTargets());

		for (TargetModuleID targetModuleID : targetModuleIDs) {
			if (!targetModuleID.getModuleID().equals(warContext)) {
				continue;
			}

			deployProgress = _deploymentManager.redeploy(
				new TargetModuleID[] {targetModuleID}, warDir, null);

			break;
		}

		if (deployProgress == null) {
			deployProgress = _deploymentManager.distribute(
				_deploymentManager.getTargets(), warDir, null);
		}

		deployProgress.addProgressListener(
			new DeploymentProgressListener(this, warContext));

		waitForStart(warContext);

		if (_error) {
			throw new Exception("Failed to deploy " + warDir);
		}
	}

	public void releaseDeploymentManager() {
		_deploymentManager.release();
	}

	public synchronized void setError(boolean error) {
		_error = error;
	}

	public synchronized void setStarted(boolean started) {
		_started = started;

		notifyAll();
	}

	protected synchronized void waitForStart(String warContext)
		throws Exception {

		while (!_error && !_started) {
			wait();
		}

		if (_error) {
			return;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(DeploymentHandler.class);

	private DeploymentManager _deploymentManager;
	private boolean _error;
	private boolean _started;

}