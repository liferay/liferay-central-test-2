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

import com.liferay.portal.util.PropsValues;

import javax.xml.namespace.QName;

/**
 * <a href="CMISConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public abstract class CMISConstants {

	public static CMISConstants getInstance() {
		String version = PropsValues.CMIS_REPOSITORY_VERSION;

		if (version.equals("0.61")) {
			return CMISConstants_0_6_1.getInstance();
		}
		else if (version.equals("1.0")) {
			return CMISConstants_1_0_0.getInstance();
		}
		else {
			throw new RuntimeException("Unsupported CMIS version");
		}
	}

	public String CMIS_NS = null;

	public String CMIS_PREFIX = null;

	public String CMISRA_NS = null;

	public String CMISRA_PREFIX = null;

	public String VERSION = null;

	// Repository

	public QName REPOSITORY_DESCRIPTION = null;

	public QName REPOSITORY_ID = null;

	public QName REPOSITORY_INFO = null;

	public QName REPOSITORY_NAME = null;

	public QName REPOSITORY_PRODUCT_NAME = null;

	public QName REPOSITORY_PRODUCT_VERSION = null;

	public QName REPOSITORY_RELATIONSHIP = null;

	public QName REPOSITORY_ROOT_FOLDER_ID = null;

	public QName REPOSITORY_SPECIFIC_INFO = null;

	public QName REPOSITORY_VENDOR_NAME = null;

	public QName REPOSITORY_VERSION_SUPPORTED = null;

	// Collection

	public QName COLLECTION_TYPE = null;

	public String COLLECTION_CHECKEDOUT = null;

	public String COLLECTION_QUERY = null;

	public String COLLECTION_ROOT = null;

	public String COLLECTION_ROOT_DESCENDANTS = null;

	public String COLLECTION_TYPES_CHILDREN = null;

	public String COLLECTION_TYPES_DESCENDANTS = null;

	public String COLLECTION_UNFILED = null;

	// Object

	public QName OBJECT = null;

	public String BASE_TYPE_DOCUMENT = null;

	public String BASE_TYPE_FOLDER = null;

	public String LINK_ALL_VERSIONS = null;

	public String LINK_ALLOWABLE_ACTIONS = null;

	public String LINK_CHILDREN = null;

	public String LINK_DESCENDANTS = null;

	public String LINK_LATEST_VERSION = null;

	public String LINK_PARENT = null;

	public String LINK_PARENTS = null;

	public String LINK_POLICIES = null;

	public String LINK_RELATIONSHIPS = null;

	public String LINK_REPOSITORY = null;

	public String LINK_SOURCE = null;

	public String LINK_STREAM = null;

	public String LINK_TARGET = null;

	public String LINK_TYPE = null;

	public QName PROPERTIES = null;

	public String PROPERTY_DEFINITION_ID = null;

	public QName PROPERTY_NAME = null;

	public QName PROPERTY_TYPE_STRING = null;

	public QName PROPERTY_TYPE_DECIMAL = null;

	public QName PROPERTY_TYPE_INTEGER = null;

	public QName PROPERTY_TYPE_BOOLEAN = null;

	public QName PROPERTY_TYPE_DATETIME = null;

	public QName PROPERTY_TYPE_URI = null;

	public QName PROPERTY_TYPE_ID = null;

	public QName PROPERTY_TYPE_XML = null;

	public QName PROPERTY_TYPE_HTML = null;

	public QName PROPERTY_VALUE = null;

	public String PROPERTY_NAME_BASETYPE = null;

	public String PROPERTY_NAME_CHECKIN_COMMENT = null;

	public String PROPERTY_NAME_CONTENT_STREAM_FILENAME = null;

	public String PROPERTY_NAME_CONTENT_STREAM_LENGTH = null;

	public String PROPERTY_NAME_CONTENT_STREAM_MIMETYPE = null;

	public String PROPERTY_NAME_CONTENT_STREAM_URI = null;

	public String PROPERTY_NAME_CREATED_BY = null;

	public String PROPERTY_NAME_CREATION_DATE = null;

	public String PROPERTY_NAME_IS_IMMUTABLE = null;

	public String PROPERTY_NAME_IS_LATEST_MAJOR_VERSION = null;

	public String PROPERTY_NAME_IS_LATEST_VERSION = null;

	public String PROPERTY_NAME_IS_MAJOR_VERSION = null;

	public String PROPERTY_NAME_IS_VERSION_SERIES_CHECKED_OUT = null;

	public String PROPERTY_NAME_LAST_MODIFIED_BY = null;

	public String PROPERTY_NAME_LAST_MODIFICATION_DATE = null;

	public String PROPERTY_NAME_NAME = null;

	public String PROPERTY_NAME_OBJECT_ID = null;

	public String PROPERTY_NAME_OBJECT_TYPE_ID = null;

	public String PROPERTY_NAME_SOURCE_ID = null;

	public String PROPERTY_NAME_TARGET_ID = null;

	public String PROPERTY_NAME_VERSION_LABEL = null;

	public String PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_BY = null;

	public String PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_ID = null;

	public String PROPERTY_NAME_VERSION_SERIES_ID = null;

}