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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointException;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portal.sharepoint.Tree;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="RemoveDocumentsMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class RemoveDocumentsMethodImpl extends BaseMethodImpl {

	public String getMethodName() {
		return _METHOD_NAME;
	}

	public List<ResponseElement> getElements(SharepointRequest request)
		throws SharepointException {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		String urlList = ParamUtil.getString(
			request.getHttpRequest(), "url_list");

		urlList = urlList.substring(1, urlList.length() - 1);

		String documentNames[] = urlList.split(StringPool.SEMICOLON);

		List<DLFolder> folders = new ArrayList<DLFolder>();

		List<DLFileEntry> entries = new ArrayList<DLFileEntry>();

		for (String documentName : documentNames) {
			try {
				long ids[] = SharepointUtil.getIds(documentName);

				folders.add(DLFolderServiceUtil.getFolder(ids[1]));
			}
			catch (Exception e) {
				if (e instanceof NoSuchFolderException) {
					try {
						entries.add(
							SharepointUtil.getDLFileEntry(documentName));
					}
					catch (Exception e2) {
					}
				}
			}
		}

		Tree documentTree = new Tree();

		Tree removedDocsTree = new Tree();
		Tree failedDocsTree = new Tree();

		for (DLFileEntry entry : entries) {
			try {
				documentTree = SharepointUtil.getDocumentTree(entry);

				DLFileEntryServiceUtil.deleteFileEntry(
					entry.getFolderId(), entry.getName());

				removedDocsTree.addChild(documentTree);
			}
			catch (Exception e) {
				try {
					failedDocsTree.addChild(documentTree);
				}
				catch (Exception e2) {
				}
			}
		}

		Tree folderTree = new Tree();

		Tree removedDirsTree = new Tree();
		Tree failedDirsTree = new Tree();

		for (DLFolder folder : folders) {
			try {
				folderTree = SharepointUtil.getDLFolderTree(folder);

				DLFolderServiceUtil.deleteFolder(folder.getFolderId());

				removedDirsTree.addChild(folderTree);
			}
			catch (Exception e) {
				try {
					failedDirsTree.addChild(folderTree);
				}
				catch (Exception e2) {
				}
			}
		}

		elements.add(new Property("removed_docs", removedDocsTree));
		elements.add(new Property("removed_dirs", removedDirsTree));
		elements.add(new Property("failed_docs", failedDocsTree));
		elements.add(new Property("failed_dirs", failedDirsTree));

		return elements;
	}

	private static final String _METHOD_NAME = "remove documents";

}