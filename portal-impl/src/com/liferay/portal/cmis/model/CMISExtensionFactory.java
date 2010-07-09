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

import org.apache.abdera.util.AbstractExtensionFactory;

/**
 * @author Alexander Chow
 */
public class CMISExtensionFactory extends AbstractExtensionFactory {

	public CMISExtensionFactory() {
		super(CMISConstants.getInstance().CMIS_NS);

		_cmisConstants = CMISConstants.getInstance();

		addImpl(_cmisConstants.REPOSITORY_INFO, CMISRepositoryInfo.class);
		addImpl(_cmisConstants.OBJECT, CMISObject.class);
	}

	private CMISConstants _cmisConstants;

}