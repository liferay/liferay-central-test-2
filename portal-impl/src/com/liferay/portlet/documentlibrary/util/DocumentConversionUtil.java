/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.SystemProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Farache
 */
public class DocumentConversionUtil {

	public static InputStream convert(
			String id, InputStream is, String sourceExtension,
			String targetExtension)
		throws IOException, SystemException {

		return _instance._convert(id, is, sourceExtension, targetExtension);
	}

	public static void disconnect() {
		_instance._disconnect();
	}

	public static String[] getConversions(String extension) {
		return _instance._getConversions(extension);
	}

	public static String getTempFileId(long id, String version) {
		return String.valueOf(id).concat(StringPool.PERIOD).concat(version);
	}

	private DocumentConversionUtil() {
		_conversionsMap.put("svg", _DRAWING_CONVERSIONS);
		_conversionsMap.put("swf", _DRAWING_CONVERSIONS);

		_conversionsMap.put("odp", _PRESENTATION_CONVERSIONS);
		_conversionsMap.put("ppt", _PRESENTATION_CONVERSIONS);
		_conversionsMap.put("pptx", _PRESENTATION_CONVERSIONS);
		_conversionsMap.put("sxi", _PRESENTATION_CONVERSIONS);

		_conversionsMap.put("csv", _SPREADSHEET_CONVERSIONS);
		_conversionsMap.put("ods", _SPREADSHEET_CONVERSIONS);
		_conversionsMap.put("sxc", _SPREADSHEET_CONVERSIONS);
		_conversionsMap.put("tsv", _SPREADSHEET_CONVERSIONS);
		_conversionsMap.put("xls", _SPREADSHEET_CONVERSIONS);
		_conversionsMap.put("xlsx", _SPREADSHEET_CONVERSIONS);

		_conversionsMap.put("doc", _TEXT_CONVERSIONS);
		_conversionsMap.put("docx", _TEXT_CONVERSIONS);
		_conversionsMap.put("htm", _TEXT_CONVERSIONS);
		_conversionsMap.put("html", _TEXT_CONVERSIONS);
		_conversionsMap.put("odt", _TEXT_CONVERSIONS);
		_conversionsMap.put("rtf", _TEXT_CONVERSIONS);
		_conversionsMap.put("sxw", _TEXT_CONVERSIONS);
		_conversionsMap.put("txt", _TEXT_CONVERSIONS);
		_conversionsMap.put("wpd", _TEXT_CONVERSIONS);
	}

	private InputStream _convert(
			String id, InputStream is, String sourceExtension,
			String targetExtension)
		throws IOException, SystemException {

		if (!PrefsPropsUtil.getBoolean(
				PropsKeys.OPENOFFICE_SERVER_ENABLED,
				PropsValues.OPENOFFICE_SERVER_ENABLED)) {

			return null;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
		sb.append("/liferay/document_conversion/");
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(targetExtension);

		String fileName = sb.toString();

		File file = new File(fileName);

		if (!PropsValues.OPENOFFICE_CACHE_ENABLED || !file.exists()) {
			DocumentFormatRegistry registry =
				new DefaultDocumentFormatRegistry();

			DocumentConverter converter = _getConverter(registry);

			if (sourceExtension.equals("htm")) {
				sourceExtension = "html";
			}

			DocumentFormat inputFormat = registry.getFormatByFileExtension(
				sourceExtension);

			UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream();

			DocumentFormat outputFormat = registry.getFormatByFileExtension(
				targetExtension);

			converter.convert(is, inputFormat, ubaos, outputFormat);

			FileUtil.write(file, ubaos.unsafeGetByteArray(), 0, ubaos.size());
		}

		return new FileInputStream(file);
	}

	private void _disconnect() {
		if (_connection != null) {
			_connection.disconnect();
		}
	}

	private String[] _getConversions(String extension) {
		String[] conversions = _conversionsMap.get(extension);

		if (conversions == null) {
			conversions = _DEFAULT_CONVERSIONS;
		}
		else {
			if (ArrayUtil.contains(conversions, extension)) {
				List<String> list = new ArrayList<String>();

				for (int i = 0; i < conversions.length; i++) {
					String conversion = conversions[i];

					if (!conversion.equals(extension)) {
						list.add(conversion);
					}
				}

				conversions = list.toArray(new String[list.size()]);
			}
		}

		return conversions;
	}

	private DocumentConverter _getConverter(DocumentFormatRegistry registry)
		throws SystemException {

		if ((_connection == null) || (_converter == null)) {
			String host = PrefsPropsUtil.getString(
				PropsKeys.OPENOFFICE_SERVER_HOST);
			int port = PrefsPropsUtil.getInteger(
				PropsKeys.OPENOFFICE_SERVER_PORT,
				PropsValues.OPENOFFICE_SERVER_PORT);

			if (_isRemoteOpenOfficeHost(host)) {
				_connection = new SocketOpenOfficeConnection(host, port);
				_converter = new StreamOpenOfficeDocumentConverter(_connection);
			}
			else {
				_connection = new SocketOpenOfficeConnection(port);
				_converter = new OpenOfficeDocumentConverter(_connection);
			}
		}

		return _converter;
	}

	private boolean _isRemoteOpenOfficeHost(String host) {
		if (Validator.isNotNull(host) && !host.equals(_LOCALHOST_IP) &&
			!host.startsWith(_LOCALHOST)) {

			return true;
		}
		else {
			return false;
		}
	}

	private static final String[] _DEFAULT_CONVERSIONS = new String[0];

	private static final String[] _DRAWING_CONVERSIONS = new String[] {"odg"};

	private static final String _LOCALHOST = "localhost";

	private static final String _LOCALHOST_IP = "127.0.0.1";

	private static final String[] _PRESENTATION_CONVERSIONS = new String[] {
		"odp", "pdf", "ppt", "swf", "sxi"
	};

	private static final String[] _SPREADSHEET_CONVERSIONS = new String[] {
		"csv", "ods", "pdf", "sxc", "tsv", "xls"
	};

	private static final String[] _TEXT_CONVERSIONS = new String[] {
		"doc", "odt", "pdf", "rtf", "sxw", "txt"
	};

	private static DocumentConversionUtil _instance =
		new DocumentConversionUtil();

	private Map<String, String[]> _conversionsMap =
		new HashMap<String, String[]>();
	private OpenOfficeConnection _connection;
	private DocumentConverter _converter;

}