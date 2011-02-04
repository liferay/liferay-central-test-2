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
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.forms.EntryDuplicateElementException;
import com.liferay.portlet.forms.EntryDuplicateStructureIdException;
import com.liferay.portlet.forms.EntryNameException;
import com.liferay.portlet.forms.EntryStructureIdException;
import com.liferay.portlet.forms.EntryXsdException;
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
 * @author Bruno Farache
 * @author Bruno Basto
 * @author Eduardo Lundgren
 */
public class FormsStructureEntryLocalServiceImpl
	extends FormsStructureEntryLocalServiceBaseImpl {

	public FormsStructureEntry addEntry(
			long userId, long groupId, String formStructureId,
			boolean autoFormStructureId, String name, String description,
			String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Structure

		User user = userPersistence.findByPrimaryKey(
			serviceContext.getUserId());

		formStructureId = formStructureId.trim().toUpperCase();
		Date now = new Date();

		try {
			xsd = FormsXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new EntryXsdException();
		}

		if (autoFormStructureId) {
			formStructureId = String.valueOf(counterLocalService.increment());
		}

		validate(groupId, formStructureId, autoFormStructureId, name, xsd);

		long id = counterLocalService.increment();

		FormsStructureEntry entry = formsStructureEntryPersistence.create(id);

		entry.setUuid(serviceContext.getUuid());
		entry.setFormStructureId(formStructureId);
		entry.setGroupId(serviceContext.getScopeGroupId());
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
		entry.setName(name);
		entry.setDescription(description);
		entry.setXsd(xsd);

		formsStructureEntryPersistence.update(entry, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addEntryResources(
				entry, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addEntryResources(
				entry, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return entry;
	}

	public void addEntryResources(
			FormsStructureEntry entry, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(),
			entry.getUserId(), FormsStructureEntry.class.getName(),
			entry.getFormStructureId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addEntryResources(
			FormsStructureEntry entry, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(),
			entry.getUserId(), FormsStructureEntry.class.getName(),
			entry.getFormStructureId(), communityPermissions,
			guestPermissions);
	}

	public void deleteEntry(FormsStructureEntry entry)
		throws PortalException, SystemException {

		// Expando

		expandoValueLocalService.deleteValues(
			FormsStructureEntry.class.getName(), entry.getPrimaryKey());

		// Resources

		resourceLocalService.deleteResource(
			entry.getCompanyId(), FormsStructureEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			entry.getFormStructureId());

		// Structure

		formsStructureEntryPersistence.remove(entry);
	}

	public void deleteEntry(long groupId, String formStrucutreId)
		throws PortalException, SystemException {

		FormsStructureEntry entry =
			formsStructureEntryPersistence.findByG_F(groupId, formStrucutreId);

		deleteEntry(entry);
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		for (FormsStructureEntry entry :
				formsStructureEntryPersistence.findByGroupId(groupId)) {

			deleteEntry(entry);
		}
	}

	public FormsStructureEntry fetchByG_F(long groupId, String formStructureId)
		throws PortalException, SystemException {

		return formsStructureEntryPersistence.fetchByG_F(
			groupId, formStructureId);
	}

	public FormsStructureEntry getEntry(long id)
		throws PortalException, SystemException {

		return formsStructureEntryPersistence.findByPrimaryKey(id);
	}

	public FormsStructureEntry getEntry(
			long groupId, String formStructureId)
		throws PortalException, SystemException {

		return formsStructureEntryPersistence.findByG_F(
			groupId, formStructureId);
	}

	public List<FormsStructureEntry> getEntries() throws SystemException {
		return formsStructureEntryPersistence.findAll();
	}

	public List<FormsStructureEntry> getEntries(long groupId)
		throws SystemException {

		return formsStructureEntryPersistence.findByGroupId(groupId);
	}

	public List<FormsStructureEntry> getEntries(
			long groupId, int start, int end)
		throws SystemException {

		return formsStructureEntryPersistence.findByGroupId(
			groupId, start, end);
	}

	public int getEntriesCount(long groupId) throws SystemException {
		return formsStructureEntryPersistence.countByGroupId(groupId);
	}

	public FormsStructureEntry updateEntry(
			long groupId, String formStructureId, String name,
			String description, String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			xsd = FormsXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new EntryXsdException();
		}

		FormsStructureEntry entry =
			formsStructureEntryPersistence.findByG_F(groupId, formStructureId);

		validate(groupId, name, xsd);

		entry.setModifiedDate(serviceContext.getModifiedDate(null));
		entry.setName(name);
		entry.setDescription(description);
		entry.setXsd(xsd);

		formsStructureEntryPersistence.update(entry, false);

		// Expando

		ExpandoBridge expandoBridge = entry.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return entry;
	}

	protected void validate(List<Element> elements, Set<String> elNames)
		throws PortalException {

		for (Element element : elements) {
			if (element.getName().equals("meta-data")) {
				continue;
			}

			String elName = element.attributeValue("name", StringPool.BLANK);
			String elType = element.attributeValue("type", StringPool.BLANK);

			if (Validator.isNull(elName) ||
				elName.startsWith(FormsStructureEntryConstants.RESERVED)) {

				throw new EntryXsdException();
			}
			else {
				char[] c = elName.toCharArray();

				for (int i = 0; i < c.length; i++) {
					if (!Validator.isChar(c[i]) &&
						!Validator.isDigit(c[i]) &&
						c[i] != CharPool.DASH &&
						c[i] != CharPool.UNDERLINE) {

						throw new EntryXsdException();
					}
				}

				String completePath = elName;

				Element parentElement = element.getParent();

				while (!parentElement.isRootElement()) {
					completePath =
						parentElement.attributeValue(
							"name", StringPool.BLANK) + StringPool.SLASH +
							completePath;

					parentElement = parentElement.getParent();
				}

				String elNameLowerCase = completePath.toLowerCase();

				if (elNames.contains(elNameLowerCase)) {
					throw new EntryDuplicateElementException();
				}
				else {
					elNames.add(elNameLowerCase);
				}
			}

			if (Validator.isNull(elType)) {
				throw new EntryXsdException();
			}

			validate(element.elements(), elNames);
		}
	}

	protected void validate(
			long groupId, String formStructureId, boolean autoFormStructureId,
			String name, String xsd)
		throws PortalException, SystemException {

		if (!autoFormStructureId) {
			validateFormStructureId(formStructureId);

			FormsStructureEntry entry =
				formsStructureEntryPersistence.fetchByG_F(
					groupId, formStructureId);

			if (entry != null) {
				throw new EntryDuplicateStructureIdException();
			}
		}

		validate(groupId, name, xsd);
	}

	protected void validate(
			long groupId, String name, String xsd)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new EntryNameException();
		}

		if (Validator.isNull(xsd)) {
			throw new EntryXsdException();
		}
		else {
			try {
				List<Element> elements = new ArrayList<Element>();

				Document document = SAXReaderUtil.read(xsd);

				Element rootElement = document.getRootElement();

				if (rootElement.elements().isEmpty()) {
					throw new EntryXsdException();
				}

				elements.addAll(rootElement.elements());

				Set<String> elNames = new HashSet<String>();

				validate(elements, elNames);
			}
			catch (EntryDuplicateElementException fdsee) {
				throw fdsee;
			}
			catch (EntryXsdException sxe) {
				throw sxe;
			}
			catch (Exception e) {
				throw new EntryXsdException();
			}
		}
	}

	protected void validateFormStructureId(String formStructureId)
		throws PortalException {

		if (Validator.isNull(formStructureId) ||
			Validator.isNumber(formStructureId) ||
			formStructureId.indexOf(CharPool.SPACE) != -1) {

			throw new EntryStructureIdException();
		}
	}

}