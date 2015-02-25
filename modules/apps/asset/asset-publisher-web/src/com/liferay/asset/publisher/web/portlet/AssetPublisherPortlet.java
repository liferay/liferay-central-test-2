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

package com.liferay.asset.publisher.web.portlet;

import com.liferay.asset.publisher.web.upgrade.AssetPublisherWebUpgrade;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.asset.publisher.web.util.AssetRSSUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.text.DateFormat;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-asset-publisher",
		"com.liferay.portlet.display-category=category.cms",
		"com.liferay.portlet.friendly-url-mapping=asset_publisher",
		"com.liferay.portlet.friendly-url-routes=com/liferay/asset/publisher/web/portlet/route/asset-publisher-friendly-url-routes.xml",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/asset_publisher.png",
		"com.liferay.portlet.instanceable=true",
		"com.liferay.portlet.layout-cacheable=true",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=false",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset Publisher",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.config-template=/configuration.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=guest,power-user,user",
		"javax.portlet.supported-public-render-parameter=categoryId",
		"javax.portlet.supported-public-render-parameter=resetCur",
		"javax.portlet.supported-public-render-parameter=tag",
		"javax.portlet.supported-public-render-parameter=tags",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AssetPublisherPortlet extends MVCPortlet {

	public void getFieldValue(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (!cmd.equals("getFieldValue")) {
			return;
		}

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				resourceRequest);

			long structureId = ParamUtil.getLong(
				resourceRequest, "structureId");

			Fields fields = (Fields)serviceContext.getAttribute(
				Fields.class.getName() + structureId);

			if (fields == null) {
				String fieldsNamespace = ParamUtil.getString(
					resourceRequest, "fieldsNamespace");

				fields = DDMUtil.getFields(
					structureId, fieldsNamespace, serviceContext);
			}

			String fieldName = ParamUtil.getString(resourceRequest, "name");

			Field field = fields.get(fieldName);

			Serializable fieldValue = field.getValue(
				themeDisplay.getLocale(), 0);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			if (fieldValue != null) {
				jsonObject.put("success", true);
			}
			else {
				jsonObject.put("success", false);

				writeJSON(resourceRequest, resourceResponse, jsonObject);

				return;
			}

			DDMStructure ddmStructure = field.getDDMStructure();

			String type = ddmStructure.getFieldType(fieldName);

			Serializable displayValue = DDMUtil.getDisplayFieldValue(
				themeDisplay, fieldValue, type);

			jsonObject.put("displayValue", String.valueOf(displayValue));

			if (fieldValue instanceof Boolean) {
				jsonObject.put("value", (Boolean) fieldValue);
			}
			else if (fieldValue instanceof Date) {
				DateFormat dateFormat =
					DateFormatFactoryUtil.getSimpleDateFormat(
						"yyyyMM ddHHmmss");

				jsonObject.put("value", dateFormat.format(fieldValue));
			}
			else if (fieldValue instanceof Double) {
				jsonObject.put("value", (Double)fieldValue);
			}
			else if (fieldValue instanceof Float) {
				jsonObject.put("value", (Float)fieldValue);
			}
			else if (fieldValue instanceof Integer) {
				jsonObject.put("value", (Integer)fieldValue);
			}
			else if (fieldValue instanceof Number) {
				jsonObject.put("value", String.valueOf(fieldValue));
			}
			else {
				jsonObject.put("value", (String)fieldValue);
			}

			writeJSON(resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void getRSS(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		if (!PortalUtil.isRSSFeedsEnabled()) {
			try {
				PortalUtil.sendRSSFeedsDisabledError(
					resourceRequest, resourceResponse);
			}
			catch (ServletException se) {
			}

			return;
		}

		resourceResponse.setContentType(ContentTypes.TEXT_XML_UTF8);

		try (OutputStream outputStream =
				resourceResponse.getPortletOutputStream()) {

			byte[] bytes = AssetRSSUtil.getRSS(
				resourceRequest, resourceResponse);

			outputStream.write(bytes);
		}
		catch (Exception e) {
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals("getFieldValue")) {
			getFieldValue(resourceRequest, resourceResponse);
		}
		else if (cmd.equals("rss")) {
			getRSS(resourceRequest, resourceResponse);
		}

		super.serveResource(resourceRequest, resourceResponse);
	}

	public void subscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetPublisherUtil.subscribe(
			themeDisplay.getPermissionChecker(), themeDisplay.getScopeGroupId(),
			themeDisplay.getPlid(), themeDisplay.getPpid());
	}

	public void unsubscribe(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		AssetPublisherUtil.unsubscribe(
			themeDisplay.getPermissionChecker(), themeDisplay.getPlid(),
			themeDisplay.getPpid());
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.class.getName())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof PrincipalException) {
			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setAssetPublisherWebUpgrade(
		AssetPublisherWebUpgrade assetPublisherWebUpgrade) {
	}

}