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

package com.liferay.portlet.dynamicdatamapping.service;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureEntryLinkService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureEntryLinkService
 * @generated
 */
public class DDMStructureEntryLinkServiceWrapper
	implements DDMStructureEntryLinkService {
	public DDMStructureEntryLinkServiceWrapper(
		DDMStructureEntryLinkService ddmStructureEntryLinkService) {
		_ddmStructureEntryLinkService = ddmStructureEntryLinkService;
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink addStructureEntryLink(
		java.lang.String structureKey, java.lang.String className,
		long classPK, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkService.addStructureEntryLink(structureKey,
			className, classPK, serviceContext);
	}

	public void deleteStructureEntryLink(long groupId,
		java.lang.String structureKey, long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryLinkService.deleteStructureEntryLink(groupId,
			structureKey, structureEntryLinkId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getStructureEntryLink(
		long groupId, java.lang.String structureKey,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkService.getStructureEntryLink(groupId,
			structureKey, className, classPK);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateStructureEntryLink(
		long structureEntryLinkId, java.lang.String structureKey, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkService.updateStructureEntryLink(structureEntryLinkId,
			structureKey, groupId, className, classPK);
	}

	public DDMStructureEntryLinkService getWrappedDDMStructureEntryLinkService() {
		return _ddmStructureEntryLinkService;
	}

	public void setWrappedDDMStructureEntryLinkService(
		DDMStructureEntryLinkService ddmStructureEntryLinkService) {
		_ddmStructureEntryLinkService = ddmStructureEntryLinkService;
	}

	private DDMStructureEntryLinkService _ddmStructureEntryLinkService;
}