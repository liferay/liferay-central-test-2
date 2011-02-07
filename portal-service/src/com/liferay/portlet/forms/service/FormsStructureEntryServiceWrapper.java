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

package com.liferay.portlet.forms.service;

/**
 * <p>
 * This class is a wrapper for {@link FormsStructureEntryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormsStructureEntryService
 * @generated
 */
public class FormsStructureEntryServiceWrapper
	implements FormsStructureEntryService {
	public FormsStructureEntryServiceWrapper(
		FormsStructureEntryService formsStructureEntryService) {
		_formsStructureEntryService = formsStructureEntryService;
	}

	public com.liferay.portlet.forms.model.FormsStructureEntry addEntry(
		long groupId, java.lang.String formStrucureId,
		boolean autoFormStrucureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formsStructureEntryService.addEntry(groupId, formStrucureId,
			autoFormStrucureId, name, description, xsd, serviceContext);
	}

	public void deleteEntry(long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formsStructureEntryService.deleteEntry(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormsStructureEntry fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formsStructureEntryService.fetchByG_F(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormsStructureEntry getEntry(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formsStructureEntryService.getEntry(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormsStructureEntry updateEntry(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formsStructureEntryService.updateEntry(groupId,
			formStructureId, name, description, xsd, serviceContext);
	}

	public FormsStructureEntryService getWrappedFormsStructureEntryService() {
		return _formsStructureEntryService;
	}

	public void setWrappedFormsStructureEntryService(
		FormsStructureEntryService formsStructureEntryService) {
		_formsStructureEntryService = formsStructureEntryService;
	}

	private FormsStructureEntryService _formsStructureEntryService;
}