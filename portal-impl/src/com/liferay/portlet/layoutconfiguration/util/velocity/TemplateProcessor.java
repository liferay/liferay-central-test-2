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

package com.liferay.portlet.layoutconfiguration.util.velocity;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.comparator.PortletRenderWeightComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="TemplateProcessor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class TemplateProcessor {

	public TemplateProcessor(
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response, String portletId) {

		_servletContext = servletContext;
		_request = request;
		_response = response;
		_portletId = portletId;
		_columnsMap = new HashMap<String, String>();
		_portletsMap = new TreeMap<Portlet, Object[]>(
			new PortletRenderWeightComparator());
	}

	public String processColumn(String columnId) throws Exception {
		return processColumn(columnId, StringPool.BLANK);
	}

	public String processColumn(String columnId, String classNames)
		throws Exception {

		Map<String, String> attributes = new HashMap<String, String>();

		attributes.put("id", columnId);
		attributes.put("classNames", classNames);

		PortletColumnLogic logic = new PortletColumnLogic(
			_servletContext, _request, _response);

		StringBuilder sb = new StringBuilder();

		logic.processContent(sb, attributes);

		_portletsMap.putAll(logic.getPortletsMap());

		String columnIdPlaceHolder = "[$TEMPLATE_COLUMN_" + columnId + "$]";

		_columnsMap.put(columnIdPlaceHolder, sb.toString());

		return columnIdPlaceHolder;
	}

	public String processMax() throws Exception {
		RuntimeLogic logic = new PortletLogic(
			_servletContext, _request, _response, _portletId);

		StringBuilder sb = new StringBuilder();

		logic.processContent(sb, new HashMap<String, String>());

		return sb.toString();
	}

	public String processPortlet(String portletId) throws Exception {
		RuntimeLogic logic = new PortletLogic(
			_servletContext, _request, _response, portletId);

		StringBuilder sb = new StringBuilder();

		logic.processContent(sb, new HashMap<String, String>());

		return sb.toString();
	}

	public Map<String, String> getColumnsMap() {
		return _columnsMap;
	}

	public Map<Portlet, Object[]> getPortletsMap() {
		return _portletsMap;
	}

	private ServletContext _servletContext;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private String _portletId;
	private Map<String, String> _columnsMap;
	private Map<Portlet, Object[]> _portletsMap;

}