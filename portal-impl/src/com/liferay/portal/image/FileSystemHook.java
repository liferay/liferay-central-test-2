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

package com.liferay.portal.image;

import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Image;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <a href="FileSystemHook.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class FileSystemHook extends BaseHook {

	public FileSystemHook() {
		_rootDir = new File(PropsValues.IMAGE_HOOK_FILE_SYSTEM_ROOT_DIR);

		if (!_rootDir.exists()) {
			_rootDir.mkdirs();
		}
	}

	public void deleteImage(Image image) {
		File file = getFile(image.getImageId(), image.getType());

		FileUtil.delete(file);
	}

	public byte[] getImageAsBytes(Image image)
		throws PortalException, SystemException {

		try {
			File file = getFile(image.getImageId(), image.getType());

			if (!file.exists()) {
				throw new NoSuchFileException(file.getPath());
			}

			return FileUtil.getBytes(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public InputStream getImageAsStream(Image image)
		throws PortalException, SystemException {

		try {
			File file = getFile(image.getImageId(), image.getType());

			if (!file.exists()) {
				throw new NoSuchFileException(file.getPath());
			}

			return new FileInputStream(file);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void updateImage(Image image, String type, byte[] bytes)
		throws SystemException {

		try {
			File file = getFile(image.getImageId(), type);

			FileUtil.write(file, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected String buildPath(String fileNameFragment) {
		if (fileNameFragment.length() <= 2) {
			return StringPool.BLANK;
		}

		int fragmentLength = fileNameFragment.length();
		int sbLength = fragmentLength / 2 + fragmentLength;
		StringBuilder sb = new StringBuilder(sbLength);
		
		for (int i = 0;i < fragmentLength;i += 2) {
			if ((i + 2) < fragmentLength) {
				sb.append(StringPool.SLASH);
				sb.append(fileNameFragment.substring(i, i + 2));
			}
		}

		return sb.toString();
	}

	protected File getFile(long imageId, String type) {
		String path = buildPath(String.valueOf(imageId));
		
		return new File(
			_rootDir + StringPool.SLASH + path + StringPool.SLASH +
				imageId + StringPool.PERIOD + type);
	}

	private File _rootDir;

}