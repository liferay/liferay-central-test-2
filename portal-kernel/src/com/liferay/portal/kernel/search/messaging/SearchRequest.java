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

package com.liferay.portal.kernel.search.messaging;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Arrays;

/**
 * <a href="SearchRequest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 */
public class SearchRequest implements Serializable {

	public static SearchRequest add(long companyId, Document document) {
		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.ADD);

		searchRequest.setCompanyId(companyId);
		searchRequest.setDocument(document);

		return searchRequest;
	}

	public static SearchRequest delete(long companyId, String uid) {
		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.DELETE);

		searchRequest.setCompanyId(companyId);
		searchRequest.setId(uid);

		return searchRequest;
	}

	public static SearchRequest deletePortletDocuments(
		long companyId, String portletId) {

		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.DELETE_PORTLET_DOCUMENTS);

		searchRequest.setCompanyId(companyId);
		searchRequest.setId(portletId);

		return searchRequest;
	}

	public static SearchRequest search(
		long companyId, Query query, Sort[] sorts, int start, int end) {

		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.SEARCH);

		searchRequest.setCompanyId(companyId);
		searchRequest.setQuery(query);
		searchRequest.setSorts(sorts);
		searchRequest.setStart(start);
		searchRequest.setEnd(end);

		return searchRequest;
	}

	public static SearchRequest update(
		long companyId, String uid, Document document) {

		SearchRequest searchRequest = new SearchRequest(
			SearchEngineCommand.UPDATE);

		searchRequest.setCompanyId(companyId);
		searchRequest.setId(uid);
		searchRequest.setDocument(document);

		return searchRequest;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Document getDocument() {
		return _document;
	}

	public int getEnd() {
		return _end;
	}

	public String getId() {
		return _id;
	}

	public Query getQuery() {
		return _query;
	}

	public SearchEngineCommand getSearchEngineCommand() {
		return _searchEngineCommand;
	}

	public Sort[] getSorts() {
		return _sorts;
	}

	public int getStart() {
		return _start;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setDocument(Document document) {
		_document = document;
	}

	public void setEnd(int end) {
		_end = end;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setQuery(Query query) {
		_query = query;
	}

	public void setSorts(Sort[] sorts) {
		_sorts = sorts;
	}

	public void setStart(int start) {
		_start = start;
	}

	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{searchEngineCommand=");
		sb.append(_searchEngineCommand);
		sb.append(", companyId=");
		sb.append(_companyId);
		sb.append(", id=");
		sb.append(_id);
		sb.append(", document=");
		sb.append(_document);
		sb.append(", query=");
		sb.append(_query);
		sb.append(", sorts=");
		sb.append(Arrays.toString(_sorts));
		sb.append(", start=");
		sb.append(_start);
		sb.append(", end=");
		sb.append(_end);
		sb.append("}");

		return sb.toString();
	}

	private SearchRequest(SearchEngineCommand searchEngineCommand) {
		_searchEngineCommand = searchEngineCommand;
	}

	private long _companyId;
	private Document _document;
	private int _end;
	private String _id;
	private Query _query;
	private SearchEngineCommand _searchEngineCommand;
	private Sort[] _sorts;
	private int _start;

}