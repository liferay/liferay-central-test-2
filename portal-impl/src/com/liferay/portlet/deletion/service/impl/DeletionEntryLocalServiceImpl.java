/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.deletion.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.deletion.model.DeletionEntry;
import com.liferay.portlet.deletion.service.base.DeletionEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the deletion entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.deletion.service.DeletionEntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.deletion.service.base.DeletionEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.deletion.service.DeletionEntryLocalServiceUtil
 */
public class DeletionEntryLocalServiceImpl
	extends DeletionEntryLocalServiceBaseImpl {

	public DeletionEntry addEntry(
			long companyId, long groupId, String className, long classPK,
			String classUuid, long parentId)
		throws PortalException, SystemException{

		long classNameId = PortalUtil.getClassNameId(className);

		return addEntry(
			companyId, groupId, classNameId, classPK, classUuid, parentId);
	}

	public DeletionEntry addEntry(
			long companyId, long groupId, long classNameId, long classPK,
			String classUuid, long parentId)
		throws PortalException, SystemException{

		long entryId = counterLocalService.increment();

		Date now = new Date();

		DeletionEntry deletionEntry = deletionEntryPersistence.create(entryId);

		deletionEntry.setGroupId(groupId);
		deletionEntry.setCompanyId(companyId);
		deletionEntry.setCreateDate(now);
		deletionEntry.setClassNameId(classNameId);
		deletionEntry.setClassPK(classPK);
		deletionEntry.setClassUuid(classUuid);
		deletionEntry.setParentId(parentId);

		deletionEntryPersistence.update(deletionEntry, false);

		return deletionEntry;
	}

	public void deleteEntries(long groupId)
		throws PortalException, SystemException {

		for (DeletionEntry entry : deletionEntryPersistence.findByGroupId(
				groupId)) {

			deleteEntry(entry);
		}
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		DeletionEntry entry = deletionEntryPersistence.findByPrimaryKey(
			entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(DeletionEntry entry)
		throws PortalException, SystemException {

		// Entry

		deletionEntryPersistence.remove(entry);
	}

	public DeletionEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return deletionEntryPersistence.findByPrimaryKey(entryId);
	}

	public DeletionEntry getEntry(long classNameId, long classPK)
		throws PortalException, SystemException {

		return deletionEntryPersistence.findByC_C(classNameId, classPK);
	}

	public DeletionEntry getEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEntry(classNameId, classPK);
	}

	public List<DeletionEntry> getEntries(long groupId, long classNameId)
		throws PortalException, SystemException {

		return deletionEntryPersistence.findByG_C(groupId, classNameId);
	}

	public List<DeletionEntry> getEntries(long groupId, String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEntries(groupId, classNameId);
	}

	public List<DeletionEntry> getEntries(
			long groupId, Date createDate, long classNameId)
		throws PortalException, SystemException {

		if (createDate == null) {
			return getEntries(groupId, classNameId);
		}
		else {
			return deletionEntryPersistence.findByG_C_C(
				groupId, createDate, classNameId);
		}
	}

	public List<DeletionEntry> getEntries(
			long groupId, Date createDate, String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		if (createDate == null) {
			return getEntries(groupId, className);
		}
		else {
			return getEntries(groupId, createDate, classNameId);
		}
	}

	public List<DeletionEntry> getEntries(
			long groupId, long classNameId, long parentId)
		throws PortalException, SystemException {

		return deletionEntryPersistence.findByG_C_P(
			groupId, classNameId, parentId);
	}

	public List<DeletionEntry> getEntries(
			long groupId, String className, long parentId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getEntries(groupId, classNameId, parentId);
	}

	public List<DeletionEntry> getEntries(
			long groupId, Date createDate, long classNameId, long parentId)
		throws PortalException, SystemException {

		if (createDate == null) {
			return getEntries(groupId, classNameId, parentId);
		}
		else {
			return deletionEntryPersistence.findByG_C_C_P(
				groupId, createDate, classNameId, parentId);
		}
	}

	public List<DeletionEntry> getEntries(
			long groupId, Date createDate, String className, long parentId)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		if (createDate == null) {
			return getEntries(groupId, className, parentId);
		}
		else {
			return getEntries(groupId, createDate, classNameId, parentId);
		}
	}

}