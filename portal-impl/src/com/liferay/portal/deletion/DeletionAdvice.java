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

package com.liferay.portal.deletion;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.deletion.service.DeletionEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import jodd.bean.BeanUtil;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * <a href="DeletionAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class DeletionAdvice {

	public Object clearGroupDeletions(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object[] arguments = proceedingJoinPoint.getArgs();

		long groupId = (Long)arguments[0];

		Object returnValue = proceedingJoinPoint.proceed();

		if (!inStaging(groupId)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not staged: ".concat(Group.class.getName()).concat(
						String.valueOf(groupId)));
			}

			return returnValue;
		}

		DeletionEntryLocalServiceUtil.deleteEntries(groupId);

		if (_log.isDebugEnabled()) {
			_log.debug("Cleared tracked group deletions: " + groupId);
		}

		return returnValue;
	}

	public Object trackDeletion(
			ProceedingJoinPoint proceedingJoinPoint, BaseModel model)
		throws Throwable {

		Object returnValue = proceedingJoinPoint.proceed();

		if (!BeanUtil.hasProperty(model, "groupId") ||
			!BeanUtil.hasProperty(model, "companyId")) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Deletion tracking excused for: ".concat(
						model.getClass().getName()).concat(model.toString()));
			}

			return returnValue;
		}

		long groupId = BeanPropertiesUtil.getLong(model, "groupId");

		if (!inStaging(groupId)) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not in staging: ".concat(
						model.getClass().getName()).concat(model.toString()));
			}

			return returnValue;
		}

		long companyId = BeanPropertiesUtil.getLong(model, "companyId");

		String className = StringUtil.replace(
			model.getClass().getName(),
			new String[] {".impl", "Impl"},
			new String[] {StringPool.BLANK, StringPool.BLANK}
		);

		long classPK = BeanPropertiesUtil.getLong(model, "primaryKey");
		String uuid = StringPool.BLANK;

		if (BeanUtil.hasProperty(model, "uuid")) {
			uuid = BeanPropertiesUtil.getString(model, "uuid");
		}

		long parentId = 0;

		if (BeanUtil.hasProperty(model, "parentFolderId")) {
			parentId = BeanPropertiesUtil.getLong(model, "parentFolderId");
		}
		else if (BeanUtil.hasProperty(model, "folderId")) {
			parentId = BeanPropertiesUtil.getLong(model, "folderId");
		}
		else if (BeanUtil.hasProperty(model, "parentCategoryId")) {
			parentId = BeanPropertiesUtil.getLong(model, "parentCategoryId");
		}
		else if (BeanUtil.hasProperty(model, "nodeId")) {
			parentId = BeanPropertiesUtil.getLong(model, "nodeId");
		}

		DeletionEntryLocalServiceUtil.addEntry(
			companyId, groupId, className, classPK, uuid, parentId);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Deleted: ".concat(className).concat(model.toString()));
		}

		return returnValue;
	}

	public Object trackDLFileEntryDeletion(
			ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object[] arguments = proceedingJoinPoint.getArgs();

		long fileEntryId = (Long)arguments[0];

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);

		Object returnValue = proceedingJoinPoint.proceed();

		if (!inStaging(fileEntry.getRepositoryId())) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not staged: ".concat(FileEntry.class.getName()).concat(
						fileEntry.toString()));
			}

			return returnValue;
		}

		DeletionEntryLocalServiceUtil.addEntry(
			fileEntry.getCompanyId(), fileEntry.getRepositoryId(),
			FileEntry.class.getName(), fileEntry.getFileEntryId(),
			fileEntry.getUuid(), fileEntry.getFolderId());

		if (_log.isDebugEnabled()) {
			_log.debug("Deleted: " + fileEntry);
		}

		return returnValue;
	}

	public Object trackDLFolderDeletion(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		Object[] arguments = proceedingJoinPoint.getArgs();

		long folderId = (Long)arguments[0];

		Folder folder = DLAppLocalServiceUtil.getFolder(folderId);

		Object returnValue = proceedingJoinPoint.proceed();

		if (!inStaging(folder.getRepositoryId())) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not staged: ".concat(Folder.class.getName()).concat(
						folder.toString()));
			}

			return returnValue;
		}

		DeletionEntryLocalServiceUtil.addEntry(
			folder.getCompanyId(), folder.getRepositoryId(),
			Folder.class.getName(), folder.getFolderId(), folder.getUuid(),
			folder.getParentFolderId());

		if (_log.isDebugEnabled()) {
			_log.debug("Deleted: " + folder);
		}

		return returnValue;
	}

	protected boolean inStaging(long groupId)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		if (group.isStagingGroup() || group.isStagedRemotely()) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(DeletionAdvice.class);

}