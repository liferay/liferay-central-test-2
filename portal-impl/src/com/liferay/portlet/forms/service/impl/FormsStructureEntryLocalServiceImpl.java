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

package com.liferay.portlet.forms.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.forms.StructureEntryDuplicateElementException;
import com.liferay.portlet.forms.StructureEntryDuplicateStructureIdException;
import com.liferay.portlet.forms.StructureEntryNameException;
import com.liferay.portlet.forms.StructureEntryStructureIdException;
import com.liferay.portlet.forms.StructureEntryXsdException;
import com.liferay.portlet.forms.model.FormsStructureEntry;
import com.liferay.portlet.forms.model.FormsStructureEntryConstants;
import com.liferay.portlet.forms.service.base.FormsStructureEntryLocalServiceBaseImpl;
import com.liferay.portlet.forms.util.FormsXMLUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Basto
 */
public class FormsStructureEntryLocalServiceImpl
	extends FormsStructureEntryLocalServiceBaseImpl {

	public FormsStructureEntry addStructureEntry(
			long userId, long groupId, String structureId,
			boolean autoStructureId, String name, String description,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Structure entry

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());

		structureId = structureId.trim().toUpperCase();

		if (autoStructureId) {
			structureId = String.valueOf(counterLocalService.increment());
		}

		try {
			xsd = FormsXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureEntryXsdException();
		}

		Date now = new Date();

		validate(groupId, structureId, autoStructureId, name, xsd);

		long structureEntryId = counterLocalService.increment();

		FormsStructureEntry structureEntry =
			formsStructureEntryPersistence.create(structureEntryId);

		structureEntry.setUuid(serviceContext.getUuid());
		structureEntry.setGroupId(serviceContext.getScopeGroupId());
		structureEntry.setCompanyId(user.getCompanyId());
		structureEntry.setUserId(user.getUserId());
		structureEntry.setUserName(user.getFullName());
		structureEntry.setCreateDate(serviceContext.getCreateDate(now));
		structureEntry.setModifiedDate(serviceContext.getModifiedDate(now));
		structureEntry.setStructureId(structureId);
		structureEntry.setName(name);
		structureEntry.setDescription(description);
		structureEntry.setXsd(xsd);

		formsStructureEntryPersistence.update(structureEntry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addStructureEntryResources(
				structureEntry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addStructureEntryResources(
				structureEntry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		return structureEntry;
	}

	public void addStructureEntryResources(
			FormsStructureEntry structureEntry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			structureEntry.getCompanyId(), structureEntry.getGroupId(),
			structureEntry.getUserId(), FormsStructureEntry.class.getName(),
			structureEntry.getStructureId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addStructureEntryResources(
			FormsStructureEntry structureEntry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			structureEntry.getCompanyId(), structureEntry.getGroupId(),
			structureEntry.getUserId(), FormsStructureEntry.class.getName(),
			structureEntry.getStructureId(), communityPermissions,
			guestPermissions);
	}

	public void deleteStructureEntries(long groupId)
		throws PortalException, SystemException {

		for (FormsStructureEntry structureEntry :
				formsStructureEntryPersistence.findByGroupId(groupId)) {

			deleteStructureEntry(structureEntry);
		}
	}

	public void deleteStructureEntry(FormsStructureEntry structureEntry)
		throws PortalException, SystemException {

		// Structure

		formsStructureEntryPersistence.remove(structureEntry);

		// Resources

		resourceLocalService.deleteResource(
			structureEntry.getCompanyId(), FormsStructureEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			structureEntry.getStructureId());
	}

	public void deleteStructureEntry(long groupId, String structureId)
		throws PortalException, SystemException {

		FormsStructureEntry structureEntry =
			formsStructureEntryPersistence.findByG_S(groupId, structureId);

		deleteStructureEntry(structureEntry);
	}

	public List<FormsStructureEntry> getStructureEntries()
		throws SystemException {

		return formsStructureEntryPersistence.findAll();
	}

	public List<FormsStructureEntry> getStructureEntries(long groupId)
		throws SystemException {

		return formsStructureEntryPersistence.findByGroupId(groupId);
	}

	public List<FormsStructureEntry> getStructureEntries(
			long groupId, int start, int end)
		throws SystemException {

		return formsStructureEntryPersistence.findByGroupId(
			groupId, start, end);
	}

	public int getStructureEntriesCount(long groupId) throws SystemException {
		return formsStructureEntryPersistence.countByGroupId(groupId);
	}

	public FormsStructureEntry getStructureEntry(long structureEntryId)
		throws PortalException, SystemException {

		return formsStructureEntryPersistence.findByPrimaryKey(
			structureEntryId);
	}

	public FormsStructureEntry getStructureEntry(
			long groupId, String structureId)
		throws PortalException, SystemException {

		return formsStructureEntryPersistence.findByG_S(
			groupId, structureId);
	}

	public FormsStructureEntry updateStructureEntry(
			long groupId, String structureId, String name,
			String description, String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			xsd = FormsXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new StructureEntryXsdException();
		}

		FormsStructureEntry structureEntry =
			formsStructureEntryPersistence.findByG_S(groupId, structureId);

		validate(groupId, name, xsd);

		structureEntry.setModifiedDate(serviceContext.getModifiedDate(null));
		structureEntry.setName(name);
		structureEntry.setDescription(description);
		structureEntry.setXsd(xsd);

		formsStructureEntryPersistence.update(structureEntry, false);

		return structureEntry;
	}

	protected void validate(List<Element> elements, Set<String> names)
		throws PortalException {

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("meta-data")) {
				continue;
			}

			String name = element.attributeValue("name", StringPool.BLANK);
			String type = element.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(name) ||
				name.startsWith(FormsStructureEntryConstants.RESERVED)) {

				throw new StructureEntryXsdException();
			}

			char[] charArray = name.toCharArray();

			for (int i = 0; i < charArray.length; i++) {
				if (!Validator.isChar(charArray[i]) &&
					!Validator.isDigit(charArray[i]) &&
					(charArray[i] != CharPool.DASH) &&
					(charArray[i] != CharPool.UNDERLINE)) {

					throw new StructureEntryXsdException();
				}
			}

			String path = name;

			Element parentElement = element.getParent();

			while (!parentElement.isRootElement()) {
				path =
					parentElement.attributeValue("name", StringPool.BLANK) +
						StringPool.SLASH + path;

				parentElement = parentElement.getParent();
			}

			path = path.toLowerCase();

			if (names.contains(path)) {
				throw new StructureEntryDuplicateElementException();
			}
			else {
				names.add(path);
			}

			if (Validator.isNull(type)) {
				throw new StructureEntryXsdException();
			}

			validate(element.elements(), names);
		}
	}

	protected void validate(
			long groupId, String structureId, boolean autoStructureId,
			String name, String xsd)
		throws PortalException, SystemException {

		if (!autoStructureId) {
			validateStructureId(structureId);

			FormsStructureEntry structureEntry =
				formsStructureEntryPersistence.fetchByG_S(
					groupId, structureId);

			if (structureEntry != null) {
				throw new StructureEntryDuplicateStructureIdException();
			}
		}

		validate(groupId, name, xsd);
	}

	protected void validate(long groupId, String name, String xsd)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new StructureEntryNameException();
		}

		if (Validator.isNull(xsd)) {
			throw new StructureEntryXsdException();
		}
		else {
			try {
				List<Element> elements = new ArrayList<Element>();

				Document document = SAXReaderUtil.read(xsd);

				Element rootElement = document.getRootElement();

				if (rootElement.elements().isEmpty()) {
					throw new StructureEntryXsdException();
				}

				elements.addAll(rootElement.elements());

				Set<String> elNames = new HashSet<String>();

				validate(elements, elNames);
			}
			catch (StructureEntryDuplicateElementException fdsee) {
				throw fdsee;
			}
			catch (StructureEntryXsdException sxe) {
				throw sxe;
			}
			catch (Exception e) {
				throw new StructureEntryXsdException();
			}
		}
	}

	protected void validateStructureId(String structureId)
		throws PortalException {

		if (Validator.isNull(structureId) || Validator.isNumber(structureId) ||
			structureId.indexOf(CharPool.SPACE) != -1) {

			throw new StructureEntryStructureIdException();
		}
	}

}