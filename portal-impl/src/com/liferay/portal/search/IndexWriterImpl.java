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

package com.liferay.portal.search;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.IndexWriterRequestMessage;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.util.JSONUtil;

import com.metaparadigm.jsonrpc.MarshallException;

/**
 * <a href="IndexWriterImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class IndexWriterImpl implements IndexWriter {

	public void addDocument(long companyId, Document doc)
		throws SearchException {

		try {
			IndexWriterRequestMessage req = new IndexWriterRequestMessage(
				IndexWriterRequestMessage.ADD, companyId, doc);

			MessageBusUtil.sendMessage(
				DestinationNames.SEARCH_INDEX_WRITER_REQUEST,
				JSONUtil.serialize(req));
		}
		catch (MarshallException me) {
			throw new SearchException(me);
		}
	}

	public void deleteDocument(long companyId, String uid)
		throws SearchException {

		try {
			IndexWriterRequestMessage req = new IndexWriterRequestMessage(
				IndexWriterRequestMessage.DELETE, companyId, uid);

			MessageBusUtil.sendMessage(
				DestinationNames.SEARCH_INDEX_WRITER_REQUEST,
				JSONUtil.serialize(req));
		}
		catch (MarshallException me) {
			throw new SearchException(me);
		}
	}

	public void updateDocument(long companyId, String uid, Document doc)
		throws SearchException {

		try {
			IndexWriterRequestMessage req = new IndexWriterRequestMessage(
				IndexWriterRequestMessage.UPDATE, companyId, uid, doc);

			MessageBusUtil.sendMessage(
				DestinationNames.SEARCH_INDEX_WRITER_REQUEST,
				JSONUtil.serialize(req));
		}
		catch (MarshallException me) {
			throw new SearchException(me);
		}
	}

}