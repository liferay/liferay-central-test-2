/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.store.s3.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Manuel de la Peña
 */
@Meta.OCD(
	id = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	localization = "content/Language", name = "%s3.store.configuration.name"
)
public interface S3StoreConfiguration {

	@Meta.AD(required = false)
	public String accessKey();

	@Meta.AD(required = true)
	public String bucketName();

	@Meta.AD(deflt = "7", required = false)
	public int cacheDirCleanUpExpunge();

	@Meta.AD(deflt = "100", required = false)
	public int cacheDirCleanUpFrequency();

	@Meta.AD(deflt = "50", required = false)
	public int httpClientMaxConnections();

	@Meta.AD(deflt = "us-east-1", required = false)
	public String s3Region();

	@Meta.AD(
		deflt = "STANDARD", optionValues = {"REDUCED_REDUNDANCY", "STANDARD"},
		required = false
	)
	public String s3StorageClass();

	@Meta.AD(required = false)
	public String secretKey();

}