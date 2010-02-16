/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.journal.util;

import com.liferay.portal.freemarker.JournalTemplateLoader;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.freemarker.FreeMarkerContext;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.TransformException;
import com.liferay.util.PwdGenerator;

import freemarker.core.ParseException;

import freemarker.template.TemplateException;

import java.io.IOException;

import java.util.List;
import java.util.Map;

/**
 * <a href="FreeMarkerTemplateParser.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class FreeMarkerTemplateParser extends VelocityTemplateParser {

	public String doTransform(
			Map<String, String> tokens, String viewMode, String languageId,
			String xml, String script)
		throws Exception {

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter(true);

		boolean load = false;

		try {
			FreeMarkerContext freeMarkerContext =
				FreeMarkerEngineUtil.getWrappedRestrictedToolsContext();

			Document doc = SAXReaderUtil.read(xml);

			Element root = doc.getRootElement();

			List<TemplateNode> nodes = extractDynamicContents(root);

			for (TemplateNode node : nodes) {
				freeMarkerContext.put(node.getName(), node);
			}

			freeMarkerContext.put(
				"xmlRequest", root.element("request").asXML());
			freeMarkerContext.put(
				"request", insertRequestVariables(root.element("request")));

			long companyId = GetterUtil.getLong(tokens.get("company_id"));
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
			long groupId = GetterUtil.getLong(tokens.get("group_id"));
			String templateId = tokens.get("template_id");
			String journalTemplatesPath =
				JournalTemplateLoader.JOURNAL_SEPARATOR + StringPool.SLASH +
					companyId + StringPool.SLASH + groupId;
			String randomNamespace =
				PwdGenerator.getPassword(PwdGenerator.KEY3, 4) +
					StringPool.UNDERLINE;

			freeMarkerContext.put("company", company);
			freeMarkerContext.put("companyId", String.valueOf(companyId));
			freeMarkerContext.put("groupId", String.valueOf(groupId));
			freeMarkerContext.put("journalTemplatesPath", journalTemplatesPath);
			freeMarkerContext.put("viewMode", viewMode);
			freeMarkerContext.put(
				"locale", LocaleUtil.fromLanguageId(languageId));
			freeMarkerContext.put(
				"permissionChecker",
				PermissionThreadLocal.getPermissionChecker());
			freeMarkerContext.put("randomNamespace", randomNamespace);

			script = injectEditInPlace(xml, script);

			try {
				String freeMarkerTemplateId = companyId + groupId + templateId;

				load = FreeMarkerEngineUtil.mergeTemplate(
					freeMarkerTemplateId, script, freeMarkerContext,
					unsyncStringWriter);
			}
			catch (SystemException se) {
				if (se.getCause() instanceof TemplateException) {
					TemplateException te = (TemplateException)se.getCause();

					freeMarkerContext.put("exception", te.getMessage());
					freeMarkerContext.put("script", script);

					String freeMarkerTemplateId =
						PropsValues.JOURNAL_ERROR_TEMPLATE_FREEMARKER;
					String freemarkerTemplateContent =
						ContentUtil.get(
							PropsValues.JOURNAL_ERROR_TEMPLATE_FREEMARKER);

					unsyncStringWriter = new UnsyncStringWriter(true);

					load = FreeMarkerEngineUtil.mergeTemplate(
						freeMarkerTemplateId, freemarkerTemplateContent,
						freeMarkerContext, unsyncStringWriter);
				}
				else {
					throw se;
				}
			}
			catch (ParseException pe) {
				freeMarkerContext.put("exception", pe.getMessage());
				freeMarkerContext.put("script", script);

				freeMarkerContext.put("column", new Integer(
					pe.getColumnNumber()));
				freeMarkerContext.put("line", new Integer(
					pe.getLineNumber()));

				String freeMarkerTemplateId =
					PropsValues.JOURNAL_ERROR_TEMPLATE_FREEMARKER;
				String freemarkerTemplateContent =
					ContentUtil.get(
						PropsValues.JOURNAL_ERROR_TEMPLATE_FREEMARKER);

				unsyncStringWriter = new UnsyncStringWriter(true);

				load = FreeMarkerEngineUtil.mergeTemplate(
					freeMarkerTemplateId, freemarkerTemplateContent,
					freeMarkerContext, unsyncStringWriter);
			}
		}
		catch (Exception e) {
			if (e instanceof DocumentException) {
				throw new TransformException("Unable to read XML document", e);
			}
			else if (e instanceof IOException) {
				throw new TransformException(
					"Error reading freemarker template", e);
			}
			else if (e instanceof TransformException) {
				throw (TransformException) e;
			}
			else {
				throw new TransformException("Unhandled exception", e);
			}
		}

		if (!load) {
			throw new TransformException(
				"Unable to dynamically load freemarker transform script");
		}

		return unsyncStringWriter.toString();
	}

}