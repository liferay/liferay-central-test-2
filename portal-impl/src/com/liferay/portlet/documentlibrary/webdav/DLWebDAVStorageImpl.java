/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.portal.DuplicateLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.webdav.LockException;
import com.liferay.portal.webdav.Resource;
import com.liferay.portal.webdav.Status;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="DLWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class DLWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public int copyCollectionResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			try {
				parentFolderId = getParentFolderId(destinationArray);
			}
			catch (NoSuchFolderException nsfe) {
				return HttpServletResponse.SC_CONFLICT;
			}

			DLFolder folder = (DLFolder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destination);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = folder.getDescription();

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(
				isAddCommunityPermissions(groupId));
			serviceContext.setAddGuestPermissions(true);

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(
						groupId, parentFolderId, name,
						webDavRequest.getLockUuid())) {

					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			if (depth == 0) {
				DLFolderServiceUtil.addFolder(
					groupId, parentFolderId, name, description, serviceContext);
			}
			else {
				DLFolderServiceUtil.copyFolder(
					groupId, folder.getFolderId(), parentFolderId, name,
					description, serviceContext);
			}

			return status;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int copySimpleResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		File file = null;

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

			try {
				parentFolderId = getParentFolderId(destinationArray);
			}
			catch (NoSuchFolderException nsfe) {
				return HttpServletResponse.SC_CONFLICT;
			}

			DLFileEntry fileEntry = (DLFileEntry)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destination);
			long userId = webDavRequest.getUserId();
			String name = WebDAVUtil.getResourceName(destinationArray);
			String title = WebDAVUtil.getResourceName(destinationArray);
			String description = fileEntry.getDescription();
			String versionDescription = StringPool.BLANK;
			String extraSettings = fileEntry.getExtraSettings();

			file = FileUtil.createTempFile(
				FileUtil.getExtension(fileEntry.getName()));

			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
				fileEntry.getCompanyId(), userId, fileEntry.getGroupId(),
				fileEntry.getFolderId(), fileEntry.getName());

			FileUtil.write(file, is);

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(
				isAddCommunityPermissions(groupId));
			serviceContext.setAddGuestPermissions(true);

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(
						groupId, parentFolderId, title,
						webDavRequest.getLockUuid())) {

					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			DLFileEntryServiceUtil.addFileEntry(
				groupId, parentFolderId, name, title, description,
				versionDescription, extraSettings, file, serviceContext);

			return status;
		}
		catch (DuplicateFileException dfe) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (LockException le) {
			return WebDAVUtil.SC_LOCKED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	public int deleteResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDavRequest);

			if (resource == null) {
				return HttpServletResponse.SC_NOT_FOUND;
			}

			Object model = resource.getModel();

			if (model instanceof DLFolder) {
				DLFolder folder = (DLFolder)model;

				DLFolderServiceUtil.deleteFolder(folder.getFolderId());
			}
			else {
				DLFileEntry fileEntry = (DLFileEntry)model;

				if (isLocked(fileEntry, webDavRequest.getLockUuid())) {
					return WebDAVUtil.SC_LOCKED;
				}

				DLFileEntryServiceUtil.deleteFileEntry(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName());
			}

			return HttpServletResponse.SC_NO_CONTENT;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public Resource getResource(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDavRequest.getPathArray();

			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);

			if (Validator.isNull(name)) {
				String path = getRootPath() + webDavRequest.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, getToken());
			}

			try {
				DLFolder folder = DLFolderServiceUtil.getFolder(
					webDavRequest.getGroupId(), parentFolderId, name);

				if ((folder.getParentFolderId() != parentFolderId) ||
					(webDavRequest.getGroupId() != folder.getGroupId())) {

					throw new NoSuchFolderException();
				}

				return toResource(webDavRequest, folder, false);
			}
			catch (NoSuchFolderException nsfe) {
				try {
					String titleWithExtension = name;

					DLFileEntry fileEntry =
						DLFileEntryServiceUtil.getFileEntryByTitle(
							webDavRequest.getGroupId(), parentFolderId,
							titleWithExtension);

					return toResource(webDavRequest, fileEntry, false);
				}
				catch (NoSuchFileEntryException nsfee) {
					return null;
				}
			}
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public List<Resource> getResources(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			long folderId = getFolderId(webDavRequest.getPathArray());

			List<Resource> folders = getFolders(webDavRequest, folderId);
			List<Resource> fileEntries = getFileEntries(
				webDavRequest, folderId);

			List<Resource> resources = new ArrayList<Resource>(
				folders.size() + fileEntries.size());

			resources.addAll(folders);
			resources.addAll(fileEntries);

			return resources;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public boolean isSupportsClassTwo() {
		return true;
	}

	public Status lockResource(
			WebDAVRequest webDavRequest, String owner, long timeout)
		throws WebDAVException {

		Resource resource = getResource(webDavRequest);

		Lock lock = null;
		int status = HttpServletResponse.SC_OK;

		try {
			if (resource == null) {
				status = HttpServletResponse.SC_CREATED;

				String[] pathArray = webDavRequest.getPathArray();

				long groupId = webDavRequest.getGroupId();
				long parentFolderId = getParentFolderId(pathArray);
				String name = WebDAVUtil.getResourceName(pathArray);

				String title = name;
				String description = StringPool.BLANK;
				String versionDescription = StringPool.BLANK;
				String extraSettings = StringPool.BLANK;

				File file = FileUtil.createTempFile(
					FileUtil.getExtension(name));

				file.createNewFile();

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setAddCommunityPermissions(
					isAddCommunityPermissions(groupId));
				serviceContext.setAddGuestPermissions(true);

				DLFileEntry fileEntry = DLFileEntryServiceUtil.addFileEntry(
					groupId, parentFolderId, name, title, description,
					versionDescription, extraSettings, file, serviceContext);

				resource = toResource(webDavRequest, fileEntry, false);
			}

			if (resource instanceof DLFileEntryResourceImpl) {
				DLFileEntry fileEntry = (DLFileEntry)resource.getModel();

				lock = DLFileEntryServiceUtil.lockFileEntry(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName(), owner, timeout);
			}
			else {
				boolean inheritable = false;

				long depth = WebDAVUtil.getDepth(
					webDavRequest.getHttpServletRequest());

				if (depth != 0) {
					inheritable = true;
				}

				DLFolder folder = (DLFolder)resource.getModel();

				lock = DLFolderServiceUtil.lockFolder(
					folder.getFolderId(), owner, inheritable, timeout);
			}
		}
		catch (Exception e) {

			// DuplicateLock is 423 not 501

			if (!(e instanceof DuplicateLockException)) {
				throw new WebDAVException(e);
			}

			status = WebDAVUtil.SC_LOCKED;
		}

		return new Status(lock, status);
	}

	public Status makeCollection(WebDAVRequest webDavRequest)
		throws WebDAVException {

		try {
			HttpServletRequest request = webDavRequest.getHttpServletRequest();

			if (request.getContentLength() > 0) {
				return new Status(
					HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			}

			String[] pathArray = webDavRequest.getPathArray();

			long groupId = webDavRequest.getGroupId();
			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(
				isAddCommunityPermissions(groupId));
			serviceContext.setAddGuestPermissions(true);

			DLFolderServiceUtil.addFolder(
				groupId, parentFolderId, name, description, serviceContext);

			String location = StringUtil.merge(pathArray, StringPool.SLASH);

			return new Status(location, HttpServletResponse.SC_CREATED);
		}
		catch (DuplicateFolderNameException dfne) {
			return new Status(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		catch (DuplicateFileException dfe) {
			return new Status(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		catch (NoSuchFolderException nsfe) {
			return new Status(HttpServletResponse.SC_CONFLICT);
		}
		catch (PrincipalException pe) {
			return new Status(HttpServletResponse.SC_FORBIDDEN);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int moveCollectionResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			DLFolder folder = (DLFolder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destinationArray);
			long folderId = folder.getFolderId();
			long parentFolderId = getParentFolderId(destinationArray);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = folder.getDescription();

			ServiceContext serviceContext = new ServiceContext();

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(
						groupId, parentFolderId, name,
						webDavRequest.getLockUuid())) {

					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			DLFolderServiceUtil.updateFolder(
				folderId, parentFolderId, name, description, serviceContext);

			return status;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int moveSimpleResource(
			WebDAVRequest webDavRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			DLFileEntry fileEntry = (DLFileEntry)resource.getModel();

			if (isLocked(fileEntry, webDavRequest.getLockUuid())) {
				return WebDAVUtil.SC_LOCKED;
			}

			long groupId = WebDAVUtil.getGroupId(destinationArray);
			long userId = webDavRequest.getUserId();
			long parentFolderId = getParentFolderId(destinationArray);
			String name = fileEntry.getName();
			String sourceFileName = null;
			String title = WebDAVUtil.getResourceName(destinationArray);
			String description = fileEntry.getDescription();
			String versionDescription = StringPool.BLANK;
			String extraSettings = fileEntry.getExtraSettings();
			byte[] bytes = null;

			String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
				DLFileEntry.class.getName(), fileEntry.getFileEntryId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAssetTagNames(assetTagNames);

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(
						groupId, parentFolderId, title,
						webDavRequest.getLockUuid())) {

					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			// LPS-5415

			if (webDavRequest.isMac()) {
				try {
					DLFileEntry destFileEntry =
						DLFileEntryServiceUtil.getFileEntryByTitle(
							groupId, parentFolderId, title);

					InputStream is =
						DLFileEntryLocalServiceUtil.getFileAsStream(
							fileEntry.getCompanyId(), userId,
							fileEntry.getGroupId(), fileEntry.getFolderId(),
							fileEntry.getName());

					bytes = FileUtil.getBytes(is);

					DLFileEntryServiceUtil.updateFileEntry(
						groupId, parentFolderId, parentFolderId,
						destFileEntry.getName(), destFileEntry.getTitle(),
						destFileEntry.getTitle(),
						destFileEntry.getDescription(), versionDescription,
						destFileEntry.getExtraSettings(), bytes,
						serviceContext);

					DLFileEntryServiceUtil.deleteFileEntry(
						fileEntry.getGroupId(), fileEntry.getFolderId(),
						fileEntry.getName());

					return status;
				}
				catch (NoSuchFileEntryException nsfee) {
				}
			}

			DLFileEntryServiceUtil.updateFileEntry(
				fileEntry.getGroupId(), fileEntry.getFolderId(), parentFolderId,
				name, sourceFileName, title, description, versionDescription,
				extraSettings, bytes, serviceContext);

			return status;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (DuplicateFileException dfe) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (LockException le) {
			return WebDAVUtil.SC_LOCKED;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int putResource(WebDAVRequest webDavRequest) throws WebDAVException {
		File file = null;

		try {
			HttpServletRequest request = webDavRequest.getHttpServletRequest();

			String[] pathArray = webDavRequest.getPathArray();

			long groupId = webDavRequest.getGroupId();
			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);
			String title = name;
			String description = StringPool.BLANK;
			String versionDescription = StringPool.BLANK;
			String extraSettings = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddCommunityPermissions(
				isAddCommunityPermissions(groupId));
			serviceContext.setAddGuestPermissions(true);

			try {
				DLFileEntry entry = DLFileEntryServiceUtil.getFileEntryByTitle(
					groupId, parentFolderId, name);

				if (isLocked(entry, webDavRequest.getLockUuid())) {
					return WebDAVUtil.SC_LOCKED;
				}

				name = entry.getName();
				description = entry.getDescription();
				extraSettings = entry.getExtraSettings();

				String[] assetTagNames = AssetTagLocalServiceUtil.getTagNames(
					DLFileEntry.class.getName(), entry.getFileEntryId());

				serviceContext.setAssetTagNames(assetTagNames);

				file = FileUtil.createTempFile(FileUtil.getExtension(name));

				FileUtil.write(file, request.getInputStream());

				DLFileEntryServiceUtil.updateFileEntry(
					groupId, parentFolderId, parentFolderId, name, title, title,
					description, versionDescription, extraSettings, file,
					serviceContext);
			}
			catch (NoSuchFileEntryException nsfee) {
				file = FileUtil.createTempFile(FileUtil.getExtension(name));

				FileUtil.write(file, request.getInputStream());

				DLFileEntryServiceUtil.addFileEntry(
					groupId, parentFolderId, name, title, description,
					versionDescription, extraSettings, file, serviceContext);
			}

			return HttpServletResponse.SC_CREATED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (NoSuchFolderException nsfe) {
			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
		finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	public Lock refreshResourceLock(
			WebDAVRequest webDavRequest, String uuid, long timeout)
		throws WebDAVException {

		Resource resource = getResource(webDavRequest);

		Lock lock = null;

		try {
			if (resource instanceof DLFileEntryResourceImpl) {
				lock = DLFileEntryServiceUtil.refreshFileEntryLock(
					uuid, timeout);
			}
			else {
				lock = DLFolderServiceUtil.refreshFolderLock(uuid, timeout);
			}
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}

		return lock;
	}

	public boolean unlockResource(WebDAVRequest webDavRequest, String token)
		throws WebDAVException {

		Resource resource = getResource(webDavRequest);

		try {
			if (resource instanceof DLFileEntryResourceImpl) {
				DLFileEntry fileEntry = (DLFileEntry)resource.getModel();

				DLFileEntryServiceUtil.unlockFileEntry(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getName(), token);
			}
			else {
				DLFolder folder = (DLFolder)resource.getModel();

				DLFolderServiceUtil.unlockFolder(
					folder.getGroupId(), folder.getParentFolderId(),
					folder.getName(), token);
			}

			return true;
		}
		catch (Exception e) {
			if (e instanceof InvalidLockException) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to unlock file entry", e);
				}
			}
		}

		return false;
	}

	protected boolean deleteResource(
			long groupId, long parentFolderId, String name, String lockUuid)
		throws Exception {

		try {
			DLFolder folder = DLFolderServiceUtil.getFolder(
				groupId, parentFolderId, name);

			DLFolderServiceUtil.deleteFolder(folder.getFolderId());

			return true;
		}
		catch (NoSuchFolderException nsfe) {
			try {
				DLFileEntry fileEntry =
					DLFileEntryServiceUtil.getFileEntryByTitle(
						groupId, parentFolderId, name);

				if (isLocked(fileEntry, lockUuid)) {
					throw new LockException();
				}

				DLFileEntryServiceUtil.deleteFileEntryByTitle(
					groupId, parentFolderId, name);

				return true;
			}
			catch (NoSuchFileEntryException nsfee) {
			}
		}

		return false;
	}

	protected List<Resource> getFileEntries(
			WebDAVRequest webDavRequest, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DLFileEntry> fileEntries = DLFileEntryServiceUtil.getFileEntries(
			webDavRequest.getGroupId(), parentFolderId);

		for (DLFileEntry fileEntry : fileEntries) {
			Resource resource = toResource(webDavRequest, fileEntry, true);

			resources.add(resource);
		}

		return resources;
	}

	protected long getFolderId(String[] pathArray) throws Exception {
		return getFolderId(pathArray, false);
	}

	protected long getFolderId(String[] pathArray, boolean parent)
		throws Exception {

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (pathArray.length <= 2) {
			return folderId;
		}
		else {
			long groupId = WebDAVUtil.getGroupId(pathArray);

			int x = pathArray.length;

			if (parent) {
				x--;
			}

			for (int i = 3; i < x; i++) {
				String name = pathArray[i];

				DLFolder folder = DLFolderServiceUtil.getFolder(
					groupId, folderId, name);

				if (groupId == folder.getGroupId()) {
					folderId = folder.getFolderId();
				}
			}
		}

		return folderId;
	}

	protected List<Resource> getFolders(
			WebDAVRequest webDavRequest, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		long groupId = webDavRequest.getGroupId();

		List<DLFolder> folders = DLFolderServiceUtil.getFolders(
			groupId, parentFolderId);

		for (DLFolder folder : folders) {
			Resource resource = toResource(webDavRequest, folder, true);

			resources.add(resource);
		}

		return resources;
	}

	protected long getParentFolderId(String[] pathArray) throws Exception {
		return getFolderId(pathArray, true);
	}

	protected boolean isLocked(DLFileEntry fileEntry, String lockUuid)
		throws Exception {

		long groupId = fileEntry.getGroupId();
		long parentFolderId = fileEntry.getFolderId();
		String fileName = fileEntry.getName();

		if (Validator.isNull(lockUuid)) {

			// Client does not claim to know of a lock

			return DLFileEntryServiceUtil.hasFileEntryLock(
				groupId, parentFolderId, fileName);
		}
		else {

			// Client claims to know of a lock. Verify the lock UUID.

			try {
				boolean verified = DLFileEntryServiceUtil.verifyFileEntryLock(
					groupId, parentFolderId, fileName, lockUuid);

				return !verified;
			}
			catch (NoSuchLockException nsle) {
				return false;
			}
		}
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, DLFileEntry fileEntry,
		boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();
		String name = StringPool.BLANK;

		if (appendPath) {
			name = fileEntry.getTitle();
		}

		return new DLFileEntryResourceImpl(
			webDavRequest, fileEntry, parentPath, name);
	}

	protected Resource toResource(
		WebDAVRequest webDavRequest, DLFolder folder, boolean appendPath) {

		String parentPath = getRootPath() + webDavRequest.getPath();
		String name = StringPool.BLANK;

		if (appendPath) {
			name = folder.getName();
		}

		Resource resource = new BaseResourceImpl(
			parentPath, name, folder.getName(), folder.getCreateDate(),
			folder.getModifiedDate());

		resource.setModel(folder);
		resource.setClassName(DLFolder.class.getName());
		resource.setPrimaryKey(folder.getPrimaryKey());

		return resource;
	}

	private static Log _log = LogFactoryUtil.getLog(DLWebDAVStorageImpl.class);

}