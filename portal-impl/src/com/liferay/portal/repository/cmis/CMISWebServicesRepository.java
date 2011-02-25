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

package com.liferay.portal.repository.cmis;

/**
 * @author Alexander Chow
 */
public class CMISWebServicesRepository extends CMISRepository {

	public static final String CONFIGURATION_WEBSERVICES = "WEBSERVICES";

	public static final String REPOSITORY_ID = "REPOSITORY_ID";

	public static final String WEBSERVICES_ACL_SERVICE =
		"WEBSERVICES_ACL_SERVICE";

	public static final String WEBSERVICES_DISCOVERY_SERVICE =
		"WEBSERVICES_DISCOVERY_SERVICE";

	public static final String WEBSERVICES_MULTIFILING_SERVICE =
		"WEBSERVICES_MULTIFILING_SERVICE";

	public static final String WEBSERVICES_NAVIGATION_SERVICE =
		"WEBSERVICES_NAVIGATION_SERVICE";

	public static final String WEBSERVICES_OBJECT_SERVICE =
		"WEBSERVICES_OBJECT_SERVICE";

	public static final String WEBSERVICES_POLICY_SERVICE =
		"WEBSERVICES_POLICY_SERVICE";

	public static final String WEBSERVICES_RELATIONSHIP_SERVICE =
		"WEBSERVICES_RELATIONSHIP_SERVICE";

	public static final String WEBSERVICES_REPOSITORY_SERVICE =
		"WEBSERVICES_REPOSITORY_SERVICE";

	public static final String WEBSERVICES_VERSIONING_SERVICE =
		"WEBSERVICES_VERSIONING_SERVICE";

	public static final String[] SUPPORTED_CONFIGURATIONS = {
		CONFIGURATION_WEBSERVICES
	};

	public static final String[][] SUPPORTED_PARAMETERS = {
		new String[] {
			REPOSITORY_ID, WEBSERVICES_ACL_SERVICE,
			WEBSERVICES_DISCOVERY_SERVICE, WEBSERVICES_MULTIFILING_SERVICE,
			WEBSERVICES_NAVIGATION_SERVICE, WEBSERVICES_OBJECT_SERVICE,
			WEBSERVICES_POLICY_SERVICE, WEBSERVICES_RELATIONSHIP_SERVICE,
			WEBSERVICES_REPOSITORY_SERVICE, WEBSERVICES_VERSIONING_SERVICE
		}
	};

	public boolean isAtomPub() {
		return false;
	}

	public boolean isWebServices() {
		return true;
	}

}