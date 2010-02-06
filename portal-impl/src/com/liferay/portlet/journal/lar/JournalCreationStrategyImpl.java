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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portlet.journal.model.JournalArticle;

/**
 * <a href="JournalCreationStrategyImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * Provides the strategy for creating new content when new Journal content is
 * imported into a layout set from a LAR. The default strategy implemented by
 * this class is to return zero for the author and approval user ids, which
 * causes the default user id import strategy to be used. Content will be added
 * as is with no transformations.
 * </p>
 *
 * @author Joel Kozikowski
 * @see	   com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see	   com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl
 */
public class JournalCreationStrategyImpl implements JournalCreationStrategy {

	public long getAuthorUserId(PortletDataContext context, Object journalObj)
		throws Exception {

		return JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY;
	}

	public long getApprovalUserId(PortletDataContext context, Object journalObj)
		throws Exception {

		return JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY;
	}

	public String getTransformedContent(
			PortletDataContext context, JournalArticle newArticle)
		throws Exception {

		return JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED;
	}

	public boolean addCommunityPermissions(
			PortletDataContext context, Object journalObj)
		throws Exception {

		return true;
	}

	public boolean addGuestPermissions(
			PortletDataContext context, Object journalObj)
		throws Exception {

		return true;
	}

}