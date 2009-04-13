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

package com.liferay.portal.kernel.search.messaging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;

/**
 * <a href="SearchRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SearchRequest {

	public static final String COMMAND_ADD = "ADD";

	public static final String COMMAND_DELETE = "DELETE";

	public static final String COMMAND_DELETE_PORTLET_DOCS =
		"DELETE_PORTLET_DOCS";

	public static final String COMMAND_REGISTER = "REGISTER";

	public static final String COMMAND_SEARCH = "SEARCH";

	public static final String COMMAND_UNREGISTER = "UNREGISTER";

	public static final String COMMAND_UPDATE = "UPDATE";

	public SearchRequest() {
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

	public Query getQuery() {
		return _query;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public int getStart() {
		return _start;
	}

	public void setStart(int start) {
		_start = start;
	}

	public int getEnd() {
		return _end;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{command=");
		sb.append(_command);
		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", id=");
		sb.append(_id);
		sb.append(", doc=");
		sb.append(_doc);
		sb.append(", query=");
		sb.append(_query);
		sb.append(", sorts=");
		sb.append(_sorts);
		sb.append(", start=");
		sb.append(_start);
		sb.append(", end=");
		sb.append(_end);
		sb.append("}");

		return sb.toString();
	}
	private String _command;
	private long _companyId;
	private String _id;
	private Document _doc;
	private Query _query;
	private Sort[] _sorts;
	private int _start;
	private int _end;

}