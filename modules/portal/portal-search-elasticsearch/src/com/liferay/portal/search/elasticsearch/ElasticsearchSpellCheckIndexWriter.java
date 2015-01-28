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
import com.liferay.portal.kernel.search.BaseGenericSpellCheckIndexWriter;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SpellCheckIndexWriter;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.util.DocumentTypes;
import com.liferay.portal.search.elasticsearch.util.LogUtil;

import java.util.Collection;
import java.util.concurrent.Future;

import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = SpellCheckIndexWriter.class)
public class ElasticsearchSpellCheckIndexWriter
	extends BaseGenericSpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		try {
			deleteIndices(searchContext, DocumentTypes.KEYWORD_QUERY);
		}
		catch (Exception e) {
			throw new SearchException("Unable to clear query suggestions", e);
		}
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		try {
			deleteIndices(searchContext, DocumentTypes.SPELL_CHECK);
		}
		catch (Exception e) {
			throw new SearchException("Unable to to clear spell checks", e);
		}
	}

	@Reference
	public void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	@Reference
	public void setElasticsearchUpdateDocumentCommand(
		ElasticsearchUpdateDocumentCommand elasticsearchUpdateDocumentCommand) {

		_elasticsearchUpdateDocumentCommand =
			elasticsearchUpdateDocumentCommand;
	}

	@Override
	protected void addDocument(
			String documentType, SearchContext searchContext, Document document)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocument(
			documentType, searchContext, document, false);
	}

	@Override
	protected void addDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents)
		throws SearchException {

		_elasticsearchUpdateDocumentCommand.updateDocuments(
			documentType, searchContext, documents, false);
	}

	@Override
	protected Document createDocument(
		long companyId, long groupId, String languageId, String keywords,
		float weight, String keywordFieldName, String typeFieldValue,
		int maxNGramLength) {

		Document document = createDocument();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);

		String localizedName = DocumentImpl.getLocalizedName(
			languageId, keywordFieldName);

		document.addKeyword(localizedName, keywords);

		document.addKeyword(Field.PRIORITY, String.valueOf(weight));
		document.addKeyword(Field.SPELL_CHECK_WORD, true);
		document.addKeyword(Field.UID, getUID(companyId, languageId, keywords));

		return document;
	}

	protected void deleteIndices(SearchContext searchContext, String indexType)
		throws Exception {

		Client client = _elasticsearchConnectionManager.getClient();

		DeleteByQueryRequestBuilder deleteByQueryRequestBuilder =
			client.prepareDeleteByQuery(
				String.valueOf(searchContext.getCompanyId()));

		deleteByQueryRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
		deleteByQueryRequestBuilder.setTypes(indexType);

		Future<DeleteByQueryResponse> deleteByQueryRequestFuture =
			deleteByQueryRequestBuilder.execute();

		DeleteByQueryResponse deleteByQueryResponse =
			deleteByQueryRequestFuture.get();

		LogUtil.logActionResponse(_log, deleteByQueryResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSpellCheckIndexWriter.class);

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private ElasticsearchUpdateDocumentCommand
		_elasticsearchUpdateDocumentCommand;

}