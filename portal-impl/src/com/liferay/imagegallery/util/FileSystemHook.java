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

package com.liferay.imagegallery.util;

import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <a href="FileSystemHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class FileSystemHook extends BaseHook {

	public FileSystemHook() {
		_rootDir = new File(_ROOT_DIR);

		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	public void deleteImage(Image image)
		throws PortalException, SystemException {

		File file = getImageFile(image.getImageId(), image.getType());

		FileUtil.delete(file);
	}

	public byte[] getImageAsBytes(Image image)
		throws PortalException, SystemException {

		try {
			File file = getImageFile(image.getImageId(), image.getType());

			if (!file.exists()) {
				throw new NoSuchFileException(file.getPath());
			}

			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException();
		}
	}

	public InputStream getImageAsStream(Image image)
		throws PortalException, SystemException {

		try {
			File file = getImageFile(image.getImageId(), image.getType());

			if (!file.exists()) {
				throw new NoSuchFileException(file.getPath());
			}

			return new FileInputStream(file);
		}
		catch (IOException ioe) {
			throw new SystemException();
		}
	}

	public void updateImage(Image image, String type, byte[] bytes)
		throws SystemException {

		try {
			File file = getImageFile(image.getImageId(), type);

			FileUtil.write(file, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException();
		}
	}

	protected File getImageFile(long imageId, String type) {

		File fileDir = getAbsoluteImageDir(imageId);

		String fileName = imageId + StringPool.PERIOD + type;

		File file = new File(fileDir + StringPool.SLASH + fileName);

		return file;
	}

	protected File getAbsoluteImageDir(long imageId) {
		return getAbsoluteImageDir(String.valueOf(imageId));
	}

	protected File getAbsoluteImageDir(String imageId) {
		return new File(_rootDir + StringPool.SLASH + getImageDir(imageId));
	}

	protected String getImageDir(String imageId) {

		String path = null;

		if (imageId.length() <= 2) {
			path = imageId;
		}
		else {
			path =
				imageId.substring(0, 2) + StringPool.SLASH +
					getImageDir(imageId.substring(2));
		}

		return path;
	}

	private static final String _ROOT_DIR = PropsUtil.get(
		PropsKeys.IG_HOOK_FILE_SYSTEM_ROOT_DIR);

	private File _rootDir;

}