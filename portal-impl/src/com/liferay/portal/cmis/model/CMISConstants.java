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

import javax.xml.namespace.QName;

/**
 * <a href="CMISConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public abstract class CMISConstants {

	public static CMISConstants getInstance(String cmisVersion) {
		if (cmisVersion.equals("0.61")) {
			return CMISConstants_0_6_1.getInstance();
		}
		else {
			throw new RuntimeException("Unsupported CMIS version");
		}
	}

	public String NS;

	public String PREFIX;

	public String VERSION;

	// Repository

	public QName REPOSITORY_DESCRIPTION;

	public QName REPOSITORY_ID;

	public QName REPOSITORY_INFO;

	public QName REPOSITORY_NAME;

	public QName REPOSITORY_PRODUCT_NAME;

	public QName REPOSITORY_PRODUCT_VERSION;

	public QName REPOSITORY_RELATIONSHIP;

	public QName REPOSITORY_ROOT_FOLDER_ID;

	public QName REPOSITORY_SPECIFIC_INFO;

	public QName REPOSITORY_VENDOR_NAME;

	public QName REPOSITORY_VERSION_SUPPORTED;

	// Collection

	public QName COLLECTION_TYPE;

	public String COLLECTION_CHECKEDOUT;

	public String COLLECTION_QUERY;

	public String COLLECTION_ROOT_CHILDREN;

	public String COLLECTION_ROOT_DESCENDANTS;

	public String COLLECTION_TYPES_CHILDREN;

	public String COLLECTION_TYPES_DESCENDANTS;

	public String COLLECTION_UNFILED;

	// Object

	public QName OBJECT;

	public String BASE_TYPE_DOCUMENT;

	public String BASE_TYPE_FOLDER;

	public String LINK_ALL_VERSIONS;

	public String LINK_ALLOWABLE_ACTIONS;

	public String LINK_CHILDREN;

	public String LINK_DESCENDANTS;

	public String LINK_LATEST_VERSION;

	public String LINK_PARENT;

	public String LINK_PARENTS;

	public String LINK_POLICIES;

	public String LINK_RELATIONSHIPS;

	public String LINK_REPOSITORY;

	public String LINK_SOURCE;

	public String LINK_STREAM;

	public String LINK_TARGET;

	public String LINK_TYPE;

	public QName PROPERTIES;

	public QName PROPERTY_NAME;

	public QName PROPERTY_TYPE_STRING;

	public QName PROPERTY_TYPE_DECIMAL;

	public QName PROPERTY_TYPE_INTEGER;

	public QName PROPERTY_TYPE_BOOLEAN;

	public QName PROPERTY_TYPE_DATETIME;

	public QName PROPERTY_TYPE_URI;

	public QName PROPERTY_TYPE_ID;

	public QName PROPERTY_TYPE_XML;

	public QName PROPERTY_TYPE_HTML;

	public QName PROPERTY_VALUE;

	public String PROPERTY_NAME_BASETYPE;

	public String PROPERTY_NAME_CHECKIN_COMMENT;

	public String PROPERTY_NAME_CONTENT_STREAM_FILENAME;

	public String PROPERTY_NAME_CONTENT_STREAM_LENGTH;

	public String PROPERTY_NAME_CONTENT_STREAM_MIMETYPE;

	public String PROPERTY_NAME_CONTENT_STREAM_URI;

	public String PROPERTY_NAME_CREATED_BY;

	public String PROPERTY_NAME_CREATION_DATE;

	public String PROPERTY_NAME_IS_IMMUTABLE;

	public String PROPERTY_NAME_IS_LATEST_MAJOR_VERSION;

	public String PROPERTY_NAME_IS_LATEST_VERSION;

	public String PROPERTY_NAME_IS_MAJOR_VERSION;

	public String PROPERTY_NAME_IS_VERSION_SERIES_CHECKED_OUT;

	public String PROPERTY_NAME_LAST_MODIFIED_BY;

	public String PROPERTY_NAME_LAST_MODIFICATION_DATE;

	public String PROPERTY_NAME_NAME;

	public String PROPERTY_NAME_OBJECT_ID;

	public String PROPERTY_NAME_OBJECT_TYPE_ID;

	public String PROPERTY_NAME_SOURCE_ID;

	public String PROPERTY_NAME_TARGET_ID;

	public String PROPERTY_NAME_VERSION_LABEL;

	public String PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_BY;

	public String PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_ID;

	public String PROPERTY_NAME_VERSION_SERIES_ID;

}