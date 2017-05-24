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

package com.liferay.portal.search.web.internal.search.results.portlet;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.web.internal.display.context.PortletURLFactory;
import com.liferay.portal.search.web.internal.portlet.shared.task.PortletSharedRequestHelper;
import com.liferay.portal.search.web.internal.result.display.builder.AssetRendererFactoryLookup;
import com.liferay.portal.search.web.internal.search.results.constants.SearchResultsWebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchRequest;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.io.IOException;

import java.util.Arrays;
import java.util.Optional;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class SearchResultsPortletTest extends SearchResultsPortlet {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpHtmlUtil();
		setUpPortletSharedSearchResponse();
		setUpProps();

		_portletURLFactory = createPortletURLFactory();
		_renderRequest = createRenderRequest();
		_searchResultsPortlet = createSearchResultsPortlet();
	}

	@Test
	public void testDocumentWithoutSummaryIsRemoved() throws Exception {
		Document document = createDocumentWithSummary();

		setUpSearchResponseDocuments(document, new DocumentImpl());

		render();

		assertDisplayContextDocuments(document);
	}

	protected void assertDisplayContextDocuments(
		Document... expectedDocuments) {

		SearchResultsPortletDisplayContext searchResultsPortletDisplayContext =
			_getDisplayContext();

		Assert.assertEquals(
			Arrays.asList(expectedDocuments),
			searchResultsPortletDisplayContext.getDocuments());
	}

	protected Document createDocumentWithSummary() throws Exception {
		Document document = new DocumentImpl();

		String className = RandomTestUtil.randomString();

		document.addKeyword(Field.ENTRY_CLASS_NAME, className);

		Mockito.doReturn(
			_createIndexerWithSummary()
		).when(
			_indexerRegistry
		).getIndexer(
			className
		);

		return document;
	}

	protected PortletSharedSearchRequest createPortletSharedSearchRequest() {
		PortletSharedSearchRequest portletSharedSearchRequest = Mockito.mock(
			PortletSharedSearchRequest.class);

		Mockito.doReturn(
			_portletSharedSearchResponse
		).when(
			portletSharedSearchRequest
		).search(
			Mockito.any()
		);

		return portletSharedSearchRequest;
	}

	protected PortletURLFactory createPortletURLFactory() throws Exception {
		PortletURLFactory portletURLFactory = Mockito.mock(
			PortletURLFactory.class);

		Mockito.doReturn(
			Mockito.mock(PortletURL.class)
		).when(
			portletURLFactory
		).getPortletURL();

		return portletURLFactory;
	}

	protected RenderRequest createRenderRequest() {
		RenderRequest renderRequest = Mockito.mock(RenderRequest.class);

		Mockito.doReturn(
			RandomTestUtil.randomString()
		).when(
			renderRequest
		).getParameter(
			"mvcPath"
		);

		Mockito.doReturn(
			RenderRequest.RENDER_MARKUP
		).when(
			renderRequest
		).getAttribute(
			RenderRequest.RENDER_PART
		);

		return renderRequest;
	}

	protected SearchResultsPortlet createSearchResultsPortlet()
		throws Exception {

		SearchResultsPortlet searchResultsPortlet = new SearchResultsPortlet() {
			{
				assetEntryLocalService = Mockito.mock(
					AssetEntryLocalService.class);
				assetRendererFactoryLookup = Mockito.mock(
					AssetRendererFactoryLookup.class);
				http = Mockito.mock(Http.class);
				indexerRegistry = _indexerRegistry;
				portletSharedRequestHelper = Mockito.mock(
					PortletSharedRequestHelper.class);
				portletSharedSearchRequest = createPortletSharedSearchRequest();
				resourceActions = Mockito.mock(ResourceActions.class);
			}

			@Override
			public void init() {
			}

			@Override
			protected void doDispatch(
				RenderRequest renderRequest, RenderResponse renderResponse) {
			}

			@Override
			protected HttpServletRequest getHttpServletRequest(
				RenderRequest renderRequest) {

				return Mockito.mock(HttpServletRequest.class);
			}

			@Override
			protected PortletURLFactory getPortletURLFactory(
				RenderRequest renderRequest, RenderResponse renderResponse) {

				return _portletURLFactory;
			}

		};

		searchResultsPortlet.init(Mockito.mock(LiferayPortletConfig.class));

		return searchResultsPortlet;
	}

	protected void render() throws IOException, PortletException {
		_searchResultsPortlet.render(_renderRequest, null);
	}

	protected void setUpHtmlUtil() throws Exception {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(Mockito.mock(Html.class));
	}

	protected void setUpPortletSharedSearchResponse() {
		Mockito.doReturn(
			Optional.empty()
		).when(
			_portletSharedSearchResponse
		).getKeywords();

		Mockito.doReturn(
			Optional.empty()
		).when(
			_portletSharedSearchResponse
		).getPortletPreferences(
			Mockito.any()
		);

		Mockito.doReturn(
			new ThemeDisplay()
		).when(
			_portletSharedSearchResponse
		).getThemeDisplay(
			Mockito.any()
		);
	}

	protected void setUpProps() {
		PropsUtil.setProps(Mockito.mock(Props.class));
	}

	protected void setUpSearchResponseDocuments(Document... documents) {
		Mockito.doReturn(
			Arrays.asList(documents)
		).when(
			_portletSharedSearchResponse
		).getDocuments();
	}

	private Indexer<?> _createIndexerWithSummary() throws SearchException {
		Indexer<?> indexer = Mockito.mock(Indexer.class);

		Mockito.doReturn(
			new Summary(null, null, null)
		).when(
			indexer
		).getSummary(
			Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any()
		);

		return indexer;
	}

	private SearchResultsPortletDisplayContext _getDisplayContext() {
		ArgumentCaptor<SearchResultsPortletDisplayContext> argumentCaptor =
			ArgumentCaptor.forClass(SearchResultsPortletDisplayContext.class);

		Mockito.verify(
			_renderRequest
		).setAttribute(
			Matchers.eq(SearchResultsWebKeys.DISPLAY_CONTEXT),
			argumentCaptor.capture()
		);

		return argumentCaptor.getValue();
	}

	@Mock
	private IndexerRegistry _indexerRegistry;

	@Mock
	private PortletSharedSearchResponse _portletSharedSearchResponse;

	private PortletURLFactory _portletURLFactory;
	private RenderRequest _renderRequest;
	private SearchResultsPortlet _searchResultsPortlet;

}