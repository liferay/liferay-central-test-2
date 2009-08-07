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
 * <a href="CMISConstants_0_6_1.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * CMIS constants supporting the draft version 0.61.
 * </p>
 *
 * @author Alexander Chow
 *
 */
public class CMISConstants_0_6_1 extends CMISConstants {

	public CMISConstants_0_6_1() {
		NS = "http://docs.oasis-open.org/ns/cmis/core/200901";

		PREFIX = "cmis";

		VERSION = "0.61";

		// Repository

		REPOSITORY_DESCRIPTION = new QName(NS, "repositoryDescription", PREFIX);

		REPOSITORY_ID = new QName(NS, "repositoryId", PREFIX);

		REPOSITORY_INFO = new QName(NS, "repositoryInfo", PREFIX);

		REPOSITORY_NAME = new QName(NS, "repositoryName", PREFIX);

		REPOSITORY_PRODUCT_NAME = new QName(NS, "productName", PREFIX);

		REPOSITORY_PRODUCT_VERSION = new QName(NS, "productVersion", PREFIX);

		REPOSITORY_RELATIONSHIP = new QName(NS, "repositoryRelationship", PREFIX);

		REPOSITORY_ROOT_FOLDER_ID = new QName(NS, "rootFolderId", PREFIX);

		REPOSITORY_SPECIFIC_INFO = new QName(NS, "repositorySpecificInformation", PREFIX);

		REPOSITORY_VENDOR_NAME = new QName(NS, "vendorName", PREFIX);

		REPOSITORY_VERSION_SUPPORTED = new QName(NS, "cmisVersionSupported", PREFIX);

		// Collection

		COLLECTION_TYPE = new QName(NS, "collectionType", PREFIX);

		COLLECTION_CHECKEDOUT = "checkedout";

		COLLECTION_QUERY = "query";

		COLLECTION_ROOT_CHILDREN = "rootchildren";

		COLLECTION_ROOT_DESCENDANTS = "rootdescendants";

		COLLECTION_TYPES_CHILDREN = "typeschildren";

		COLLECTION_TYPES_DESCENDANTS = "typesdescendants";

		COLLECTION_UNFILED = "unfiled";

		// Object

		OBJECT = new QName(NS, "object", PREFIX);

		BASE_TYPE_DOCUMENT = "document";

		BASE_TYPE_FOLDER = "folder";

		LINK_ALL_VERSIONS = "allversions";

		LINK_ALLOWABLE_ACTIONS = "allowableactions";

		LINK_CHILDREN = "children";

		LINK_DESCENDANTS = "descendants";

		LINK_LATEST_VERSION = "latestversion";

		LINK_PARENT = "parent";

		LINK_PARENTS = "parents";

		LINK_POLICIES = "policies";

		LINK_RELATIONSHIPS = "relationships";

		LINK_REPOSITORY = "repository";

		LINK_SOURCE = "source";

		LINK_STREAM = "stream";

		LINK_TARGET = "target";

		LINK_TYPE = "type";

		PROPERTIES = new QName(NS, "properties", PREFIX);

		PROPERTY_NAME = new QName(NS, "name", PREFIX);

		PROPERTY_TYPE_STRING = new QName(NS, "propertyString", PREFIX);

		PROPERTY_TYPE_DECIMAL = new QName(NS, "propertyDecimal", PREFIX);

		PROPERTY_TYPE_INTEGER = new QName(NS, "propertyInteger", PREFIX);

		PROPERTY_TYPE_BOOLEAN = new QName(NS, "propertyBoolean", PREFIX);

		PROPERTY_TYPE_DATETIME = new QName(NS, "propertyDateTime", PREFIX);

		PROPERTY_TYPE_URI = new QName(NS, "propertyUri", PREFIX);

		PROPERTY_TYPE_ID = new QName(NS, "propertyId", PREFIX);

		PROPERTY_TYPE_XML = new QName(NS, "propertyXml", PREFIX);

		PROPERTY_TYPE_HTML = new QName(NS, "propertyHtml", PREFIX);

		PROPERTY_VALUE = new QName(NS, "value", PREFIX);

		PROPERTY_NAME_BASETYPE = "BaseType";

		PROPERTY_NAME_CHECKIN_COMMENT = "CheckinComment";

		PROPERTY_NAME_CONTENT_STREAM_FILENAME = "ContentStreamFilename";

		PROPERTY_NAME_CONTENT_STREAM_LENGTH = "ContentStreamLength";

		PROPERTY_NAME_CONTENT_STREAM_MIMETYPE = "ContentStreamMimetype";

		PROPERTY_NAME_CONTENT_STREAM_URI = "ContentStreamUri";

		PROPERTY_NAME_CREATED_BY = "CreatedBy";

		PROPERTY_NAME_CREATION_DATE = "CreationDate";

		PROPERTY_NAME_IS_IMMUTABLE = "IsImmutable";

		PROPERTY_NAME_IS_LATEST_MAJOR_VERSION = "IsLatestMajorVersion";

		PROPERTY_NAME_IS_LATEST_VERSION = "IsLatestVersion";

		PROPERTY_NAME_IS_MAJOR_VERSION = "IsMajorVersion";

		PROPERTY_NAME_IS_VERSION_SERIES_CHECKED_OUT = "IsVersionSeriesCheckedOut";

		PROPERTY_NAME_LAST_MODIFIED_BY = "LastModifiedBy";

		PROPERTY_NAME_LAST_MODIFICATION_DATE = "LastModificationDate";

		PROPERTY_NAME_NAME = "Name";

		PROPERTY_NAME_OBJECT_ID  = "ObjectId";

		PROPERTY_NAME_OBJECT_TYPE_ID = "ObjectTypeId";

		PROPERTY_NAME_SOURCE_ID = "SourceId";

		PROPERTY_NAME_TARGET_ID = "TargetId";

		PROPERTY_NAME_VERSION_LABEL = "VersionLabel";

		PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_BY = "VersionSeriesCheckedOutBy";

		PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_ID = "VersionSeriesCheckedOutId";

		PROPERTY_NAME_VERSION_SERIES_ID = "VersionSeriesId";
	}

	public static CMISConstants getInstance() {
		return _instance;
	}

	private static CMISConstants _instance = new CMISConstants_0_6_1();

}