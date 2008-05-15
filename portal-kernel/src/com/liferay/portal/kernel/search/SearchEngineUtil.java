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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <a href="SearchEngineUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class SearchEngineUtil {

	public static final int ALL_POS = -1;

	public static final String INDEX_WRITER_DESTINATION =
		"liferay/search/IndexWriter";

	public static void addDocument(long companyId, Document doc)
		throws SearchException {

		_instance._addDocument(companyId, doc);
	}

	public static void deleteDocument(long companyId, String uid)
		throws SearchException {

		_instance._deleteDocument(companyId, uid);
	}

	public static Collection<SearchEngine> getRegisteredSearchEngines() {
		return _instance._getRegisteredSearchEngines();
	}

	public static void init(
		SearchEngine defaultSearchEngine, IndexWriter defaultIndexWriter) {

		_instance._init(defaultSearchEngine, defaultIndexWriter);
	}

	public static boolean isIndexReadOnly() {
		return _instance._isIndexReadOnly();
	}

	public static boolean isMessageBusListener() {
		return _instance._isMessageBusListener();
	}

	public static void registerSearchEngine(
		SearchEngine engine, boolean current) {

		_instance._registerSearchEngine(engine, current);
	}

	public static Hits search(long companyId, Query query, int begin, int end)
		throws SearchException {

		return _instance._search(companyId, query, begin, end);
	}

	public static void setCurrentSearchEngine(String name) {
		_instance._setCurrentSearchEngine(name);
	}

	public static void unregisterSearchEngine(String name) {
		_instance._unregisterSearchEngine(name);
	}

	public static void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		_instance._updateDocument(companyId, uid, doc);
	}

	private SearchEngineUtil() {
	}

	private void _addDocument(long companyId, Document doc)
		throws SearchException {

		ClassLoader contextClassLoader = _getContextClassLoader();

		try {
			if (_currentSearchEngine.isMessageBusListener()) {
				_messageBusIndexWriter.addDocument(companyId, doc);
			}
			else {
				_getWriter().addDocument(companyId, doc);
			}
		}
		finally {
			_setContextClassLoader(contextClassLoader);
		}
	}

	private void _deleteDocument(long companyId, String uid)
		throws SearchException {

		ClassLoader contextClassLoader = _getContextClassLoader();

		try {
			if (_currentSearchEngine.isMessageBusListener()) {
				_messageBusIndexWriter.deleteDocument(companyId, uid);
			}
			else {
				_getWriter().deleteDocument(companyId, uid);
			}
		}
		finally {
			_setContextClassLoader(contextClassLoader);
		}
	}

	private ClassLoader _getContextClassLoader() {
		ClassLoader contextClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
			_instance._getCurrentSearchEngineClassLoader());

		return contextClassLoader;
	}

	private ClassLoader _getCurrentSearchEngineClassLoader() {
		return _registeredSearchEnginesClassLoaders.get(
			_currentSearchEngine.getName());
	}

	private Collection<SearchEngine> _getRegisteredSearchEngines() {
		return _registeredSearchEngines.values();
	}

	private IndexSearcher _getSearcher() {
		return _currentSearchEngine.getSearcher();
	}

	private IndexWriter _getWriter() {
		return _currentSearchEngine.getWriter();
	}

	private void _init(
		SearchEngine defaultSearchEngine, IndexWriter messageBusIndexWriter) {

		_defaultSearchEngine = defaultSearchEngine;
		_messageBusIndexWriter = messageBusIndexWriter;
		_registerSearchEngine(_defaultSearchEngine, true);
	}

	private boolean _isIndexReadOnly() {
		ClassLoader contextClassLoader = _getContextClassLoader();

		try {
			return _currentSearchEngine.isIndexReadOnly();
		}
		finally {
			_setContextClassLoader(contextClassLoader);
		}
	}

	private boolean _isMessageBusListener() {
		ClassLoader contextClassLoader = _getContextClassLoader();

		try {
			return _currentSearchEngine.isMessageBusListener();
		}
		finally {
			_setContextClassLoader(contextClassLoader);
		}
	}

	private void _registerSearchEngine(SearchEngine engine, boolean current) {
		_registeredSearchEngines.put(engine.getName(), engine);
		_registeredSearchEnginesClassLoaders.put(
			engine.getName(), Thread.currentThread().getContextClassLoader());

		if (current) {
			_setCurrentSearchEngine(engine.getName());
		}
	}

	private Hits _search(long companyId, Query query, int begin, int end)
		throws SearchException {

		ClassLoader contextClassLoader = _getContextClassLoader();

		try {
			return _instance._getSearcher().search(
				companyId, query, begin, end);
		}
		finally {
			_setContextClassLoader(contextClassLoader);
		}
	}

	private void _setContextClassLoader(ClassLoader contextClassLoader) {
		Thread.currentThread().setContextClassLoader(contextClassLoader);
	}

	private void _setCurrentSearchEngine(String name) {
		SearchEngine engine = _registeredSearchEngines.get(name);

		if (engine != null) {
			_currentSearchEngine = engine;
		}
	}

	private void _unregisterSearchEngine(String name) {
		if (!name.equals(_defaultSearchEngine.getName())) {
			_registeredSearchEngines.remove(name);
			_registeredSearchEnginesClassLoaders.remove(name);

			if (_currentSearchEngine.getName().equals(name)) {
				_currentSearchEngine = _defaultSearchEngine;
			}
		}
	}

	private void _updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		ClassLoader contextClassLoader = _getContextClassLoader();

		try {
			if (_currentSearchEngine.isMessageBusListener()) {
				_messageBusIndexWriter.updateDocument(companyId, uid, doc);
			}
			else {
				_getWriter().updateDocument(companyId, uid, doc);
			}
		}
		finally {
			_setContextClassLoader(contextClassLoader);
		}
	}

	private static SearchEngineUtil _instance = new SearchEngineUtil();

	private Map<String, SearchEngine> _registeredSearchEngines =
		new HashMap<String, SearchEngine>();
	private Map<String, ClassLoader> _registeredSearchEnginesClassLoaders =
		new HashMap<String, ClassLoader>();
	private SearchEngine _currentSearchEngine;
	private SearchEngine _defaultSearchEngine;
	private IndexWriter _messageBusIndexWriter;

}