/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.xuggle.mediatool.MediaListenerAdapter;
import com.xuggle.mediatool.event.IVideoPictureEvent;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

/**
 * @author Juan González
 * @author Sergio González
 */
public class CaptureFrameListener extends MediaListenerAdapter {
	public CaptureFrameListener(
		File destFile, String extension, int height, int width) {

		_destFile = destFile;
		_extension = extension;
		_height = height;
		_width = width;
	}

	public void onVideoPicture(IVideoPictureEvent event) {
		try {
			if (_written) {
				return;
			}

			if (event.getStreamIndex() != mVideoStreamIndex) {
				if (mVideoStreamIndex == -1) {
					mVideoStreamIndex = event.getStreamIndex();
				}
				else {
					return;
				}
			}

			_written = true;

			_destFile.createNewFile();

			RenderedImage renderedImage = ImageProcessorUtil.scale(
				event.getImage(), _height, _width);

			ImageIO.write(
				renderedImage, _extension, new FileOutputStream(_destFile));
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private int mVideoStreamIndex = -1;

	private File _destFile;
	private String _extension;
	private boolean _written = false;

	private int _height;
	private int _width;

	private static Log _log = LogFactoryUtil.getLog(CaptureFrameListener.class);

}