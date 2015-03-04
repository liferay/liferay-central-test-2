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

package com.liferay.portal.search.test;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.DDMFormValuesReader;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.service.persistence.test.AssetEntryQueryTestUtil;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.Value;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Assume;

/**
 * @author Preston Crary
 * @author André de Oliveira
 */
public abstract class TestOrderHelper {

	public void testOrderByDDMBooleanField() throws Exception {
		testOrderByDDMField(
			new String[] {"false", "true", "false", "true"},
			new String[] {"false", "false", "true", "true"}, "boolean",
			"checkbox");
	}

	public void testOrderByDDMIntegerField() throws Exception {
		testOrderByDDMField(
			new String[] {"1", "10", "3", "2"},
			new String[] {"1", "2", "3", "10"}, "integer", "ddm-integer");
	}

	public void testOrderByDDMNumberField() throws Exception {
		Assume.assumeTrue(isOrderByDDMNumberFieldImplementedForSearchEngine());

		testOrderByDDMField(
			new String[] {"3", "3.14", "12.34", "2.72", "1.41", "23.45", "20"},
			new String[] {"1.41", "2.72", "3", "3.14", "12.34", "20", "23.45"},
			"number", "ddm-number");
	}

	public void testOrderByDDMTextField() throws Exception {
		testOrderByDDMField(
			new String[] {"A", "D", "C", "B"},
			new String[] {"A", "B", "C", "D"}, "string", "text");
	}

	protected TestOrderHelper(Group group) throws Exception {
		_group = group;

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());
	}

	protected DDMStructure addDDMStructure(String dataType, String type)
		throws Exception {

		String definition = DDMStructureTestUtil.getSampleStructureDefinition(
			"name", dataType, false, type, new Locale[] {LocaleUtil.US},
			LocaleUtil.US);

		return DDMStructureTestUtil.addStructure(
			_serviceContext.getScopeGroupId(),
			getSearchableAssetEntryStructureClassName(), definition);
	}

	protected DDMTemplate addDDMTemplate(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateTestUtil.addTemplate(
			_serviceContext.getScopeGroupId(), ddmStructure.getStructureId());
	}

	protected void addSearchableAssetEntries(
			String[] unsortedValues, DDMStructure ddmStructure,
			DDMTemplate ddmTemplate)
		throws Exception {

		BaseModel<?> parentBaseModel = getSearchableAssetEntryParentBaseModel(
			_group, _serviceContext);

		for (String unsortedValue : unsortedValues) {
			addSearchableAssetEntry(
				parentBaseModel, unsortedValue, ddmStructure, ddmTemplate,
				_serviceContext);
		}
	}

	protected abstract BaseModel<?> addSearchableAssetEntry(
			BaseModel<?> parentBaseModel, String keywords,
			DDMStructure ddmStructure, DDMTemplate ddmTemplate,
			ServiceContext serviceContext)
		throws Exception;

	protected void assertArrayEquals(
			String[] sortedValues, AssetEntryQuery assetEntryQuery,
			AssetRendererFactory assetRendererFactory)
		throws Exception {

		Hits hits = search(assetEntryQuery);

		List<AssetEntry> assetEntries = AssetUtil.getAssetEntries(hits);

		String[] values = new String[assetEntries.size()];

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				assetEntry.getClassPK());

			values[i] = getValue(assetRenderer);
		}

		Assert.assertArrayEquals(sortedValues, values);
	}

	protected AssetEntryQuery createAssetEntryQuery(DDMStructure ddmStructure)
		throws Exception {

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group.getGroupId(),
				new String[] {getSearchableAssetEntryClassName()});

		String orderByCol1 = DDMIndexerUtil.encodeName(
			ddmStructure.getStructureId(), "name");

		assetEntryQuery.setOrderByCol1(orderByCol1);
		assetEntryQuery.setOrderByType1("asc");

		return assetEntryQuery;
	}

	protected abstract String getSearchableAssetEntryClassName();

	protected abstract BaseModel<?> getSearchableAssetEntryParentBaseModel(
		Group group, ServiceContext serviceContext) throws Exception;

	protected abstract String getSearchableAssetEntryStructureClassName();

	protected boolean isOrderByDDMNumberFieldImplementedForSearchEngine() {
		SearchEngine searchEngine = SearchEngineUtil.getSearchEngine(
			SearchEngineUtil.getDefaultSearchEngineId());

		String vendor = searchEngine.getVendor();

		if (vendor.equals("Elasticsearch") || vendor.equals("SOLR")) {
			return true;
		}

		return false;
	}

	protected Hits search(AssetEntryQuery assetEntryQuery) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext();

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		return AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	protected void testOrderByDDMField(
			String[] unsortedValues, final String[] sortedValues,
			String dataType, String type)
		throws Exception {

		DDMStructure ddmStructure = addDDMStructure(dataType, type);

		DDMTemplate ddmTemplate = addDDMTemplate(ddmStructure);

		addSearchableAssetEntries(unsortedValues, ddmStructure, ddmTemplate);

		final AssetEntryQuery assetEntryQuery = createAssetEntryQuery(
			ddmStructure);

		final AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				getSearchableAssetEntryClassName());

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					assertArrayEquals(
						sortedValues, assetEntryQuery, assetRendererFactory);

					return null;
				}

			});
	}

	private String getValue(AssetRenderer assetRenderer) throws Exception {
		DDMFormValuesReader ddmFormValuesReader =
			assetRenderer.getDDMFormValuesReader();

		DDMFormValues ddmFormValues = ddmFormValuesReader.getDDMFormValues();

		Map<String, List<DDMFormFieldValue>> map =
			ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> list = map.get("name");

		DDMFormFieldValue ddmFormFieldValue = list.get(0);

		Value value = ddmFormFieldValue.getValue();

		return value.getString(LocaleUtil.getDefault());
	}

	private final Group _group;
	private final ServiceContext _serviceContext;

}