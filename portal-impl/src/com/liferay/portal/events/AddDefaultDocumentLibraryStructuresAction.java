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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.metadata.TikaRawMetadataProcessor;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.io.StringReader;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Miguel Pastor
 * @author Sergio González
 */
public class AddDefaultDocumentLibraryStructuresAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void addDDMStructures(
			long userId, long groupId, ServiceContext serviceContext)
		throws DocumentException, PortalException, SystemException {

		String xml = ContentUtil.get(
			"com/liferay/portal/events/dependencies/" +
				"document-library-structures.xml");

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			String name = structureElement.elementText("name");
			String structureKey = name;

			String description = structureElement.elementText("description");

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(groupId, structureKey);

			if (ddmStructure != null) {
				continue;
			}

			Element structureElementRootElement = structureElement.element(
				"root");

			String xsd = structureElementRootElement.asXML();

			Map<Locale, String> nameMap = new HashMap<Locale, String>();

			nameMap.put(LocaleUtil.getDefault(), name);

			Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

			descriptionMap.put(LocaleUtil.getDefault(), description);

			DDMStructureLocalServiceUtil.addStructure(
				userId, groupId,
				PortalUtil.getClassNameId(DLDocumentMetadataSet.class),
				structureKey, nameMap, descriptionMap, xsd, "xml",
				serviceContext);
		}
	}

	protected void addDLDocumentType(
			long userId, long groupId, String dlDocumentTypeName,
			String dlDocumentTypeDescription, String ddmStructureName,
			ServiceContext serviceContext)
		throws Exception {

		String structureKey = ddmStructureName;

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			groupId, structureKey);

		if (ddmStructure == null) {
			return;
		}

		long[] ddmStructureId = new long[] {ddmStructure.getStructureId()};

		List<DLDocumentType> dlDocumentTypes =
			DLDocumentTypeLocalServiceUtil.getDocumentTypes(
				groupId, dlDocumentTypeName, dlDocumentTypeDescription);

		if (dlDocumentTypes.isEmpty()) {
			DLDocumentTypeLocalServiceUtil.addDocumentType(
				userId, groupId, dlDocumentTypeName, dlDocumentTypeDescription,
				ddmStructureId,	serviceContext);
		}
	}

	protected void addDLDocumentTypes(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		addDLDocumentType(
			userId, groupId, "Image", "Image Document Type",
			"Default Image's Metadata Set", serviceContext);

		addDLDocumentType(
			userId, groupId, "Video", "Video Document Type",
			"Default Videos's Metadata Set", serviceContext);
	}

	protected void addDLRawMetadataStructures(
		long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		String xsd = buildDLRawMetadataConfig(
			TikaRawMetadataProcessor.RAW_METADATA_SETS);

		Document document = SAXReaderUtil.read(new StringReader(xsd));

		Element rootElement = document.getRootElement();
		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			String name = structureElement.elementText("name");
			String description = structureElement.elementText("description");

			Element structureElementRootElement = structureElement.element(
				"root");
			String elementXsd = structureElementRootElement.asXML();

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(groupId, name);

			if (ddmStructure != null) {
				ddmStructure.setXsd(elementXsd);

				DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure);
			}
			else {
				HashMap<Locale, String> nameMap = new HashMap<Locale, String>();

				nameMap.put(LocaleUtil.getDefault(), name);

				HashMap<Locale, String> descriptionMap =
					new HashMap<Locale, String>();

				nameMap.put(LocaleUtil.getDefault(), description);

				DDMStructureLocalServiceUtil.addStructure(
					userId, groupId,
					PortalUtil.getClassNameId(DLFileEntry.class),
					name, nameMap, descriptionMap,
					elementXsd, "xml", serviceContext);
			}
		}
	}

	protected String buildDLRawMetadataConfig(Map<String, Field[]> fields) {
		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root>");

		for (String key : fields.keySet()) {
			sb.append(buildDLRawMetadataStructureConfig(
				key, fields.get(key)));
		}

		sb.append("</root>");

		return sb.toString();
	}

	protected String buildDLRawMetadataElementConfig(
		Field field, String structureName) {

		StringBundler sb = new StringBundler();
		String name = field.getName();

		sb.append("<dynamic-element dataType=\"string\" name=\"");
		sb.append(name);
		sb.append("\" type=\"text\">");

		sb.append(
			"<meta-data><entry name=\"label\"><![CDATA[");

		sb.append(I18N_RAW_METADATA_PREFIX);
		sb.append(structureName);
		sb.append(StringPool.PERIOD);
		sb.append(name);

		sb.append("]]></entry><entry name=\"predefinedValue\">");
		sb.append("<![CDATA[]]></entry><entry name=\"required\">");
		sb.append("<![CDATA[false]]></entry><entry name=\"showLabel\">");
		sb.append("<![CDATA[true]]></entry></meta-data></dynamic-element>");

		return sb.toString();
	}

	protected String buildDLRawMetadataStructureConfig(
		String name, Field[] fields) {

		StringBundler sb = new StringBundler();

		sb.append("<structure><name><![CDATA[");
		sb.append(name);
		sb.append("]]></name>");

		sb.append("<description><![CDATA[");
		sb.append(name);
		sb.append("]]></description><root>");

		for (Field field : fields) {
			sb.append(buildDLRawMetadataElementConfig(field, name));
		}

		sb.append("</root></structure>");

		return sb.toString();
	}

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		addDDMStructures(
			defaultUserId, group.getGroupId(), serviceContext);
		addDLDocumentTypes(defaultUserId, group.getGroupId(), serviceContext);
		addDLRawMetadataStructures(
			defaultUserId, group.getGroupId(), serviceContext);
	}

	private static final String I18N_RAW_METADATA_PREFIX = "metadata.";

}