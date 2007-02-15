/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.webdav.BaseResourceImpl;
import com.liferay.portal.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.webdav.Resource;
import com.liferay.portal.webdav.WebDAVException;
import com.liferay.portal.webdav.WebDAVRequest;
import com.liferay.portal.webdav.WebDAVUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.util.FileUtil;
import com.liferay.util.PwdGenerator;
import com.liferay.util.SystemProperties;
import com.liferay.util.Time;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="DLWebDAVStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLWebDAVStorageImpl extends BaseWebDAVStorageImpl {

	public int addFolder(WebDAVRequest webDavReq)
		throws WebDAVException {

		try {
			String path = webDavReq.getPath();

			String[] pathArray = WebDAVUtil.getPathArray(path);

			String plid = getPlid(webDavReq.getGroupId());
			String parentFolderId = getParentFolderId(path, true);
			String name = pathArray[pathArray.length - 1];
			String description = StringPool.BLANK;
			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			DLFolderServiceUtil.addFolder(
				plid, parentFolderId, name, description,
				addCommunityPermissions, addGuestPermissions);

			return HttpServletResponse.SC_CREATED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int deleteResource(WebDAVRequest webDavReq) throws WebDAVException {
		try {
			Resource resource = getResource(webDavReq);

			if (resource != null) {
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
			else {
				return HttpServletResponse.SC_NOT_FOUND;
			}
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public Resource getResource(WebDAVRequest webDavReq) throws WebDAVException {
		try {
			String path = webDavReq.getPath();

			String[] pathArray = WebDAVUtil.getPathArray(path);

			DLFolder parentFolder = null;

			String parentFolderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;

			for (int i = 2; i < (pathArray.length - 1); i++) {
				String folderName = pathArray[i];

				parentFolder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, folderName);

				parentFolderId = parentFolder.getFolderId();
			}

			String name = pathArray[pathArray.length - 1];

			try {
				DLFolder folder = DLFolderLocalServiceUtil.getFolder(
					parentFolderId, name);

				return toResource(webDavReq, folder);
			}
			catch (NoSuchFolderException nsfe) {
				try {
					DLFileEntry fileEntry =
						DLFileEntryLocalServiceUtil.getFileEntry(
							parentFolderId, name);

					return toResource(webDavReq, fileEntry);
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

	public List getResources(WebDAVRequest webDavReq)
		throws WebDAVException {

		try {
			String parentFolderId = getParentFolderId(
				webDavReq.getPath(), false);

			List folders = getFolders(webDavReq, parentFolderId);
			List fileEntries = getFileEntries(webDavReq, parentFolderId);

			List resources = new ArrayList(folders.size() + fileEntries.size());

			resources.addAll(folders);
			resources.addAll(fileEntries);

			return resources;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int moveResource(WebDAVRequest webDavReq, String destination)
		throws WebDAVException {

		try {
			Resource resource = getResource(webDavReq);

			if (resource != null) {
				Object model = resource.getModel();

				if (model instanceof DLFolder) {
					DLFolder folder = (DLFolder)model;

					String[] destinationArray =
						WebDAVUtil.getPathArray(destination);

					String folderId = folder.getFolderId();
					String parentFolderId = folder.getFolderId();
					String name = destinationArray[destinationArray.length - 1];
					String description = folder.getDescription();

					DLFolderServiceUtil.updateFolder(
						folderId, parentFolderId, name, description);
				}
				else {
					DLFileEntry fileEntry = (DLFileEntry)model;

					/*String folderId = fileEntry.getFolderId();
					String newFolderId = folderId;
					String name = fileEntry.getName();
					String sourceFileName = null;
					String title = fileEntry.getTitle();
					String description = fileEntry.getDescription();
					String[] tagsEntries = null;
					String extraSettings = fileEntry.getExtraSettings();
					byte[] byteArray = null;

					DLFileEntryServiceUtil.updateFileEntry(
						fileEntry.getFolderId(), fileEntry.getFolderId(),
						fileEntry.getName(), null, fileEntry.getTitle(), fileEntry.getDescription());

	public DLFileEntry updateFileEntry(
			String folderId, String newFolderId, String name,
			String sourceFileName, String title, String description,
			String[] tagsEntries, String extraSettings, byte[] byteArray)*/

				}

				return HttpServletResponse.SC_CREATED;
			}
			else {
				return HttpServletResponse.SC_NOT_FOUND;
			}
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	public int putResource(WebDAVRequest webDavReq, String destination)
		throws WebDAVException {

		File file = null;

		try {
			HttpServletRequest req = webDavReq.getHttpServletRequest();
			String path = webDavReq.getPath();
			String userId = webDavReq.getUserId();

			String[] pathArray = WebDAVUtil.getPathArray(path);

			String folderId = getParentFolderId(path, true);
			String name = pathArray[pathArray.length - 1];
			String title = StringPool.BLANK;
			String description = StringPool.BLANK;
			String[] tagsEntries = new String[0];
			String extraSettings = StringPool.BLANK;

			String fileName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					StringPool.SLASH + Time.getTimestamp() +
						PwdGenerator.getPassword(PwdGenerator.KEY2, 8);

			file = new File(fileName);

			FileUtil.write(file, req.getInputStream());

			if (_log.isDebugEnabled()) {
				_log.debug("Writing request to file " + fileName);
			}

			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			DLFolderPermission.check(
				webDavReq.getPermissionChecker(), folderId,
				ActionKeys.ADD_DOCUMENT);

			DLFileEntryLocalServiceUtil.addFileEntry(
				userId, folderId, name, title, description, tagsEntries,
				extraSettings, file, addCommunityPermissions,
				addGuestPermissions);

			return HttpServletResponse.SC_CREATED;
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

	protected List getFileEntries(
			WebDAVRequest webDavReq, String parentFolderId)
		throws Exception {

		List fileEntries = new ArrayList();

		Iterator itr = DLFileEntryLocalServiceUtil.getFileEntries(
			parentFolderId).iterator();

		while (itr.hasNext()) {
			DLFileEntry fileEntry = (DLFileEntry)itr.next();

			Resource resource = toResource(webDavReq, fileEntry);

			fileEntries.add(resource);
		}

		return fileEntries;
	}

	protected List getFolders(WebDAVRequest webDavReq, String parentFolderId)
		throws Exception {

		List folders = new ArrayList();

		Iterator itr = DLFolderLocalServiceUtil.getFolders(
			webDavReq.getGroupId(), parentFolderId).iterator();

		while (itr.hasNext()) {
			DLFolder folder = (DLFolder)itr.next();

			Resource resource = toResource(webDavReq, folder);

			folders.add(resource);
		}

		return folders;
	}

	protected String getParentFolderId(String path, boolean newResource)
		throws Exception {

		String parentFolderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;

		String[] pathArray = WebDAVUtil.getPathArray(path);

		if (pathArray.length <= 2) {
			return parentFolderId;
		}
		else {
			int x = pathArray.length;

			if (newResource) {
				x--;
			}

			for (int i = 2; i < x; i++) {
				try {
					DLFolder folder = DLFolderLocalServiceUtil.getFolder(
						parentFolderId, pathArray[i]);

					parentFolderId = folder.getFolderId();
				}
				catch (NoSuchFolderException nsfe) {
					break;
				}
			}
		}

		return parentFolderId;
	}

	protected Resource toResource(
		WebDAVRequest webDavReq, DLFileEntry fileEntry) {

		String href =
			getRootPath() + webDavReq.getPath() + StringPool.SLASH +
				fileEntry.getName();

		return new DLFileEntryResourceImpl(webDavReq, fileEntry, href);
	}

	protected Resource toResource(WebDAVRequest webDavReq, DLFolder folder) {
		String href =
			getRootPath() + webDavReq.getPath() + StringPool.SLASH +
				folder.getName();

		Resource resource = new BaseResourceImpl(
			href, true, folder.getCreateDate(), folder.getModifiedDate());

		resource.setModel(folder);

		return resource;
	}

	private static Log _log = LogFactory.getLog(DLWebDAVStorageImpl.class);

}