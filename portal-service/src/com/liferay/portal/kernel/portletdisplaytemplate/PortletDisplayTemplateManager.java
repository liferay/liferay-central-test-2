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

package com.liferay.portal.kernel.portletdisplaytemplate;

import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateVariableGroup;
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataException;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Leonardo Barros
 */
public interface PortletDisplayTemplateManager {

	public static final String DISPLAY_STYLE_PREFIX = "ddmTemplate_";

	public static final String ENTRIES = "entries";

	public void exportDDMTemplateStagedModel(
			PortletDataContext portletDataContext, String portletId,
			DDMTemplate ddmTemplate)
		throws PortletDataException;

	public DDMTemplate getDDMTemplate(
		long groupId, long classNameId, String displayStyle,
		boolean useDefault);

	public long getDDMTemplateGroupId(long groupId);

	public String getDisplayStyle(String ddmTemplateKey);

	public List<TemplateHandler> getPortletDisplayTemplateHandlers();

	public Map<String, TemplateVariableGroup> getTemplateVariableGroups(
		String language);

	public String renderDDMTemplate(
			HttpServletRequest request, HttpServletResponse response,
			long templateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception;

}