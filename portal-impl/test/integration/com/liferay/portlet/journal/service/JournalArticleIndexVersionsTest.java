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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AggregateTestRule;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationTestRule;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleIndexVersionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS = false;

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS));
	}

	@Test
	public void testIndexVersions() throws Exception {
		long initialSearchCount = searchCount();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalArticle expiredArticle = JournalTestUtil.updateArticle(article);

		expiredArticle = JournalTestUtil.expireArticle(
			_group.getGroupId(), article, expiredArticle.getVersion());

		List<JournalArticle> articles = search();

		Assert.assertEquals(initialSearchCount + 1, articles.size());

		JournalArticle searchArticle = articles.get(0);

		Assert.assertEquals(article.getId(), searchArticle.getId());

		article = JournalTestUtil.updateArticle(expiredArticle);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		articles = search();

		Assert.assertEquals(initialSearchCount + 1, articles.size());

		searchArticle = articles.get(0);

		Assert.assertEquals(article.getId(), searchArticle.getId());
	}

	protected List<JournalArticle> search() throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return JournalUtil.getArticles(results);
	}

	protected long searchCount() throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	@DeleteAfterTestRun
	private Group _group;

}