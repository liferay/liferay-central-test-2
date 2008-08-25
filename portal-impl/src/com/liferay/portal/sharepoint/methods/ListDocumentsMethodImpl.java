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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portal.sharepoint.Tree;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <a href="ListDocumentsMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class ListDocumentsMethodImpl extends BaseMethodImpl {

	public String getMethodName() {
		return _METHOD_NAME;
	}

	protected void addDLFolders(
			String initialURL, List<ResponseElement> elements, Tree urlDirsTree)
		throws Exception {

		long[] ids = SharepointUtil.getIds(initialURL);

		long groupId = ids[0];
		long parentFolderId = ids[1];

		String rootFolderPath = SharepointUtil.getGroupPath(groupId);

		Date rootCreateDate = new Date();
		Date rootModifiedDate = rootCreateDate;
		Date rootLastPostDate = rootCreateDate;

		List<DLFileEntry> entries = new ArrayList<DLFileEntry>();

		if (parentFolderId != DLFolderImpl.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder folder = DLFolderLocalServiceUtil.getDLFolder(
				parentFolderId);

			rootCreateDate = folder.getCreateDate();
			rootModifiedDate = folder.getModifiedDate();
			rootLastPostDate = folder.getLastPostDate();

			rootFolderPath = SharepointUtil.getDLFolderPath(parentFolderId);

			entries = DLFileEntryLocalServiceUtil.getFileEntries(
				parentFolderId);

			addFileEntries(elements, entries, rootFolderPath);
		}
		else {
			addFileEntries(elements, entries, null);
		}

		List<DLFolder> folders = DLFolderLocalServiceUtil.getFolders(
			groupId, parentFolderId);

		for (DLFolder folder : folders) {
			urlDirsTree.addChild(SharepointUtil.getDLFolderTree(folder));
		}

		Tree rootFolderTree = SharepointUtil.getFolderTree(
			rootFolderPath, rootCreateDate, rootModifiedDate, rootLastPostDate);

		urlDirsTree.addChild(rootFolderTree);
	}

	protected void addFileEntries(
			List<ResponseElement> elements, List<DLFileEntry> entries,
			String folderPath)
		throws Exception {

		Tree documentListTree = new Tree();

		for (DLFileEntry fileEntry : entries) {
			documentListTree.addChild(
				SharepointUtil.getDocumentTree(fileEntry));
		}

		Property documentListProperty =
			new Property("document_list", documentListTree);

		elements.add(documentListProperty);
	}

	protected void addGroups(
			SharepointRequest sharepointRequest, List<ResponseElement> elements,
			Tree urlDirsTree)
		throws Exception {

		List<DLFileEntry> fileEntries = new ArrayList<DLFileEntry>();

		addFileEntries(elements, fileEntries, null);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put(
			"usersGroups", new Long(sharepointRequest.getUserId()));

		List<Group> groups = GroupLocalServiceUtil.search(
			sharepointRequest.getCompanyId(), null, null, groupParams,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Group userGroup = GroupLocalServiceUtil.getUserGroup(
			sharepointRequest.getCompanyId(), sharepointRequest.getUserId());

		groups.add(userGroup);

		List<Organization> organizations =
			OrganizationLocalServiceUtil.getUserOrganizations(
				sharepointRequest.getUserId());

		for (Organization organization : organizations) {
			groups.add(organization.getGroup());
		}

		for (Group group : groups) {
			urlDirsTree.addChild(SharepointUtil.getGroupTree(group));
		}

		Date now = new Date();

		urlDirsTree.addChild(
			SharepointUtil.getFolderTree(StringPool.BLANK, now, now, now));
	}

	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		String initialURL = ParamUtil.getString(
			sharepointRequest.getHttpRequest(), "initialUrl");

		Tree urlDirsTree = new Tree();

		if (Validator.isNull(initialURL)) {
			addGroups(sharepointRequest, elements, urlDirsTree);
		}
		else {
			addDLFolders(initialURL, elements, urlDirsTree);
		}

		Property urlDirsProperty = new Property("urldirs", urlDirsTree);

		elements.add(urlDirsProperty);

		return elements;
	}

	private static final String _METHOD_NAME = "list documents";

}