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

package com.liferay.portal.search.elasticsearch.internal.index;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.Index;
import com.liferay.portal.search.elasticsearch.internal.connection.IndexName;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author Rodrigo Paulino
 * @author André de Oliveira
 */
public class LiferayTypeMappingsDDMKeywordEmptyStringTest {

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		_liferayIndexFixture = new LiferayIndexFixture(
			clazz.getSimpleName(), new IndexName(testName.getMethodName()));

		_liferayIndexFixture.setUp();

		_index = _liferayIndexFixture.getIndex();

		_elasticsearchFixture = _liferayIndexFixture.getElasticsearchFixture();
	}

	@Test
	public void testDDMKeywordEmptyStringInSecondDocument() throws Exception {
		final String field1 = randomDDMKeywordField();
		final String ddmField2 = randomDDMKeywordField();
		final String ddmField3 = randomDDMKeywordField();
		final String ddmField4 = randomDDMKeywordField();
		final String ddmField5 = randomDDMKeywordField();
		final String ddmField6 = randomDDMKeywordField();
		final String ddmField7 = randomDDMKeywordField();
		final String ddmField8 = randomDDMKeywordField();
		final String ddmField9 = randomDDMKeywordField();

		index(
			new HashMap<String, Object>() {
				{
					put(field1, RandomTestUtil.randomString());
					put(ddmField2, StringPool.BLANK);
					put(ddmField3, new Date());
					put(ddmField4, "2011-07-01T01:32:33");
					put(ddmField5, 321231312321L);
					put(ddmField6, "321231312321");
					put(ddmField7, true);
					put(ddmField8, "true");
					put(ddmField9, "NULL");
				}
			});

		index(
			new HashMap<String, Object>() {
				{
					put(field1, StringPool.BLANK);
					put(ddmField2, StringPool.BLANK);
					put(ddmField3, StringPool.BLANK);
					put(ddmField4, StringPool.BLANK);
					put(ddmField5, StringPool.BLANK);
					put(ddmField6, StringPool.BLANK);
					put(ddmField7, StringPool.BLANK);
					put(ddmField8, StringPool.BLANK);
					put(ddmField9, StringPool.BLANK);
				}
			});

		index(
			new HashMap<String, Object>() {
				{
					put(field1, RandomTestUtil.randomString());
					put(ddmField2, RandomTestUtil.randomString());
					put(ddmField3, RandomTestUtil.randomString());
					put(ddmField4, RandomTestUtil.randomString());
					put(ddmField5, String.valueOf(RandomTestUtil.randomLong()));
					put(ddmField6, RandomTestUtil.randomString());
					put(ddmField7, RandomTestUtil.randomString());
					put(ddmField8, RandomTestUtil.randomString());
					put(ddmField9, RandomTestUtil.randomString());
				}
			});

		assertType(field1, "string");
		assertType(ddmField2, "string");
		assertType(ddmField3, "string");
		assertType(ddmField4, "string");
		assertType(ddmField5, "long");
		assertType(ddmField6, "string");
		assertType(ddmField7, "boolean");
		assertType(ddmField8, "string");
		assertType(ddmField9, "string");
	}

	@Rule
	public TestName testName = new TestName();

	protected static String randomDDMKeywordField() {
		return "ddm__keyword__" + RandomTestUtil.randomString();
	}

	protected void assertType(String field, String type) throws Exception {
		FieldMappingAssert.assertType(
			type, field, LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE,
			_index.getName(), _elasticsearchFixture.getIndicesAdminClient());
	}

	protected IndexRequestBuilder getIndexRequestBuilder() {
		Client client = _elasticsearchFixture.getClient();

		IndexRequestBuilder indexRequestBuilder = client.prepareIndex(
			_index.getName(),
			LiferayTypeMappingsConstants.LIFERAY_DOCUMENT_TYPE);

		return indexRequestBuilder;
	}

	protected void index(Map<String, Object> map) {
		IndexRequestBuilder indexRequestBuilder = getIndexRequestBuilder();

		indexRequestBuilder.setSource(map);

		indexRequestBuilder.get();
	}

	private ElasticsearchFixture _elasticsearchFixture;
	private Index _index;
	private LiferayIndexFixture _liferayIndexFixture;

}