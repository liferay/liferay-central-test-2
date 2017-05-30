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

package com.liferay.portal.search.solr.internal.facet;

import com.liferay.portal.search.solr.internal.SolrIndexingFixture;
import com.liferay.portal.search.test.util.facet.BaseAssetTagNamesFacetTestCase;
import com.liferay.portal.search.test.util.indexing.IndexingFixture;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class AssetTagNamesFacetTest extends BaseAssetTagNamesFacetTestCase {

	@Override
	@Test
	public void testCaseInsensitiveSearchCaseSensitiveGrouping()
		throws Exception {

		super.testCaseInsensitiveSearchCaseSensitiveGrouping();
	}

	@Override
	@Test
	public void testKeysWithSpaces() throws Exception {
		super.testKeysWithSpaces();
	}

	@Override
	protected IndexingFixture createIndexingFixture() throws Exception {
		return new SolrIndexingFixture();
	}

}