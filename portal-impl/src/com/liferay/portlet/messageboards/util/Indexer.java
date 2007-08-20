/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.search.DocumentSummary;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portlet.messageboards.service.jms.IndexProducer;

import java.io.IOException;

import javax.portlet.PortletURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;

/**
 * <a href="Indexer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class Indexer implements com.liferay.portal.kernel.search.Indexer {

	public static void addMessage(
			long companyId, long groupId, String userName, long categoryId,
			long threadId, long messageId, String title, String content)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "addMessage",
				new Object[] {
					new LongWrapper(companyId), new LongWrapper(groupId),
					userName, new LongWrapper(categoryId),
					new LongWrapper(threadId), new LongWrapper(messageId),
					title, content
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public static void deleteMessage(long companyId, long messageId)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "deleteMessage",
				new Object[] {
					new LongWrapper(companyId), new LongWrapper(messageId)
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public static void deleteMessages(long companyId, long threadId)
		throws IOException, ParseException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "deleteMessages",
				new Object[] {
					new LongWrapper(companyId), new LongWrapper(threadId)
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public static void updateMessage(
			long companyId, long groupId, String userName, long categoryId,
			long threadId, long messageId, String title, String content)
		throws IOException {

		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "updateMessage",
				new Object[] {
					new LongWrapper(companyId), new LongWrapper(groupId),
					userName, new LongWrapper(categoryId),
					new LongWrapper(threadId), new LongWrapper(messageId),
					title, content
				});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof IOException) {
				throw (IOException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	public DocumentSummary getDocumentSummary(
		com.liferay.portal.kernel.search.Document doc, PortletURL portletURL) {

		// Title

		String title = doc.get(LuceneFields.TITLE);

		// Content

		String content = doc.get(LuceneFields.CONTENT);

		content = StringUtil.shorten(content, 200);

		// URL

		long messageId = GetterUtil.getLong(doc.get("messageId"));

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter("messageId", String.valueOf(messageId));

		return new DocumentSummary(title, content, portletURL);
	}

	public void reIndex(String[] ids) throws SearchException {
		try {
			MethodWrapper methodWrapper = new MethodWrapper(
				IndexerImpl.class.getName(), "reIndex",
				new Object[] {ids});

			IndexProducer.produce(methodWrapper);
		}
		catch (Exception e) {
			if (e instanceof SearchException) {
				throw (SearchException)e;
			}
			else {
				_log.error(e);
			}
		}
	}

	private static Log _log = LogFactory.getLog(Indexer.class);

}