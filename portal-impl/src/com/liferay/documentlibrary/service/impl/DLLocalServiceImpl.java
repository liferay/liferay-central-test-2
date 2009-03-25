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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Date;

/**
 * <a href="DLLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLLocalServiceImpl implements DLLocalService {

	public void addFile(
			long companyId, String portletId, long groupId, long repositoryId,
			String fileName, long fileEntryId, String properties,
			Date modifiedDate, String[] tagsCategories, String[] tagsEntries,
			InputStream is)
		throws PortalException, SystemException {

		validate(fileName, is);

		Hook hook = HookFactory.getInstance();

		hook.addFile(
			companyId, portletId, groupId, repositoryId, fileName, fileEntryId,
			properties, modifiedDate, tagsCategories, tagsEntries, is);
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
				contextQuery.addRequiredTerm(Field.GROUP_ID, groupId);
			}

			if ((repositoryIds != null) && (repositoryIds.length > 0)) {
				BooleanQuery repositoryIdsQuery =
					BooleanQueryFactoryUtil.create();

				for (long repositoryId : repositoryIds) {
					try {
						if (userId > 0) {
							try {
								dlFolderService.getFolder(repositoryId);
							}
							catch (Exception e) {
								continue;
							}
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
				searchQuery.addTerm(Field.TAGS_ENTRIES, keywords);
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
			String[] tagsCategories, String[] tagsEntries, InputStream is)
		throws PortalException, SystemException {

		validate(fileName, sourceFileName, is);

		Hook hook = HookFactory.getInstance();

		hook.updateFile(
			companyId, portletId, groupId, repositoryId, fileName,
			versionNumber, sourceFileName, fileEntryId, properties,
			modifiedDate, tagsCategories, tagsEntries, is);
	}

	public void validate(String fileName, File file)
		throws PortalException, SystemException {
		validate(fileName);

		if (((PropsValues.WEBDAV_LITMUS) ||
			 (PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0)) &&
			((file == null) ||
			 (file.length() >
				 PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

			throw new FileSizeException(fileName);
		}
	}

	public void validate(String fileName, byte[] bytes)
		throws PortalException, SystemException {
		validate(fileName);

		if (((PropsValues.WEBDAV_LITMUS) ||
			(PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE) > 0)) &&
			((bytes == null) ||
			(bytes.length >
				 PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE)))) {

			throw new FileSizeException(fileName);
		}
	}

	public void validate(String fileName, InputStream is)
		throws PortalException, SystemException {

		validate(fileName);

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

	public void validate(String fileName)
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

		boolean validFileExtension = false;

		String[] fileExtensions = PrefsPropsUtil.getStringArray(
			PropsKeys.DL_FILE_EXTENSIONS, ",");

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

	public void validate(String fileName, String sourceFileName, InputStream is)
		throws PortalException {

		String fileNameExtension = FileUtil.getExtension(fileName);
		String sourceFileNameExtension = FileUtil.getExtension(sourceFileName);

		if (!PropsValues.WEBDAV_LITMUS) {
			if (Validator.isNull(fileNameExtension) ||
				!fileNameExtension.equalsIgnoreCase(sourceFileNameExtension)) {

				throw new SourceFileNameException(sourceFileName);
			}
		}

		if (is == null) {
			throw new FileSizeException(fileName);
		}
	}

	@BeanReference(name = _DL_FOLDER_SERVICE)
	protected DLFolderService dlFolderService;

	private static final String _DL_FOLDER_SERVICE =
		"com.liferay.portlet.documentlibrary.service.DLFolderService.impl";

}