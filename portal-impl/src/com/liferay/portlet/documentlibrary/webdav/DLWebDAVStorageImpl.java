/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.webdav.Resource;
import com.liferay.portal.webdav.Status;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.util.FileUtil;

import java.io.File;
import java.io.InputStream;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class DLWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public int copyCollectionResource(
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite, long depth)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;

			try {
				parentFolderId = getParentFolderId(destinationArray);
			}
			catch (NoSuchFolderException nsfe) {
				return HttpServletResponse.SC_CONFLICT;
			}

			DLFolder folder = (DLFolder)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destination);
			long plid = getPlid(groupId);
			String name = WebDAVUtil.getResourceName(destinationArray);
			String description = folder.getDescription();
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, name)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			if (depth == 0) {
				DLFolderServiceUtil.addFolder(
					plid, parentFolderId, name, description,
					addCommunityPermissions, addGuestPermissions);
			}
			else {
				DLFolderServiceUtil.copyFolder(
					plid, folder.getFolderId(), parentFolderId, name,
					description, addCommunityPermissions, addGuestPermissions);
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
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		File file = null;

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			long parentFolderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;

			try {
				parentFolderId = getParentFolderId(destinationArray);
			}
			catch (NoSuchFolderException nsfe) {
				return HttpServletResponse.SC_CONFLICT;
			}

			DLFileEntry fileEntry = (DLFileEntry)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destination);
			long userId = webDavReq.getUserId();
			String name = StringPool.BLANK;
			String title = WebDAVUtil.getResourceName(destinationArray);
			String description = fileEntry.getDescription();
			String[] tagsEntries = null;
			String extraSettings = fileEntry.getExtraSettings();

			file = FileUtil.createTempFile(
				FileUtil.getExtension(fileEntry.getName()));

			InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
				fileEntry.getCompanyId(), userId, fileEntry.getFolderId(),
				fileEntry.getName());

			FileUtil.write(file, is);

			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, title)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			DLFileEntryServiceUtil.addFileEntry(
				parentFolderId, name, title, description, tagsEntries,
				extraSettings, file, addCommunityPermissions,
				addGuestPermissions);

			return status;
		}
		catch (DuplicateFolderNameException dfne) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
		}
		catch (DuplicateFileException dfe) {
			return HttpServletResponse.SC_PRECONDITION_FAILED;
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

	public int deleteResource(WebDAVRequest webDavReq) throws WebDAVException {
		try {
			Resource resource = getResource(webDavReq);

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

				DLFileEntryServiceUtil.deleteFileEntry(
					fileEntry.getFolderId(), fileEntry.getName());
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

	public Resource getResource(WebDAVRequest webDavReq)
		throws WebDAVException {

		try {
			String[] pathArray = webDavReq.getPathArray();

			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);

			if (Validator.isNull(name)) {
				String path = getRootPath() + webDavReq.getPath();

				return new BaseResourceImpl(path, StringPool.BLANK, getToken());
			}

			try {
				DLFolder folder = DLFolderServiceUtil.getFolder(
					webDavReq.getGroupId(), parentFolderId, name);

				if ((folder.getParentFolderId() != parentFolderId) ||
					(webDavReq.getGroupId() != folder.getGroupId())) {

					throw new NoSuchFolderException();
				}

				return toResource(webDavReq, folder, false);
			}
			catch (NoSuchFolderException nsfe) {
				try {
					String titleWithExtension = name;

					DLFileEntry fileEntry =
						DLFileEntryServiceUtil.getFileEntryByTitle(
							parentFolderId, titleWithExtension);

					return toResource(webDavReq, fileEntry, false);
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

	public List<Resource> getResources(WebDAVRequest webDavReq)
		throws WebDAVException {

		try {
			long folderId = getFolderId(webDavReq.getPathArray());

			List<Resource> folders = getFolders(webDavReq, folderId);
			List<Resource> fileEntries = getFileEntries(webDavReq, folderId);

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

	public Status makeCollection(WebDAVRequest webDavReq)
		throws WebDAVException {

		try {
			HttpServletRequest req = webDavReq.getHttpServletRequest();

			if (req.getContentLength() > 0) {
				return new Status(
					HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
			}

			String[] pathArray = webDavReq.getPathArray();

			long plid = getPlid(webDavReq.getGroupId());
			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);
			String description = StringPool.BLANK;
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			DLFolderServiceUtil.addFolder(
				plid, parentFolderId, name, description,
				addCommunityPermissions, addGuestPermissions);

			String location = StringUtil.merge(pathArray, StringPool.SLASH);

			return new Status(location, HttpServletResponse.SC_CREATED);
		}
		catch (DuplicateFolderNameException dfne) {
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
			WebDAVRequest webDavReq, Resource resource, String destination,
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

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, name)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			DLFolderServiceUtil.updateFolder(
				folderId, parentFolderId, name, description);

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
			WebDAVRequest webDavReq, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			String[] destinationArray = WebDAVUtil.getPathArray(
				destination, true);

			DLFileEntry fileEntry = (DLFileEntry)resource.getModel();

			long groupId = WebDAVUtil.getGroupId(destinationArray);
			long parentFolderId = getParentFolderId(destinationArray);
			String name = fileEntry.getName();
			String sourceFileName = null;
			String title = WebDAVUtil.getResourceName(destinationArray);
			String description = fileEntry.getDescription();
			String[] tagsEntries = null;
			String extraSettings = fileEntry.getExtraSettings();
			byte[] byteArray = null;

			int status = HttpServletResponse.SC_CREATED;

			if (overwrite) {
				if (deleteResource(groupId, parentFolderId, title)) {
					status = HttpServletResponse.SC_NO_CONTENT;
				}
			}

			DLFileEntryServiceUtil.updateFileEntry(
				fileEntry.getFolderId(), parentFolderId, name, sourceFileName,
				title, description, tagsEntries, extraSettings, byteArray);

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
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int putResource(WebDAVRequest webDavReq) throws WebDAVException {
		File file = null;

		try {
			HttpServletRequest req = webDavReq.getHttpServletRequest();

			String[] pathArray = webDavReq.getPathArray();

			long parentFolderId = getParentFolderId(pathArray);
			String name = WebDAVUtil.getResourceName(pathArray);
			String title = name;
			String description = StringPool.BLANK;
			String[] tagsEntries = null;
			String extraSettings = StringPool.BLANK;
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			file = FileUtil.createTempFile(FileUtil.getExtension(name));

			FileUtil.write(file, req.getInputStream());

			DLFileEntryServiceUtil.addFileEntry(
				parentFolderId, name, title, description, tagsEntries,
				extraSettings, file, addCommunityPermissions,
				addGuestPermissions);

			return HttpServletResponse.SC_CREATED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
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

	protected boolean deleteResource(
			long groupId, long parentFolderId, String name)
		throws PortalException, SystemException, RemoteException {

		try {
			DLFolder folder = DLFolderServiceUtil.getFolder(
				groupId, parentFolderId, name);

			DLFolderServiceUtil.deleteFolder(folder.getFolderId());

			return true;
		}
		catch (NoSuchFolderException nsfe) {
			try {
				DLFileEntryServiceUtil.deleteFileEntryByTitle(
					parentFolderId, name);

				return true;
			}
			catch (NoSuchFileEntryException nsfee) {
			}
		}

		return false;
	}

	protected List<Resource> getFileEntries(
			WebDAVRequest webDavReq, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		List<DLFileEntry> fileEntries = DLFileEntryServiceUtil.getFileEntries(
			parentFolderId);

		for (DLFileEntry fileEntry : fileEntries) {
			Resource resource = toResource(webDavReq, fileEntry, true);

			resources.add(resource);
		}

		return resources;
	}

	protected long getFolderId(String[] pathArray) throws Exception {
		return getFolderId(pathArray, false);
	}

	protected long getFolderId(String[] pathArray, boolean parent)
		throws Exception {

		long folderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;

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
			WebDAVRequest webDavReq, long parentFolderId)
		throws Exception {

		List<Resource> resources = new ArrayList<Resource>();

		long groupId = webDavReq.getGroupId();

		List<DLFolder> folders = DLFolderServiceUtil.getFolders(
			groupId, parentFolderId);

		for (DLFolder folder : folders) {
			Resource resource = toResource(webDavReq, folder, true);

			resources.add(resource);
		}

		return resources;
	}

	protected long getParentFolderId(String[] pathArray) throws Exception {
		return getFolderId(pathArray, true);
	}

	protected Resource toResource(
		WebDAVRequest webDavReq, DLFileEntry fileEntry, boolean appendPath) {

		String parentPath = getRootPath() + webDavReq.getPath();
		String name = StringPool.BLANK;

		if (appendPath) {
			name = fileEntry.getTitleWithExtension();
		}

		return new DLFileEntryResourceImpl(
			webDavReq, fileEntry, parentPath, name);
	}

	protected Resource toResource(
		WebDAVRequest webDavReq, DLFolder folder, boolean appendPath) {

		String parentPath = getRootPath() + webDavReq.getPath();
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

	private static Log _log = LogFactory.getLog(DLWebDAVStorageImpl.class);

}