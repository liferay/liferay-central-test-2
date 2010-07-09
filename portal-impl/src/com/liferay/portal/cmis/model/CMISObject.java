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

package com.liferay.portal.cmis.model;

import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.ExtensibleElementWrapper;

/**
 * @author Alexander Chow
 */
public class CMISObject extends ExtensibleElementWrapper {

	public CMISObject(Element element) {
		super(element);

		_cmisConstants = CMISConstants.getInstance();
	}

	public CMISObject(Factory factory) {
		super(factory, CMISConstants.getInstance().OBJECT);

		_cmisConstants = CMISConstants.getInstance();
	}

	public String getBaseType() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_BASETYPE);
	}

	public String getCheckinComment() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_CHECKIN_COMMENT);
	}

	public String getContentStreamFilename() {
		return getPropertyValue(
			_cmisConstants.PROPERTY_NAME_CONTENT_STREAM_FILENAME);
	}

	public int getContentStreamLength() {
		String value = getPropertyValue(
			_cmisConstants.PROPERTY_NAME_CONTENT_STREAM_LENGTH);

		return GetterUtil.getInteger(value);
	}

	public String getContentStreamMimetype() {
		return getPropertyValue(
			_cmisConstants.PROPERTY_NAME_CONTENT_STREAM_MIMETYPE);
	}

	public String getContentStreamUri() {
		return getPropertyValue(
			_cmisConstants.PROPERTY_NAME_CONTENT_STREAM_URI);
	}

	public String getCreatedBy() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_CREATED_BY);
	}

	public Date getCreationDate() {
		return getDate(_cmisConstants.PROPERTY_NAME_CREATION_DATE);
	}

	public Date getLastModificationDate() {
		return getDate(_cmisConstants.PROPERTY_NAME_LAST_MODIFICATION_DATE);
	}

	public String getLastModifiedBy() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_LAST_MODIFIED_BY);
	}

	public String getName() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_NAME);
	}

	public String getObjectId() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_OBJECT_ID);
	}

	public String getObjectTypeId() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_OBJECT_TYPE_ID);
	}

	public String getSourceId() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_SOURCE_ID);
	}

	public String getTargetId() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_TARGET_ID);
	}

	public String getVersionLabel() {
		return getPropertyValue(_cmisConstants.PROPERTY_NAME_VERSION_LABEL);
	}

	public String getVersionSeriesCheckedOutBy() {
		return getPropertyValue(
			_cmisConstants.PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_BY);
	}

	public String getVersionSeriesCheckedOutId() {
		return getPropertyValue(
			_cmisConstants.PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_ID);
	}

	public String getVersionSeriesId() {
		return getPropertyValue(
			_cmisConstants.PROPERTY_NAME_VERSION_SERIES_ID);
	}

	public boolean isImmutable() {
		return GetterUtil.getBoolean(
			getPropertyValue(_cmisConstants.PROPERTY_NAME_IS_IMMUTABLE));
	}

	public boolean isLatestMajorVersion() {
		return GetterUtil.getBoolean(
			getPropertyValue(
				_cmisConstants.PROPERTY_NAME_IS_LATEST_MAJOR_VERSION));
	}

	public boolean isLatestVersion() {
		return GetterUtil.getBoolean(
			getPropertyValue(_cmisConstants.PROPERTY_NAME_IS_LATEST_VERSION));
	}

	public boolean isMajorVersion() {
		return GetterUtil.getBoolean(
			getPropertyValue(_cmisConstants.PROPERTY_NAME_IS_MAJOR_VERSION));
	}

	public boolean isVersionSeriesCheckedOut() {
		return GetterUtil.getBoolean(
			getPropertyValue(
				_cmisConstants.PROPERTY_NAME_IS_VERSION_SERIES_CHECKED_OUT));
	}

	public void setValue(String propertyName, Serializable propertyValue) {
		Factory factory = getFactory();

		Element propertiesElement = getFirstChild(_cmisConstants.PROPERTIES);

		if (propertiesElement == null) {
			propertiesElement = factory.newElement(_cmisConstants.PROPERTIES);

			addExtension(propertiesElement);
		}

		Element propertyElement = null;

		if (propertyValue instanceof Date) {
			propertyElement = factory.newElement(
				_cmisConstants.PROPERTY_TYPE_DATETIME, propertiesElement);

			propertyValue = _dateFormat.format(propertyValue);
		}
		else if (propertyValue instanceof Double) {
			propertyElement = factory.newElement(
				_cmisConstants.PROPERTY_TYPE_DECIMAL, propertiesElement);
		}
		else if (propertyValue instanceof Integer) {
			propertyElement = factory.newElement(
				_cmisConstants.PROPERTY_TYPE_INTEGER, propertiesElement);
		}
		else if ((propertyValue.toString()).startsWith("http")) {
			propertyElement = factory.newElement(
				_cmisConstants.PROPERTY_TYPE_URI, propertiesElement);
		}
		else {
			propertyElement = factory.newElement(
				_cmisConstants.PROPERTY_TYPE_STRING, propertiesElement);
		}

		if (_cmisConstants instanceof CMISConstants_1_0_0) {
			propertyElement.setAttributeValue(
				_cmisConstants.PROPERTY_DEFINITION_ID, propertyName);
		}
		else {
			propertyElement.setAttributeValue(
				_cmisConstants.PROPERTY_NAME, propertyName);
		}

		Element valueElement = factory.newElement(
			_cmisConstants.PROPERTY_VALUE, propertyElement);

		valueElement.setText(propertyValue.toString());
	}

	protected Date getDate(String propertyName) {
		try {
			String value = getPropertyValue(propertyName);

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				_DATE_FORMAT_PATTERN);

			return dateFormat.parse(value);
		}
		catch (Exception e) {
			return new Date();
		}
	}

	protected List<String> getPropertyNames() {
		List<String> propertyNames = new ArrayList<String>();

		Element propertiesElement = getFirstChild(_cmisConstants.PROPERTIES);

		for (Element propertyElement : propertiesElement.getElements()) {
			String propertyName = propertyElement.getAttributeValue(
				_cmisConstants.PROPERTY_NAME);

			propertyNames.add(propertyName);
		}

		return propertyNames;
	}

	protected String getPropertyValue(String propertyName) {
		String propertyValue = null;

		Element propertiesElement = getFirstChild(_cmisConstants.PROPERTIES);

		for (Element propertyElement : propertiesElement.getElements()) {
			String curPropertyName = propertyElement.getAttributeValue(
				_cmisConstants.PROPERTY_DEFINITION_ID);

			if (propertyName.equals(curPropertyName)) {
				Element propertyValueElement = propertyElement.getFirstChild(
					_cmisConstants.PROPERTY_VALUE);

				if (propertyValueElement != null) {
					propertyValue = propertyValueElement.getText();
				}

				break;
			}
		}

		return propertyValue;
	}

	private static final String _DATE_FORMAT_PATTERN =
		"yyyy-MM-dd'T'HH:mm:ss.SSSZ";

	private static Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat(_DATE_FORMAT_PATTERN);

	private CMISConstants _cmisConstants;

}