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

package com.liferay.portal.service.impl;

import com.liferay.portal.model.Image;
import com.liferay.portal.service.spring.ImageLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.ImageUtil;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ImageLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Michael Weisser
 *
 */
public class ImageLocalUtil {

	public static Image get(String id) {
		Image image = null;

		try {
			image = ImageLocalServiceUtil.getImage(id);
		}
		catch (Exception e) {
			_log.warn(e);
		}

		return image;
	}

	public static Image getDefaultSpacer() {
		return _instance._defaultSpacer;
	}

	public static Image getDefaultUserPortrait() {
		return _instance._defaultUserPortrait;
	}

	public static boolean isNullOrDefaultSpacer(byte[] bytes) {
		if ((bytes == null) || (bytes.length == 0) ||
			(Arrays.equals(bytes, getDefaultSpacer().getTextObj()))) {

			return true;
		}
		else {
			return false;
		}
	}

	public static void put(String id, byte[] bytes) {
		try {
			ImageLocalServiceUtil.updateImage(id, bytes);
		}
		catch (Exception e) {
			_log.warn(e);
		}
	}

	public static void remove(String id) {
		try {
			ImageLocalServiceUtil.deleteImage(id);
		}
		catch (Exception e) {
			_log.warn(e);
		}
	}

	private ImageLocalUtil() {
		ClassLoader classLoader = getClass().getClassLoader();

		_defaultSpacer = new Image();

		_defaultSpacer.setTextObj(ImageUtil.read(
			classLoader, PropsUtil.get(PropsUtil.IMAGE_DEFAULT_SPACER)));

		_defaultUserPortrait = new Image();

		_defaultUserPortrait.setTextObj(ImageUtil.read(
			classLoader, PropsUtil.get(PropsUtil.IMAGE_DEFAULT_USER_PORTRAIT)));
	}

	private static Log _log = LogFactory.getLog(ImageLocalUtil.class);

	private static ImageLocalUtil _instance = new ImageLocalUtil();

	private Image _defaultSpacer;
	private Image _defaultUserPortrait;

}