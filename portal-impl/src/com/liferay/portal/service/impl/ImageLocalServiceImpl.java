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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.impl.ImageImpl;
import com.liferay.portal.service.base.ImageLocalServiceBaseImpl;
import com.liferay.portal.util.PropsUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ImageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ImageLocalServiceImpl extends ImageLocalServiceBaseImpl {

	public ImageLocalServiceImpl() {
		ClassLoader classLoader = getClass().getClassLoader();

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsUtil.IMAGE_DEFAULT_SPACER));

			if (is == null) {
				_log.error("Default spacer is not available");
			}

			_defaultSpacer = getImage(is);
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to configure the default spacer: " + ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsUtil.IMAGE_DEFAULT_COMPANY_LOGO));

			if (is == null) {
				_log.error("Default company logo is not available");
			}

			_defaultCompanyLogo = getImage(is);
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to configure the default company logo: " +
					ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsUtil.IMAGE_DEFAULT_USER_FEMALE_PORTRAIT));

			if (is == null) {
				_log.error("Default user female portrait is not available");
			}

			_defaultUserFemalePortrait = getImage(is);
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to configure the default user female portrait: " +
					ioe.getMessage());
		}

		try {
			InputStream is = classLoader.getResourceAsStream(
				PropsUtil.get(PropsUtil.IMAGE_DEFAULT_USER_MALE_PORTRAIT));

			if (is == null) {
				_log.error("Default user male portrait is not available");
			}

			_defaultUserMalePortrait = getImage(is);
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to configure the default user male portrait: " +
					ioe.getMessage());
		}
	}

	public void deleteImage(long imageId) throws SystemException {
		try {
			if (imageId > 0) {
				imagePersistence.remove(imageId);
			}
		}
		catch (NoSuchImageException nsie) {
		}
	}

	public Image getCompanyLogo(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			image = getDefaultCompanyLogo();
		}

		return image;
	}

	public Image getDefaultCompanyLogo() {
		return _defaultCompanyLogo;
	}

	public Image getDefaultSpacer() {
		return _defaultSpacer;
	}

	public Image getDefaultUserFemalePortrait() {
		return _defaultUserFemalePortrait;
	}

	public Image getDefaultUserMalePortrait() {
		return _defaultUserMalePortrait;
	}

	public Image getImage(long imageId) {
		try {
			if (imageId > 0) {
				return imagePersistence.findByPrimaryKey(imageId);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get image " + imageId + ": " + e.getMessage());
			}
		}

		return null;
	}

	public Image getImage(byte[] bytes) throws IOException {
		return getImage(null, bytes);
	}

	public Image getImage(File file) throws IOException {
		return getImage(new FileInputStream(file));
	}

	public Image getImage(InputStream is) throws IOException {
		return getImage(is, null);
	}

	public Image getImageOrDefault(long imageId) {
		Image image = getImage(imageId);

		if (image == null) {
			image = getDefaultSpacer();
		}

		return image;
	}

	public List<Image> getImages() throws SystemException {
		return imagePersistence.findAll();
	}

	public List<Image> getImages(int start, int end) throws SystemException {
		return imagePersistence.findAll(start, end);
	}

	public List<Image> getImagesBySize(int size) throws SystemException {
		return imagePersistence.findBySize(size);
	}

	public boolean isNullOrDefaultSpacer(byte[] bytes) {
		if ((bytes == null) || (bytes.length == 0) ||
			(Arrays.equals(bytes, getDefaultSpacer().getTextObj()))) {

			return true;
		}
		else {
			return false;
		}
	}

	public Image updateImage(long imageId, byte[] bytes)
		throws SystemException {

		try {
			Image image = getImage(bytes);

			return updateImage(
				imageId, image.getTextObj(), image.getType(), image.getHeight(),
				image.getWidth(), image.getSize());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Image updateImage(long imageId, File file)
		throws SystemException {

		try {
			Image image = getImage(file);

			return updateImage(
				imageId, image.getTextObj(), image.getType(), image.getHeight(),
				image.getWidth(), image.getSize());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Image updateImage(long imageId, InputStream is)
		throws SystemException {

		try {
			Image image = getImage(is);

			return updateImage(
				imageId, image.getTextObj(), image.getType(), image.getHeight(),
				image.getWidth(), image.getSize());
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Image updateImage(
			long imageId, byte[] bytes, String type, int height, int width,
			int size)
		throws SystemException {

		Image image = imagePersistence.fetchByPrimaryKey(imageId);

		if (image == null) {
			image = imagePersistence.create(imageId);
		}

		image.setModifiedDate(new Date());
		image.setTextObj(bytes);
		image.setType(type);
		image.setHeight(height);
		image.setWidth(width);
		image.setSize(size);

		imagePersistence.update(image, false);

		ImageServletTokenUtil.resetToken(imageId);

		return image;
	}

	protected Image getImage(InputStream is, byte[] bytes) throws IOException {
		try {
			if (is != null) {
				bytes = FileUtil.getBytes(is);
			}

			ImageBag imageBag = ImageProcessorUtil.read(bytes);

			RenderedImage renderedImage = imageBag.getRenderedImage();
			String type = imageBag.getType();

			if (renderedImage == null) {
				throw new IOException(
					"Unable to retreive rendered image from input stream " +
						"with type " + type);
			}

			int height = renderedImage.getHeight();
			int width = renderedImage.getWidth();
			int size = bytes.length;

			Image image = new ImageImpl();

			image.setTextObj(bytes);
			image.setType(type);
			image.setHeight(height);
			image.setWidth(width);
			image.setSize(size);

			return image;
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException ioe) {
					if (_log.isWarnEnabled()) {
						_log.warn(ioe);
					}
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(ImageLocalServiceImpl.class);

	private Image _defaultSpacer;
	private Image _defaultCompanyLogo;
	private Image _defaultUserFemalePortrait;
	private Image _defaultUserMalePortrait;

}