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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eduardo Lundgren
 */
public class DDMStructureSearch extends SearchContainer<DDMStructure> {

	static List<String> headerNames = new ArrayList<String>();
	static Map<String, String> orderableHeaders = new HashMap<String, String>();

	static {
		headerNames.add("name");
		headerNames.add("storageType");
		headerNames.add("structureKey");
		headerNames.add("classNameId");
	}

	public static final String EMPTY_RESULTS_MESSAGE =
		"there-are-no-structures";

	public DDMStructureSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new DDMStructureDisplayTerms(portletRequest),
			new DDMStructureSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		DDMStructureDisplayTerms displayTerms =
			(DDMStructureDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			DDMStructureDisplayTerms.CLASS_NAME_ID,
			displayTerms.getClassNameId());
		iteratorURL.setParameter(
			DDMStructureDisplayTerms.DESCRIPTION,
			displayTerms.getDescription());
		iteratorURL.setParameter(
			DDMStructureDisplayTerms.NAME, displayTerms.getName());
		iteratorURL.setParameter(
			DDMStructureDisplayTerms.STORAGE_TYPE,
			displayTerms.getStorageType());
		iteratorURL.setParameter(
			DDMStructureDisplayTerms.STRUCTURE_KEY,
			displayTerms.getStructureKey());
	}

}