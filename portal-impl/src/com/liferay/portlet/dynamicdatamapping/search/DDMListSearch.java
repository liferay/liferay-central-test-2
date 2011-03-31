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
import com.liferay.portlet.dynamicdatamapping.model.DDMList;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Marcellus Tavares
 */
public class DDMListSearch extends SearchContainer<DDMList> {

	static List<String> headerNames = new ArrayList<String>();

	static {
		headerNames.add("id");
		headerNames.add("name");
		headerNames.add("description");
		headerNames.add("modified-date");
	}

	public static final String EMPTY_RESULTS_MESSAGE = "no-lists-were-found";

	public DDMListSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, new DDMListDisplayTerms(portletRequest),
			new DDMListSearchTerms(portletRequest), DEFAULT_CUR_PARAM,
			DEFAULT_DELTA, iteratorURL, headerNames, EMPTY_RESULTS_MESSAGE);

		DDMListDisplayTerms displayTerms =
			(DDMListDisplayTerms)getDisplayTerms();

		iteratorURL.setParameter(
			DDMListDisplayTerms.DESCRIPTION, displayTerms.getDescription());
		iteratorURL.setParameter(
			DDMListDisplayTerms.LIST_KEY, displayTerms.getListKey());
		iteratorURL.setParameter(
			DDMListDisplayTerms.NAME, String.valueOf(displayTerms.getName()));
	}

}