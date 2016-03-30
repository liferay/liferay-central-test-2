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

package com.liferay.portal.portlet.bridge.soy;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.portlet.bridge.soy.internal.SoyPortletHelper;
import com.liferay.portal.portlet.bridge.soy.internal.SoyTemplateResourcesCollector;

import java.io.IOException;
import java.io.Writer;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Miroslav Ligas
 * @author Bruno Basto
 */
public class SoyPortlet extends MVCPortlet {

	@Override
	public void init() throws PortletException {
		super.init();

		propagateRequestParameters = GetterUtil.getBoolean(
			getInitParameter("propagate-request-parameters"), true);

		_bundle = FrameworkUtil.getBundle(this.getClass());

		try {
			_soyPortletHelper = new SoyPortletHelper(_bundle);

			moduleName = _soyPortletHelper.getModuleName();

			template = _getTemplate();
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(WebKeys.TEMPLATE, template);

		super.render(renderRequest, renderResponse);
	}

	protected Set<String> getRequiredModules(String path) {
		Set<String> requiredModules = new LinkedHashSet<>();

		if (moduleName != null) {
			String controllerName = _soyPortletHelper.getControllerName(path);

			requiredModules.add(
				moduleName.concat(StringPool.SLASH).concat(controllerName));
		}

		return requiredModules;
	}

	@Override
	protected void include(
			String namespace, PortletRequest portletRequest,
			PortletResponse portletResponse, String lifecycle)
		throws IOException, PortletException {

		try {
			String path = getPath(portletRequest, portletResponse);

			if (Validator.isNull(path)) {
				path = namespace;
			}

			template.put(
				TemplateConstants.NAMESPACE,
				_soyPortletHelper.getTemplateNamespace(path));

			if (propagateRequestParameters) {
				propagateRequestParameters(portletRequest);
			}

			Writer writer = null;

			if (portletResponse instanceof MimeResponse) {
				MimeResponse mimeResponse = (MimeResponse)portletResponse;

				writer = UnsyncPrintWriterPool.borrow(mimeResponse.getWriter());
			}
			else {
				writer = new UnsyncStringWriter();
			}

			String portletNamespace = portletResponse.getNamespace();

			String portletComponentId = portletNamespace.concat(
				"PortletComponent");

			template.put(
				"element", StringPool.POUND.concat(portletComponentId));
			template.put("id", portletComponentId);

			template.processTemplate(writer);

			Set<String> requiredModules = getRequiredModules(path);

			String portletJavaScript = _soyPortletHelper.getPortletJavaScript(
				template, requiredModules, portletResponse.getNamespace());

			writer.write(portletJavaScript);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		if (clearRequestParameters) {
			if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
				portletResponse.setProperty("clear-request-parameters", "true");
			}
		}
	}

	protected void propagateRequestParameters(PortletRequest portletRequest) {
		Map<String, String[]> parametersMap = portletRequest.getParameterMap();

		for (String parameterName : parametersMap.keySet()) {
			String[] parameterValues = parametersMap.get(parameterName);

			if (parameterValues.length == 1) {
				template.put(parameterName, parameterValues[0]);
			}
			else if (parameterValues.length > 1) {
				template.put(parameterName, parameterValues);
			}
		}
	}

	protected String moduleName;
	protected boolean propagateRequestParameters;
	protected Template template;

	private Template _getTemplate() throws TemplateException {
		SoyTemplateResourcesCollector soyTemplateResourcesCollector =
			new SoyTemplateResourcesCollector(_bundle, templatePath);

		return TemplateManagerUtil.getTemplate(
			TemplateConstants.LANG_TYPE_SOY,
			soyTemplateResourcesCollector.getTemplateResources(), false);
	}

	private Bundle _bundle;
	private SoyPortletHelper _soyPortletHelper;

}