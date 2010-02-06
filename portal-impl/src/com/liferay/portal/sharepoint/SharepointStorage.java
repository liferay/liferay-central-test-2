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

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.xml.Element;

import java.io.InputStream;

import java.util.List;

/**
 * <a href="SharepointStorage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public interface SharepointStorage {

	public void addDocumentElements(
			SharepointRequest sharepointRequest, Element element)
		throws Exception;

	public void createFolder(SharepointRequest sharepointRequest)
		throws Exception;

	public InputStream getDocumentInputStream(
			SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getDocumentTree(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getDocumentsTree(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getFolderTree(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree getFoldersTree(SharepointRequest sharepointRequest)
		throws Exception;

	public void getParentFolderIds(
			long groupId, String path, List<Long> folderIds)
		throws Exception;

	public Tree[] moveDocument(SharepointRequest sharepointRequest)
		throws Exception;

	public void putDocument(SharepointRequest sharepointRequest)
		throws Exception;

	public Tree[] removeDocument(SharepointRequest sharepointRequest)
		throws Exception;

}