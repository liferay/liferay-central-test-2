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

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.StringMaker;

/**
 * <a href="SearchEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SearchEngineUtil {

	public static final int ALL_POS = -1;

	public static void addDocument(long companyId, Document doc)
		throws SearchException {

		_instance._addDocument(companyId, doc);
	}

	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		_instance._deleteDocument(companyId, uid);
	}

	public static void deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		_instance._deletePortletDocuments(companyId, portletId);
	}

	public static void init(
			IndexSearcher defaultIndexSearcher,
			IndexWriter defaultIndexWriter) {

		_instance._init(defaultIndexSearcher, defaultIndexWriter);
	}

	public static boolean isIndexReadOnly() {
		return _instance._isIndexReadOnly();
	}

	public static Hits search(long companyId, String query, int start, int end)
		throws SearchException {

		return _instance._search(companyId, query, start, end);
	}

	public static Hits search(
			long companyId, String query, Sort sort, int start, int end)
		throws SearchException {

		return _instance._search(companyId, query, sort, start, end);
	}

	public static void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		_instance._updateDocument(companyId, uid, doc);
	}

	private SearchEngineUtil() {
	}

	private void _addDocument(long companyId, Document doc)
		throws SearchException {

		_messageBusIndexWriter.addDocument(companyId, doc);
	}

	private void _deleteDocument(long companyId, String uid)
		throws SearchException {

		_messageBusIndexWriter.deleteDocument(companyId, uid);
	}

	private void _deletePortletDocuments(long companyId, String portletId)
		throws SearchException {

		_messageBusIndexWriter.deletePortletDocuments(companyId, portletId);
	}

	private void _init(
			IndexSearcher messageBusIndexSearcher,
			IndexWriter messageBusIndexWriter) {

		_messageBusIndexSearcher = messageBusIndexSearcher;
		_messageBusIndexWriter = messageBusIndexWriter;
	}

	private boolean _isIndexReadOnly() {
		StringMaker sm = new StringMaker();

		sm.append("{\"javaClass\":\"");
		sm.append(SearchEngineRequest.class.getName());
		sm.append("\",\"command\":\"");
		sm.append(SearchEngineRequest.COMMAND_INDEX_ONLY);
		sm.append("\"}");

		String json = MessageBusUtil.sendSynchronizedMessage(
			DestinationNames.SEARCH, sm.toString());

		if (json.indexOf("true") != -1) {
			return true;
		}

		return false;
	}

	private Hits _search(long companyId, String query, int start, int end)
		throws SearchException {

		return _messageBusIndexSearcher.search(companyId, query, start, end);
	}

	private Hits _search(
			long companyId, String query, Sort sort, int start, int end)
		throws SearchException {

		return _messageBusIndexSearcher.search(
			companyId, query, sort, start, end);
	}

	private void _updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		_messageBusIndexWriter.updateDocument(companyId, uid, doc);
	}

	private static SearchEngineUtil _instance = new SearchEngineUtil();

	private IndexSearcher _messageBusIndexSearcher;
	private IndexWriter _messageBusIndexWriter;

}