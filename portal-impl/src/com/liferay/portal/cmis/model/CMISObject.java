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

import com.liferay.portal.cmis.CMISUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
 *
 */
public class CMISObject extends ExtensibleElementWrapper {

	public CMISObject(Element internal) {
		super(internal);

		_constants = CMISUtil.getConstants();
	}

	public CMISObject(Factory factory) {
		super(factory, CMISUtil.getConstants().OBJECT);

		_constants = CMISUtil.getConstants();
	}

	public String getBaseType() {
		return getPropertyValue(_constants.PROPERTY_NAME_BASETYPE);
	}

	public String getCheckinComment() {
		return getPropertyValue(_constants.PROPERTY_NAME_CHECKIN_COMMENT);
	}

	public String getContentStreamFilename() {
		return getPropertyValue(
			_constants.PROPERTY_NAME_CONTENT_STREAM_FILENAME);
	}

	public int getContentStreamLength() {
		String value =
			getPropertyValue(_constants.PROPERTY_NAME_CONTENT_STREAM_LENGTH);

		return GetterUtil.getInteger(value);
	}

	public String getContentStreamMimetype() {
		return getPropertyValue(
			_constants.PROPERTY_NAME_CONTENT_STREAM_MIMETYPE);
	}

	public String getContentStreamUri() {
		return getPropertyValue(_constants.PROPERTY_NAME_CONTENT_STREAM_URI);
	}

	public String getCreatedBy() {
		return getPropertyValue(_constants.PROPERTY_NAME_CREATED_BY);
	}

	public Date getCreationDate() {
		return getDate(_constants.PROPERTY_NAME_CREATION_DATE);
	}
	public String getLastModifiedBy() {
		return getPropertyValue(_constants.PROPERTY_NAME_LAST_MODIFIED_BY);
	}

	public Date getLastModificationDate() {
		return getDate(_constants.PROPERTY_NAME_LAST_MODIFICATION_DATE);
	}

	public String getName() {
		return getPropertyValue(_constants.PROPERTY_NAME_NAME);
	}

	public String getObjectId() {
		return getPropertyValue(_constants.PROPERTY_NAME_OBJECT_ID);
	}

	public String getObjectTypeId() {
		return getPropertyValue(_constants.PROPERTY_NAME_OBJECT_TYPE_ID);
	}

	public String getSourceId() {
		return getPropertyValue(_constants.PROPERTY_NAME_SOURCE_ID);
	}

	public String getTargetId() {
		return getPropertyValue(_constants.PROPERTY_NAME_TARGET_ID);
	}

	public String getVersionLabel() {
		return getPropertyValue(_constants.PROPERTY_NAME_VERSION_LABEL);
	}

	public String getVersionSeriesCheckedOutBy() {
		return getPropertyValue(
			_constants.PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_BY);
	}

	public String getVersionSeriesCheckedOutId() {
		return getPropertyValue(
			_constants.PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_ID);
	}

	public String getVersionSeriesId() {
		return getPropertyValue(
			_constants.PROPERTY_NAME_VERSION_SERIES_ID);
	}

	public boolean isImmutable() {
		return GetterUtil.getBoolean(
			getPropertyValue(_constants.PROPERTY_NAME_IS_IMMUTABLE));
	}

	public boolean isLatestMajorVersion() {
		return GetterUtil.getBoolean(
			getPropertyValue(_constants.PROPERTY_NAME_IS_LATEST_MAJOR_VERSION));
	}

	public boolean isLatestVersion() {
		return GetterUtil.getBoolean(
			getPropertyValue(_constants.PROPERTY_NAME_IS_LATEST_VERSION));
	}

	public boolean isMajorVersion() {
		return GetterUtil.getBoolean(
			getPropertyValue(_constants.PROPERTY_NAME_IS_MAJOR_VERSION));
	}

	public boolean isVersionSeriesCheckedOut() {
		return GetterUtil.getBoolean(
			getPropertyValue(
				_constants.PROPERTY_NAME_IS_VERSION_SERIES_CHECKED_OUT));
	}

	public void setValue(String name, Serializable value) {
		Element properties = getFirstChild(_constants.PROPERTIES);

		Factory factory = getFactory();

		if (properties == null) {
			properties = factory.newElement(_constants.PROPERTIES);

			this.addExtension(properties);
		}

		Element property;

		if (value instanceof Integer) {
			property = factory.newElement(
				_constants.PROPERTY_TYPE_INTEGER, properties);
		}
		else if (value instanceof Double) {
			property = factory.newElement(
				_constants.PROPERTY_TYPE_DECIMAL, properties);
		}
		else if (value instanceof Date) {
			property = factory.newElement(
				_constants.PROPERTY_TYPE_DATETIME, properties);

			value = _formatter.format((Date)value);
		}
		else if ((value.toString()).startsWith("http")) {
			property = factory.newElement(
				_constants.PROPERTY_TYPE_URI, properties);
		}
		else {
			property = factory.newElement(
				_constants.PROPERTY_TYPE_STRING, properties);
		}

		property.setAttributeValue(_constants.PROPERTY_NAME, name);

		Element valueNode =
			factory.newElement(_constants.PROPERTY_VALUE, property);

		valueNode.setText(value.toString());
	}

	protected Date getDate(String propertyName) {
		try {
			String value = getPropertyValue(propertyName);

			return _formatter.parse(value);
		}
		catch (Exception e) {
			return new Date();
		}
	}

	protected List<String> getPropertyNames() {
		Element element = getFirstChild(_constants.PROPERTIES);

		List<Element> properties = element.getElements();

		List<String> names = new ArrayList<String>(properties.size());

		for (Element property : properties) {
			names.add(property.getAttributeValue(_constants.PROPERTY_NAME));
		}

		return names;
	}

	protected String getPropertyValue(String propertyName) {
		String text = null;

		Element element = getFirstChild(_constants.PROPERTIES);

		List<Element> properties = element.getElements();

		for (Element property : properties) {
			String attributeValue =
				property.getAttributeValue(_constants.PROPERTY_NAME);

			if (propertyName.equals(attributeValue)) {
				Element value =
					property.getFirstChild(_constants.PROPERTY_VALUE);

				if (value != null) {
					text = value.getText();
				}

				break;
			}
		}

		return text;
	}

	private CMISConstants _constants;

	private static final DateFormat _formatter =
		new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

}