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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * <a href="SharepointUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SharepointUtil {

	public static final String VERSION = "6.0.2.8117";

	public static void addBottom(StringBuilder sb) {
		sb.append("</body>");
		sb.append(StringPool.NEW_LINE);
		sb.append("</html>");
	}

	public static void addTop(StringBuilder sb, String methodName) {
		sb.append("<html><head><title>vermeer RPC packet</title></head>");
		sb.append(StringPool.NEW_LINE);
		sb.append("<body>");
		sb.append(StringPool.NEW_LINE);

		Property method = new Property("method", methodName + ":" + VERSION);

		sb.append(method.parse());
	}

	public static String getDate(Date date) {
		StringBuilder sb = new StringBuilder();

		sb.append("TR|");
		sb.append(DateUtil.getDate(date, "dd MMM yyyy HH:mm:ss Z", Locale.US));

		return sb.toString();
	}

	public static DLFileEntry getDLFileEntry(String documentName)
		throws Exception {

		int pos = documentName.lastIndexOf(StringPool.FORWARD_SLASH);

		String folderName = documentName.substring(0, pos);
		String name = documentName.substring(pos + 1);

		long ids[] = getIds(folderName);

		long folderId = ids[1];

		DLFileEntry fileEntry = DLFileEntryServiceUtil.getFileEntryByTitle(
			folderId, name);

		return fileEntry;
	}

	public static Tree getDocumentTree(String documentName) throws Exception {
		DLFileEntry fileEntry = getDLFileEntry(documentName);

		Tree documentTree = new Tree();

		documentTree.addChild(new Leaf("document_name", documentName, true));

		Tree metaInfoTree = new Tree();

		String createDate = getDate(fileEntry.getCreateDate());
		String modifiedDate = getDate(fileEntry.getModifiedDate());

		metaInfoTree.addChild(new Leaf("vti_timecreated", createDate, false));
		metaInfoTree.addChild(
			new Leaf("vti_timelastmodified", modifiedDate, false));
		metaInfoTree.addChild(
			new Leaf("vti_timelastwritten", modifiedDate, false));
		metaInfoTree.addChild(
			new Leaf("vti_filesize", "IR|" + fileEntry.getSize(), false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_sourcecontrolcheckedoutby",
				"SR|" + fileEntry.getUserName(), false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontroltimecheckedout", createDate, false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_sourcecontrolversion", "SR|V" + fileEntry.getVersion(),
				false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrollockexpires", createDate, false));

		documentTree.addChild(new Leaf("meta_info", metaInfoTree));

		return documentTree;
	}

	public static long[] getIds(String initialURL) throws Exception {
		long groupId = 0;

		long parentFolderId = DLFolderImpl.DEFAULT_PARENT_FOLDER_ID;

		String[] folderNames = initialURL.split(StringPool.FORWARD_SLASH);

		String groupFolderName = folderNames[0];

		if (groupFolderName != null) {
			int pos = groupFolderName.lastIndexOf(StringPool.OPEN_BRACKET);

			if (pos != -1) {
				 groupId = GetterUtil.getLong(
					groupFolderName.substring(
						pos, groupFolderName.length() - 1));
			}

			List<Long> folderIds = new ArrayList<Long>();

			folderIds.add(parentFolderId);

			pos = initialURL.indexOf(StringPool.FORWARD_SLASH);

			if (pos != -1) {
				initialURL = initialURL.substring(pos + 1);

				getParentFolderIds(groupId, initialURL, folderIds);
			}

			parentFolderId = folderIds.get(folderIds.size() - 1);
		}

		return new long[] {groupId, parentFolderId};
	}

	public static void getParentFolderIds(
			long groupId, String initialURL, List<Long> folderIds)
		throws Exception {

		long parentFolderId = folderIds.get(folderIds.size() - 1);

		String[] folderNames = initialURL.split(StringPool.FORWARD_SLASH);

		if (folderNames.length > 0) {
			long folderId = DLFolderServiceUtil.getFolderId(
				groupId, parentFolderId, folderNames[0]);

			folderIds.add(folderId);

			int pos = initialURL.indexOf(StringPool.FORWARD_SLASH);

			if (pos != -1) {
				initialURL = initialURL.substring(pos + 1);

				getParentFolderIds(groupId, initialURL, folderIds);
			}
		}
	}

}