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
import com.liferay.portal.shared.util.ServerDetector;
import com.liferay.portal.tools.PortletDeployer;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="AutoPortletDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ivica Cardic
 * @author  Brian Wing Shun Chan
 *
 */
public class AutoPortletDeployer
	extends PortletDeployer implements AutoDeployer {

	public AutoPortletDeployer() {
		try {
			baseDir = OmniadminUtil.getAutoDeployDeployDir();
			destDir = OmniadminUtil.getAutoDeployDestDir();
			appServerType = ServerDetector.getServerId();
			portletTaglibDTD = DeployUtil.getResourcePath(
				"liferay-portlet.tld");
			unpackWar = OmniadminUtil.getAutoDeployUnpackWar();
			jbossPrefix = OmniadminUtil.getAutoDeployJbossPrefix();
			tomcatLibDir = OmniadminUtil.getAutoDeployTomcatLibDir();

			List jars = new ArrayList();

			jars.add(DeployUtil.getResourcePath("util-java.jar"));
			jars.add(DeployUtil.getResourcePath("util-jsf.jar"));
			jars.add(DeployUtil.getResourcePath("util-taglib.jar"));

			this.jars = jars;

			checkArguments();
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	public void deploy(String file) throws AutoDeployException {
		List wars = new ArrayList();

		wars.add(file);

		this.wars = wars;

		try {
			deploy();
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	private static Log _log = LogFactory.getLog(AutoPortletDeployer.class);

}