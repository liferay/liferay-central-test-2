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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portal.sharepoint.Tree;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.util.servlet.ServletResponseUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="GetDocumentMethodImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class GetDocumentMethodImpl extends BaseMethodImpl {

	public String getMethodName() {
		return _METHOD_NAME;
	}

	protected void doProcess(SharepointRequest sharepointRequest)
		throws Exception {

		StringBuilder sb = getResponseBuffer(sharepointRequest);

		sb.append(StringPool.NEW_LINE);

		String documentName = ParamUtil.getString(
			sharepointRequest.getHttpRequest(), "document_name");

		DLFileEntry fileEntry = SharepointUtil.getDLFileEntry(documentName);

		InputStream is = DLFileEntryLocalServiceUtil.getFileAsStream(
			sharepointRequest.getCompanyId(), sharepointRequest.getUserId(),
			fileEntry.getFolderId(), fileEntry.getName());

		byte[] bytes = ArrayUtil.append(
			sb.toString().getBytes(), FileUtil.getBytes(is));

		ServletResponseUtil.write(
			sharepointRequest.getHttpResponse(), bytes);
	}

	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		String documentName = ParamUtil.getString(
			sharepointRequest.getHttpRequest(), "document_name");

		elements.add(
			new Property(
				"message",
				"successfully retrieved document '" + documentName +
					"' from '" + documentName + "'"));

		Tree documentTree = SharepointUtil.getDocumentTree(documentName);

		Property documentProperty = new Property("document", documentTree);

		elements.add(documentProperty);

		return elements;
	}

	private static final String _METHOD_NAME = "get document";

}