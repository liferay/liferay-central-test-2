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

package com.liferay.portal.search.elasticsearch.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Michael C. Han
 */
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration"
)
public interface ElasticsearchConfiguration {

	@Meta.AD(deflt = "", required = false)
	public String[] additionalConfigurations();

	@Meta.AD(deflt = "false", required = false)
	public boolean bootstrapMlockAll();

	@Meta.AD(deflt = "false", required = false)
	public boolean clientTransportIgnoreClusterName();

	@Meta.AD(deflt = "5s", required = false)
	public String clientTransportNodesSamplerInterval();

	@Meta.AD(deflt = "true", required = false)
	public boolean clientTransportSniff();

	@Meta.AD(deflt = "LiferayElasticSearch", required = false)
	public String clusterName();

	@Meta.AD(deflt = "9300-9400", required = false)
	public String discoveryZenPingUnicastHostsPort();

	@Meta.AD(deflt = "", required = false)
	public String[] httpCORSConfigurations();

	@Meta.AD(deflt = "true", required = false)
	public boolean httpCORSEnabled();

	@Meta.AD(deflt = "true", required = false)
	public boolean httpEnabled();

	@Meta.AD(deflt = "", required = false)
	public String networkBindHost();

	@Meta.AD(deflt = "", required = false)
	public String networkHost();

	@Meta.AD(deflt = "", required = false)
	public String networkPublishHost();

	@Meta.AD(
		deflt = "EMBEDDED", optionLabels = {"Embedded", "Remote"},
		optionValues = {"EMBEDDED", "REMOTE"},
		required = false
	)
	public String operationMode();

	@Meta.AD(deflt = "5", required = false)
	public int retryOnConflict();

	@Meta.AD(deflt = "localhost:9300", required = false)
	public String[] transportAddresses();

	@Meta.AD(deflt = "", required = false)
	public String transportTcpPort();

}