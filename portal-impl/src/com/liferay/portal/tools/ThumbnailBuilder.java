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

package com.liferay.portal.tools;

import com.liferay.portal.image.ImageProcessorImpl;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.util.GetterUtil;

import java.awt.image.RenderedImage;

import java.io.File;

import javax.imageio.ImageIO;

/**
 * <a href="ThumbnailBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ThumbnailBuilder {

	public static void main(String[] args) {
		File originalFile = new File(
			System.getProperty("thumbnail.original.file"));
		File thumbnailFile = new File(
			System.getProperty("thumbnail.thumbnail.file"));
		int height = GetterUtil.getInteger(
			System.getProperty("thumbnail.height"));
		int width = GetterUtil.getInteger(
			System.getProperty("thumbnail.width"));
		boolean overwrite = GetterUtil.getBoolean(
			System.getProperty("thumbnail.overwrite"));

		new ThumbnailBuilder(
			originalFile, thumbnailFile, height, width, overwrite);
	}

	public ThumbnailBuilder(
		File originalFile, File thumbnailFile, int height, int width,
		boolean overwrite) {

		try {
			if (!originalFile.exists()) {
				return;
			}

			if (!overwrite) {
				if (thumbnailFile.lastModified() >
						originalFile.lastModified()) {

					return;
				}
			}

			ImageBag imageBag = _imageProcessorUtil.read(originalFile);

			RenderedImage thumbnail = _imageProcessorUtil.scale(
				imageBag.getRenderedImage(), height, width);

			ImageIO.write(thumbnail, imageBag.getType(), thumbnailFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ImageProcessorImpl _imageProcessorUtil =
		ImageProcessorImpl.getInstance();

}