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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLDocumentMetadataSet;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.DLDocumentTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.util.ContentUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
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
		throws Exception {

		String xml = ContentUtil.get(
			"com/liferay/portal/events/dependencies/" +
				"document-library-structures.xml");

		Document document = SAXReaderUtil.read(xml);

		Element rootElement = document.getRootElement();

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			String name = structureElement.elementText("name");
			String description = structureElement.elementText("description");

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					groupId, name);

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
				PortalUtil.getClassNameId(DLDocumentMetadataSet.class), name,
				nameMap, descriptionMap, xsd, "xml", serviceContext);
		}
	}

	protected void addDLDocumentType(
			long userId, long groupId, String dlDocumentTypeName,
			String dlDocumentTypeDescription, String ddmStructureName,
			ServiceContext serviceContext)
		throws Exception {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				groupId, ddmStructureName);

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
			"Default Image's Metadata Set",  serviceContext);

		addDLDocumentType(
			userId, groupId, "Video", "Video Document Type",
			"Default Videos's Metadata Set", serviceContext);
	}

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getGroup(
			companyId, GroupConstants.GUEST);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		addDDMStructures(defaultUserId, group.getGroupId(), serviceContext);
		addDLDocumentTypes(defaultUserId, group.getGroupId(), serviceContext);
	}

}