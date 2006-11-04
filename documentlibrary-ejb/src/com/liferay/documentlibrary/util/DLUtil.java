/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.portal.jcr.JCRConstants;
import com.liferay.portal.jcr.JCRFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * <a href="DLUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class DLUtil {

	public static Node getFolderNode(Node node, String name)
		throws RepositoryException {

		Node folderNode = null;

		if (node.hasNode(name)) {
			folderNode = node.getNode(name);
		}
		else {
			folderNode = node.addNode(name, JCRConstants.NT_FOLDER);
		}

		return folderNode;
	}

	public static Node getRootNode(Session session, String companyId)
		throws RepositoryException {

		Node companyNode = getFolderNode(session.getRootNode(), companyId);

		return getFolderNode(companyNode, JCRFactory.NODE_DOCUMENTLIBRARY);
	}

}