/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.search.lucene.LuceneSearchEngineImpl;

/**
 * <a href="SearchEngineDestinationEventListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Michael C. Han
 */
public class SearchEngineDestinationEventListener
	extends BaseDestinationEventListener {

	public SearchEngineDestinationEventListener() {
	}

	/**
	 * @deprecated
	 */
	public SearchEngineDestinationEventListener(
		SearchReaderMessageListener searchReaderMessageListener,
		SearchWriterMessageListener searchWriterMessageListener) {

		_searchReaderMessageListener = searchReaderMessageListener;
		_searchWriterMessageListener = searchWriterMessageListener;
	}

	public void messageListenerRegistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SEARCH_READER, _searchReaderMessageListener);
		MessageBusUtil.unregisterMessageListener(
			DestinationNames.SEARCH_WRITER, _searchWriterMessageListener);

		BaseSearchEngineMessageListener baseSearchEngineMessageListener =
			(BaseSearchEngineMessageListener)messageListener;

		if (!baseSearchEngineMessageListener.getSearchEngineName().contains(
				LuceneSearchEngineImpl.NAME)) {

			setBooleanQueryFactory(
				new com.liferay.portal.search.generic.
					BooleanQueryFactoryImpl());
			setTermQueryFactory(
				new com.liferay.portal.search.generic.TermQueryFactoryImpl());
		}
	}

	public void messageListenerUnregistered(
		String destinationName, MessageListener messageListener) {

		if (!isProceed(destinationName, messageListener)) {
			return;
		}

		MessageBusUtil.registerMessageListener(
			DestinationNames.SEARCH_READER, _searchReaderMessageListener);
		MessageBusUtil.registerMessageListener(
			DestinationNames.SEARCH_WRITER, _searchWriterMessageListener);

		BaseSearchEngineMessageListener baseSearchEngineMessageListener =
			(BaseSearchEngineMessageListener)messageListener;

		if (!baseSearchEngineMessageListener.getSearchEngineName().contains(
				LuceneSearchEngineImpl.NAME)) {

			setBooleanQueryFactory(
				new com.liferay.portal.search.lucene.BooleanQueryFactoryImpl());
			setTermQueryFactory(
				new com.liferay.portal.search.lucene.TermQueryFactoryImpl());
		}
	}

	public void setSearchReaderMessageListener(
		SearchReaderMessageListener searchReaderMessageListener) {

		_searchReaderMessageListener = searchReaderMessageListener;
	}

	public void setSearchWriterMessageListener(
		SearchWriterMessageListener searchWriterMessageListener) {

		_searchWriterMessageListener = searchWriterMessageListener;
	}

	protected boolean isProceed(
		String destinationName, MessageListener messageListener) {

		if ((!destinationName.equals(DestinationNames.SEARCH_READER)) ||
			(messageListener == _searchReaderMessageListener) ||
			!(messageListener instanceof SearchReaderMessageListener)) {

			return false;
		}
		else {
			return true;
		}
	}

	protected void setBooleanQueryFactory(
		BooleanQueryFactory booleanQueryFactory) {

		BooleanQueryFactoryUtil booleanQueryFactoryUtil =
			(BooleanQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				BooleanQueryFactoryUtil.class.getName());

		booleanQueryFactoryUtil.setBooleanQueryFactory(booleanQueryFactory);
	}

	protected void setTermQueryFactory(TermQueryFactory termQueryFactory) {
		TermQueryFactoryUtil termQueryFactoryUtil =
			(TermQueryFactoryUtil)PortalBeanLocatorUtil.locate(
				TermQueryFactoryUtil.class.getName());

		termQueryFactoryUtil.setTermQueryFactory(termQueryFactory);
	}

	private SearchReaderMessageListener _searchReaderMessageListener;
	private SearchWriterMessageListener _searchWriterMessageListener;

}