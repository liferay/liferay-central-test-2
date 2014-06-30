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

package com.liferay.portlet.dynamicdatalists.model.impl;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTest;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Marcellus Tavares
 */
@PrepareForTest(
	{
		DDMStructureLocalServiceUtil.class, DDMTemplateLocalServiceUtil.class,
		LocaleUtil.class, PropsUtil.class
	})
public class DDLRecordSetImplTest extends BaseDDMTest {

	@Before
	public void setUp() {
		setUpDDMFormXSDDeserializerUtil();
		setUpDDMStructureLocalServiceUtil();
		setUpDDMTemplateLocalServiceUtil();
		setUpHtmlUtil();
		setUpLocaleUtil();
		setUpPropsUtil();
		setUpSAXReaderUtil();
	}

	@Test
	public void testGetDDMStructure() throws Exception {
		Document document = createDocument("Text 1", "Text 2", "Text 3");

		DDMStructure ddmStructure = createStructure(
			"Test Structure", "Text 1", "Text 2", "Text 3");

		DDLRecordSet recordSet = createRecordSet(
			ddmStructure.getStructureId(), "Test Record Set");

		Element rootElement = document.getRootElement();

		Element dynamicElement = rootElement.element("dynamic-element");

		rootElement.remove(dynamicElement);

		DDMTemplate template = createTemplate(
			ddmStructure.getStructureId(), "Test Form Template",
			document.asXML());

		Set<String> fieldNames = ddmStructure.getFieldNames();

		DDMStructure recordSetDDMStructure = recordSet.getDDMStructure(
			template.getTemplateId());

		if (fieldNames.equals(recordSetDDMStructure.getFieldNames())) {
			Assert.fail();
		}

		recordSetDDMStructure = recordSet.getDDMStructure();

		if (!fieldNames.equals(recordSetDDMStructure.getFieldNames())) {
			Assert.fail();
		}
	}

	protected DDLRecordSet createRecordSet(long ddmStructureId, String name) {
		DDLRecordSet recordSet = new DDLRecordSetImpl();

		recordSet.setDDMStructureId(ddmStructureId);
		recordSet.setName(name);

		return recordSet;
	}

}