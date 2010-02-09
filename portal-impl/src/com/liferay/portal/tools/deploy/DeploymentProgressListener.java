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

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.status.DeploymentStatus;
import javax.enterprise.deploy.spi.status.ProgressEvent;
import javax.enterprise.deploy.spi.status.ProgressListener;
import javax.enterprise.deploy.spi.status.ProgressObject;

/**
 * <a href="DeploymentProgressListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Sandeep Soni
 */
public class DeploymentProgressListener implements ProgressListener {

	public DeploymentProgressListener(
		DeploymentHandler deploymentHandler, String warContext) {

		_deploymentHandler = deploymentHandler;
		_warContext = warContext;
		_deploymentManager = _deploymentHandler.getDeploymentManager();
	}

	public void handleProgressEvent(ProgressEvent progressEvent) {
		DeploymentStatus deploymentStatus = progressEvent.getDeploymentStatus();

		if (_log.isInfoEnabled()) {
			_log.info(deploymentStatus.getMessage());
		}

		if (deploymentStatus.isCompleted()) {
			try {
				TargetModuleID[] targetModuleIDs =
					_deploymentManager.getNonRunningModules(
						ModuleType.WAR, _deploymentManager.getTargets());

				if ((targetModuleIDs != null) && (targetModuleIDs.length > 0)) {
					for (TargetModuleID targetModuleID : targetModuleIDs) {
						if (!_warContext.equals(targetModuleID.getModuleID())) {
							continue;
						}

						ProgressObject startProgress = _deploymentManager.start(
							new TargetModuleID[] {targetModuleID});

						startProgress.addProgressListener(
							new StartProgressListener(_deploymentHandler));

						_deploymentHandler.setError(false);
						_deploymentHandler.setStarted(true);

						break;
					}
				}
				else {
					targetModuleIDs = _deploymentManager.getAvailableModules(
						ModuleType.WAR, _deploymentManager.getTargets());

					for (TargetModuleID targetModuleID : targetModuleIDs) {
						if (!_warContext.equals(targetModuleID.getModuleID())) {
							continue;
						}

						ProgressObject startProgress = _deploymentManager.start(
							new TargetModuleID[] {targetModuleID});

						startProgress.addProgressListener(
							new StartProgressListener(_deploymentHandler));

						_deploymentHandler.setError(false);
						_deploymentHandler.setStarted(true);

						break;
					}
				}
			}
			catch (Exception e) {
				_log.error(e, e);

				_deploymentHandler.setError(true);
				_deploymentHandler.setStarted(false);
			}
		}
		else if (deploymentStatus.isFailed()) {
			_deploymentHandler.setError(true);
			_deploymentHandler.setStarted(false);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DeploymentProgressListener.class);

	private DeploymentHandler _deploymentHandler;
	private String _warContext;
	private DeploymentManager _deploymentManager;

}