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

package com.liferay.portal.kernel.transformation;

import com.liferay.portal.TransformException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
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

	protected String doGetErrorTemplateContent() {
		return null;
	}

	protected String doGetErrorTemplateId() {
		return null;
	}

	protected TransformationContext doGetTransformationContext() {
		return null;
	}

	protected boolean doMergeTemplate(
			String templateId, String script, TransformationContext context,
			Writer writer, String errorTemplateId, String errorTemplateContent)
		throws Exception {

		return false;
	}

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId,	String xml, String script)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try	{
			TransformationContext context = doGetTransformationContext();

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<TemplateNode> nodes = extractDynamicContents(
				themeDisplay, root);

			for (TemplateNode node : nodes) {
				context.put(node.getName(), node);
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

			doPopulateCustomContext(context);

			script = injectEditInPlace(xml, script);

			templateId = companyId + groupId + templateId;

			long companyGroupId = GetterUtil.getLong(
				tokens.get("company_group_id"));

			if (companyGroupId > 0) {
				templateId = companyId + companyGroupId + templateId;
			}

			String errorTemplateId = doGetErrorTemplateId();
			String errorTemplateContent = doGetErrorTemplateContent();

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

	protected void doPopulateCustomContext(TransformationContext context) {
	}

	protected List<TemplateNode> extractDynamicContents(
			ThemeDisplay themeDisplay, Element parent)
		throws TransformException {

		List<TemplateNode> nodes = new ArrayList<TemplateNode>();

		Map<String, TemplateNode> prototypeNodes =
			new HashMap<String, TemplateNode>();

		for (Element el : parent.elements("dynamic-element")) {
			Element content = el.element("dynamic-content");

			String data = StringPool.BLANK;

			if (content != null) {
				data = content.getText();
			}

			String name = el.attributeValue("name", "");

			if (name.length() == 0) {
				throw new TransformException(
					"Element missing \"name\" attribute");
			}

			String type = el.attributeValue("type", "");

			data = StringUtil.stripBetween(
				data, StringPool.CDATA_OPEN, StringPool.CDATA_CLOSE);

			TemplateNode node = new TemplateNode(
				themeDisplay, name, data, type);

			if (el.element("dynamic-element") != null) {
				node.appendChildren(extractDynamicContents(themeDisplay, el));
			}
			else if ((content != null) && (content.element("option") != null)) {
				for (Element option : content.elements("option")) {
					String value = StringUtil.stripBetween(
						option.getText(), StringPool.CDATA_OPEN,
						StringPool.CDATA_CLOSE);

					node.appendOption(value);
				}
			}

			TemplateNode prototypeNode = prototypeNodes.get(name);

			if (prototypeNode == null) {
				prototypeNode = node;

				prototypeNodes.put(name, prototypeNode);

				nodes.add(node);
			}

			prototypeNode.appendSibling(node);
		}

		return nodes;
	}

	protected String injectEditInPlace(String xml, String script)
		throws DocumentException {

		Document doc = SAXReaderUtil.read(xml);

		List<Node> nodes = doc.selectNodes("//dynamic-element");

		for (Node node : nodes) {
			Element el = (Element)node;

			String name = GetterUtil.getString(el.attributeValue("name"));
			String type = GetterUtil.getString(el.attributeValue("type"));

			if ((!name.startsWith("reserved-")) &&
				(type.equals("text") || type.equals("text_box") ||
				 type.equals("text_area"))) {

				script = wrapField(script, name, type, "data");
				script = wrapField(script, name, type, "getData()");
			}
		}

		return script;
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

	protected String wrapField(
		String script, String name, String type, String call) {

		String field = "$" + name + "." + call;
		String wrappedField =
			"<span class=\"journal-content-eip-" + type + "\" " +
				"id=\"journal-content-field-name-" + name + "\">" + field +
					"</span>";

		return StringUtil.replace(
			script, "$editInPlace(" + field + ")", wrappedField);
	}

}