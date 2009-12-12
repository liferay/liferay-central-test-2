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

package com.liferay.maven.plugins;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * <a href="PluginDeployerMojo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 * @author Thiago Moreira
 * @goal   deploy
 */
public class PluginDeployerMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException {
		try {
			Log log = getLog();

			log.info(
				"Deploying " + warFileName + " to " +
					autoDeployDir.getAbsolutePath());

			FileUtils.copyFile(warFile, new File(autoDeployDir, warFileName));
		}
		catch (IOException e) {
			throw new MojoExecutionException(
				"Deploying " + warFileName + "failed", e);
		}
	}

	/**
	 * @parameter
	 * @required
	 */
	private File autoDeployDir;

	/**
	 * @parameter expression=
	 *			  "${project.build.directory}/${project.build.finalName}.war"
	 * @required
	 */
	private File warFile;

	/**
	 * @parameter expression="${project.build.finalName}.war"
	 * @required
	 */
	private String warFileName;

}