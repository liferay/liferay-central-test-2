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

import javax.xml.namespace.QName;

/**
 * @author Alexander Chow
 */
public class CMISConstants_1_0_0 extends CMISConstants {

	public static CMISConstants getInstance() {
		return _instance;
	}

	private CMISConstants_1_0_0() {
		CMIS_NS = "http://docs.oasis-open.org/ns/cmis/core/200908/";

		CMIS_PREFIX = "cmis";

		CMISRA_NS = "http://docs.oasis-open.org/ns/cmis/restatom/200908/";

		CMISRA_PREFIX = "cmisra";

		VERSION = "1.0";

		// Repository

		REPOSITORY_DESCRIPTION = new QName(
			CMIS_NS, "repositoryDescription", CMIS_PREFIX);

		REPOSITORY_ID = new QName(CMIS_NS, "repositoryId", CMIS_PREFIX);

		REPOSITORY_INFO = new QName(CMISRA_NS, "repositoryInfo", CMISRA_PREFIX);

		REPOSITORY_NAME = new QName(CMIS_NS, "repositoryName", CMIS_PREFIX);

		REPOSITORY_PRODUCT_NAME = new QName(
			CMIS_NS, "productName", CMIS_PREFIX);

		REPOSITORY_PRODUCT_VERSION = new QName(
			CMIS_NS, "productVersion", CMIS_PREFIX);

		REPOSITORY_RELATIONSHIP = new QName(
			CMIS_NS, "repositoryRelationship", CMIS_PREFIX);

		REPOSITORY_ROOT_FOLDER_ID = new QName(
			CMIS_NS, "rootFolderId", CMIS_PREFIX);

		REPOSITORY_SPECIFIC_INFO = new QName(
			CMIS_NS, "repositorySpecificInformation", CMIS_PREFIX);

		REPOSITORY_VENDOR_NAME = new QName(CMIS_NS, "vendorName", CMIS_PREFIX);

		REPOSITORY_VERSION_SUPPORTED = new QName(
			CMIS_NS, "cmisVersionSupported", CMIS_PREFIX);

		// Collection

		COLLECTION_TYPE = new QName(CMISRA_NS, "collectionType", CMISRA_PREFIX);

		COLLECTION_CHECKEDOUT = "checkedout";

		COLLECTION_QUERY = "query";

		COLLECTION_ROOT = "root";

		COLLECTION_ROOT_DESCENDANTS = "rootdescendants";

		COLLECTION_TYPES_CHILDREN = "typeschildren";

		COLLECTION_TYPES_DESCENDANTS = "typesdescendants";

		COLLECTION_UNFILED = "unfiled";

		// Object

		OBJECT = new QName(CMISRA_NS, "object", CMISRA_PREFIX);

		BASE_TYPE_DOCUMENT = "cmis:document";

		BASE_TYPE_FOLDER = "cmis:folder";

		LINK_ALL_VERSIONS = "version-history";

		LINK_ALLOWABLE_ACTIONS = "allowableactions";

		LINK_CHILDREN = "down";

		LINK_DESCENDANTS = "down";

		LINK_LATEST_VERSION = "latestversion";

		LINK_PARENT = "parent";

		LINK_PARENTS = "parents";

		LINK_POLICIES = "policies";

		LINK_RELATIONSHIPS = "relationships";

		LINK_REPOSITORY = "repository";

		LINK_SOURCE = "source";

		LINK_STREAM = "enclosure";

		LINK_TARGET = "target";

		LINK_TYPE = "type";

		PROPERTIES = new QName(CMIS_NS, "properties", CMIS_PREFIX);

		PROPERTY_DEFINITION_ID = "propertyDefinitionId";

		PROPERTY_NAME = new QName(CMIS_NS, "name", CMIS_PREFIX);

		PROPERTY_TYPE_STRING = new QName(
			CMIS_NS, "propertyString", CMIS_PREFIX);

		PROPERTY_TYPE_DECIMAL = new QName(
			CMIS_NS, "propertyDecimal", CMIS_PREFIX);

		PROPERTY_TYPE_INTEGER = new QName(
			CMIS_NS, "propertyInteger", CMIS_PREFIX);

		PROPERTY_TYPE_BOOLEAN = new QName(
			CMIS_NS, "propertyBoolean", CMIS_PREFIX);

		PROPERTY_TYPE_DATETIME = new QName(
			CMIS_NS, "propertyDateTime", CMIS_PREFIX);

		PROPERTY_TYPE_URI = new QName(CMIS_NS, "propertyUri", CMIS_PREFIX);

		PROPERTY_TYPE_ID = new QName(CMIS_NS, "propertyId", CMIS_PREFIX);

		PROPERTY_TYPE_XML = new QName(CMIS_NS, "propertyXml", CMIS_PREFIX);

		PROPERTY_TYPE_HTML = new QName(CMIS_NS, "propertyHtml", CMIS_PREFIX);

		PROPERTY_VALUE = new QName(CMIS_NS, "value", CMIS_PREFIX);

		PROPERTY_NAME_BASETYPE = "cmis:baseTypeId";

		PROPERTY_NAME_CHECKIN_COMMENT = "cmis:checkinComment";

		PROPERTY_NAME_CONTENT_STREAM_FILENAME = "cmis:contentStreamFilename";

		PROPERTY_NAME_CONTENT_STREAM_LENGTH = "cmis:contentStreamLength";

		PROPERTY_NAME_CONTENT_STREAM_MIMETYPE = "cmis:contentStreamMimetype";

		PROPERTY_NAME_CONTENT_STREAM_URI = "cmis:contentStreamUri";

		PROPERTY_NAME_CREATED_BY = "cmis:createdBy";

		PROPERTY_NAME_CREATION_DATE = "cmis:creationDate";

		PROPERTY_NAME_IS_IMMUTABLE = "cmis:sImmutable";

		PROPERTY_NAME_IS_LATEST_MAJOR_VERSION = "cmis:isLatestMajorVersion";

		PROPERTY_NAME_IS_LATEST_VERSION = "cmis:isLatestVersion";

		PROPERTY_NAME_IS_MAJOR_VERSION = "cmis:isMajorVersion";

		PROPERTY_NAME_IS_VERSION_SERIES_CHECKED_OUT =
			"cmis:isVersionSeriesCheckedOut";

		PROPERTY_NAME_LAST_MODIFIED_BY = "cmis:lastModifiedBy";

		PROPERTY_NAME_LAST_MODIFICATION_DATE = "cmis:lastModificationDate";

		PROPERTY_NAME_NAME = "cmis:name";

		PROPERTY_NAME_OBJECT_ID  = "cmis:objectId";

		PROPERTY_NAME_OBJECT_TYPE_ID = "cmis:objectTypeId";

		PROPERTY_NAME_SOURCE_ID = "cmis:sourceId";

		PROPERTY_NAME_TARGET_ID = "cmis:targetId";

		PROPERTY_NAME_VERSION_LABEL = "cmis:versionLabel";

		PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_BY =
			"cmis:versionSeriesCheckedOutBy";

		PROPERTY_NAME_VERSION_SERIES_CHECKED_OUT_ID =
			"cmis:versionSeriesCheckedOutId";

		PROPERTY_NAME_VERSION_SERIES_ID = "cmis:versionSeriesId";
	}

	private static CMISConstants _instance = new CMISConstants_1_0_0();

}