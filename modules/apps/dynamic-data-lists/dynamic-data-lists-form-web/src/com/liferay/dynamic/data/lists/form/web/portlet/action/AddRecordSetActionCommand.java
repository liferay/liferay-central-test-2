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

package com.liferay.dynamic.data.lists.form.web.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.StructureDefinitionException;
import com.liferay.portlet.dynamicdatamapping.StructureLayoutException;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONDeserializer;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormLayoutJSONDeserializer;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureService;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = {
		"action.command.name=addRecordSet",
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN
	},
	service = ActionCommand.class
)
public class AddRecordSetActionCommand extends BaseTransactionalActionCommand {

	protected DDMStructure addDDMStructure(PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String structureKey = ParamUtil.getString(
			portletRequest, "structureKey");
		String storageType = ParamUtil.getString(portletRequest, "storageType");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");
		DDMForm ddmForm = getDDMForm(portletRequest);
		DDMFormLayout ddmFormLayout = getDDMFormLayout(portletRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMStructure.class.getName(), portletRequest);

		return _ddmStructureService.addStructure(
			groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			PortalUtil.getClassNameId(DDLRecordSet.class), structureKey,
			getLocalizedMap(themeDisplay.getLocale(), name),
			getLocalizedMap(themeDisplay.getLocale(), description), ddmForm,
			ddmFormLayout, storageType, DDMStructureConstants.TYPE_DEFAULT,
			serviceContext);
	}

	protected void addRecordSet(
			PortletRequest portletRequest, long ddmStructureId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(portletRequest, "groupId");
		String recordSetKey = ParamUtil.getString(
			portletRequest, "recordSetKey");
		String name = ParamUtil.getString(portletRequest, "name");
		String description = ParamUtil.getString(portletRequest, "description");
		int scope = ParamUtil.getInteger(portletRequest, "scope");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecordSet.class.getName(), portletRequest);

		_ddlRecordSetService.addRecordSet(
			groupId, ddmStructureId, recordSetKey,
			getLocalizedMap(themeDisplay.getLocale(), name),
			getLocalizedMap(themeDisplay.getLocale(), description),
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT, scope,
			serviceContext);
	}

	@Override
	protected void doTransactionalCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws Exception {

		DDMStructure ddmStructure = addDDMStructure(portletRequest);

		addRecordSet(portletRequest, ddmStructure.getStructureId());
	}

	protected DDMForm getDDMForm(PortletRequest portletRequest)
		throws PortalException {

		try {
			String definition = ParamUtil.getString(
				portletRequest, "definition");

			return _ddmFormJSONDeserializer.deserialize(definition);
		}
		catch (PortalException pe) {
			throw new StructureDefinitionException(pe);
		}
	}

	protected DDMFormLayout getDDMFormLayout(PortletRequest portletRequest)
		throws PortalException {

		try {
			String layout = ParamUtil.getString(portletRequest, "layout");

			return _ddmFormLayoutJSONDeserializer.deserialize(layout);
		}
		catch (PortalException pe) {
			throw new StructureLayoutException(pe);
		}
	}

	protected Map<Locale, String> getLocalizedMap(Locale locale, String value) {
		Map<Locale, String> localizedMap = new HashMap<>();

		localizedMap.put(locale, value);

		return localizedMap;
	}

	@Reference
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference
	protected void setDDMFormJSONDeserializer(
		DDMFormJSONDeserializer ddmFormJSONDeserializer) {

		_ddmFormJSONDeserializer = ddmFormJSONDeserializer;
	}

	@Reference
	protected void setDDMFormLayoutJSONDeserializer(
		DDMFormLayoutJSONDeserializer ddmFormLayoutJSONDeserializer) {

		_ddmFormLayoutJSONDeserializer = ddmFormLayoutJSONDeserializer;
	}

	@Reference
	protected void setDDMStructureService(
		DDMStructureService ddmStructureService) {

		_ddmStructureService = ddmStructureService;
	}

	private DDLRecordSetService _ddlRecordSetService;
	private DDMFormJSONDeserializer _ddmFormJSONDeserializer;
	private DDMFormLayoutJSONDeserializer _ddmFormLayoutJSONDeserializer;
	private DDMStructureService _ddmStructureService;

}