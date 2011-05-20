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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLDocumentType;
import com.liferay.portlet.documentlibrary.service.base.DLDocumentTypeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 * @author Sergio González
 */
public class DLDocumentTypeLocalServiceImpl
	extends DLDocumentTypeLocalServiceBaseImpl {

	public DLDocumentType addDocumentType(
			long userId, long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		verify(ddmStructureIds);

		long documentTypeId = counterLocalService.increment();

		DLDocumentType documentType = dlDocumentTypePersistence.create(
			documentTypeId);

		documentType.setGroupId(groupId);
		documentType.setCompanyId(user.getCompanyId());
		documentType.setUserId(user.getUserId());
		documentType.setUserName(user.getFullName());
		documentType.setCreateDate(serviceContext.getCreateDate(now));
		documentType.setModifiedDate(serviceContext.getModifiedDate(now));
		documentType.setName(name);
		documentType.setDescription(description);

		dlDocumentTypePersistence.update(documentType, false);

		dlDocumentTypePersistence.addDDMStructures(
			documentTypeId, ddmStructureIds);

		if (serviceContext.getAddGroupPermissions() ||
			 serviceContext.getAddGuestPermissions()) {

			addDocumentTypeResources(
				documentType, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addDocumentTypeResources(
				documentType, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return documentType;
	}

	public void deleteDocumentType(long documentTypeId)
		throws PortalException, SystemException {

		dlDocumentTypePersistence.remove(documentTypeId);
	}

	public DLDocumentType getDocumentType(long documentTypeId)
		throws PortalException, SystemException {

		return dlDocumentTypePersistence.findByPrimaryKey(documentTypeId);
	}

	public List<DLDocumentType> getDocumentTypes(long groupId)
		throws SystemException {

		return dlDocumentTypePersistence.findByGroupId(groupId);
	}

	public List<DLDocumentType> getDocumentTypes(
			long groupId, int start, int end)
		throws SystemException {

		return dlDocumentTypePersistence.findByGroupId(groupId, start, end);
	}

	public List<DLDocumentType> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return dlDocumentTypeFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	public int searchCount(
			long companyId, long groupId, String keywords)
		throws SystemException {

		return dlDocumentTypeFinder.countByKeywords(
			companyId, groupId, keywords);
	}

	public void updateDocumentType(
			long documentTypeId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLDocumentType documentType =
			dlDocumentTypePersistence.findByPrimaryKey(documentTypeId);

		documentType.setModifiedDate(serviceContext.getModifiedDate(null));
		documentType.setName(name);
		documentType.setDescription(description);

		dlDocumentTypePersistence.update(documentType, false);
	}

	protected void addDocumentTypeResources(
			DLDocumentType documentType, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			documentType.getCompanyId(), documentType.getGroupId(),
			documentType.getUserId(), DLDocumentType.class.getName(),
			documentType.getDocumentTypeId(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	protected void addDocumentTypeResources(
			DLDocumentType documentType, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			documentType.getCompanyId(), documentType.getGroupId(),
			documentType.getUserId(), DLDocumentType.class.getName(),
			documentType.getDocumentTypeId(), communityPermissions,
			guestPermissions);
	}

	protected void verify(long[] ddmStructureIds)
		throws PortalException, SystemException {

		for (long ddmStructureId : ddmStructureIds) {
			ddmStructurePersistence.findByPrimaryKey(ddmStructureId);
		}
	}

}