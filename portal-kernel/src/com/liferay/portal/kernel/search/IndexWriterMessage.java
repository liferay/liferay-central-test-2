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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.StringMaker;

/**
 * <a href="IndexWriterMessage.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class IndexWriterMessage {

	public static final String ADD = "add";

	public static final String DELETE = "delete";

	public static final String DELETE_PORTLET_DOCS = "delete_portlet_docs";

	public static final String UPDATE = "update";

	public IndexWriterMessage() {
	}

	public IndexWriterMessage(String command, long companyId, String id) {
		this(command, companyId, id, null);
	}

	public IndexWriterMessage(String command, long companyId, Document doc) {
		this(command, companyId, null, doc);
	}

	public IndexWriterMessage(
		String command, long companyId, String id, Document doc) {

		_command = command;
		_companyId = companyId;
		_id = id;
		_doc = doc;
	}

	public String getCommand() {
		return _command;
	}

	public void setCommand(String command) {
		_command = command;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		_id = id;
	}

	public Document getDocument() {
		return _doc;
	}

	public void setDocument(Document doc) {
		_doc = doc;
	}

	public String toString() {
		StringMaker sm = new StringMaker();

		sm.append("{_command=");
		sm.append(_command);
		sm.append(", _companyId=");
		sm.append(_companyId);
		sm.append(", _id=");
		sm.append(_id);
		sm.append(", _doc=");
		sm.append(_doc);
		sm.append("}");

		return sm.toString();
	}

	private String _command;
	private long _companyId;
	private String _id;
	private Document _doc;

}