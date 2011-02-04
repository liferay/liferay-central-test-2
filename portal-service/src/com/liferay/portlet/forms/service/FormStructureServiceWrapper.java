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
 * This class is a wrapper for {@link FormStructureService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormStructureService
 * @generated
 */
public class FormStructureServiceWrapper implements FormStructureService {
	public FormStructureServiceWrapper(
		FormStructureService formStructureService) {
		_formStructureService = formStructureService;
	}

	public com.liferay.portlet.forms.model.FormStructure addFormStructure(
		long groupId, java.lang.String formStructureId,
		boolean autoFormStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureService.addFormStructure(groupId, formStructureId,
			autoFormStructureId, name, description, xsd, serviceContext);
	}

	public void deleteFormStructure(long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureService.deleteFormStructure(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureService.fetchByG_F(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormStructure getFormStructure(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureService.getFormStructure(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureService.updateFormStructure(groupId,
			formStructureId, name, description, xsd, serviceContext);
	}

	public FormStructureService getWrappedFormStructureService() {
		return _formStructureService;
	}

	public void setWrappedFormStructureService(
		FormStructureService formStructureService) {
		_formStructureService = formStructureService;
	}

	private FormStructureService _formStructureService;
}