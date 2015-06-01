/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.journal.search;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.test.BaseSearchResultUtilTestCase;
import com.liferay.portal.kernel.search.test.SearchTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({IndexerRegistryUtil.class, ServiceTrackerCollections.class})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilJournalArticleTest
	extends BaseSearchResultUtilTestCase {

	@Test
	public void testJournalArticle() {
		SearchResult searchResult = assertOneSearchResult(createDocument());

		assertSearchResult(searchResult);

		Assert.assertNull(searchResult.getSummary());
	}

	@Test
	public void testJournalArticleWithDefectiveIndexer() throws Exception {
		Indexer indexer = Mockito.mock(Indexer.class);

		Mockito.doThrow(
			IllegalArgumentException.class
		).when(
			indexer
		).getSummary(
			(Document)Matchers.any(), Matchers.anyString(),
			(PortletRequest)Matchers.any(), (PortletResponse)Matchers.any());

		stub(
			method(IndexerRegistryUtil.class, "getIndexer", String.class)
		).toReturn(
			indexer
		);

		Document document = createDocument();

		SearchResult searchResult = assertOneSearchResult(document);

		assertSearchResult(searchResult);

		Assert.assertNull(searchResult.getSummary());

		Mockito.verify(
			indexer
		).getSummary(
			document, StringPool.BLANK, null, null
		);
	}

	protected void assertSearchResult(SearchResult searchResult) {
		Assert.assertEquals(
			_JOURNAL_ARTICLE_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		List<String> versions = searchResult.getVersions();

		Assert.assertEquals(_DOCUMENT_VERSION, versions.get(0));
		Assert.assertEquals(1, versions.size());

		assertEmptyFileEntryTuples(searchResult);
		assertEmptyMBMessages(searchResult);
	}

	protected Document createDocument() {
		Document document = SearchTestUtil.createDocument(
			_JOURNAL_ARTICLE_CLASS_NAME);

		document.add(new Field(Field.VERSION, _DOCUMENT_VERSION));

		return document;
	}

	private static final String _DOCUMENT_VERSION = String.valueOf(
		RandomTestUtil.randomInt());

	private static final String _JOURNAL_ARTICLE_CLASS_NAME =
		JournalArticle.class.getName();

}