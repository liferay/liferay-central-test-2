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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration",
	immediate = true, service = ElasticsearchConnectionManager.class
)
public class ElasticsearchConnectionManager {

	public AdminClient getAdminClient() {
		Client client = getClient();

		return client.admin();
	}

	public Client getClient() {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		if (elasticsearchConnection == null) {
			throw new IllegalStateException(
				"Elasticsearch connection not initialized");
		}

		return elasticsearchConnection.getClient();
	}

	public ClusterAdminClient getClusterAdminClient() {
		AdminClient adminClient = getAdminClient();

		return adminClient.cluster();
	}

	public ClusterHealthResponse getClusterHealthResponse(
		long timeout, int nodesCount) {

		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		return elasticsearchConnection.getClusterHealthResponse(
			timeout, nodesCount);
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnections.get(_operationMode);
	}

	@Reference(cardinality = ReferenceCardinality.AT_LEAST_ONE)
	public void setElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.put(
			elasticsearchConnection.getOperationMode(),
			elasticsearchConnection);
	}

	public void unsetElasticsearchConnection(
		ElasticsearchConnection elasticsearchConnection) {

		_elasticsearchConnections.remove(
			elasticsearchConnection.getOperationMode());

		elasticsearchConnection.close();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_elasticsearchConfiguration = Configurable.createConfigurable(
			ElasticsearchConfiguration.class, properties);

		OperationMode newOperationMode = OperationMode.valueOf(
			_elasticsearchConfiguration.operationMode());

		if (newOperationMode.equals(_operationMode)) {
			return;
		}

		if (_operationMode != null) {
			ElasticsearchConnection elasticsearchConnection =
				_elasticsearchConnections.get(_operationMode);

			elasticsearchConnection.close();
		}

		_operationMode = newOperationMode;

		ElasticsearchConnection newElasticsearchConnection =
			_elasticsearchConnections.get(_operationMode);

		newElasticsearchConnection.initialize();
	}

	private volatile ElasticsearchConfiguration _elasticsearchConfiguration;
	private final Map<OperationMode, ElasticsearchConnection>
		_elasticsearchConnections = new HashMap<>();
	private OperationMode _operationMode;

}