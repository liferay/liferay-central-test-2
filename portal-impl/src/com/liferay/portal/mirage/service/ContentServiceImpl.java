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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.service;

import com.liferay.portal.mirage.aop.ContentInvoker;
import com.liferay.portal.mirage.aop.SearchCriteriaInvoker;
import com.liferay.portal.mirage.model.MirageJournalArticle;
import com.liferay.portal.mirage.util.MirageLoggerUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.VersionableContent;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.service.custom.ContentService;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="ContentServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Prakash Reddy
 * @author Karthik Sudarshan
 * @author K.Joshna Reddy
 *
 */
public class ContentServiceImpl implements ContentService {

	public void checkinContent(Content content) {
		throw new UnsupportedOperationException();
	}

	public VersionableContent checkoutContent(
		String contentName, String contentTypeUUID) {

		throw new UnsupportedOperationException();
	}

	public int contentSearchCount(
			ContentType contentType, SearchCriteria searchCriteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "contentSearchCount");

		SearchCriteriaInvoker searchCriteriaInvoker =
			(SearchCriteriaInvoker)searchCriteria;

		searchCriteriaInvoker.invoke();

		Integer i = (Integer)searchCriteriaInvoker.getReturnValue();

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "contentSearchCount");

		return i.intValue();
	}

	public int contentSearchCount(SearchCriteria searchCriteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "contentSearchCount");

		SearchCriteriaInvoker searchCriteriaInvoker =
			(SearchCriteriaInvoker)searchCriteria;

		searchCriteriaInvoker.invoke();

		Integer i = (Integer)searchCriteriaInvoker.getReturnValue();

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "contentSearchCount");

		return i.intValue();
	}

	public void createContent(Content content) throws CMSException {
		MirageLoggerUtil.enter(_log, _CLASS_NAME, "createContent");

		process(content);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "createContent");
	}

	public void deleteContent(Content content) throws CMSException {
		MirageLoggerUtil.enter(_log, _CLASS_NAME, "deleteContent");

		process(content);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "deleteContent");
	}

	public void deleteContent(String contentName, String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public Content getContent(Content content, OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "getContent");

		ContentInvoker contentInvoker = (ContentInvoker)content;

		contentInvoker.invoke();

		JournalArticle article =
			(JournalArticle)contentInvoker.getReturnValue();

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "getContent");

		return new MirageJournalArticle(article);
	}

	public Content getContentByNameAndType(
		String contentName, String contentTypeUUID) {

		throw new UnsupportedOperationException();
	}

	public Content getContentByNameTypeNameAndCategory(
		String contentName, String contentTypeName, String categoryName) {

		throw new UnsupportedOperationException();
	}

	public Content getContentByUUID(String uuid) {
		throw new UnsupportedOperationException();
	}

	public VersionableContent getContentByVersion(
		String contentName, String contentTypeUUID, String versionName) {

		throw new UnsupportedOperationException();
	}

	public List<String> getContentNamesByType(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public List<Content> getContentsByType(String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public String getContentURL(String appURL, String UUID) {
		throw new UnsupportedOperationException();
	}

	public List<String> getVersionNames(
		String contentName, String contentTypeUUID) {

		throw new UnsupportedOperationException();
	}

	public List<VersionableContent> getVersions(
		String contentName, String contentTypeUUID) {

		throw new UnsupportedOperationException();
	}

	public List<Content> searchContents(SearchCriteria searchCriteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "searchContents");

		SearchCriteriaInvoker searchCriteriaInvoker =
			(SearchCriteriaInvoker)searchCriteria;

		searchCriteriaInvoker.invoke();

		List<JournalArticle> articles = (List<JournalArticle>)
			searchCriteriaInvoker.getReturnValue();

		List<Content> contents = new ArrayList<Content>(
			articles.size());

		for (JournalArticle article : articles) {
			contents.add(new MirageJournalArticle(article));
		}

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "searchContents");

		return contents;
	}

	public List<Content> searchContentsByType(
			ContentType contentType, SearchCriteria searchCriteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "searchContentsByType");

		SearchCriteriaInvoker searchCriteriaInvoker =
			(SearchCriteriaInvoker)searchCriteria;

		searchCriteriaInvoker.invoke();

		List<JournalArticle> articles = (List<JournalArticle>)
			searchCriteriaInvoker.getReturnValue();

		List<Content> contents = new ArrayList<Content>(articles.size());

		for (JournalArticle article : articles) {
			contents.add(new MirageJournalArticle(article));
		}

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "searchContentsByType");

		return contents;
	}

	public void unCheckoutContent(String contentName, String contentTypeUUID) {
		throw new UnsupportedOperationException();
	}

	public void updateContent(Content content) throws CMSException {
		MirageLoggerUtil.enter(_log, _CLASS_NAME, "updateContent");

		process(content);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "updateContent");
	}

	public void updateContent(Content content, OptionalCriteria criteria)
		throws CMSException {

		MirageLoggerUtil.enter(_log, _CLASS_NAME, "updateContent");

		process(content);

		MirageLoggerUtil.exit(_log, _CLASS_NAME, "updateContent");
	}

	public void updateFileField(
		String contentUUID, String fieldName,
		InputStream updatedFileInputStream) {

		throw new UnsupportedOperationException();
	}

	protected void process(Content content) throws CMSException {
		ContentInvoker contentInvoker = (ContentInvoker)content;

		contentInvoker.invoke();
	}

	private static final String _CLASS_NAME =
		ContentServiceImpl.class.getName();

	private static final Log _log =
		LogFactory.getLog(ContentServiceImpl.class);

}