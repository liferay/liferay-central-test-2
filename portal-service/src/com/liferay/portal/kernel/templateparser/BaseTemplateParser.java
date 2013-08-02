/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.UnknownDevice;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public abstract class BaseTemplateParser implements TemplateParser {

	@Override
	public String getLanguageId() {
		return _languageId;
	}

	@Override
	public String getScript() {
		return _script;
	}

	@Override
	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
	}

	@Override
	public Map<String, String> getTokens() {
		return _tokens;
	}

	@Override
	public String getViewMode() {
		return _viewMode;
	}

	@Override
	public String getXML() {
		return _xml;
	}

	public void setContextObjects(Map<String, Object> contextObjects) {
		_contextObjects = contextObjects;
	}

	@Override
	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	@Override
	public void setScript(String script) {
		_script = script;
	}

	@Override
	public void setThemeDisplay(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	@Override
	public void setTokens(Map<String, String> tokens) {
		_tokens = tokens;
	}

	@Override
	public void setViewMode(String viewMode) {
		_viewMode = viewMode;
	}

	@Override
	public void setXML(String xml) {
		_xml = xml;
	}

	@Override
	public String transform() throws TransformException {
		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try {
			TemplateContext templateContext = getTemplateContext();

			if (Validator.isNotNull(_xml)) {
				Document document = SAXReaderUtil.read(_xml);

				Element rootElement = document.getRootElement();

				List<TemplateNode> templateNodes = getTemplateNodes(
					rootElement);

				if (templateNodes != null) {
					for (TemplateNode templateNode : templateNodes) {
						templateContext.put(
							templateNode.getName(), templateNode);
					}
				}

				Element requestElement = rootElement.element("request");

				templateContext.put(
					"request", insertRequestVariables(requestElement));

				templateContext.put("xmlRequest", requestElement.asXML());
			}

			if (_contextObjects != null) {
				for (String key : _contextObjects.keySet()) {
					templateContext.put(key, _contextObjects.get(key));
				}
			}

			populateTemplateContext(templateContext);

			load = mergeTemplate(templateContext, unsyncStringWriter);
		}
		catch (Exception e) {
			if (e instanceof DocumentException) {
				throw new TransformException("Unable to read XML document", e);
			}
			else if (e instanceof IOException) {
				throw new TransformException("Error reading template", e);
			}
			else if (e instanceof TransformException) {
				throw (TransformException)e;
			}
			else {
				throw new TransformException("Unhandled exception", e);
			}
		}

		if (!load) {
			throw new TransformException(
				"Unable to dynamically load transform script");
		}

		return unsyncStringWriter.toString();
	}

	protected long getArticleGroupId() {
		return GetterUtil.getLong(_tokens.get("article_group_id"));
	}

	protected Company getCompany() throws Exception {
		if (_themeDisplay != null) {
			return _themeDisplay.getCompany();
		}

		return CompanyLocalServiceUtil.getCompany(getCompanyId());
	}

	protected long getCompanyGroupId() {
		if (_themeDisplay != null) {
			return _themeDisplay.getCompanyGroupId();
		}

		return GetterUtil.getLong(_tokens.get("company_group_id"));
	}

	protected long getCompanyId() {
		if (_themeDisplay != null) {
			return _themeDisplay.getCompanyId();
		}

		return GetterUtil.getLong(_tokens.get("company_id"));
	}

	protected Device getDevice() {
		if (_themeDisplay != null) {
			return _themeDisplay.getDevice();
		}

		return UnknownDevice.getInstance();
	}

	protected long getGroupId() {
		return getArticleGroupId();
	}

	protected long getScopeGroupId() {
		return GetterUtil.getLong(_tokens.get("scope_group_id"));
	}

	protected abstract TemplateContext getTemplateContext() throws Exception;

	protected String getTemplateId() {
		long companyGroupId = getCompanyGroupId();

		String templateId = null;

		if (_tokens != null) {
			templateId = _tokens.get("template_id");
		}

		if (Validator.isNull(templateId)) {
			templateId = (String.valueOf(_contextObjects.get("template_id")));
		}

		StringBundler sb = new StringBundler(5);

		sb.append(getCompanyId());
		sb.append(StringPool.POUND);

		if (companyGroupId > 0) {
			sb.append(companyGroupId);
		}
		else {
			sb.append(getGroupId());
		}

		sb.append(StringPool.POUND);
		sb.append(templateId);

		return sb.toString();
	}

	protected abstract List<TemplateNode> getTemplateNodes(Element element)
		throws Exception;

	protected Map<String, Object> insertRequestVariables(Element element) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (element == null) {
			return map;
		}

		for (Element childElement : element.elements()) {
			String name = childElement.getName();

			if (name.equals("attribute")) {
				Element nameElement = childElement.element("name");
				Element valueElement = childElement.element("value");

				map.put(nameElement.getText(), valueElement.getText());
			}
			else if (name.equals("parameter")) {
				Element nameElement = childElement.element("name");

				List<Element> valueElements = childElement.elements("value");

				if (valueElements.size() == 1) {
					Element valueElement = valueElements.get(0);

					map.put(nameElement.getText(), valueElement.getText());
				}
				else {
					List<String> values = new ArrayList<String>();

					for (Element valueElement : valueElements) {
						values.add(valueElement.getText());
					}

					map.put(nameElement.getText(), values);
				}
			}
			else if (childElement.elements().size() > 0) {
				map.put(name, insertRequestVariables(childElement));
			}
			else {
				map.put(name, childElement.getText());
			}
		}

		return map;
	}

	protected abstract boolean mergeTemplate(
			TemplateContext templateContext,
			UnsyncStringWriter unsyncStringWriter)
		throws Exception;

	protected void populateTemplateContext(TemplateContext templateContext)
		throws Exception {

		templateContext.put("articleGroupId", getArticleGroupId());
		templateContext.put("company", getCompany());
		templateContext.put("companyId", getCompanyId());
		templateContext.put("device", getDevice());
		templateContext.put("groupId", getGroupId());

		Locale locale = LocaleUtil.fromLanguageId(_languageId);

		templateContext.put("locale", locale);

		templateContext.put(
			"permissionChecker", PermissionThreadLocal.getPermissionChecker());
		templateContext.put("scopeGroupId", getScopeGroupId());
		templateContext.put("viewMode", _viewMode);
	}

	private Map<String, Object> _contextObjects = new HashMap<String, Object>();
	private String _languageId;
	private String _script;
	private ThemeDisplay _themeDisplay;
	private Map<String, String> _tokens;
	private String _viewMode;
	private String _xml;

}