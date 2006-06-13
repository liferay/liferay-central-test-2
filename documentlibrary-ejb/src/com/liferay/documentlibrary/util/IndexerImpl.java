/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.documentlibrary.util;

import com.liferay.documentlibrary.service.impl.DLServiceImpl;
import com.liferay.documentlibrary.service.spring.DLLocalServiceUtil;
import com.liferay.portal.jcr.JCRConstants;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.util.StringPool;
import com.liferay.util.lucene.IndexerException;

import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

/**
 * <a href="IndexerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class IndexerImpl {

	public static void addFile(
			String companyId, String portletId, String groupId,
			String repositoryId, String fileName)
		throws IOException {

		synchronized (IndexWriter.class) {
			_log.debug(
				"Indexing document " + companyId + " " + portletId + " " +
					groupId + " " + repositoryId + " " + fileName);

			String fileExt = fileName;

			int fileExtPos = fileExt.indexOf(DLServiceImpl.VERSION);

			if (fileExtPos != -1) {
				fileExt = fileExt.substring(
					fileExt.lastIndexOf(StringPool.PERIOD, fileExtPos),
						fileExtPos);
			}
			else {
				fileExt = fileExt.substring(
					fileExt.lastIndexOf(StringPool.PERIOD), fileExt.length());
			}

			InputStream is = null;

			Session session = null;

			try {
				session = JCRFactoryUtil.createSession();

				Node contentNode = DLLocalServiceUtil.getFileContentNode(
					session, companyId, repositoryId, fileName, 0);

				is = contentNode.getProperty(JCRConstants.JCR_DATA).getStream();
			}
			catch (Exception e) {
			}
			finally {
				if (session != null) {
					session.logout();
				}
			}

			if (is == null) {
				_log.debug(
					"Document " + companyId + " " + portletId + " " + groupId +
						" " + repositoryId + " " + fileName +
							" does not have any content");

				return;
			}

			IndexWriter writer = LuceneUtil.getWriter(companyId);

			Document doc = new Document();

			doc.add(
				LuceneFields.getKeyword(
					LuceneFields.UID,
					LuceneFields.getUID(portletId, repositoryId, fileName)));

			doc.add(LuceneFields.getKeyword(
				LuceneFields.COMPANY_ID, companyId));
			doc.add(LuceneFields.getKeyword(
				LuceneFields.PORTLET_ID, portletId));
			doc.add(LuceneFields.getKeyword(LuceneFields.GROUP_ID, groupId));

			doc.add(LuceneFields.getFile(LuceneFields.CONTENT, is, fileExt));

			doc.add(LuceneFields.getDate(LuceneFields.MODIFIED));

			doc.add(LuceneFields.getKeyword("repositoryId", repositoryId));
			doc.add(LuceneFields.getKeyword("path", fileName));

			writer.addDocument(doc);

			LuceneUtil.write(writer);

			_log.debug(
				"Document " + companyId + " " + portletId + " " + groupId +
					" " + repositoryId + " " + fileName +
						" indexed successfully");
		}
	}

	public synchronized static void deleteFile(
			String companyId, String portletId, String repositoryId,
			String fileName)
		throws IOException {

		synchronized (IndexWriter.class) {
			IndexReader reader = LuceneUtil.getReader(companyId);

			reader.deleteDocuments(
				new Term(
					LuceneFields.UID,
					LuceneFields.getUID(portletId, repositoryId, fileName)));

			reader.close();
		}
	}

	public static void reIndex(String[] ids) throws IndexerException {
		String companyId = ids[0];
		String portletId = ids[1];
		String groupId = ids[2];
		String repositoryId = ids[3];

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);

			NodeIterator itr = repositoryNode.getNodes();

			while (itr.hasNext()) {
				Node node = (Node)itr.next();

				if (node.getPrimaryNodeType().getName().equals(
						JCRConstants.NT_FILE)) {

					addFile(
						companyId, portletId, groupId, repositoryId,
						node.getName());
				}
			}
		}
		catch (Exception e) {
			throw new IndexerException(e);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public synchronized static void updateFile(
			String companyId, String portletId, String groupId,
			String repositoryId, String fileName)
		throws IOException {

		try {
			deleteFile(companyId, portletId, repositoryId, fileName);
		}
		catch (IOException ioe) {
		}

		addFile(companyId, portletId, groupId, repositoryId, fileName);
	}

	private static Log _log = LogFactory.getLog(IndexerImpl.class);

}