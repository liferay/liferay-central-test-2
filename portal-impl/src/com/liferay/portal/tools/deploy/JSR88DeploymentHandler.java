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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2009 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.tools.deploy;

import com.liferay.portal.util.PropsKeys;

import java.io.File;

import java.util.Properties;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.shared.factories.DeploymentFactoryManager;
import javax.enterprise.deploy.spi.DeploymentManager;
import javax.enterprise.deploy.spi.TargetModuleID;
import javax.enterprise.deploy.spi.exceptions.DeploymentManagerCreationException;
import javax.enterprise.deploy.spi.exceptions.TargetException;
import javax.enterprise.deploy.spi.factories.DeploymentFactory;
import javax.enterprise.deploy.spi.status.ProgressObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JSR88DeploymentHandler.java.html"><b><i>View Source</i></b></a>
 *
 * @author Sandeep Soni
 *
 */

public class JSR88DeploymentHandler {

	public JSR88DeploymentHandler() {
	}

	public JSR88DeploymentHandler(ClassLoader loader, Properties props) {
		_loader = loader;
		_deploymentProperties = props;
	}

	public DeploymentManager getDeploymentManager() {

		if (null == _deploymentManager) {

			DeploymentFactoryManager dfm =
				DeploymentFactoryManager.getInstance();

			try {
				Class dfClass =
					_loader.loadClass(_getParam(PropsKeys.JSR88_DF_CLASSNAME));

				DeploymentFactory dfInstance;
				dfInstance = (DeploymentFactory) dfClass.newInstance();
				dfm.registerDeploymentFactory(dfInstance);
			}
			catch (ClassNotFoundException cnfe) {
				_log.warn(cnfe);
			}
			catch (IllegalAccessException iae) {
				_log.warn(iae);
			}
			catch (InstantiationException ie) {
				_log.warn(ie);
			}

			try {
				_deploymentManager = dfm.getDeploymentManager(
					_getParam(PropsKeys.JSR88_DM_ID),
					_getParam(PropsKeys.JSR88_DM_USER),
					_getParam(PropsKeys.JSR88_DM_PASSWORD));
			} catch (DeploymentManagerCreationException dmce) {
				_log.warn(dmce);
			}
		}

		return _deploymentManager;
	}

	public void runApp(String warFilename, String warContext)
		throws Exception {

		setAppStarted(false);
		boolean redeploy = false;
		TargetModuleID[] tmIds = new TargetModuleID[1];

		try {
			TargetModuleID[] ids = getDeploymentManager().getAvailableModules(
					ModuleType.WAR, getDeploymentManager().getTargets());

			TargetModuleID[] myIDs = new TargetModuleID[1];

			for (TargetModuleID id : ids) {
				if (warContext.equals(id.getModuleID())) {
					myIDs[0] = id;
					redeploy = true;
					tmIds[0] = id;
					break;
				}
			}
		}
		catch (IllegalStateException ise) {
			_log.warn(ise);
		}
		catch (TargetException te) {
			_log.warn(te);
		}

		ProgressObject deplProgress = null;

		if (!redeploy) {
			deplProgress = getDeploymentManager().distribute(
				getDeploymentManager().getTargets(),
				new File(warFilename),
				null);
		} else {
			deplProgress = getDeploymentManager().redeploy(
				tmIds, new File(warFilename), null);
		}

		deplProgress.addProgressListener(
			new DeploymentProgressListener(this, warContext));

		waitForAppStart(warContext);

		if (_isError) {
			throw new Exception("Deployment failed for " + warFilename);
		}
	}

	public void undeployApp(String warContext) throws Exception {

		setAppUndeployed(false);

		try {
			TargetModuleID[] ids = getDeploymentManager().getRunningModules(
					ModuleType.WAR, getDeploymentManager().getTargets());

			TargetModuleID[] myIDs = new TargetModuleID[1];

			for (TargetModuleID id : ids) {

				if (warContext.equals(id.getModuleID())) {
					myIDs[0] = id;
					ProgressObject startProgress =
						getDeploymentManager().undeploy(myIDs);
					startProgress.addProgressListener(
						new UndeploymentProgressListener(this, warContext));
				}
			}
		}
		catch (IllegalStateException ise) {
			_log.warn(ise);
		}
		catch (TargetException te) {
			_log.warn(te);
		}

		waitForAppUndeployment();

		if (_isError) {
			throw new Exception("Undeployment failed for " + warContext);
		}
	}

	public void releaseDeploymentManager() {
		if (null != _deploymentManager) {
			_deploymentManager.release();
		}
	}

	synchronized void setAppStarted(boolean appStarted) {
		_appStarted = appStarted;
		notifyAll();
	}

	synchronized void setAppUndeployed(boolean appUndeployed) {
		this._appUndeployed = appUndeployed;
		notifyAll();
	}

	synchronized void setError(boolean error) {
		_isError = error;
	}

	synchronized void waitForAppStart(String warContext) {
		while (!_appStarted && !_isError) {
			try {
				wait();
			} catch (InterruptedException e) {
				_log.info("Interrupted start of app for " + warContext);
			}
		}

		if ( !_isError ) {
			TargetModuleID[] ids;
			try {
				ids = getDeploymentManager().getAvailableModules(
						ModuleType.WAR, getDeploymentManager().getTargets());
				int i = -1;
				for (i = 0; i < ids.length; i++) {
					if (ids[i].getModuleID().equals(warContext)) {
						break;
					}
				}
				if (i>=0) {
					getDeploymentManager().start(new TargetModuleID[]{ids[i]});
				}
			} catch (IllegalStateException ise) {
				_log.warn(ise);
			} catch (TargetException te) {
				_log.warn(te);
			}
		}
	}

	synchronized void waitForAppUndeployment() {
		while (!_appUndeployed && !_isError) {
			try {
				wait();
			} catch (InterruptedException e) {
				_log.info("Interrupted waitForAppUndeployment().");
			}
		}
	}

	private String _getParam(String param) {
		if ( null == _deploymentProperties ) {
			return null;
		}
		else {
			return _deploymentProperties.getProperty(param);
		}
	}

	private boolean _appStarted;
	private boolean _appUndeployed;
	private DeploymentManager _deploymentManager;
	private Properties _deploymentProperties;
	private boolean _isError;
	private ClassLoader _loader;

	private static Log _log = LogFactory.getLog(JSR88DeploymentHandler.class);

}