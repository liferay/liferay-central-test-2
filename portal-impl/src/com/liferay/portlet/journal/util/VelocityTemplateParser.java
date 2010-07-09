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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.velocity.VelocityContext;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.velocity.VelocityResourceListener;
import com.liferay.portlet.journal.TransformException;
import com.liferay.util.PwdGenerator;
import com.liferay.util.xml.CDATAUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.VelocityException;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class VelocityTemplateParser extends BaseTemplateParser {

	protected String doTransform(
			ThemeDisplay themeDisplay, Map<String, String> tokens,
			String viewMode, String languageId,	String xml, String script)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		boolean load = false;

		try {
			VelocityContext velocityContext =
				VelocityEngineUtil.getWrappedRestrictedToolsContext();

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<TemplateNode> nodes = extractDynamicContents(
				themeDisplay, root);

			for (TemplateNode node : nodes) {
				velocityContext.put(node.getName(), node);
			}

			velocityContext.put("xmlRequest", root.element("request").asXML());
			velocityContext.put(
				"request", insertRequestVariables(root.element("request")));

			long companyId = GetterUtil.getLong(tokens.get("company_id"));
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
			long groupId = GetterUtil.getLong(tokens.get("group_id"));
			String templateId = tokens.get("template_id");
			String journalTemplatesPath =
				VelocityResourceListener.JOURNAL_SEPARATOR + StringPool.SLASH +
					companyId + StringPool.SLASH + groupId;
			String randomNamespace =
				PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
					StringPool.UNDERLINE;

			velocityContext.put("company", company);
			velocityContext.put("companyId", String.valueOf(companyId));
			velocityContext.put("groupId", String.valueOf(groupId));
			velocityContext.put("journalTemplatesPath", journalTemplatesPath);
			velocityContext.put("viewMode", viewMode);
			velocityContext.put(
				"locale", LocaleUtil.fromLanguageId(languageId));
			velocityContext.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
			velocityContext.put("randomNamespace", randomNamespace);

			script = injectEditInPlace(xml, script);

			try {
				String velocityTemplateId = companyId + groupId + templateId;

				load = VelocityEngineUtil.mergeTemplate(
					velocityTemplateId, script, velocityContext,
					unsyncStringWriter);
			}
			catch (VelocityException ve) {
				velocityContext.put("exception", ve.getMessage());
				velocityContext.put("script", script);

				if (ve instanceof ParseErrorException) {
					ParseErrorException pe = (ParseErrorException)ve;

					velocityContext.put(
						"column", new Integer(pe.getColumnNumber()));
					velocityContext.put(
						"line", new Integer(pe.getLineNumber()));
				}

				String velocityTemplateId =
					PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY;
				String velocityTemplateContent = ContentUtil.get(
					PropsValues.JOURNAL_ERROR_TEMPLATE_VELOCITY);

				load = VelocityEngineUtil.mergeTemplate(
					velocityTemplateId, velocityTemplateContent,
					velocityContext, unsyncStringWriter);
			}
		}
		catch (Exception e) {
			if (e instanceof DocumentException) {
				throw new TransformException("Unable to read XML document", e);
			}
			else if (e instanceof VelocityException) {
				VelocityException pex = (VelocityException)e;

				throw new TransformException(
					"Unable to parse velocity template: " +
						HtmlUtil.escape(pex.getMessage()),
					e);
			}
			else if (e instanceof IOException) {
				throw new TransformException(
					"Error reading velocity template", e);
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
				"Unable to dynamically load velocity transform script");
		}

		return unsyncStringWriter.toString();
	}

	protected List<TemplateNode> extractDynamicContents(
			ThemeDisplay themeDisplay, Element parent)
		throws TransformException {

		List<TemplateNode> nodes = new ArrayList<TemplateNode>();

		Map<String, TemplateNode> prototypeNodes =
			new HashMap<String, TemplateNode>();

		for (Element el : parent.elements("dynamic-element")) {
			Element content = el.element("dynamic-content");

			if (content == null) {
				throw new TransformException(
					"Element missing \"dynamic-content\"");
			}

			String name = el.attributeValue("name", "");

			if (name.length() == 0) {
				throw new TransformException(
					"Element missing \"name\" attribute");
			}

			String type = el.attributeValue("type", "");

			TemplateNode node = new TemplateNode(
				themeDisplay, name, CDATAUtil.strip(content.getText()), type);

			if (el.element("dynamic-element") != null) {
				node.appendChildren(extractDynamicContents(themeDisplay, el));
			}
			else if (content.element("option") != null) {
				for (Element option : content.elements("option")) {
					node.appendOption(CDATAUtil.strip(option.getText()));
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