/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.search.lucene.messaging;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.messaging.BaseDestinationEventListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.search.BooleanQueryFactory;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.TermQueryFactory;
import com.liferay.portal.kernel.search.TermQueryFactoryUtil;
import com.liferay.portal.kernel.search.messaging.BaseSearchEngineMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchReaderMessageListener;
import com.liferay.portal.kernel.search.messaging.SearchWriterMessageListener;

/**
 * <a href="SearchEngineDestinationEventListener.java.html"><b><i>View
 * Source</i></b></a>
 *
 * This destination event listener listens for when other search engines
 * register to receive events.  If another search engine registers to the
 * search reader destination, we will unregister the default engine (Lucene).
 * If the replacement search engine unregisters, we will re-register the
 * default engine (Lucene).
 *
 * The naming convention for search engines should look something like:
 * SOLR_LUCENE, COMPASS_LUCENE, GOOGLE_SEARCH_APPLICANCE, FAST, etc.
 *
 * For search engines that have Lucene as their base, specifying LUCENE in the
 * name will ensure the portal utilizes the appropriate query factories.
 *
 * WARNING: This assumes that the replacing search engine registers both
 * to the DestinationNames.SEACH_READER destination in addition to the
 * DestinationNames.SEARCH_WRITER destination.  This is a safe assumption since
 * you should never have one search engine write and another read. 
 *
 * @author Michael C. Han
 */
public class SearchEngineDestinationEventListener
	extends BaseDestinationEventListener {

	public SearchEngineDestinationEventListener(
		SearchReaderMessageListener searchReaderMessageListener,
		SearchWriterMessageListener searchWriterMessageListener) {

		_defaultSearchReaderMessageListener = searchReaderMessageListener;
		_defaultSearchWriterMessageListener = searchWriterMessageListener;
	}

	@Override
	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {
		if (!_shouldProceed(destinationName, messageListener)) {
			return;
		}

		//we have another search engine registering, go ahead and unregister the default
		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SEARCH_READER, _defaultSearchReaderMessageListener);
		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SEARCH_WRITER, _defaultSearchWriterMessageListener);

		//if the registering search engine is not Lucene based, then replace
		// the query factories
		BaseSearchEngineMessageListener searchEngineMessageListener =
			(BaseSearchEngineMessageListener) messageListener;

		if (!searchEngineMessageListener.getSearchEngineName().
				contains(_LUCENE_SEARCH_ENGINE_STRING)) {

			_setBooleanQueryFactory(
				new com.liferay.portal.search.generic.BooleanQueryFactoryImpl());

			_setTermQueryFactory(
				new com.liferay.portal.search.lucene.TermQueryFactoryImpl());
		}
	}

	@Override
	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {
		if (!_shouldProceed(destinationName, messageListener)) {
			return;
		}
		//we have anothe search engine unregistering, go ahead and register the default
		MessageBusUtil.registerMessageListener(
			DestinationNames.SEARCH_READER, _defaultSearchReaderMessageListener);
		MessageBusUtil.registerMessageListener(
			DestinationNames.SEARCH_WRITER, _defaultSearchWriterMessageListener);

		//if the unregistered search engine is not Lucene based, then replace
		// the query factories
		BaseSearchEngineMessageListener searchEngineMessageListener =
			(BaseSearchEngineMessageListener) messageListener;

		if (!searchEngineMessageListener.getSearchEngineName().
				contains(_LUCENE_SEARCH_ENGINE_STRING)) {

			_setBooleanQueryFactory(
				new com.liferay.portal.search.lucene.BooleanQueryFactoryImpl());

			_setTermQueryFactory(
				new com.liferay.portal.search.lucene.TermQueryFactoryImpl());
		}
	}

	private void _setBooleanQueryFactory(BooleanQueryFactory booleanQueryFactory) {
		BooleanQueryFactoryUtil booleanQueryFactoryUtil =
			(BooleanQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				_BOOLEAN_QUERY_FACTORY_BEAN_NAME);

		booleanQueryFactoryUtil.setBooleanQueryFactory(booleanQueryFactory);
	}

	private void _setTermQueryFactory(TermQueryFactory termQueryFactory) {
		TermQueryFactoryUtil termQueryFactoryUtil =
			(TermQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				_TERM_QUERY_FACTORY_BEAN_NAME);

		termQueryFactoryUtil.setTermQueryFactory(termQueryFactory);
	}


	private boolean _shouldProceed(
		String destinationName, MessageListener messageListener) {

		if (!destinationName.equals(DestinationNames.SEARCH_READER) ||
			(messageListener == _defaultSearchReaderMessageListener) ||
			!(messageListener instanceof SearchReaderMessageListener)) {
			return false;
		}
		return true;
	}

	private static final String _BOOLEAN_QUERY_FACTORY_BEAN_NAME =
		"com.liferay.portal.kernel.search.BooleanQueryFactoryUtil";
	private static final String _LUCENE_SEARCH_ENGINE_STRING = "LUCENE";
	private static final String _TERM_QUERY_FACTORY_BEAN_NAME =
		"com.liferay.portal.kernel.search.TermQueryFactoryUtil";

	private SearchReaderMessageListener _defaultSearchReaderMessageListener;
	private SearchWriterMessageListener _defaultSearchWriterMessageListener;
}
