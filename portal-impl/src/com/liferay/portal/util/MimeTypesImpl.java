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
import eu.medsea.util.EncodingGuesser;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

/**
 * <a href="MimeTypesImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class MimeTypesImpl implements MimeTypes {

	public String getContentType(InputStream is, String fileName) {
		List<MimeType> types =
			new ArrayList<MimeType>(MimeUtil.getMimeTypes(is));

		MimeType type = types.get(0);

		if (types.size() > 1 && (type instanceof TextMimeType)) {
			type = types.get(1);
		}

		String contentType = type.getMediaType() + "/" + type.getSubType();

		if (contentType.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {
			contentType = getContentType(fileName);
		}
		else if (contentType.equals(ContentTypes.APPLICATION_ZIP)) {
			String typeByFileName = getContentType(fileName);

			if (typeByFileName.contains("vnd.openxmlformats")) {
				contentType = typeByFileName;
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Content type " + contentType + " returned for InputStream");
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

	static {
		MimeUtil.registerMimeDetector(
			"eu.medsea.mimeutil.detector.MagicMimeMimeDetector");

		Collection<String> encodings = new HashSet<String>();

		encodings.add(StringPool.UTF8);
		encodings.add(System.getProperty("file.encoding"));

		EncodingGuesser.setSupportedEncodings(encodings);
	}

}