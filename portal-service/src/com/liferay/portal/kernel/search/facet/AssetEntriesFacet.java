/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.facet;

import com.liferay.documentlibrary.model.FileModel;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.AssetEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class AssetEntriesFacet extends MultiValueFacet {

	public AssetEntriesFacet(SearchContext searchContext) {
		super(searchContext);

		setFieldName(Field.ENTRY_CLASS_NAME);
		initFacetClause();
	}

	public void setFacetConfiguration(FacetConfiguration facetConfiguration) {
		super.setFacetConfiguration(facetConfiguration);

		initFacetClause();
	}

	protected BooleanClause doGetFacetClause() {
		SearchContext searchContext = getSearchContext();

		String[] entryClassNames = searchContext.getEntryClassNames();

		BooleanQuery facetQuery = BooleanQueryFactoryUtil.create();

		for (String entryClassName : entryClassNames) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(entryClassName);

			if (indexer == null) {
				continue;
			}

			try {
				BooleanQuery indexerQuery = indexer.getFacetQuery(
					entryClassName, searchContext);

				if ((indexerQuery == null ) ||
					(indexerQuery.clauses().isEmpty())) {

					continue;
				}

				indexer.postProcessContextQuery(indexerQuery, searchContext);

				for (IndexerPostProcessor indexerPostProcessor :
						indexer.getIndexerPostProcessors()) {

					indexerPostProcessor.postProcessContextQuery(
						indexerQuery, searchContext);
				}

				facetQuery.add(indexerQuery, BooleanClauseOccur.SHOULD);

				if (!indexer.isStagingAware()) {
					continue;
				}

				if (!searchContext.isIncludeLiveGroups() &&
					searchContext.isIncludeStagingGroups()) {

					facetQuery.addRequiredTerm(Field.STAGING_GROUP, true);
				}
				else if (searchContext.isIncludeLiveGroups() &&
						!searchContext.isIncludeStagingGroups()) {

					facetQuery.addRequiredTerm(Field.STAGING_GROUP, false);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (facetQuery.clauses().isEmpty()) {
			return null;
		}

		return BooleanClauseFactoryUtil.create(
			facetQuery, BooleanClauseOccur.MUST.getName());
	}

	protected void initFacetClause() {
		SearchContext searchContext = getSearchContext();
		FacetConfiguration facetConfiguration = getFacetConfiguration();
		JSONObject data = facetConfiguration.getData();

		String[] entryClassNames = null;

		if (data.has("values")) {
			JSONArray valuesJSONArray = data.getJSONArray("values");

			entryClassNames = new String[valuesJSONArray.length()];

			for (int i = 0; i < valuesJSONArray.length(); i++) {
				entryClassNames[i] = valuesJSONArray.getString(i);
			}
		}

		if ((entryClassNames == null) || (entryClassNames.length == 0)) {
			entryClassNames = searchContext.getEntryClassNames();
		}

		if (!isStatic()) {
			String[] entryClassNameParam = StringUtil.split(
				GetterUtil.getString(
					searchContext.getAttribute(getFieldName())));

			if ((entryClassNameParam != null) &&
				(entryClassNameParam.length > 0)) {

				entryClassNames = entryClassNameParam;
			}
		}

		if ((entryClassNames == null) || (entryClassNames.length == 0)) {
			List<String> entryClassNamesList = new ArrayList<String>();

			for (Indexer indexer : IndexerRegistryUtil.getIndexers()) {
				for (String className : indexer.getClassNames()) {
					if (!entryClassNamesList.contains(className) &&
						!className.equals(AssetEntry.class.getName()) &&
						!className.equals(FileModel.class.getName()) &&
						!className.equals(PluginPackage.class.getName())) {

						entryClassNamesList.add(className);
					}
				}
			}

			entryClassNames = entryClassNamesList.toArray(
				new String[entryClassNamesList.size()]);

			if (!facetConfiguration.getData().has("values")) {
				JSONArray entriesJSONArray = JSONFactoryUtil.createJSONArray();

				for (String entryClassName : entryClassNames) {
					entriesJSONArray.put(entryClassName);
				}

				facetConfiguration.getData().put(
					"values", entriesJSONArray);
			}
		}

		searchContext.setEntryClassNames(entryClassNames);
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntriesFacet.class);

	private BooleanClause _facetClause;

}