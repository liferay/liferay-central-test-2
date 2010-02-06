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
 * <a href="JournalCreationStrategy.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * An interface defining how newly created content should be added to the
 * Journal when imported from a LAR file. A class implementing this interface
 * should be specified in <i>portal.properties</i> under the
 * <b>journal.lar.creation.strategy</b> property.
 * </p>
 *
 * @author Joel Kozikowski
 */
public interface JournalCreationStrategy {

	/**
	 * Constant returned by getAuthorUserId() and/or getApprovalUserId() that
	 * indicates the default portlet data import user id strategy that should be
	 * used to determine the user id.
	 */
	public static final long USE_DEFAULT_USER_ID_STRATEGY = 0;

	/**
	 * Constant returned by getTransformedContent() to indicate that the article
	 * text should remained unchanged.
	 */
	public static final String ARTICLE_CONTENT_UNCHANGED = null;

	/**
	 * Returns the author's user id to assign to newly created content. If zero
	 * is returned, the default user id import strategy will determine the
	 * author id.
	 *
	 * @return the author's user id or USE_DEFAULT_USER_ID_STRATEGY to use the
	 *		   default user id strategy
	 */
	public long getAuthorUserId(PortletDataContext context, Object journalObj)
		throws Exception;

	/**
	 * Returns the approver's user id to assign to newly created content. If
	 * zero is returned, the default user id import strategy will determine the
	 * author id.
	 *
	 * @return the approver's user id or USE_DEFAULT_USER_ID_STRATEGY to use the
	 *		   default user id strategy
	 */
	public long getApprovalUserId(PortletDataContext context, Object journalObj)
		throws Exception;

	/**
	 * Gives the content creation strategy an opportunity to transform the
	 * content before the new article is saved to the database. Possible use
	 * cases include using Velocity to merge in community specific values into
	 * the text. Returns the new content to assign to the article. If null is
	 * returned, the article content will be added unchanged.
	 *
	 * @return the transformed content to save in the database or
	 *		   ARTICLE_CONTENT_UNCHANGED if the content should be added
	 *		   unchanged
	 */
	public String getTransformedContent(
			PortletDataContext context, JournalArticle newArticle)
		throws Exception;

	/**
	 * Returns true if the default community permissions should be added when
	 * the specified journalObj is created.
	 *
	 * @return true if default community permissions should be added to the
	 *		   specified journalObj
	 */
	public boolean addCommunityPermissions(
			PortletDataContext context, Object journalObj)
		throws Exception;

	/**
	 * Returns true if the default guest permissions should be added when the
	 * specified journalObj is created.
	 *
	 * @return true if default guest permissions should be added to the
	 *		   specified journalObj
	 */
	public boolean addGuestPermissions(
			PortletDataContext context, Object journalObj)
		throws Exception;

}