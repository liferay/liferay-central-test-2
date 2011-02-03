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
import com.liferay.portlet.forms.FormDuplicateStructureElementException;
import com.liferay.portlet.forms.FormDuplicateStructureIdException;
import com.liferay.portlet.forms.FormStructureIdException;
import com.liferay.portlet.forms.FormStructureNameException;
import com.liferay.portlet.forms.FormStructureXsdException;
import com.liferay.portlet.forms.model.FormStructure;
import com.liferay.portlet.forms.model.FormStructureConstants;
import com.liferay.portlet.forms.service.base.FormStructureLocalServiceBaseImpl;
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
public class FormStructureLocalServiceImpl
	extends FormStructureLocalServiceBaseImpl {

	public FormStructure addFormStructure(
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
			throw new FormStructureXsdException();
		}

		if (autoFormStructureId) {
			formStructureId = String.valueOf(counterLocalService.increment());
		}

		validate(groupId, formStructureId, autoFormStructureId, name, xsd);

		long id = counterLocalService.increment();

		FormStructure formStructure = formStructurePersistence.create(id);

		formStructure.setUuid(serviceContext.getUuid());
		formStructure.setFormStructureId(formStructureId);
		formStructure.setGroupId(serviceContext.getScopeGroupId());
		formStructure.setCompanyId(user.getCompanyId());
		formStructure.setUserId(user.getUserId());
		formStructure.setUserName(user.getFullName());
		formStructure.setCreateDate(serviceContext.getCreateDate(now));
		formStructure.setModifiedDate(serviceContext.getModifiedDate(now));
		formStructure.setName(name);
		formStructure.setDescription(description);
		formStructure.setXsd(xsd);

		formStructurePersistence.update(formStructure, false);

		// Resources

		if (serviceContext.getAddCommunityPermissions() ||
			serviceContext.getAddGuestPermissions()) {

			addFormStructureResources(
				formStructure, serviceContext.getAddCommunityPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFormStructureResources(
				formStructure, serviceContext.getCommunityPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Expando

		ExpandoBridge expandoBridge = formStructure.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return formStructure;
	}

	public void addFormStructureResources(
			FormStructure formStructure, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			formStructure.getCompanyId(), formStructure.getGroupId(),
			formStructure.getUserId(), FormStructure.class.getName(),
			formStructure.getFormStructureId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFormStructureResources(
			FormStructure formStructure, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			formStructure.getCompanyId(), formStructure.getGroupId(),
			formStructure.getUserId(), FormStructure.class.getName(),
			formStructure.getFormStructureId(), communityPermissions,
			guestPermissions);
	}

	public void deleteFormStructure(FormStructure formStructure)
		throws PortalException, SystemException {

		// Expando

		expandoValueLocalService.deleteValues(
			FormStructure.class.getName(), formStructure.getPrimaryKey());

		// Resources

		resourceLocalService.deleteResource(
			formStructure.getCompanyId(), FormStructure.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			formStructure.getFormStructureId());

		// Structure

		formStructurePersistence.remove(formStructure);
	}

	public void deleteFormStructure(long groupId, String formStructureId)
		throws PortalException, SystemException {

		FormStructure formStructure =
			formStructurePersistence.findByG_F(groupId, formStructureId);

		deleteFormStructure(formStructure);
	}

	public void deleteFormStructures(long groupId)
		throws PortalException, SystemException {

		for (FormStructure formStructure :
				formStructurePersistence.findByGroupId(groupId)) {

			deleteFormStructure(formStructure);
		}
	}

	public FormStructure fetchByG_F(long groupId, String formStructureId)
		throws PortalException, SystemException {

		return formStructurePersistence.fetchByG_F(groupId, formStructureId);
	}

	public FormStructure getFormStructure(long id)
		throws PortalException, SystemException {

		return formStructurePersistence.findByPrimaryKey(id);
	}

	public FormStructure getFormStructure(long groupId, String formStructureId)
		throws PortalException, SystemException {

		return formStructurePersistence.findByG_F(groupId, formStructureId);
	}

	public List<FormStructure> getFormStructures() throws SystemException {
		return formStructurePersistence.findAll();
	}

	public List<FormStructure> getFormStructures(long groupId)
		throws SystemException {

		return formStructurePersistence.findByGroupId(groupId);
	}

	public List<FormStructure> getFormStructures(
			long groupId, int start, int end)
		throws SystemException {

		return formStructurePersistence.findByGroupId(groupId, start, end);
	}

	public int getFormStructuresCount(long groupId) throws SystemException {
		return formStructurePersistence.countByGroupId(groupId);
	}

	public FormStructure updateFormStructure(
			long groupId, String formStructureId, String name,
			String description, String xsd, ServiceContext serviceContext)
		throws PortalException, SystemException {

		try {
			xsd = FormsXMLUtil.formatXML(xsd);
		}
		catch (Exception e) {
			throw new FormStructureXsdException();
		}

		FormStructure formStructure =
			formStructurePersistence.findByG_F(groupId, formStructureId);

		validate(groupId, name, xsd);

		formStructure.setModifiedDate(serviceContext.getModifiedDate(null));
		formStructure.setName(name);
		formStructure.setDescription(description);
		formStructure.setXsd(xsd);

		formStructurePersistence.update(formStructure, false);

		// Expando

		ExpandoBridge expandoBridge = formStructure.getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);

		return formStructure;
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
				elName.startsWith(FormStructureConstants.RESERVED)) {

				throw new FormStructureXsdException();
			}
			else {
				char[] c = elName.toCharArray();

				for (int i = 0; i < c.length; i++) {
					if (!Validator.isChar(c[i]) &&
						!Validator.isDigit(c[i]) &&
						c[i] != CharPool.DASH &&
						c[i] != CharPool.UNDERLINE) {

						throw new FormStructureXsdException();
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
					throw new FormDuplicateStructureElementException();
				}
				else {
					elNames.add(elNameLowerCase);
				}
			}

			if (Validator.isNull(elType)) {
				throw new FormStructureXsdException();
			}

			validate(element.elements(), elNames);
		}
	}

	protected void validate(
			long groupId, String formStructureId, boolean autoStructureId,
			String name, String xsd)
		throws PortalException, SystemException {

		if (!autoStructureId) {
			validateFormStructureId(formStructureId);

			FormStructure structure =
				formStructurePersistence.fetchByG_F(groupId, formStructureId);

			if (structure != null) {
				throw new FormDuplicateStructureIdException();
			}
		}

		validate(groupId, name, xsd);
	}

	protected void validate(
			long groupId, String name, String xsd)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new FormStructureNameException();
		}

		if (Validator.isNull(xsd)) {
			throw new FormStructureXsdException();
		}
		else {
			try {
				List<Element> elements = new ArrayList<Element>();

				Document document = SAXReaderUtil.read(xsd);

				Element rootElement = document.getRootElement();

				if (rootElement.elements().isEmpty()) {
					throw new FormStructureXsdException();
				}

				elements.addAll(rootElement.elements());

				Set<String> elNames = new HashSet<String>();

				validate(elements, elNames);
			}
			catch (FormDuplicateStructureElementException fdsee) {
				throw fdsee;
			}
			catch (FormStructureXsdException sxe) {
				throw sxe;
			}
			catch (Exception e) {
				throw new FormStructureXsdException();
			}
		}
	}

	protected void validateFormStructureId(String formStructureId)
		throws PortalException {

		if (Validator.isNull(formStructureId) ||
			Validator.isNumber(formStructureId) ||
			formStructureId.indexOf(CharPool.SPACE) != -1) {

			throw new FormStructureIdException();
		}
	}

}