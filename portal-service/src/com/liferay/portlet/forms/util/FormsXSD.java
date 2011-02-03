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

package com.liferay.portlet.forms.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

import javax.servlet.jsp.PageContext;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 */
public interface FormsXSD {

	public String getHTML(PageContext pageContext, Document document)
		throws Exception;

	public String getHTML(PageContext pageContext,Element element)
		throws Exception;

	public String getHTML(PageContext pageContext, String xml)
		throws DocumentException, Exception;

	public JSONArray getJSONArray(Document document) throws JSONException;

	public JSONArray getJSONArray(Element element) throws JSONException;

	public JSONArray getJSONArray(String xml)
		throws DocumentException, JSONException;

}