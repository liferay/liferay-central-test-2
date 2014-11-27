/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.FolderNameException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Adolfo Pérez
 */
public final class DLValidatorImpl implements DLValidator {

	@Override
	public boolean isValidName(String name) {
		if (Validator.isNull(name)) {
			return false;
		}

		for (String blacklistChar : PropsValues.DL_CHAR_BLACKLIST) {
			if (name.contains(blacklistChar)) {
				return false;
			}
		}

		for (String blacklistLastChar : PropsValues.DL_CHAR_LAST_BLACKLIST) {
			if (blacklistLastChar.startsWith(_UNICODE_PREFIX)) {
				blacklistLastChar = UnicodeFormatter.parseString(
					blacklistLastChar);
			}

			if (name.endsWith(blacklistLastChar)) {
				return false;
			}
		}

		String nameWithoutExtension = name;

		if (name.contains(StringPool.PERIOD)) {
			int index = name.lastIndexOf(StringPool.PERIOD);

			nameWithoutExtension = name.substring(0, index);
		}

		for (String blacklistName : PropsValues.DL_NAME_BLACKLIST) {
			if (StringUtil.equalsIgnoreCase(
					nameWithoutExtension, blacklistName)) {

				return false;
			}
		}

		return true;
	}

	@Override
	public void validateDirectoryName(String directoryName)
		throws FolderNameException {

		if (!isValidName(directoryName)) {
			throw new FolderNameException(directoryName);
		}
	}

	@Override
	public void validateFileExtension(String fileName)
		throws FileExtensionException {

		boolean validFileExtension = false;

		String[] fileExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);

		for (String fileExtension : fileExtensions) {
			if (StringPool.STAR.equals(fileExtension) ||
				StringUtil.endsWith(fileName, fileExtension)) {

				validFileExtension = true;

				break;
			}
		}

		if (!validFileExtension) {
			throw new FileExtensionException(fileName);
		}
	}

	@Override
	public void validateFileName(String fileName) throws FileNameException {
		if (!isValidName(fileName)) {
			throw new FileNameException(fileName);
		}
	}

	@Override
	public void validateFileSize(String fileName, byte[] bytes)
		throws FileSizeException {

		if (bytes == null) {
			throw new FileSizeException(fileName);
		}

		validateFileSize(fileName, bytes.length);
	}

	@Override
	public void validateFileSize(String fileName, File file)
		throws FileSizeException {

		if (file == null) {
			throw new FileSizeException(fileName);
		}

		validateFileSize(fileName, file.length());
	}

	@Override
	public void validateFileSize(String fileName, InputStream is)
		throws FileSizeException {

		try {
			if (is == null) {
				throw new FileSizeException(fileName);
			}

			validateFileSize(fileName, is.available());
		}
		catch (IOException ioe) {
			new FileSizeException(ioe);
		}
	}

	@Override
	public void validateFileSize(String fileName, long size)
		throws FileSizeException {

		long maxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if ((maxSize > 0) && (size > maxSize)) {
			throw new FileSizeException(fileName);
		}
	}

	@Override
	public void validateSourceFileExtension(
			String fileExtension, String sourceFileName)
		throws SourceFileNameException {

		String sourceFileExtension = FileUtil.getExtension(sourceFileName);

		if (Validator.isNotNull(sourceFileName) &&
			PropsValues.DL_FILE_EXTENSIONS_STRICT_CHECK &&
			!fileExtension.equals(sourceFileExtension)) {

			throw new SourceFileNameException(sourceFileExtension);
		}
	}

	@Override
	public void validateVersionLabel(String versionLabel)
		throws InvalidFileVersionException {

		if (Validator.isNull(versionLabel)) {
			return;
		}

		if (!DLUtil.isValidVersion(versionLabel)) {
			throw new InvalidFileVersionException();
		}
	}

	private static final String _UNICODE_PREFIX = "\\u";

}