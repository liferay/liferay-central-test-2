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

package com.liferay.portal.search.elasticsearch;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.search.AbstractSearchEngineConfigurator;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;

import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;

/**
 * @author Michael C. Han
 */
public class ElasticsearchEngineConfigurator
	extends AbstractSearchEngineConfigurator {

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		elasticsearchConnection.initialize();

		Client client = elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();

		try {
			_indexFactory.createIndices(adminClient);
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		elasticsearchConnection.close();
	}

	public void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		_indexFactory = indexFactory;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		_indexSearcher = indexSearcher;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	@Override
	protected String getDefaultSearchEngineId() {
		return SearchEngineUtil.SYSTEM_ENGINE_ID;
	}

	@Override
	protected IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	protected IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	protected MessageBus getMessageBus() {
		return _messageBus;
	}

	@Override
	protected ClassLoader getOperatingClassloader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private static Log _log = LogFactoryUtil.getLog(
		ElasticsearchEngineConfigurator.class);

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private IndexFactory _indexFactory;
	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private MessageBus _messageBus;

}