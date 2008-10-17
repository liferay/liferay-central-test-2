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

package com.liferay.portal.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusException;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.messaging.SearchRequest;

/**
 * <a href="SearchEngineImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SearchEngineImpl implements SearchEngine {

	public String getName() {
		throw new IllegalStateException();
	}

	public IndexSearcher getSearcher() {
		return _searcher;
	}

	public IndexWriter getWriter() {
		return _writer;
	}

	public boolean isIndexReadOnly() {
		if (_indexReadOnly != null) {
			return _indexReadOnly.booleanValue();
		}

		try {
			SearchRequest searchRequest = new SearchRequest();

			searchRequest.setCommand(SearchRequest.COMMAND_INDEX_ONLY);

			_indexReadOnly = (Boolean)MessageBusUtil.sendSynchronousMessage(
				DestinationNames.SEARCH_READER, searchRequest);

			if (_indexReadOnly == null) {
				_indexReadOnly = Boolean.FALSE;
			}

			return _indexReadOnly.booleanValue();
		}
		catch (MessageBusException mbe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to check index status", mbe);
			}

			return false;
		}
	}

	public void setSearcher(IndexSearcher searcher) {
		_searcher = searcher;
	}

	public void setWriter(IndexWriter writer) {
		_writer = writer;
	}

	public void unregister(String name) {
		SearchRequest searchRequest = new SearchRequest();

		searchRequest.setCommand(SearchRequest.COMMAND_UNREGISTER);
		searchRequest.setId(name);

		MessageBusUtil.sendMessage(
			DestinationNames.SEARCH_WRITER, searchRequest);
	}

	private static Log _log = LogFactoryUtil.getLog(SearchEngineImpl.class);

	private IndexSearcher _searcher;
	private IndexWriter _writer;
	private Boolean _indexReadOnly;

}