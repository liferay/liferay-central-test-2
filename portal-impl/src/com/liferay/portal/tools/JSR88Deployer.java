/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.ant.DeleteTask;
import com.liferay.util.ant.WarTask;

import java.io.File;

import java.util.Properties;

/**
 * <a href="JSR88Deployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Sandeep Soni
 *
 */

public class JSR88Deployer {

	public static void deploy(String displayName, File srcFile, File tempDir, 
			File deployDir, File webXml) throws Exception {

		String appServerId = GetterUtil.getString(ServerDetector.getServerId());
		
		String jsr88DeployEnabled = PropsUtil.get(
				appServerId + StringPool.PERIOD + 
				PropsKeys.JSR88_DEPLOYMENT_ENABLED) + StringPool.BLANK;
		
		if (jsr88DeployEnabled.equalsIgnoreCase("true")) {
			File tempDirWar = new File(
				tempDir.getParent(), deployDir.getName());

			if (tempDirWar.exists()) {
				tempDirWar.delete();
			}

			boolean success = tempDir.renameTo(tempDirWar);

			if (success) {
				_doJsr88Deploy(displayName, tempDirWar);
			} else {
				_doJsr88Deploy(displayName, tempDir);
			}
		} else {
			// Do the normal appserver AutoDeploy
			if (!tempDir.renameTo(deployDir)) {
				WarTask.war(srcFile, deployDir, "WEB-INF/web.xml", webXml);
			}

			DeleteTask.deleteDirectory(tempDir);			
		}
	}

	private static void _doJsr88Deploy(String displayName, File tempDirWar)
			throws Exception {
		JSR88DeploymentHandler handler = _getdeploymentHandler();
		handler.runApp(tempDirWar.getAbsolutePath(), displayName);
		handler.releaseDeploymentManager();
		DeleteTask.deleteDirectory(tempDirWar);
	}

	private static JSR88DeploymentHandler _getdeploymentHandler() {
		String appServerId = GetterUtil.getString(ServerDetector.getServerId());

		String dmId = PropsUtil.get(
			appServerId + StringPool.PERIOD + PropsKeys.JSR88_DM_ID);
		String adminUser = PropsUtil.get(
			appServerId + StringPool.PERIOD + PropsKeys.JSR88_DM_USER);
		String adminPwd  = PropsUtil.get(
			appServerId + StringPool.PERIOD + PropsKeys.JSR88_DM_PASSWORD);
		String dfClassName  = PropsUtil.get(
			appServerId + StringPool.PERIOD + PropsKeys.JSR88_DF_CLASSNAME);

		Properties props = new Properties();
		props.put(PropsKeys.JSR88_DM_ID, dmId);
		props.put(PropsKeys.JSR88_DM_USER, adminUser );
		props.put(PropsKeys.JSR88_DM_PASSWORD, adminPwd);
		props.put(PropsKeys.JSR88_DF_CLASSNAME, dfClassName);

		JSR88DeploymentHandler handler = new JSR88DeploymentHandler(
			JSR88DeploymentHandler.class.getClassLoader(), props);

		return handler;
	}

}