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

package com.liferay.portal.search.solr.internal.server;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr.configuration.SolrConfiguration;
import com.liferay.portal.search.solr.http.HttpClientFactory;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrServer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author László Csontos
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr.configuration.SolrConfiguration",
	immediate = true,
	property = {"auth.mode=BASIC", "url=http://localhost:8080/solr"},
	service = SolrServer.class
)
public class HttpSolrServer extends BaseHttpSolrServer {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		if (getHttpSolrServer() != null) {
			shutdown();
		}

		_solrConfiguration = Configurable.createConfigurable(
			SolrConfiguration.class, properties);

		String url = _solrConfiguration.url();

		setUrl(url);

		String authMode = _solrConfiguration.authenticationMode();

		HttpClientFactory httpClientFactory = _httpClientFactories.get(
			authMode);

		if (httpClientFactory == null) {
			throw new IllegalStateException(
				"No HTTP client factory for " + authMode);
		}

		HttpClient httpClient = httpClientFactory.createInstance();

		initHttpSolrServer(httpClient);
	}

	@Deactivate
	protected void deactivate() {
		shutdown();
	}

	@Reference(target = "(type=BASIC)")
	protected void setBasicHttpClientFactory(
		HttpClientFactory httpClientFactory) {

		_httpClientFactories.put("BASIC", httpClientFactory);
	}

	@Reference(target = "(type=CERT)")
	protected void setCertHttpClientFactory(
		HttpClientFactory httpClientFactory) {

		_httpClientFactories.put("CERT", httpClientFactory);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(!(type=BASIC))(!(type=CERT)))"
	)
	protected void setHttpClientFactory(
		HttpClientFactory httpClientFactory,
		Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "type");

		if (Validator.isNull(type)) {
			throw new IllegalArgumentException(
				"Invalid authentication type " + type);
		}

		_httpClientFactories.put(type, httpClientFactory);
	}

	protected void unsetBasicHttpClientFactory(
		HttpClientFactory httpClientFactory) {

		_httpClientFactories.remove("BASIC");
	}

	protected void unsetCertHttpClientFactory(
		HttpClientFactory httpClientFactory) {

		_httpClientFactories.remove("CERT");
	}

	protected void unsetHttpClientFactory(
		HttpClientFactory httpClientFactory,
		Map<String, Object> properties) {

		String auth = MapUtil.getString(properties, "type");

		if (Validator.isNull(auth)) {
			return;
		}

		_httpClientFactories.remove(auth);
	}

	private final Map<String, HttpClientFactory> _httpClientFactories =
		new HashMap<>();
	private volatile SolrConfiguration _solrConfiguration;

}