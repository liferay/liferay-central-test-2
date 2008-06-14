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

package com.liferay.portal.kernel.search.messaging;

import com.liferay.portal.kernel.search.Document;
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

	public static final String COMMAND_INDEX_ONLY = "INDEX_ONLY";

	public static final String COMMAND_SEARCH = "SEARCH";

	public static final String COMMAND_UPDATE = "UPDATE";

	public SearchRequest() {
	}

	public SearchRequest(String command, long companyId, String id) {
		this(command, companyId, id, null);
	}

	public SearchRequest(String command, long companyId, Document doc) {
		this(command, companyId, null, doc);
	}

	public SearchRequest(
		String command, long companyId, String id, Document doc) {

		this(command, companyId, id, doc, null, null, 0, 0);
	}

	public SearchRequest(
		String command, long companyId, String query, Sort sort, int start,
		int end) {

		this(command, companyId, null, null, query, sort, start, end);
	}

	public SearchRequest(
		String command, long companyId, String id, Document doc, String query,
		Sort sort, int start, int end) {

		_command = command;
		_companyId = companyId;
		_id = id;
		_doc = doc;
		_query = query;
		_sort = sort;
		_start = start;
		_end = end;
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

	public String getQuery() {
		return _query;
	}

	public void setQuery(String query) {
		_query = query;
	}

	public Sort getSort() {
		return _sort;
	}

	public void setSort(Sort sort) {
		_sort = sort;
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

	private String _command;
	private long _companyId;
	private String _id;
	private Document _doc;
	private String _query;
	private Sort _sort;
	private int _start;
	private int _end;

}