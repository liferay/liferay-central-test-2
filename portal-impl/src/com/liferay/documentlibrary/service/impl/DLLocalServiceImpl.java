/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.documentlibrary.service.impl;

import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.SourceFileNameException;
import com.liferay.documentlibrary.service.DLLocalService;
import com.liferay.documentlibrary.util.Hook;
import com.liferay.documentlibrary.util.HookFactory;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;

/**
 * <a href="DLLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class DLLocalServiceImpl implements DLLocalService {

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		addFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, serviceContext, is, true);
	}

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, ServiceContext serviceContext, InputStream is,
			boolean checkExtension)
		throws PortalException, SystemException {

		validate(fileName, is, checkExtension);

		Hook hook = HookFactory.getInstance();

		hook.addFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, serviceContext, is);
	}

	public void checkRoot(long companyId) throws SystemException {
		Hook hook = HookFactory.getInstance();

		hook.checkRoot(companyId);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName)
		throws PortalException, SystemException {

		Hook hook = HookFactory.getInstance();

		return hook.getFileAsStream(companyId, repositoryId, fileName);
	}

	public InputStream getFileAsStream(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		Hook hook = HookFactory.getInstance();

		return hook.getFileAsStream(
			companyId, repositoryId, fileName, versionNumber);
	}

	public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		Hook hook = HookFactory.getInstance();

		return hook.hasFile(companyId, repositoryId, fileName, versionNumber);
	}

	public void move(String srcDir, String destDir) throws SystemException {
		Hook hook = HookFactory.getInstance();

		hook.move(srcDir, destDir);
	}

	public Hits search(
			long companyId, String portletId, long groupId,
			long userId, long[] repositoryIds, String keywords, int start,
			int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = BooleanQueryFactoryUtil.create();

			contextQuery.addRequiredTerm(Field.PORTLET_ID, portletId);

			if (groupId > 0) {
				Group group = groupLocalService.getGroup(groupId);

				if (group.isLayout()) {
					contextQuery.addRequiredTerm(Field.SCOPE_GROUP_ID, groupId);

					groupId = group.getParentGroupId();
				}

				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((repositoryIds != null) && (repositoryIds.length > 0)) {
				BooleanQuery repositoryIdsQuery =
					BooleanQueryFactoryUtil.create();

				for (long repositoryId : repositoryIds) {
					try {
						if (userId > 0) {
							PermissionChecker permissionChecker =
								PermissionThreadLocal.getPermissionChecker();

							DLFolderPermission.check(
								permissionChecker, groupId, repositoryId,
								ActionKeys.VIEW);
						}

						if (repositoryId ==
								DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

							repositoryId = groupId;
						}

						TermQuery termQuery = TermQueryFactoryUtil.create(
							"repositoryId", repositoryId);

						repositoryIdsQuery.add(
							termQuery, BooleanClauseOccur.SHOULD);
					}
					catch (Exception e) {
					}
				}

				contextQuery.add(repositoryIdsQuery, BooleanClauseOccur.MUST);
			}

			BooleanQuery searchQuery = BooleanQueryFactoryUtil.create();

			if (Validator.isNotNull(keywords)) {
				searchQuery.addTerm(Field.CONTENT, keywords);
				searchQuery.addTerm(Field.PROPERTIES, keywords);
				searchQuery.addTerm(Field.ASSET_TAG_NAMES, keywords, true);
			}

			BooleanQuery fullQuery = BooleanQueryFactoryUtil.create();

			fullQuery.add(contextQuery, BooleanClauseOccur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClauseOccur.MUST);
			}

			return SearchEngineUtil.search(
				companyId, groupId, userId, DLFileEntry.class.getName(),
				fullQuery, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is)
		throws PortalException, SystemException {

		updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, is, true);
	}

	public void updateFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, double versionNumber, String sourceFileName,
			long fileEntryId, String properties, Date modifiedDate,
			ServiceContext serviceContext, InputStream is,
			boolean checkExtension)
		throws PortalException, SystemException {

		if (checkExtension) {
			validate(fileName, sourceFileName, is);
		}

		Hook hook = HookFactory.getInstance();

		hook.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, serviceContext, is);
	}

	public void validate(String fileName)
		throws PortalException, SystemException {

		validate(fileName, true);
	}

	public void validate(String fileName, byte[] bytes)
		throws PortalException, SystemException {

		validate(fileName, bytes, true);
	}

	public void validate(String fileName, File file)
		throws PortalException, SystemException {

		validate(fileName, file, true);
	}

	public void validate(String fileName, InputStream is)
		throws PortalException, SystemException {

		validate(fileName, is, true);
	}

	public void validate(String fileName, String sourceFileName, InputStream is)
		throws PortalException {

		String fileNameExtension = FileUtil.getExtension(fileName);
		String sourceFileNameExtension = FileUtil.getExtension(sourceFileName);

		if (!PropsValues.WEBDAV_LITMUS) {
			if (!fileNameExtension.equalsIgnoreCase(sourceFileNameExtension)) {
				throw new SourceFileNameException(sourceFileName);
			}
		}

		if (is == null) {
			throw new FileSizeException(fileName);
		}
	}

	protected void validate(String fileName, boolean checkExtension)
		throws PortalException, SystemException {

		if ((fileName.indexOf("\\\\") != -1) ||
			(fileName.indexOf("//") != -1) ||
			(fileName.indexOf(":") != -1) ||
			(fileName.indexOf("*") != -1) ||
			(fileName.indexOf("?") != -1) ||
			(fileName.indexOf("\"") != -1) ||
			(fileName.indexOf("<") != -1) ||
			(fileName.indexOf(">") != -1) ||
			(fileName.indexOf("|") != -1) ||
			(fileName.indexOf("&") != -1) ||
			(fileName.indexOf("[") != -1) ||
			(fileName.indexOf("]") != -1) ||
			(fileName.indexOf("'") != -1)) {

			throw new FileNameException(fileName);
		}

		if (checkExtension) {
			validateExtension(fileName);
		}
	}

	protected void validate(
			String fileName, byte[] bytes, boolean checkExtension)
		throws PortalException, SystemException {

		validate(fileName, checkExtension);

		if (((PropsValues.WEBDAV_LITMUS) ||
			(PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0)) &&
			((bytes == null) ||
			(bytes.length >
				 PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

			throw new FileSizeException(fileName);
		}
	}

	protected void validate(String fileName, File file, boolean checkExtension)
		throws PortalException, SystemException {

		validate(fileName, true);

		if (((PropsValues.WEBDAV_LITMUS) ||
			 (PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0)) &&
			((file == null) ||
			 (file.length() >
				PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

			throw new FileSizeException(fileName);
		}
	}

	protected void validate(
			String fileName, InputStream is, boolean checkExtension)
		throws PortalException, SystemException {

		validate(fileName, checkExtension);

		// LEP-4851

		try {
			if (((PropsValues.WEBDAV_LITMUS) ||
				(PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0)) &&
				((is == null) ||
				(is.available() >
					 PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

				throw new FileSizeException(fileName);
			}
		}
		catch (IOException ioe) {
			throw new FileSizeException(ioe.getMessage());
		}
	}

	protected void validateExtension(String fileName)
		throws PortalException, SystemException {

		boolean validFileExtension = false;

		String[] fileExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA);

		if (!PropsValues.WEBDAV_LITMUS) {
			for (int i = 0; i < fileExtensions.length; i++) {
				if (StringPool.STAR.equals(fileExtensions[i]) ||
					StringUtil.endsWith(fileName, fileExtensions[i])) {

					validFileExtension = true;

					break;
				}
			}

			if (!validFileExtension) {
				throw new FileNameException(fileName);
			}
		}
	}

	@BeanReference(name = _GROUP_LOCAL_SERVICE)
	protected GroupLocalService groupLocalService;

	@BeanReference(name = _DL_FOLDER_SERVICE)
	protected DLFolderService dlFolderService;

	private static final String _GROUP_LOCAL_SERVICE =
		"com.liferay.portal.service.GroupLocalService.impl";

	private static final String _DL_FOLDER_SERVICE =
		"com.liferay.portlet.documentlibrary.service.DLFolderService.impl";

}