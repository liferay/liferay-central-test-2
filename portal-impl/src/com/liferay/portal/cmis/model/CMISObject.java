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

package com.liferay.portal.cmis.model;

import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Element;
import org.apache.abdera.model.ExtensibleElementWrapper;

/**
 * <a href="CMISObject.java.html"><b><i>View Source</i></b></a>
 *
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

			propertyValue = _dateFormat.format((Date)propertyValue);
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

			return _dateFormat.parse(value);
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
				_cmisConstants.PROPERTY_NAME);

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

	private static DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	private CMISConstants _cmisConstants;

}