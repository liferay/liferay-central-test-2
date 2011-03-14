/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.search;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class IndexerWrapper implements Indexer {

	public IndexerWrapper(Indexer indexer) {
		_indexer = indexer;
	}

	public void delete(Object obj) throws SearchException {
		_indexer.delete(obj);
	}

	public String[] getClassNames() {
		return _indexer.getClassNames();
	}

	public Document getDocument(Object obj) throws SearchException {
		return _indexer.getDocument(obj);
	}

	public Summary getSummary(
			Document document, String snippet, PortletURL portletURL)
		throws SearchException {

		return _indexer.getSummary(document, snippet, portletURL);
	}

	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexer.registerIndexerPostProcessor(indexerPostProcessor);
	}

	public void reindex(Object obj) throws SearchException {
		_indexer.reindex(obj);
	}

	public void reindex(String className, long classPK) throws SearchException {
		_indexer.reindex(className, classPK);
	}

	public void reindex(String[] ids) throws SearchException {
		_indexer.reindex(ids);
	}

	public Hits search(SearchContext searchContext) throws SearchException {
		return _indexer.search(searchContext);
	}

	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {

		_indexer.unregisterIndexerPostProcessor(indexerPostProcessor);
	}

	private Indexer _indexer;

}