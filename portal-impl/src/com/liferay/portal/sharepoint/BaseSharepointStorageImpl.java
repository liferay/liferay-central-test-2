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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <a href="BaseSharepointStorageImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public abstract class BaseSharepointStorageImpl implements SharepointStorage {

	public void addDocumentElements(
			SharepointRequest sharepointRequest, Element element)
		throws Exception {
	}

	public void createFolder(SharepointRequest sharepointRequest)
		throws Exception {
	}

	public InputStream getDocumentInputStream(
			SharepointRequest sharepointRequest)
		throws Exception {

		return null;
	}

	public Tree getDocumentTree(SharepointRequest sharepointRequest)
		throws Exception {

		return new Tree();
	}

	public Tree getDocumentsTree(SharepointRequest sharepointRequest)
		throws Exception {

		return new Tree();
	}

	public Tree getFolderTree(SharepointRequest sharepointRequest)
		throws Exception {

		return new Tree();
	}

	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception {

		return new Tree();
	}

	public void getParentFolderIds(
			long groupId, String path, List<Long> folderIds)
		throws Exception {
	}

	public Tree[] moveDocument(SharepointRequest sharepointRequest)
		throws Exception {

		return null;
	}

	public void putDocument(SharepointRequest sharepointRequest)
		throws Exception {
	}

	public Tree[] removeDocument(SharepointRequest sharepointRequest)
		throws Exception {

		return null;
	}

	protected void addDocumentElement(
			Element element, String documentName, Date createDate,
			Date modifiedDate, String userName)
		throws Exception {

		element.addNamespace("z", "#RowsetSchema");

		Element rowEl = element.addElement("z:row");

		rowEl.addAttribute("ows_FileRef", documentName);
		rowEl.addAttribute("ows_FSObjType", "0");
		rowEl.addAttribute("ows_Created", getDate(createDate, true));
		rowEl.addAttribute("ows_Author", userName);
		rowEl.addAttribute("ows_Modified", getDate(modifiedDate, true));
		rowEl.addAttribute("ows_Editor", userName);
	}

	protected String getDate(Date date, boolean xml) {
		if (date == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(2);

		if (xml) {
			sb.append(
				DateUtil.getDate(date, "yyyy-mm-dd HH:mm:ss Z", Locale.US));
		}
		else {
			sb.append("TR|");
			sb.append(
				DateUtil.getDate(date, "dd MMM yyyy HH:mm:ss Z", Locale.US));
		}

		return sb.toString();
	}

	protected Tree getDocumentTree(
		String documentName, Date createDate, Date modifiedDate, int size,
		String userName, double version) {

		Tree documentTree = new Tree();

		documentName = SharepointUtil.replaceBackSlashes(documentName);

		documentTree.addChild(new Leaf("document_name", documentName, true));

		String createDateString = getDate(createDate, false);
		String modifiedDateString = getDate(modifiedDate, false);

		Tree metaInfoTree = new Tree();

		metaInfoTree.addChild(
			new Leaf("vti_timecreated", createDateString, false));
		metaInfoTree.addChild(
			new Leaf("vti_timelastmodified", modifiedDateString, false));
		metaInfoTree.addChild(
			new Leaf("vti_timelastwritten", modifiedDateString, false));
		metaInfoTree.addChild(new Leaf("vti_filesize", "IR|" + size, false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolcheckedoutby", "SR|" + userName, false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_sourcecontroltimecheckedout", createDateString, false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolversion", "SR|V" + version, false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrollockexpires", createDateString, false));

		documentTree.addChild(new Leaf("meta_info", metaInfoTree));

		return documentTree;
	}

	protected Tree getFolderTree(String name) {
		Date now = new Date();

		return getFolderTree(name, now, now, now);
	}

	protected Tree getFolderTree(
		String name, Date createDate, Date modifiedDate, Date lastPostDate) {

		Tree folderTree = new Tree();

		Tree metaInfoTree = new Tree();

		name = SharepointUtil.replaceBackSlashes(name);

		metaInfoTree.addChild(
			new Leaf("vti_timecreated", getDate(createDate, false), false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_timelastmodified", getDate(modifiedDate, false), false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_timelastwritten", getDate(lastPostDate, false), false));
		metaInfoTree.addChild(new Leaf("vti_hassubdirs", "BR|true", false));
		metaInfoTree.addChild(new Leaf("vti_isbrowsable", "BR|true", false));
		metaInfoTree.addChild(new Leaf("vti_isexecutable", "BR|false", false));
		metaInfoTree.addChild(new Leaf("vti_isscriptable", "BR|false", false));

		folderTree.addChild(new Leaf("url", name, true));
		folderTree.addChild(new Leaf("meta_info", metaInfoTree));

		return folderTree;
	}

	protected long getLastFolderId(
			long groupId, String path, long defaultParentFolderId)
		throws Exception {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(defaultParentFolderId);

		String[] pathArray = SharepointUtil.getPathArray(path);

		if (pathArray.length > 2) {
			path = removeFoldersFromPath(path, 2);

			getParentFolderIds(groupId, path, folderIds);
		}

		return folderIds.get(folderIds.size() - 1);
	}

	protected String getParentFolderPath(String path) {
		int pos = path.lastIndexOf(StringPool.FORWARD_SLASH);

		return path.substring(0, pos);
	}

	protected String getResourceName(String path) {
		int pos = path.lastIndexOf(StringPool.FORWARD_SLASH);

		return path.substring(pos + 1);
	}

	protected String removeFoldersFromPath(String path, int index) {
		for (int i = 0; i < index; i++) {
			int pos = path.indexOf(StringPool.SLASH);

			path = path.substring(pos + 1);
		}

		return path;
	}

}