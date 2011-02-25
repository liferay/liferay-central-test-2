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
public class CMISAtomPubRepository extends CMISRepository {

	public static final String ATOMPUB_URL = "ATOMPUB_URL";

	public static final String CONFIGURATION_ATOMPUB = "ATOMPUB";

	public static final String REPOSITORY_ID = "REPOSITORY_ID";

	public static final String[] SUPPORTED_CONFIGURATIONS = {
		CONFIGURATION_ATOMPUB
	};

	public static final String[][] SUPPORTED_PARAMETERS = {
		new String[] {
			ATOMPUB_URL, REPOSITORY_ID
		}
	};

	public boolean isAtomPub() {
		return true;
	}
	
	public boolean isWebServices() {
		return false;
	}

}