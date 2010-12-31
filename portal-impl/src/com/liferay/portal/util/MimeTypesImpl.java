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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;
import eu.medsea.mimeutil.TextMimeType;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.util.EncodingGuesser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

/**
 * <p>
 * Additional MIME types should be added to META-INF/mime.types or magic.mime.
 * </p>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MimeTypesImpl implements MimeTypes {

	public MimeTypesImpl() {
		MimeUtil.registerMimeDetector(MagicMimeMimeDetector.class.getName());

		Collection<String> encodings = new HashSet<String>();

		encodings.add(StringPool.UTF8);
		encodings.add(System.getProperty("file.encoding"));

		EncodingGuesser.setSupportedEncodings(encodings);
	}

	public String getContentType(File file) {
		try {
			return getContentType(new FileInputStream(file), file.getName());
		}
		catch (FileNotFoundException fnfe) {
			return getContentType(file.getName());
		}
	}

	public String getContentType(InputStream inputStream, String fileName) {
		String contentType = getContentType(fileName);

		if (!contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {
			return contentType;
		}

		List<MimeType> mimeTypes = new ArrayList<MimeType>(
			MimeUtil.getMimeTypes(inputStream));

		MimeType mimeType = mimeTypes.get(0);

		if ((mimeTypes.size() > 1) && (mimeType instanceof TextMimeType)) {
			mimeType = mimeTypes.get(1);
		}

		contentType =
			mimeType.getMediaType() + StringPool.SLASH + mimeType.getSubType();

		if (contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {
			contentType = getContentType(fileName);
		}
		else if (contentType.equals(ContentTypes.APPLICATION_ZIP)) {
			String contentTypeByFileName = getContentType(fileName);

			if (contentTypeByFileName.contains("vnd.openxmlformats")) {
				contentType = contentTypeByFileName;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Content type " + contentType + " returned for " + fileName);
		}

		return contentType;
	}

	public String getContentType(String fileName) {
		if (Validator.isNull(fileName)) {
			return ContentTypes.APPLICATION_OCTET_STREAM;
		}

		if (!fileName.contains(StringPool.PERIOD)) {
			fileName = StringPool.PERIOD + fileName;
		}

		String contentType = _mimeTypes.getContentType(fileName);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Content type " + contentType + " returned for file name " +
					fileName);
		}

		return contentType;
	}

	private static Log _log = LogFactoryUtil.getLog(MimeTypesImpl.class);

	private MimetypesFileTypeMap _mimeTypes = new MimetypesFileTypeMap();

}