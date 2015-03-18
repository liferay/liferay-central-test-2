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

package com.liferay.portal.search.elasticsearch.connection;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;

import java.util.concurrent.Future;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequestBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.unit.TimeValue;

/**
 * @author Michael C. Han
 */
public abstract class BaseElasticsearchConnection
	implements ElasticsearchConnection {

	@Override
	public void close() {
		if (_client != null) {
			_client.close();
		}
	}

	@Override
	public Client getClient() {
		return _client;
	}

	@Override
	public ClusterHealthResponse getClusterHealthResponse(
		long timeout, int nodesCount) {

		AdminClient adminClient = _client.admin();

		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		ClusterHealthRequestBuilder clusterHealthRequestBuilder =
			clusterAdminClient.prepareHealth();

		clusterHealthRequestBuilder.setTimeout(
			TimeValue.timeValueMillis(timeout));

		clusterHealthRequestBuilder.setWaitForGreenStatus();
		clusterHealthRequestBuilder.setWaitForNodes(">" + (nodesCount - 1));

		Future<ClusterHealthResponse> future =
			clusterHealthRequestBuilder.execute();

		try {
			return future.get();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public synchronized void initialize() {
		if (_client != null) {
			return;
		}

		ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();

		loadOptionalDefaultConfigurations(builder);

		if (Validator.isNotNull(
				elasticsearchConfiguration.additionalConfigurations())) {

			builder.loadFromSource(
				elasticsearchConfiguration.additionalConfigurations());
		}

		loadRequiredDefaultConfigurations(builder);

		_client = createClient(builder);
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		_indexFactory = indexFactory;
	}

	protected abstract Client createClient(ImmutableSettings.Builder builder);

	protected IndexFactory getIndexFactory() {
		return _indexFactory;
	}

	protected void loadOptionalDefaultConfigurations(
		ImmutableSettings.Builder builder) {

		try {
			Class<?> clazz = getClass();

			builder.classLoader(clazz.getClassLoader());

			builder.loadFromClasspath(
				"/META-INF/elasticsearch-optional-defaults.yml");
		}
		catch (Exception e) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to load optional default configurations", e);
			}
		}
	}

	protected abstract void loadRequiredDefaultConfigurations(
		ImmutableSettings.Builder builder);

	protected void setClient(Client client) {
		_client = client;
	}

	protected volatile ElasticsearchConfiguration elasticsearchConfiguration;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseElasticsearchConnection.class);

	private Client _client;
	private IndexFactory _indexFactory;

}