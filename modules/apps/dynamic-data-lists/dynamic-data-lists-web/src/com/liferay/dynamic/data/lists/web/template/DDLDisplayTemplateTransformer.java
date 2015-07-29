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

package com.liferay.dynamic.data.lists.web.template;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.util.DDLConstants;
import com.liferay.dynamic.data.lists.web.configuration.DDLWebConfigurationUtil;
import com.liferay.dynamic.data.lists.web.configuration.DDLWebConfigurationValues;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.templateparser.Transformer;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcellus Tavares
 */
public class DDLDisplayTemplateTransformer {

	public String transform(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		Transformer transformer = TransformerHolder.getTransformer();

		Map<String, Object> contextObjects = new HashMap<>();

		contextObjects.put(
			DDLConstants.RESERVED_DDM_STRUCTURE_ID,
			recordSet.getDDMStructureId());
		contextObjects.put(
			DDLConstants.RESERVED_DDM_TEMPLATE_ID, ddmTemplateId);
		contextObjects.put(
			DDLConstants.RESERVED_RECORD_SET_DESCRIPTION,
			recordSet.getDescription(themeDisplay.getLocale()));
		contextObjects.put(
			DDLConstants.RESERVED_RECORD_SET_ID, recordSet.getRecordSetId());
		contextObjects.put(
			DDLConstants.RESERVED_RECORD_SET_NAME,
			recordSet.getName(themeDisplay.getLocale()));
		contextObjects.put(TemplateConstants.TEMPLATE_ID, ddmTemplateId);

		String viewMode = Constants.VIEW;

		if (renderRequest != null) {
			viewMode = ParamUtil.getString(
				renderRequest, "viewMode", Constants.VIEW);
		}

		contextObjects.put("viewMode", viewMode);

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		contextObjects.put(
			TemplateConstants.CLASS_NAME_ID, ddmTemplate.getClassNameId());

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(ddmTemplate.getLanguage());

		TemplateHandler templateHandler =
			TemplateHandlerRegistryUtil.getTemplateHandler(
				DDLRecordSet.class.getName());

		templateManager.addContextObjects(
			contextObjects, templateHandler.getCustomContextObjects());

		return transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(),
			ddmTemplate.getLanguage(), new UnsyncStringWriter());
	}

	private static class TransformerHolder {

		public static Transformer getTransformer() {
			return _transformer;
		}

		private static final Transformer _transformer = new Transformer(
			DDLWebConfigurationValues.DYNAMIC_DATA_LISTS_ERROR_TEMPLATE, true) {

			@Override
			protected String getErrorTemplateId(
				String errorTemplatePropertyKey, String langType) {

				return DDLWebConfigurationUtil.get(
					errorTemplatePropertyKey, new Filter(langType));
			};

		};

	}

}