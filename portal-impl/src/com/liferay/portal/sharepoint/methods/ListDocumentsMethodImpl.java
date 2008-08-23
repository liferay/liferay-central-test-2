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
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.sharepoint.Leaf;
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
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

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

		String rootFolderPath = getGroupPath(groupId);

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

			rootFolderPath = getDLFolderPath(parentFolderId);

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
			String folderPath = getDLFolderPath(folder.getFolderId());

			Date createDate = folder.getCreateDate();
			Date modifiedDate = folder.getModifiedDate();
			Date lastPostDate = folder.getLastPostDate();

			Tree folderTree = getFolderTree(
				folderPath, createDate, modifiedDate, lastPostDate);

			urlDirsTree.addChild(folderTree);
		}

		Tree rootFolderTree = getFolderTree(
			rootFolderPath, rootCreateDate, rootModifiedDate, rootLastPostDate);

		urlDirsTree.addChild(rootFolderTree);
	}

	protected void addFileEntries(
			List<ResponseElement> elements, List<DLFileEntry> entries,
			String folderPath) {

		Tree documentListTree = new Tree();

		for (DLFileEntry fileEntry : entries) {
			Tree metaInfoTree = new Tree();

			Date createDate = fileEntry.getCreateDate();
			Date modifiedDate = fileEntry.getModifiedDate();

			metaInfoTree.addChild(
				new Leaf(
					"vti_timecreated", SharepointUtil.getDate(createDate),
					false));

			metaInfoTree.addChild(
				new Leaf(
					"vti_timelastmodified",
					SharepointUtil.getDate(modifiedDate), false));

			metaInfoTree.addChild(
				new Leaf(
					"vti_timelastwritten", SharepointUtil.getDate(modifiedDate),
					false));

			metaInfoTree.addChild(
				new Leaf(
					"vti_title", "SR|" + fileEntry.getTitle(), false));

			metaInfoTree.addChild(
				new Leaf(
					"vti_filesize", "IR|" + fileEntry.getSize(), false));

			metaInfoTree.addChild(
				new Leaf(
					"vti_sourcecontrolversion", "SR|V" + fileEntry.getVersion(),
					false));

			Tree fileEntryTree = new Tree();

			fileEntryTree.addChild(
				new Leaf(
					"document_name",
					folderPath + StringPool.FORWARD_SLASH +
					fileEntry.getTitleWithExtension(),
					true));

			fileEntryTree.addChild(new Leaf("meta_info", metaInfoTree));

			documentListTree.addChild(fileEntryTree);
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
			String groupPath = getGroupPath(group.getGroupId());

			urlDirsTree.addChild(getFolderTree(groupPath));
		}

		urlDirsTree.addChild(getFolderTree(StringPool.BLANK));
	}

	protected String getDLFolderPath(long folderId) throws Exception {
		StringBuilder sb = new StringBuilder();

		DLFolder folder = DLFolderServiceUtil.getFolder(folderId);

		sb.append(getGroupPath(folder.getGroupId()));
		sb.append(folder.getPath());

		return sb.toString();
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

	protected Tree getFolderTree(String name) {
		Date now = new Date();

		return getFolderTree(name, now, now, now);
	}

	protected Tree getFolderTree(
		String name, Date createDate, Date modifiedDate, Date lastPostDate) {

		Tree folderTree = new Tree();

		Tree metaInfoTree = new Tree();

		metaInfoTree.addChild(
			new Leaf(
				"vti_timecreated", SharepointUtil.getDate(createDate), false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_timelastmodified", SharepointUtil.getDate(modifiedDate),
				false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_timelastwritten", SharepointUtil.getDate(lastPostDate),
				false));
		metaInfoTree.addChild(new Leaf("vti_hassubdirs", "BR|true", false));
		metaInfoTree.addChild(new Leaf("vti_isbrowsable", "BR|true", false));
		metaInfoTree.addChild(new Leaf("vti_isexecutable", "BR|false", false));
		metaInfoTree.addChild(new Leaf("vti_isscriptable", "BR|false", false));

		folderTree.addChild(new Leaf("url", name, true));
		folderTree.addChild(new Leaf("meta_info", metaInfoTree));

		return folderTree;
	}

	protected String getGroupPath(long groupId) throws Exception {
		StringBuilder sb = new StringBuilder();

		Group group = GroupServiceUtil.getGroup(groupId);

		String name = group.getName();

		long classPK = group.getClassPK();

		if (group.isUser()) {
			User user = UserLocalServiceUtil.getUserById(classPK);

			name = user.getFullName();
		}
		else if (group.isOrganization()) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(classPK);

			name= organization.getName();
		}

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_BRACKET);
		sb.append(group.getGroupId());
		sb.append(StringPool.CLOSE_BRACKET);

		return sb.toString();
	}

	private static final String _METHOD_NAME = "list documents";

}