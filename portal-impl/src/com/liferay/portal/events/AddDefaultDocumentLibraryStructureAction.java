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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.util.List;

/**
 * @author Sergio González
 */
public class AddDefaultDocumentLibraryStructureAction extends SimpleAction {

	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun(long companyId) throws Exception {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		Group generalGuestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		long groupId = generalGuestGroup.getGroupId();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(defaultUserId);
		serviceContext.setScopeGroupId(groupId);

		addDocumentStructures(defaultUserId, groupId, serviceContext);
		addDocumentTypes(defaultUserId, groupId, serviceContext);
	}

	protected void addDocumentStructures(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(DDLRecordSet.class);

		String structureKey = StringPool.BLANK;

		boolean autoStructureKey = true;

		String storageType = "xml";

		String xml = ContentUtil.get(
			"com/liferay/portal/dependencies/" +
				"liferay-documentlibrary-structures.xml");

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			Element nameElement = structureElement.element("name");
			Element descriptionElement = structureElement.element(
				"description");

			String name = nameElement.getText();
			String description = descriptionElement. getText();

			List<DDMStructure> structures =
					DDMStructureLocalServiceUtil.getStructure(
						groupId, name, description);

			if (structures.size() > 0) {
				continue;
			}

			Element structureDefinition = structureElement.element("root");

			String xsd = structureDefinition.asXML();

			DDMStructureLocalServiceUtil.addStructure(
				userId, groupId, classNameId, structureKey,
				autoStructureKey, name, description, xsd, storageType,
				serviceContext);
		}
	}

	protected void addDocumentType(
			long userId, long groupId, String documentTypeName,
			String documentTypeDescription, String structureName,
			String structureDescription, ServiceContext serviceContext)
		throws Exception {

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructure(
					groupId, structureName, structureDescription);

		if (structures.size() == 0) {
			return;
		}

		long[] ddmStructureId = new long[] {structures.get(0).getStructureId()};

		List<DLDocumentType> documentTypes =
			DLDocumentTypeLocalServiceUtil.getDocumentTypes(
				groupId, documentTypeName, documentTypeDescription);

		if (documentTypes.size() == 0) {
			DLDocumentTypeLocalServiceUtil.addDocumentType(
				userId, groupId, documentTypeName, documentTypeDescription,
				ddmStructureId,	serviceContext);
		}
	}

	protected void addDocumentTypes(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		addDocumentType(userId, groupId, "Image", "Image Document Type",
			"Default Image's Metadata Set based on Dublin Core Metadata " +
				"Iniative",
			"Default Image's Metadata Set based on Dublin Core Metadata " +
				"Iniative", serviceContext);

		addDocumentType(userId, groupId, "Video", "Video Document Type",
			"Default Videos's Metadata Set", "Default Videos's Metadata Set",
			serviceContext);
	}

}