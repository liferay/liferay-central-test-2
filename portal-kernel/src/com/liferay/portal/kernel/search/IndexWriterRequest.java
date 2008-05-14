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

/**
 * <a href="IndexWriterRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class IndexWriterRequest {

	public static final String ADD = "add";

	public static final String DELETE = "delete";

	public static final String UPDATE = "update";

	public IndexWriterRequest(String command, long companyId, String uid) {
		this(command, companyId, uid, null);
	}

	public IndexWriterRequest(String command, long companyId, Document doc) {
		this(command, companyId, null, doc);
	}

	public IndexWriterRequest(
		String command, long companyId, String uid, Document doc) {

		_command = command;
		_companyId = companyId;
		_uid = uid;
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

	public String getUid() {
		return _uid;
	}

	public void setUid(String uid) {
		_uid = uid;
	}

	public Document getDocument() {
		return _doc;
	}

	public void setDocument(Document doc) {
		_doc = doc;
	}

	private String _command;
	private long _companyId;
	private String _uid;
	private Document _doc;

}