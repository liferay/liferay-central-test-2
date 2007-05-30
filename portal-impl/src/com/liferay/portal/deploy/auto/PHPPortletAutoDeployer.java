/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.deploy.DeployUtil;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="PHPPortletAutoDeployer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class PHPPortletAutoDeployer extends PortletAutoDeployer {

	protected PHPPortletAutoDeployer() throws AutoDeployException {
		try {
			String[] phpJars = new String[] {
				"quercus.jar", "resin-util.jar", "script-10.jar"
			};

			for (int i = 0; i < phpJars.length; i++) {
				String phpJar = phpJars[i];

				jars.add(downloadJar(phpJar));
			}

			jars.add(DeployUtil.getResourcePath("portals-bridges.jar"));
		}
		catch (Exception e) {
			throw new AutoDeployException(e);
		}
	}

	protected void copyXmls(File srcFile, String displayName) throws Exception {
		super.copyXmls(srcFile, displayName);

		Map filterMap = new HashMap();

		filterMap.put("portlet_class", "com.liferay.util.php.PHPPortlet");
		filterMap.put("portlet_name", displayName);
		filterMap.put("portlet_title", displayName);

		copyDependencyXml(
			"liferay-display.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"liferay-portlet.xml", srcFile + "/WEB-INF", filterMap);
		copyDependencyXml(
			"portlet.xml", srcFile + "/WEB-INF", filterMap);
	}

}