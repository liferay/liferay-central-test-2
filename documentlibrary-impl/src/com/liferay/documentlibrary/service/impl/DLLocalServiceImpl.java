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

package com.liferay.documentlibrary.service.impl;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.NoSuchFileException;
import com.liferay.documentlibrary.SourceFileNameException;
import com.liferay.documentlibrary.service.DLLocalService;
import com.liferay.documentlibrary.util.DLUtil;
import com.liferay.documentlibrary.util.Indexer;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.jcr.JCRConstants;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.lucene.LuceneFields;
import com.liferay.portal.lucene.LuceneUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.lucene.HitsImpl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Calendar;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.version.Version;

import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TermQuery;

/**
 * <a href="DLLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DLLocalServiceImpl implements DLLocalService {

	public static final double DEFAULT_VERSION = 1.0;

	public void addFile(
			long companyId, String portletId, long groupId, String repositoryId,
			String fileName, InputStream is)
		throws PortalException, SystemException {

		if ((fileName.indexOf("\\\\") != -1) ||
			(fileName.indexOf("//") != -1) ||
			(fileName.indexOf(":") != -1) ||
			(fileName.indexOf("*") != -1) ||
			(fileName.indexOf("?") != -1) ||
			(fileName.indexOf("\"") != -1) ||
			(fileName.indexOf("<") != -1) ||
			(fileName.indexOf(">") != -1) ||
			(fileName.indexOf("|") != -1) ||
			(fileName.indexOf("&") != -1) ||
			(fileName.indexOf("[") != -1) ||
			(fileName.indexOf("]") != -1) ||
			(fileName.indexOf("'") != -1)) {

			throw new FileNameException(fileName);
		}

		boolean validFileExtension = false;

		String[] fileExtensions =
			PropsUtil.getArray(PropsUtil.DL_FILE_EXTENSIONS);

		for (int i = 0; i < fileExtensions.length; i++) {
			if (StringPool.STAR.equals(fileExtensions[i]) ||
				StringUtil.endsWith(fileName, fileExtensions[i])) {

				validFileExtension = true;

				break;
			}
		}

		if (!validFileExtension) {
			throw new FileNameException(fileName);
		}

		if (is == null) {
			throw new FileSizeException(fileName);
		}

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);

			if (repositoryNode.hasNode(fileName)) {
				throw new DuplicateFileException(fileName);
			}
			else {
				Node fileNode = repositoryNode.addNode(
					fileName, JCRConstants.NT_FILE);

				Node contentNode = fileNode.addNode(
					JCRConstants.JCR_CONTENT, JCRConstants.NT_RESOURCE);

				contentNode.addMixin(JCRConstants.MIX_VERSIONABLE);
				contentNode.setProperty(
					JCRConstants.JCR_MIME_TYPE, "text/plain");
				contentNode.setProperty(JCRConstants.JCR_DATA, is);
				contentNode.setProperty(
					JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

				session.save();

				Version version = contentNode.checkin();

				contentNode.getVersionHistory().addVersionLabel(
					version.getName(), Double.toString(DEFAULT_VERSION), false);

				Indexer.addFile(
					companyId, portletId, groupId, repositoryId, fileName);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

	public InputStream getFileAsStream(
			long companyId, String repositoryId, String fileName)
		throws PortalException, SystemException {

		return getFileAsStream(companyId, repositoryId, fileName, 0);
	}

	public InputStream getFileAsStream(
			long companyId, String repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		InputStream is = null;

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node contentNode = DLUtil.getFileContentNode(
				session, companyId, repositoryId, fileName, versionNumber);

			Property data = contentNode.getProperty(JCRConstants.JCR_DATA);

			is = new BufferedInputStream(data.getStream());
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}

		return is;
	}

	public boolean hasFileContentNode(
			long companyId, String repositoryId, String fileName,
			double versionNumber)
		throws PortalException, SystemException {

		try {
			DLUtil.getFileContentNode(
				companyId, repositoryId, fileName, versionNumber);
		}
		catch (NoSuchFileException nsfe) {
			return false;
		}

		return true;
	}

	public Hits search(
			long companyId, String portletId, long groupId,
			String[] repositoryIds, String keywords)
		throws SystemException {

		try {
			HitsImpl hits = new HitsImpl();

			if (Validator.isNull(keywords)) {
				return hits;
			}

			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.PORTLET_ID, portletId);
			LuceneUtil.addRequiredTerm(
				contextQuery, LuceneFields.GROUP_ID, groupId);

			if ((repositoryIds != null) && (repositoryIds.length > 0)) {
				BooleanQuery repositoryIdsQuery = new BooleanQuery();

				for (int i = 0; i < repositoryIds.length; i++) {
					Term term = new Term("repositoryId", repositoryIds[i]);
					TermQuery termQuery = new TermQuery(term);

					repositoryIdsQuery.add(
						termQuery, BooleanClause.Occur.SHOULD);
				}

				contextQuery.add(repositoryIdsQuery, BooleanClause.Occur.MUST);
			}

			BooleanQuery searchQuery = new BooleanQuery();

			LuceneUtil.addTerm(searchQuery, LuceneFields.CONTENT, keywords);
			LuceneUtil.addTerm(searchQuery, LuceneFields.PROPERTIES, keywords);

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);
			fullQuery.add(searchQuery, BooleanClause.Occur.MUST);

			Searcher searcher = LuceneUtil.getSearcher(companyId);

			hits.recordHits(searcher.search(fullQuery));

			return hits;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (ParseException pe) {
			throw new SystemException(pe);
		}
	}

	public void updateFile(
			long companyId, String portletId, long groupId,
			String repositoryId, String fileName, double versionNumber,
			String sourceFileName, InputStream is)
		throws PortalException, SystemException {

		String versionLabel = String.valueOf(versionNumber);

		int pos = fileName.lastIndexOf(StringPool.PERIOD);

		if (pos != -1) {
			String fileNameExtension =
				fileName.substring(pos, fileName.length());

			pos = sourceFileName.lastIndexOf(StringPool.PERIOD);

			if (pos == -1) {
				throw new SourceFileNameException(sourceFileName);
			}
			else {
				String sourceFileNameExtension =
					sourceFileName.substring(pos, sourceFileName.length());

				if (!fileNameExtension.equalsIgnoreCase(
						sourceFileNameExtension)) {

					throw new SourceFileNameException(sourceFileName);
				}
			}
		}

		if (is == null) {
			throw new FileSizeException(fileName);
		}

		Session session = null;

		try {
			session = JCRFactoryUtil.createSession();

			Node rootNode = DLUtil.getRootNode(session, companyId);
			Node repositoryNode = DLUtil.getFolderNode(rootNode, repositoryId);
			Node fileNode = repositoryNode.getNode(fileName);
			Node contentNode = fileNode.getNode(JCRConstants.JCR_CONTENT);

			contentNode.checkout();

			contentNode.setProperty(JCRConstants.JCR_MIME_TYPE, "text/plain");
			contentNode.setProperty(JCRConstants.JCR_DATA, is);
			contentNode.setProperty(
				JCRConstants.JCR_LAST_MODIFIED, Calendar.getInstance());

			session.save();

			Version version = contentNode.checkin();

			contentNode.getVersionHistory().addVersionLabel(
				version.getName(), versionLabel, false);

			Indexer.updateFile(
				companyId, portletId, groupId, repositoryId, fileName);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
		catch (PathNotFoundException pnfe) {
			throw new NoSuchFileException(fileName);
		}
		catch (RepositoryException re) {
			throw new SystemException(re);
		}
		finally {
			if (session != null) {
				session.logout();
			}
		}
	}

}