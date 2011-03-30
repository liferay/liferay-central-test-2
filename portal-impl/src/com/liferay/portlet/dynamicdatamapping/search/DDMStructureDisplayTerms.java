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

package com.liferay.portlet.dynamicdatamapping.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletRequest;

/**
 * @author Eduardo Lundgren
 */
public class DDMStructureDisplayTerms extends DisplayTerms {

	public static final String CLASS_NAME_ID = "classNameId";

	public static final String DESCRIPTION = "description";

	public static final String NAME = "name";

	public static final String STORAGE_TYPE = "storageType";

	public static final String STRUCTURE_KEY = "structureKey";

	public DDMStructureDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		structureKey = ParamUtil.getString(portletRequest, STRUCTURE_KEY);
		name = ParamUtil.getString(portletRequest, NAME);
		storageType = ParamUtil.getString(portletRequest, STORAGE_TYPE);
		classNameId = ParamUtil.getString(portletRequest, CLASS_NAME_ID);
	}

	public String getClassNameId() {
		return classNameId;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	public String getStorageType() {
		return storageType;
	}

	public String getStructureKey() {
		return structureKey;
	}

	protected String structureKey;
	protected String name;
	protected String description;
	protected String storageType;
	protected String classNameId;

}