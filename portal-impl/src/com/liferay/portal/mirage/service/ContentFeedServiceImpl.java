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

import com.liferay.portal.mirage.aop.ContentFeedInvoker;
import com.liferay.portal.mirage.aop.SearchCriteriaInvoker;
import com.liferay.portal.mirage.model.MirageJournalFeed;
import com.liferay.portlet.journal.model.JournalFeed;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.ContentFeed;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.UpdateCriteria;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.service.custom.ContentFeedService;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ContentFeedServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Karthik Sudarshan
 * @author Brian Wing Shun Chan
 *
 */
public class ContentFeedServiceImpl implements ContentFeedService {

	public void createContentFeed(ContentFeed contentFeed) throws CMSException {
		ContentFeedInvoker contentFeedInvoker = (ContentFeedInvoker)contentFeed;

		contentFeedInvoker.invoke();
	}

	public void deleteContentFeed(ContentFeed contentFeed) throws CMSException {
		ContentFeedInvoker contentFeedInvoker = (ContentFeedInvoker)contentFeed;

		contentFeedInvoker.invoke();
	}

	public ContentFeed getContentFeed(
			ContentFeed contentFeed, OptionalCriteria optionalCriteria)
		throws CMSException {

		ContentFeedInvoker contentFeedInvoker = (ContentFeedInvoker)contentFeed;

		contentFeedInvoker.invoke();

		JournalFeed feed = (JournalFeed)contentFeedInvoker.getReturnValue();

		return new MirageJournalFeed(feed);
	}

	public int getContentFeedSearchCount(SearchCriteria searchCriteria)
		throws CMSException {

		SearchCriteriaInvoker searchCriteriaInvoker =
			(SearchCriteriaInvoker)searchCriteria;

		searchCriteriaInvoker.invoke();

		Integer i = (Integer)searchCriteriaInvoker.getReturnValue();

		return i.intValue();
	}

	public List<ContentFeed> searchContentFeeds(SearchCriteria searchCriteria)
		throws CMSException {

		SearchCriteriaInvoker searchCriteriaInvoker =
			(SearchCriteriaInvoker)searchCriteria;

		searchCriteriaInvoker.invoke();

		List<JournalFeed> feeds = (List<JournalFeed>)
			searchCriteriaInvoker.getReturnValue();

		List<ContentFeed> contentFeeds = new ArrayList<ContentFeed>(
			feeds.size());

		for (JournalFeed feed : feeds) {
			contentFeeds.add(new MirageJournalFeed(feed));
		}

		return contentFeeds;
	}

	public void updateContentFeed(
			ContentFeed contentFeed, UpdateCriteria updateCriteria)
		throws CMSException {

		ContentFeedInvoker contentFeedInvoker = (ContentFeedInvoker)contentFeed;

		contentFeedInvoker.invoke();
	}

}