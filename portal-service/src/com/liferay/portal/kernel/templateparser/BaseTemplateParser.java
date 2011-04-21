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

package com.liferay.portal.kernel.templateparser;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public abstract class BaseTemplateParser implements TemplateParser {

	public String transform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, String xml, String script)
		throws TransformException {

		try {
			return doTransform(
				themeDisplay, tokens, viewMode, languageId, xml, script);
		}
		catch (TransformException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TransformException(e);
		}
	}

	protected boolean doMergeTemplate(
			String templateId, String script, TemplateContext context,
			Writer writer, String errorTemplateId, String errorTemplateContent)
		throws Exception {

		return false;
	}

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId, String xml, String script)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try	{
			TemplateContext context = getTemplateContext();

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<TemplateNode> nodes = getTemplateNodes(themeDisplay, root);

			if (nodes != null) {
				for (TemplateNode node : nodes) {
					context.put(node.getName(), node);
				}
			}

			context.put(
				"xmlRequest", root.element("request").asXML());
			context.put(
				"request", insertRequestVariables(root.element("request")));

			long companyId = GetterUtil.getLong(tokens.get("company_id"));
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
			long groupId = GetterUtil.getLong(tokens.get("group_id"));
			String templateId = tokens.get("template_id");

			context.put("company", company);
			context.put("companyId", String.valueOf(companyId));
			context.put("groupId", String.valueOf(groupId));
			context.put("viewMode", viewMode);
			context.put("locale", LocaleUtil.fromLanguageId(languageId));
			context.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());

			populateCustomContext(context);

			templateId = companyId + groupId + templateId;

			long companyGroupId = GetterUtil.getLong(
				tokens.get("company_group_id"));

			if (companyGroupId > 0) {
				templateId = companyId + companyGroupId + templateId;
			}

			String errorTemplateId = getErrorTemplateId();
			String errorTemplateContent = getErrorTemplateContent();

			load = doMergeTemplate(
				templateId, script, context, unsyncStringWriter,
				errorTemplateId, errorTemplateContent);
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

	protected String getErrorTemplateContent() {
		return null;
	}

	protected String getErrorTemplateId() {
		return null;
	}

	protected TemplateContext getTemplateContext() {
		return null;
	}

	protected List<TemplateNode> getTemplateNodes(
			ThemeDisplay themeDisplay, Element root)
		throws TransformException {

		return null;
	}

	protected Map<String, Object> insertRequestVariables(Element parent) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (parent == null) {
			return map;
		}

		for (Element el : parent.elements()) {
			String name = el.getName();

			if (name.equals("attribute")) {
				map.put(el.elementText("name"), el.elementText("value"));
			}
			else if (name.equals("parameter")) {
				name = el.element("name").getText();

				List<Element> valueEls = el.elements("value");

				if (valueEls.size() == 1) {
					map.put(name, (valueEls.get(0)).getText());
				}
				else {
					List<String> values = new ArrayList<String>();

					for (Element valueEl : valueEls) {
						values.add(valueEl.getText());
					}

					map.put(name, values);
				}
			}
			else if (el.elements().size() > 0) {
				map.put(name, insertRequestVariables(el));
			}
			else {
				map.put(name, el.getText());
			}
		}

		return map;
	}

	protected void populateCustomContext(TemplateContext templateContext) {
	}

}