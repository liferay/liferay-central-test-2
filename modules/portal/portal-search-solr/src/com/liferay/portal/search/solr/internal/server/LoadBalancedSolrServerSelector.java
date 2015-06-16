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

import com.liferay.portal.search.solr.server.SolrServerSelector;

import java.util.List;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SolrServerSelector.class)
public class LoadBalancedSolrServerSelector implements SolrServerSelector {

	@Override
	public SolrServerWrapper select(
		List<SolrServerWrapper> solrServerWrappers) {

		TreeSet<SolrServerWrapper> sortedSolrServerWrappers = new TreeSet<>(
			new SolrServerWrapperComparator());

		sortedSolrServerWrappers.addAll(solrServerWrappers);

		return sortedSolrServerWrappers.first();
	}

}