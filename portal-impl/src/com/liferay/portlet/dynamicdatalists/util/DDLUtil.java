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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcelllus Tavares
 */
public class DDLUtil {

	public static void addAllReservedEls(
		Element root, Map<String, String> tokens, DDLRecordSet recordSet) {

		JournalUtil.addReservedEl(
			root, tokens, DDLConstants.RESERVED_RECORD_SET_ID,
			String.valueOf(recordSet.getRecordSetId()));

		JournalUtil.addReservedEl(
			root, tokens, DDLConstants.RESERVED_RECORD_SET_NAME,
			recordSet.getName());

		JournalUtil.addReservedEl(
			root, tokens, DDLConstants.RESERVED_RECORD_SET_DESCRIPTION,
			recordSet.getDescription());

		JournalUtil.addReservedEl(
			root, tokens, DDLConstants.RESERVED_DDM_STRUCTURE_ID,
			String.valueOf(recordSet.getDDMStructureId()));
	}

	public static String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		String viewMode = ParamUtil.getString(renderRequest, "viewMode");
		String languageId = LanguageUtil.getLanguageId(renderRequest);
		String xmlRequest = PortletRequestUtil.toXML(
			renderRequest, renderResponse);

		if (Validator.isNull(xmlRequest)) {
			xmlRequest = "<request />";
		}

		Map<String, String> tokens = JournalUtil.getTokens(
			recordSet.getGroupId(), themeDisplay, xmlRequest);

		String xml = StringPool.BLANK;

		Document doc = SAXReaderUtil.createDocument();

		Element root = doc.addElement("root");

		Document request = SAXReaderUtil.read(xmlRequest);

		root.add(request.getRootElement().createCopy());

		addAllReservedEls(root, tokens, recordSet);

		xml = DDMXMLUtil.formatXML(doc);

		String script = template.getScript();
		String langType = template.getLanguage();

		return _transformer.transform(
			themeDisplay, tokens, viewMode, languageId, xml, script, langType);
	}

	private static Transformer _transformer = new DDLTransformer();

}