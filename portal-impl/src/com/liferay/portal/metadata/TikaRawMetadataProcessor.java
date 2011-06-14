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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portlet.dynamicdatamapping.StorageException;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import org.apache.tika.Tika;
import org.apache.tika.metadata.ClimateForcast;
import org.apache.tika.metadata.CreativeCommons;
import org.apache.tika.metadata.DublinCore;
import org.apache.tika.metadata.Geographic;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.MSOffice;
import org.apache.tika.metadata.Message;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.Property;
import org.apache.tika.metadata.TIFF;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.metadata.TikaMimeKeys;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Pastor
 */
public class TikaRawMetadataProcessor implements RawMetadataProcessor {

	public static final
		Map<String, java.lang.reflect.Field[]> RAW_METADATA_SETS =
			new HashMap<String, java.lang.reflect.Field[]>();

	public Map<String, Fields> getRawMetadataMap(InputStream is)
		throws PortalException, SystemException {

		Metadata metadata = extractMetadata(is);

		return createDDMFieldsMap(metadata, RAW_METADATA_SETS);
	}

	public void setTika(Tika tika) {
		_tika = tika;
	}

	protected Metadata extractMetadata(InputStream inputStream) {
		Metadata metadata = new Metadata();

		try {
			_tika.parse(inputStream, metadata);
		}
		catch (IOException e) {

		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException e) {
			}
		}

		return metadata;
	}

	protected String getMetadataValue(
		Metadata metadata, java.lang.reflect.Field beanField) {

		Object fieldValue = getFieldValue(metadata, beanField);

		String metadataKey = null;

		if (fieldValue instanceof String) {
			metadataKey = (String)fieldValue;
		}
		else {
			metadataKey = ((Property)fieldValue).getName();
		}

		return metadata.get(metadataKey);
	}

	protected Fields createDDMFields(
			Metadata metadata, java.lang.reflect.Field[] beanFields)
		throws StorageException {

		Fields ddmFields = new Fields();

		for (java.lang.reflect.Field beanField : beanFields) {
			String value = getMetadataValue(metadata, beanField);

			if (value != null) {
				ddmFields.put(
					new com.liferay.portlet.dynamicdatamapping.storage.Field(
						beanField.getName(), value));
			}
		}

		return ddmFields;
	}

	protected Map<String, Fields> createDDMFieldsMap(
			Metadata metadata, Map<String, java.lang.reflect.Field[]> fieldsMap)
		throws SystemException, StorageException {

		Map<String, Fields> ddmFieldsMap = new HashMap<String, Fields>();

		for (String key : fieldsMap.keySet()) {
			Fields ddmFields = createDDMFields(metadata, fieldsMap.get(key));

			if (ddmFields.getNames().size() > 0) {
				ddmFieldsMap.put(key, ddmFields);
			}
		}

		return ddmFieldsMap;
	}

	protected Object getFieldValue(
		Metadata metadata, java.lang.reflect.Field beanField) {

		Object fieldValue = null;

		try {
			fieldValue = beanField.get(metadata);
		}
		catch (IllegalAccessException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"The property " + beanField.getName() +
					" will not be added to the metatada set");
			}
		}

		return fieldValue;
	}

	private static final java.lang.reflect.Field[] _CLIMATE_FOR_CAST_FIELDS =
		ClimateForcast.class.getFields();

	private static final java.lang.reflect.Field[]
		_CREATIVE_COMMONS_FIELDS = CreativeCommons.class.getFields();

	private static final java.lang.reflect.Field[] _DUBLIN_CORE_FIELDS =
		DublinCore.class.getFields();

	private static final java.lang.reflect.Field[] _GEOGRAPHIC_FIELDS =
		Geographic.class.getFields();

	private static final java.lang.reflect.Field[] _HTTP_HEADERS_FIELDS =
		HttpHeaders.class.getFields();

	private static final java.lang.reflect.Field[] _MESSAGE_FIELDS =
		Message.class.getFields();

	private static final java.lang.reflect.Field[] _MS_OFFICE_FIELDS =
		MSOffice.class.getFields();

	private static final java.lang.reflect.Field[]
		_TIKA_METADATA_KEYS_FIELDS = TikaMetadataKeys.class.getFields();

	private static final java.lang.reflect.Field[] _TIKA_MIME_KEYS_FIELDS =
		TikaMimeKeys.class.getFields();

	private static final java.lang.reflect.Field[]
		_TIFF_FIELDS = TIFF.class.getFields();

	private static Log _log = LogFactoryUtil.getLog(TikaRawMetadataProcessor.class);

	private Tika _tika;

	static {
		RAW_METADATA_SETS.put(
			ClimateForcast.class.getSimpleName(), 
			_CLIMATE_FOR_CAST_FIELDS);
		RAW_METADATA_SETS.put(
			CreativeCommons.class.getSimpleName(), 
			_CREATIVE_COMMONS_FIELDS);
		RAW_METADATA_SETS.put(
			DublinCore.class.getSimpleName(), _DUBLIN_CORE_FIELDS);
		RAW_METADATA_SETS.put(
			Geographic.class.getSimpleName(), _GEOGRAPHIC_FIELDS);
		RAW_METADATA_SETS.put(
			HttpHeaders.class.getSimpleName(), _HTTP_HEADERS_FIELDS);
		RAW_METADATA_SETS.put(
			Message.class.getSimpleName(), _MESSAGE_FIELDS);
		RAW_METADATA_SETS.put(
			MSOffice.class.getSimpleName(), _MS_OFFICE_FIELDS);
		RAW_METADATA_SETS.put(
			TikaMetadataKeys.class.getSimpleName(),
			_TIKA_METADATA_KEYS_FIELDS);
		RAW_METADATA_SETS.put(
			TikaMimeKeys.class.getSimpleName(), _TIKA_MIME_KEYS_FIELDS);
		RAW_METADATA_SETS.put(TIFF.class.getSimpleName(), _TIFF_FIELDS);
	}

}
