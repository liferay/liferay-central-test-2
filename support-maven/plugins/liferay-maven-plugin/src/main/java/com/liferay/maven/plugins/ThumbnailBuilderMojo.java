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

import com.liferay.portal.tools.ThumbnailBuilder;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * <a href="ThumbnailBuilderMojo.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 * @goal   build-thumbnail
 * @phase  process-sources
 */
public class ThumbnailBuilderMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException {
		new ThumbnailBuilder(
			originalFile, thumbnailFile, height, width, overwrite);
	}

	/**
	 * @parameter default-value="120"
	 * @required
	 */
	private int height;

	/**
	 * @parameter expression="${basedir}/src/main/webapp/images/screenshot.png"
	 * @required
	 */
	private File originalFile;

	/**
	 * @parameter default-value="false"
	 * @required
	 */
	private boolean overwrite;

	/**
	 * @parameter expression="${basedir}/src/main/webapp/images/thumbnail.png"
	 * @required
	 */
	private File thumbnailFile;

	/**
	 * @parameter default-value="160"
	 * @required
	 */
	private int width;

}