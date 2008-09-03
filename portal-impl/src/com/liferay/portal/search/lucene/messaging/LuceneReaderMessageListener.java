/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageTypes;
import com.liferay.portal.kernel.search.messaging.SearchRequest;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.lucene.LuceneSearchEngineUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LuceneReaderMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LuceneReaderMessageListener implements MessageListener {


	public void receive(Message message) {
		String replyTo = message.getReplyTo();
		String messageId = message.getMessageId();
		if (Validator.isNull(replyTo) || Validator.isNull(messageId)) {
			return;
		}
		SearchRequest searchRequest = (SearchRequest)message.getPayload();
		String command = searchRequest.getCommand();
		if (!command.equals(SearchRequest.COMMAND_INDEX_ONLY) &&
			!command.equals(SearchRequest.COMMAND_SEARCH)) {
			return;
		}
		try {
			Object payload = null;
			if (command.equals(SearchRequest.COMMAND_INDEX_ONLY)) {
				payload = doCommandIndexOnly(searchRequest);
			}
			else if (command.equals(SearchRequest.COMMAND_SEARCH)) {
				payload = doCommandSearch(searchRequest);
			}
			Message reply = new Message(MessageTypes.INDEX_READER_MESSAGE);
			reply.setDestination(replyTo);
			reply.setMessageId(messageId);
			reply.setPayload(payload);
			MessageBusUtil.sendMessage(replyTo, message);

		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}


	protected Object doCommandIndexOnly(SearchRequest searchRequest)
		throws Exception {

		return Boolean.valueOf(LuceneSearchEngineUtil.isIndexReadOnly());

	}

	protected Object doCommandSearch(SearchRequest searchRequest)
		throws Exception {

		return LuceneSearchEngineUtil.search(
			searchRequest.getCompanyId(), searchRequest.getQuery(),
			searchRequest.getSort(), searchRequest.getStart(),
			searchRequest.getEnd());

	}

	private static Log _log =
		LogFactory.getLog(LuceneReaderMessageListener.class);

}